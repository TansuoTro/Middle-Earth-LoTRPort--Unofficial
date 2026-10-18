package net.jukoz.me.item.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.jukoz.me.gui.map.MapScreenController;
import net.jukoz.me.gui.playerbook.PlayerBookScreen;
import net.jukoz.me.gui.return_confirmation.ReturnConfirmationScreen;

public class PlayerBookItem extends Item {
    public PlayerBookItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new PlayerBookScreen(Text.of("Player's book")));
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
