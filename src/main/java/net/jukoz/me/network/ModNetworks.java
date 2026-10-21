package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModNetworks {
    public static final ResourceLocation ITEM_SYNC = new ResourceLocation(MiddleEarth.MOD_ID, "item_sync");

    public static void registerS2CPackets() { // Server to Client packets
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
