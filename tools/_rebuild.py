
import pathlib, subprocess

root = pathlib.Path("src/main/java")
baseline = set(subprocess.run(["git", "ls-tree", "-r", "--name-only", "de34d13", "--", "src/main/java"],
                              capture_output=True, text=True).stdout.split())
current = {str(p.relative_to(".")).replace("\\", "/") for p in root.rglob("*.java")}

HAND = {
    "src/main/java/net/jukoz/me/compat/NbtCompat.java",
    "src/main/java/net/jukoz/me/entity/EntityAttributesME.java",
    "src/main/java/net/jukoz/me/item/MEComponents.java",
    "src/main/java/net/jukoz/me/item/MEComponent.java",
    "src/main/java/net/jukoz/me/item/MESettings.java",
    "src/main/java/net/jukoz/me/mixin/ItemMixin.java",
}
donor_derived = sorted(current - baseline - HAND)
donor_derived = [p for p in donor_derived if "/item/dataComponents/" not in p]
print("deleting", len(donor_derived), "donor-derived files")
for p in donor_derived:
    pathlib.Path(p).unlink()
pathlib.Path("build/donor_derived.txt").write_text("\n".join(donor_derived), encoding="utf-8")
