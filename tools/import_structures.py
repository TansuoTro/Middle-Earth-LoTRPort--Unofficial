#!/usr/bin/env python3
"""Import donor structures with explicit 1.20.1 schema conversions.

Requires nbtlib (pip install nbtlib). The donor is read from git, including sparse
checkouts. --check validates shipped data against a Rosetta registry export.
No unknown block, property, item or loot reference is silently discarded.
"""
from __future__ import annotations
import argparse
import collections
import gzip
import io
import json
import pathlib
import re
import subprocess
import tarfile
import uuid
import nbtlib as n

ROOT = pathlib.Path(__file__).resolve().parents[1]
TARGET = ROOT / 'src/main/resources/data/me'
COLORS = 'white orange magenta light_blue yellow lime pink gray light_gray cyan purple blue brown green red black'.split()
# These are deliberate visual substitutions for vanilla additions absent in 1.20.1.
# The complete applied mapping is included in build/structure-import-report.json.
BLOCK_MAP = {
    'minecraft:short_grass': 'minecraft:grass',
    'minecraft:bush': 'me:large_bush',
    'minecraft:firefly_bush': 'me:large_bush',
    'minecraft:short_dry_grass': 'me:dry_heather_bush',
    'minecraft:tall_dry_grass': 'me:dry_heather_bush',
    'minecraft:leaf_litter': 'me:fallen_leaves',
    'minecraft:wildflowers': 'me:clovers',
    'minecraft:pale_moss_block': 'me:forest_moss_block',
    'minecraft:pale_hanging_moss': 'me:mirkwood_hanging_roots',
    'minecraft:resin_clump': 'me:forest_moss',
    'minecraft:oxidized_copper_trapdoor': 'me:bronze_trapdoor',
    'minecraft:waxed_exposed_copper_trapdoor': 'me:bronze_trapdoor',
    'minecraft:waxed_exposed_copper_door': 'me:bronze_door',
}
for _tuff in ['chiseled_tuff', 'chiseled_tuff_bricks', 'polished_tuff', 'polished_tuff_slab',
              'polished_tuff_stairs', 'polished_tuff_wall', 'tuff_brick_slab',
              'tuff_brick_stairs', 'tuff_bricks', 'tuff_slab', 'tuff_stairs', 'tuff_wall']:
    BLOCK_MAP['minecraft:' + _tuff] = 'me:' + _tuff
ITEM_MAP = {'minecraft:trial_key': 'me:brigand_key', 'wild-things:cooked_swan': 'minecraft:cooked_chicken'}
PROPERTY_REMOVE = {
    'minecraft:leaf_litter': {'segment_amount'}, 'minecraft:wildflowers': {'flower_amount', 'facing'},
    'minecraft:pale_hanging_moss': {'tip'}, 'minecraft:resin_clump': {'waterlogged'},
    # Pre-1.20.3 decorated pots have no inventory/cracked state.
    'minecraft:decorated_pot': {'cracked'},
}
STATS = collections.Counter()
REFERENCES = collections.defaultdict(set)
REGISTRY = {}


def rewrite(value):
    if isinstance(value, str):
        return value.replace('middle-earth:', 'me:')
    if isinstance(value, dict):
        return {rewrite(k): rewrite(v) for k, v in value.items()}
    if isinstance(value, list):
        return [rewrite(v) for v in value]
    return value


def tagify(v):
    if isinstance(v, dict): return n.Compound({k: tagify(x) for k, x in v.items()})
    if isinstance(v, list): return n.List([tagify(x) for x in v])
    if isinstance(v, bool): return n.Byte(v)
    if isinstance(v, int): return n.Int(v)
    if isinstance(v, float): return n.Double(v)
    return n.String(v)


