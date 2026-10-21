package net.jukoz.me.item.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.special.ReinforcedScaffoldingBlock;

public class ReinforcedScaffoldingItem extends ScaffoldingBlockItem {
    public ReinforcedScaffoldingItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public BlockPlaceContext getPlacementContext(BlockPlaceContext context) {
        BlockPos blockPos = context.getBlockPos();
        Level world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (blockState.isOf(block)) {
            Direction direction;
            if (context.shouldCancelInteraction()) {
                direction = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
            } else {
                direction = context.getSide() == Direction.UP ? context.getHorizontalPlayerFacing() : Direction.UP;
            }

            int horizontalDistance = 0;
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);

            while (horizontalDistance < ReinforcedScaffoldingBlock.MAX_SUPPORT_DISTANCE) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    if (context.getPlayer() instanceof ServerPlayer serverPlayer && mutable.getY() > world.getTopY()) {
                        serverPlayer.sendMessageToClient(
                                Component.translatable("build.tooHigh", world.getTopY()).formatted(ChatFormatting.RED),
                                true
                        );
                    }
                    return null;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(block)) {
                    return blockState.canReplace(context) ? BlockPlaceContext.offset(context, mutable, direction) : null;
                }

                mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    horizontalDistance++;
                }
            }

            return null;
        }

        return ReinforcedScaffoldingBlock.calculateDistance(world, blockPos, block) == ReinforcedScaffoldingBlock.MAX_SUPPORT_DISTANCE ? null : context;
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }
}
