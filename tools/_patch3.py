
import pathlib
p = pathlib.Path("src/main/java/net/jukoz/me/entity/EntityAttributesME.java")
t = p.read_text(encoding="utf-8")
t = t.replace("import net.minecraft.registry.Registries;", "import net.minecraft.registry.Registries;\nimport net.minecraft.registry.Registry;")
p.write_text(t, encoding="utf-8")

q = pathlib.Path("tools/port.py")
s = q.read_text(encoding="utf-8")
s = s.replace('''    text, n = rewrite_components(text)
    if n:
        stats["components"] = n''', '''    text, n = rewrite_components(text)
    if n:
        stats["components"] = n
        # rewrite_components emits MEComponents.* call sites, which need the import
        # just as much as the key renames below do.
        text = ensure_import(text, "net.jukoz.me.item.MEComponents")''')
q.write_text(s, encoding="utf-8")
print("patched")
