package net.jukoz.me.registries;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.great_horn.GreatHornVariant;
import net.jukoz.me.entity.spider.SpiderVariant;
import net.jukoz.me.registries.content.biomevents.BiomeEventRegistry;
import net.jukoz.me.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.jukoz.me.registries.content.texturepresets.TexturePresetsRegistry;
import net.jukoz.me.registries.content.factions.FactionRegistry;
import net.jukoz.me.registries.content.npctypes.NpcRegistry;
import net.jukoz.me.registries.content.races.RaceRegistry;
import net.jukoz.me.registries.content.spidervariants.SpiderVariantRegistry;
import net.jukoz.me.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import net.jukoz.me.resources.datas.texture_presets.TexturePresetDataPool;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.texture_presets.CharacterTextureMaterial;
import net.jukoz.me.resources.datas.texture_presets.CharacterTexturePattern;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData;

public class DynamicRegistriesME extends net.jukoz.me.api.registries.DynamicRegistries {
    public static final RegistryKey<Registry<Race>> RACE = RegistryKey.ofRegistry(MiddleEarth.of("race"));
    public static final RegistryKey<Registry<Faction>> FACTION = RegistryKey.ofRegistry(MiddleEarth.of("faction"));
    public static final RegistryKey<Registry<NpcType>> NPC_TYPE = RegistryKey.ofRegistry(MiddleEarth.of("npc_type"));
    public static final Codec<RegistryEntry<NpcType>> NPC_TYPE_CODEC = RegistryFixedCodec.of(DynamicRegistriesME.NPC_TYPE);
    public static final RegistryKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = RegistryKey.ofRegistry(MiddleEarth.of("structure_manager_data"));
    public static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT = RegistryKey.ofRegistry(MiddleEarth.of("biome_event"));
    public static final RegistryKey<Registry<BiomeEventData>> STRUCTURE_EVENT = RegistryKey.ofRegistry(MiddleEarth.of("structure_event"));

    public static final RegistryKey<Registry<TexturePresetDataPool>> TEXTURE_PRESETS = RegistryKey.ofRegistry(MiddleEarth.of( "texture_presets"));

    public static final RegistryKey<Registry<CharacterTextureMaterial>> SKIN_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.of("skin_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> EYE_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.of("eye_material"));
    public static final RegistryKey<Registry<CharacterTextureMaterial>> HAIR_MATERIAL = RegistryKey.ofRegistry(MiddleEarth.of("hair_material"));

    public static final RegistryKey<Registry<CharacterTexturePattern>> SKIN_PATTERN = RegistryKey.ofRegistry(MiddleEarth.of("skin_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> EYE_PATTERN = RegistryKey.ofRegistry(MiddleEarth.of("eye_pattern"));
    public static final RegistryKey<Registry<CharacterTexturePattern>> HAIR_PATTERN = RegistryKey.ofRegistry(MiddleEarth.of("hair_pattern"));

    public static final RegistryKey<Registry<SpiderVariant>> SPIDER_VARIANTS = RegistryKey.ofRegistry(MiddleEarth.of("spider_variants"));
    public static final RegistryKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS = RegistryKey.ofRegistry(MiddleEarth.of("great_horn_variants"));

    public static void register() {
        net.jukoz.me.entity.EntityAttributesME.register();
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTING.register(
                server -> net.jukoz.me.entity.EntityAttributesME.registerPlayerDefaults());
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTED.register(
                net.jukoz.me.compat.net.PacketCodecs::setServer);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STOPPED.register(
                server -> net.jukoz.me.compat.net.PacketCodecs.setServer(null));
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC_TYPE, NpcType.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
        DynamicRegistries.registerSynced(TEXTURE_PRESETS, TexturePresetDataPool.CODEC);

        DynamicRegistries.registerSynced(SKIN_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_PATTERN, CharacterTexturePattern.CODEC);

        DynamicRegistries.registerSynced(SKIN_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_MATERIAL, CharacterTextureMaterial.CODEC);

        DynamicRegistries.registerSynced(SPIDER_VARIANTS, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(GREAT_HORN_VARIANTS, GreatHornVariant.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RACE, RaceRegistry::bootstrap);
        registryBuilder.addRegistry(NPC_TYPE, NpcRegistry::bootstrap);
        registryBuilder.addRegistry(FACTION, FactionRegistry::bootstrap);
        registryBuilder.addRegistry(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.addRegistry(STRUCTURE_EVENT, BiomeEventRegistry::bootstrapStructureEvents);
        registryBuilder.addRegistry(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.addRegistry(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);

        registryBuilder.addRegistry(SPIDER_VARIANTS, SpiderVariantRegistry::bootstrap);
        registryBuilder.addRegistry(GREAT_HORN_VARIANTS, GreatHornVariantRegistry::bootstrap);

        // Character appearance: skin/eye/hair materials and patterns. Without these the six
        // registries stay empty and the NPC generator can only use the presets' inline materials.
        registryBuilder.addRegistry(SKIN_MATERIAL, net.jukoz.me.registries.CharacterMaterialsRegistryME::bootstrapSkins);
        registryBuilder.addRegistry(EYE_MATERIAL, net.jukoz.me.registries.CharacterMaterialsRegistryME::bootstrapEyes);
        registryBuilder.addRegistry(HAIR_MATERIAL, net.jukoz.me.registries.CharacterMaterialsRegistryME::bootstrapHairs);
        registryBuilder.addRegistry(SKIN_PATTERN, net.jukoz.me.registries.CharacterPatternsRegistryME::bootstrapSkins);
        registryBuilder.addRegistry(EYE_PATTERN, net.jukoz.me.registries.CharacterPatternsRegistryME::bootstrapEyes);
        registryBuilder.addRegistry(HAIR_PATTERN, net.jukoz.me.registries.CharacterPatternsRegistryME::bootstrapHairs);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
    }
}
