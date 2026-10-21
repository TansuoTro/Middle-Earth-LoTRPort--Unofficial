package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.api.utils.IdentifierUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;

public class PacketSetRace extends ClientToServerPacket<PacketSetRace>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_set_race");

    public static final PacketType<PacketSetRace> TYPE = PacketType.create(ID, PacketSetRace::new);

    private final String race;

    public PacketSetRace(String race){
        this.race = race;
    }

    public PacketSetRace(FriendlyByteBuf buf) {
        this(buf.readUtf());
    }

    @Override
    public PacketType<PacketSetRace> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeString(this.race);
    }

    @Override
    public void process(ServerPacketContext context) {
        MinecraftServer server = context.player().getServer();
        server.execute(() -> {
            try{
                RaceUtil.updateRace(context.player(), RaceLookup.getRace(context.player().getWorld(), MiddleEarth.fetchId(race)), true);
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("PacketSetRace::Tried setting race for player.", e);
            }
        });
    }
}
