package net.fabricmc.fabric.api.client.rendering.v1;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

/** Forge compat shim for Fabric's {@code ColorProviderRegistry}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class ColorProviderRegistry {
    public static final ColorProviderRegistry ITEM = new ColorProviderRegistry();
    public static final ColorProviderRegistry BLOCK = new ColorProviderRegistry();

    private final List<Object[]> itemRegistrations = new ArrayList<>();
    private final List<Object[]> blockRegistrations = new ArrayList<>();

    private ColorProviderRegistry() {}

    public void register(ItemColor provider, ItemLike... items) {
        itemRegistrations.add(new Object[]{provider, items});
    }

    public void register(BlockColor provider, Block... blocks) {
        blockRegistrations.add(new Object[]{provider, blocks});
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        BLOCK.blockRegistrations.forEach(entry -> event.getBlockColors().register(
                (BlockColor) entry[0], (Block[]) entry[1]));
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        ITEM.itemRegistrations.forEach(entry -> event.getItemColors().register(
                (ItemColor) entry[0], (ItemLike[]) entry[1]));
    }
}
