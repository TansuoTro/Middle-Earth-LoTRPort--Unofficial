package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 1.20.1 backport of the 1.21.x vanilla TintedParticleLeavesBlock:
 * a LeavesBlock that occasionally spawns falling leaf particles below its
 * bottom-most segment. The 1.21.x biome-tinted leaf particle does not exist
 * in 1.20.1, so the vanilla cherry-leaf particle is used instead.
 */
public class TintedParticleLeavesBlock extends LeavesBlock {
    private final float particleChance;

    public TintedParticleLeavesBlock(float particleChance, net.minecraft.world.level.block.state.BlockBehaviour.Properties settings) {
        super(settings);
        this.particleChance = particleChance;
    }

    @Override
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextFloat() >= this.particleChance) {
            return;
        }
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        mutable.set(pos, Direction.DOWN);
        BlockState below = world.getBlockState(mutable);
        while (below.getBlock() instanceof TintedParticleLeavesBlock && mutable.getY() > world.getBottomY()) {
            mutable.move(Direction.DOWN);
            below = world.getBlockState(mutable);
        }
        if (below.isAir()) {
            world.addParticle(ParticleTypes.CHERRY_LEAVES,
                    mutable.getX() + random.nextDouble(),
                    mutable.getY() + random.nextDouble() * 0.5,
                    mutable.getZ() + random.nextDouble(),
                    0.0, -0.1, 0.0);
        }
    }
}
