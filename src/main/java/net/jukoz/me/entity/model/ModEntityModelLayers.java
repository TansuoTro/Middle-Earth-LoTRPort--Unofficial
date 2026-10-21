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
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.EntityModelLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(value= EnvType.CLIENT)
public final class ModEntityModelLayers {
    private static final String MAIN = "main";
    public static final EntityModelLayer DWARF = ModEntityModelLayers.registerEntityModelLayer("dwarf", DwarfModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer HUMAN = ModEntityModelLayers.registerEntityModelLayer("human", HumanModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer ELF = ModEntityModelLayers.registerEntityModelLayer("elf", ElfModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer HOBBIT = ModEntityModelLayers.registerEntityModelLayer("hobbit", HobbitModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer NAZGUL = ModEntityModelLayers.registerEntityModelLayer("nazgul", NazgulModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer ORC = ModEntityModelLayers.registerEntityModelLayer("orc", OrcModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer URUK = ModEntityModelLayers.registerEntityModelLayer("uruk", UrukModel.getTexturedModelData(CubeDeformation.NONE));
    public static final EntityModelLayer CAVE_TROLL = ModEntityModelLayers.registerEntityModelLayer("cave_troll", CaveTrollModel.getTexturedModelData());
    public static final EntityModelLayer SNOW_TROLL = ModEntityModelLayers.registerEntityModelLayer("snow_troll", SnowTrollModel.getTexturedModelData());
    public static final EntityModelLayer STONE_TROLL = ModEntityModelLayers.registerEntityModelLayer("stone_troll", StoneTrollModel.getTexturedModelData());
    public static final EntityModelLayer PETRIFIED_TROLL = ModEntityModelLayers.registerEntityModelLayer("petrified_troll", PetrifiedTrollModel.getTexturedModelData());
    public static final EntityModelLayer BARROW_WIGHT = ModEntityModelLayers.registerEntityModelLayer("barrow_wight", BarrowWightModel.getTexturedModelData());
    public static final EntityModelLayer SPIDER = ModEntityModelLayers.registerEntityModelLayer("spider", MirkwoodSpiderModel.getTexturedModelData());
    public static final EntityModelLayer BALROG = ModEntityModelLayers.registerEntityModelLayer("balrog", BalrogModel.getTexturedModelData());


    // Animals
    public static final EntityModelLayer CRAB = ModEntityModelLayers.registerEntityModelLayer("crab", CrabModel.getTexturedModelData());
    public static final EntityModelLayer GOOSE = ModEntityModelLayers.registerEntityModelLayer("goose", GooseModel.getTexturedModelData());
    public static final EntityModelLayer DUCK = ModEntityModelLayers.registerEntityModelLayer("duck", DuckModel.getTexturedModelData());
    public static final EntityModelLayer SWAN = ModEntityModelLayers.registerEntityModelLayer("swan", SwanModel.getTexturedModelData());
    public static final EntityModelLayer PHEASANT = ModEntityModelLayers.registerEntityModelLayer("pheasant", PheasantModel.getTexturedModelData());
    public static final EntityModelLayer SNAIL = ModEntityModelLayers.registerEntityModelLayer("snail", SnailModel.getTexturedModelData());
    public static final EntityModelLayer DEER = ModEntityModelLayers.registerEntityModelLayer("deer", DeerModel.getTexturedModelData());


    /**
     * The modelData is used to know the UV map to use for the 3D model
     * **/
    /** Forces this class's static initialiser to run (see MiddleEarthClient#onInitializeClient). */
    public static void init() {
    }

    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        // Port shim: EntityModelLayersME declares the same layer ids for a second, newer renderer
        // family (render-state based). Sharing an id means whichever class initialises first wins
        // the TexturedModelData, and the other family's model constructor then explodes with
        // "Can't find part <x>". Keep this family on its own id namespace.
        EntityModelLayer entityModelLayer = new EntityModelLayer(new ResourceLocation(MiddleEarth.MOD_ID, "legacy_" + registryName), MAIN);
        return net.jukoz.me.compat.client.MEModelLayers.register(entityModelLayer, () -> modelData);
    }
}
