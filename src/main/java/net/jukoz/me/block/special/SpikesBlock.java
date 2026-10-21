package net.jukoz.me.block.special;
import net.minecraft.world.level.LevelAccess;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.block.enums.Thickness;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
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
import net.minecraft.world.level.LevelView;

public class SpikesBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public SpikesBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }



    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) || blockState.isOf(this);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        BlockState downState = world.getBlockState(pos.down());

        if(!downState.isOf(this) && !downState.isSideSolidFullSquare(world, pos, Direction.UP)){
            return Blocks.AIR.getDefaultState();
        } else if (doubleBlockHalf == DoubleBlockHalf.LOWER && world.getBlockState(pos.up()).isAir()){
            return state.with(HALF, DoubleBlockHalf.UPPER);
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState downState = world.getBlockState(pos.down());
        if (downState.isOf(this)){
            world.setBlockState(pos.down(), downState.with(HALF, DoubleBlockHalf.LOWER));
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)world;
                if (projectile.canModifyAt(serverWorld, blockPos) && projectile instanceof TridentEntity && projectile.getVelocity().length() > 0.6) {
                    world.breakBlock(blockPos, true);
                }
            }
        }
    }

    public void onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.handleFallDamage((float)(fallDistance + 2.5), 2.0F, world.getDamageSources().fall());
    }
}
