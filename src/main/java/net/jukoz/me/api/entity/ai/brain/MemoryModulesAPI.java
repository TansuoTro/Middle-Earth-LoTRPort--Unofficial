package net.jukoz.me.api.entity.ai.brain;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.jukoz.me.api.SevenStarsApi;
import java.util.Optional;

public class MemoryModulesAPI<U> {
    public static final MemoryModuleType<Boolean> DEFENDING_HOME = register("defending_home", Codec.BOOL);
    public static final MemoryModuleType<BlockPos> SHELTER_POS = register("shelter_pos");

    private final Optional<Codec<ExpirableValue<U>>> codec;

    @VisibleForTesting
    public MemoryModulesAPI(Optional<Codec<U>> codec) {
        this.codec = codec.map(ExpirableValue::createCodec);
    }

    public Optional<Codec<ExpirableValue<U>>> getCodec() {
        return this.codec;
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, new ResourceLocation(SevenStarsApi.MOD_ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, new ResourceLocation(SevenStarsApi.MOD_ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void registerModMemoryModules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
