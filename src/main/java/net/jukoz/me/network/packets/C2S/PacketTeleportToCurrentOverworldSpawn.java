package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.item.items.StarlightPhialItem;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;


public class PacketTeleportToCurrentOverworldSpawn extends ClientToServerPacket<PacketTeleportToCurrentOverworldSpawn> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_teleport_to_current_overworld_spawn");
    public static final PacketTeleportToCurrentOverworldSpawn INSTANCE = new PacketTeleportToCurrentOverworldSpawn();
    public static final PacketType<PacketTeleportToCurrentOverworldSpawn> TYPE = PacketType.create(ID, PacketTeleportToCurrentOverworldSpawn::new);





    private PacketTeleportToCurrentOverworldSpawn() {
    }

    public PacketTeleportToCurrentOverworldSpawn(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public PacketType<PacketTeleportToCurrentOverworldSpawn> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                RaceUtil.reset(context.player());

                if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
                    ModDimensions.teleportPlayerToOverworld(context.player());
                    RaceUtil.reset(context.player());
                    if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                        RaceUtil.initializeRace(context.player());
                    } else {
                        RaceUtil.reset(context.player());
                    }

                    if(!context.player().isCreative() && context.player().getMainHandStack().getItem() instanceof StarlightPhialItem)
                        context.player().getStackInHand(InteractionHand.MAIN_HAND).decrement(1);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketTeleportToCurrentOverworldSpawn::Apply - Tried applying the return to overworld packet",e);
        }
    }
}