#!/usr/bin/env python3
"""
damage_audit.py - find out who may be intercepting / cancelling damage events
on the Mohist (Bukkit + Forge) server, over the Rosetta remote debug bridge.

Usage:
  python tools/damage_audit.py audit              # static audit (safe, read-only)
  python tools/damage_audit.py probe [--seconds N]  # install staged listeners, wait, report
  python tools/damage_audit.py probe-report       # just read the probe log
  python tools/damage_audit.py uninstall          # remove the probe listeners

Env / args:
  ROSETTA_HOST (default frp-top.com)  ROSETTA_PORT (default 17799)
  ROSETTA_TOKEN (default the emoney server token)
"""
import argparse, json, os, socket, sys, time

HOST = os.environ.get("ROSETTA_HOST", "frp-top.com")
PORT = int(os.environ.get("ROSETTA_PORT", "17799"))
TOKEN = os.environ.get("ROSETTA_TOKEN", "5qDnF4arJtN2JCK9bMbemrp47kFc")


def call(cmd, args=None, timeout=120, host=None, port=None, token=None):
    host = host or HOST
    port = port or PORT
    token = token or TOKEN
    s = socket.create_connection((host, port), 15)
    s.settimeout(timeout)
    s.sendall((json.dumps({"token": token, "cmd": cmd, "args": args or {}}) + "\n").encode())
    buf = b""
    while True:
        d = s.recv(65536)
        if not d:
            break
        buf += d
        if b"\n" in buf:
            break
    s.close()
    raw = buf.decode("utf-8", "replace").strip()
    try:
        j = json.loads(raw)
    except Exception:
        return raw
    if isinstance(j, dict):
        r = j.get("result")
        if isinstance(r, dict):
            return r.get("result", json.dumps(r, ensure_ascii=False))
        if r is not None:
            return str(r)
        if "error" in j:
            return "ERROR: " + str(j["error"])
    return raw


def run_java(code, **kw):
    return call("exec", {"code": code}, **kw)


