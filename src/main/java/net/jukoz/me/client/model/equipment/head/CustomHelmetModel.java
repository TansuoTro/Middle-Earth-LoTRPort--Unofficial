package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class CustomHelmetModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public  ResourceLocation HELMET_ADDON_TEXTURE;

    public CustomHelmetModel(ModelPart root) {
        super(root);
    }
}