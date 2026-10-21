package net.jukoz.me.item.items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.client.screens.MiddleEarthMapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        Minecraft mc = Minecraft.getInstance();
        if(world.isClientSide) {
            if (mc.currentScreen == null) {
                mc.setScreen(new MiddleEarthMapScreen());
            }
        }
        return super.use(world, user, hand);
    }
}
