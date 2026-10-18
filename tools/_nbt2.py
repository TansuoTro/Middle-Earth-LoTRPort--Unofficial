
import pathlib

def matching_paren(text, i):
    depth = 0
    while i < len(text):
        if text[i] == "(":
            depth += 1
        elif text[i] == ")":
            depth -= 1
            if depth == 0:
                return i
        i += 1
    return i

def receiver_start(text, dot):
    i = dot - 1
    while i >= 0 and text[i] in " \t":
        i -= 1
    while i >= 0:
        c = text[i]
        if c in ")]":
            close, open_ = (")", "(") if c == ")" else ("]", "[")
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

# 1.21 returns Optional<T> from these; 1.20.1 returns T directly.
# asString()/getString() return "" instead of an empty Optional.
CASES = [
    ("asString",   "{}[X].asString()",              "!{}[X].asString().isEmpty()"),
    ("getString",  "{}[X].getString({})",           "!{}[X].getString({}).isEmpty()"),
    ("getCompound","{}[X].getCompound({})",         None),
    ("getList",    "{}[X].getList({})",             None),
]

def fix(text):
    changed = 0
    for method, get_fmt, present_fmt in CASES:
        needle = "." + method + "("
        pos = len(text)
        while True:
            pos = text.rfind(needle, 0, pos)
            if pos < 0:
                break
            start = receiver_start(text, pos)
            close = matching_paren(text, pos + len(needle) - 1)
            args = text[pos + len(needle):close]
            recv = text[start:pos]
            after = close + 1
            if text.startswith(".get()", after):
                end = after + 6
                repl = get_fmt.replace("[X]", "").format(recv, args)
            elif text.startswith(".isPresent()", after) and present_fmt:
                end = after + 12
                repl = present_fmt.replace("[X]", "").format(recv, args)
            else:
                continue
            text = text[:start] + repl + text[end:]
            changed += 1
            pos = start
    return text, changed

total = 0
for root in ("port-staging/java", "src/main/java"):
    for f in pathlib.Path(root).rglob("*.java"):
        text = orig = f.read_text(encoding="utf-8", errors="replace")
        text, n = fix(text)
        # 1.21 exposes the cauldron behaviour map through a holder; 1.20.1 has the map itself.
        text = text.replace("WATER_CAULDRON_BEHAVIOR.map()", "WATER_CAULDRON_BEHAVIOR")
        if text != orig:
            f.write_text(text, encoding="utf-8")
            total += 1
            print(f.name, n)
print("files touched:", total)
