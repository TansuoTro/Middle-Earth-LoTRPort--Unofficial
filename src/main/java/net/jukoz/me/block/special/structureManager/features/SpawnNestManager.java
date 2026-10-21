package net.jukoz.me.block.special.structureManager.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Uuids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.*;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(Uuids.CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius),
            Codec.unboundedMap(Codec.STRING, BlockPos.CODEC).optionalFieldOf("bed_assignments", Map.of())
                    .forGetter(SpawnNestManager::getBedAssignments)
    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private ResourceLocation id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;

    private final Map<String, BlockPos> bedAssignments;

    public SpawnNestManager(ResourceLocation dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius) {
        this(dataId, dataEntities, dataRespawnEventTriggerTick, dataRespawnTickDelay, position, spawnRadius, Map.of());
    }

    public SpawnNestManager(ResourceLocation dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius, Map<String, BlockPos> beds) {
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
        this.bedAssignments = new LinkedHashMap<>(beds);
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        this.respawnEventTriggerTick = 0;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
        this.bedAssignments = new LinkedHashMap<>();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public ArrayList<UUID> getEntityUuids() {
        return entities;
    }

    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public BlockPos getOriginPos() {
        return this.originPos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }

    public void addEntity(LivingEntity entity){
        if(entity.getWorld().isClient)
            return;

        UUID uuid = entity.getUuid();
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        this.entities.add(uuid);
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.getWorld().isClient || !this.entities.contains(entity.getUuid()))
            return false;
        this.entities.remove(entity.getUuid());
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.getWorld());
        }
        return true;
    }
    public void removeEntity(Level world, UUID uuid){
        if(world.isClient || !this.entities.contains(uuid))
            return;
        this.entities.remove(uuid);
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
    }

    private void beginRespawnSequence(Level world) {
        this.respawnEventTriggerTick = world.getTime();
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, ServerLevel world, BlockPos sourcePos) {
        if(canRespawn(currentTick)){
            respawnAll(structureManagerData, world, sourcePos);
        }
    }

    public void doWellnessCheck(StructureManagerData structureManagerData, Level world, BlockPos sourcePos) {
        if(entities != null && !entities.isEmpty()){
            List<UUID> toRemove = new ArrayList<>();
            for (UUID uuid : entities){ // Wellness check
                var entity = world instanceof net.minecraft.server.level.ServerLevel sw ? sw.getEntity(uuid) : null;
                // An unloaded entity is not dead. Removing it here duplicates residents on respawn.
                if(entity != null && !entity.isAlive())
                    toRemove.add(uuid);
            }
            for (UUID uuid : toRemove)
                removeEntity(world, uuid);
        }
    }


    private void respawnAll(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        if(structureManagerData == null)
            return;
        SpawnNestNodeData data = structureManagerData.getNpcSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            int entityAmountToSpawn = pool.getEntityAmount();
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = StructureManagerService.spawnEntity(world, pool, originPos, spawnRadius);
                if(entityToAdd instanceof NpcEntity npcEntity){
                    npcEntity.assignStructureManager((StructureManagerBlockEntity) world.getBlockEntity(structureManagerPos));
                }
                if(entityToAdd != null)
                    addEntity(entityToAdd);
            }
            world.markDirty(structureManagerPos);
        }
        this.respawnEventTriggerTick = -1;
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            return true;
        }
        return false;
    }

    public void forceRespawn(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        for(var uuid : getEntityUuids()){
            if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                livingEntity.setRemoved(Entity.RemovalReason.DISCARDED);
                MiddleEarth.LOGGER.logDebugMsg("Removed %s".formatted(uuid));
            }
        }
        entities = new ArrayList<UUID>();

        respawnAll(structureManagerData, world, structureManagerPos);
        if (world.getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity manager) {
            manager.redistributeBeds();
        }
    }

    public Map<String, BlockPos> getBedAssignments() {
        return Collections.unmodifiableMap(bedAssignments);
    }

    /** Reserve existing valid assignments before any nest allocates another resident's bed. */
    public void retainBeds(List<BlockPos> candidates, Set<BlockPos> claimed, ServerLevel world) {
        Set<String> residents = new HashSet<>();
        for (UUID uuid : entities) residents.add(uuid.toString());
        bedAssignments.entrySet().removeIf(entry -> !residents.contains(entry.getKey())
                || (world.isChunkLoaded(entry.getValue()) && !candidates.contains(entry.getValue()))
                || !claimed.add(entry.getValue()));
    }

    public void assignBeds(List<BlockPos> candidates, Set<BlockPos> claimed, ServerLevel world) {
        for (UUID uuid : entities) {
            if (!(world.getEntity(uuid) instanceof NpcEntity npc)) continue;
            String key = uuid.toString();
            BlockPos bed = bedAssignments.get(key);
            if (bed == null) {
                for (BlockPos candidate : candidates) {
                    if (world.getBlockState(candidate).get(BedBlock.OCCUPIED) || !claimed.add(candidate)) continue;
                    bed = candidate;
                    bedAssignments.put(key, bed);
                    break;
                }
            }
            if (bed == null) {
                npc.getBrain().forget(MemoryModulesME.ASSIGNED_BED_POS);
            } else {
                npc.getBrain().remember(MemoryModulesME.ASSIGNED_BED_POS, bed);
            }
        }
    }
}

