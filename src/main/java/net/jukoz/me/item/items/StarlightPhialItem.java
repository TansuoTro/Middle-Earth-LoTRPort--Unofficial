package net.jukoz.me.item.items;

import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;

public class StarlightPhialItem extends Item {
    public StarlightPhialItem(Settings settings) {
        super(settings);
    }

    /**
     * Donor: the phial opens the onboarding flow through a server-authoritative packet and is
     * never consumed. The port keeps its direct teleport, but only from the server side of the
     * vanilla interact packet ({@code use} runs there too) and without spending the item.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!ModDimensions.isInMiddleEarth(world) && !world.isClient) {
            ModDimensions.teleportPlayerToME(user);
        }
        return InteractionResultHolder.success(user.getStackInHand(hand));
    }
}
