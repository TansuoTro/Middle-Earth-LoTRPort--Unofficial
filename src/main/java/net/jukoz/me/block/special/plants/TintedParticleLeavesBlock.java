package net.jukoz.me.block.special.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * 1.20.1 backport of the 1.21.x vanilla TintedParticleLeavesBlock:
 * a LeavesBlock that occasionally spawns falling leaf particles below its
 * bottom-most segment. The 1.21.x biome-tinted leaf particle does not exist
 * in 1.20.1, so the vanilla cherry-leaf particle is used instead.
 */
public class TintedParticleLeavesBlock extends LeavesBlock {
    private final float particleChance;

    public TintedParticleLeavesBlock(float particleChance, net.minecraft.block.AbstractBlock.Settings settings) {
        super(settings);
        this.particleChance = particleChance;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextFloat() >= this.particleChance) {
            return;
        }
        BlockPos.Mutable mutable = new BlockPos.Mutable();
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
