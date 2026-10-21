java.lang.reflect.Field bridgeField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("instance");
bridgeField.setAccessible(true);
java.lang.reflect.Field serverField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("mcServer");
serverField.setAccessible(true);
net.minecraft.server.MinecraftServer server = (net.minecraft.server.MinecraftServer) serverField.get(bridgeField.get(null));
if (server.isDedicated()) {
    server.stop(false);
} else {
    net.minecraft.client.MinecraftClient.getInstance().execute(() -> net.minecraft.client.MinecraftClient.getInstance().scheduleStop());
}
return "Clean shutdown scheduled";
