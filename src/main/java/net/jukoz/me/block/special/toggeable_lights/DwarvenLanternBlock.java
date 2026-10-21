package net.jukoz.me.block.special.toggeable_lights;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class DwarvenLanternBlock extends AbstractToggeableLightBlock {
    public static final BooleanProperty HANGING;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape STANDING_SHAPE, HANGING_SHAPE;

    public DwarvenLanternBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(HANGING, false).with(WATERLOGGED, false).with(LEVEL_15, 15));
    }
    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add (HANGING);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && !state.get(LIT)) {
            world.setBlockState(hit.getBlockPos(), state.with(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    protected Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    static {
        HANGING = BlockStateProperties.HANGING;
        STATE_TO_LUMINANCE = (state) -> { return state.get(LIT) ? state.get(LEVEL_15) : 0; };

        STANDING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
                Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 7.0, 10.0));
        HANGING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 3.0, 5.0, 11.0, 8.0, 11.0),
                Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));
    }
}
