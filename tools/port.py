#!/usr/bin/env python3
"""Porting helper: Middle-earth 1.21.8 (Fabric) -> 1.20.1 (Fabric).

Commands
--------
inventory   Scan a donor Java tree for APIs that do not exist in 1.20.1 and
            write a markdown report (counts + worst files per pattern).
import      Copy donor sources into the project, applying the mechanical
            rewrites that are safe to automate.
errors      Run gradlew compileJava and group javac errors into a worklist.

Usage
-----
    python tools/port.py inventory --out build/port-report.md
    python tools/port.py import block/registration registries item/utils
    python tools/port.py errors
"""
from __future__ import annotations

import argparse
import collections
import json
import re
import subprocess
import sys
from pathlib import Path

PROJECT = Path(__file__).resolve().parent.parent
DONOR_ROOT = Path("H:/MinecraftMods/_donor/middle-earth/src/main/java")
DONOR_PKG = "net/sevenstars/middleearth"
TARGET_PKG = "net/jukoz/me"

# (id, regex, note) -- what breaks on 1.20.1 and roughly what to do about it.
PATTERNS = [
    ("identifier-of", r"Identifier\.of\(",
     "1.21 factory method; 1.20.1 has new Identifier(ns, path)"),
    ("settings-registrykey", r"\.registryKey\(",
     "Settings.registryKey is 1.21+; drop it and register by Identifier"),
    ("data-components", r"\bDataComponentTypes\.",
     "DataComponents landed in 1.20.5; 1.20.1 needs NBT / FoodProperties / capabilities"),
    ("component-set", r"\.set\(\s*DataComponentTypes\.|\.get\(\s*DataComponentTypes\.",
     "component read/write on ItemStack"),
    ("component-package", r"net\.minecraft\.component\.",
     "component package does not exist in 1.20.1"),
    ("registry-entry-armor", r"RegistryEntry<\s*ArmorMaterial\s*>|Holder<\s*ArmorMaterial\s*>",
     "1.21 armour material is a registry entry; 1.20.1 uses the ArmorMaterial interface"),
    ("recipe-input", r"\bRecipeInput\b",
     "1.20.5+ recipe API; 1.20.1 uses Inventory"),
    ("data-component-type", r"\bDataComponentType\b",
     "no data component types in 1.20.1"),
    ("equipment-asset", r"EquipmentAsset",
     "1.21.2+ equipment assets"),
    ("item-model-definition", r"\bItemModels\.|net\.minecraft\.client\.item\.ItemModel\b",
     "1.21.4+ item model definitions (mostly a data concern: assets/<ns>/items/*.json)"),
]

JAVA_COMMENT = re.compile(r"//[^\n]*|/\*.*?\*/", re.S)


def iter_java(root: Path):
    yield from sorted(root.rglob("*.java"))


def strip_comments(text: str) -> str:
    return JAVA_COMMENT.sub("", text)


def cmd_inventory(args) -> int:
    donor = Path(args.donor)
    if not donor.is_dir():
        print(f"donor not found: {donor}", file=sys.stderr)
        return 2

    hits = {pid: collections.Counter() for pid, _, _ in PATTERNS}
    totals = {pid: 0 for pid, _, _ in PATTERNS}
    files = 0
    lines = 0

    for path in iter_java(donor):
        files += 1
        raw = path.read_text(encoding="utf-8", errors="replace")
        lines += raw.count("\n") + 1
        body = strip_comments(raw)
        rel = path.relative_to(donor).as_posix()
        for pid, rx, _ in PATTERNS:
            n = len(re.findall(rx, body))
            if n:
                totals[pid] += n
                hits[pid][rel] = n

    out = ["# Port inventory", "",
           f"- donor: {donor}",
           f"- java files: {files}, lines: {lines}", "",
           "| pattern | hits | files | note |", "|---|---:|---:|---|"]
    for pid, _, note in PATTERNS:
        out.append(f"| {pid} | {totals[pid]} | {len(hits[pid])} | {note} |")

    # which component types are actually touched, and how often
    per_component = collections.Counter()
    for path in iter_java(donor):
        body = strip_comments(path.read_text(encoding="utf-8", errors="replace"))
        for m in re.finditer(r"(?:DataComponentTypes|DataComponentTypesME)\.([A-Z][A-Z0-9_]*)", body):
            per_component[m.group(1)] += 1

    out += ["", "## Component usage (vanilla DataComponentTypes + mod DataComponentTypesME)", "",
            "| component | uses |", "|---|---:|"]
    for name, n in per_component.most_common():
        out.append(f"| {name} | {n} |")

    out += ["", "## Worst offenders", ""]
    for pid, _, _ in PATTERNS:
        if not hits[pid]:
            continue
        out.append(f"### {pid} ({totals[pid]})")
        for rel, n in hits[pid].most_common(10):
            out.append(f"- {n:>4}  {rel}")
        out.append("")

    dest = Path(args.out)
    dest.parent.mkdir(parents=True, exist_ok=True)
    dest.write_text("\n".join(out), encoding="utf-8")
    print("\n".join(out[:12]))
    print(f"\nwrote {dest}")
    return 0


def drop_call(text: str, needle: str):
    """Remove every needle(...) call, paren-aware, including a leading dot."""
    out, i, removed = [], 0, 0
    while True:
        j = text.find(needle, i)
        if j < 0:
            out.append(text[i:])
            break
        start = j - 1 if j > 0 and text[j - 1] == "." else j
        k = j + len(needle)
        depth = 1
        while k < len(text) and depth:
            if text[k] == "(":
                depth += 1
            elif text[k] == ")":
                depth -= 1
            k += 1
        out.append(text[i:start])
        i = k
        removed += 1
    return "".join(out), removed


def unwrap_call(text: str, prefix: str):
    """Replace prefix(...) with its inner argument list: f(A, X) -> X for prefix 'f(A,'."""
    open_offset = prefix.index("(")
    out, i, n = [], 0, 0
    while True:
        j = text.find(prefix, i)
        if j < 0:
            out.append(text[i:])
            break
        if j and (text[j - 1].isalnum() or text[j - 1] in "_$"):
            # part of a longer identifier, e.g. BlockRegistryKey.of(...)
            out.append(text[i:j + len(prefix)])
            i = j + len(prefix)
            continue
        # Walk to the ')' that closes the prefix's own '('. Counting must start at the
        # opening paren itself, and must start at zero: the scan begins on it.
        k, depth = j + open_offset, 0
        while k < len(text):
            if text[k] == "(":
                depth += 1
            elif text[k] == ")":
                depth -= 1
                if depth == 0:
                    k += 1
                    break
            k += 1
        out.append(text[i:j])
        out.append(text[j + len(prefix):k - 1])
        i = k
        n += 1
    return "".join(out), n


