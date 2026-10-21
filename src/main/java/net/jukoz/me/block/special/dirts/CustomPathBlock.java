package net.jukoz.me.block.special.dirts;

import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public class CustomPathBlock extends DirtPathBlock {

    private final Block target;

    public CustomPathBlock(Settings settings, Block target) {
        super(settings);
        this.target = target;
    }

    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Block.pushEntitiesUpBeforeBlockChange(this.getDefaultState(), target.getDefaultState(), ctx.getWorld(), ctx.getBlockPos()) : super.getPlacementState(ctx);
    }

    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        CustomFarmlandBlock.setToDirt((Entity)null, state, world, pos, target);
    }
}
