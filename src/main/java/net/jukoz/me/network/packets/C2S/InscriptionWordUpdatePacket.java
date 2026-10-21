package net.jukoz.me.network.packets.C2S;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class InscriptionWordUpdatePacket extends ClientToServerPacket<InscriptionWordUpdatePacket> {
    public static final ResourceLocation ID = MiddleEarth.of("inscription_word_update_packet");
    public static final PacketType<InscriptionWordUpdatePacket> TYPE = PacketType.create(ID, InscriptionWordUpdatePacket::new);

    private final boolean add;
    private final String word;

    public InscriptionWordUpdatePacket(boolean add, String word) {
        this.add = add;
        this.word = word;
    }

    public InscriptionWordUpdatePacket(FriendlyByteBuf buf) {
        this(buf.readBoolean(), buf.readUtf());
    }

    @Override
    public PacketType<InscriptionWordUpdatePacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.add);
        buf.writeString(this.word);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().currentScreenHandler;
                screenHandler.updateWords(this.add, this.word, false);
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }
}
