package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;

import java.util.ArrayList;
import java.util.List;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_force_onboarding_screen");
    public static final PacketType<PacketForceOnboardingScreen> TYPE = PacketType.create(ID, PacketForceOnboardingScreen::new);
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
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isCreative())
            delay = 0;

        PlayerEntity player = context.player();
        var controller = new OnboardingFactionScreenController(player.getWorld(), delay, AttributePoolElement.obtainAttributeList(attributeList));
        controller.open();
    }

    public PacketForceOnboardingScreen(PacketByteBuf buf) {
        this(buf.readFloat(), buf.readNbt());
    }

    @Override
    public PacketType<PacketForceOnboardingScreen> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.delayOnTeleportationConfirm);
        buf.writeNbt(this.attributeList);
    }


}
