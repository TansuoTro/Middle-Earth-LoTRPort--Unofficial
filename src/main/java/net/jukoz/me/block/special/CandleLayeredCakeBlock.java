package net.jukoz.me.block.special;

import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.LevelView;
import net.jukoz.me.block.registration.ModBlocks;

import java.util.List;
import java.util.Map;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CandleLayeredCakeBlock extends AbstractCandleBlock {
    public static final BooleanProperty LIT;
    private static final VoxelShape SHAPE;
    private static final Map<CandleBlock, CandleLayeredCakeBlock> CANDLES_TO_CANDLE_CAKES;
    private static final Iterable<Vec3> PARTICLE_OFFSETS;
    private final CandleBlock candle;



    public CandleLayeredCakeBlock(Block candle, BlockBehaviour.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, false));
        if (candle instanceof CandleBlock candleBlock) {
            CANDLES_TO_CANDLE_CAKES.put(candleBlock, this);
            this.candle = candleBlock;
        } else {
            String var10002 = String.valueOf(CandleBlock.class);
            throw new IllegalArgumentException("Expected block to be of " + var10002 + " was " + String.valueOf(candle.getClass()));
        }
    }

    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.FLINT_AND_STEEL) || stack.isOf(Items.FIRE_CHARGE)) {
            if(canBeLit(state)) {
                setLit(world, state, pos, true);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        } else {
            if (isHittingCandle(hit) && stack.isEmpty() && (Boolean)state.get(LIT)) {
                extinguish(player, state, world, pos);
                return InteractionResult.SUCCESS;
            } else {
                InteractionResult actionResult = LayeredCakeBlock.tryEat(world, pos, ModBlocks.LAYERED_CAKE.getDefaultState(), player);
                if (actionResult.isAccepted()) {
                    dropStacks(state, world, pos);
                }
                return actionResult;
            }
        }
    }

    private static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 11);
    }

    private static boolean isHittingCandle(BlockHitResult hitResult) {
        return hitResult.getPos().y - (double)hitResult.getBlockPos().getY() > 0.5;
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT});
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(ModBlocks.LAYERED_CAKE);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return CakeBlock.DEFAULT_COMPARATOR_OUTPUT;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public boolean canPathfindThrough(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    public static BlockState getCandleCakeFromCandle(CandleBlock candle) {
        return ((CandleLayeredCakeBlock)CANDLES_TO_CANDLE_CAKES.get(candle)).getDefaultState();
    }

    public static boolean canBeLit(BlockState state) {
        return state.contains(LIT) && !(Boolean)state.get(LIT);
    }

    static {
        LIT = AbstractCandleBlock.LIT;
        SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0.0, 0, 16.0, 8.0, 16.0),
                Block.createCuboidShape(2, 8, 2, 14, 16, 14));
        CANDLES_TO_CANDLE_CAKES = Maps.newHashMap();
        PARTICLE_OFFSETS = List.of((new Vec3(5.0, 23, 5.0)).multiply(0.0625),
                (new Vec3(5.0, 23, 11.0)).multiply(0.0625),
                (new Vec3(11.0, 23, 5.0)).multiply(0.0625),
                (new Vec3(11.0, 23, 11.0)).multiply(0.0625)
        );
    }
}
