package net.jukoz.me.network.packets.C2S;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.structureManager.nest.StructureNestBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

import java.util.Optional;

public class PacketStructureNestUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureNestUpdateBlockEntityRequest>
{
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "structure_nest_update_block_entity_request");

    public static final PacketType<PacketStructureNestUpdateBlockEntityRequest> TYPE = PacketType.create(ID, PacketStructureNestUpdateBlockEntityRequest::new);

    private Optional<Identifier> getStructureManagerId() {
        return Optional.ofNullable(structureManagerId);
    }

    private Optional<Identifier> getStructureNestId() {
        return Optional.ofNullable(structureNestId);
    }

    private final BlockPos pos;
    private final Identifier structureManagerId;
    private final Identifier structureNestId;
    private final int spawnRadius;
    private final boolean isEnabled;

    public PacketStructureNestUpdateBlockEntityRequest(BlockPos pos, Optional<Identifier> structureManagerId, Optional<Identifier> structureNestId, int spawnRadius, boolean isEnabled) {
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



    public PacketStructureNestUpdateBlockEntityRequest(PacketByteBuf buf) {
        this(buf.readBlockPos(),
                Optional.ofNullable(buf.readNullable(PacketByteBuf::readIdentifier)),
                Optional.ofNullable(buf.readNullable(PacketByteBuf::readIdentifier)),
                buf.readInt(), buf.readBoolean());
    }

    @Override
    public PacketType<PacketStructureNestUpdateBlockEntityRequest> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeNullable(this.structureManagerId, PacketByteBuf::writeIdentifier);
        buf.writeNullable(this.structureNestId, PacketByteBuf::writeIdentifier);
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