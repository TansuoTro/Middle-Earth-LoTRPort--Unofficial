package net.jukoz.me.network.packets.C2S;

import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class InscriptionConfirmationPacket extends ClientToServerPacket<InscriptionConfirmationPacket> {
    public static final Identifier ID = MiddleEarth.of("inscription_confirmation_packet");
    public static final InscriptionConfirmationPacket INSTANCE = new InscriptionConfirmationPacket();
    public static final PacketType<InscriptionConfirmationPacket> TYPE = PacketType.create(ID, InscriptionConfirmationPacket::new);

    public InscriptionConfirmationPacket() {
    }



    public InscriptionConfirmationPacket(PacketByteBuf buf) {
        this();
    }

    @Override
    public PacketType<InscriptionConfirmationPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().currentScreenHandler;
                screenHandler.enchantItem();
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionConfirmation error: ", e);
        }
    }
}
