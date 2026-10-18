import re, collections, sys, os

path = sys.argv[1] if len(sys.argv) > 1 else "build/port-errors.log"
blob = open(path, encoding="utf-8", errors="replace").read()
ERR = re.compile(r"^(?P<file>\S.*?[.]java):(?P<line>[0-9]+): (?:error|\u9519\u8bef): (?P<msg>.*)$", re.M)
ms = [(os.path.basename(m.group("file")), int(m.group("line")), m.group("msg").strip()) for m in ERR.finditer(blob)]
c = collections.Counter(ms)
print("raw", len(ms), "unique(file,line,msg)", len(c), "raw/2", len(ms) / 2)
print("mult dist", dict(collections.Counter(c.values())))
RENDER = ("renderer", "client", "gui", "particle")
rend = [x for x in ms if any(k in x[0].lower() for k in RENDER)]
print("render raw", len(rend), "render unique", len(set(rend)), "render raw/2", len(rend) / 2)
