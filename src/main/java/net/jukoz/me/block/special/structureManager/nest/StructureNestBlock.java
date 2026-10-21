package net.jukoz.me.block.special.structureManager.nest;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.InteractionHand;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import net.jukoz.me.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class StructureNestBlock extends BlockWithEntity implements BlockEntityProvider, Waterloggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty ENABLED = BooleanProperty.of("enabled");

    public StructureNestBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ENABLED, false));
    }



    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClient) {
            return InteractionResult.SUCCESS;
        } else {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if(screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StructureNestBlockEntity(pos, state);
    }

    protected boolean isTransparent(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return (BlockState)this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, ENABLED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, ModBlockEntities.STRUCTURE_NEST);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(Level world, BlockEntityType<T> givenType, BlockEntityType<StructureNestBlockEntity> expectedType) {
        BlockEntityTicker ticker;
        if (world.isClient) {
            return null;
        }

        ticker = checkType(givenType, expectedType, (worldx, pos, state, blockEntity) -> {
            StructureNestBlockEntity.tickEvent(world, pos, state, blockEntity);
        });

        return ticker;
    }
}
