package net.jukoz.me.block.special.saplings;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import java.util.List;

public class VariantSaplingBlock extends SaplingBlock {
    private final List<AbstractTreeGrower> trees;

    public VariantSaplingBlock(Settings settings, List<AbstractTreeGrower> trees) {
        super(trees.get(0), settings);
        this.trees = trees;
    }

    @Override
    public void generate(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.get(STAGE) == 0) {
            world.setBlock(pos, (BlockState)state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            AbstractTreeGrower saplingGenerator = trees.get(random.nextInt(trees.size()));
            saplingGenerator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }
}
