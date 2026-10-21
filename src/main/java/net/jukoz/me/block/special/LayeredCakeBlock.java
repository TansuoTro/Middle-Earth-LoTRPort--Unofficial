package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.jukoz.me.compat.MEBlockShapes;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LayeredCakeBlock extends Block {
    public static final int MAX_BITES = 8;
    public static final IntProperty BITES;
    public static final int DEFAULT_COMPARATOR_OUTPUT;
    private static final VoxelShape[] SHAPES_BY_BITES;



    public LayeredCakeBlock(BlockBehaviour.Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_BITES[state.get(BITES)];
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        if (stack.isIn(ItemTags.CANDLES) && (Integer)state.get(BITES) == 0) {
            Block var10 = Block.getBlockFromItem(item);
            if (var10 instanceof CandleBlock) {
                CandleBlock candleBlock = (CandleBlock)var10;
                if (!player.isCreative()) stack.decrement(1);
                world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, CandleLayeredCakeBlock.getCandleCakeFromCandle(candleBlock));
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                return InteractionResult.SUCCESS;
            }
        }

        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return InteractionResult.SUCCESS;
            }

            if (player.getStackInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return tryEat(world, pos, state, player);
    }

    protected static InteractionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, Player player) {
        if (!player.canConsume(false)) {
            return InteractionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(3, 0.15F);
            int i = state.get(BITES);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            if (i < MAX_BITES) {
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return getComparatorOutput(state.get(BITES));
    }

    public static int getComparatorOutput(int bites) {
        return (MAX_BITES - bites) * 2;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public boolean canPathfindThrough(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        BITES = IntProperty.of("bites", 0, MAX_BITES);;
        DEFAULT_COMPARATOR_OUTPUT = getComparatorOutput(0);
        SHAPES_BY_BITES = MEBlockShapes.createShapeArray(MAX_BITES, (bites) -> {
            return VoxelShapes.union(Block.createCuboidShape(0, 0.0, 0, 16.0, 8.0, 16.0 - (Math.max(0, Math.min(4, bites - 4)) * 4) + Math.max(0, Math.min(1, bites - 4)) * 2),
                    Block.createCuboidShape(2, 8, 2, 14, 16, 14 - (Math.max(0, Math.min(4, bites)) * 3)));
        });
    }
}
