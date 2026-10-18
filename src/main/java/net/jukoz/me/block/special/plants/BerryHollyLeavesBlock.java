package net.jukoz.me.block.special.plants;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.jukoz.me.block.registration.ModNatureBlocks;

public class BerryHollyLeavesBlock extends TintedParticleLeavesBlock {

    public BerryHollyLeavesBlock(float f, AbstractBlock.Settings settings) {
        super(f, settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.BONE_MEAL)) {
            world.setBlockState(pos, ModNatureBlocks.BERRY_HOLLY_LEAVES.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}