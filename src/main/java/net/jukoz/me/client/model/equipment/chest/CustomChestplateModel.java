package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;

public class CustomChestplateModel<T extends LivingEntity> extends HumanoidModel<T> {

    public  ResourceLocation CHESTPLATE_ADDON_TEXTURE;

    public CustomChestplateModel(ModelPart root) {
        super(root);
    }
}
