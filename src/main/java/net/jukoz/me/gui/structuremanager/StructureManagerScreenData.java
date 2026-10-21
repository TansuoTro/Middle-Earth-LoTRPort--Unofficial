package net.jukoz.me.gui.structuremanager;

import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import java.util.Optional;

public class StructureManagerScreenData{
    private BlockPos pos;
    private ResourceLocation structureManagerIdentifier;
    private boolean isActive;
    private boolean toInitialize;

    public static final PacketCodec<? super FriendlyByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public ResourceLocation getStructureManagerIdentifier() {
        return this.structureManagerIdentifier;
    }
    private Optional<ResourceLocation> getStructureManagerIdentifierOptional() {
        return Optional.ofNullable(this.structureManagerIdentifier);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public boolean getToInitialize() {
        return this.toInitialize;
    }

    public void setStructureManagerIdentifier(ResourceLocation structureManagerIdentifier) {
        this.structureManagerIdentifier = structureManagerIdentifier;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setToInitialize(boolean toInitialize) {
        this.toInitialize = toInitialize;
    }

    public StructureManagerScreenData(BlockPos pos, boolean isActive, boolean toInitialize, Optional<ResourceLocation> structureManagerId){
        this.pos = pos;
        setActive(isActive);
        setToInitialize(toInitialize);
        structureManagerId.ifPresentOrElse(this::setStructureManagerIdentifier, () -> setStructureManagerIdentifier(null));
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.BLOCK_POS, StructureManagerScreenData::getPos,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getIsActive,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getToInitialize,
                PacketCodecs.optional(PacketCodecs.IDENTIFIER), StructureManagerScreenData::getStructureManagerIdentifierOptional,
                StructureManagerScreenData::new
        );
    }
}
