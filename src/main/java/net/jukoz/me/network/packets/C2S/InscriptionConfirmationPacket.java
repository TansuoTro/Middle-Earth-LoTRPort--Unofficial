package net.jukoz.me.network.packets.C2S;

import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class InscriptionConfirmationPacket extends ClientToServerPacket<InscriptionConfirmationPacket> {
    public static final ResourceLocation ID = MiddleEarth.of("inscription_confirmation_packet");
    public static final InscriptionConfirmationPacket INSTANCE = new InscriptionConfirmationPacket();
    public static final PacketType<InscriptionConfirmationPacket> TYPE = PacketType.create(ID, InscriptionConfirmationPacket::new);

    public InscriptionConfirmationPacket() {
    }



    public InscriptionConfirmationPacket(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public PacketType<InscriptionConfirmationPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