def banner_pattern(identifier):
    if identifier in REGISTRY.get('banner_patterns', {}): return REGISTRY['banner_patterns'][identifier]
    if identifier.startswith('me:'): return identifier[3:]
    vanilla = {'base': 'b', 'gradient': 'gra', 'gradient_up': 'gru', 'border': 'bo',
               'stripe_bottom': 'bs', 'stripe_top': 'ts', 'stripe_left': 'ls', 'stripe_right': 'rs',
               'stripe_center': 'cs', 'stripe_middle': 'ms', 'stripe_downright': 'drs',
               'stripe_downleft': 'dls', 'small_stripes': 'ss', 'cross': 'cr', 'straight_cross': 'sc',
               'square_bottom_left': 'bl', 'square_bottom_right': 'br', 'square_top_left': 'tl',
               'square_top_right': 'tr', 'triangle_bottom': 'bt', 'triangle_top': 'tt',
               'triangles_bottom': 'bts', 'triangles_top': 'tts', 'diagonal_left': 'ld',
               'diagonal_up_right': 'rd', 'diagonal_up_left': 'lud', 'diagonal_right': 'rud',
               'circle': 'mc', 'rhombus': 'mr', 'half_vertical': 'vh', 'half_horizontal': 'hh',
               'half_vertical_right': 'vhr', 'half_horizontal_bottom': 'hhb', 'curly_border': 'cbo',
               'bricks': 'bri', 'globe': 'glb', 'creeper': 'cre', 'skull': 'sku', 'flower': 'flo',
               'mojang': 'moj', 'piglin': 'pig'}
    return vanilla[identifier.removeprefix('minecraft:')]


def components_to_tag(components):
    tag = n.Compound()
    for key, value in components.items():
        if key.startswith('me:'):
            tag.setdefault('MEComponents', n.Compound())[key[3:]] = value
        elif key in ('minecraft:custom_name', 'minecraft:item_name'):
            # Text components became NBT in 1.21.5; 1.20.1 expects JSON text.
            text = str(value) if isinstance(value, str) else json.dumps(value.unpack())
            if not text.startswith(('{', '[', '"')): text = json.dumps({'text': text})
            tag.setdefault('display', n.Compound())['Name'] = n.String(text)
        elif key == 'minecraft:hide_additional_tooltip': tag['HideFlags'] = n.Int(32)
        elif key == 'minecraft:container_loot':
            be = tag.setdefault('BlockEntityTag', n.Compound())
            be['LootTable'] = value['loot_table']
            if 'seed' in value: be['LootTableSeed'] = n.Long(value['seed'])
        elif key == 'minecraft:potion_contents': tag['Potion'] = value['potion']
        elif key == 'minecraft:dyed_color':
            tag.setdefault('display', n.Compound())['color'] = n.Int(value['rgb'] if isinstance(value, dict) else value)
        elif key == 'minecraft:trim': tag['Trim'] = value
        elif key == 'minecraft:damage': tag['Damage'] = n.Int(value)
        elif key == 'minecraft:custom_data': tag.update(value)
        else: raise ValueError(f'Unimplemented component: {key}')
        STATS['component:' + key] += 1
    return tag


