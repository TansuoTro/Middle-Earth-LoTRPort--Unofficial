// Execute in an isolated world. No entities are added to the world in this phase.
java.lang.reflect.Field bridgeField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("instance");
bridgeField.setAccessible(true);
java.lang.reflect.Field serverField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("mcServer");
serverField.setAccessible(true);
net.minecraft.server.MinecraftServer server = (net.minecraft.server.MinecraftServer) serverField.get(bridgeField.get(null));
net.minecraft.server.world.ServerWorld world = server.getOverworld();
java.util.List<String> failures = new java.util.ArrayList<>();
com.google.gson.JsonObject report = new com.google.gson.JsonObject();
com.google.gson.JsonArray entityIds = new com.google.gson.JsonArray();
for (net.minecraft.util.Identifier id : net.minecraft.registry.Registries.ENTITY_TYPE.getIds()) {
    if (!id.getNamespace().equals("me")) continue;
    try {
        net.minecraft.entity.EntityType<?> type = net.minecraft.registry.Registries.ENTITY_TYPE.get(id);
        net.minecraft.entity.Entity entity = type.create(world);
        if (entity == null) { failures.add(id + ": factory returned null"); continue; }
        entity.getDataTracker().getChangedEntries();
        net.minecraft.nbt.NbtCompound nbt = entity.writeNbt(new net.minecraft.nbt.NbtCompound());
        net.minecraft.entity.Entity restored = type.create(world);
        restored.readNbt(nbt);
        restored.getDataTracker().getChangedEntries();
        entityIds.add(id.toString());
    } catch (Throwable error) {
        failures.add(id + ": " + error);
    }
}
report.add("entity_factories_and_nbt", entityIds);
com.google.gson.JsonArray npcIds = new com.google.gson.JsonArray();
net.minecraft.registry.Registry<net.jukoz.me.resources.datas.npc_types.NpcType> types =
        world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.NPC_TYPE);
for (net.minecraft.util.Identifier id : types.getIds()) {
    try {
        net.jukoz.me.entity.npcs.NpcEntity npc = net.jukoz.me.entity.EntitiesME.NPC.create(world);
        npc.prepareNpcIdentifier(id);
        npc.prepare();
        net.jukoz.me.entity.npcs.initializer.NpcEntityInitializer.initializeNpcEntity(world, npc);
        npc.getDataTracker().getChangedEntries();
        if (!id.equals(npc.getNpcTypeIdentifier())) throw new AssertionError("NPC type not initialized");
        net.minecraft.nbt.NbtCompound saved = npc.writeNbt(new net.minecraft.nbt.NbtCompound());
        net.jukoz.me.entity.npcs.NpcEntity restored = net.jukoz.me.entity.EntitiesME.NPC.create(world);
        restored.readNbt(saved);
        if (!id.equals(restored.getNpcTypeIdentifier())) throw new AssertionError("NPC type lost after NBT reload");
        net.minecraft.network.PacketByteBuf buffer = new net.minecraft.network.PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        try {
            net.jukoz.me.entity.npcs.data.NpcData.PACKET_CODEC.encode(buffer, npc.retrieveNpcData());
            net.jukoz.me.entity.npcs.data.NpcData decoded = net.jukoz.me.compat.net.PacketCodecs.decode(
                    net.jukoz.me.entity.npcs.data.NpcData.PACKET_CODEC, buffer, world.getRegistryManager());
            if (!id.equals(decoded.getNpcTypeId())) throw new AssertionError("NPC type lost in packet");
            if (buffer.isReadable()) throw new AssertionError("Unread packet bytes");
        } finally { buffer.release(); }
        npcIds.add(id.toString());
    } catch (Throwable error) {
        failures.add("NPC " + id + ": " + error);
    }
}
report.add("npc_initialization_save_and_packet", npcIds);
com.google.gson.JsonObject counts = new com.google.gson.JsonObject();
counts.addProperty("biomes", world.getRegistryManager().get(net.minecraft.registry.RegistryKeys.BIOME).getIds().stream().filter(id -> id.getNamespace().equals("me")).count());
counts.addProperty("races", world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.RACE).size());
counts.addProperty("factions", world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.FACTION).size());
counts.addProperty("npc_types", types.size());
counts.addProperty("spider_variants", world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.SPIDER_VARIANTS).size());
counts.addProperty("great_horn_variants", world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.GREAT_HORN_VARIANTS).size());
report.add("registries", counts);
for (String[] entry : new String[][]{{"erebor_crossbow", "512"}, {"erebor_noble_crossbow", "768"}, {"uruk_hai_crossbow", "768"}, {"goblin_crossbow", "512"}}) {
    net.minecraft.item.Item item = net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("me", entry[0]));
    int expected = Integer.parseInt(entry[1]);
    if (item.getMaxDamage() != expected || item.getMaxCount() != 1) failures.add(entry[0] + ": expected damage " + expected + "/count 1, got " + item.getMaxDamage() + "/" + item.getMaxCount());
}
if (counts.get("biomes").getAsInt() < 269 || counts.get("npc_types").getAsInt() < 100) failures.add("Missing donor dynamic registry content");
report.add("failures", new com.google.gson.Gson().toJsonTree(failures));
java.nio.file.Files.writeString(java.nio.file.Path.of("../build/entities-smoke.json"), new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(report));
if (!failures.isEmpty()) throw new AssertionError(failures.toString());
return "PASS: " + entityIds.size() + " entity factory/NBT/tracker checks, " + npcIds.size() + " NPC types initialized, saved and packet round-tripped";
