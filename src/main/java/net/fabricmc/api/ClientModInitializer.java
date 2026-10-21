package net.fabricmc.api;

/** Forge compat marker: client init is driven by the Forge client setup event. */
public interface ClientModInitializer {
    void onInitializeClient();
}
