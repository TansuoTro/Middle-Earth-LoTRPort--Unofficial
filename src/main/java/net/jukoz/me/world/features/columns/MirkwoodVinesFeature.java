package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MirkwoodVinesFeature  extends Feature<NoneFeatureConfiguration> {
    public MirkwoodVinesFeature(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        RandomSource random = context.getRandom();
        if (!structureWorldAccess.isAir(blockPos)) {
            return false;
        } else {
            BlockState blockState = structureWorldAccess.getBlockState(blockPos.above());
            if (!blockState.is(WoodBlockSets.MIRKWOOD_SET.leaves) && !blockState.isSolidBlock(context.getWorld(), blockPos.above())) {
                return false;
            } else {
                this.generateVinesInArea(structureWorldAccess, random, blockPos);
                return true;
            }
        }
    }

    private void generateVinesInArea(LevelAccessor world, RandomSource random, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 32; ++i) {
            mutable.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));

            if (world.isAir(mutable) && validateRoot(world, mutable)) {
                int length = Mth.nextInt(random, 1, 8);

                if (random.nextInt(6) == 0) { // very long vine
                    length *= 2;
                }

                if (random.nextInt(5) == 0) { // short vine
                    length = 1;
                }

                generateVineColumn(world, mutable, length);
            }
        }
    }

    public static void generateVineColumn(LevelAccessor world, BlockPos.MutableBlockPos pos, int length) {
        for(int i = 0; i <= length; ++i) {
            if (world.isAir(pos)) {
                BlockState blockStateAbove = world.getBlockState(pos.above());

                if(blockStateAbove.isAir())
                    break;

                if(blockStateAbove.is(WoodBlockSets.MIRKWOOD_SET.leaves)){
                    world.setBlock(pos.above(), WoodBlockSets.MIRKWOOD_SET.leaves.defaultBlockState().with(LeavesBlock.PERSISTENT, false), 2);
                }

                if (i == length || !world.getBlockState(pos.below()).isAir()) {
                    world.setBlock(pos, ModNatureBlocks.MIRKWOOD_VINES.defaultBlockState().with(net.jukoz.me.block.special.hangingstuff.CustomHangingBlock.TIP, true), 2);
                    break;
                }

                world.setBlock(pos, ModNatureBlocks.MIRKWOOD_VINES.defaultBlockState().with(net.jukoz.me.block.special.hangingstuff.CustomHangingBlock.TIP, false), 2);
            }

            pos.move(Direction.DOWN);
        }
    }

    private static boolean validateRoot(LevelAccessor world, BlockPos.MutableBlockPos mutable) {
        BlockState blockState = world.getBlockState(mutable.above());
        return (blockState.is(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()) || blockState.is(WoodBlockSets.MIRKWOOD_SET.leaves));
    }
}