COMP_CALL = re.compile(
    r"(?P<recv>[A-Za-z_][\w.]*)\.(?P<op>getOrDefault|contains|remove|get|set)"
    r"\(\s*DataComponentTypesME\.(?P<key>[A-Z0-9_]+)")


def _split_rest(text: str, pos: int):
    """Scan to the ')' that closes the call; text[pos] is just past the first argument."""
    depth = 1
    i = pos
    while i < len(text) and depth:
        if text[i] == "(":
            depth += 1
        elif text[i] == ")":
            depth -= 1
        i += 1
    return text[pos:i - 1], i


def rewrite_components(text: str):
    """stack.set(DataComponentTypesME.X, v) -> MEComponents.set(stack, MEComponents.X, v)."""
    out, i, n = [], 0, 0
    while True:
        m = COMP_CALL.search(text, i)
        if not m:
            out.append(text[i:])
            break
        rest, end = _split_rest(text, m.end())
        recv, op, key = m.group("recv"), m.group("op"), m.group("key")
        rest = rest.strip()
        if rest.startswith(","):
            rest = rest[1:].strip()
        out.append(text[i:m.start()])
        if op == "set":
            out.append(f"MEComponents.set({recv}, MEComponents.{key}, {rest})")
        elif op == "getOrDefault":
            out.append(f"MEComponents.getOrDefault({recv}, MEComponents.{key}, {rest})")
        else:
            out.append(f"MEComponents.{op}({recv}, MEComponents.{key})")
        i = end
        n += 1
    return "".join(out), n


def ensure_import(text: str, fqcn: str) -> str:
    if f"import {fqcn};" in text:
        return text
    pkg = fqcn.rsplit(".", 1)[0]
    m = re.search(r"package\s+" + re.escape(pkg) + r"\s*;", text)
    if m:
        return text  # same package, no import needed
    m = re.search(r"package\s+[\w.]+\s*;", text)
    if m:
        return text[:m.end()] + f"\nimport {fqcn};" + text[m.end():]
    return f"import {fqcn};\n" + text


