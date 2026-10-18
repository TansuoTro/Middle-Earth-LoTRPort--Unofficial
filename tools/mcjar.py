"""Ask the mapped 1.20.1 Minecraft jar what actually exists.

The port hinges on questions like 'does NoteBlockInstrument exist in 1.20.1' or
'what are the constants of Instrument'. Guessing from memory is how a rename gets
applied to a class that never existed. This reads the Loom-mapped jar directly.
"""

import argparse
import pathlib
import subprocess
import sys
import zipfile

PROJECT = pathlib.Path(__file__).resolve().parent.parent


def find_jar() -> pathlib.Path:
    jars = sorted((PROJECT / '.gradle/loom-cache/minecraftMaven').rglob('*.jar'))
    if not jars:
        raise SystemExit('no mapped minecraft jar in .gradle/loom-cache/minecraftMaven')
    return jars[0]


def cmd_has(jar, args) -> int:
    needle = args.name.lower()
    with zipfile.ZipFile(jar) as zf:
        hits = [n for n in zf.namelist()
                if n.endswith('.class') and needle in n.lower()]
    for h in hits:
        print(h[:-6].replace('/', '.'))
    if not hits:
        print('(not present)', file=sys.stderr)
    return 0 if hits else 1


def cmd_members(jar, args) -> int:
    subprocess.run(['javap', '-classpath', str(jar), args.fqcn])
    return 0


def main() -> int:
    ap = argparse.ArgumentParser()
    sub = ap.add_subparsers(dest='cmd', required=True)
    p = sub.add_parser('has', help='find classes whose path contains the given text')
    p.add_argument('name')
    p.set_defaults(func=cmd_has)
    p = sub.add_parser('members', help='javap a class')
    p.add_argument('fqcn')
    p.set_defaults(func=cmd_members)
    args = ap.parse_args()
    return args.func(find_jar(), args)


if __name__ == '__main__':
    raise SystemExit(main())