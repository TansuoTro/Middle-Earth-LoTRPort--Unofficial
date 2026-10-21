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

public class PacketStructureManagerRespawnEntities extends ClientToServerPacket<PacketStructureManagerRespawnEntities> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "structure_manager_respawn_entities");

    public static final PacketType<PacketStructureManagerRespawnEntities> TYPE = PacketType.create(ID, PacketStructureManagerRespawnEntities::new);

    private final BlockPos pos;

    public PacketStructureManagerRespawnEntities(BlockPos pos) {
        this.pos = pos;
    }



    public PacketStructureManagerRespawnEntities(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public PacketType<PacketStructureManagerRespawnEntities> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.respawnAllEntities();
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerRespawnEntities::Tried to reset all entities.", e);
        }
    }
}