# Each entry is (name, pattern, replacement). The patterns are regexes anchored on a
# non-identifier boundary: a plain string replace of "Identifier.of(" also matched
# CategoryIdentifier.of( and produced "Categorynew Identifier(".
REWRITES = [
    ("package-api", r"net[.]sevenstars[.]api", "net.jukoz.me.api"),
    ("package", r"net[.]sevenstars[.]middleearth", "net.jukoz.me"),
    ("identifier", r"(?<![A-Za-z0-9_$])Identifier[.]of[(]", "new Identifier("),
    ("registrykey-type-block", r"(?<![A-Za-z0-9_$])RegistryKey<Block>", "Identifier"),
    ("registrykey-type-item", r"(?<![A-Za-z0-9_$])RegistryKey<Item>", "Identifier"),
    # 1.21.2 split ModelTransform.of into a builder; pivot(x, y, z) is the same thing
    ("modeltransform-origin", r"(?<![A-Za-z0-9_$])ModelTransform[.]origin[(]", "ModelTransform.pivot("),
    # 1.20.1 kept the old names for both of these.
    ("instrument", r"(?<![A-Za-z0-9_$])NoteBlockInstrument", "Instrument"),
    ("short-grass", r"(?<![A-Za-z0-9_$])Blocks[.]SHORT_GRASS", "Blocks.GRASS"),
    # 1.21.2 component classes that do not exist in 1.20.1; the stand-ins live in compat.
    ("dyed-color-import", r"net[.]minecraft[.]component[.]type[.]DyedColorComponent", "net.jukoz.me.compat.DyedColorComponent"),
    ("dyed-color-key", r"(?<![A-Za-z0-9_$])DataComponentTypes[.]DYED_COLOR", "MEComponents.DYED_COLOR"),
    # 1.21.2 renames, verified against the mapped 1.20.1 jar.
    ("move-origin", r"Transformation[.]Targets[.]MOVE_ORIGIN", "Transformation.Targets.TRANSLATE"),
    ("animation-definition", r"(?<![A-Za-z0-9_$])AnimationDefinition", "Animation"),
    # 1.21 helpers with a direct 1.20.1 equivalent.
    ("of-vanilla", r"(?<![A-Za-z0-9_$])Identifier[.]ofVanilla[(]", 'new Identifier("minecraft", '),
    # 1.21 moved the trim classes; 1.20.1 keeps them in net.minecraft.item.trim.
    # 1.21 moved the food classes into the component package.
    ("food-components", r"net[.]minecraft[.]component[.]type[.]FoodComponents", "net.minecraft.item.FoodComponents"),
    ("food-component", r"net[.]minecraft[.]component[.]type[.]FoodComponent", "net.minecraft.item.FoodComponent"),
    ("trim-package", r"net[.]minecraft[.]item[.]equipment[.]trim[.]", "net.minecraft.item.trim."),
    # 1.21.2 renames on PlayerEntity / World.
    ("is-in-creative", r"(?<![A-Za-z0-9_$])isInCreativeMode[(]", "isCreative("),
    ("add-particle-client", r"(?<![A-Za-z0-9_$])addParticleClient[(]", "addParticle("),
    # 1.21.2 split ActionResult.PASS into two; 1.20.1 only has PASS.
    ("pass-to-default", r"ActionResult[.]PASS_TO_DEFAULT_BLOCK_ACTION", "ActionResult.PASS"),
    # 1.21 renamed the FoodComponent accessors.
    ("food-nutrition-getter", r"(?<![A-Za-z0-9_$])nutrition[(][)]", "getHunger()"),
    ("food-nutrition-builder", r"(?<![A-Za-z0-9_$])nutrition[(](?![)])", "hunger("),
    # repairs already-retouched builder sites: getHunger(x) -> hunger(x), getHunger() stays
    ("food-nutrition-fix", r"[.]getHunger[(](?![)])", ".hunger("),
    ("food-saturation-fix", r"[.]getSaturationModifier[(](?![)])", ".saturationModifier("),
    ("food-saturation", r"(?<![A-Za-z0-9_$])saturation[(]", "getSaturationModifier("),
    # 1.21 DynamicRegistryManager.getOrThrow collapsed to get() in 1.20.1.
    ("registry-manager-get", r"getRegistryManager[(][)][.]getOrThrow[(]", "getRegistryManager().get("),
    # --- DataComponentTypes batch 1: settings-side and self-contained storage ---
    ("unbreakable-component", r"[.]component[(]DataComponentTypes[.]UNBREAKABLE, Unit[.]INSTANCE[)]", ".unbreakable()"),
    ("max-damage-setting", r"[.]component[(]DataComponentTypes[.]MAX_DAMAGE, ", ".maxDamage("),
    ("lore-setting", r"[.]component[(]DataComponentTypes[.]LORE, ", ".lore("),
    ("provides-banner-patterns", r"[.]component[(]DataComponentTypes[.]PROVIDES_BANNER_PATTERNS, ", ".providesBannerPatterns("),
    ("item-name", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]ITEM_NAME, ", "\\1.setCustomName("),
    ("item-model-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]ITEM_MODEL, ", "MEComponents.set(\\1, MEComponents.ITEM_MODEL, "),
    ("item-model-get", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]ITEM_MODEL[)]", "MEComponents.get(\\1, MEComponents.ITEM_MODEL)"),
    ("entity-data", r"itemStack[.]set[(]DataComponentTypes[.]ENTITY_DATA, NbtComponent[.]of[(]compoundData[)][)]", "itemStack.getOrCreateSubNbt(\"EntityTag\").copyFrom(compoundData)"),
    # --- DataComponentTypes batch 2 (census: build/agents/components-batch2.md) ---
    ("banner-import", r"net[.]minecraft[.]component[.]type[.]BannerPatternsComponent", "net.jukoz.me.compat.MEBannerPatterns"),
    ("banner-decl", r"BannerPatternsComponent ([A-Za-z_][A-Za-z0-9_]*)", "MEBannerPatterns \\1"),
    ("banner-getordefault", r"[(]?[(]BannerPatternsComponent[)]([A-Za-z_][A-Za-z0-9_]*)[.]getOrDefault[(]DataComponentTypes[.]BANNER_PATTERNS, MEBannerPatterns[.]DEFAULT[)]", "MEBannerPatterns.get(\\1)"),
    ("banner-get", r"[(]?[(]BannerPatternsComponent[)]([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]BANNER_PATTERNS[)]", "MEBannerPatterns.get(\\1)"),
    ("banner-default-lit", r"BannerPatternsComponent[.]DEFAULT", "MEBannerPatterns.DEFAULT"),
    ("banner-getordefault2", r"([(]BannerPatternsComponent[)])?([A-Za-z_][A-Za-z0-9_]*)[.]getOrDefault[(]DataComponentTypes[.]BANNER_PATTERNS, MEBannerPatterns[.]DEFAULT[)]", "MEBannerPatterns.get(\\2)"),
    ("banner-type-ref", r"BannerPatternsComponent[.](Layer|Builder)", "MEBannerPatterns.\\1"),
    ("banner-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]BANNER_PATTERNS, ", "MEBannerPatterns.set(\\1, "),
    ("base-color-get", r"[(]?[(]DyeColor[)]([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]BASE_COLOR[)]", "MEBannerPatterns.getBaseColor(\\1)"),
    ("base-color-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]BASE_COLOR,[^;]*;", "/* base colour is intrinsic to the banner item in 1.20.1 */;"),
    ("trim-get", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]TRIM[)]", "MEArmorTrim.get(\\1)"),
    ("trim-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]TRIM, ", "MEArmorTrim.set(\\1, "),
    ("food-get", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]FOOD[)]", "\\1.getItem().getFoodComponent()"),
    ("food-contains", r"([A-Za-z_][A-Za-z0-9_]*)[.]contains[(]DataComponentTypes[.]FOOD[)]", "\\1.getItem().isFood()"),
    ("consumable-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]CONSUMABLE, ConsumableComponents[.]FOOD[)];?", ""),
    ("consumable-get", r"ConsumableComponent ([A-Za-z_][A-Za-z0-9_]*) = ([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]CONSUMABLE[)];?", ""),
    ("consumable-null", r"consumableComponent != null[ ]*&&[ ]*", ""),
    ("onconsume-1.20", r"([A-Za-z_][A-Za-z0-9_]*)[.]onConsume[(]([^,]+), ([^,]+), ([^,]+), [^)]+[)]", "\\1.onConsume(\\2, \\3, \\4)"),
    ("charged-settings", r"[.]component[(]DataComponentTypes[.]CHARGED_PROJECTILES, ChargedProjectilesComponent[.]DEFAULT[)]", ""),
    ("charged-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]CHARGED_PROJECTILES, ChargedProjectilesComponent[.]of[(]([^)]*)[)][)]", "MECrossbow.setCharged(\\1, \\2)"),
    ("charged-default", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]CHARGED_PROJECTILES, ChargedProjectilesComponent[.]DEFAULT[)]", "MECrossbow.clear(\\1)"),
    ("charged-remove", r"([A-Za-z_][A-Za-z0-9_]*)[.]remove[(]DataComponentTypes[.]CHARGED_PROJECTILES[)]", "MECrossbow.clear(\\1)"),
    ("tool-correct", r"toolComponent[.]isCorrectForDrops[(]([A-Za-z_][A-Za-z0-9_]*)[)]", "stack.isSuitableFor(\\1)"),
    ("tool-decl", r"ToolComponent toolComponent = stack[.]get[(]DataComponentTypes[.]TOOL[)];", ""),
    ("tool-assert", r"assert toolComponent != null;", ""),
    ("tool-null", r"toolComponent != null[ ]*&&[ ]*", ""),
    ("contains-damage", r"([A-Za-z_][A-Za-z0-9_]*)[.]contains[(]DataComponentTypes[.]DAMAGE[)]", "\\1.isDamageable()"),
    ("contains-maxdamage", r"([A-Za-z_][A-Za-z0-9_]*)[.]contains[(]DataComponentTypes[.]MAX_DAMAGE[)]", "\\1.isDamageable()"),
    ("maxdamage-get-null", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]MAX_DAMAGE[)] == null", "!MEDurability.hasCustom(\\1)"),
    ("maxdamage-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]MAX_DAMAGE, ([^;]+)[)]", "MEDurability.set(\\1, \\2)"),
    ("component-damage", r"[.]component[(]DataComponentTypes[.]DAMAGE, [^)]*[)]", ""),
    ("use-remainder-decl", r"UseRemainderComponent ([A-Za-z_][A-Za-z0-9_]*) = ", "ItemStack \\1 = "),
    ("use-remainder", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]USE_REMAINDER[)]", "\\1.getRecipeRemainder()"),
    ("use-remainder-convert", r"([A-Za-z_][A-Za-z0-9_]*)[.]convertInto[(][)]", "\\1"),
    ("equippable-import", r"net[.]minecraft[.]component[.]type[.]EquippableComponent", "net.minecraft.util.Identifier"),
    ("equippable-decl", r"EquippableComponent ([A-Za-z_][A-Za-z0-9_]*)", "Identifier \\1"),
    ("equippable-get", r"[(]?[(]EquippableComponent[)]([A-Za-z_][A-Za-z0-9_]*)[.]get[(]DataComponentTypes[.]EQUIPPABLE[)]", "MEComponents.get(\\1, MEComponents.EQUIPPABLE_ASSET)"),
    ("profile-set", r"([A-Za-z_][A-Za-z0-9_]*)[.]set[(]DataComponentTypes[.]PROFILE, new ProfileComponent[(]([^)]*)[)][)]", "net.minecraft.nbt.NbtHelper.writeGameProfile(\\1.getOrCreateSubNbt(\"SkullOwner\"), \\2)"),
    ("import-consumable", r"import net[.]minecraft[.]component[.]type[.]ConsumableComponent;", ""),
    ("import-useRemainder", r"import net[.]minecraft[.]component[.]type[.]UseRemainderComponent;", ""),
    ("import-profile", r"import net[.]minecraft[.]component[.]type[.]ProfileComponent;", ""),
    ("import-tool", r"import net[.]minecraft[.]component[.]type[.]ToolComponent;", ""),
    ("import-charged", r"import net[.]minecraft[.]component[.]type[.]ChargedProjectilesComponent;", ""),
    # --- recipe trio (census: build/agents/recipe-trio.md) ---
    ("recipe-entry-wild", r"RecipeEntry<[?] extends ([A-Za-z0-9_]+)>", "\\1"),
    ("recipe-entry-flat", r"RecipeEntry<([A-Za-z0-9_]+)>", "\\1"),
    ("recipe-entry-value", r"recipeEntry[.]value[(][)]", "recipeEntry"),
    ("recipe-entry-value2", r"match[.]value[(][)]", "match"),
    ("recipe-entry-value3", r"recipe[.]value[(][)]", "recipe"),
    ("import-recipe-entry", r"import net[.]minecraft[.]recipe[.]RecipeEntry;", ""),
    ("server-recipe-manager", r"ServerRecipeManager", "RecipeManager"),
    ("get-all-of-type", r"[.]getAllOfType[(]", ".listAllOfType("),
    ("get-all-matches-tolist", r"([.]getAllMatches[(][^;]*[)])[.]toList[(][)]", "\\1"),
    ("import-crafting-input", r"import net[.]minecraft[.]recipe[.]input[.]CraftingRecipeInput;", "import net.minecraft.inventory.RecipeInputInventory;"),
    ("crafting-recipe-input", r"CraftingRecipeInput", "RecipeInputInventory"),
    ("get-stack-in-slot", r"[.]getStackInSlot[(]", ".getStack("),
    ("recipe-remainders", r"getRecipeRemainders", "getRemainder"),
    ("craft-lookup", r"(craft[(][A-Za-z0-9_]+ [A-Za-z0-9_]+, )RegistryWrapper[.]WrapperLookup", "\\1DynamicRegistryManager"),
    ("output-lookup", r"(getOutput[(])RegistryWrapper[.]WrapperLookup", "\\1DynamicRegistryManager"),
    ("special-serializer-nest", r"SpecialCraftingRecipe[.]SpecialRecipeSerializer", "SpecialRecipeSerializer"),
    ("import-single-stack", r"import net[.]minecraft[.]recipe[.]input[.]SingleStackRecipeInput;", "import net.jukoz.me.compat.SingleStackRecipeInput;"),
    ("import-recipe-input", r"import net[.]minecraft[.]recipe[.]input[.]RecipeInput;", ""),
    ("mecomponents-get", r"([A-Za-z_][A-Za-z0-9_]*)[.]get[(](MEComponents[.][A-Z_]+[A-Za-z0-9_]*)[)]", "MEComponents.get(\\1, \\2)"),
    ("mecomponents-contains", r"([A-Za-z_][A-Za-z0-9_]*)[.]contains[(](MEComponents[.][A-Z_]+[A-Za-z0-9_]*)[)]", "MEComponents.contains(\\1, \\2)"),
    ("recipe-remainder-item", r"([A-Za-z_][A-Za-z0-9_]*)[.]getItem[(][)][.]getRecipeRemainder[(][)]", "\\1.getRecipeRemainder()"),
    ("import-dct", r"import net[.]minecraft[.]component[.]DataComponentTypes;", ""),
    # --- render layer R1: mechanical rules + shims (report: build/agents/render-r1-report.md) ---
    # R2: extends XRenderState adapters
    # R3: handheld registry re-base
    # 1.21.8 moved every render state into net.minecraft.client.render.entity.state;
    # 1.20.1 has none of it, so the compat package supplies pass-through skeletons.
    ("render-state-pkg", r"net[.]minecraft[.]client[.]render[.]entity[.]state[.]", "net.jukoz.me.compat.client.render.state."),
    # ItemDisplayContext -> ModelTransformationMode, and its import moved from
    # net.minecraft.item to net.minecraft.client.render.model.json.
    ("item-display-context-import", r"net[.]minecraft[.]item[.]ItemDisplayContext", "net.minecraft.client.render.model.json.ModelTransformationMode"),
    ("item-display-context", r"ItemDisplayContext", "ModelTransformationMode"),
    # Direction.getPositiveHorizontalDegrees() (1.21) is Direction.asRotation() (1.20.1).
    ("direction-degrees", r"[.]getPositiveHorizontalDegrees[(][)]", ".asRotation()"),
    # 1.21.8 getArmorGlintConsumer(vp, layer, glint) gained a 4th arg in 1.20.1:
    # getArmorGlintConsumer(vp, layer, solid, glint). The donor shape always ends in
    # an x.hasGlint() call, so match a real argument list (nested parens allowed) and
    # refuse to touch sites that already have a 4th argument (negative lookahead).
    ("armor-glint-3arg", r"getArmorGlintConsumer\(([^,()]+),\s*(RenderLayer\.[A-Za-z_]\w*\((?:[^()]|\([^()]*\))*\)),\s*([^,()]+\.hasGlint\(\))\)(?!\s*,)", "getArmorGlintConsumer(\\1, \\2, true, \\3)"),
]

