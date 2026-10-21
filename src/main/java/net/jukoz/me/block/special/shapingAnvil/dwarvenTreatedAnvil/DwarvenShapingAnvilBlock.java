package net.jukoz.me.block.special.shapingAnvil.dwarvenTreatedAnvil;

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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.stream.Stream;
import net.minecraft.world.phys.shapes.Shapes;

public class DwarvenShapingAnvilBlock extends AbstractShapingAnvilBlock {

    public DwarvenShapingAnvilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.get(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 5, 15),
                        Block.createCuboidShape(2, 5, 3, 14, 12, 13),
                        Block.createCuboidShape(0, 12, 3, 16, 16, 13),
                        Block.createCuboidShape(0, 0, 5, 2, 7, 11),
                        Block.createCuboidShape(14, 0, 5, 16, 7, 11)
                ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.createCuboidShape(1, 0, 1, 15, 5, 15),
                        Block.createCuboidShape(3, 5, 2, 13, 12, 14),
                        Block.createCuboidShape(3, 12, 0, 13, 16, 16),
                        Block.createCuboidShape(5, 0, 14, 11, 7, 16),
                        Block.createCuboidShape(5, 0, 0, 11, 7, 2)
                ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
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
        return DwarvenShapingAnvilBlock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClient ? null : DwarvenShapingAnvilBlock.checkType(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