# --------------------------------------------------------------------------
# 1. static audit
# --------------------------------------------------------------------------
JAVA_AUDIT = r"""
StringBuilder sb = new StringBuilder();
String NL = "\n";

// ===== 1. Bukkit damage listeners =====
sb.append("### 1. Bukkit listeners on damage events").append(NL);
String[] EVS = {
  "org.bukkit.event.entity.EntityDamageEvent",
  "org.bukkit.event.entity.EntityDamageByEntityEvent",
  "org.bukkit.event.entity.EntityDamageByBlockEvent",
  "org.bukkit.event.entity.EntityRegainHealthEvent",
  "org.bukkit.event.entity.EntityDeathEvent"
};
java.util.Set<Integer> seenLists = new java.util.HashSet<>();
for (String en : EVS) {
  Class<?> ec;
  try { ec = Class.forName(en); } catch (Throwable t) { continue; }
  Object hl;
  try { hl = ec.getMethod("getHandlerList").invoke(null); } catch (Throwable t) { continue; }
  if (!seenLists.add(System.identityHashCode(hl))) {
    sb.append("  -- ").append(en.substring(en.lastIndexOf('.') + 1)).append(": shares handler list, see above").append(NL);
    continue;
  }
  sb.append("  -- ").append(en.substring(en.lastIndexOf('.') + 1)).append(NL);
  try {
    Object raw = hl.getClass().getMethod("getRegisteredListeners").invoke(hl);
    java.util.List<Object> rls = new java.util.ArrayList<>();
    if (raw instanceof Object[]) { for (Object o : (Object[]) raw) rls.add(o); }
    else if (raw instanceof Iterable) { for (Object o : (Iterable<?>) raw) rls.add(o); }
    java.util.TreeMap<String, String> sorted = new java.util.TreeMap<>();
    int i = 0;
    for (Object rl : rls) {
      Class<?> rc = rl.getClass();
      Object prio = rc.getMethod("getPriority").invoke(rl);
      int ord = ((Enum<?>) prio).ordinal();
      Object plug = rc.getMethod("getPlugin").invoke(rl);
      String pn = "?";
      try { pn = String.valueOf(plug.getClass().getMethod("getName").invoke(plug)); } catch (Throwable t) {}
      Object lis = rc.getMethod("getListener").invoke(rl);
      boolean ign = Boolean.TRUE.equals(rc.getMethod("isIgnoringCancelled").invoke(rl));
      boolean canCancel = !"MONITOR".equals(String.valueOf(prio));
      String line = String.format("       %-8s %-20s %s%s",
          String.valueOf(prio), pn, lis.getClass().getName(),
          canCancel ? "" : "   [MONITOR - cannot cancel]");
      if (!ign) line += "   (sees already-cancelled)";
      sorted.put(String.format("%02d_%03d", ord, i++), line);
    }
    for (String s : sorted.values()) sb.append(s).append(NL);
    sb.append("       total=").append(rls.size()).append(NL);
  } catch (Throwable t) {
    sb.append("       ERR ").append(t).append(NL);
  }
}

// ===== 2. Forge bus listeners on damage events =====
sb.append(NL).append("### 2. Forge EVENT_BUS listeners (damage-related)").append(NL);
try {
  Class<?> mf = Class.forName("net.minecraftforge.common.MinecraftForge");
  Object bus = mf.getField("EVENT_BUS").get(null);
  Object map = null;
  Class<?> k = bus.getClass();
  while (k != null && map == null) {
    for (java.lang.reflect.Field f : k.getDeclaredFields()) {
      if (f.getName().equals("listeners") && java.util.Map.class.isAssignableFrom(f.getType())) {
        f.setAccessible(true); map = f.get(bus); break;
      }
    }
    k = k.getSuperclass();
  }
  if (!(map instanceof java.util.Map)) {
    sb.append("  could not locate EventBus.listeners").append(NL);
  } else {
    java.util.Map<?, ?> m = (java.util.Map<?, ?>) map;
    String[] pats = {"LivingAttackEvent", "LivingHurtEvent", "LivingDamageEvent",
                     "LivingIncomingDamageEvent", "LivingKnockBackEvent"};
    java.util.TreeMap<String, java.util.List<String>> hits = new java.util.TreeMap<>();
    int total = 0;
    for (Object v : m.values()) {
      for (Object l : (java.util.List<?>) v) {
        total++;
        String readable = "";
        try {
          java.lang.reflect.Field rf = null;
          for (Class<?> c = l.getClass(); c != null && rf == null; c = c.getSuperclass()) {
            for (java.lang.reflect.Field f : c.getDeclaredFields()) {
              if (f.getName().equals("readable")) { f.setAccessible(true); rf = f; break; }
            }
          }
          readable = String.valueOf(rf == null ? l : rf.get(l));
        } catch (Throwable t) { readable = String.valueOf(l); }
        for (String p : pats) {
          if (readable.contains(p)) {
            String prio = "";
            try {
              java.lang.reflect.Field sf = null;
              for (Class<?> c = l.getClass(); c != null && sf == null; c = c.getSuperclass()) {
                for (java.lang.reflect.Field f : c.getDeclaredFields()) {
                  if (f.getName().equals("subInfo")) { f.setAccessible(true); sf = f; break; }
                }
              }
              Object si = sf == null ? null : sf.get(l);
              prio = si == null ? "" : String.valueOf(si).replace("@net.minecraftforge.eventbus.api.SubscribeEvent", "");
            } catch (Throwable t3) {}
            hits.computeIfAbsent(p, x -> new java.util.ArrayList<>()).add(
                "       " + prio + "  " + readable.replace("ASM: class ", "").replace("Lnet/minecraftforge/event/", ""));
          }
        }
      }
    }
    for (String p : pats) {
      java.util.List<String> l = hits.get(p);
      sb.append("  -- ").append(p).append(": ").append(l == null ? 0 : l.size()).append(NL);
      if (l != null) for (String s : l) sb.append(s).append(NL);
    }
    sb.append("  total forge listeners=").append(total).append(NL);
  }
} catch (Throwable t) {
  sb.append("  ERR ").append(t).append(NL);
}

// ===== 3. server.properties / bukkit.yml relevant flags =====
sb.append(NL).append("### 3. server config").append(NL);
String[] props = {"pvp", "difficulty", "hardcore", "spawn-protection", "enable-command-block", "force-gamemode", "gamemode"};
try {
  java.io.File f = new java.io.File("server.properties");
  if (f.exists()) {
    for (String line : java.nio.file.Files.readAllLines(f.toPath())) {
      String t = line.trim();
      for (String p : props) if (t.startsWith(p + "=")) sb.append("  server.properties: ").append(t).append(NL);
    }
  }
} catch (Throwable t) { sb.append("  ERR ").append(t).append(NL); }

// ===== 4. NPC + player positions (reach check) =====
sb.append(NL).append("### 4. players vs NPC distance (server-side reach)").append(NL);
try {
  net.minecraft.server.MinecraftServer srv = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
  for (net.minecraft.server.level.ServerLevel lvl : srv.getAllLevels()) {
    java.util.List<net.minecraft.world.entity.Entity> npcs = new java.util.ArrayList<>();
    java.util.List<net.minecraft.world.entity.Entity> players = new java.util.ArrayList<>();
    for (net.minecraft.world.entity.Entity e : lvl.getAllEntities()) {
      if (e.getClass().getName().equals("net.jukoz.me.entity.npcs.NpcEntity")) npcs.add(e);
      if (e instanceof net.minecraft.server.level.ServerPlayer) players.add(e);
    }
    if (npcs.isEmpty() || players.isEmpty()) continue;
    sb.append("  dim ").append(lvl.dimension().location()).append(" npcs=").append(npcs.size()).append(" players=").append(players.size()).append(NL);
    for (net.minecraft.world.entity.Entity p : players) {
      double best = 1e9; int bid = -1;
      for (net.minecraft.world.entity.Entity n : npcs) {
        double dx = p.m_20185_() - n.m_20185_(), dy = p.m_20186_() - n.m_20186_(), dz = p.m_20189_() - n.m_20189_();
        double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (d < best) { best = d; bid = n.m_19879_(); }
      }
      String gm = "?";
      try { gm = ((net.minecraft.server.level.ServerPlayer) p).m_7500_() ? "creative" : "non-creative"; } catch (Throwable t3) {}
      sb.append("    player ").append(p.m_7755_() != null ? p.m_7755_().getString() : "?").append("(id=").append(p.m_19879_()).append(")")
        .append(" gamemode=").append(gm)
        .append(" nearestNpc=").append(bid).append(" dist=").append(String.format("%.2f", best))
        .append(best <= 3.0 ? "  [within 3.0]" : (best <= 6.0 ? "  [3.0..6.0]" : "  [far]"))
        .append(NL);
    }
  }
} catch (Throwable t) { sb.append("  ERR ").append(t).append(NL); }

// ===== 5. plugins present =====
sb.append(NL).append("### 5. installed plugins").append(NL);
try {
  java.io.File d = new java.io.File("plugins");
  java.io.File[] fs = d.listFiles();
  java.util.List<String> names = new java.util.ArrayList<>();
  if (fs != null) for (java.io.File x : fs) if (x.isDirectory()) names.add(x.getName());
  java.util.Collections.sort(names);
  sb.append("  ").append(names).append(NL);
} catch (Throwable t) { sb.append("  ERR ").append(t).append(NL); }

return sb.toString();
"""