def convert_nbt(value):
    if isinstance(value, n.String): return n.String(rewrite(str(value)))
    if isinstance(value, n.List):
        for i, item in enumerate(value): value[i] = convert_nbt(item)
    elif isinstance(value, n.Compound):
        for key in list(value): value[key] = convert_nbt(value[key])
        identifier = str(value.get('id', ''))
        item = 'count' in value and identifier
        if item:
            value['id'] = n.String(ITEM_MAP.get(identifier, identifier))
            value['Count'] = n.Byte(int(value.pop('count')))
            STATS['item_stack'] += 1
        components = value.pop('components', None)
        if components:
            legacy = components_to_tag(components)
            if item: value.setdefault('tag', n.Compound()).update(legacy)
            else:
                value.update(legacy.pop('BlockEntityTag', n.Compound()))
                # Block names use CustomName, not item display.Name.
                if 'display' in legacy: value['CustomName'] = legacy.pop('display')['Name']
                value.update(legacy)
        if 'patterns' in value and identifier == 'minecraft:banner':
            value['Patterns'] = n.List[n.Compound]([
                n.Compound({'Pattern': n.String(banner_pattern(str(layer['pattern']))),
                            'Color': n.Int(COLORS.index(str(layer['color'])))}) for layer in value.pop('patterns')])
            STATS['banner'] += 1
        if 'equipment' in value:
            equipment = value.pop('equipment')
            value['ArmorItems'] = n.List[n.Compound]([equipment.get(slot, n.Compound()) for slot in ('feet','legs','chest','head')])
            value['HandItems'] = n.List[n.Compound]([equipment.get(slot, n.Compound()) for slot in ('mainhand','offhand')])
            STATS['entity_equipment'] += 1
        if 'attributes' in value:
            attributes = value.pop('attributes')
            for attr in attributes:
                name = str(attr.pop('id')).replace('minecraft:', 'minecraft:generic.')
                attr['Name'] = n.String(name); attr['Base'] = n.Double(attr.pop('base'))
                if 'modifiers' in attr:
                    modifiers = attr.pop('modifiers')
                    for modifier in modifiers:
                        mid = str(modifier.pop('id'))
                        number = uuid.uuid5(uuid.NAMESPACE_URL, mid).int
                        modifier['UUID'] = n.IntArray([(number >> shift & 0xffffffff) - (0x100000000 if number >> shift & 0x80000000 else 0) for shift in (96,64,32,0)])
                        modifier['Name'] = n.String(mid)
                        modifier['Amount'] = n.Double(modifier.pop('amount'))
                        op = str(modifier.pop('operation'))
                        modifier['Operation'] = n.Int({'add_value':0,'add_multiplied_base':1,'add_multiplied_total':2}.get(op, int(op) if op.isdigit() else 0))
                    attr['Modifiers'] = modifiers
            value['Attributes'] = attributes
        if 'fall_distance' in value: value['FallDistance'] = n.Float(value.pop('fall_distance'))
        if 'block_pos' in value:
            for key, coordinate in zip(('TileX','TileY','TileZ'), value.pop('block_pos')): value[key] = n.Int(coordinate)
        if 'bees' in value:
            bees = value.pop('bees')
            for bee in bees:
                for old,new in [('entity_data','EntityData'),('ticks_in_hive','TicksInHive'),('min_ticks_in_hive','MinOccupationTicks')]:
                    if old in bee: bee[new] = bee.pop(old)
            value['Bees'] = bees
        for old,new in [('lit_time_remaining','BurnTime'),('cooking_time_spent','CookTime'),('cooking_total_time','CookTimeTotal')]:
            if old in value: value[new] = n.Short(value.pop(old))
        value.pop('lit_total_time', None)
        if identifier == 'minecraft:jigsaw':
            value.pop('placement_priority', None); value.pop('selection_priority', None)
            value['final_state'] = n.String(convert_final_state(str(value['final_state'])))
        if identifier == 'minecraft:trial_spawner': value['id'] = n.String('me:trial_spawner')
        if identifier == 'minecraft:vault':
            value['id'] = n.String('me:vault')
            config = value.setdefault('config', n.Compound())
            config.setdefault('loot_table', n.String('me:gameplay/vaults/brigand_dungeon_treasure'))
            # A structure must not inherit the source world's player reward history/timer.
            value['server_data'] = n.Compound()
        for key in ('LootTable','loot_table','ejecting_loot_table','items_to_drop_when_ominous'):
            if key in value: REFERENCES['loot_tables'].add(str(value[key]))
    return value


def convert_state(state):
    old = rewrite(str(state['Name']))
    state['Name'] = n.String(BLOCK_MAP.get(old, old))
    if old != str(state['Name']): STATS['block:' + old + ' -> ' + str(state['Name'])] += 1
    props = state.get('Properties', {})
    for key in PROPERTY_REMOVE.get(old, set()):
        if key in props: del props[key]; STATS['property:' + old + '.' + key] += 1
    if not props: state.pop('Properties', None)
    return state


def convert_final_state(text):
    match = re.fullmatch(r'([^\[]+)(?:\[([^\]]+)\])?', text)
    if not match: raise ValueError('Unsupported jigsaw final state: ' + text)
    state = n.Compound({'Name': n.String(match[1])})
    if match[2]: state['Properties'] = n.Compound({k:n.String(v) for k,v in (part.split('=',1) for part in match[2].split(','))})
    convert_state(state)
    props = state.get('Properties', {})
    return str(state['Name']) + ('[' + ','.join(f'{k}={v}' for k,v in props.items()) + ']' if props else '')


