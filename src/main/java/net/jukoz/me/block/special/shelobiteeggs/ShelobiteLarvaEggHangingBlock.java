package net.jukoz.me.block.special.shelobiteeggs;
import net.minecraft.world.level.LevelAccess;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import net.jukoz.me.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ShelobiteLarvaEggHangingBlock extends AbstractShelobiteLarvaEgg {
    public static final EnumProperty<BlockHalf> BLOCK_HALF = BlockStateProperties.BLOCK_HALF;

    public ShelobiteLarvaEggHangingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(BLOCK_HALF, BlockHalf.TOP));
    }



    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (Objects.requireNonNull(state.get(BLOCK_HALF)) == BlockHalf.BOTTOM) {
            return !world.isWater(pos) && world.getBlockState(pos.down()).isAir();
        }
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) && !world.isWater(pos) && world.getBlockState(pos.down()).isAir();
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_HALF);
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.down();
            world.setBlockState(blockPos, (BlockState)state.with(BLOCK_HALF, BlockHalf.BOTTOM), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClient) {
            if (state.get(BLOCK_HALF) == BlockHalf.BOTTOM){
                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, pos.up(), Block.getRawIdFromState(state));
            } else{
                world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, pos.down(), Block.getRawIdFromState(state));
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.createCuboidShape(4, 4, 4, 12, 16, 12);
            case TOP -> {
                if (world.getBlockState(pos.down()).isOf(this)){
                    yield VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 14, 0, 16, 16, 16), Block.createCuboidShape(5, 0, 5, 11, 14, 11), BooleanBiFunction.OR);
                } else {
                    yield Block.createCuboidShape(0, 0, 0, 0, 0, 0);
                }
            }
        };
    }

    @Override
    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (entity.getType() != EntitiesME.SHELOBITE_SCUTTLER && state.get(BLOCK_HALF) == BlockHalf.BOTTOM){
            breakEgg(world, pos, state);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.createCuboidShape(4, 4, 4, 12, 16, 12);
            case TOP -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 14, 0, 16, 16, 16), Block.createCuboidShape(5, 0, 5, 11, 14, 11), BooleanBiFunction.OR);
        };
    }
}