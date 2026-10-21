package net.jukoz.me.network.packets.C2S;

import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class PacketStructureManagerUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureManagerUpdateBlockEntityRequest>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "structure_manager_update_block_entity_request");

    public static final PacketType<PacketStructureManagerUpdateBlockEntityRequest> TYPE = PacketType.create(ID, PacketStructureManagerUpdateBlockEntityRequest::new);
    private final BlockPos pos;
    private final ResourceLocation structureManagerId;
    private final boolean toInitialize;
    private final boolean isActive;

    public PacketStructureManagerUpdateBlockEntityRequest(BlockPos pos, ResourceLocation structureManagerId, boolean toInitialize, boolean isActive) {
        this.pos = pos;
        this.structureManagerId = structureManagerId;
        this.toInitialize = toInitialize;
        this.isActive = isActive;
    }



    public PacketStructureManagerUpdateBlockEntityRequest(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readIdentifier(), buf.readBoolean(), buf.readBoolean());
    }

    @Override
    public PacketType<PacketStructureManagerUpdateBlockEntityRequest> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeIdentifier(this.structureManagerId);
        buf.writeBoolean(this.toInitialize);
        buf.writeBoolean(this.isActive);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.updateData(structureManagerId, isActive, toInitialize);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}