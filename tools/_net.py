
import pathlib, re, sys

READ = {
    'PacketCodecs.BOOLEAN': ('buf.readBoolean()', 'writeBoolean'),
    'PacketCodecs.INTEGER': ('buf.readInt()', 'writeInt'),
    'PacketCodecs.DOUBLE': ('buf.readDouble()', 'writeDouble'),
    'PacketCodecs.FLOAT': ('buf.readFloat()', 'writeFloat'),
    'PacketCodecs.STRING': ('buf.readString()', 'writeString'),
    'PacketCodecs.NBT_COMPOUND': ('buf.readNbt()', 'writeNbt'),
    'PacketCodecs.BYTE_ARRAY': ('buf.readByteArray()', 'writeByteArray'),
    'BlockPos.PACKET_CODEC': ('buf.readBlockPos()', 'writeBlockPos'),
    'Identifier.PACKET_CODEC': ('buf.readIdentifier()', 'writeIdentifier'),
    'ItemStack.PACKET_CODEC': ('buf.readItemStack()', 'writeItemStack'),
}

def matching_paren(text, i):
    depth = 0
    while i < len(text):
        if text[i] == '(':
            depth += 1
        elif text[i] == ')':
            depth -= 1
            if depth == 0:
                return i
        i += 1
    return i

def split_top(text):
    parts, depth, cur = [], 0, ''
    for c in text:
        if c in '([{':
            depth += 1
        elif c in ')]}':
            depth -= 1
        if c == ',' and depth == 0:
            parts.append(cur.strip())
            cur = ''
            continue
        cur += c
    if cur.strip():
        parts.append(cur.strip())
    return parts

def read_write(codec, getter):
    if codec in READ:
        read, writer = READ[codec]
        return read, 'buf.%s(%s);' % (writer, getter)
    m = re.match(r'PacketCodecs\.optional\((.+)\)$', codec)
    if m:
        inner = m.group(1)
        read, writer = READ[inner]
        return ('buf.readNullable(PacketByteBuf::%s)' % read[4:-2],
                'buf.writeNullable(%s, PacketByteBuf::%s);' % (getter, writer))
    if codec == 'StatusEffectInstance.PACKET_CODEC':
        return ('StatusEffectInstance.fromNbt(buf.readNbt())',
                'buf.writeNbt(%s.writeNbt(new NbtCompound()));' % getter)
    return None

def convert(path):
    t = path.read_text(encoding='utf-8', errors='replace')
    cls = path.stem
    orig = t

    m = re.search(r'public static final Id<' + cls + r'> ID = new Id<>\((.*?)\);', t)
    if not m:
        return None
    ident = m.group(1)
    t = t[:m.start()] + 'public static final Identifier ID = ' + ident + ';' + t[m.end():]

    tu = re.search(r'public static final PacketCodec<RegistryByteBuf, ' + cls + r'> CODEC =\s*PacketCodec\.unit\(', t)
    reads, writes = [], []
    if tu:
        close = matching_paren(t, tu.end() - 1)
        t = t[:tu.start()] + 'public static final PacketType<' + cls + '> TYPE = PacketType.create(ID, ' + cls + '::new);' + t[close + 1:]
    else:
        tt = re.search(r'public static final PacketCodec<RegistryByteBuf, ' + cls + r'> CODEC =\s*PacketCodec\.tuple\(', t)
        if not tt:
            return None
        close = matching_paren(t, tt.end() - 1)
        args = split_top(t[tt.end():close])
        pairs = args[:-1]
        for i in range(0, len(pairs), 2):
            codec = pairs[i]
            getter = pairs[i + 1]
            getter = re.sub(r'^p\s*->\s*p\.', 'this.', getter)
            rw = read_write(codec, getter)
            if rw is None:
                print('  UNSUPPORTED codec', codec, 'in', cls, file=sys.stderr)
                return None
            reads.append(rw[0])
            writes.append(rw[1])
        t = t[:tt.start()] + 'public static final PacketType<' + cls + '> TYPE = PacketType.create(ID, ' + cls + '::new);' + t[close + 1:]

    t = re.sub(r'\n\s*@Override\s*\n\s*public Id<' + cls + r'> getId\(\) \{\s*\n\s*return ID;\s*\n\s*\}', '', t)
    t = re.sub(r'\n\s*@Override\s*\n\s*public PacketCodec<RegistryByteBuf, ' + cls + r'> streamCodec\(\) \{\s*\n\s*return CODEC;\s*\n\s*\}', '', t)

    ctor = '\n\n    public ' + cls + '(PacketByteBuf buf) {\n        this(' + ', '.join(reads) + ');\n    }'
    writer = '\n\n    @Override\n    public void write(PacketByteBuf buf) {\n'
    if writes:
        writer += ''.join('        ' + w + '\n' for w in writes)
    writer += '    }'

    anchor = 'public ' + cls + '(PacketByteBuf buf)'
    idx = t.find('    @Override\n    public void process(')
    if idx < 0:
        idx = t.rfind('\n}')
    t = t[:idx] + ctor + writer + '\n\n' + t[idx:]

    t = t.replace('import net.minecraft.network.RegistryByteBuf;\n', '')
    t = t.replace('import net.minecraft.network.codec.PacketCodec;\n', '')
    t = t.replace('import net.minecraft.network.codec.PacketCodecs;\n', '')
    header = ('import net.fabricmc.fabric.api.networking.v1.PacketType;\n'
              'import net.minecraft.network.PacketByteBuf;\n')
    if 'import net.minecraft.network.PacketByteBuf;' not in t:
        t = t.replace('import net.jukoz.me.MiddleEarth;', header + 'import net.jukoz.me.MiddleEarth;', 1)
        if 'import net.minecraft.network.PacketByteBuf;' not in t:
            m2 = re.search(r'^package\s+[\w.]+\s*;', t, re.M)
            t = t[:m2.end()] + '\n' + header + t[m2.end():]

    if 'StatusEffectInstance' in ''.join(writes):
        t = t.replace('import net.minecraft.network.PacketByteBuf;',
                      'import net.minecraft.nbt.NbtCompound;\nimport net.minecraft.network.PacketByteBuf;', 1)
    if t != orig:
        path.write_text(t, encoding='utf-8')
        return True
    return None

done = []
for f in sorted(pathlib.Path('src/main/java/net/jukoz/me/network/packets').rglob('*.java')):
    try:
        if convert(f):
            done.append(f.name)
    except Exception as e:
        print('FAILED', f.name, e, file=sys.stderr)
print('converted', len(done))
print(', '.join(done))
