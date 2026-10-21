package net.fabricmc.loader.api;

import net.minecraftforge.fml.loading.FMLLoader;

import java.nio.file.Path;

/** Forge compat shim for the parts of FabricLoader the port uses. */
public final class FabricLoader {
    private static final FabricLoader INSTANCE = new FabricLoader();

    private FabricLoader() {}

    public static FabricLoader getInstance() {
        return INSTANCE;
    }

    public boolean isModLoaded(String modid) {
        return net.minecraftforge.fml.ModList.get() != null
                && net.minecraftforge.fml.ModList.get().isLoaded(modid);
    }

    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    public Path getConfigDir() {
        return FMLLoader.getGamePath().resolve("config");
    }

    public Path getGameDir() {
        return FMLLoader.getGamePath();
    }
}
