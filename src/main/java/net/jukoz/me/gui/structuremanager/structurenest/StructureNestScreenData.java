package net.jukoz.me.gui.structuremanager.structurenest;

import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import java.util.Optional;

public class StructureNestScreenData {
    private BlockPos pos;
    private ResourceLocation structureManagerId;
    private ResourceLocation structureNestId;
    private int spawnRadius;
    private boolean isEnabled;

    public static final PacketCodec<? super FriendlyByteBuf, StructureNestScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }
    public boolean getIsEnabled() {
        return this.isEnabled;
    }
    public ResourceLocation getStructureManagerId() {
        return this.structureManagerId;
    }
    public ResourceLocation getStructureNestId() {
        return this.structureNestId;
    }
    private Optional<ResourceLocation> getStructureManagerIdOptional() {
        return Optional.ofNullable(this.structureManagerId);
    }
    private Optional<ResourceLocation> getStructureNestIdOptional() {
        return Optional.ofNullable(this.structureNestId);
    }

    public void setStructureManagerId(ResourceLocation structureManagerId) {
        this.structureManagerId = structureManagerId;
    }
    public void setStructureNestId(ResourceLocation structureNestId) {
        this.structureNestId = structureNestId;
    }

    public StructureNestScreenData(BlockPos pos, Optional<ResourceLocation> structureManagerId, Optional<ResourceLocation> structureNestId, int spawnRadius, boolean isEnabled){
        this.pos = pos;
        structureManagerId.ifPresentOrElse(x -> setStructureManagerId(x), () -> setStructureManagerId(null));
        structureNestId.ifPresentOrElse(x -> setStructureNestId(x), () -> setStructureNestId(null));
        this.spawnRadius = spawnRadius;
        this.isEnabled = isEnabled;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.BLOCK_POS, StructureNestScreenData::getPos,
                PacketCodecs.optional(PacketCodecs.IDENTIFIER), StructureNestScreenData::getStructureManagerIdOptional,
                PacketCodecs.optional(PacketCodecs.IDENTIFIER), StructureNestScreenData::getStructureNestIdOptional,
                PacketCodecs.INTEGER, StructureNestScreenData::getSpawnRadius,
                PacketCodecs.BOOLEAN, StructureNestScreenData::getIsEnabled,
                StructureNestScreenData::new
        );
    }

    public void toggleActiveState() {
        this.isEnabled = !this.isEnabled;
    }
}
