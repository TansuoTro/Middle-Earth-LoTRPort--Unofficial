package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ModLargeSaplingGenerator extends LargeTreeSaplingGenerator {
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey;
    private final ResourceKey<ConfiguredFeature<?, ?>> largeTreeKey;

    public ModLargeSaplingGenerator(ResourceKey<ConfiguredFeature<?, ?>> treeKey, ResourceKey<ConfiguredFeature<?, ?>> largeTreeKey) {
        this.treeKey = treeKey;
        this.largeTreeKey = largeTreeKey;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean bees) {
        return treeKey;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(RandomSource random) {
        return largeTreeKey;
    }
}
