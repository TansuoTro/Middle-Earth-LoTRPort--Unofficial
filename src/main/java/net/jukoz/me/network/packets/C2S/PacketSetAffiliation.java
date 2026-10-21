package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.StarlightPhialItem;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;

public class PacketSetAffiliation extends ClientToServerPacket<PacketSetAffiliation>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_set_affiliation");

    public static final PacketType<PacketSetAffiliation> TYPE = PacketType.create(ID, PacketSetAffiliation::new);

    private final String dispositionName;
    private final String factionName;
    private final String spawnName;


    public PacketSetAffiliation(String dispositionName, String factionName, String spawnName){
        this.dispositionName = dispositionName;
        this.factionName = factionName;
        this.spawnName = spawnName;
    }



    public PacketSetAffiliation(FriendlyByteBuf buf) {
        this(buf.readString(), buf.readString(), buf.readString());
    }

    @Override
    public PacketType<PacketSetAffiliation> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeString(this.dispositionName);
        buf.writeString(this.factionName);
        buf.writeString(this.spawnName);
    }

    @Override
    public void process(ServerPacketContext context) {
        MinecraftServer server = context.player().getServer();
        server.execute(() -> {
            try{
                ResourceLocation factionId = new ResourceLocation(factionName);
                Faction faction = FactionLookup.getFactionById(context.player().getWorld(), factionId);
                ResourceLocation spawnId = new ResourceLocation(spawnName);
                FactionUtil.updateFaction(context.player(), faction, spawnId);
                if(!context.player().isCreative() && context.player().getMainHandStack().getItem() instanceof StarlightPhialItem)
                    context.player().getStackInHand(InteractionHand.MAIN_HAND).decrement(1);
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("AffiliationPacket::Tried getting affiliation packet and couldn't fetch any.", e);
            }
        });
    }
}