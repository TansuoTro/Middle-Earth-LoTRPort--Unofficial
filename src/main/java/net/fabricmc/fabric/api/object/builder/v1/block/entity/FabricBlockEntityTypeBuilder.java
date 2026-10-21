package net.fabricmc.fabric.api.object.builder.v1.block.entity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Forge compat shim for Fabric's {@code FabricBlockEntityTypeBuilder}. */
public final class FabricBlockEntityTypeBuilder<T extends BlockEntity> {
    private final BiFunction<net.minecraft.core.BlockPos, net.minecraft.world.level.block.state.BlockState, T> factory;
    private final Block[] blocks;

    private FabricBlockEntityTypeBuilder(BiFunction<net.minecraft.core.BlockPos,
            net.minecraft.world.level.block.state.BlockState, T> factory, Block... blocks) {
        this.factory = factory;
        this.blocks = blocks;
    }

    public static <T extends BlockEntity> FabricBlockEntityTypeBuilder<T> create(
            BiFunction<net.minecraft.core.BlockPos, net.minecraft.world.level.block.state.BlockState, T> factory,
            Block... blocks) {
        return new FabricBlockEntityTypeBuilder<>(factory, blocks);
    }

    public BlockEntityType<T> build() {
        return BlockEntityType.Builder.of(this.factory::apply, this.blocks).build();
    }
}
