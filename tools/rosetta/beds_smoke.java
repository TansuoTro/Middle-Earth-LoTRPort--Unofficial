java.lang.reflect.Field bridgeField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("instance");
bridgeField.setAccessible(true);
java.lang.reflect.Field serverField = com.rosetta.remotedebugbridge.net.RemoteBridge.class.getDeclaredField("mcServer");
serverField.setAccessible(true);
net.minecraft.server.MinecraftServer server = (net.minecraft.server.MinecraftServer) serverField.get(bridgeField.get(null));
net.minecraft.server.world.ServerWorld world = server.getOverworld();
net.minecraft.util.math.BlockPos origin = new net.minecraft.util.math.BlockPos(0, 200, 0);
world.getChunk(origin);
net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData definition =
        world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.STRUCTURE_MANAGER_DATA)
                .stream().filter(data -> !data.getNpcSpawnNest().isEmpty()).findFirst().orElseThrow();
net.minecraft.util.Identifier nestId = definition.getNpcSpawnNest().get(0).getId();
net.minecraft.util.Identifier npcId = world.getRegistryManager().get(net.jukoz.me.registries.DynamicRegistriesME.NPC_TYPE).getIds().iterator().next();
net.jukoz.me.entity.npcs.NpcEntity a = net.jukoz.me.entity.EntitiesME.NPC.create(world);
net.jukoz.me.entity.npcs.NpcEntity b = net.jukoz.me.entity.EntitiesME.NPC.create(world);
for (net.jukoz.me.entity.npcs.NpcEntity npc : java.util.List.of(a, b)) {
    npc.prepareNpcIdentifier(npcId);
    npc.prepare();
    npc.setAiDisabled(true);
    npc.refreshPositionAndAngles(0.5, 201, 0.5, 0, 0);
    world.spawnEntity(npc);
}
net.minecraft.util.math.BlockPos first = origin.add(2, 0, 0);
net.minecraft.util.math.BlockPos second = origin.add(4, 0, 0);
net.minecraft.block.BlockState bed = net.minecraft.block.Blocks.RED_BED.getDefaultState()
        .with(net.minecraft.block.BedBlock.PART, net.minecraft.block.enums.BedPart.HEAD);
world.setBlockState(first, bed, 2);
world.setBlockState(second, bed, 2);
try {
    net.jukoz.me.block.special.structureManager.features.SpawnNestManager one =
            new net.jukoz.me.block.special.structureManager.features.SpawnNestManager(nestId, java.util.List.of(a.getUuid()), 0, 100, origin, 3);
    net.jukoz.me.block.special.structureManager.features.SpawnNestManager two =
            new net.jukoz.me.block.special.structureManager.features.SpawnNestManager(nestId, java.util.List.of(b.getUuid()), 0, 100, origin.add(1, 0, 0), 3);
    net.minecraft.nbt.NbtCompound data = new net.minecraft.nbt.NbtCompound();
    data.putString("structure_manager.Identifier", definition.getId().toString());
    data.put("structure_manager.Nests", net.jukoz.me.block.special.structureManager.features.StructureNestList.CODEC
            .encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, new net.jukoz.me.block.special.structureManager.features.StructureNestList(java.util.List.of(one, two)))
            .result().orElseThrow());
    net.minecraft.block.BlockState state = net.minecraft.registry.Registries.BLOCK.get(new net.minecraft.util.Identifier("me", "structure_manager")).getDefaultState();
    net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity manager =
            new net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity(origin, state);
    manager.setWorld(world);
    manager.readNbt(data);
    manager.redistributeBeds();
    net.minecraft.util.math.BlockPos aBed = a.getBrain().getOptionalMemory(net.jukoz.me.entity.ai.brain.MemoryModulesME.ASSIGNED_BED_POS).orElseThrow();
    net.minecraft.util.math.BlockPos bBed = b.getBrain().getOptionalMemory(net.jukoz.me.entity.ai.brain.MemoryModulesME.ASSIGNED_BED_POS).orElseThrow();
    if (aBed.equals(bBed)) throw new AssertionError("Two NPCs share a bed");
    manager.readNbt(manager.createNbt());
    manager.redistributeBeds();
    if (!aBed.equals(a.getBrain().getOptionalMemory(net.jukoz.me.entity.ai.brain.MemoryModulesME.ASSIGNED_BED_POS).orElseThrow()))
        throw new AssertionError("Assignment changed after reload");
    world.setBlockState(aBed, net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
    manager.redistributeBeds();
    if (a.getBrain().getOptionalMemory(net.jukoz.me.entity.ai.brain.MemoryModulesME.ASSIGNED_BED_POS).isPresent())
        throw new AssertionError("Broken bed retained");
    if (!bBed.equals(b.getBrain().getOptionalMemory(net.jukoz.me.entity.ai.brain.MemoryModulesME.ASSIGNED_BED_POS).orElseThrow()))
        throw new AssertionError("Unrelated resident lost its bed");
    java.nio.file.Files.writeString(java.nio.file.Path.of("../build/beds-smoke.json"), "{\"passed\":true,\"checks\":[\"deduplication\",\"NBT reload\",\"broken bed removal\",\"stable resident assignment\"]}");
    return "PASS: bed discovery, deduplication, persistence and redistribution";
} finally {
    a.discard();
    b.discard();
    world.setBlockState(first, net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
    world.setBlockState(second, net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
}
