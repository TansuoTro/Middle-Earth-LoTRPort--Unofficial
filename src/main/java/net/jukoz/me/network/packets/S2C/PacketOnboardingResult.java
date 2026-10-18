package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.handlers.OnboardingScreenHandler;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_onboarding_result");
    public static final PacketType<PacketOnboardingResult> TYPE = PacketType.create(ID, PacketOnboardingResult::new);

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;
    private final NbtCompound attributeList;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm, NbtCompound attributeList) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm,  PlayerEntity player) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isCreative())
            delay = 0;
        if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
            if(!canReturnToOverworld){
                return;
            }
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new ReturnConfirmationScreen(delay));
        } else if(ModDimensions.isInOverworld(context.player().getWorld())){
            OnboardingScreenHandler.handle(context, havePlayerData, delay, AttributePoolElement.obtainAttributeList(attributeList));
        }
    }

    public PacketOnboardingResult(PacketByteBuf buf) {
        this(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readFloat(), buf.readNbt());
    }

    @Override
    public PacketType<PacketOnboardingResult> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.havePlayerData);
        buf.writeBoolean(this.canChangeFaction);
        buf.writeBoolean(this.canReturnToOverworld);
        buf.writeFloat(this.delayOnTeleportationConfirm);
        buf.writeNbt(this.attributeList);
    }


}
