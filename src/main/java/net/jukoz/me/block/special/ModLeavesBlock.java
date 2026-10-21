package net.jukoz.me.block.special;

import net.jukoz.me.particles.ModParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import java.util.OptionalInt;

public class ModLeavesBlock extends LeavesBlock {
    final protected boolean castShadow;
    private final ParticleOptions particleType;
    public ModLeavesBlock(Settings settings, boolean castShadow, ParticleOptions particleType) {
        super(settings);
        this.castShadow = castShadow;
        this.particleType = particleType;
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, updateDistanceFromLogs(state, world, pos), (Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        int i;
        if (state.get(WATERLOGGED).booleanValue()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if ((i = getDistanceFromLog(neighborState) + 1) != 1 || state.get(DISTANCE) != i) {
            world.scheduleRecheck(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, LevelAccessor world, BlockPos pos) {
        int i = 7;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    if(x == 0 && y == 0 && z == 0) continue;
                    mutable.set(pos, x, y, z);
                    i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
                    if (i == 1) break;
                }
            }
        }

        return state.setValue(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState state) {
        return LeavesBlock.getOptionalDistanceAt(state).orElse(7);
    }

    @Override
    public int getOpacity(BlockState state, BlockGetter world, BlockPos pos) {
        if(castShadow) return super.getOpacity(state, world, pos);
        return 0;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = (this.defaultBlockState().with(PERSISTENT, true)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
    }

    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.randomDisplayTick(state, world, pos, random);
        if(this.particleType != null){
            if (random.nextInt(10) == 0) {
                BlockPos blockPos = pos.below();
                BlockState blockState = world.getBlockState(blockPos);
                if (!isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
                    ParticleUtils.spawnParticle(world, pos, random, this.particleType);
                }
            }
        }

    }
}
