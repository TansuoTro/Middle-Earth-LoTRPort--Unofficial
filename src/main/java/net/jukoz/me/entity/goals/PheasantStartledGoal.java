package net.jukoz.me.entity.goals;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.pheasant.PheasantEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PheasantStartledGoal extends Goal {
    PheasantEntity pheasant;
    Player player;
    Vec3 vec3d = Vec3.ZERO;
    Vec3 fleeDir;
    @Nullable
    Path fleePath;
    final EntityNavigation fleeingEntityNavigation;
    boolean flying;

    public PheasantStartledGoal(PheasantEntity pheasant) {
        this.setControls(EnumSet.of(Control.MOVE));
        this.pheasant = pheasant;
        this.fleeingEntityNavigation = pheasant.getNavigation();
    }

    @Override
    public boolean canStart() {
        player = pheasant.getWorld().getClosestPlayer(pheasant, 7.0d);
        if(player == null) {
            return false;
        }

        vec3d = NoPenaltyTargeting.findFrom(this.pheasant, 4, 3, this.player.getPos());
        if (vec3d == null) {
            return false;
        }
        if (this.player.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.player.squaredDistanceTo(this.pheasant)) {
            return false;
        }

        this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
        if(this.fleePath == null) {
            return false;
        }

        boolean wearingCloak =  player.getEquippedStack(EquipmentSlot.CHEST).isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "cloaks")))
                && player.getEquippedStack(EquipmentSlot.HEAD).isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "cloaks")));

        return !(player == null || wearingCloak || player.isSneaking() || !pheasant.isOnGround());
    }

    @Override
    public void start() {
        fleeDir = vec3d.subtract(pheasant.getPos()).multiply(1,0,1).normalize();
        this.flying = false;
        this.fleeingEntityNavigation.startMovingAlong(this.fleePath, 1.6d);
    }

    @Override
    public void tick() {
        if(this.fleeingEntityNavigation.isIdle() && !this.flying) {
            this.pheasant.setVelocity(fleeDir.multiply(1.7, 0, 1.7).add(0, 0.5, 0));
            this.pheasant.setYaw((float) Math.toDegrees(Math.atan2(-fleeDir.x, fleeDir.z)));
            this.flying = true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.flying;
    }

    @Override
    public void stop() {
        this.player = null;
        this.flying = false;
    }
}