package net.jukoz.me.item.items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.client.screens.MiddleEarthMapScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.level.Level;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Settings settings) {
        super(settings);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(world.isClient) {
            if (mc.currentScreen == null) {
                mc.setScreen(new MiddleEarthMapScreen());
            }
        }
        return super.use(world, user, hand);
    }
}
