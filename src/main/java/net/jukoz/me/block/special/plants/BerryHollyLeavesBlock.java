package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModNatureBlocks;

public class BerryHollyLeavesBlock extends TintedParticleLeavesBlock {

    public BerryHollyLeavesBlock(float f, BlockBehaviour.Properties settings) {
        super(f, settings);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.BONE_MEAL)) {
            world.setBlock(pos, ModNatureBlocks.BERRY_HOLLY_LEAVES.defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
