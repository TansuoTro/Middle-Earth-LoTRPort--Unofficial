package net.jukoz.me.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.chunk.Chunk;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.biome_events.data.SpawnEventDataUtil;
import net.jukoz.me.resources.datas.biome_events.data.WildSpawnEventData;
import net.jukoz.me.resources.datas.npc_types.NpcType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BiomeEventData {
    public static class Fields {
        public static final String SPAWN_DEFAULT_WHEN_UNMET = "SpawnDefaultWhenUnmet";
        public static final String WILD_SPAWNS = "WildSpawns";
    }

    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf(Fields.SPAWN_DEFAULT_WHEN_UNMET).forGetter(BiomeEventData::getSpawnDefaultWhenUnmet),
            Codec.list(WildSpawnEventData.CODEC).fieldOf(Fields.WILD_SPAWNS).forGetter(BiomeEventData::getWildSpawnEventDatas)
    ).apply(instance, BiomeEventData::new));

    private Boolean shouldSpawnDefaultWhenUnmet;
    private List<WildSpawnEventData> wildSpawnEventDatas;

    private static HashMap<ChunkPos, Long> disabledChunkSpawningCache = new HashMap<>();

    public BiomeEventData(boolean shouldSpawnDefaultWhenUnmet, List<WildSpawnEventData> wildNpcs){
        this(wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public BiomeEventData withMoreWildSpawns(List<WildSpawnEventData> wildNpcs){

        List<WildSpawnEventData> newWildSpawns = new ArrayList<>();
        if(wildSpawnEventDatas != null)
            newWildSpawns.addAll(wildSpawnEventDatas);
        newWildSpawns.addAll(wildNpcs);
        this.wildSpawnEventDatas = newWildSpawns;
        return this;
    }



    private List<WildSpawnEventData> getWildSpawnEventDatas() {
        return this.wildSpawnEventDatas;
    }

    public BiomeEventData(List<WildSpawnEventData> wildNpcs){
        this.wildSpawnEventDatas = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public Boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public ContextualizedBiomeData findNpcData(Level world, NpcEntity entity) {
        List<WildSpawnEventData> weightedData = new ArrayList<>();
        DynamicRegistryManager manager = world.getRegistryManager();

        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            if(SpawnEventDataUtil.isConsideredForSpawning(data, Registries.ENTITY_TYPE.getId(EntitiesME.NPC), world, entity.getBlockPos()))
            {
                int weightAmount = data.getWeight(1);
                for(int i = 0; i < weightAmount; i ++){
                    weightedData.add(data);
                }
            }
        }
        if(weightedData.isEmpty())
            return null;

        WildSpawnEventData spawningData = weightedData.get(RandomSource.create().nextInt(weightedData.size()));
        Registry<NpcType> npcDataRegistry = manager.get(DynamicRegistriesME.NPC_TYPE);
        ResourceLocation npcId = spawningData.getNpcType(null);
        NpcType foundNpcType = (npcId != null) ? npcDataRegistry.get(npcId) : null;

        return new ContextualizedBiomeData(foundNpcType);
    }

    public boolean canSpawn(EntityType<?> type, ServerLevel world, BlockPos pos, RandomSource random) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();

        Long expiredTick = disabledChunkSpawningCache.get(chunkPos);

        if (expiredTick != null) {
            if(world.getTime() >= expiredTick)
                disabledChunkSpawningCache.remove(chunkPos);
            else
                return false;
        }

        List<WildSpawnEventData> weightedData = new ArrayList<>();
        boolean containEntityType = false;
        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            ResourceLocation entityId = Registries.ENTITY_TYPE.getId(type);
            if(!containEntityType && MiddleEarth.compareId(data.getEntityType(), entityId))
                containEntityType = true;
            if(SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, pos))
            {
                int weightAmount = data.getWeight(1);
                for(int i = 0; i < weightAmount; i ++){
                    weightedData.add(data);
                }
            }
        }
        if(!containEntityType)
            return true;
        if(weightedData.isEmpty()){
            disableChunk(world, chunkPos);
            return false;
        }
        WildSpawnEventData spawningData = weightedData.get(RandomSource.create().nextInt(weightedData.size()));
        if(spawningData.isDiscarded(random)){
            disableChunk(world, chunkPos);
            return false;
        }
        spawningData.broadcastMessage(world, pos);
        return true;
    }

    private void disableChunk(ServerLevel world, ChunkPos chunkPos) {
        if(disabledChunkSpawningCache == null)
            disabledChunkSpawningCache = new HashMap<>();
        disabledChunkSpawningCache.remove(chunkPos);
        disabledChunkSpawningCache.put(chunkPos, world.getTime() + 20 * 5);
    }


    public record ContextualizedBiomeData(NpcType npcType){

    }
}
