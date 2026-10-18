package net.jukoz.me.item;

import net.jukoz.me.item.dataComponents.FactionDataComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The donor writes {@code new Item.Settings().maxCount(1).component(KEY, VALUE)}; every
 * vanilla method must keep returning MESettings or the chain stops compiling.
 *
 * <p>Deliberately stops at the Settings object. Building an {@code Item} forces
 * {@code Item}'s static initialiser, which walks {@code RegistryKeys} and needs the full
 * vanilla Bootstrap; in a loom test JVM that also hits an {@code IllegalAccessError}
 * between the named and remapped jars. The stack-side resolution is covered by the dev
 * client smoke test instead.
 */
class MESettingsTest {

    @Test
    void chainStaysMESettingsThroughVanillaCalls() {
        MESettings settings = new MESettings()
                .maxCount(1)
                .maxDamage(12)
                .maxDamageIfAbsent(8)
                .fireproof()
                .rarity(Rarity.RARE);

        assertInstanceOf(MESettings.class, settings);
        assertTrue(settings.components().isEmpty());
    }

    @Test
    void componentIsRecordedOnTheSettings() {
        FactionDataComponent faction = new FactionDataComponent(new Identifier("middle-earth", "gondor"));

        MESettings settings = new MESettings().maxCount(1).component(MEComponents.FACTION_DATA, faction);

        assertEquals(1, settings.components().size());
        assertEquals(faction, settings.components().get(MEComponents.FACTION_DATA));
    }

    @Test
    void componentsAreImmutableFromOutside() {
        MESettings settings = new MESettings()
                .component(MEComponents.FACTION_DATA, new FactionDataComponent(new Identifier("middle-earth", "rohan")));

        assertEquals(1, settings.components().size());
        try {
            settings.components().clear();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("components() must be read-only");
    }
}
