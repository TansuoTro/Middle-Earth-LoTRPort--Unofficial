package net.jukoz.me.world.features.growth;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.jukoz.me.block.special.WebbingBlock;
import java.util.List;

public class MultifaceStateFeature extends Feature<MultifaceStateFeatureConfig> {
	public MultifaceStateFeature(Codec<MultifaceStateFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeaturePlaceContext<MultifaceStateFeatureConfig> context) {
		WorldGenLevel structureWorldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		RandomSource random = context.getRandom();
		MultifaceStateFeatureConfig multifaceGrowthFeatureConfig = context.getConfig();
		if (!isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = multifaceGrowthFeatureConfig.shuffleDirections(random);
			if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), multifaceGrowthFeatureConfig, random, list)) {
				return true;
			} else {
				BlockPos.MutableBlockPos mutable = blockPos.mutableCopy();

				for (Direction direction : list) {
					mutable.set(blockPos);
					List<Direction> list2 = multifaceGrowthFeatureConfig.shuffleDirections(random, direction.getOpposite());

					for (int i = 0; i < multifaceGrowthFeatureConfig.searchRange; i++) {
						mutable.set(blockPos, direction);
						BlockState blockState = structureWorldAccess.getBlockState(mutable);
						if (!isAirOrWater(blockState) && !blockState.is(multifaceGrowthFeatureConfig.block)) {
							break;
						}

						if (generate(structureWorldAccess, mutable, blockState, multifaceGrowthFeatureConfig, random, list2)) {
							return true;
						}
					}
				}

				return false;
			}
		}
	}

	public static boolean generate(
			WorldGenLevel world, BlockPos pos, BlockState state, MultifaceStateFeatureConfig config, RandomSource random, List<Direction> directions
	) {
		BlockPos.MutableBlockPos mutable = pos.mutableCopy();

		for (Direction direction : directions) {
			BlockState blockState = world.getBlockState(mutable.set(pos, direction));
			if (blockState.is(config.canPlaceOn)) {
				BlockState blockState2 = config.block.withDirection(state, world, pos, direction);
				if (blockState2 == null) {
					return false;
				}
				blockState2 = blockState2.with(WebbingBlock.PERSISTENT, config.persistent);

				world.setBlock(pos, blockState2, (Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS));
				world.getChunk(pos).markPosForPostprocessing(pos);
				if (random.nextFloat() < config.spreadChance) {
					config.block.getGrower().grow(blockState2, world, pos, direction, random, true);
				}

				return true;
			}
		}

		return false;
	}

	private static boolean isAirOrWater(BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}
}
