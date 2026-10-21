"""Merge the donor's compatible tags into the 1.20.1 resource pack.

Uses committed git objects, so sparse checkouts are supported. Existing values
are retained. Run from any directory: python tools/import_port_tags.py DONOR.
"""
import argparse
import io
import json
from pathlib import Path
import subprocess
import tarfile

PROJECT = Path(__file__).resolve().parent.parent
DATA = PROJECT / "src/main/resources/data"
PREFIX = "middle-earth/src/main/resources/data/"
KINDS = {"item": "items", "items": "items", "block": "blocks",
         "entity_type": "entity_types", "banner_pattern": "banner_pattern",
         "painting_variant": "painting_variant", "worldgen": "worldgen"}
# Wild Things is split out in the donor; these animals remain in this port.
IDS = {"wild-things:raw_swan": "me:raw_swan",
       "wild-things:cooked_swan": "me:cooked_swan",
       "wild-things:cooked_poultry": "me:cooked_duck",
       "#minecraft:enchantable/sharp_weapon": "#me:swords_axes"}


def mapped(value):
    if isinstance(value, str):
        return IDS.get(value, value.replace("middle-earth:", "me:"))
    if isinstance(value, list):
        return [mapped(entry) for entry in value if entry != "minecraft:mace"]
    if isinstance(value, dict):
        return {key: mapped(entry) for key, entry in value.items()}
    return value


def merge(path, data):
    old = json.loads(path.read_text(encoding="utf-8")) if path.exists() else {"values": []}
    values = old["values"][:]
    for entry in data["values"]:
        if entry not in values:
            values.append(entry)
    result = {"replace": old.get("replace", False), "values": values}
    if path.exists() and old == result:
        return False
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(result, indent=2) + "\n", encoding="utf-8")
    return True


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("donor", type=Path)
    parser.add_argument("--ref", default="fe655f71374db31629c228e1435e64636698b56c")
    args = parser.parse_args()
    archive = subprocess.check_output(["git", "archive", args.ref, PREFIX], cwd=args.donor)
    added = updated = 0
    with tarfile.open(fileobj=io.BytesIO(archive)) as source:
        for entry in source:
            parts = entry.name.removeprefix(PREFIX).split("/")
            if not entry.isfile() or len(parts) < 4 or parts[1] != "tags" or parts[2] not in KINDS:
                continue
            parts[0] = "me" if parts[0] == "middle-earth" else parts[0]
            parts[2] = KINDS[parts[2]]
            path = DATA.joinpath(*parts)
            existed = path.exists()
            data = mapped(json.load(source.extractfile(entry)))
            if merge(path, data):
                updated += existed
                added += not existed
    print(f"Added {added} tags; merged {updated} existing tags without dropping values.")


if __name__ == "__main__":
    main()