# 1.21 wraps block/item ids in a RegistryKey; 1.20.1 registers by Identifier.
UNWRAPS = [
    ("registrykey-block", "RegistryKey.of(RegistryKeys.BLOCK, "),
    ("registrykey-item", "RegistryKey.of(RegistryKeys.ITEM, "),
]



def rewrite(text: str):
    stats = collections.Counter()
    for name, pattern, new in REWRITES:
        text, n = re.subn(pattern, new, text)
        if n:
            stats[name] += n
    text, n = drop_call(text, "registryKey(")
    if n:
        stats["registryKey"] = n
    for name, prefix in UNWRAPS:
        text, n = unwrap_call(text, prefix)
        if n:
            stats[name] = n
    text, n = rewrite_components(text)
    if n:
        stats["components"] = n
        # rewrite_components emits MEComponents.* call sites, which need the import
        # just as much as the key renames below do.
        text = ensure_import(text, "net.jukoz.me.item.MEComponents")

    text, extra = rewrite_attributes(text)
    stats.update(extra)
    text, extra = rewrite_nbt(text)
    stats.update(extra)
    text, extra = rewrite_nbt_two_arg(text)
    stats.update(extra)


    # 1.21 items declare their components on Item.Settings; MESettings keeps that chain
    # intact and forwards the values to MEComponents when the Item is constructed.
    n = text.count("new Item.Settings()")
    if n:
        text = text.replace("new Item.Settings()", "new MESettings()")
        stats["settings"] = n
        text = ensure_import(text, "net.jukoz.me.item.MESettings")

    # key references: DataComponentTypesME.X -> MEComponents.X (ComponentType -> MEComponent)
    n = text.count("DataComponentTypesME.")
    if n:
        text = text.replace("DataComponentTypesME.", "MEComponents.")
        stats["component-keys"] = n
        text = ensure_import(text, "net.jukoz.me.item.MEComponents")

    return text, dict(stats)


