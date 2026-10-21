package net.jukoz.me.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.EntityCategories;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.util.RandomSource;

public class NpcInitializationData {
    public static final Codec<NpcInitializationData> CODEC;
    public static final PacketCodec<FriendlyByteBuf, NpcInitializationData> PACKET_CODEC;

    private ResourceLocation type;
    private Boolean isRandom;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("Type").forGetter(NpcInitializationData::getOptionalType),
                Codec.BOOL.optionalFieldOf("IsRandom").forGetter(NpcInitializationData::getOptionalRandom)
        ).apply(instance, NpcInitializationData::new));

        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.optional(PacketCodecs.STRING), NpcInitializationData::getOptionalType,
                PacketCodecs.optional(PacketCodecs.BOOLEAN), NpcInitializationData::getOptionalRandom,
                NpcInitializationData::new);
    }

    public NpcInitializationData(Optional<String> type, Optional<Boolean> isRandom) {
        this.type = type.map(MiddleEarth::fetchId).orElse(null);
        this.isRandom = isRandom == null || isRandom.isEmpty() || isRandom.get() == false ? null : isRandom.get();
    }

    public NpcInitializationData(ResourceLocation type, Boolean isRandom) {
        this.type = type;
        this.isRandom = isRandom;
    }

    public NpcInitializationData() {
        this.type = null;
        this.isRandom = null;
    }

    public NpcInitializationData withType(ResourceLocation newType) {
        return new NpcInitializationData(newType, this.isRandom);
    }

    private Optional<String> getOptionalType() {
        return type != null ? Optional.of(type.toString()) : Optional.empty();
    }

    private Optional<Boolean> getOptionalRandom() {
        return isRandom != null ? Optional.of(isRandom) : Optional.empty();
    }

    public boolean tryToInitialize(NpcEntity entity) {
        Level world = entity.level();
        if(world == null)
            return false;

        if(isRandom != null && isRandom){
            Registry<NpcType> registry = world.registryAccess().get(DynamicRegistriesME.NPC_TYPE);
            List<ResourceKey<NpcType>> entryList =  registry.getKeys().stream().toList();
            RandomSource random = new RandomSource();
            ResourceKey<NpcType> randomKey = entryList.get(random.nextInt(entryList.size()));
            this.type = randomKey.getValue();
            this.isRandom = false;
        }

        if(type != null){
            Registry<NpcType> registry = world.registryAccess().get(DynamicRegistriesME.NPC_TYPE);
            NpcType npcType = registry.get(type);
            if(npcType == null)
                return false;
            Holder<NpcType> entry = registry.getEntry(npcType);
            this.type = null;
            EntityCategories category = null;
            if(!npcType.hasCategory(world, category))
                category = npcType.getNpcTextureData(world).getRandomCategory();

            entity.saveCategory(category);
            entity.saveNpcType(entry);

            return true;
        }

        return false;
    }
}
