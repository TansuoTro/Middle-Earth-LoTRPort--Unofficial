
import pathlib
p = pathlib.Path("tools/port.py")
t = p.read_text(encoding="utf-8")
start = t.index("def unwrap_call(")
end = t.index("COMP_CALL = re.compile(")
new = '''def unwrap_call(text: str, prefix: str):
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


'''
p.write_text(t[:start] + new + t[end:], encoding="utf-8")

import importlib.util
spec = importlib.util.spec_from_file_location("port", "tools/port.py")
m = importlib.util.module_from_spec(spec); spec.loader.exec_module(m)
for f, lo, hi in [("block/registration/ModBlocks.java", 818, 833),
                  ("compat/REICommonPluginME.java", 20, 24)]:
    src = open(r"H:/MinecraftMods/_donor/middle-earth/src/main/java/net/sevenstars/middleearth/" + f, encoding="utf-8").read()
    out, _ = m.rewrite(src)
    print("===", f)
    for i, line in enumerate(out.splitlines(), 1):
        if lo <= i <= hi:
            print(" ", i, line)
