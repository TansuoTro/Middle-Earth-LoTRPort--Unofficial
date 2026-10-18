package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;

import java.util.ArrayList;
import java.util.List;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Id<PacketForceOnboardingScreen> ID = new Id<>(new Identifier(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketCodecs.NBT_COMPOUND, p -> p.attributeList,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;
    private final NbtCompound attributeList;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, NbtCompound attributeList) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, PlayerEntity player) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    public Id<PacketForceOnboardingScreen> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isInCreativeMode())
            delay = 0;

        PlayerEntity player = context.player();
        var controller = new OnboardingFactionScreenController(player.getWorld(), delay, AttributePoolElement.obtainAttributeList(attributeList));
        controller.open();
    }
}
