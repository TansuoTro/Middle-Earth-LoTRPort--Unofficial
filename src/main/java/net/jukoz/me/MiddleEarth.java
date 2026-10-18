package net.jukoz.me;

import net.fabricmc.api.ModInitializer;
import net.jukoz.me.api.utils.IdentifierUtil;
import net.jukoz.me.api.utils.ModLogger;
import net.minecraft.util.Identifier;
import net.jukoz.me.block.*;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.registries.ModRegistries;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.LootModifiers;
import net.jukoz.me.utils.commands.ModCommandRegistry;
import net.jukoz.me.utils.resources.FileUtils;
import net.jukoz.me.world.map.MiddleEarthMapGeneration;
import net.jukoz.me.world.gen.ModWorldGeneration;
import net.jukoz.me.world.spawners.ModEntitySpawning;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.dimension.ModDimensions;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";

    /**
     * The namespace the mod used before the port. {@code RegistriesME.registerAliases} rewrites
     * every {@code middle-earth:} id in an existing world to its {@code me:} equivalent.
     */
    public static final String OLD_MOD_ID = "middle-earth";
	public static final String MOD_VERSION = "alpha-1.4.0-1.20.1";
	public static final boolean IS_DEBUG = true;

	/**
	 * The 1.21 tree replaced the old instance-scoped LoggerUtil with a static logger
	 * reachable as {@code MiddleEarth.LOGGER}. Keeping both means ported files compile
	 * against the static field while the 1.20.1 initialiser keeps using its instance.
	 */
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);

	private LoggerUtil loggerUtil;
	@Override
	public void onInitialize() {
		new FileUtils(getClass().getClassLoader());
		loggerUtil = LoggerUtil.getInstance();

		loggerUtil.logInfoMsg("");
		loggerUtil.logInfoMsg("================ MiddleEarth ================");

		ModCommandRegistry.register();
		ModStatusEffects.registerStatusEffects();
		OreRockSets.registerModBlockSets();
		ModWeaponItems.registerModItems();
		ModEquipmentItems.registerModItems();
		ModToolItems.registerModItems();
		ModFoodItems.registerModItems();
		ModResourceItems.registerModItems();
		ModEggItems.registerModItems();
		ModItemGroups.register();

		WoodBlockSets.registerModBlockSets();
		MushroomBlockSets.registerModBlockSets();
		StoneBlockSets.registerModBlockSets();
		ModDecorativeItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		RoofBlockSets.registerModBlockSets();

		ModRegistries.registerFuels();
		ModRegistries.registerFlammableBlocks();
		ModRegistries.registerAgingCopperBlocks();
		ModRegistries.registerComposterBlocks();
		ModRegistries.registerCauldronBehaviour();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();
		ModParticleTypes.registerParticleTypes();

		ModDimensions.register();
		MEBiomeKeys.registerModBiomes();
		EnchantmentsME.register();
		MEBiomesData.loadBiomes();
		ModWorldGeneration.generateModWorldGen();

		LootModifiers.modifyLootTables();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ------------------------------------------------------------------
	// Identifier helpers introduced by the 1.21 rewrite. Ported files call
	// MiddleEarth.of(...) in place of new Identifier(MOD_ID, ...) everywhere,
	// so the helpers live here rather than being inlined at ~400 call sites.
	// ------------------------------------------------------------------

	public static Identifier fetchId(String stringId) {
		return IdentifierUtil.getIdentifierFromString(stringId);
	}

	public static Identifier ofPrefix(Identifier base, Identifier prefixId) {
		if (base == null) return null;
		return base.withPrefixedPath(String.format("%s/", prefixId.getPath()));
	}

	public static Identifier of(String path) {
		return IdentifierUtil.build(MOD_ID, path);
	}

	public static Identifier of(String... names) {
		return IdentifierUtil.buildAggregate(MOD_ID, names);
	}

	public static Identifier of(char splitter, String... names) {
		return IdentifierUtil.build(MOD_ID, createAggregate(splitter, names));
	}

	public static Identifier ofPath(String... names) {
		return IdentifierUtil.build(MOD_ID, createAggregate('/', names));
	}

	public static Identifier ofVanillaPath(String... names) {
		return IdentifierUtil.ofVanilla(createAggregate('/', names));
	}

	public static Identifier append(Identifier base, String suffix) {
		return new Identifier(base.toString() + suffix);
	}

	public static String createAggregate(char splitter, String... names) {
		return IdentifierUtil.createAggregateValue(splitter, names);
	}

	public static boolean compareId(Identifier id1, Identifier id2) {
		return id1.compareTo(id2) == 0;
	}
}

