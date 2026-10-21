package net.fabricmc.fabric.api.client.particle.v1;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

/**
 * Forge compat shim for Fabric's {@code ParticleFactoryRegistry}. Registrations are buffered and
 * replayed when Forge fires {@link RegisterParticleProvidersEvent}.
 */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class ParticleFactoryRegistry {
    private static final ParticleFactoryRegistry INSTANCE = new ParticleFactoryRegistry();

    private record Simple<T extends ParticleOptions>(ParticleType<T> type, ParticleProvider<T> provider) {}
    private record Sprite<T extends ParticleOptions>(ParticleType<T> type,
                                                     ParticleProvider.Sprite<T> provider) {}

    private final List<Simple<?>> simple = new ArrayList<>();
    private final List<Sprite<?>> sprites = new ArrayList<>();

    private ParticleFactoryRegistry() {}

    public static ParticleFactoryRegistry getInstance() {
        return INSTANCE;
    }

    public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
        simple.add(new Simple<>(type, provider));
    }

    public <T extends ParticleOptions> void register(ParticleType<T> type,
                                                     ParticleProvider.Sprite<T> provider) {
        sprites.add(new Sprite<>(type, provider));
    }

    @SubscribeEvent
    public static void onRegisterProviders(RegisterParticleProvidersEvent event) {
        INSTANCE.simple.forEach(entry ->
                event.register(entry.type(), entry.provider()));
        INSTANCE.sprites.forEach(entry ->
                event.registerSprite(entry.type(), entry.provider()));
    }

    /** Fabric's provider functional interface, kept for API parity. */
    public interface ParticleProviderCompat<T extends ParticleOptions> {
        Particle create(ClientLevel level, double x, double y, double z, T options);
    }
}
