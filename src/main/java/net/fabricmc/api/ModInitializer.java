package net.fabricmc.api;

/** Forge compat marker: the mod entry point now lives on the Forge {@code @Mod} constructor. */
public interface ModInitializer {
    void onInitialize();
}