# --------------------------------------------------------------------------
# 2. probe: staged listeners (Bukkit + Forge) that record the cancelled state
#    at every event priority, so the stage where damage dies is visible.
# --------------------------------------------------------------------------
JAVA_PROBE_INSTALL = r"""
StringBuilder sb = new StringBuilder();
String NL = "\n";
boolean already = "true".equals(System.getProperty("damage_probe_installed"));

if (!already) {
  // ---- Bukkit side ----
  Class<?> bk = Class.forName("org.bukkit.Bukkit");
  Object pm = bk.getMethod("getPluginManager").invoke(null);
  Object[] plugins = (Object[]) pm.getClass().getMethod("getPlugins").invoke(pm);
  Object bukkitPlugin = null;
  for (Object p : plugins) {
    String n = String.valueOf(p.getClass().getMethod("getName").invoke(p));
    if (n.equalsIgnoreCase("MiddleEarth") || n.equalsIgnoreCase("Mohist")) { bukkitPlugin = p; break; }
  }
  if (bukkitPlugin == null && plugins.length > 0) bukkitPlugin = plugins[0];

  Class<?> listenerCls = Class.forName("org.bukkit.event.Listener");
  Class<?> execCls = Class.forName("org.bukkit.plugin.EventExecutor");
  Class<?> prioCls = Class.forName("org.bukkit.event.EventPriority");
  Class<?> rlCls = Class.forName("org.bukkit.plugin.RegisteredListener");
  Class<?> evCls = Class.forName("org.bukkit.event.entity.EntityDamageEvent");
  Object hl = evCls.getMethod("getHandlerList").invoke(null);

  final java.nio.file.Path OUT = java.nio.file.Paths.get("logs/damage_probe.log");
  java.lang.reflect.InvocationHandler lisH = (proxy, method, larg) -> {
    if (method.getName().equals("equals")) return proxy == larg[0];
    if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
    if (method.getName().equals("toString")) return "DamageProbeListener";
    return null;
  };
  Object listener = java.lang.reflect.Proxy.newProxyInstance(listenerCls.getClassLoader(), new Class<?>[]{listenerCls}, lisH);

  java.lang.reflect.Constructor<?> ctor = null;
  for (java.lang.reflect.Constructor<?> c : rlCls.getConstructors()) if (c.getParameterCount() == 5) { ctor = c; break; }
  if (ctor == null) return "no 5-arg RegisteredListener constructor";
  java.lang.reflect.Method register = hl.getClass().getMethod("register", rlCls);

  String[] order = {"LOWEST", "LOW", "NORMAL", "HIGH", "HIGHEST", "MONITOR"};
  for (final String pn : order) {
    java.lang.reflect.InvocationHandler execH = (proxy, method, margs) -> {
      if (!method.getName().equals("execute") || margs == null || margs.length < 2) return null;
      if (!"true".equals(System.getProperty("damage_probe_enabled"))) return null;
      Object ev = margs[1];
      try {
        boolean cancelled = (Boolean) ev.getClass().getMethod("isCancelled").invoke(ev);
        double dmg = ((Number) ev.getClass().getMethod("getDamage").invoke(ev)).doubleValue();
        double fin = ((Number) ev.getClass().getMethod("getFinalDamage").invoke(ev)).doubleValue();
        Object cause = ev.getClass().getMethod("getCause").invoke(ev);
        Object ent = ev.getClass().getMethod("getEntity").invoke(ev);
        String entType = String.valueOf(ent.getClass().getMethod("getType").invoke(ent));
        if (entType.contains("DROPPED_ITEM")) return null;
        String damager = "-";
        try {
          Object d = ev.getClass().getMethod("getDamager").invoke(ev);
          if (d != null) damager = String.valueOf(d.getClass().getMethod("getType").invoke(d)) + "/" + String.valueOf(d.getClass().getMethod("getName").invoke(d));
        } catch (Throwable t) {}
        String line = String.format("%s | BUKKIT  %-7s | cancelled=%-5s | dmg=%.2f final=%.2f | %s | %s | damager=%s",
            new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()),
            pn, String.valueOf(cancelled), dmg, fin, String.valueOf(cause), entType, damager);
        java.nio.file.Files.write(OUT, java.util.Arrays.asList(line),
            java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
      } catch (Throwable t) {}
      return null;
    };
    Object executor = java.lang.reflect.Proxy.newProxyInstance(execCls.getClassLoader(), new Class<?>[]{execCls}, execH);
    Object prio = Enum.valueOf((Class) prioCls, pn);
    Object rl = ctor.newInstance(listener, executor, prio, bukkitPlugin, Boolean.FALSE);
    register.invoke(hl, rl);
    sb.append("bukkit registered ").append(pn).append(NL);
  }

  // ---- Forge side ----
  Class<?> forgePrio = Class.forName("net.minecraftforge.eventbus.api.EventPriority");
  Object bus = Class.forName("net.minecraftforge.common.MinecraftForge").getField("EVENT_BUS").get(null);
  java.lang.reflect.Method addListener = null;
  for (java.lang.reflect.Method m : bus.getClass().getMethods()) {
    if (m.getName().equals("addListener") && m.getParameterCount() == 4
        && m.getParameterTypes()[0].getName().endsWith("EventPriority")
        && m.getParameterTypes()[3].getName().equals("java.util.function.Consumer")) { addListener = m; break; }
  }
  if (addListener == null) { sb.append("no 4-arg addListener").append(NL); }
  else {
    String[] fevs = {"LivingAttackEvent", "LivingHurtEvent", "LivingDamageEvent"};
    String[] forder = {"LOWEST", "LOW", "NORMAL", "HIGH", "HIGHEST", "MONITOR"};
    for (String simple : fevs) {
      Class<?> evc;
      try { evc = Class.forName("net.minecraftforge.event.entity.living." + simple); } catch (Throwable t) { continue; }
      for (final String pn : forder) {
        final String label = "FORGE   " + pn + " " + simple.replace("Living", "").replace("Event", "");
        java.util.function.Consumer<Object> cons = (ev) -> {
          if (!"true".equals(System.getProperty("damage_probe_enabled"))) return;
          try {
            Object ent = ev.getClass().getMethod("getEntity").invoke(ev);
            if (ent.getClass().getName().contains("ItemEntity")) return;
            Object canceled = ev.getClass().getMethod("isCanceled").invoke(ev);
            String entType;
            try { entType = String.valueOf(ent.getClass().getMethod("m_6095_").invoke(ent)); }
            catch (Throwable t) { entType = ent.getClass().getSimpleName(); }
            String dmg = "";
            try { dmg = " amt=" + String.format("%.2f", ((Number) ev.getClass().getMethod("getAmount").invoke(ev)).doubleValue()); } catch (Throwable t) {}
            String src = "?";
            try { src = String.valueOf(ev.getClass().getMethod("getSource").invoke(ev)); } catch (Throwable t) {}
            String line = String.format("%s | %-28s | canceled=%-5s | %s%s | src=%s",
                new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()),
                label, String.valueOf(canceled), entType, dmg, src);
            java.nio.file.Files.write(OUT, java.util.Arrays.asList(line),
                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
          } catch (Throwable t) {
            try {
              java.nio.file.Files.write(OUT, java.util.Arrays.asList("PROBE-ERR " + t),
                  java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
            } catch (Throwable t2) {}
          }
        };
        Object prio = Enum.valueOf((Class) forgePrio, pn);
        addListener.invoke(bus, prio, Boolean.TRUE, evc, cons);
        sb.append("forge registered ").append(pn).append(" ").append(simple).append(NL);
      }
    }
  }
  System.setProperty("damage_probe_installed", "true");
} else {
  sb.append("probe listeners already installed").append(NL);
}
System.setProperty("damage_probe_enabled", "true");
sb.append("log=").append(java.nio.file.Paths.get("logs/damage_probe.log").toAbsolutePath()).append(NL);
return sb.toString();
"""

