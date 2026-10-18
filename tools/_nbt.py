
import pathlib, re

def receiver_start(text, dot):
    """Walk backwards from the '.' of '.asCompound()' to the start of the receiver."""
    i = dot - 1
    while i >= 0 and text[i] in " \t":
        i -= 1
    while i >= 0:
        c = text[i]
        if c in ")]":
            close = c
            open_ = "(" if c == ")" else "["
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

def rewrite(text, method, helper, check, action):
    out = text
    needle = "." + method + "()"
    pos = len(out)
    changed = 0
    while True:
        pos = out.rfind(needle, 0, pos)
        if pos < 0:
            break
        start = receiver_start(out, pos)
        recv = out[start:pos]
        after = pos + len(needle)
        if out.startswith(".get()", after):
            end, repl = after + 6, helper + "(" + recv + ")"
        elif out.startswith(".isPresent()", after):
            end, repl = after + 12, check + "(" + recv + ")"
        elif out.startswith(".ifPresent(", after):
            close = matching_paren(out, after + 10)
            end = close + 1
            repl = action + "(" + recv + ", " + out[after + 11:close] + ")"
        else:
            end, repl = after, helper + "(" + recv + ")"
        out = out[:start] + repl + out[end:]
        changed += 1
    return out, changed

def matching_paren(text, i):
    depth = 0
    while i < len(text):
        c = text[i]
        if c == "(":
            depth += 1
        elif c == ")":
            depth -= 1
            if depth == 0:
                return i
        i += 1
    return i

total = 0
for root in ("port-staging/java", "src/main/java"):
    for f in pathlib.Path(root).rglob("*.java"):
        text = orig = f.read_text(encoding="utf-8", errors="replace")
        text, a = rewrite(text, "asCompound", "NbtCompat.compound", "NbtCompat.isCompound", "NbtCompat.ifCompound")
        text, b = rewrite(text, "asNbtList", "NbtCompat.list", "NbtCompat.isList", "NbtCompat.ifList")
        if text != orig:
            if "import net.jukoz.me.compat.NbtCompat;" not in text:
                text = text.replace("\nimport ", "\nimport net.jukoz.me.compat.NbtCompat;\nimport ", 1)
            f.write_text(text, encoding="utf-8")
            total += 1
            print(f"{f.name}: {a} compound, {b} list")
print("files touched:", total)
