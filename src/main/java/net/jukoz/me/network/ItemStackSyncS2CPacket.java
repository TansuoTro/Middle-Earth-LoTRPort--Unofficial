package net.jukoz.me.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.jukoz.me.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.core.BlockPos;

public class ItemStackSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               FriendlyByteBuf buf, PacketSender responseSender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos position = buf.readBlockPos();

        if(client.world.getBlockEntity(position) instanceof AlloyFurnaceEntity blockEntity) {
            blockEntity.setInventory(list);
        } else if(client.world.getBlockEntity(position) instanceof WoodPileBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }
}