def convert_json(value):
    value = rewrite(value)
    if isinstance(value, list): return [convert_json(v) for v in value]
    if not isinstance(value, dict): return value
    value = {k: convert_json(v) for k,v in value.items()}
    if value.get('type') == 'minecraft:loot_table' and 'value' in value: value['name'] = value.pop('value')
    if value.get('type') == 'minecraft:item': value['name'] = ITEM_MAP.get(value['name'], value['name'])
    if value.get('function') == 'minecraft:set_components':
        value['function'] = 'minecraft:set_nbt'
        value['tag'] = components_to_tag(tagify(value.pop('components'))).snbt()
        value.pop('id', None)
    if value.get('function') == 'minecraft:enchant_with_levels':
        value.pop('options', None)  # 1.20.1 selects from the static enchantment registry.
        value.setdefault('treasure', False)
    if value.get('function') == 'minecraft:enchant_randomly' and 'options' in value:
        options = value.pop('options')
        if isinstance(options, list): value['enchantments'] = options
    if 'Name' in value: value = convert_state(tagify(value)).unpack()
    if 'block' in value: value['block'] = BLOCK_MAP.get(value['block'], value['block'])
    return value


def donor_files(repo):
    prefix = 'middle-earth/src/main/resources/data/middle-earth/'
    raw = subprocess.check_output(['git','-C',str(repo),'archive','HEAD',prefix.rstrip('/')])
    with tarfile.open(fileobj=io.BytesIO(raw)) as archive:
        return {entry.name[len(prefix):]: archive.extractfile(entry).read() for entry in archive.getmembers() if entry.isfile()}


def write_nbt(path, data):
    raw = io.BytesIO(); data.write(raw)
    path.parent.mkdir(parents=True, exist_ok=True)
    # Stable gzip header makes a repeated import produce identical git content.
    path.write_bytes(gzip.compress(raw.getvalue(), mtime=0))


def import_data(repo):
    files = donor_files(repo)
    for rel, raw in sorted(files.items()):
        if rel.startswith('structure/') and rel.endswith('.nbt'):
            target = TARGET / rel.replace('structure/', 'structures/', 1)
            # Keep original 1.20.1 templates that overlap with unchanged donor baseline.
            parsed = n.File.parse(io.BytesIO(gzip.decompress(raw)))
            if int(parsed.get('DataVersion',0)) <= 3465 and target.exists(): continue
            for palette in [parsed.get('palette',[]), *parsed.get('palettes',[])]:
                for state in palette: convert_state(state)
            for block in parsed.get('blocks',[]):
                if 'nbt' in block: block['nbt'] = convert_nbt(block['nbt'])
            for entity in parsed.get('entities',[]): entity['nbt'] = convert_nbt(entity['nbt'])
            parsed['DataVersion'] = n.Int(3465)
            write_nbt(target,parsed); STATS['templates_imported'] += 1
            if STATS['templates_imported'] % 50 == 0: print(f"Converted {STATS['templates_imported']} templates", flush=True)
        elif any(rel.startswith('worldgen/'+folder+'/') for folder in ('structure','structure_set','template_pool','processor_list')):
            target = TARGET / rel
            value = convert_json(json.loads(raw))
            if target.exists() and '/structure_set/' in rel:
                existing = json.loads(target.read_text())
                names = {v['structure'] for v in value['structures']}
                value['structures'].extend(v for v in existing['structures'] if v['structure'] not in names)
            if target.exists() and '/template_pool/' in rel:
                existing = json.loads(target.read_text())
                names = {json.dumps(v['element'], sort_keys=True) for v in value['elements']}
                value['elements'].extend(v for v in existing['elements'] if json.dumps(v['element'],sort_keys=True) not in names)
            target.parent.mkdir(parents=True,exist_ok=True)
            target.write_text(json.dumps(value,indent=2)+'\n',encoding='utf-8'); STATS[rel.split('/')[1]+'_imported'] += 1
        elif rel.startswith(('loot_table/structures/','loot_table/gameplay/vaults/')):
            target = TARGET / rel.replace('loot_table/', 'loot_tables/',1)
            target.parent.mkdir(parents=True,exist_ok=True)
            target.write_text(json.dumps(convert_json(json.loads(raw)),indent=2)+'\n',encoding='utf-8'); STATS['loot_tables_imported'] += 1


