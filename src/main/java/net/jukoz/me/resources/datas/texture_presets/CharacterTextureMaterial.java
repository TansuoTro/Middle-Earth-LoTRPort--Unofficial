package net.jukoz.me.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.CharacterMaterialTypes;

public class CharacterTextureMaterial {

    public static final PacketCodec<FriendlyByteBuf, CharacterTextureMaterial> PACKET_CODEC;
    public static final Codec<Holder<CharacterTextureMaterial>> ENTRY_CODEC;
    public static final PacketCodec<FriendlyByteBuf, Holder<CharacterTextureMaterial>> ENTRY_PACKET_CODEC;

    public static final Codec<CharacterTextureMaterial> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("asset_id").forGetter(CharacterTextureMaterial::getIdentifier),
                Codec.STRING.fieldOf("type").forGetter(CharacterTextureMaterial::getTypeValue))
                .apply(instance, CharacterTextureMaterial::new);
    });
    private final ResourceLocation assetId;
    private final CharacterMaterialTypes type;

    public CharacterTextureMaterial(ResourceLocation assetId, String type){
        this.assetId = assetId;
        this.type = CharacterMaterialTypes.valueOf(type.toUpperCase());
    }
    public CharacterTextureMaterial(ResourceLocation id, CharacterMaterialTypes type){
        this.assetId = id;
        this.type = type;
    }
    public ResourceLocation getIdentifier() {
        return assetId;
    }

    public CharacterMaterialTypes getType(){
        return this.type;
    }

    public String getTypeValue() {
        return this.type.name().toUpperCase();
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.IDENTIFIER, CharacterTextureMaterial::getIdentifier,
                PacketCodecs.STRING, CharacterTextureMaterial::getTypeValue, CharacterTextureMaterial::new);
        ENTRY_CODEC = RegistryElementCodec.of(DynamicRegistriesME.SKIN_MATERIAL, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.SKIN_MATERIAL, PACKET_CODEC);
    }
}
