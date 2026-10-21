package net.jukoz.me.block.special.shapingAnvil.treatedAnvil;

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

public class TreatedAnvilblock extends AbstractShapingAnvilBlock {
    public TreatedAnvilblock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.get(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 6, 15),
                        Block.createCuboidShape(1, 12, 4, 15, 16, 12),
                        Block.createCuboidShape(4, 6, 5, 12, 12, 11)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 6, 15),
                        Block.createCuboidShape(4, 12, 1, 12, 16, 15),
                        Block.createCuboidShape(5, 6, 4, 11, 12, 12)
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
        return TreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClient ? null : TreatedAnvilblock.checkType(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
