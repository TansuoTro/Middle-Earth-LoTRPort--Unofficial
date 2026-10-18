
import pathlib

p = pathlib.Path("tools/port.py")
t = p.read_text(encoding="utf-8")

BLOCK = '''

# --------------------------------------------------------------------------
# 1.20.5 / 1.21 API drift
# --------------------------------------------------------------------------

# 1.21 renamed the vanilla attributes when it dropped the GENERIC_ prefix.
ATTRIBUTE_RENAMES = [
    ("MAX_HEALTH", "GENERIC_MAX_HEALTH"),
    ("FOLLOW_RANGE", "GENERIC_FOLLOW_RANGE"),
    ("KNOCKBACK_RESISTANCE", "GENERIC_KNOCKBACK_RESISTANCE"),
    ("MOVEMENT_SPEED", "GENERIC_MOVEMENT_SPEED"),
    ("FLYING_SPEED", "GENERIC_FLYING_SPEED"),
    ("ATTACK_DAMAGE", "GENERIC_ATTACK_DAMAGE"),
    ("ATTACK_KNOCKBACK", "GENERIC_ATTACK_KNOCKBACK"),
    ("ATTACK_SPEED", "GENERIC_ATTACK_SPEED"),
    ("ARMOR_TOUGHNESS", "GENERIC_ARMOR_TOUGHNESS"),
    ("ARMOR", "GENERIC_ARMOR"),
    ("JUMP_STRENGTH", "GENERIC_JUMP_STRENGTH"),
    ("STEP_HEIGHT", "GENERIC_STEP_HEIGHT"),
    ("MAX_ABSORPTION", "GENERIC_MAX_ABSORPTION"),
    ("GRAVITY", "GENERIC_GRAVITY"),
    ("LUCK", "GENERIC_LUCK"),
]

# These only exist from 1.20.5 on; EntityAttributesME registers them locally.
ATTRIBUTE_LOCAL = [
    "SCALE", "ENTITY_INTERACTION_RANGE", "BLOCK_INTERACTION_RANGE",
    "SAFE_FALL_DISTANCE", "SNEAKING_SPEED", "MINING_EFFICIENCY",
    "FALL_DAMAGE_MULTIPLIER", "WATER_MOVEMENT_EFFICIENCY",
    "MOVEMENT_EFFICIENCY", "BURNING_TIME",
]


def rewrite_attributes(text):
    stats = collections.Counter()
    for new, old in sorted(ATTRIBUTE_RENAMES, key=lambda pair: -len(pair[0])):
        needle = "EntityAttributes." + new
        n = text.count(needle)
        if n:
            text = text.replace(needle, "EntityAttributes." + old)
            stats["attribute-rename"] += n
    for name in ATTRIBUTE_LOCAL:
        needle = "EntityAttributes." + name
        n = text.count(needle)
        if n:
            text = text.replace(needle, "EntityAttributesME." + name)
            stats["attribute-local"] += n
    if stats["attribute-local"]:
        text = ensure_import(text, "net.jukoz.me.entity.EntityAttributesME")
    return text, stats


def _matching_paren(text, i):
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


def _receiver_start(text, dot):
    """Start index of the expression ending at the '.' before a method call."""
    i = dot - 1
    while i >= 0 and text[i] in " \\t":
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


def _rewrite_chain(text, method, on_get, on_present, on_if):
    """Rewrite 'recv.method(args).get()/isPresent()/ifPresent()' in one pass."""
    changed = 0
    needle = "." + method + "("
    pos = len(text)
    while True:
        pos = text.rfind(needle, 0, pos)
        if pos < 0:
            break
        start = _receiver_start(text, pos)
        close = _matching_paren(text, pos + len(needle) - 1)
        recv = text[start:pos]
        args = text[pos + len(needle):close]
        after = close + 1
        if text.startswith(".get()", after) and on_get:
            end, repl = after + 6, on_get(recv, args)
        elif text.startswith(".isPresent()", after) and on_present:
            end, repl = after + 12, on_present(recv, args)
        elif text.startswith(".ifPresent(", after) and on_if:
            inner = _matching_paren(text, after + 10)
            end, repl = inner + 1, on_if(recv, text[after + 11:inner])
        else:
            continue
        text = text[:start] + repl + text[end:]
        changed += 1
        pos = start
    return text, changed


def rewrite_nbt(text):
    """1.21.5 made the type-checked NBT accessors return Optionals."""
    stats = collections.Counter()

    def compound(recv, args):
        return "NbtCompat.compound({})".format(recv)

    def list_(recv, args):
        return "NbtCompat.list({})".format(recv)

    text, n = _rewrite_chain(text, "asCompound", compound,
                             lambda r, a: "NbtCompat.isCompound({})".format(r),
                             lambda r, a: "NbtCompat.ifCompound({}, {})".format(r, a))
    stats["nbt-compound"] += n
    text, n = _rewrite_chain(text, "asNbtList", list_,
                             lambda r, a: "NbtCompat.isList({})".format(r),
                             lambda r, a: "NbtCompat.ifList({}, {})".format(r, a))
    stats["nbt-list"] += n

    # 1.20.1 returns the value directly, and "" instead of an empty Optional.
    for method in ("getCompound", "getList", "getString", "asString"):
        text, n = _rewrite_chain(
            text, method,
            lambda r, a, m=method: "{}.{}({})".format(r, m, a),
            (lambda r, a, m=method: "!{}.{}({}).isEmpty()".format(r, m, a))
            if method in ("getString", "asString") else None,
            None)
        stats["nbt-unwrap"] += n

    if stats["nbt-compound"] or stats["nbt-list"]:
        text = ensure_import(text, "net.jukoz.me.compat.NbtCompat")
    return text, stats
'''

anchor = "def cmd_import(args) -> int:"
t = t.replace(anchor, BLOCK.lstrip("\n") + "\n\n" + anchor, 1)

# hook into rewrite()
t = t.replace('''    text, n = rewrite_components(text)
    if n:
        stats["components"] = n
''', '''    text, n = rewrite_components(text)
    if n:
        stats["components"] = n

    text, extra = rewrite_attributes(text)
    stats.update(extra)
    text, extra = rewrite_nbt(text)
    stats.update(extra)
''')
p.write_text(t, encoding="utf-8")
print("patched")