def validate():
    errors = set()
    counts = collections.Counter()
    def exists(kind, ref):
        if not ref.startswith('me:'): return
        path = ref[3:]
        ext = '.nbt' if kind == 'structures' else '.json'
        if not any((base / kind / (path+ext)).exists() for base in [TARGET,ROOT/'src/main/generated/data/me']):
            errors.add(f'Missing {kind}: {ref}')
    def state_check(state, file):
        name = str(state['Name']); counts['palette_states']+=1
        if not REGISTRY: return
        block = REGISTRY['blocks'].get(name)
        if block is None: errors.add(f'{file}: unknown block {name}'); return
        for key,val in state.get('Properties',{}).items():
            if str(val) not in block.get(key,[]): errors.add(f'{name}: invalid {key}={val}')
    def walk(value, file):
        if isinstance(value,dict):
            ident = str(value.get('id',''))
            if 'Count' in value and ident and REGISTRY and ident not in REGISTRY['items']: errors.add(f'{file}: unknown item {ident}')
            for key,val in value.items():
                if key in ('LootTable','loot_table','ejecting_loot_table','items_to_drop_when_ominous'): exists('loot_tables',str(val))
                if key == 'pool': exists('worldgen/template_pool',str(val))
                if key == 'final_state':
                    match = re.match(r'([^\[]+)',str(val))
                    if REGISTRY and match[1] not in REGISTRY['blocks']: errors.add(f'{file}: unknown jigsaw final state {val}')
                walk(val,file)
        elif isinstance(value,list):
            for v in value: walk(v,file)
    for file in TARGET.joinpath('structures').rglob('*.nbt'):
        parsed = n.load(file); counts['templates']+=1
        if int(parsed.get('DataVersion',0)) > 3465: errors.add(f'{file}: future DataVersion')
        for palette in [parsed.get('palette',[]),*parsed.get('palettes',[])]:
            for state in palette: state_check(state,file.name)
        for block in parsed.get('blocks',[]):
            if 'nbt' in block:
                be = block['nbt'];walk(be,file.name)
                if REGISTRY and str(be.get('id')) not in REGISTRY['block_entities']: errors.add(f'{file.name}: unknown block entity {be.get("id")}')
        for entity in parsed.get('entities',[]):
            walk(entity['nbt'],file.name)
            if REGISTRY and str(entity['nbt'].get('id')) not in REGISTRY['entities']: errors.add(f'{file.name}: unknown entity {entity["nbt"].get("id")}')
    for folder in ['structure','structure_set','template_pool','processor_list']:
        for file in TARGET.joinpath('worldgen',folder).rglob('*.json'):
            data=json.loads(file.read_text()); counts[folder]+=1
            def json_walk(value):
                if isinstance(value,dict):
                    if 'Name' in value: state_check(value,file.name)
                    for key,v in value.items():
                        if isinstance(v,str):
                            for field,kind in [('start_pool','worldgen/template_pool'),('fallback','worldgen/template_pool'),('location','structures'),('processors','worldgen/processor_list'),('structure','worldgen/structure')]:
                                if key==field:exists(kind,v)
                        json_walk(v)
                elif isinstance(value,list):
                    for v in value:json_walk(v)
            json_walk(data)
    for file in TARGET.joinpath('loot_tables').rglob('*.json'):
        if not any(p in file.parts for p in ('structures','vaults')): continue
        data=json.loads(file.read_text())
        def loot_walk(value):
            if isinstance(value,dict):
                if value.get('type')=='minecraft:loot_table':exists('loot_tables',value['name'])
                if value.get('type')=='minecraft:item' and REGISTRY and value['name'] not in REGISTRY['items']:errors.add(f'{file.name}: unknown loot item {value["name"]}')
                for v in value.values():loot_walk(v)
            elif isinstance(value,list):
                for v in value:loot_walk(v)
        loot_walk(data)
    return counts, sorted(errors)


def main():
    global REGISTRY
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--donor',default=str(ROOT.parent/'.tmp_me_upstream'))
    parser.add_argument('--registry',type=pathlib.Path,default=ROOT/'build/structure-registry.json')
    parser.add_argument('--check',action='store_true')
    args=parser.parse_args()
    if args.registry.exists(): REGISTRY=json.loads(args.registry.read_text(encoding='utf-8-sig'))
    if not args.check: import_data(args.donor)
    counts,errors=validate()
    report={'statistics':dict(STATS),'inventory':dict(counts),'runtime_registry_checked':bool(REGISTRY),'block_mappings':BLOCK_MAP,'item_mappings':ITEM_MAP,'errors':errors}
    report_path=ROOT/'build/structure-import-report.json'
    report_path.write_text(json.dumps(report,indent=2)+'\n',encoding='utf-8')
    print(json.dumps(report,indent=2))
    return bool(errors)


if __name__=='__main__': raise SystemExit(main())
