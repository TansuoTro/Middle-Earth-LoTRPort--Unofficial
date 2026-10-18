package net.jukoz.me.gui.structuremanager;

import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructureManagerScreenData{
    private BlockPos pos;
    private Identifier structureManagerIdentifier;
    private boolean isActive;
    private boolean toInitialize;

    public static final PacketCodec<? super PacketByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public Identifier getStructureManagerIdentifier() {
        return this.structureManagerIdentifier;
    }
    private Optional<Identifier> getStructureManagerIdentifierOptional() {
        return Optional.ofNullable(this.structureManagerIdentifier);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public boolean getToInitialize() {
        return this.toInitialize;
    }

    public void setStructureManagerIdentifier(Identifier structureManagerIdentifier) {
        this.structureManagerIdentifier = structureManagerIdentifier;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setToInitialize(boolean toInitialize) {
        this.toInitialize = toInitialize;
    }

    public StructureManagerScreenData(BlockPos pos, boolean isActive, boolean toInitialize, Optional<Identifier> structureManagerId){
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
