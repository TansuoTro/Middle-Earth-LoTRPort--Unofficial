package net.jukoz.me.block.special.plate;
import net.minecraft.world.WorldAccess;
import net.minecraft.util.Hand;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.compat.MELootTable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.TickPriority;
import net.jukoz.me.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BlockWithEntity {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty UTENSILS = BooleanProperty.of("utensils");

    public PlateBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(UTENSILS, false));
    }



    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(world.getBlockEntity(pos) instanceof PlateBlockEntity plateBlockEntity && world instanceof ServerWorld serverWorld) {
            MELootTable.Loot loot = MELootTable.get(itemStack);
            if(loot != null) {
                plateBlockEntity.setLootTable(loot.table(), loot.seed());
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if(world instanceof ServerWorld serverWorld && world.getBlockEntity(pos) instanceof PlateBlockEntity) {
            serverWorld.scheduleBlockTick(pos, this, 1, TickPriority.NORMAL);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if(world.getBlockEntity(pos) instanceof PlateBlockEntity plateBlockEntity) {
            if(plateBlockEntity.isBlockPlaced()) {
                plateBlockEntity.generateItem(world);
            }
            plateBlockEntity.setBlockPlaced();
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockEntity(pos) instanceof PlateBlockEntity plateBlockEntity && plateBlockEntity.isBlockPlaced()) {
            plateBlockEntity.generateItem(world);
        }
        super.randomTick(state, world, pos, random);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return checkType(type, ModBlockEntities.PLATE, PlateBlockEntity::tick);
        }
        return super.getTicker(world, state, type);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ?
                Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PlateBlock) {
            return ActionResult.PASS;
        }
        if(!stack.isEmpty() && stack.getItem().isFood()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof PlateBlockEntity plateBlockEntity) {
                ItemStack plateStack = plateBlockEntity.getStack();
                if(plateStack == null || plateStack.isEmpty()) {
                    ItemStack insertedStack = stack.copy();
                    insertedStack.setCount(1);
                    plateBlockEntity.setStack(insertedStack);
                    if (!player.isCreative()) stack.decrement(1);
                    return ActionResult.SUCCESS;
                } else if (world.isClient) {
                    return onEat(world, pos, player);
                }
            } else {
                return ActionResult.FAIL;
            }
        }
        ActionResult result = onEat(world, pos, player);
        if(result == ActionResult.PASS || result == ActionResult.FAIL) {
            if(world instanceof ServerWorld serverWorld) {
                boolean hasUtensils = state.get(UTENSILS);
                serverWorld.setBlockState(pos, state.with(UTENSILS, !hasUtensils), 2);
            }
        }

        return result;
    }

    protected static ActionResult onEat(World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient) {
            if (tryEat(world, pos, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, player);
    }

    protected static ActionResult tryEat(World world, BlockPos pos, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }

        if(!(world.getBlockEntity(pos) instanceof PlateBlockEntity plateBlockEntity)) return ActionResult.FAIL;

        ItemStack food = plateBlockEntity.getStack();
        if(food == null || food.isEmpty()) {
            return ActionResult.PASS;
        }

        FoodComponent foodComponent = food.getItem().getFoodComponent();

        if(foodComponent != null) {
            if(!world.isClient) {
                player.getHungerManager().add((foodComponent.getHunger() / 2), foodComponent.getSaturationModifier() / 2);
            }
            world.emitGameEvent(player, GameEvent.EAT, pos);

            net.minecraft.item.Item remainderItem = food.getItem().getRecipeRemainder();
            plateBlockEntity.setStack(remainderItem == null ? ItemStack.EMPTY : new ItemStack(remainderItem));
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, UTENSILS);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(UTENSILS, false);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlateBlockEntity(pos, state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}
