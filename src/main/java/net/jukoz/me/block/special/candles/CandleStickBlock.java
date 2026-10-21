package net.jukoz.me.block.special.candles;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.InteractionHand;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class CandleStickBlock extends Block {
    public static final EnumProperty<Direction> HORIZONTAL_FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty CANDLES = IntProperty.of("candles", 1, 4);
    public static final BooleanProperty ATTACHED;
    public static final BooleanProperty LIT;
    private static final Int2ObjectMap CANDLES_TO_PARTICLE_OFFSETS;
    private static final Int2ObjectMap WALL_CANDLES_TO_PARTICLE_OFFSETS;

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape SHAPE;

    public CandleStickBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(ATTACHED, false).with(CANDLES, 1)
                .with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, ATTACHED, CANDLES, HORIZONTAL_FACING);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
                if(ctx.getSide() != Direction.UP) {
                    blockState = blockState.with(ATTACHED, true);
                }
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ?
                Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(state.get(ATTACHED)) {
            return Block.sideCoversSmallSquare(world, pos.offset(state.get(HORIZONTAL_FACING).getOpposite()), state.get(HORIZONTAL_FACING));
        } else return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);
        if (heldStack.getItem() == this.asItem() && state.get(CANDLES) < 4) {
            if (!player.isCreative()) heldStack.decrement(1);
            world.setBlockState(pos, state.cycle(CANDLES));
            return ActionResult.CONSUME;
        }
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            ItemStack itemStack = player.getStackInHand(hand);
            if(player.isCreative() && itemStack == ItemStack.EMPTY){
                world.setBlockState(pos, state.cycle(LIT));
                return ActionResult.SUCCESS;
            }
            if (state.get(LIT) && itemStack.isIn(ItemTags.SHOVELS)) {
                extinguish(null, state, world, pos);
            } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                setLit(world, state, pos, true);
            }
        }
        return ActionResult.SUCCESS;
    }

    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.get(LIT)) {
            this.getParticleOffsets(state).forEach((offset) -> {
                int quarter = state.get(HORIZONTAL_FACING).getHorizontal();
                Vec3 rotatedOffset = offset.add(-0.5, -0.5, -0.5).rotateY(quarter * -90 * ((float)Math.PI / 180)).add(0.5, 0.5, 0.5);
                spawnCandleParticles(world, rotatedOffset.add(pos.getX(), pos.getY(), pos.getZ()), random);
            });
        }
    }

    private static void spawnCandleParticles(Level world, Vec3 vec3d, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.5F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.getDefaultState();
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && !state.get(LIT)) {
            world.setBlockState(hit.getBlockPos(), state.with(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        if(state.get(ATTACHED)) return (Iterable)WALL_CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES));
        else return (Iterable)CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected Direction attachedDirection(BlockState state) {
        return state.get(ATTACHED) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    static {
        ATTACHED = BooleanProperty.of("attached");
        LIT = BlockStateProperties.LIT;
        STATE_TO_LUMINANCE = (state) -> state.get(LIT) ? (int)(state.get(CANDLES) * 3.5f) : 0;
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(new Int2ObjectOpenHashMap(4), (int2ObjectOpenHashMap) -> {
            int2ObjectOpenHashMap.put(1, List.of((new Vec3(8.0, 17.0, 8.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(2, List.of((new Vec3(5.0, 17.0, 8.0)).multiply(0.0625), (new Vec3(11.0, 17.0, 8.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(3, List.of((new Vec3(8.0, 17.0, 8.0)).multiply(0.0625), (new Vec3(3.0, 17.0, 8.0)).multiply(0.0625), (new Vec3(13.0, 17.0, 8.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(4, List.of((new Vec3(8.0, 17.0, 8.0)).multiply(0.0625), (new Vec3(3.0, 16.0, 8.0)).multiply(0.0625),
                    (new Vec3(13.0, 16.0, 8.0)).multiply(0.0625), (new Vec3(8.0, 16.0, 3.0)).multiply(0.0625), (new Vec3(8.0, 16.0, 13.0)).multiply(0.0625)));
        });
        WALL_CANDLES_TO_PARTICLE_OFFSETS = Util.make(new Int2ObjectOpenHashMap(4), (int2ObjectOpenHashMap) -> {
            int2ObjectOpenHashMap.put(1, List.of((new Vec3(8.0, 17.0, 7.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(2, List.of((new Vec3(3.0, 17.0, 6.0)).multiply(0.0625), (new Vec3(13.0, 17.0, 6.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(3, List.of((new Vec3(8.0, 14.0, 8.0)).multiply(0.0625), (new Vec3(3.0, 17.0, 6.0)).multiply(0.0625), (new Vec3(13.0, 17.0, 6.0)).multiply(0.0625)));
            int2ObjectOpenHashMap.put(4, List.of((new Vec3(3.0, 17.0, 5.0)).multiply(0.0625), (new Vec3(13.0, 17.0, 5)).multiply(0.0625),
                    (new Vec3(4.0, 14.0, 10.0)).multiply(0.0625), (new Vec3(12.0, 14.0, 10.0)).multiply(0.0625)));
        });
        SHAPE = Block.createCuboidShape(4, 0, 4, 12, 15, 12);
    }
}
