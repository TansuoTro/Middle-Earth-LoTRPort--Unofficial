package net.jukoz.me.entity.model;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.balrog.BalrogModel;
import net.jukoz.me.entity.barrow_wights.BarrowWightModel;
import net.jukoz.me.entity.beasts.trolls.petrified.PetrifiedTrollModel;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollModel;
import net.jukoz.me.entity.crab.CrabModel;
import net.jukoz.me.entity.deer.DeerModel;
import net.jukoz.me.entity.duck.DuckModel;
import net.jukoz.me.entity.goose.GooseModel;
import net.jukoz.me.entity.humans.gondor.GondorHumanModel;
import net.jukoz.me.entity.nazguls.NazgulModel;
import net.jukoz.me.entity.pheasant.PheasantModel;
import net.jukoz.me.entity.snail.SnailModel;
import net.jukoz.me.entity.spider.MirkwoodSpiderModel;
import net.jukoz.me.entity.swan.SwanModel;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollModel;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    public static final ModelLayerLocation DWARF = ModEntityModelLayers.registerEntityModelLayer("dwarf", DwarfModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation HUMAN = ModEntityModelLayers.registerEntityModelLayer("human", HumanModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation ELF = ModEntityModelLayers.registerEntityModelLayer("elf", ElfModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation HOBBIT = ModEntityModelLayers.registerEntityModelLayer("hobbit", HobbitModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation NAZGUL = ModEntityModelLayers.registerEntityModelLayer("nazgul", NazgulModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation ORC = ModEntityModelLayers.registerEntityModelLayer("orc", OrcModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation URUK = ModEntityModelLayers.registerEntityModelLayer("uruk", UrukModel.getTexturedModelData(CubeDeformation.NONE));
    public static final ModelLayerLocation CAVE_TROLL = ModEntityModelLayers.registerEntityModelLayer("cave_troll", CaveTrollModel.getTexturedModelData());
    public static final ModelLayerLocation SNOW_TROLL = ModEntityModelLayers.registerEntityModelLayer("snow_troll", SnowTrollModel.getTexturedModelData());
    public static final ModelLayerLocation STONE_TROLL = ModEntityModelLayers.registerEntityModelLayer("stone_troll", StoneTrollModel.getTexturedModelData());
    public static final ModelLayerLocation PETRIFIED_TROLL = ModEntityModelLayers.registerEntityModelLayer("petrified_troll", PetrifiedTrollModel.getTexturedModelData());
    public static final ModelLayerLocation BARROW_WIGHT = ModEntityModelLayers.registerEntityModelLayer("barrow_wight", BarrowWightModel.getTexturedModelData());
    public static final ModelLayerLocation SPIDER = ModEntityModelLayers.registerEntityModelLayer("spider", MirkwoodSpiderModel.getTexturedModelData());
    public static final ModelLayerLocation BALROG = ModEntityModelLayers.registerEntityModelLayer("balrog", BalrogModel.getTexturedModelData());

    // Animals
    public static final ModelLayerLocation CRAB = ModEntityModelLayers.registerEntityModelLayer("crab", CrabModel.getTexturedModelData());
    public static final ModelLayerLocation GOOSE = ModEntityModelLayers.registerEntityModelLayer("goose", GooseModel.getTexturedModelData());
    public static final ModelLayerLocation DUCK = ModEntityModelLayers.registerEntityModelLayer("duck", DuckModel.getTexturedModelData());
    public static final ModelLayerLocation SWAN = ModEntityModelLayers.registerEntityModelLayer("swan", SwanModel.getTexturedModelData());
    public static final ModelLayerLocation PHEASANT = ModEntityModelLayers.registerEntityModelLayer("pheasant", PheasantModel.getTexturedModelData());
    public static final ModelLayerLocation SNAIL = ModEntityModelLayers.registerEntityModelLayer("snail", SnailModel.getTexturedModelData());
    public static final ModelLayerLocation DEER = ModEntityModelLayers.registerEntityModelLayer("deer", DeerModel.getTexturedModelData());

    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    /** Forces this class's static initialiser to run (see MiddleEarthClient#onInitializeClient). */
    public static void init() {
    }

    private static ModelLayerLocation registerEntityModelLayer(String registryName, LayerDefinition modelData) {
        // Port shim: EntityModelLayersME declares the same layer ids for a second, newer renderer
        // family (render-state based). Sharing an id means whichever class initialises first wins
        // the LayerDefinition, and the other family's model constructor then explodes with
        // "Can't find part <x>". Keep this family on its own id namespace.
        ModelLayerLocation entityModelLayer = new ModelLayerLocation(new ResourceLocation(MiddleEarth.MOD_ID, "legacy_" + registryName), MAIN);
        return net.jukoz.me.compat.client.MEModelLayers.register(entityModelLayer, () -> modelData);
    }
}
