
import pathlib, subprocess
baseline = set(subprocess.run(["git", "ls-tree", "-r", "--name-only", "de34d13", "--", "src/main/java"],
                              capture_output=True, text=True).stdout.split())
root = pathlib.Path("src/main/java/net/jukoz/me/datageneration")
staging = pathlib.Path("port-staging/java/net/jukoz/me/datageneration")
moved = 0
for f in sorted(root.rglob("*.java")):
    rel = str(f.relative_to(".")).replace("\\", "/")
    if rel in baseline:
        continue
    dst = staging / f.relative_to(root)
    dst.parent.mkdir(parents=True, exist_ok=True)
    dst.write_bytes(f.read_bytes())
    f.unlink()
    moved += 1
print("deferred", moved, "new datagen files")
