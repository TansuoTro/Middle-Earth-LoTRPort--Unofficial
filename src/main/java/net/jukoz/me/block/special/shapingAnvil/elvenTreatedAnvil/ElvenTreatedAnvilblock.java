package net.jukoz.me.block.special.shapingAnvil.elvenTreatedAnvil;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.AbstractShapingAnvilBlock;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BlockWithEntity;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ElvenTreatedAnvilblock extends AbstractShapingAnvilBlock {
    public ElvenTreatedAnvilblock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.get(FACING)){
            case NORTH -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(12, 12, 6, 16, 16, 10),
                        Block.createCuboidShape(4, 13, 6, 12, 16, 10),
                        Block.createCuboidShape(0, 14, 7, 4, 16, 9),
                        Block.createCuboidShape(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(0, 12, 6, 4, 16, 10),
                        Block.createCuboidShape(4, 13, 6, 12, 16, 10),
                        Block.createCuboidShape(12, 14, 7, 16, 16, 9),
                        Block.createCuboidShape(5, 5, 6, 11, 13, 10)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(6, 12, 12, 10, 16, 16),
                        Block.createCuboidShape(6, 13, 4, 10, 16, 12),
                        Block.createCuboidShape(7, 14, 0, 9, 16, 4),
                        Block.createCuboidShape(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case WEST -> {
                return Stream.of(
                        Block.createCuboidShape(2, 0, 2, 14, 5, 14),
                        Block.createCuboidShape(6, 12, 0, 10, 16, 4),
                        Block.createCuboidShape(6, 13, 4, 10, 16, 12),
                        Block.createCuboidShape(7, 14, 12, 9, 16, 16),
                        Block.createCuboidShape(6, 5, 5, 10, 13, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            default -> {
                return Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            }
        }
    }



    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShapingAnvilBlockEntity(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return ElvenTreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClient ? null : ElvenTreatedAnvilblock.checkType(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
