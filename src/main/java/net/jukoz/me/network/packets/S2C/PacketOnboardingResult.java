package net.jukoz.me.network.packets.S2C;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_onboarding_result");
    public static final PacketType<PacketOnboardingResult> TYPE = PacketType.create(ID, PacketOnboardingResult::new);

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm, CompoundTag attributeList) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm,  Player player) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isCreative())
            delay = 0;
        if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
            if(!canReturnToOverworld){
                return;
            }
            Minecraft client = Minecraft.getInstance();
            client.setScreen(new ReturnConfirmationScreen(delay));
        } else if(ModDimensions.isInOverworld(context.player().getWorld())){
            OnboardingScreenHandler.handle(context, havePlayerData, delay, AttributePoolElement.obtainAttributeList(attributeList));
        }
    }

    public PacketOnboardingResult(FriendlyByteBuf buf) {
        this(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readFloat(), buf.readNbt());
    }

    @Override
    public PacketType<PacketOnboardingResult> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.havePlayerData);
        buf.writeBoolean(this.canChangeFaction);
        buf.writeBoolean(this.canReturnToOverworld);
        buf.writeFloat(this.delayOnTeleportationConfirm);
        buf.writeNbt(this.attributeList);
    }

}
