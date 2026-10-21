package net.jukoz.me.block.special;

import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlowWormBlock extends AbstractPlantBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public GlowWormBlock(BlockBehaviour.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.GLASS_BOTTLE)) {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(ModResourceItems.GLOWWORM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(ModResourceItems.GLOWWORM_BOTTLE))) {
                player.dropItem(new ItemStack(ModResourceItems.GLOWWORM_BOTTLE), false);
            }
            world.removeBlock(pos,false);
        }
        return InteractionResult.success(world.isClient);
    }

    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModNatureBlocks.GLOWWORM_WEBBING;
    }
}
