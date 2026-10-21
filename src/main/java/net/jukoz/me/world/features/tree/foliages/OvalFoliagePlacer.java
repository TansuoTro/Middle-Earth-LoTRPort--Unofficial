package net.jukoz.me.world.features.tree.foliages;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeatureConfig;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import java.util.ArrayList;
import java.util.List;

public class OvalFoliagePlacer extends FoliagePlacer {
    protected final int baseHeight;
    protected final IntProvider offset;
    protected final IntProvider baseRadius;
    protected final ArrayList<BlockState> leaves;
    protected final float extraSize;

    public static final Codec<OvalFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.intRange(0,16).fieldOf("baseHeight").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseHeight;
            }), IntProvider.createValidatingCodec(-16, 16).fieldOf("offset").forGetter((trunkPlacer) -> {
                return trunkPlacer.offset;
            }), IntProvider.createValidatingCodec(0, 16).fieldOf("baseRadius").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseRadius;
            }), Codec.list(BlockState.CODEC).fieldOf("leaves").forGetter((trunkPlacer) -> {
                return trunkPlacer.leaves;
            }), Codec.floatRange(0.0f, 1.0f).fieldOf("extraSize").forGetter((trunkPlacer) -> {
                return trunkPlacer.extraSize;
            })).apply(instance, OvalFoliagePlacer::new));

    public OvalFoliagePlacer(int baseHeight, IntProvider offset, IntProvider baseRadius, List<BlockState> leaves, float extraRadius) {
        super(baseRadius, offset);
        this.baseHeight = baseHeight;
        this.offset = offset;
        this.baseRadius = baseRadius;
        this.leaves = new ArrayList<>(leaves);
        this.extraSize = extraRadius;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModTreeGeneration.OVAL_FOLIAGE;
    }

    @Override
    protected void generate(LevelSimulatedReader world, BlockPlacer placer, RandomSource random, TreeConfiguration config, int trunkHeight,
                            TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int bounds = radius + 1;
        for(int x = -bounds; x <= bounds; ++x) {
            for(int z = -bounds; z <= bounds; ++z) {
                for(int y = -baseHeight; y <= baseHeight; ++y) {
                    if (!this.isPositionInvalid(random, x, offset + y, z, radius, false)) {
                        if (isPointInside(x, y, z, radius)) {
                            mutable.set(treeNode.getCenter(), x, offset + y, z);
                            placeLeavesBlock(world, placer, random, config, mutable, leaves);
                        }
                    }
                }
            }
        }
    }

    protected static boolean placeLeavesBlock(LevelSimulatedReader world, BlockPlacer placer, RandomSource random, TreeConfiguration config,
                                               BlockPos pos, List<BlockState> leaves) {
        if (!TreeFeature.canReplace(world, pos)) {
            return false;
        } else {
            BlockState leavesBlock;
            if(leaves.isEmpty()) {
                leavesBlock = config.foliageProvider.get(random, pos);
            } else {
                leavesBlock = leaves.get(random.nextBetween(0, leaves.size() - 1));
            }

            if (leavesBlock.contains(BlockStateProperties.WATERLOGGED)) {
                leavesBlock = leavesBlock.with(BlockStateProperties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
            }

            placer.placeBlock(pos, leavesBlock);
            return true;
        }
    }

    private boolean isPointInside(int x, int y, int z, int radius) {
        float randomness = -0.35f + (float) (Math.random() * 0.7f);
        float squareRadius = (radius + extraSize + randomness) * (radius + extraSize + randomness);
        float squareHeight = (baseHeight + extraSize + randomness) * (baseHeight + extraSize + randomness);
        float deltaX = (float)(x*x) / squareRadius;
        float deltaY = (float)(y*y) / squareHeight;
        float deltaZ = (float)(z*z) / squareRadius;

        return (deltaX + deltaY + deltaZ <= 1);
    }

    @Override
    public int getRandomHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return 4;
    }

    @Override
    protected boolean isInvalidForLeaves(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (dx + dz >= 7) {
            return true;
        } else {
            return dx * dx + dz * dz > (radius + extraSize) * (radius + extraSize);
        }
    }
}
