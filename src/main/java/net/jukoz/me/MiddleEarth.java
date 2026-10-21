package net.jukoz.me;

import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.api.utils.IdentifierUtil;
import net.jukoz.me.api.utils.ModLogger;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.OreRockSets;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.block.MushroomBlockSets;
import net.jukoz.me.block.RoofBlockSets;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.registries.RegistriesME;
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

@Mod(MiddleEarth.MOD_ID)
public class MiddleEarth {
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

	public MiddleEarth() {
		new FileUtils(getClass().getClassLoader());
		loggerUtil = LoggerUtil.getInstance();

		loggerUtil.logInfoMsg("");
                loggerUtil.logInfoMsg("================ MiddleEarth ================");

                // The donor shipped these brain registries as a separate sevenstars-api mod.
                // In the port they live in-tree, so their initialiser has to run before any
                // entity class touches a lazily-registered memory module (registry freeze).
                new net.jukoz.me.api.SevenStarsApi().onInitialize();

                // Custom tracked-data handlers and dynamic registries have to exist before any
                // entity class initialises, otherwise the 1.21 donor's variant lookups and the
                // entity tracker serialisers have nothing to resolve against.
                net.jukoz.me.entity.TrackedDataHandlerRegistryME.register();
                net.jukoz.me.registries.DynamicRegistriesME.register();
                net.jukoz.me.network.ModServerNetworkHandler.register(new net.jukoz.me.network.connections.ConnectionToClient());

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
// RoofBlockSets still owns content that WoodBlockSets/GenericBlockSets do not create
// (charred shingles, tiling, thatch). ModBlocks.registerBlock now reuses an already
// registered id instead of throwing, so loading it is safe.
        RoofBlockSets.registerModBlockSets();

		RegistriesME.registerFuels();
		RegistriesME.registerToolTipAppenders();
		RegistriesME.registerFlammableBlocks();
		RegistriesME.registerTillableBlocks();
		RegistriesME.registerAgingCopperBlocks();
		RegistriesME.registerComposterBlocks();
		RegistriesME.registerCauldronBehaviour();
		RegistriesME.registerLandPathNodeTypesBlocks();
		RegistriesME.registerRegistryAliases();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();net.jukoz.me.recipe.RecipesME.registerRecipes();

		ModEntities.registerModEntities();net.jukoz.me.entity.EntitiesME.registerModEntities();net.jukoz.me.entity.ai.brain.SensorsME.registerModSensors();net.jukoz.me.entity.ai.brain.ActivitiesME.registerModActivities();net.jukoz.me.entity.ai.brain.MemoryModulesME.registerModMemoryModules();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();net.jukoz.me.sound.SoundsME.registerModSounds();
		ModParticleTypes.registerParticleTypes();

		ModDimensions.register();
		net.jukoz.me.world.biomes.surface.MapBiomeData.loadBiomes();MEBiomeKeys.registerModBiomes();
		EnchantmentsME.register();
        net.jukoz.me.event.ModEvents.register();
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
	// ResourceLocation helpers introduced by the 1.21 rewrite. Ported files call
	// MiddleEarth.of(...) in place of new ResourceLocation(MOD_ID, ...) everywhere,
	// so the helpers live here rather than being inlined at ~400 call sites.
	// ------------------------------------------------------------------

	public static ResourceLocation fetchId(String stringId) {
		return IdentifierUtil.getIdentifierFromString(stringId);
	}

	public static ResourceLocation ofPrefix(ResourceLocation base, ResourceLocation prefixId) {
		if (base == null) return null;
		return base.withPrefixedPath(String.format("%s/", prefixId.getPath()));
	}

	public static ResourceLocation of(String path) {
		return IdentifierUtil.build(MOD_ID, path);
	}

	public static ResourceLocation of(String... names) {
		return IdentifierUtil.buildAggregate(MOD_ID, names);
	}

	public static ResourceLocation of(char splitter, String... names) {
		return IdentifierUtil.build(MOD_ID, createAggregate(splitter, names));
	}

	public static ResourceLocation ofPath(String... names) {
		return IdentifierUtil.build(MOD_ID, createAggregate('/', names));
	}

	public static ResourceLocation ofVanillaPath(String... names) {
		return IdentifierUtil.ofVanilla(createAggregate('/', names));
	}

	public static ResourceLocation append(ResourceLocation base, String suffix) {
		return new ResourceLocation(base.toString() + suffix);
	}

	public static String createAggregate(char splitter, String... names) {
		return IdentifierUtil.createAggregateValue(splitter, names);
	}

	public static boolean compareId(ResourceLocation id1, ResourceLocation id2) {
		return id1.compareTo(id2) == 0;
	}
}

