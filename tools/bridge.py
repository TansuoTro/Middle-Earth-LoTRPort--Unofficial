# -*- coding: utf-8 -*-
"""Minimal Rosetta bridge client for the Middle-earth dev server."""
import io, json, os, socket, sys
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
    cmd = sys.argv[1]
    if cmd == 'console':
        print(json.dumps(call('console', {'command': sys.argv[2]}), ensure_ascii=False)[:2000])
    elif cmd == 'exec':
        print(json.dumps(call('exec', {'code': open(sys.argv[2], encoding='utf-8').read()}), ensure_ascii=False)[:4000])