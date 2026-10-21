package net.jukoz.me.item.utils;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelView;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

public class ModVerticallyAttachableBlockItem extends BlockItem {
    protected final Block wallBlock;
    private final Direction[] verticalAttachmentDirections;

    public ModVerticallyAttachableBlockItem(Block standingBlock, Block wallBlock, Settings settings, Direction[] verticalAttachmentDirections) {
        super(standingBlock, settings);
        this.wallBlock = wallBlock;
        this.verticalAttachmentDirections = verticalAttachmentDirections;
    }

    protected boolean canPlaceAt(LevelReader world, BlockState state, BlockPos pos) {
        return state.canPlaceAt(world, pos);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState = this.wallBlock.getPlacementState(context);
        BlockState blockState2 = null;
        LevelReader worldView = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        Direction[] var6 = context.getPlacementDirections();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            for(Direction verticalAttachmentDirection : verticalAttachmentDirections){
                if (direction != verticalAttachmentDirection.getOpposite()) {
                    BlockState blockState3 = direction == verticalAttachmentDirection ? this.getBlock().getPlacementState(context) : blockState;
                    if (blockState3 != null && this.canPlaceAt(worldView, blockState3, blockPos)) {
                        blockState2 = blockState3;
                        return worldView.canPlace(blockState2, blockPos, CollisionContext.absent()) ? blockState2 : null;
                    }
                }
            }
        }
        return null;
    }

    public void appendBlocks(Map<Block, Item> map, Item item) {
        super.appendBlocks(map, item);
        map.put(this.wallBlock, item);
    }
}
