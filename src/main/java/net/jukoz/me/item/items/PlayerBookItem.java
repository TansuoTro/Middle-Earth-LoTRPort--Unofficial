package net.jukoz.me.item.items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.jukoz.me.gui.map.MapScreenController;
import net.jukoz.me.gui.playerbook.PlayerBookScreen;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;

public class PlayerBookItem extends Item {
    public PlayerBookItem(Settings settings) {
        super(settings);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public net.minecraft.world.InteractionResultHolder<net.minecraft.world.item.ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(world.isClient) {
            Minecraft client = Minecraft.getInstance();
            client.setScreen(new PlayerBookScreen(Component.of("Player's book")));
            return net.minecraft.util.InteractionResultHolder.success(user.getStackInHand(hand));
        }
        return net.minecraft.util.InteractionResultHolder.fail(user.getStackInHand(hand));
    }
}
