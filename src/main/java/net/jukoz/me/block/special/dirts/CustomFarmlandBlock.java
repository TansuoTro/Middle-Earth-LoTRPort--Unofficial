package net.jukoz.me.block.special.dirts;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class CustomFarmlandBlock extends FarmlandBlock {

    private final Block target;

    public CustomFarmlandBlock(Settings settings, Block target) {
        super(settings);
        this.target = target;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? target.getDefaultState() : super.getPlacementState(ctx);
    }

    public static void setToDirt(@Nullable Entity entity, BlockState state, Level world, BlockPos pos, Block target) {
        BlockState blockState = pushEntitiesUpBeforeBlockChange(state, target.getDefaultState(), world, pos);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, blockState));
    }

    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canPlaceAt(world, pos)) {
            setToDirt((Entity)null, state, world, pos, target);
        }

    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = (Integer)state.get(MOISTURE);
        if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, (BlockState)state.with(MOISTURE, i - 1), 2);
            } else if (!hasCrop(world, pos)) {
                setToDirt((Entity)null, state, world, pos, target);
            }
        } else if (i < 7) {
            world.setBlockState(pos, (BlockState)state.with(MOISTURE, 7), 2);
        }

    }

    public void onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (world instanceof ServerLevel serverWorld) {
            if ((double)world.random.nextFloat() < fallDistance - 0.5 && entity instanceof LivingEntity && (entity instanceof Player || serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
                setToDirt((Entity)null, state, world, pos, target);
            }
        }
        entity.handleFallDamage(fallDistance, 1.0F, entity.getDamageSources().fall());
    }

    private static boolean hasCrop(BlockGetter world, BlockPos pos) {
        return world.getBlockState(pos.up()).isIn(BlockTags.MAINTAINS_FARMLAND);
    }

    private static boolean isWaterNearby(WorldView world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!world.getFluidState(blockPos).isIn(FluidTags.WATER));

        return true;
    }
}
