package net.jukoz.me.block.special.saplings;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.List;

public class WeightedSaplingBlock extends SaplingBlock {
    private final List<WeightedTree> trees;

    public WeightedSaplingBlock(Settings settings, List<WeightedTree> trees) {
        super(trees.get(0).saplingGenerator, settings);
        this.trees = trees;
    }

    @Override
    public void generate(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, (BlockState)state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            int totalWeight = trees.stream().mapToInt(obj -> obj.weight).sum();
            int randomWeight = random.nextInt(totalWeight);
            int accumulatedWeight = 0;
            SaplingGenerator tree = null;
            for(WeightedTree weightedTree : trees) {
                if(weightedTree.weight + accumulatedWeight > randomWeight) {
                    tree = weightedTree.saplingGenerator;
                    break;
                } else {
                    accumulatedWeight += weightedTree.weight;
                }
            }
            if(tree != null) tree.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }

    public static class WeightedTree {
        public SaplingGenerator saplingGenerator;
        public int weight;

        public WeightedTree(SaplingGenerator saplingGenerator, int weight) {
            this.saplingGenerator = saplingGenerator;
            this.weight = weight;
        }
        public WeightedTree(ResourceKey<ConfiguredFeature<?, ?>> saplingGenerator, int weight, String name) {
            // 1.20.1 SaplingGenerator has only the no-arg constructor; the tree key is
            // returned from getTreeFeature instead of being passed through the ctor.
            this.saplingGenerator = new SaplingGenerator() {
                @Override
                protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean large) {
                    return saplingGenerator;
                }
            };
            this.weight = weight;
        }
    }
}
