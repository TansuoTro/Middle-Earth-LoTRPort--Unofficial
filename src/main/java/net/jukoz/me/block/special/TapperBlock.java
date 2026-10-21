package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.item.FoodItemsME;

public class TapperBlock extends HorizontalFacingBlock {
    public static final int FULL_TAP_LEVEL = 5;
    public static final IntProperty TAP_LEVEL = IntProperty.of("tap_level", 0, FULL_TAP_LEVEL);
    public static final int RANDOM_TICK_CHANCE = 7;

    public TapperBlock(Settings settings) {
        super(settings.ticksRandomly());

        this.setDefaultState(((this.stateManager.getDefaultState())
                .with(TAP_LEVEL, 0))
                .with(FACING, Direction.NORTH));
    }

    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return state.get(TAP_LEVEL);
    }



    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState behindBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));
        return behindBlock.isIn(BlockTags.LOGS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case SOUTH -> Block.createCuboidShape(4.0, 0.0, 0.0, 12.0, 10.0, 8.0);
            default -> Block.createCuboidShape(4.0, 0.0, 8.0, 12.0, 10.0, 16.0);
            case WEST -> Block.createCuboidShape(8.0, 0.0, 4.0, 16.0, 10.0, 12.0);
            case EAST -> Block.createCuboidShape(0.0, 0.0, 4.0, 8.0, 10.0, 12.0);
        };
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
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
            else if (stack.isOf(Items.GLASS_BOTTLE)) {
                if(result != Items.SLIME_BALL) stack.decrement(1);
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.1F);

                if (stack.isEmpty()) {
                    player.setStackInHand(hand, stackResult);
                } else if (!player.getInventory().insertStack(stackResult)) {
                    player.dropItem(stackResult, false);
                }

                bl = true;
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }

            if (!world.isClient() && bl) {
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
            serverWorld.setBlockState(pos, state.with(TAP_LEVEL, 0), 2);
        }
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int rand = world.random.nextInt(RANDOM_TICK_CHANCE);
        if (rand == 0) {
            int i = state.get(TAP_LEVEL);
            if (i < FULL_TAP_LEVEL) {
                world.setBlockState(pos, state.with(TAP_LEVEL, i + 1), 2);
            }
        }
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAP_LEVEL, FACING);
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
