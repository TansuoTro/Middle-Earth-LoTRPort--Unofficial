package net.jukoz.me.world.features.platedfood;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.plate.PlateBlockEntity;
import net.jukoz.me.world.features.chain.ChainFeatureConfig;

public class PlatedFoodFeature extends Feature<PlatedFoodFeatureConfig> {

    public PlatedFoodFeature(Codec<PlatedFoodFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<PlatedFoodFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        RandomSource random = context.getRandom();
        PlatedFoodFeatureConfig config = context.getConfig();
        ResourceLocation lootTableIdentifier = config.lootTable;

        this.setBlockState(structureWorldAccess, blockPos, config.plate);

        BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos);
        if(blockEntity != null) {
            PlateBlockEntity plateBlockEntity = (PlateBlockEntity) blockEntity;
            plateBlockEntity.setLootTable(lootTableIdentifier, random.nextLong());
            plateBlockEntity.setBlockPlaced();
            plateBlockEntity.generateItem((ServerLevel) structureWorldAccess);
        }
        return true;
    }
}
