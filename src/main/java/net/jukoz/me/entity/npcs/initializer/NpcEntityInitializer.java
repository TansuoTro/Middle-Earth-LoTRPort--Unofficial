package net.jukoz.me.entity.npcs.initializer;

import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import net.jukoz.me.resources.datas.biome_events.BiomeEventDataLookup;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;

public class NpcEntityInitializer {
    public static void initializeNpcEntity(ServerLevel serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerLevel serverWorld, NpcEntity npcEntity){
        ResourceLocation npcTypeId = npcEntity.getNpcTypeIdentifier();
        if(!NpcEntityInitializerUtil.characterIdentifierExist(serverWorld, npcTypeId))
        {
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = null;
            try{
                contextualizedBiomeData = findContextualizedNpcData(serverWorld, npcEntity);
                if(contextualizedBiomeData == null)
                {
                    npcEntity.discard();
                    return;
                }
                NpcType npcType = contextualizedBiomeData.npcType();
                npcTypeId = npcType.getId();
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("NpcEntity initializer failed : ", e);
                npcEntity.discard();
                return;
            }
        }

        npcEntity.prepareNpcIdentifier(npcTypeId);
        npcEntity.prepare();
        NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
        npcEntity.updateTargetGoals();
    }

    private static BiomeEventData.ContextualizedBiomeData findContextualizedNpcData(ServerLevel world, NpcEntity npcEntity)  {
        BlockPos blockPos = npcEntity.getBlockPos();
        Holder<Biome> biome = world.getBiome(blockPos);
        Registry<Structure> structureRegistry = world.registryAccess().get(Registries.STRUCTURE);
        List<StructureStart> structureStarts = world.getStructureAccessor().getStructureStarts(world.getChunk(blockPos).getPos(), s -> true);

        for (StructureStart structureStart : structureStarts) {
            Structure structure = structureStart.getStructure();
            ResourceLocation structureId = structureRegistry.getId(structure);
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForStructure(world, structureId, npcEntity);
            if(contextualizedBiomeData != null){
                return contextualizedBiomeData;
            }
        }

        return BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
    }

    public static boolean shouldInitialize(ServerLevel serverWorld, NpcEntity npcEntity){
        ResourceLocation currentNpcDataId = npcEntity.getNpcTypeIdentifier();
        if(currentNpcDataId == null)
            return true;

        RegistryAccess registryManager = serverWorld.registryAccess();
        Optional<Holder.Reference<NpcType>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC_TYPE).get().getEntry(net.minecraft.resources.ResourceKey.create(DynamicRegistriesME.NPC_TYPE, currentNpcDataId));

        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }
}
