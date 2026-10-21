package net.fabricmc.fabric.api.particle.v1;

import net.minecraft.core.particles.SimpleParticleType;

/** Forge compat shim for Fabric's {@code FabricParticleTypes}. */
public final class FabricParticleTypes {
    private FabricParticleTypes() {}

    public static SimpleParticleType simple() {
        return new SimpleParticleType(false);
    }

    public static SimpleParticleType simple(boolean alwaysSpawn) {
        return new SimpleParticleType(alwaysSpawn);
    }
}
