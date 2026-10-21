package net.jukoz.me.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.artisanTable.ArtisanTableDisplay;
import net.jukoz.me.compat.mcforge.AlloyingDisplay;

/**
 * Retained for the ported donor wiring. REI 12.0 registers displays through the client
 * {@code DisplayRegistry} (see {@link ModREIClientPlugin}); the server plugin only needs
 * to exist and expose the shared category identifiers.
 */
public class REICommonPluginME implements REIServerPlugin {
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE_CATEGORY =
            CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE_CATEGORY =
            CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");
}
