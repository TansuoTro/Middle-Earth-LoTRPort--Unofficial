package net.jukoz.me.resources.datas.biome_events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.biomevents.BiomeEventRegistry;
import net.jukoz.me.world.dimension.ModDimensions;

import java.util.*;


public class BiomeEventDataLookup {
    public static HashMap<EntityType<?>, Set<UUID>> entities = new HashMap<>();

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForBiome(Level world, Holder<Biome> biome, NpcEntity entity) {
        ResourceLocation biomeEventId = biome.getKey().map(k -> k.getValue()).orElse(new ResourceLocation("minecraft", "plains"));
        BiomeEventData eventData = world.getRegistryManager().get(DynamicRegistriesME.BIOME_EVENT).get(biomeEventId);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.getRegistryManager().get(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.getRegistryManager().get(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForStructure(Level world, ResourceLocation structure, NpcEntity entity) {
        BiomeEventData eventData = world.getRegistryManager().get(DynamicRegistriesME.STRUCTURE_EVENT).get(structure);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.getRegistryManager().get(DynamicRegistriesME.STRUCTURE_EVENT).get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.getRegistryManager().get(DynamicRegistriesME.STRUCTURE_EVENT).get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static boolean canEntitySpawn(ServerLevel world, Holder<Biome> biome, BlockPos pos, EntityType<?> type, RandomSource random) {
        Holder.Reference<BiomeEventData> dataRef = world.getRegistryManager().get(DynamicRegistriesME.BIOME_EVENT).getEntry(net.minecraft.resources.ResourceKey.of(DynamicRegistriesME.BIOME_EVENT, biome.getKey().map(k -> k.getValue()).orElse(new ResourceLocation("minecraft", "plains")))).orElse(null);
        if(dataRef == null)
            return true;
        BiomeEventData data = dataRef.value();
        boolean canSpawn = data.canSpawn(type, world, pos, random);

        if(!canSpawn)
            return false;
        if(type.getSpawnGroup() != SpawnGroup.MONSTER)
            return true;

        if(!entities.containsKey(type)){
            entities.put(type, new HashSet<>());
        }
        return canSpawn(type);
    }

    public static boolean canSpawn(EntityType<?> type) {
        return entities.getOrDefault(type, Collections.emptySet()).size() < ModServerConfigs.GLOBAL_MOB_CAP;
    }

    public static void addEntity(LivingEntity entity){
        if (!entity.getWorld().getRegistryKey().equals(ModDimensions.ME_WORLD_KEY)) {
            return;
        }

        EntityType<?> type = entity.getType();
        UUID uuid = entity.getUuid();
        if(type.getSpawnGroup() != SpawnGroup.MONSTER)
            return;
        if(entity instanceof Mob mobEntity && mobEntity.isPersistent()){
            return;
        }
        entities.computeIfAbsent(type, t -> new HashSet<>()).add(uuid);
    }

    public static void removeEntity(EntityType<?> type, UUID uuid){
        if(type.getSpawnGroup() != SpawnGroup.MONSTER) return;
        Set<UUID> set = entities.get(type);
        if (set == null) return;
        set.remove(uuid);
        if (set.isEmpty()) entities.remove(type);
    }
}
