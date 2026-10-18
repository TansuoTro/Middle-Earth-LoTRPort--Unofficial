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


REWRITES = [
    ("package-api", "net.sevenstars.api", "net.jukoz.me.api"),
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
ERR = re.compile(r"^(?P<file>.+?[.]java):(?P<line>[0-9]+): (?:error|错误): (?P<msg>.*)$", re.M)
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

    p = sub.add_parser("promote", help="move staged files into src/main/java")
    p.add_argument("paths", nargs="+", help="paths relative to the package root")
    p.set_defaults(func=cmd_promote)

    p = sub.add_parser("errors")
    p.add_argument("--json")
    p.set_defaults(func=cmd_errors)

    p = sub.add_parser("components", help="read-only: list Item.Settings.component calls")
    p.add_argument("--limit", type=int, default=40)
    p.add_argument("--json")
    p.set_defaults(func=cmd_components)

    args = ap.parse_args()
    return args.func(args)


if __name__ == "__main__":
    raise SystemExit(main())
