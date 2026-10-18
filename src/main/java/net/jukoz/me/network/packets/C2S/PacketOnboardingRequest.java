package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.network.packets.S2C.PacketOnboardingResult;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final Id<PacketOnboardingRequest> ID = new Id<>(new Identifier(MiddleEarth.MOD_ID, "packet_onboarding_request"));
    public static final PacketOnboardingRequest INSTANCE = new PacketOnboardingRequest();
    public static final PacketCodec<RegistryByteBuf, PacketOnboardingRequest> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<PacketOnboardingRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOnboardingRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();

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
