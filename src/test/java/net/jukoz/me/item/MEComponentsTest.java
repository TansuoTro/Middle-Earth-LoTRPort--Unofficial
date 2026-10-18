package net.jukoz.me.item;

import net.jukoz.me.item.dataComponents.CooldownDataComponent;
import net.jukoz.me.item.dataComponents.FactionDataComponent;
import net.jukoz.me.item.dataComponents.RaceDataComponent;
import net.jukoz.me.item.dataComponents.SneakAttackDataComponent;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Codec round-trips for the ME component shim.
 *
 * <p>Deliberately avoids {@code ItemStack}: building one needs the vanilla Bootstrap,
 * which drags registry initialisation into the test JVM. The encode/decode pair is the
 * part that can actually be wrong; the NBT plumbing around it is three lines.
 */
class MEComponentsTest {

    private static <T> T roundTrip(MEComponent<T> key, T value) {
        NbtElement encoded = MEComponents.encode(key, value);
        assertNotNull(encoded, "encode failed for " + key);
        return MEComponents.decode(key, encoded);
    }

    @Test
    void factionRoundTrip() {
        FactionDataComponent value = new FactionDataComponent(new Identifier("middle-earth", "gondor"));
        FactionDataComponent back = roundTrip(MEComponents.FACTION_DATA, value);
        assertNotNull(back);
        assertEquals(value.getFactionId(), back.getFactionId());
    }

    @Test
    void raceRoundTrip() {
        RaceDataComponent value = new RaceDataComponent(new Identifier("middle-earth", "dwarf"));
        assertEquals(value.raceId(), roundTrip(MEComponents.RACE_DATA, value).raceId());
    }

    @Test
    void temperatureRoundTrip() {
        assertEquals(80, roundTrip(MEComponents.TEMPERATURE_DATA, new TemperatureDataComponent(80)).temperature());
    }

    @Test
    void cooldownRoundTrip() {
        assertEquals(42, roundTrip(MEComponents.COOLDOWN, new CooldownDataComponent(42)).cooldown());
    }

    @Test
    void sneakAttackRoundTrip() {
        assertEquals(7, roundTrip(MEComponents.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(7)).timer());
    }

    @Test
    void componentIdsAreNamespacedUnderTheMod() {
        assertEquals("me:faction", MEComponents.FACTION_DATA.id().toString());
        assertEquals("me:temperature", MEComponents.TEMPERATURE_DATA.id().toString());
    }

    @Test
    void decodeRejectsForeignNbt() {
        assertNull(MEComponents.decode(MEComponents.FACTION_DATA, net.minecraft.nbt.NbtString.of("not an id")));
    }
}
