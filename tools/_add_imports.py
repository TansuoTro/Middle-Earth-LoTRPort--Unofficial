
import pathlib, re
root = pathlib.Path("src/main/java/net/jukoz/me")
added = 0
for f in root.rglob("*.java"):
    t = f.read_text(encoding="utf-8", errors="replace")
    if "MEComponents." not in t:
        continue
    if "import net.jukoz.me.item.MEComponents;" in t:
        continue
    if f.parent == root / "item":
        continue
    m = re.search(r"^package\s+[\w.]+\s*;\s*\n", t, re.M)
    if not m:
        continue
    t = t[:m.end()] + "import net.jukoz.me.item.MEComponents;\n" + t[m.end():]
    f.write_text(t, encoding="utf-8")
    added += 1
print("imports added:", added)
