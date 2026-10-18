package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
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
    public static final Id<InscriptionEnchantInfoPacket> ID = new Id<>(MiddleEarth.of("inscription_enchant_info_packet"));
    public static final PacketCodec<RegistryByteBuf, InscriptionEnchantInfoPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.enchant,
            PacketCodecs.INTEGER, p -> p.level,
            PacketCodecs.INTEGER, p -> p.maxLevel,
            PacketCodecs.BYTE_ARRAY, p -> p.words,
            InscriptionEnchantInfoPacket::new
    );

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
    public Id<InscriptionEnchantInfoPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, InscriptionEnchantInfoPacket> streamCodec() {
        return CODEC;
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
}
