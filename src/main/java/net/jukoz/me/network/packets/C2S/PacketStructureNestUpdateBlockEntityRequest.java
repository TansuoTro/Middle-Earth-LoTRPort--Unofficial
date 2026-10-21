package net.jukoz.me.network.packets.C2S;

import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.structureManager.nest.StructureNestBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

import java.util.Optional;

public class PacketStructureNestUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureNestUpdateBlockEntityRequest>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "structure_nest_update_block_entity_request");

    public static final PacketType<PacketStructureNestUpdateBlockEntityRequest> TYPE = PacketType.create(ID, PacketStructureNestUpdateBlockEntityRequest::new);

    private Optional<ResourceLocation> getStructureManagerId() {
        return Optional.ofNullable(structureManagerId);
    }

    private Optional<ResourceLocation> getStructureNestId() {
        return Optional.ofNullable(structureNestId);
    }

    private final BlockPos pos;
    private final ResourceLocation structureManagerId;
    private final ResourceLocation structureNestId;
    private final int spawnRadius;
    private final boolean isEnabled;

    public PacketStructureNestUpdateBlockEntityRequest(BlockPos pos, Optional<ResourceLocation> structureManagerId, Optional<ResourceLocation> structureNestId, int spawnRadius, boolean isEnabled) {
        this.pos = pos;
        this.spawnRadius = spawnRadius;
        this.isEnabled = isEnabled;

        if(structureManagerId.isPresent())
            this.structureManagerId = structureManagerId.get();
        else
            this.structureManagerId = null;

        if(structureNestId.isPresent())
            this.structureNestId = structureNestId.get();
        else
            this.structureNestId = null;
    }



    public PacketStructureNestUpdateBlockEntityRequest(FriendlyByteBuf buf) {
        this(buf.readBlockPos(),
                Optional.ofNullable(buf.readNullable(FriendlyByteBuf::readIdentifier)),
                Optional.ofNullable(buf.readNullable(FriendlyByteBuf::readIdentifier)),
                buf.readInt(), buf.readBoolean());
    }

    @Override
    public PacketType<PacketStructureNestUpdateBlockEntityRequest> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeNullable(this.structureManagerId, FriendlyByteBuf::writeIdentifier);
        buf.writeNullable(this.structureNestId, FriendlyByteBuf::writeIdentifier);
        buf.writeInt(this.spawnRadius);
        buf.writeBoolean(this.isEnabled);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureNestBlockEntity blockEntity){
                    blockEntity.setStructureManagerId(structureManagerId);
                    blockEntity.setStructureNestId(structureNestId);
                    blockEntity.setSpawnRadius(spawnRadius);
                    blockEntity.setIsEnabled(isEnabled);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureNestUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}