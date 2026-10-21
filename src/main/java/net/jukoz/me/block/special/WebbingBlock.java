package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WebbingBlock extends MultifaceBlock {
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public WebbingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.defaultBlockState().with(PERSISTENT, true));
    }

    @Override
    public MultifaceSpreader getGrower() {
        return this.grower;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
        super.appendProperties(builder);
    }

    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3d = new Vec3(0.75, 0.50, 0.75);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(MobEffects.SLOWNESS)) {
                vec3d = new Vec3(0.90, 0.75, 0.90);
            }
        }

        entity.slowMovement(state, vec3d);
    }

    @Override
    public void neighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos fromPos, boolean notify) {
        if (!world.isClientSide) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.offset(direction)).is(net.minecraft.tags.FluidTags.WATER)) {
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

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (this.shouldDecay(state)) {
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return !(Boolean)state.get(PERSISTENT);
    }
}