JAVA_PROBE_REPORT = r"""
java.nio.file.Path p = java.nio.file.Paths.get("logs/damage_probe.log");
if (!java.nio.file.Files.exists(p)) return "(no probe log)";
java.util.List<String> lines = java.nio.file.Files.readAllLines(p);
StringBuilder sb = new StringBuilder();
int start = Math.max(0, lines.size() - 120);
for (int i = start; i < lines.size(); i++) sb.append(lines.get(i)).append("\n");
return sb.toString();
"""

JAVA_PROBE_CLEAR = r"""
System.setProperty("damage_probe_enabled", "false");
java.nio.file.Path p = java.nio.file.Paths.get("logs/damage_probe.log");
if (java.nio.file.Files.exists(p)) java.nio.file.Files.delete(p);
return "cleared, probe disabled";
"""

JAVA_PROBE_UNINSTALL = r"""
System.setProperty("damage_probe_enabled", "false");
StringBuilder sb = new StringBuilder();
Class<?> evCls = Class.forName("org.bukkit.event.entity.EntityDamageEvent");
Object hl = evCls.getMethod("getHandlerList").invoke(null);
Object raw = hl.getClass().getMethod("getRegisteredListeners").invoke(hl);
java.util.List<Object> rls = new java.util.ArrayList<>();
if (raw instanceof Object[]) { for (Object o : (Object[]) raw) rls.add(o); }
else if (raw instanceof Iterable) { for (Object o : (Iterable<?>) raw) rls.add(o); }
int n = 0;
java.lang.reflect.Method unreg = hl.getClass().getMethod("unregister", Class.forName("org.bukkit.plugin.RegisteredListener"));
for (Object rl : rls) {
  Object lis = rl.getClass().getMethod("getListener").invoke(rl);
  if (java.lang.reflect.Proxy.isProxyClass(lis.getClass())) {
    try { unreg.invoke(hl, rl); n++; } catch (Throwable t) {}
  }
}
sb.append("unregistered ").append(n).append(" bukkit proxy listener(s); forge probe left in place but disabled");
return sb.toString();
"""


