package net.jukoz.me.item.items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.jukoz.me.gui.map.MapScreenController;
import net.jukoz.me.gui.playerbook.PlayerBookScreen;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;

public class PlayerBookItem extends Item {
    public PlayerBookItem(Settings settings) {
        super(settings);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public net.minecraft.util.TypedActionResult<net.minecraft.world.item.ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new PlayerBookScreen(Component.of("Player's book")));
            return net.minecraft.util.TypedActionResult.success(user.getStackInHand(hand));
        }
        return net.minecraft.util.TypedActionResult.fail(user.getStackInHand(hand));
    }
}
