package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WizardHatModel extends HelmetAddonModel {

    public WizardHatModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition wizard = head.addChild("wizard", CubeListBuilder.create()
                .uv(0, 47).cuboid(-8.0F, -5.15F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.9F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition bone = wizard.addChild("bone", CubeListBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -9.25F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.7F))
                .uv(32, 0).cuboid(-4.0F, -9.25F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(1.0F)),
                PartPose.of(0.0F, -0.325F, 0.2F, 0.0436F, 0.0F, 0.0F));

        PartDefinition hat = bone.addChild("hat", CubeListBuilder.create(),
                PartPose.of(0.0F, 1.6F, -0.4F, -0.0175F, 0.0F, 0.0F));

        hat.addChild("wizard_0", CubeListBuilder.create()
                .uv(24, 1).mirrored().cuboid(2.0F, -9.7386F, -2.2615F, 0.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(46, 12).cuboid(0.0F, -4.7386F, -2.2615F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.of(-2.0F, -13.0143F, 1.5539F, -0.5672F, 0.0F, 0.0F));
        hat.addChild("wizard_1", CubeListBuilder.create()
                .uv(0, 12).cuboid(-3.0F, -4.75F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.5F)),
                PartPose.of(0.0F, -9.5F, 0.0F, -0.0873F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }
}
