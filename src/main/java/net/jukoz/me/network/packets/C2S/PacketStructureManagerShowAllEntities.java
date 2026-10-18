package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class PacketStructureManagerShowAllEntities extends ClientToServerPacket<PacketStructureManagerShowAllEntities> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "structure_manager_show_all_entities");
    public static final PacketType<PacketStructureManagerShowAllEntities> TYPE = PacketType.create(ID, PacketStructureManagerShowAllEntities::new);

    private final BlockPos pos;

    public PacketStructureManagerShowAllEntities(BlockPos pos) {
        this.pos = pos;
    }

    public PacketStructureManagerShowAllEntities(PacketByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public PacketType<PacketStructureManagerShowAllEntities> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(!context.player().hasPermissionLevel(2))
                    return;
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.showAllEntities();
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerShowAllEntities::Tried to show all entities.", e);
        }
    }
}
