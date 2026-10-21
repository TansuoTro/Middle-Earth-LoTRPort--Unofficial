Class<?> c = Class.forName("net.minecraft.client.Minecraft");
java.lang.reflect.Field f = c.getDeclaredField("f_90981_");
f.setAccessible(true);
Object mc = f.get(null);
if (mc == null) return "no client instance";
c.getMethod("m_91341_").invoke(mc);
return "stop scheduled";