package net.jukoz.me.api.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.api.SevenStarsApi;

import java.util.Optional;

public class MemoryModulesAPI<U> {
    public static final MemoryModuleType<Boolean> DEFENDING_HOME = register("defending_home", Codec.BOOL);
    public static final MemoryModuleType<BlockPos> SHELTER_POS = register("shelter_pos");

    private final Optional<Codec<Memory<U>>> codec;

    @VisibleForTesting
    public MemoryModulesAPI(Optional<Codec<U>> codec) {
        this.codec = codec.map(Memory::createCodec);
    }

    public Optional<Codec<Memory<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, new Identifier(SevenStarsApi.MOD_ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, new Identifier(SevenStarsApi.MOD_ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
