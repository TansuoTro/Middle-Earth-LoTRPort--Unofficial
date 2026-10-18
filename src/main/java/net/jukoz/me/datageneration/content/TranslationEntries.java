package net.jukoz.me.datageneration.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection points for the language provider.
 *
 * <p>The donor's registration code records every item, block, entity, ... it creates so the
 * datagen language provider can emit a translation key for each one. Datagen is deferred in
 * this port (the 46 provider classes are still in `port-staging`), but the registration code
 * still calls into here, so the lists exist as a plain stub. When datagen comes back, the
 * provider walks these lists exactly as upstream does.
 *
 * <p>The lists are deliberately untyped: the donor adds {@code Item}, {@code Block},
 * {@code EntityType} and {@code Identifier} values to the same list.
 */
public final class TranslationEntries {

    public static final List<Object> itemEntries = new ArrayList<>();
    public static final List<Object> blockEntries = new ArrayList<>();
    public static final List<Object> spawnEggEntries = new ArrayList<>();
    public static final List<Object> structureManagerEntries = new ArrayList<>();
    public static final List<Object> spawnEntries = new ArrayList<>();
    public static final List<Object> raceEntries = new ArrayList<>();
    public static final List<Object> npcTypeEntries = new ArrayList<>();
    public static final List<Object> inscriptionEntries = new ArrayList<>();
    public static final List<Object> factionEntries = new ArrayList<>();
    public static final List<Object> entityEntries = new ArrayList<>();
    public static final List<Object> bannerPatternEntries = new ArrayList<>();
    public static final List<Object> biomeEntries = new ArrayList<>();

    private TranslationEntries() {
    }
}
