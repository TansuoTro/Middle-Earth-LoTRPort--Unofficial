package net.jukoz.me.block.special.artisantable;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.InteractionHand;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.stream.Stream;

public class ArtisanTable extends HorizontalFacingBlock {
    public static final EnumProperty<ArtisanTablePart> PART = EnumProperty.of("part", ArtisanTablePart.class);
    private static final Component TITLE = Component.translatable("container.artisan_table");


    public ArtisanTable(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, ArtisanTablePart.LEFT).with(FACING, Direction.NORTH));
    }

    @Nullable
    public static Direction getDirection(BlockGetter world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() instanceof ArtisanTable ? (Direction)blockState.get(FACING) : null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == getDirectionTowardsOtherPart((ArtisanTablePart)state.get(PART), (Direction)state.get(FACING).rotateYClockwise())) {
            return neighborState.isOf(this) && neighborState.get(PART) != state.get(PART) ? (BlockState)state : Blocks.AIR.getDefaultState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClient && player.isCreative()) {
            ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
            ArtisanTablePart tablePartOpposite = (ArtisanTablePart)state.get(PART).getOpposite(state.get(PART));

            BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(tablePart, (Direction)state.get(FACING).rotateYClockwise()));
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(this) && blockState.get(PART) == tablePartOpposite) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }

        super.onBreak(world, pos, state, player);
    }

    private static Direction getDirectionTowardsOtherPart(ArtisanTablePart part, Direction direction) {
        return part == ArtisanTablePart.LEFT ? direction : direction.getOpposite();
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction.rotateYClockwise());
        Level world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? (BlockState)this.getDefaultState().with(FACING, direction).with(PART, ArtisanTablePart.LEFT) : null;
    }

    public static Direction getOppositePartDirection(BlockState state) {
        Direction direction = (Direction)state.get(FACING);
        return state.get(PART) == ArtisanTablePart.RIGHT ? direction.getOpposite() : direction;
    }

    public static DoubleBlockProperties.Type getTablePart(BlockState state) {
        ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
        return tablePart == ArtisanTablePart.RIGHT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
            world.setBlockState(blockPos, (BlockState)state.with(PART, ArtisanTablePart.RIGHT), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset((Direction)state.get(FACING), state.get(PART) == ArtisanTablePart.RIGHT ? 0 : 1);
        return Mth.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    public boolean canPathfindThrough(BlockState state, BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public ActionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_STONECUTTER);
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, Level world, BlockPos pos) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                buf.writeString(getDispositionData(player, world));
            }

            @Override
            public Component getDisplayName() {
                return TITLE;
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, Player player) {
                return new ArtisanTableScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos), getDispositionData(player, world));
            }
        };
    }

    /** Donor opens the artisan table with "{disposition}/{isCreative}", used to filter the recipe list. */
    private static String getDispositionData(Player player, Level world) {
        DispositionType dispositionType = PlayerDataService.getPlayerDisposition(player, world);
        if (dispositionType == null) {
            dispositionType = DispositionType.NEUTRAL;
        }
        return dispositionType + "/" + player.isCreative();
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, PART);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return switch (state.get(PART)){
            case LEFT ->
                    switch (state.get(FACING)){
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 4, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.createCuboidShape(12, 0, 1, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case WEST -> Stream.of(
                                Block.createCuboidShape(1, 0, 12, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case EAST -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 15, 12, 4),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                    };
            case RIGHT ->
                    switch (state.get(FACING)) {
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.createCuboidShape(12, 0, 1, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 4, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case WEST -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 15, 12, 4),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case EAST -> Stream.of(
                                Block.createCuboidShape(1, 0, 12, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                    };

        };
    }
}
