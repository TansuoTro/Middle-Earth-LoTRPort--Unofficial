package net.jukoz.me.entity;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.jukoz.me.block.special.bellows.BellowsBlockEntityRenderer;
import net.jukoz.me.block.special.coffers.*;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.jukoz.me.block.special.skull.OldSkullBlockEntityRenderer;
import net.jukoz.me.entity.barrel.BarrelEntityModel;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollModel;
import net.jukoz.me.entity.npcs.renderer.NpcEntityModel;
import net.jukoz.me.entity.npcs.renderer.features.ear.EarModel;
import net.jukoz.me.entity.npcs.renderer.features.feet.FeetModel;
import net.jukoz.me.entity.npcs.renderer.features.hair.HairModel;
import net.jukoz.me.entity.npcs.renderer.features.nose.NoseModel;
import net.jukoz.me.entity.spider.EnwebbedModel;
import net.jukoz.me.entity.spider.larva.ShelobiteLarvaModel;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.jukoz.me.entity.spider.spawn.SpawnOfShelobModel;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class EntityModelsME {
    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        builder.put(EntityModelLayersME.NPC, NpcEntityModel.getTexturedModelData(Dilation.NONE));
        builder.put(EntityModelLayersME.NPC_ENTITY_HAIR, HairModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_EAR, EarModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_NOSE, NoseModel.getTexturedModelData());
        builder.put(EntityModelLayersME.NPC_ENTITY_FEET, FeetModel.getTexturedModelData());

        builder.put(EntityModelLayersME.SNOW_TROLL, SnowTrollModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SHELOBITE_LARVA, ShelobiteLarvaModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SHELOBITE_SCUTTLER, ShelobiteScuttlerModel.getTexturedModelData());
        builder.put(EntityModelLayersME.SPAWN_OF_SHELOB, SpawnOfShelobModel.getTexturedModelData());

        builder.put(EntityModelLayersME.ENWEBBED, EnwebbedModel.getTexturedModelData());

        builder.put(EntityModelLayersME.REINFORCED_CHEST, ReinforcedChestEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.BELLOWS, BellowsBlockEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.LARCH_COFFER, LarchCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.PINE_COFFER, PineCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.SPRUCE_COFFER, SpruceCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.FIR_COFFER, FirCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.BEECH_COFFER, BeechCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.CHESTNUT_COFFER, ChestnutCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.OAK_COFFER, OakCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.WILLOW_COFFER, WillowCofferEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.OLD_SKULL, OldSkullBlockEntityRenderer.getTexturedModelData());
        builder.put(EntityModelLayersME.REINFORCED_BARREL, BarrelEntityModel.getTexturedModelData());
        builder.put(EntityModelLayersME.REINFORCED_BARREL_WATER_MASK, BarrelEntityModel.getBaseTexturedModelData());

        ImmutableMap<EntityModelLayer, TexturedModelData> immutableMap = builder.build();
        return immutableMap;
    }
}
