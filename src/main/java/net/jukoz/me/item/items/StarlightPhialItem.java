package net.jukoz.me.item.items;

import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!ModDimensions.isInMiddleEarth(world) && !world.isClient) {
            ModDimensions.teleportPlayerToME(user);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