# --------------------------------------------------------------------------
# 1.20.5 / 1.21 API drift
# --------------------------------------------------------------------------

# 1.21 renamed the vanilla attributes when it dropped the GENERIC_ prefix.
ATTRIBUTE_RENAMES = [
    ("MAX_HEALTH", "GENERIC_MAX_HEALTH"),
    ("FOLLOW_RANGE", "GENERIC_FOLLOW_RANGE"),
    ("KNOCKBACK_RESISTANCE", "GENERIC_KNOCKBACK_RESISTANCE"),
    ("MOVEMENT_SPEED", "GENERIC_MOVEMENT_SPEED"),
    ("FLYING_SPEED", "GENERIC_FLYING_SPEED"),
    ("ATTACK_DAMAGE", "GENERIC_ATTACK_DAMAGE"),
    ("ATTACK_KNOCKBACK", "GENERIC_ATTACK_KNOCKBACK"),
    ("ATTACK_SPEED", "GENERIC_ATTACK_SPEED"),
    ("ARMOR_TOUGHNESS", "GENERIC_ARMOR_TOUGHNESS"),
    ("ARMOR", "GENERIC_ARMOR"),
    ("JUMP_STRENGTH", "GENERIC_JUMP_STRENGTH"),
    ("STEP_HEIGHT", "GENERIC_STEP_HEIGHT"),
    ("MAX_ABSORPTION", "GENERIC_MAX_ABSORPTION"),
    ("GRAVITY", "GENERIC_GRAVITY"),
    ("LUCK", "GENERIC_LUCK"),
]

# These only exist from 1.20.5 on; EntityAttributesME registers them locally.
ATTRIBUTE_LOCAL = [
    "SCALE", "ENTITY_INTERACTION_RANGE", "BLOCK_INTERACTION_RANGE",
    "SAFE_FALL_DISTANCE", "SNEAKING_SPEED", "MINING_EFFICIENCY",
    "FALL_DAMAGE_MULTIPLIER", "WATER_MOVEMENT_EFFICIENCY",
    "MOVEMENT_EFFICIENCY", "BURNING_TIME",
]


def rewrite_attributes(text):
    stats = collections.Counter()
    for new, old in sorted(ATTRIBUTE_RENAMES, key=lambda pair: -len(pair[0])):
        needle = "EntityAttributes." + new
        n = text.count(needle)
        if n:
            text = text.replace(needle, "EntityAttributes." + old)
            stats["attribute-rename"] += n
    for name in ATTRIBUTE_LOCAL:
        needle = "EntityAttributes." + name
        n = text.count(needle)
        if n:
            text = text.replace(needle, "EntityAttributesME." + name)
            stats["attribute-local"] += n
    if stats["attribute-local"]:
        text = ensure_import(text, "net.jukoz.me.entity.EntityAttributesME")
    return text, stats


def _matching_paren(text, i):
    depth = 0
    while i < len(text):
        if text[i] == "(":
            depth += 1
        elif text[i] == ")":
            depth -= 1
            if depth == 0:
                return i
        i += 1
    return i


def _receiver_start(text, dot):
    """Start index of the expression ending at the '.' before a method call."""
    i = dot - 1
    # Skip newlines too: fluent chains are routinely wrapped.
    while i >= 0 and text[i].isspace():
        i -= 1
    while i >= 0:
        c = text[i]
        if c in ")]":
            close, open_ = (")", "(") if c == ")" else ("]", "[")
            depth = 0
            while i >= 0:
                if text[i] == close:
                    depth += 1
                elif text[i] == open_:
                    depth -= 1
                    if depth == 0:
                        i -= 1
                        break
                i -= 1
            continue
        if c.isalnum() or c in "_$.":
            i -= 1
            continue
        break
    return i + 1


