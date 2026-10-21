package net.jukoz.me.block.special.artisantable;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.InteractionHand;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ArtisanTable extends HorizontalDirectionalBlock {
    public static final EnumProperty<ArtisanTablePart> PART = EnumProperty.create("part", ArtisanTablePart.class);
    private static final Component TITLE = Component.translatable("container.artisan_table");

    public ArtisanTable(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.stateManager.defaultBlockState().with(PART, ArtisanTablePart.LEFT).with(FACING, Direction.NORTH));
    }

    @Nullable
    public static Direction getDirection(BlockGetter world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() instanceof ArtisanTable ? (Direction)blockState.get(FACING) : null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == getDirectionTowardsOtherPart((ArtisanTablePart)state.get(PART), (Direction)state.get(FACING).getClockWise())) {
            return neighborState.is(this) && neighborState.get(PART) != state.get(PART) ? (BlockState)state : Blocks.AIR.defaultBlockState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide && player.isCreative()) {
            ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
            ArtisanTablePart tablePartOpposite = (ArtisanTablePart)state.get(PART).getOpposite(state.get(PART));

            BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(tablePart, (Direction)state.get(FACING).getClockWise()));
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(this) && blockState.get(PART) == tablePartOpposite) {
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }

        super.onBreak(world, pos, state, player);
    }

    private static Direction getDirectionTowardsOtherPart(ArtisanTablePart part, Direction direction) {
        return part == ArtisanTablePart.LEFT ? direction : direction.getOpposite();
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction.getClockWise());
        Level world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? (BlockState)this.defaultBlockState().with(FACING, direction).with(PART, ArtisanTablePart.LEFT) : null;
    }

    public static Direction getOppositePartDirection(BlockState state) {
        Direction direction = (Direction)state.get(FACING);
        return state.get(PART) == ArtisanTablePart.RIGHT ? direction.getOpposite() : direction;
    }

    public static DoubleBlockCombiner.BlockType getTablePart(BlockState state) {
        ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
        return tablePart == ArtisanTablePart.RIGHT ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.offset((Direction)state.get(FACING).getClockWise());
            world.setBlock(blockPos, (BlockState)state.setValue(PART, ArtisanTablePart.RIGHT), 3);
            world.updateNeighbours(pos, Blocks.AIR);
            state.updateNeighbours(world, pos, 3);
        }
    }

    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset((Direction)state.get(FACING), state.get(PART) == ArtisanTablePart.RIGHT ? 0 : 1);
        return Mth.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    public boolean canPathfindThrough(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_STONECUTTER);
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    public MenuProvider createScreenHandlerFactory(BlockState state, Level world, BlockPos pos) {
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
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new ArtisanTableScreenHandler(syncId, playerInventory, ContainerLevelAccess.create(world, pos), getDispositionData(player, world));
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
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(PART)){
            case LEFT ->
                    switch (state.get(FACING)){
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.box(1, 0, 1, 4, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.box(12, 0, 1, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case WEST -> Stream.of(
                                Block.box(1, 0, 12, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case EAST -> Stream.of(
                                Block.box(1, 0, 1, 15, 12, 4),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                    };
            case RIGHT ->
                    switch (state.get(FACING)) {
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.box(12, 0, 1, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.box(1, 0, 1, 4, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case WEST -> Stream.of(
                                Block.box(1, 0, 1, 15, 12, 4),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                        case EAST -> Stream.of(
                                Block.box(1, 0, 12, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> Shapes.combineAndSimplify(v1, v2, BooleanOp.OR)).get();
                    };

        };
    }
}
