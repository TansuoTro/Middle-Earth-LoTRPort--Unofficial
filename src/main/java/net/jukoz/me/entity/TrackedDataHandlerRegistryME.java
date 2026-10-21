package net.jukoz.me.entity;

import net.jukoz.me.compat.net.TrackedDataHandlerME;
import net.minecraft.entity.data.TrackedDataHandler;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.great_horn.GreatHornVariant;
import net.jukoz.me.entity.npcs.data.NpcData;
import net.jukoz.me.entity.npcs.data.NpcInitializationData;
import net.jukoz.me.entity.npcs.data.NpcTextureData;
import net.jukoz.me.entity.spider.SpiderVariant;

public class TrackedDataHandlerRegistryME {
    public static final TrackedDataHandler<NpcData> NPC_DATA;
    public static final TrackedDataHandler<NpcInitializationData> NPC_INITIALIZATION_DATA;
    public static final TrackedDataHandler<NpcTextureData> NPC_TEXTURE_DATA;


    public static final TrackedDataHandler<Long> INITIALIZATION_TICK;
    public static final TrackedDataHandler<String> FACTION_ID;
    public static final TrackedDataHandler<String> NPC_DATA_ID;
    public static final TrackedDataHandler<String> CATEGORY;
    public static final TrackedDataHandler<BlockPos> STRUCTURE_MANAGER_HOST_POS;
    public static final TrackedDataHandler<Holder<SpiderVariant>> SPIDER_VARIANT;
    public static final TrackedDataHandler<Holder<GreatHornVariant>> GREAT_HORN_VARIANT;

    public static void register() {
        of("npc_data", NPC_DATA);
        of("npc_initialization_data", NPC_INITIALIZATION_DATA);
        of("npc_texture_data", NPC_TEXTURE_DATA);
        of("initialization_tick", INITIALIZATION_TICK);
        of("faction_id", FACTION_ID);
        of("npc_data_id", NPC_DATA_ID);
        of("category", CATEGORY);
        of("structure_manager_host_pos", STRUCTURE_MANAGER_HOST_POS);
        of("spider_variant", SPIDER_VARIANT);
        of("great_horn_variant", GREAT_HORN_VARIANT);
    }

    private static void of(String name, TrackedDataHandler<?> dataHandler) {
        net.minecraft.entity.data.TrackedDataHandlerRegistry.register(dataHandler);
    }

    static {
        NPC_INITIALIZATION_DATA = TrackedDataHandlerME.create(NpcInitializationData.PACKET_CODEC);
        NPC_DATA = TrackedDataHandlerME.create(NpcData.PACKET_CODEC);
        INITIALIZATION_TICK = TrackedDataHandlerME.create(PacketCodecs.LONG);
        FACTION_ID = TrackedDataHandlerME.create(PacketCodecs.STRING);
        NPC_DATA_ID = TrackedDataHandlerME.create(PacketCodecs.STRING);
        CATEGORY = TrackedDataHandlerME.create(PacketCodecs.STRING);
        NPC_TEXTURE_DATA = TrackedDataHandlerME.create(NpcTextureData.PACKET_CODEC);
        STRUCTURE_MANAGER_HOST_POS = TrackedDataHandlerME.create(PacketCodecs.BLOCK_POS);
        SPIDER_VARIANT = TrackedDataHandlerME.create(SpiderVariant.PACKET_CODEC);
        GREAT_HORN_VARIANT = TrackedDataHandlerME.create(GreatHornVariant.PACKET_CODEC);
    }
}