def _rewrite_chain(text, method, on_get, on_present, on_if):
    """Rewrite 'recv.method(args).get()/isPresent()/ifPresent()' in one pass."""
    changed = 0
    needle = "." + method + "("
    pos = len(text)
    while True:
        pos = text.rfind(needle, 0, pos)
        if pos < 0:
            break
        start = _receiver_start(text, pos)
        close = _matching_paren(text, pos + len(needle) - 1)
        recv = text[start:pos]
        args = text[pos + len(needle):close]
        after = close + 1
        if text.startswith(".get()", after) and on_get:
            end, repl = after + 6, on_get(recv, args)
        elif text.startswith(".isPresent()", after) and on_present:
            end, repl = after + 12, on_present(recv, args)
        elif text.startswith(".ifPresent(", after) and on_if:
            inner = _matching_paren(text, after + 10)
            end, repl = inner + 1, on_if(recv, text[after + 11:inner])
        else:
            continue
        text = text[:start] + repl + text[end:]
        changed += 1
        pos = start
    return text, changed


def _split_args(text):
    parts, depth, cur = [], 0, ""
    for c in text:
        if c in "([{":
            depth += 1
        elif c in ")]}":
            depth -= 1
        if c == "," and depth == 0:
            parts.append(cur)
            cur = ""
            continue
        cur += c
    if cur.strip():
        parts.append(cur)
    return parts


def _rewrite_to_static(text, method, helper, arity=None):
    """Turn RECV.method(ARGS) into helper(RECV, ARGS)."""
    changed = 0
    needle = '.' + method + '('
    pos = len(text)
    while True:
        pos = text.rfind(needle, 0, pos)
        if pos < 0:
            break
        start = _receiver_start(text, pos)
        close = _matching_paren(text, pos + len(needle) - 1)
        recv = text[start:pos]
        args = text[pos + len(needle):close]
        if arity is not None and len(_split_args(args)) != arity:
            pos -= 1
            continue
        tail = ', ' + args if args.strip() else ''
        repl = '{}({}{})'.format(helper, recv, tail)
        text = text[:start] + repl + text[close + 1:]
        changed += 1
        pos = start
    return text, changed


def rewrite_text_calls(text):
    stats = collections.Counter()
    text, n = _rewrite_to_static(text, 'withColor', 'METext.withColor')
    if n:
        stats['text-withcolor'] = n
        text = ensure_import(text, 'net.jukoz.me.compat.METext')
    return text, stats


def rewrite_nbt_two_arg(text):
    """1.21.5 added getString(key, fallback); 1.20.1 only has getString(key)."""
    stats = collections.Counter()
    text, n = _rewrite_to_static(text, 'getString', 'NbtCompat.getString', arity=2)
    if n:
        stats['nbt-getstring'] = n
        text = ensure_import(text, 'net.jukoz.me.compat.NbtCompat')
    return text, stats


def rewrite_nbt(text):
    """1.21.5 made the type-checked NBT accessors return Optionals."""
    stats = collections.Counter()

    def compound(recv, args):
        return "NbtCompat.compound({})".format(recv)

    def list_(recv, args):
        return "NbtCompat.list({})".format(recv)

    text, n = _rewrite_chain(text, "asCompound", compound,
                             lambda r, a: "NbtCompat.isCompound({})".format(r),
                             lambda r, a: "NbtCompat.ifCompound({}, {})".format(r, a))
    stats["nbt-compound"] += n
    text, n = _rewrite_chain(text, "asNbtList", list_,
                             lambda r, a: "NbtCompat.isList({})".format(r),
                             lambda r, a: "NbtCompat.ifList({}, {})".format(r, a))
    stats["nbt-list"] += n

    # 1.20.1 returns the value directly, and "" instead of an empty Optional.
    for method in ("getCompound", "getList", "getString", "asString"):
        text, n = _rewrite_chain(
            text, method,
            lambda r, a, m=method: "{}.{}({})".format(r, m, a),
            (lambda r, a, m=method: "!{}.{}({}).isEmpty()".format(r, m, a))
            if method in ("getString", "asString") else None,
            None)
        stats["nbt-unwrap"] += n

    if stats["nbt-compound"] or stats["nbt-list"]:
        text = ensure_import(text, "net.jukoz.me.compat.NbtCompat")
    return text, stats


def cmd_import(args) -> int:
    donor = Path(args.donor)
    dest_root = (PROJECT / args.dest).resolve() / args.target_pkg
    total = collections.Counter()
    copied = 0
    skipped = 0

    for rel in args.paths:
        src = donor / args.donor_pkg / rel
        if not src.exists():
            print(f"skip (missing): {rel}", file=sys.stderr)
            continue
        files = [src] if src.is_file() else list(iter_java(src))
        for path in files:
            target_rel = path.relative_to(donor / args.donor_pkg)
            dst = dest_root / target_rel
            original = path.read_text(encoding="utf-8", errors="replace")
            text, stats = rewrite(original)
            # A file that already exists in the target tree is the 1.20.1 version of
            # that class and is always closer to correct than the 1.21.8 donor.
            # Overwriting it is never the right move: new 1.21 features get merged in
            # by hand, on top of working code.
            if dst.exists() and not args.force:
                skipped += 1
                continue
            if args.dry_run:
                changed = sum(1 for a, b in zip(original.splitlines(), text.splitlines()) if a != b)
                print(f"{str(target_rel):<70} {stats}")
                if changed:
                    for a, b in zip(original.splitlines(), text.splitlines()):
                        if a != b:
                            print(f"  - {a.strip()[:110]}")
                            print(f"  + {b.strip()[:110]}")
                            break
                copied += 1
                total.update(stats)
                continue
            dst.parent.mkdir(parents=True, exist_ok=True)
            dst.write_text(text, encoding="utf-8")
            copied += 1
            total.update(stats)

    print(f"{'would copy' if args.dry_run else 'copied'} {copied} files, skipped {skipped} existing")
    for k, v in sorted(total.items()):
        print(f"  {k}: {v}")
    return 0


def cmd_retouch(args) -> int:
    """Re-run the rewrite over an already-ported tree.

    Needed after the rewrite rules themselves change: the tree was produced by an
    older rule set and would otherwise keep the mistakes of that version.
    """
    root = (PROJECT / args.path).resolve()
    changed = 0
    totals = collections.Counter()
    for path in sorted(iter_java(root)):
        original = path.read_text(encoding="utf-8", errors="replace")
        text, stats = rewrite(original)
        if text != original:
            path.write_text(text, encoding="utf-8")
            changed += 1
            totals.update(stats)
    print("retouched {} of {} files".format(changed, len(list(iter_java(root)))))
    for k, v in sorted(totals.items()):
        print("  {}: {}".format(k, v))
    return 0


