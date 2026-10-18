package net.jukoz.me.network.packets.C2S;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.jukoz.me.item.items.armor.HelmetAttachmentItem;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class HoodStateTogglePacket extends ClientToServerPacket<HoodStateTogglePacket> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "hood_state_toggle_packet");
    public static final HoodStateTogglePacket INSTANCE = new HoodStateTogglePacket();
    public static final PacketType<HoodStateTogglePacket> TYPE = PacketType.create(ID, HoodStateTogglePacket::new);

    public HoodStateTogglePacket() {
    }



    public HoodStateTogglePacket(PacketByteBuf buf) {
        this();
    }

    @Override
    public PacketType<HoodStateTogglePacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                PlayerEntity player = context.player();

                ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
                if(helmet != null){
                    if(helmet.getItem() instanceof HelmetAttachmentItem)
                        HelmetAttachmentItem.toggleHelmetAttachmentState(context.player(), helmet);
                    else if(helmet.getItem() instanceof CustomHelmetItem)
                        CustomHelmetItem.toggleHoodState(context.player(), helmet);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("HoodStatePacket error: ", e);
        }
    }
}
