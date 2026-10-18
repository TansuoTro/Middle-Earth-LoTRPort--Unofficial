package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreen;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.world.dimension.ModDimensions;

import java.util.List;

public class InscriptionEnchantInfoPacket extends ServerToClientPacket<InscriptionEnchantInfoPacket> {
    public static final Identifier ID = MiddleEarth.of("inscription_enchant_info_packet");
    public static final PacketType<InscriptionEnchantInfoPacket> TYPE = PacketType.create(ID, InscriptionEnchantInfoPacket::new);

    private final String enchant;
    private final int level;
    private final int maxLevel;
    private final byte[] words;

    public InscriptionEnchantInfoPacket(String enchant, int level, int maxLevel, byte[] words) {
        this.enchant = enchant;
        this.level = level;
        this.maxLevel = maxLevel;
        this.words = words;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        InscriptionTableScreen screen = (InscriptionTableScreen)client.currentScreen;
        if (screen != null) screen.updateInfo(this.enchant, this.level, this.maxLevel);

        try{
            InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().currentScreenHandler;
            screenHandler.updateAvailableWords(words);
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }

    public InscriptionEnchantInfoPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readInt(), buf.readInt(), buf.readByteArray());
    }

    @Override
    public PacketType<InscriptionEnchantInfoPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(this.enchant);
        buf.writeInt(this.level);
        buf.writeInt(this.maxLevel);
        buf.writeByteArray(this.words);
    }


}
