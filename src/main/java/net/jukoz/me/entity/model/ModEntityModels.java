package net.jukoz.me.entity.model;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.entity.crab.CrabModel;
import net.jukoz.me.entity.nazguls.NazgulModel;
import net.jukoz.me.entity.snail.SnailModel;
import net.jukoz.me.entity.spider.MirkwoodSpiderModel;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollModel;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.EntityModelLayer;
import java.util.Map;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class ModEntityModels {
    public static Map<ModelLayerLocation, LayerDefinition> getModels() {
        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();

        builder.put(ModEntityModelLayers.HUMAN, HumanModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.DWARF, DwarfModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.ELF, ElfModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.HOBBIT, HobbitModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.NAZGUL, NazgulModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.ORC, OrcModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.URUK, UrukModel.getTexturedModelData(CubeDeformation.NONE));
        builder.put(ModEntityModelLayers.CAVE_TROLL, CaveTrollModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SNOW_TROLL, SnowTrollModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SPIDER, MirkwoodSpiderModel.getTexturedModelData());

        // Animals
        builder.put(ModEntityModelLayers.CRAB, CrabModel.getTexturedModelData());
        builder.put(ModEntityModelLayers.SNAIL, SnailModel.getTexturedModelData());

        ImmutableMap<ModelLayerLocation, LayerDefinition> immutableMap = builder.build();
        return immutableMap;
    }
}
