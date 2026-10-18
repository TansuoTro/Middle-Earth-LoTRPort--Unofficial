package net.jukoz.me.entity.spider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
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
public record SpiderVariant(SpiderAssetInfo assetInfo) {

    public static final Codec<SpiderVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    SpiderAssetInfo.CODEC.fieldOf("assets").forGetter(SpiderVariant::assetInfo)
            ).apply(instance, SpiderVariant::new)
    );

    public static final PacketCodec<PacketByteBuf, RegistryEntry<SpiderVariant>> PACKET_CODEC =
            PacketCodecs.registryEntry(DynamicRegistriesME.SPIDER_VARIANTS);

    public record AssetInfo(Identifier texturePath) {
        public static final Codec<AssetInfo> CODEC =
                Identifier.CODEC.xmap(AssetInfo::new, AssetInfo::texturePath);
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
