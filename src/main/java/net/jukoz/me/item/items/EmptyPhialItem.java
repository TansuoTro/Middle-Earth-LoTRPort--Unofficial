package net.jukoz.me.item.items;

import net.jukoz.me.item.ModResourceItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUsage;
import net.minecraft.tags.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class EmptyPhialItem  extends Item {
    public EmptyPhialItem(Item.Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!world.canPlayerModifyAt(user, blockPos)) {
                    return TypedActionResult.pass(itemStack);
                }

                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.15F);
                    world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                    return TypedActionResult.success(this.fill(itemStack, user, new ItemStack(ModResourceItems.WATER_PHIAL)), world.isClient());
                }
            }

        }
        return TypedActionResult.pass(itemStack);
    }

    protected ItemStack fill(ItemStack stack, Player player, ItemStack outputStack) {
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.exchangeStack(stack, player, outputStack);
    }
}
