package net.jukoz.me.entity.beasts.great_horn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.jukoz.me.registries.DynamicRegistriesME;

/**
 * 1.20.1 port of the 1.21.5 data-driven GreatHornVariant.
 * The donor carried a {@code SpawnConditionSelectors} field (net.minecraft.entity.spawn,
 * 1.21.5+ API); biome-driven variant choice is now done directly at initialize time
 * (see GreatHornEntity), so this record only carries the asset texture.
 */
public record GreatHornVariant(GreatHornAssetInfo assetInfo, net.jukoz.me.compat.spawn.SpawnConditionSelectors spawnConditions) {

    public static final Codec<GreatHornVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    GreatHornAssetInfo.CODEC.fieldOf("assets").forGetter(GreatHornVariant::assetInfo),
                    net.jukoz.me.compat.spawn.SpawnConditionSelectors.CODEC.optionalFieldOf("spawn_conditions",
                            net.jukoz.me.compat.spawn.SpawnConditionSelectors.createFallback(0)).forGetter(GreatHornVariant::spawnConditions)
            ).apply(instance, GreatHornVariant::new)
    );

    public static final PacketCodec<FriendlyByteBuf, Holder<GreatHornVariant>> PACKET_CODEC =
            PacketCodecs.registryEntry(DynamicRegistriesME.GREAT_HORN_VARIANTS);

    public record AssetInfo(ResourceLocation texturePath) {
        public static final Codec<AssetInfo> CODEC =
                ResourceLocation.CODEC.xmap(AssetInfo::new, AssetInfo::texturePath);
    }

    public record GreatHornAssetInfo(AssetInfo id) {
        public static final Codec<GreatHornAssetInfo> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        AssetInfo.CODEC.fieldOf("id").forGetter(GreatHornAssetInfo::id)
                ).apply(instance, GreatHornAssetInfo::new)
        );
    }
}
