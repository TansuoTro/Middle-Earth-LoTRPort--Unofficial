package net.jukoz.me.client.model.equipment.chest.backAttachments;

import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.jukoz.me.client.renderer.ArmedEntityRenderStateAccess;
import net.jukoz.me.client.renderer.BipedEntityRenderStateAccess;
import net.jukoz.me.utils.ToRad;

public class CloakCapeModel extends ChestplateAddonModel {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
    }

    @Override
    public void setAngles(BipedEntityRenderState bipedEntityRenderState) {
        BipedEntityRenderStateAccess renderStateAccess = ((BipedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess != null) {
            this.cape.traverse().forEach(ModelPart::resetTransform);
            Vec3 currentVelocity = renderStateAccess.getVelocity();
            if(currentVelocity == null) currentVelocity = new Vec3(0, 0, 0);

            double sqrVel = currentVelocity.length();
            double speed = (sqrVel * 0.65f) + Math.sqrt(Math.abs(bipedEntityRenderState.limbSwingAmplitude)) * 0.35f;
            double degree;

            if (bipedEntityRenderState.isInSneakingPose) {
                degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
            } else {
                degree = (MAX_ANGLE_CLOAK * speed);
            }
            degree = Math.max(0.0F, degree);
            degree = Math.min(MAX_ANGLE_CLOAK, degree);

            double result = renderStateAccess.getVelocity().dotProduct(currentVelocity);

            if(result > 0) {
                this.cape.pitch = ToRad.ex(degree);
            }
        }
    }
}
