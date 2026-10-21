package net.jukoz.me.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jukoz.me.MiddleEarth;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModParticleTypes {
    public static final DefaultParticleType MALLORN_LEAVES_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType MIRKWOOD_LEAVES_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType ANVIL_SPARK_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType BIOME_FOG_PARTICLE = FabricParticleTypes.simple(true);

    public static void registerParticleTypes() {
        Registry.register(Registries.PARTICLE_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "mallorn_leaves_particles"), MALLORN_LEAVES_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "mirkwood_leaves_particles"), MIRKWOOD_LEAVES_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "anvil_spark_particles"), ANVIL_SPARK_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "biome_fog_particles"), BIOME_FOG_PARTICLE);
    }
}
