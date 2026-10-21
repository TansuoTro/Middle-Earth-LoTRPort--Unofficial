# -*- coding: utf-8 -*-
"""Minimal Rosetta bridge client for the Middle-earth dev server."""
import argparse, io, json, os, pathlib, socket, sys
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding="utf-8", errors="replace")

def call(cmd, args, port=48798, gamedir='run', timeout=120):
    token = open(os.path.join(gamedir, 'RosettaRemoteDebugBridge', 'remote-token.txt'), encoding='utf-8').read().strip()
    sock = socket.create_connection(('127.0.0.1', port), timeout=timeout)
    stream = sock.makefile('rwb')
    stream.write((json.dumps({'token': token, 'cmd': cmd, 'args': args}) + chr(10)).encode('utf-8'))
    stream.flush()
    line = stream.readline().decode('utf-8', 'replace')
    sock.close()
    try:
        return json.loads(line)
    except Exception:
        return {'raw': line}

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('command', choices=['ping', 'console', 'exec'])
    parser.add_argument('input', nargs='?')
    parser.add_argument('--port', type=int, default=48798)
    parser.add_argument('--gamedir', default='run')
    parser.add_argument('--timeout', type=float, default=120)
    parser.add_argument('--output', type=pathlib.Path)
    options = parser.parse_args()
    if options.command != 'ping' and options.input is None:
        parser.error('console and exec require an input')
    args = ({'command': options.input} if options.command == 'console' else
            {'code': pathlib.Path(options.input).read_text(encoding='utf-8')} if options.command == 'exec' else {})
    reply = call(options.command, args, options.port, options.gamedir, options.timeout)
    output = json.dumps(reply, ensure_ascii=False, indent=2)
    if options.output:
        options.output.parent.mkdir(parents=True, exist_ok=True)
        options.output.write_text(output + '\n', encoding='utf-8')
    print(output)
    sys.exit(0 if reply.get('ok') else 1)
