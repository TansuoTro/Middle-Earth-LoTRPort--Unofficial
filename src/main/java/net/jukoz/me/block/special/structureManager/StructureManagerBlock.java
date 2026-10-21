package net.jukoz.me.block.special.structureManager;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class StructureManagerBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public StructureManagerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }



    @Override
    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();

        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            case DOWN, UP -> null;
            case NORTH -> Block.createCuboidShape(0, 0, 2, 16, 16, 16);
            case WEST -> Block.createCuboidShape(2, 0, 0, 16, 16, 16);
            case SOUTH -> Block.createCuboidShape(0, 0, 0, 16, 16, 14);
            case EAST -> Block.createCuboidShape(0, 0, 0, 14, 16, 16);
        };
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClient) {
            return InteractionResult.SUCCESS;
        } else if(player.isCreative() && player.isCreativeLevelTwoOp()) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if(screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StructureManagerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, ModBlockEntities.STRUCTURE_MANAGER);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(Level world, BlockEntityType<T> givenType, BlockEntityType<StructureManagerBlockEntity> expectedType) {
        BlockEntityTicker ticker;
        if (world.isClient) {
            return null;
        }

        ticker = checkType(givenType, expectedType, (worldx, pos, state, blockEntity) -> {
            StructureManagerBlockEntity.tickEvent(world, pos, state, blockEntity);
        });

        return ticker;
    }
}
