package net.jukoz.me.block.special;

import net.minecraft.world.level.LevelAccessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DroopingIciclesBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(2.0, 10.0, 2.0, 14.0, 16.0, 14.0);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public DroopingIciclesBlock(BlockBehaviour.Properties settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.UPPER));
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN) || blockState.is(this);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        BlockState upState = world.getBlockState(pos.above());

        if(!upState.is(this) && !upState.isSideSolidFullSquare(world, pos, Direction.DOWN)){
            return Blocks.AIR.defaultBlockState();
        } else if (doubleBlockHalf == DoubleBlockHalf.UPPER && world.getBlockState(pos.below()).isAir()){
            return state.setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState upState = world.getBlockState(pos.above());
        if (upState.is(this)){
            world.setBlock(pos.above(), upState.setValue(HALF, DoubleBlockHalf.UPPER));
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.LOWER), 3);
        } else {
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER){
            return super.getOutlineShape(state, world, pos, context);
        } else  {
            return SHAPE;
        }
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)world;
                if (projectile.canModifyAt(serverWorld, blockPos) && projectile instanceof ThrownTrident && projectile.getVelocity().length() > 0.6) {
                    world.breakBlock(blockPos, true);
                }
            }

        }
    }

}
