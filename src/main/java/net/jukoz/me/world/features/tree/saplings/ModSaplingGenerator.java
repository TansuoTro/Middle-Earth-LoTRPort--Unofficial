package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ModSaplingGenerator extends AbstractTreeGrower {
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey;

    public ModSaplingGenerator(ResourceKey<ConfiguredFeature<?, ?>> treeKey) {
        this.treeKey = treeKey;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean bees) {
        return treeKey;
    }
}
