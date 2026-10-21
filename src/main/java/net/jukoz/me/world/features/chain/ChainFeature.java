package net.jukoz.me.world.features.chain;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ChainFeature extends Feature<ChainFeatureConfig> {

    public ChainFeature(Codec<ChainFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<ChainFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        RandomSource random = context.getRandom();
        ChainFeatureConfig config = context.getConfig();
        int maxDistance = config.maxLength;
        Direction direction = config.direction;

        this.setBlockState(structureWorldAccess, blockPos, config.startBlock);
        for(int i = 1; i <= maxDistance; i++) {
            BlockState mutableBlockState = structureWorldAccess.getBlockState(blockPos.mutableCopy().offset(direction, i));
            if(mutableBlockState.isOf(Blocks.AIR) || mutableBlockState.isIn(BlockTags.REPLACEABLE_BY_TREES)) {
                this.setBlockState(structureWorldAccess, blockPos.mutableCopy().offset(direction, i), config.chainBlock);
            } else {
                this.setBlockState(structureWorldAccess, blockPos.mutableCopy().offset(direction, Math.max(0, i - 1)), config.endBlock);
                break;
            }
        }
        return true;
    }
}
