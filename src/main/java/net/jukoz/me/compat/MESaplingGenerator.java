package net.jukoz.me.compat;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 1.21.8's concrete {@code net.minecraft.block.SaplingGenerator(name, secondary, tree, flowers)}
 * shim. 1.20.1's generator is abstract and only exposes {@code getTreeFeature(Random, boolean)},
 * so this subclass stores the single configured feature the donor passed.
 */
public class MESaplingGenerator extends SaplingGenerator {
    private final String name;
    @Nullable
    private final RegistryKey<ConfiguredFeature<?, ?>> treeFeature;

    public MESaplingGenerator(String name, Optional<?> secondaryFeature, Optional<RegistryKey<ConfiguredFeature<?, ?>>> treeFeature, Optional<?> flowersFeature) {
        this.name = name;
        this.treeFeature = treeFeature.orElse(null);
    }

    public String getName() {
        return name;
    }

    @Override
    @Nullable
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return this.treeFeature;
    }
}
