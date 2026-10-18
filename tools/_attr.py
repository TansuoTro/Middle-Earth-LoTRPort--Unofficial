
import pathlib, re, sys

RENAMES = [
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
LOCAL = [
    "SCALE", "ENTITY_INTERACTION_RANGE", "BLOCK_INTERACTION_RANGE",
    "SAFE_FALL_DISTANCE", "SNEAKING_SPEED", "MINING_EFFICIENCY",
    "FALL_DAMAGE_MULTIPLIER", "WATER_MOVEMENT_EFFICIENCY",
    "MOVEMENT_EFFICIENCY", "BURNING_TIME",
]

total = 0
for root in ("port-staging/java", "src/main/java"):
    for f in pathlib.Path(root).rglob("*.java"):
        text = orig = f.read_text(encoding="utf-8", errors="replace")
        # longest first so GENERIC_ is not produced twice and MOVEMENT_EFFICIENCY
        # is not eaten by a shorter prefix match
        for new, old in sorted(RENAMES, key=lambda p: -len(p[0])):
            text = text.replace("EntityAttributes." + new, "EntityAttributes." + old)
        for name in LOCAL:
            text = text.replace("EntityAttributes." + name, "EntityAttributesME." + name)
        if text != orig:
            if "EntityAttributesME." in text and "import net.jukoz.me.entity.EntityAttributesME;" not in text \
                    and not text.startswith("package net.jukoz.me.entity;"):
                text = text.replace("\nimport ", "\nimport net.jukoz.me.entity.EntityAttributesME;\nimport ", 1)
            f.write_text(text, encoding="utf-8")
            total += 1
print("files touched:", total)
