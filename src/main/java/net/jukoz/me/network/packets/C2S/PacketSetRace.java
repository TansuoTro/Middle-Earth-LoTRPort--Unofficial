package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.api.utils.IdentifierUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class PacketSetRace extends ClientToServerPacket<PacketSetRace>
{
    public static final Id<PacketSetRace> ID = new Id<>(new Identifier(MiddleEarth.MOD_ID, "packet_set_race"));

    public static final PacketCodec<RegistryByteBuf, PacketSetRace> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.race,
            PacketSetRace::new
    );

    private final String race;


    public PacketSetRace(String race){
        this.race = race;
    }

    @Override
    public Id<PacketSetRace> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSetRace> streamCodec() {
        return CODEC;
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