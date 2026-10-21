package net.jukoz.me.utils;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;

public class ParticleManager {
    private static final float RANDOM_PARTICLE_RANGE = 0.25f;
    private static final float PARTICLE_VELOCITY = 0.25f;
    private static final int PARTICLE_QUANTITY = 15;

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType) {
        createParticles(world, pos, particleType, PARTICLE_QUANTITY, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType, int quantity) {
        createParticles(world, pos, particleType, quantity, PARTICLE_VELOCITY, RANDOM_PARTICLE_RANGE);
    }

    public static void createParticles(Level world, Position pos, SimpleParticleType particleType, int quantity, float velocity, float range) {
        for (int i = 0; i < quantity; i++) {
            world.addParticle(particleType,
                    pos.getX() + range * (-0.5f + Math.random()),
                    pos.getY() + Math.random(),
                    pos.getZ() + range * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()),
                    velocity * (-0.5f + Math.random()));
        }
    }
}
