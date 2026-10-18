package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WebbingBlock extends MultifaceGrowthBlock {
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
    private final LichenGrower grower = new LichenGrower(this);

    public WebbingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, true));
    }



    @Override
    public LichenGrower getGrower() {
        return this.grower;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
        super.appendProperties(builder);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Vec3d vec3d = new Vec3d(0.75, 0.50, 0.75);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(StatusEffects.WEAVING)) {
                vec3d = new Vec3d(0.90, 0.75, 0.90);
            }
        }

        entity.slowMovement(state, vec3d);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.offset(direction)).isIn(net.minecraft.registry.tag.FluidTags.WATER)) {
                    world.breakBlock(pos, true);
                    return;
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, fromPos, notify);
    }

    public boolean hasRandomTicks(BlockState state) {
        return !(Boolean)state.get(PERSISTENT);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldDecay(state)) {
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return !(Boolean)state.get(PERSISTENT);
    }
}
