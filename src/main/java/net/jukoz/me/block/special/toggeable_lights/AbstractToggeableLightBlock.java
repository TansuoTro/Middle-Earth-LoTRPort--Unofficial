package net.jukoz.me.block.special.toggeable_lights;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.*;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public abstract class AbstractToggeableLightBlock extends Block {
    public static final BooleanProperty LIT;
    public static final IntProperty LEVEL_15;
    public static final BooleanProperty WATERLOGGED;
    protected AbstractToggeableLightBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL_15, LIT, WATERLOGGED);
    }
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return null;
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockRenderView renderView, BlockPos pos, Direction side, @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return addNbtForLevel(super.getPickStack(world, pos, state), (Integer)state.get(LEVEL_15));
    }

    public static ItemStack addNbtForLevel(ItemStack stack, int level) {
        if (level != 15) {
            CompoundTag nbtCompound = new CompoundTag();
            nbtCompound.putString(LEVEL_15.getName(), String.valueOf(level));
            stack.setSubNbt("BlockStateTag", nbtCompound);
        }
        return stack;
    }

    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = attachedDirection(state).getOpposite();
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            extinguish(null, state, world, pos);
        }

        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected abstract Direction attachedDirection(BlockState state);

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }

    @Override
    public InteractionResult onUse(BlockState state, World world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            if(player.isCreative()){
                world.setBlockState(pos, state.cycle(LIT));
            } else {
                ItemStack itemStack = player.getStackInHand(hand);
                if (state.get(LIT) && itemStack.isIn(ItemTags.SHOVELS)) {
                    extinguish(null, state, world, pos);
                } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean emitsLight(BlockState state){
        return state.get(LIT);
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit).cycle(LEVEL_15), 2 | 3);
        if(lit){
            world.playSound((Player)null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound((Player)null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.5F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    static {
        LIT = Properties.LIT;
        LEVEL_15 = Properties.LEVEL_15;
        WATERLOGGED = Properties.WATERLOGGED;
    }

}
