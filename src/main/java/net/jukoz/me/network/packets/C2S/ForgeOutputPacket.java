package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ForgeOutputPacket extends ClientToServerPacket<ForgeOutputPacket> {
    public static final ResourceLocation ID = MiddleEarth.of("forge_output_packet");
    public static final PacketType<ForgeOutputPacket> TYPE = PacketType.create(ID, ForgeOutputPacket::new);

    public int getAmount() {
        return amount;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getMode() {
        return mode;
    }

    private final int amount;
    private final double x;
    private final double y;
    private final double z;
    private final int mode;

    public ForgeOutputPacket(int amount, double x, double y, double z, int mode) {
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mode = mode;
    }



    public ForgeOutputPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
    }

    @Override
    public PacketType<ForgeOutputPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.amount);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeInt(this.mode);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                Vec3 coordinates = new Vec3(x, y, z);
                ForgeBlockEntity.outputItemStack(amount, coordinates, context.player(), mode);
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketForgeOutput error: ", e);
        }
    }
}
