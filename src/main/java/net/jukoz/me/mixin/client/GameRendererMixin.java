package net.jukoz.me.mixin.client;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "updateTargetedEntity", at = @At("HEAD"), cancellable = true)
    private void me$attributeReach(float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity camera = client.getCameraEntity();
        if (!(camera instanceof Player player) || client.world == null || client.interactionManager == null) return;
        double blockReach = EntityAttributesME.blockReach(player);
        double entityReach = EntityAttributesME.entityReach(player);
        var eye = camera.getCameraPosVec(tickDelta);
        var direction = camera.getRotationVec(tickDelta);
        client.crosshairTarget = camera.raycast(blockReach, tickDelta, false);
        client.targetedEntity = null;
        // Long weapons stop at walls even when the wall is outside block-use reach.
        var obstruction = camera.raycast(entityReach, tickDelta, false);
        double limit = Math.min(entityReach * entityReach, eye.squaredDistanceTo(obstruction.getPos()));
        var hit = ProjectileUtil.raycast(camera, eye, eye.add(direction.multiply(entityReach)),
                camera.getBoundingBox().stretch(direction.multiply(entityReach)).expand(1.0),
                target -> !target.isSpectator() && target.canHit(), limit);
        if (hit != null && eye.squaredDistanceTo(hit.getPos()) <= limit) {
            client.crosshairTarget = hit;
            if (hit.getEntity() instanceof LivingEntity || hit.getEntity() instanceof ItemFrameEntity) client.targetedEntity = hit.getEntity();
        }
        ci.cancel();
    }
}
