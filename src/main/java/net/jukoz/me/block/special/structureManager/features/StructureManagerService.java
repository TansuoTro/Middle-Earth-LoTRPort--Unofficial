package net.jukoz.me.block.special.structureManager.features;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.npcs.initializer.NpcEntityBuilder;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StructureManagerService {
    private static final Map<ResourceKey<Level>, Set<BlockPos>> STRUCTURE_MANAGERS = new HashMap<>();

    public static StructureManagerData getStructureManagerData(Level world, ResourceLocation structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity spawnEntity(ServerLevel world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
        var random = world.getRandom();
        int chances = 5;
        BlockPos chosenBlockPos = null;
        for(BlockPos blockPos : BlockPos.iterateRandomly(random, chances, pos, spawnRadius)){
            blockPos = blockPos.withY(pos.getY());
            if(world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.up()).isAir()){
                chosenBlockPos = blockPos;
                break;
            }
        }
        if(chosenBlockPos == null)
            chosenBlockPos = pos;

        LivingEntity entity;
        if(pool.getEntityType() == EntitiesME.NPC){

            entity = new NpcEntityBuilder(world, chosenBlockPos)
                    .withNpcType(pool.getNpcIdentifier().get())
                    .build();
        } else {
            entity = (LivingEntity) pool.getEntityType().create(world);
            if(entity instanceof Mob mob)
                mob.initialize(world, world.getLocalDifficulty(pos), MobSpawnType.STRUCTURE, null, null);
            entity.setPosition(chosenBlockPos.toCenterPos());
        }

        if(entity != null){
            world.spawnEntity(entity);
            return entity;
        }
        return null;
    }

    public static void register(StructureManagerBlockEntity manager) {
        Level world = manager.getWorld();
        if (world == null || world.isClient())
            return;
        STRUCTURE_MANAGERS.computeIfAbsent(world.getRegistryKey(), key -> new HashSet<>())
                          .add(manager.getPos().toImmutable());
    }

    public static void unregister(StructureManagerBlockEntity manager) {
        Level world = manager.getWorld();
        if (world == null || world.isClient())
            return;
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());
        if (managers == null)
            return;

        managers.remove(manager.getPos());
        if (managers.isEmpty())
            STRUCTURE_MANAGERS.remove(world.getRegistryKey());
    }

    public static boolean isClose(Level world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());

        if (managers == null) {
            return false;
        }

        int radiusSquared = radius * radius;

        for (BlockPos managerPos : managers) {
            if (managerPos.getSquaredDistance(pos) <= radiusSquared) {
                return true;
            }
        }

        return false;
    }

    public static StructureManagerBlockEntity getClosest(Level world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());

        if (managers == null) {
            return null;
        }

        int radiusSquared = radius * radius;
        double closestDistance = Double.MAX_VALUE;
        StructureManagerBlockEntity closest = null;

        for (BlockPos managerPos : managers) {
            double distance = managerPos.getSquaredDistance(pos);

            if (distance > radiusSquared || distance >= closestDistance) {
                continue;
            }

            BlockEntity blockEntity = world.getBlockEntity(managerPos);

            if (blockEntity instanceof StructureManagerBlockEntity structureManager) {
                closestDistance = distance;
                closest = structureManager;
            }
        }

        return closest;
    }
}
