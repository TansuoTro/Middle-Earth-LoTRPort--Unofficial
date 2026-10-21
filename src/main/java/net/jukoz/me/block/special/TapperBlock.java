package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.item.FoodItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TapperBlock extends HorizontalDirectionalBlock {
    public static final int FULL_TAP_LEVEL = 5;
    public static final IntegerProperty TAP_LEVEL = IntegerProperty.create("tap_level", 0, FULL_TAP_LEVEL);
    public static final int RANDOM_TICK_CHANCE = 7;

    public TapperBlock(BlockBehaviour.Properties settings) {
        super(settings.ticksRandomly());

        this.setDefaultState(((this.stateManager.defaultBlockState())
                .with(TAP_LEVEL, 0))
                .with(FACING, Direction.NORTH));
    }

    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return state.get(TAP_LEVEL);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.defaultBlockState().with(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockState behindBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));
        return behindBlock.is(BlockTags.LOGS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(FACING)) {
            case SOUTH -> Block.box(4.0, 0.0, 0.0, 12.0, 10.0, 8.0);
            default -> Block.box(4.0, 0.0, 8.0, 12.0, 10.0, 16.0);
            case WEST -> Block.box(8.0, 0.0, 4.0, 16.0, 10.0, 12.0);
            case EAST -> Block.box(0.0, 0.0, 4.0, 8.0, 10.0, 12.0);
        };
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        int i = state.get(TAP_LEVEL);
        boolean bl = false;
        if (i >= FULL_TAP_LEVEL) {
            Item item = stack.getItem();

            BlockState behindBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));

            Item result = Items.SLIME_BALL;
            if(behindBlock.getBlock() == WoodBlockSets.MAPLE_SET.logBlocks.log() || behindBlock.getBlock() == WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()) {
                result = FoodItemsME.MAPLE_SYRUP;
            } else if (behindBlock.getBlock() == Blocks.BIRCH_LOG) {
                result = FoodItemsME.BIRCH_WATER;
            }

            ItemStack stackResult = new ItemStack(result);
            if(result.equals(Items.SLIME_BALL)) {
                if (!player.getInventory().insertStack(stackResult)) {
                    player.dropItem(stackResult, false);
                }
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }
            else if (stack.is(Items.GLASS_BOTTLE)) {
                if(result != Items.SLIME_BALL) stack.decrement(1);
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.1F);

                if (stack.isEmpty()) {
                    player.setStackInHand(hand, stackResult);
                } else if (!player.getInventory().insertStack(stackResult)) {
                    player.dropItem(stackResult, false);
                }

                bl = true;
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }

            if (!world.isClientSide() && bl) {
                player.incrementStat(Stats.USED.getOrCreateStat(item));
            }
        }

        if (bl) {
            return InteractionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    private void emptyBucket(BlockState state, Level world, BlockPos pos) {
        if(world instanceof ServerLevel serverWorld) {
            serverWorld.setBlock(pos, state.setValue(TAP_LEVEL, 0), 2);
        }
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int rand = world.random.nextInt(RANDOM_TICK_CHANCE);
        if (rand == 0) {
            int i = state.get(TAP_LEVEL);
            if (i < FULL_TAP_LEVEL) {
                world.setBlock(pos, state.setValue(TAP_LEVEL, i + 1), 2);
            }
        }
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAP_LEVEL, FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }
}
