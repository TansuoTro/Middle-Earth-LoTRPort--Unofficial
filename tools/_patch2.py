
import pathlib
p = pathlib.Path("tools/port.py")
t = p.read_text(encoding="utf-8")

FN = '''def cmd_retouch(args) -> int:
    """Re-run the rewrite over an already-ported tree.

    Needed after the rewrite rules themselves change: the tree was produced by an
    older rule set and would otherwise keep the mistakes of that version.
    """
    root = (PROJECT / args.path).resolve()
    changed = 0
    totals = collections.Counter()
    for path in sorted(iter_java(root)):
        original = path.read_text(encoding="utf-8", errors="replace")
        text, stats = rewrite(original)
        if text != original:
            path.write_text(text, encoding="utf-8")
            changed += 1
            totals.update(stats)
    print("retouched {} of {} files".format(changed, len(list(iter_java(root)))))
    for k, v in sorted(totals.items()):
        print("  {}: {}".format(k, v))
    return 0


'''
t = t.replace("def cmd_promote(args) -> int:", FN + "def cmd_promote(args) -> int:", 1)
t = t.replace('''    p = sub.add_parser("promote", help="move staged files into src/main/java")''',
'''    p = sub.add_parser("retouch", help="re-apply the rewrite rules to an existing tree")
    p.add_argument("path", nargs="?", default="src/main/java")
    p.set_defaults(func=cmd_retouch)

    p = sub.add_parser("promote", help="move staged files into src/main/java")''', 1)
p.write_text(t, encoding="utf-8")
print("registered retouch")
