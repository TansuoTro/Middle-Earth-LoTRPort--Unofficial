# -*- coding: utf-8 -*-
"""Shut down the Middle-earth dev dedicated server through the Rosetta bridge."""
import io, json, os, socket, sys
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding="utf-8", errors="replace")
port = int(sys.argv[1]); gamedir = sys.argv[2]
token = open(os.path.join(gamedir, "RosettaRemoteDebugBridge", "remote-token.txt"), encoding="utf-8").read().strip()
CODE = """
net.minecraft.server.MinecraftServer server = net.minecraft.server.MinecraftServer.getServer();
if (server == null) { return "no server"; }
server.stop(false);
return "stop scheduled";
"""
sock = socket.create_connection(("127.0.0.1", port), timeout=30)
stream = sock.makefile("rwb")
stream.write((json.dumps({"token": token, "cmd": "exec", "args": {"code": CODE}}) + chr(10)).encode("utf-8"))
stream.flush()
reply = json.loads(stream.readline().decode("utf-8", "replace"))
sock.close()
print(reply.get("result", {}).get("result") if reply.get("ok") else ("ERROR: %s" % reply.get("error")))