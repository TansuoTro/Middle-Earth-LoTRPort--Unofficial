package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.entity.spider.Pouncer;

import java.util.EnumSet;

public class SpiderPonceAtTargetGoal extends Goal {
    private final HostileEntity spider;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;
    private boolean startPrePounce;
    private int preparationPounceTimer;
    private Path path;
    private Pouncer pouncer;
    private final int minDistance;
    private final int maxDistance;
    private final int moduloTicks;

    public SpiderPonceAtTargetGoal(HostileEntity mob, Pouncer pouncer, float verticalVelocity, float horizontalVelocity) {
        this(mob, pouncer, verticalVelocity, horizontalVelocity, 3, 12, 5);
    }

    public SpiderPonceAtTargetGoal(HostileEntity mob, Pouncer pouncer, float verticalVelocity, float horizontalVelocity,
                                   int minDistance, int maxDistance, int moduloTicks) {
        this.spider = mob;
        this.pouncer = pouncer;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.moduloTicks = moduloTicks;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        startPrePounce = false;
        preparationPounceTimer = 18;
    }

    @Override
    public boolean canStart() {
        if (this.spider.hasPassengers()) {
            return false;
        }
        this.target = this.spider.getTarget();
        if (this.target == null) {
            return false;
        }
        double d = this.spider.distanceTo(this.target);
        if (d < this.minDistance || d > this.maxDistance) {
            return false;
        }
        if (!this.spider.isOnGround()) {
            return false;
        }
        this.path = this.spider.getNavigation().findPathTo(target, 0);
        if(path == null) return false;

        int randomInt = this.spider.getRandom().nextInt(PounceAtTargetGoal.toGoalTicks(this.moduloTicks));
        return  this.spider.getRandom().nextInt(2) == 0 && randomInt == 0;
    }

    @Override
    public void tick() {
        super.tick();
        this.path = this.spider.getNavigation().findPathTo(target, 0);
        if(this.path != null) {
            this.spider.getNavigation().startMovingTo(target, 0.8f);
        }
        this.spider.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());

        if(startPrePounce) {
            preparationPounceTimer = Math.max(preparationPounceTimer - 1, 0);
            if(preparationPounceTimer == 0) {
                this.spider.getNavigation().stop();
                Vec3 vec3d = this.spider.getVelocity();
                Vec3 vec3d2 = new Vec3(this.target.getX() - this.spider.getX(), 0.0, this.target.getZ() - this.spider.getZ());
                if (vec3d2.lengthSquared() > 1.0E-7) {
                    vec3d2 = vec3d2.multiply(horizontalVelocity).add(vec3d.multiply(0.2));;
                }
                float verticalVel = this.verticalVelocity;
                if(target.getY() > this.spider.getY()) {
                    verticalVel += 0.1f;
                }
                this.spider.setVelocity(vec3d2.x, verticalVel, vec3d2.z);
                startPrePounce = false;
                stop();
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return startPrePounce;
    }

    @Override
    public void start() {
        preparationPounceTimer = 18;
        startPrePounce = true;
        this.spider.getNavigation().startMovingAlong(this.path, 0.8f);
        this.pouncer.startPounceAnimation();
    }

    @Override
    public void stop() {
        super.stop();
        this.pouncer.stopPounceAnimation();
    }
}
