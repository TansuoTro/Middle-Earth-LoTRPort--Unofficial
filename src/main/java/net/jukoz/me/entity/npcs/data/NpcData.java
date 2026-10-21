package net.jukoz.me.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.EntityCategories;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import java.util.Optional;

public class NpcData {
    public static final Codec<NpcData> CODEC;
    public static final PacketCodec<FriendlyByteBuf, NpcData> PACKET_CODEC;

    private Holder<NpcType> type;
    private EntityCategories category;
    private final MobSpawnType spawnReason;
    private final Optional<BlockPos> structureManagerPos;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                DynamicRegistriesME.NPC_TYPE_CODEC.optionalFieldOf("Type").forGetter(NpcData::getOptionalType),
                Codec.STRING.optionalFieldOf("Category").forGetter(NpcData::getOptionalCategory),
                Codec.STRING.optionalFieldOf("MobSpawnType").forGetter(NpcData::getOptionalSpawnReason),
                BlockPos.CODEC.optionalFieldOf("StructureManagerPos").forGetter((data) -> data.structureManagerPos)
        ).apply(instance, NpcData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.registryEntry(DynamicRegistriesME.NPC_TYPE)), NpcData::getOptionalType,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalCategory,
                PacketCodecs.optional(PacketCodecs.STRING), NpcData::getOptionalSpawnReason,
                PacketCodecs.optional(PacketCodecs.BLOCK_POS), NpcData::getOptionalStructureManagerPos,
                NpcData::new);
    }

    public NpcData(Optional<Holder<NpcType>> type, Optional<String> category, Optional<String> spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type.orElse(null);
        this.category = category.map(value -> parseEnum(EntityCategories.class, value)).orElse(null);
        this.spawnReason = spawnReason.map(value -> parseEnum(MobSpawnType.class, value)).orElse(null);
        this.structureManagerPos = structureManagerPos == null ||  structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData(Holder<NpcType> type, EntityCategories category, MobSpawnType spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type;
        this.category = category;
        this.spawnReason = spawnReason;
        this.structureManagerPos = structureManagerPos == null || structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData() {
        this.type = null;
        this.category = null;
        this.spawnReason = null;
        this.structureManagerPos = Optional.empty();
    }

    // [BUILDERS]
    public NpcData withType(Holder<NpcType> type) {
        return new NpcData(type, category, spawnReason, structureManagerPos);
    }

    public NpcData withCategory(EntityCategories category) {
        return new NpcData(this.type, category, this.spawnReason, this.structureManagerPos);
    }

    public NpcData withSpawnReason(MobSpawnType spawnReason) {
        return new NpcData(this.type, this.category, spawnReason, this.structureManagerPos);
    }

    public NpcData withStructureManagerPos(BlockPos structureManagerPos) {
        return new NpcData(this.type, this.category, this.spawnReason, Optional.of(structureManagerPos));
    }
    public NpcData withType(Level world, ResourceLocation npcDataIdentifier) {
        if(npcDataIdentifier == null || world == null)
            return this;

        Registry<NpcType> registry = world.getRegistryManager().get(DynamicRegistriesME.NPC_TYPE);
        NpcType npcType = registry.get(npcDataIdentifier);
        if(npcType == null)
            return this;
        return this.withType(registry.getEntry(npcType));
    }

    public ResourceLocation getNpcTypeId() {
        if(type == null){
            return null;
        }
        return type.getKey()
                .map(ResourceKey::getValue)
                .orElse(null);
    }

    public ResourceLocation getFaction() {
        if(type == null)
            return null;
        return type.value().getFactionIdentifier();
    }

    public EntityCategories getCategory() {
        return category;
    }

    public MobSpawnType getSpawnReason() {
        return spawnReason;
    }

    public NpcType getNpcType() {
        if(type == null)
            return null;
        return type.value();
    }

    private Optional<Holder<NpcType>> getOptionalType() {
        return type != null ? Optional.of(type) : Optional.empty();
    }

    private Optional<String> getOptionalCategory() {
        return category != null ? Optional.of(category.name()) : Optional.empty();
    }
    private Optional<String> getOptionalSpawnReason() {
        return spawnReason != null ? Optional.of(spawnReason.name()) : Optional.empty();
    }

    private Optional<BlockPos> getOptionalStructureManagerPos() {
        return structureManagerPos;
    }

    public BlockPos getStructureManagerPos() {
        return structureManagerPos.orElse(null);
    }

    private static <E extends Enum<E>> E parseEnum(Class<E> type, String value) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
