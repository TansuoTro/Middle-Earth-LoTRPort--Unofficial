package net.jukoz.me.block.special;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.seat.SeatEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SeatBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    public SeatBlock(BlockBehaviour.Properties settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(OCCUPIED, false));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Quaternionf quaternion = state.get(FACING).getOpposite().getRotationQuaternion().normalize();
        Vector3f eulerAngles = new Vector3f(0, 0, 0);
        eulerAngles = quaternion.getEulerAnglesXYZ(eulerAngles);
        float yaw = (float) Math.atan2(eulerAngles.x, eulerAngles.z);

        SeatEntity seat = new SeatEntity(EntitiesME.SEAT_ENTITY, world);
        seat.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        seat.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, 0);
        seat.setNoGravity(true);
        seat.setSilent(true);
        seat.setInvisible(true);
        seat.setInvulnerable(true);

        if(world.isClientSide) {
            return InteractionResult.CONSUME;
        } else if(player.isShiftKeyDown() || player.isSpectator() || player.isPassenger()) {
            return InteractionResult.FAIL;
        } else if (player.isSecondaryUseActive()) {
            return InteractionResult.SUCCESS;
        } else if (world.getBlockState(pos.above()).isOpaque()){
            player.sendSystemMessage(Component.translatable("alert.%s.seat.space_not_empty".formatted(MiddleEarth.MOD_ID)), true);
            return InteractionResult.SUCCESS;
        }else if (state.get(OCCUPIED)){
        player.sendSystemMessage(Component.translatable("alert.%s.seat.occupied".formatted(MiddleEarth.MOD_ID)), true);
            return InteractionResult.SUCCESS;
        } else {
            if(world.spawnEntity(seat)) {
                player.startRiding(seat, true);
                player.setYaw(yaw);
                player.setHeadYaw(yaw);
                world.setBlock(pos, state.setValue(OCCUPIED, true));
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, WATERLOGGED, OCCUPIED);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.defaultBlockState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.get(FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }
}
