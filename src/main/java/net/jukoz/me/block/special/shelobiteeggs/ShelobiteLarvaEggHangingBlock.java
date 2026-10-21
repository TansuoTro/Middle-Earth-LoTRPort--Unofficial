package net.jukoz.me.block.special.shelobiteeggs;

import net.minecraft.world.level.LevelAccessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ShelobiteLarvaEggHangingBlock extends AbstractShelobiteLarvaEgg {
    public static final EnumProperty<Half> BLOCK_HALF = BlockStateProperties.BLOCK_HALF;

    public ShelobiteLarvaEggHangingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.defaultBlockState().with(BLOCK_HALF, Half.TOP));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        if (Objects.requireNonNull(state.get(BLOCK_HALF)) == Half.BOTTOM) {
            return !world.isWater(pos) && world.getBlockState(pos.below()).isAir();
        }
        return Block.sideCoversSmallSquare(world, pos.above(), Direction.DOWN) && !world.isWater(pos) && world.getBlockState(pos.below()).isAir();
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_HALF);
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.below();
            world.setBlock(blockPos, (BlockState)state.setValue(BLOCK_HALF, Half.BOTTOM), 3);
            world.updateNeighbours(pos, Blocks.AIR);
            state.updateNeighbours(world, pos, 3);
        }
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            if (state.get(BLOCK_HALF) == Half.BOTTOM){
                world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, pos.above(), Block.getRawIdFromState(state));
            } else{
                world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, pos.below(), Block.getRawIdFromState(state));
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.box(4, 4, 4, 12, 16, 12);
            case TOP -> {
                if (world.getBlockState(pos.below()).is(this)){
                    yield Shapes.combineAndSimplify(Block.box(0, 14, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 14, 11), BooleanOp.OR);
                } else {
                    yield Block.box(0, 0, 0, 0, 0, 0);
                }
            }
        };
    }

    @Override
    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (entity.getType() != EntitiesME.SHELOBITE_SCUTTLER && state.get(BLOCK_HALF) == Half.BOTTOM){
            breakEgg(world, pos, state);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.box(4, 4, 4, 12, 16, 12);
            case TOP -> Shapes.combineAndSimplify(Block.box(0, 14, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 14, 11), BooleanOp.OR);
        };
    }
}