def cmd_audit(a):
    print(run_java(JAVA_AUDIT, timeout=180))


def cmd_probe(a):
    print(run_java(JAVA_PROBE_CLEAR, timeout=60))
    print(run_java(JAVA_PROBE_INSTALL, timeout=180))
    print("\n>>> 现在去游戏里打一只 NPC（多打几下）。等待 %d 秒...\n" % a.seconds)
    time.sleep(a.seconds)
    print("### probe trace")
    print(run_java(JAVA_PROBE_REPORT, timeout=120))
    print(run_java(JAVA_PROBE_UNINSTALL, timeout=60))


def cmd_probe_report(a):
    print(run_java(JAVA_PROBE_REPORT, timeout=120))


def cmd_uninstall(a):
    print(run_java(JAVA_PROBE_UNINSTALL, timeout=60))


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--host", default=HOST)
    ap.add_argument("--port", type=int, default=PORT)
    ap.add_argument("--token", default=TOKEN)
    sub = ap.add_subparsers(dest="cmd")
    p = sub.add_parser("audit")
    p.set_defaults(fn=cmd_audit)
    p = sub.add_parser("probe")
    p.add_argument("--seconds", type=int, default=30)
    p.set_defaults(fn=cmd_probe)
    p = sub.add_parser("probe-report")
    p.set_defaults(fn=cmd_probe_report)
    p = sub.add_parser("uninstall")
    p.set_defaults(fn=cmd_uninstall)
    a = ap.parse_args()
    if not getattr(a, "fn", None):
        a.fn = cmd_audit
    globals()["HOST"] = a.host
    globals()["PORT"] = a.port
    globals()["TOKEN"] = a.token
    a.fn(a)


if __name__ == "__main__":
    main()
