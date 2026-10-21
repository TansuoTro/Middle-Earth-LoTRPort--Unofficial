package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.network.packets.S2C.PacketOnboardingResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_onboarding_request");
    public static final PacketOnboardingRequest INSTANCE = new PacketOnboardingRequest();
    public static final PacketType<PacketOnboardingRequest> TYPE = PacketType.create(ID, PacketOnboardingRequest::new);



    private PacketOnboardingRequest() {
    }

    public PacketOnboardingRequest(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public PacketType<PacketOnboardingRequest> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayer player = context.player();

                PacketOnboardingResult newPacket = new PacketOnboardingResult(
                        PlayerDataService.playerPassedOnboarding(context.player()),
                        ModServerConfigs.ENABLE_FACTION_RESET,
                        ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD,
                        ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION,
                        AttributePoolElement.createAttributeNbtListFromPlayer(player)
                );
                ServerPlayNetworking.send(player, newPacket);
            });
        } catch(Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }
}
