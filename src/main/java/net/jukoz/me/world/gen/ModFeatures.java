package net.jukoz.me.world.gen;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.features.boulder.BigBoulderFeature;
import net.jukoz.me.world.features.boulder.BigBoulderFeatureConfig;
import net.jukoz.me.world.features.chain.ChainFeature;
import net.jukoz.me.world.features.chain.ChainFeatureConfig;
import net.jukoz.me.world.features.columns.*;
import net.jukoz.me.world.features.deltas.ModDeltaFeatures;
import net.jukoz.me.world.features.growth.MultifaceStateFeature;
import net.jukoz.me.world.features.growth.MultifaceStateFeatureConfig;
import net.jukoz.me.world.features.ores.ModOreFeature;
import net.jukoz.me.world.features.ores.ModOreFeatureConfig;
import net.jukoz.me.world.features.ores.SurfaceOreFeature;
import net.jukoz.me.world.features.pillar.PillarFeature;
import net.jukoz.me.world.features.pillar.PillarFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.gen.feature.*;
import net.jukoz.me.world.features.platedfood.PlatedFoodFeature;
import net.jukoz.me.world.features.platedfood.PlatedFoodFeatureConfig;

public class ModFeatures {
    public static Feature<DeltaFeatureConfig> DELTA_FEATURE = register("delta_feature", new ModDeltaFeatures(DeltaFeatureConfig.CODEC));

    public static Feature<OreFeatureConfig> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreFeatureConfig.CODEC));
    public static Feature<ClusterFeatureConfig> CLUSTER = register("cluster", new ClusterFeature(ClusterFeatureConfig.CODEC));
    public static Feature<SmallPointedStoneFeatureConfig> SMALL_POINTED_STONE = register("small_pointed_stone", new SmallPointedStoneFeature(SmallPointedStoneFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<CaveColumnFeatureConfig> CAVE_COLUMN = register("cave_columns", new CaveColumnFeature(CaveColumnFeatureConfig.CODEC));
    public static Feature<MultifaceStateFeatureConfig> MULTIFACE_PERSISTENT = register("multiface_persistent", new MultifaceStateFeature(MultifaceStateFeatureConfig.CODEC));
    public static Feature<ColumnsFeatureConfig> COLUMNS = register("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
    public static Feature<BigBoulderFeatureConfig> BIG_BOULDER = register("big_boulder", new BigBoulderFeature(BigBoulderFeatureConfig.CODEC));
    public static Feature<ModOreFeatureConfig> ORE = register("ore", new ModOreFeature(ModOreFeatureConfig.CODEC));
    public static Feature<ChainFeatureConfig> CHAIN = register("chain", new ChainFeature(ChainFeatureConfig.CODEC));
    public static Feature<PlatedFoodFeatureConfig> PLATED_FOOD = register("plated_food", new PlatedFoodFeature(PlatedFoodFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> MIRKWOOD_VINE = register("mirkwood_vine", new MirkwoodVinesFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> WILLOW_VINE = register("willow_vine", new WillowVinesFeature(DefaultFeatureConfig.CODEC));

    public static void init() {
        LoggerUtil.getInstance().logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name), feature);
    }
}
