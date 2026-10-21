package net.jukoz.me.network.packets.S2C;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import java.util.ArrayList;
import java.util.List;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_force_onboarding_screen");
    public static final PacketType<PacketForceOnboardingScreen> TYPE = PacketType.create(ID, PacketForceOnboardingScreen::new);
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, CompoundTag attributeList) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, Player player) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isCreative())
            delay = 0;

        Player player = context.player();
        var controller = new OnboardingFactionScreenController(player.getWorld(), delay, AttributePoolElement.obtainAttributeList(attributeList));
        controller.open();
    }

    public PacketForceOnboardingScreen(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readNbt());
    }

    @Override
    public PacketType<PacketForceOnboardingScreen> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.delayOnTeleportationConfirm);
        buf.writeNbt(this.attributeList);
    }

}