def cmd_promote(args) -> int:
    """Move already-rewritten files from the staging tree into the compiled tree."""
    staged = (PROJECT / "port-staging/java" / TARGET_PKG).resolve()
    dest_root = (PROJECT / "src/main/java" / TARGET_PKG).resolve()
    moved = collided = missing = 0
    for rel in args.paths:
        src = staged / rel
        if not src.exists():
            print(f"skip (not staged): {rel}", file=sys.stderr)
            missing += 1
            continue
        files = [src] if src.is_file() else sorted(iter_java(src))
        for path in files:
            target_rel = path.relative_to(staged)
            dst = dest_root / target_rel
            if dst.exists():
                # The target already holds the 1.20.1 version of this class; merging
                # the new 1.21 members in is a hand job, not a file copy.
                collided += 1
                continue
            dst.parent.mkdir(parents=True, exist_ok=True)
            dst.write_bytes(path.read_bytes())
            path.unlink()
            moved += 1
    print(f"promoted {moved} files, {collided} need manual merge, {missing} not staged")
    return 0


# javac localises its diagnostics; the default JDK locale on this machine is zh_CN.
# Match both spellings so the tool keeps working whichever locale wins.
# The path prefix is absolute on Windows ("H:\...\Foo.java"), so it contains a
# colon itself -- matching [^:] silently produced zero hits.
ERR = re.compile(r"^(?P<file>\S.*?[.]java):(?P<line>[0-9]+): (?:error|错误): (?P<msg>.*)$", re.M)
SYMBOL = re.compile(r"^\s*symbol:\s+(?P<kind>\S+)\s+(?P<name>.*)$", re.M)
LOCATION = re.compile(r"^\s*location:\s+(?P<loc>.*)$", re.M)


def parse_errors(blob):
    """Split javac output into one record per diagnostic, keeping the symbol detail.

    javac puts the useful part on the 'symbol:' line under the caret, not in the
    headline, so grouping on the headline alone lumps everything into
    'cannot find symbol' and hides the actual worklist.
    """
    starts = list(ERR.finditer(blob))
    out = []
    for i, m in enumerate(starts):
        end = starts[i + 1].start() if i + 1 < len(starts) else len(blob)
        block = blob[m.end():end]
        sym = SYMBOL.search(block)
        loc = LOCATION.search(block)
        out.append({
            "file": m.group("file"),
            "line": int(m.group("line")),
            "msg": m.group("msg").strip(),
            "symbol": ((sym.group("kind") + " " + sym.group("name")).strip() if sym else ""),
            "location": loc.group("loc").strip() if loc else "",
        })
    return out


def cmd_errors(args) -> int:
    # "gradlew.bat", not "./gradlew.bat": Windows resolves it through cmd.exe,
    # which does not accept a "./" prefix and fails with a 48-byte error message.
    gradlew = "gradlew.bat" if sys.platform == "win32" else "./gradlew"
    proc = subprocess.run([gradlew, "compileJava", "--console=plain"],
                          cwd=PROJECT, capture_output=True, text=True, errors="replace")
    blob = (proc.stdout or "") + (proc.stderr or "")
    found = parse_errors(blob)
    log = PROJECT / "build/port-errors.log"
    log.parent.mkdir(parents=True, exist_ok=True)
    log.write_text(blob, encoding="utf-8", errors="replace")

    print(f"{len(found)} errors in {len({m['file'] for m in found})} files\n")
    groups = collections.Counter(m["msg"] for m in found)
    for msg, n in groups.most_common(15):
        print(f"{n:>4}  {msg[:130]}")

    # Anything referring to a class that is still sitting in port-staging is not a
    # porting problem yet -- it is just a file that has not been promoted.
    # "Not ported yet" means the class exists in the donor but not in the compiled
    # tree -- derived from the donor rather than from port-staging, because staging
    # only holds what has not been promoted and would make the number drift.
    donor_root = Path(args.donor) / DONOR_PKG
    compiled_root = PROJECT / "src/main/java" / TARGET_PKG
    have = {f.stem for f in compiled_root.rglob("*.java")}
    pending = {f.stem for f in donor_root.rglob("*.java")} - have if donor_root.exists() else set()

    def is_pending(rec):
        # A missing method only counts as "not ported yet" if the class it was
        # looked up on is itself still sitting in staging -- otherwise it is a real
        # API drift that has to be fixed.
        for field in ("symbol", "location"):
            name = rec[field].split()[-1] if rec[field] else ""
            name = name.split("(")[0].split(".")[-1]
            if name in pending:
                return True
        return False

    real = [m for m in found if not is_pending(m)]
    print(f"\n{len(real)} errors are not explained by an un-promoted class "
          f"({len(found) - len(real)} are)")

    symbols = collections.Counter(
        (m["msg"], m["symbol"]) for m in real if m["symbol"])
    print("\n-- by symbol --")
    for (msg, sym), n in symbols.most_common(30):
        print(f"{n:>4}  {sym[:90]:<90} ({msg[:40]})")
    if args.files:
        per = collections.Counter(m["file"] for m in real)
        print("\n-- by file --")
        for f, n in per.most_common(args.files):
            print(f"{n:>5}  {f}")
    if args.json:
        Path(args.json).write_text(json.dumps(found, indent=2), encoding="utf-8")
        print(f"\nwrote {args.json}")
    return 0



# --------------------------------------------------------------------------
# component extraction (read-only)
# --------------------------------------------------------------------------

DECL = re.compile(
    r"(?m)^[ \t]*(?:public|private|protected)?[ \t]*static[ \t]+final[ \t]+"
    r"(?P<type>[A-Za-z_][\w.<>\[\], ]*?)[ \t]+(?P<name>[A-Z_][A-Z0-9_]*)[ \t]*=[ \t]*")
COMPONENT_CALL = re.compile(r"\.component\(")


def _skip_string(text: str, i: int) -> int:
    quote = text[i]
    i += 1
    while i < len(text):
        if text[i] == "\\":
            i += 2
            continue
        if text[i] == quote:
            return i + 1
        i += 1
    return i


