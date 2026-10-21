package net.jukoz.me.block.special.shapingAnvil.orcishTreatedAnvil;

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

public class OrcishTreatedAnvilblock extends AbstractShapingAnvilBlock {
    public OrcishTreatedAnvilblock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.get(FACING)){
            case NORTH, SOUTH -> {
                return Stream.of(
                        Block.createCuboidShape(3, 0, 3, 13, 10, 13),
                        Block.createCuboidShape(1, 14, 7, 4, 16, 9),
                        Block.createCuboidShape(4, 13, 5, 12, 16, 11),
                        Block.createCuboidShape(12, 14, 7, 15, 16, 9),
                        Block.createCuboidShape(5, 7, 5, 11, 13, 11)
                ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
            }
            case EAST, WEST -> {
                return Stream.of(
                        Block.createCuboidShape(3, 0, 3, 13, 10, 13),
                        Block.createCuboidShape(7, 14, 12, 9, 16, 15),
                        Block.createCuboidShape(5, 13, 4, 11, 16, 12),
                        Block.createCuboidShape(7, 14, 1, 9, 16, 4),
                        Block.createCuboidShape(5, 7, 5, 11, 13, 11)
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
        return OrcishTreatedAnvilblock.validateTicker(world, type, ModBlockEntities.TREATED_ANVIL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ShapingAnvilBlockEntity> expectedType) {
        return world.isClient ? null : OrcishTreatedAnvilblock.checkType(givenType, expectedType, ShapingAnvilBlockEntity::tick);
    }
}
