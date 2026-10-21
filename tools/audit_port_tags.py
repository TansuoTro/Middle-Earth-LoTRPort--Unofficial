"""Check packed tag references, including required nested tags and registry IDs.

Use --registry build/structure-registry.json after the Rosetta registry export
to check item, block, entity and banner IDs against the running game.
"""
import argparse
import json
from pathlib import Path
import zipfile

PROJECT = Path(__file__).resolve().parent.parent


def key_for(path):
    parts = path.split("/")
    namespace, kind = parts[1], parts[3]
    start = 4
    if kind == "worldgen":
        kind += "/" + parts[4]
        start = 5
    return kind, namespace + ":" + "/".join(parts[start:]).removesuffix(".json")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--registry", type=Path)
    parser.add_argument("--output", type=Path, default=PROJECT / "build/tag-reference-audit.json")
    args = parser.parse_args()
    jar = next((PROJECT / ".gradle/loom-cache/minecraftMaven").rglob("*.jar"))
    vanilla = {}
    biome_ids = set()
    with zipfile.ZipFile(jar) as source:
        for name in source.namelist():
            if name.startswith("data/") and "/tags/" in name and name.endswith(".json"):
                vanilla[key_for(name)] = json.loads(source.read(name))
            if name.startswith("data/minecraft/worldgen/biome/") and name.endswith(".json"):
                biome_ids.add("minecraft:" + Path(name).stem)
    # processResources uses EXCLUDE with handwritten resources first. Its tag
    # files replace duplicate generated files before Minecraft merges packs.
    mod = {}
    origins = {}
    for base in (PROJECT / "src/main/generated", PROJECT / "src/main/resources"):
        for path in (base / "data").glob("*/tags/**/*.json"):
            key = key_for(path.relative_to(base).as_posix())
            mod[key] = json.loads(path.read_text(encoding="utf-8"))
            origins[key] = str(path.relative_to(PROJECT))
        for path in (base / "data").glob("*/worldgen/biome/*.json"):
            biome_ids.add(path.parts[-4] + ":" + path.stem)
    tags = dict(vanilla)
    for key, data in mod.items():
        previous = [] if data.get("replace", False) else tags.get(key, {}).get("values", [])
        tags[key] = {"values": previous + data["values"]}
    registry = {"worldgen/biome": biome_ids}
    if args.registry:
        snapshot = json.loads(args.registry.read_text(encoding="utf-8"))
        for kind, name in {"items": "items", "blocks": "blocks", "entity_types": "entities",
                           "banner_pattern": "banner_patterns"}.items():
            registry[kind] = set(snapshot[name])
    failures = []
    checked_ids = checked_refs = 0
    edges = {}
    for key, data in tags.items():
        edges[key] = []
        for entry in data["values"]:
            required = not isinstance(entry, dict) or entry.get("required", True)
            value = entry if isinstance(entry, str) else entry["id"]
            if not required:
                continue
            if value.startswith("#"):
                target = key[0], value[1:] if ":" in value else "minecraft:" + value[1:]
                edges[key].append(target)
                if key in mod:
                    checked_refs += 1
                    if target not in tags:
                        failures.append({"file": origins[key], "missing_tag": value})
            elif key in mod and key[0] in registry:
                checked_ids += 1
                if value not in registry[key[0]]:
                    failures.append({"file": origins[key], "missing_id": value})
    complete = set()
    def visit(key, ancestors):
        if key in ancestors:
            failures.append({"tag_cycle": [name for _, name in ancestors + [key]]})
            return
        if key in complete:
            return
        for target in edges.get(key, []):
            visit(target, ancestors + [key])
        complete.add(key)
    for key in mod:
        visit(key, [])
    report = {"mod_tag_files": len(mod), "checked_required_tag_refs": checked_refs,
              "checked_registry_ids": checked_ids, "live_registry_snapshot": bool(args.registry),
              "failures": failures}
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(json.dumps(report, indent=2))
    return bool(failures)


if __name__ == "__main__":
    raise SystemExit(main())