def blank_comments(text: str) -> str:
    """Replace comments with spaces, preserving every offset and newline."""
    out = list(text)
    i, n = 0, len(text)
    while i < n:
        if text.startswith("//", i):
            j = text.find("\n", i)
            j = n if j < 0 else j
            for k in range(i, j):
                out[k] = " "
            i = j
        elif text.startswith("/*", i):
            j = text.find("*/", i + 2)
            j = n if j < 0 else j + 2
            for k in range(i, j):
                if out[k] != "\n":
                    out[k] = " "
            i = j
        elif text[i] in "\"\'":
            i = _skip_string(text, i)
        else:
            i += 1
    return "".join(out)


def _statement_end(text: str, pos: int) -> int:
    depth = 0
    i, n = pos, len(text)
    while i < n:
        c = text[i]
        if c in "\"\'":
            i = _skip_string(text, i)
            continue
        if c in "([{":
            depth += 1
        elif c in ")]}":
            depth -= 1
        elif c == ";" and depth == 0:
            return i
        i += 1
    return n


def _split_top_level(text: str):
    parts, depth, start = [], 0, 0
    for i, c in enumerate(text):
        if c in "([{":
            depth += 1
        elif c in ")]}":
            depth -= 1
        elif c == "," and depth == 0:
            parts.append(text[start:i])
            start = i + 1
    parts.append(text[start:])
    return [p.strip() for p in parts]


def cmd_components(args) -> int:
    donor = Path(args.donor)
    rows, unresolved = [], []
    seen = collections.Counter()

    for path in iter_java(donor):
        text = blank_comments(path.read_text(encoding="utf-8", errors="replace"))
        rel = path.relative_to(donor).as_posix()
        covered = set()

        for decl in DECL.finditer(text):
            name = decl.group("name")
            start = decl.end()
            end = _statement_end(text, start)
            statement = text[start:end]
            for call in COMPONENT_CALL.finditer(statement):
                open_idx = call.end()
                close_idx = _statement_end_arg(statement, open_idx)
                inner = statement[open_idx:close_idx]
                args2 = _split_top_level(inner)
                if len(args2) < 2:
                    continue
                key_raw, value = args2[0], " ".join(args2[1].split())
                if key_raw.startswith("DataComponentTypesME."):
                    family = "me"
                elif key_raw.startswith("DataComponentTypes."):
                    family = "vanilla"
                else:
                    family = "other"
                key = key_raw.split(".")[-1]
                line = text.count("\n", 0, start + call.start()) + 1
                covered.add(start + call.start())
                rows.append({"file": rel, "line": line, "item": name,
                             "family": family, "key": key, "value": value})
                seen[(name, key)] += 1

        for call in COMPONENT_CALL.finditer(text):
            if call.start() not in covered:
                line = text.count("\n", 0, call.start()) + 1
                unresolved.append({"file": rel, "line": line})

    me_rows = [r for r in rows if r["family"] == "me"]
    vanilla_rows = [r for r in rows if r["family"] == "vanilla"]
    other_rows = [r for r in rows if r["family"] == "other"]
    dupes = {k: v for k, v in seen.items() if v > 1}

    print(f"items with components : {len({r['item'] for r in rows})}")
    print(f"component calls       : {len(rows)}  (me={len(me_rows)}, vanilla={len(vanilla_rows)}, other={len(other_rows)})")
    print(f"outside a declaration : {len(unresolved)}")
    print(f"duplicate item+key    : {len(dupes)}")
    print()
    print("| item | key | value |")
    print("|---|---|---|")
    for r in rows[:args.limit]:
        value = r["value"] if len(r["value"]) <= 70 else r["value"][:67] + "..."
        print(f"| {r['item']} | {r['family']}:{r['key']} | {value} |")
    if len(rows) > args.limit:
        print(f"| ... | ({len(rows) - args.limit} more) | |")

    if unresolved:
        print("\nunresolved:")
        for u in unresolved[:20]:
            print(f"  {u['file']}:{u['line']}")
    if dupes:
        print("\nduplicates:")
        for (name, key), n in list(dupes.items())[:20]:
            print(f"  {name}.{key} x{n}")

    if args.json:
        Path(args.json).write_text(json.dumps(rows, indent=2), encoding="utf-8")
        print(f"\nwrote {args.json}")
    return 0


def _statement_end_arg(text: str, pos: int) -> int:
    """Index of the ')' closing an argument list; text[pos] is just past the '('."""
    depth = 1
    i = pos
    while i < len(text) and depth:
        c = text[i]
        if c in "\"\'":
            i = _skip_string(text, i)
            continue
        if c == "(":
            depth += 1
        elif c == ")":
            depth -= 1
            if depth == 0:
                return i
        i += 1
    return i


def main() -> int:
    ap = argparse.ArgumentParser(description="Middle-earth porting helper")
    ap.add_argument("--donor", default=str(DONOR_ROOT))
    sub = ap.add_subparsers(dest="cmd", required=True)

    p = sub.add_parser("inventory")
    p.add_argument("--out", default="build/port-report.md")
    p.set_defaults(func=cmd_inventory)

    p = sub.add_parser("import")
    p.add_argument("paths", nargs="+", help="paths relative to the donor package")
    p.add_argument("--dry-run", action="store_true", help="report only, write nothing")
    p.add_argument("--force", action="store_true",
                   help="overwrite files that already exist in the target tree")
    p.add_argument("--donor-pkg", default=DONOR_PKG,
                   help="package root inside the donor source tree")
    p.add_argument("--target-pkg", default=TARGET_PKG,
                   help="package root inside the destination source tree")
    p.add_argument("--dest", default="src/main/java",
                   help="destination source root, relative to the project")
    p.set_defaults(func=cmd_import)

    p = sub.add_parser("retouch", help="re-apply the rewrite rules to an existing tree")
    p.add_argument("path", nargs="?", default="src/main/java")
    p.set_defaults(func=cmd_retouch)

    p = sub.add_parser("promote", help="move staged files into src/main/java")
    p.add_argument("paths", nargs="+", help="paths relative to the package root")
    p.set_defaults(func=cmd_promote)

    p = sub.add_parser("errors")
    p.add_argument("--json")
    p.add_argument("--files", type=int, default=0)
    p.set_defaults(func=cmd_errors)

    p = sub.add_parser("components", help="read-only: list Item.Settings.component calls")
    p.add_argument("--limit", type=int, default=40)
    p.add_argument("--json")
    p.set_defaults(func=cmd_components)

    args = ap.parse_args()
    return args.func(args)


if __name__ == "__main__":
    raise SystemExit(main())
