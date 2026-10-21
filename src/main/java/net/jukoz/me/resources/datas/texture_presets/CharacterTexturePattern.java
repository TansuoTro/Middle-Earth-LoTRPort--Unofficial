package net.jukoz.me.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.CharacterPatternTypes;
import java.util.Optional;

public class CharacterTexturePattern {

    public static final Codec<CharacterTexturePattern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(CharacterTexturePattern::getIdentifier),
            Codec.STRING.fieldOf("category").forGetter(CharacterTexturePattern::getCategoryString),
            Codec.BOOL.optionalFieldOf("has_addon").forGetter(CharacterTexturePattern::hasAddonOptional))
            .apply(instance, CharacterTexturePattern::new));

    public static final PacketCodec<FriendlyByteBuf, CharacterTexturePattern> PACKET_CODEC;
    public static final Codec<Holder<CharacterTexturePattern>> ENTRY_CODEC;
    public static final PacketCodec<FriendlyByteBuf, Holder<CharacterTexturePattern>> ENTRY_PACKET_CODEC;

    private final ResourceLocation assetId;
    private final CharacterPatternTypes patternType;

    private Boolean hasAddon;
    public CharacterTexturePattern(ResourceLocation assetId, String type, Optional<Boolean> hasAddon){
        this.assetId = assetId;
        this.patternType = CharacterPatternTypes.valueOf(type);
        this.hasAddon = hasAddon.orElse(false);
    }

    public CharacterTexturePattern(ResourceLocation assetId, String type, Boolean hasAddon){
        this.assetId = assetId;
        this.patternType = CharacterPatternTypes.valueOf(type.toUpperCase());
        this.hasAddon = hasAddon;
    }

    public CharacterTexturePattern(ResourceLocation id, CharacterPatternTypes type, Boolean hasAddon){
        this(id, type);
        this.hasAddon = hasAddon;
    }
    public CharacterTexturePattern(ResourceLocation id, CharacterPatternTypes type){
        this.assetId = id;
        this.patternType = type;
    }
    public ResourceLocation getIdentifier() {
        return assetId;
    }

    public CharacterPatternTypes getPatternType(){
        return this.patternType;
    }

    public String getCategoryString() {
        return this.patternType.name().toUpperCase();
    }

    private Optional<Boolean> hasAddonOptional() {
        if(hasAddon == null || !hasAddon)
            return Optional.empty();
        return Optional.of(true);
    }
    public Boolean hasAddonRawValue() {
        return hasAddon;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.IDENTIFIER,
                CharacterTexturePattern::getIdentifier,
                PacketCodecs.STRING,
                CharacterTexturePattern::getCategoryString,
                PacketCodecs.BOOLEAN,
                CharacterTexturePattern::hasAddonRawValue,
                CharacterTexturePattern::new);
        ENTRY_CODEC = RegistryFileCodec.of(DynamicRegistriesME.SKIN_PATTERN, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.SKIN_PATTERN, PACKET_CODEC);
    }
}
