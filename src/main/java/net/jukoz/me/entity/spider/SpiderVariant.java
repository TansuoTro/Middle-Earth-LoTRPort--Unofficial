package net.jukoz.me.entity.spider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.jukoz.me.registries.DynamicRegistriesME;

/**
 * 1.20.1 port of the 1.21.5 data-driven SpiderVariant.
 * The donor carried a {@code SpawnConditionSelectors} field (net.minecraft.entity.spawn,
 * 1.21.5+ API); biome-driven variant choice is now done directly at initialize time
 * (see SpawnOfShelobEntity / ShelobiteScuttlerEntity / ShelobiteLarvaEntity), so this
 * record only carries the asset textures.
 */
public record SpiderVariant(SpiderAssetInfo assetInfo, net.jukoz.me.compat.spawn.SpawnConditionSelectors spawnConditions) {

    public static final Codec<SpiderVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    SpiderAssetInfo.CODEC.fieldOf("assets").forGetter(SpiderVariant::assetInfo),
                    net.jukoz.me.compat.spawn.SpawnConditionSelectors.CODEC.optionalFieldOf("spawn_conditions",
                            net.jukoz.me.compat.spawn.SpawnConditionSelectors.createFallback(0)).forGetter(SpiderVariant::spawnConditions)
            ).apply(instance, SpiderVariant::new)
    );

    public static final PacketCodec<FriendlyByteBuf, Holder<SpiderVariant>> PACKET_CODEC =
            PacketCodecs.registryEntry(DynamicRegistriesME.SPIDER_VARIANTS);

    public record AssetInfo(ResourceLocation texturePath) {
        public static final Codec<AssetInfo> CODEC =
                ResourceLocation.CODEC.xmap(AssetInfo::new, AssetInfo::texturePath);
    }

    public record SpiderAssetInfo(AssetInfo larva, AssetInfo scuttler, AssetInfo spawnOfShelob) {
        public static final Codec<SpiderAssetInfo> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        AssetInfo.CODEC.fieldOf("larva").forGetter(SpiderAssetInfo::larva),
                        AssetInfo.CODEC.fieldOf("scuttler").forGetter(SpiderAssetInfo::scuttler),
                        AssetInfo.CODEC.fieldOf("spawn").forGetter(SpiderAssetInfo::spawnOfShelob)
                ).apply(instance, SpiderAssetInfo::new)
        );
    }
}
