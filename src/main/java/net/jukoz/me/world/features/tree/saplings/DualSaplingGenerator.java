package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class DualSaplingGenerator extends AbstractTreeGrower {
    private final float weight;
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey1;
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey2;

    public DualSaplingGenerator(float weight, ResourceKey<ConfiguredFeature<?, ?>> treeKey1, ResourceKey<ConfiguredFeature<?, ?>> treeKey2) {
        this.treeKey1 = treeKey1;
        this.treeKey2 = treeKey2;
        if(weight > 1.0f) throw new IllegalArgumentException("Cannot have a weight more than 1.0f for sapling generator");
        this.weight = weight;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean bees) {
        if(Math.random() < weight) {
            return treeKey1;
        }
        return treeKey2;
    }
}
