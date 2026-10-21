package net.jukoz.me.client.model.equipment.head.helmets;
import net.jukoz.me.compat.client.render.MEBipedEntityModel;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class HelmetAddonModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public HelmetAddonModel(ModelPart root) {
        super(root);
    }
}