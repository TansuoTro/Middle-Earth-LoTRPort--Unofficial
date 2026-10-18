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
    out, i, n = [], 0, 0
    while True:
        j = text.find(prefix, i)
        if j < 0:
            out.append(text[i:])
            break
        open_idx = text.rfind("(", 0, j)
        k, depth = j, 1
        while k < len(text) and depth:
            if text[k] == "(":
                depth += 1
            elif text[k] == ")":
                depth -= 1
            k += 1
        out.append(text[i:open_idx])
        out.append(text[j:k - 1])
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


REWRITES = [
    ("package", "net.sevenstars.middleearth", "net.jukoz.me"),
    ("identifier", "Identifier.of(", "new Identifier("),
    ("registrykey-type-block", "RegistryKey<Block>", "Identifier"),
    ("registrykey-type-item", "RegistryKey<Item>", "Identifier"),
]

# 1.21 wraps block/item ids in a RegistryKey; 1.20.1 registers by Identifier.
UNWRAPS = [
    ("registrykey-block", "RegistryKey.of(RegistryKeys.BLOCK, "),
    ("registrykey-item", "RegistryKey.of(RegistryKeys.ITEM, "),
]


def rewrite(text: str):
    stats = collections.Counter()
    for name, old, new in REWRITES:
        n = text.count(old)
        if n:
            text = text.replace(old, new)
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
    return text, dict(stats)


def cmd_import(args) -> int:
    donor = Path(args.donor)
    dest_root = PROJECT / "src/main/java" / TARGET_PKG
    total = collections.Counter()
    copied = 0

    for rel in args.paths:
        src = donor / DONOR_PKG / rel
        if not src.exists():
            print(f"skip (missing): {rel}", file=sys.stderr)
            continue
        files = [src] if src.is_file() else list(iter_java(src))
        for path in files:
            target_rel = path.relative_to(donor / DONOR_PKG)
            dst = dest_root / target_rel
            original = path.read_text(encoding="utf-8", errors="replace")
            text, stats = rewrite(original)
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

    print(f"{'would copy' if args.dry_run else 'copied'} {copied} files")
    for k, v in sorted(total.items()):
        print(f"  {k}: {v}")
    return 0


ERR = re.compile(r"^(?P<file>[^:\n]+\.java):(?P<line>\d+): error: (?P<msg>.*)$", re.M)


def cmd_errors(args) -> int:
    gradlew = "./gradlew.bat" if sys.platform == "win32" else "./gradlew"
    proc = subprocess.run([gradlew, "compileJava", "--console=plain", "-q"],
                          cwd=PROJECT, capture_output=True, text=True, errors="replace")
    blob = (proc.stdout or "") + (proc.stderr or "")
    found = [m.groupdict() for m in ERR.finditer(blob)]
    groups = collections.Counter(m["msg"] for m in found)

    print(f"{len(found)} errors in {len({m['file'] for m in found})} files\n")
    for msg, n in groups.most_common(25):
        print(f"{n:>4}  {msg[:150]}")
    if args.json:
        Path(args.json).write_text(json.dumps(found, indent=2), encoding="utf-8")
        print(f"\nwrote {args.json}")
    return 0


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
    p.set_defaults(func=cmd_import)

    p = sub.add_parser("errors")
    p.add_argument("--json")
    p.set_defaults(func=cmd_errors)

    args = ap.parse_args()
    return args.func(args)


if __name__ == "__main__":
    raise SystemExit(main())
