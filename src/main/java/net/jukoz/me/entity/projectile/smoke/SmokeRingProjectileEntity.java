package net.jukoz.me.entity.projectile.smoke;

import net.minecraft.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.utils.ModCollisionUtils;

public class SmokeRingProjectileEntity extends ProjectileEntity {
    public static final int MAX_LIFESPAN_TICKS = 40;
    public static final int FAILED_MAX_LIFESPAN_TICKS = 14;
    private static final TrackedData<Boolean> FAILED = DataTracker.registerData(SmokeRingProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final double ENTITY_BOX_EXPANSION = 1.0;
    private static final float ENTITY_COLLISION_MARGIN = 0.3F;

    private transient boolean isFadingOut = false;

    public SmokeRingProjectileEntity(
            EntityType<? extends SmokeRingProjectileEntity> type,
            Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        this.checkCollision();
        this.checkLifespan();
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.triggerFadeOut();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FAILED, false);
    }

    @Override
    protected void readCustomDataFromNbt(CompoundTag nbt) {
        ReadView view = new ReadView(nbt);
        this.setFailed(view.getBoolean("Failed", false));
    }

    @Override
    protected void writeCustomDataToNbt(CompoundTag nbt) {
        WriteView view = new WriteView(nbt);
        view.putBoolean("Failed", this.isFailed());
    }

    private void checkLifespan() {
        if (this.age >= this.getMaxLifespanTicks()) {
            this.discard();
        }
    }

    private void triggerFadeOut() {
        if (isFadingOut) return;

        isFadingOut = true;
        this.age = this.getMaxLifespanTicks() - 3;

        this.setVelocity(Vec3.ZERO);
        this.setPosition(this.getX(), this.getY(), this.getZ());
    }

    private void checkCollision() {
        if (isFadingOut) return;

        if (this.isFailed()) {
            this.move(MovementType.SELF, this.getVelocity());
            return;
        }

        if (checkEntityCollision() || checkFanBlockCollision()) {
            return;
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    private boolean checkEntityCollision() {
        Vec3 start = this.getPos();
        Vec3 end = this.getPos().add(this.getVelocity());

        EntityHitResult hit = ProjectileUtil.getEntityCollision(this.getWorld(),
                this,
                start,
                end,
                this.getBoundingBox().stretch(this.getVelocity()).expand(ENTITY_BOX_EXPANSION),
                this::canHit,
                ENTITY_COLLISION_MARGIN);

        if (hit != null) {
            this.setPosition(hit.getPos());
            this.onCollision(hit);
            return true;
        }

        return false;
    }

    private boolean checkFanBlockCollision() {
        return ModCollisionUtils.checkBlockFanCollision(this.getWorld(),
                this.getBoundingBox(),
                this.getVelocity(),
                this,
                this::onCollision);
    }

    public void setFailed(boolean failed) {
        this.dataTracker.set(FAILED, failed);
    }

    public boolean isFailed() {
        return this.dataTracker.get(FAILED);
    }

    public int getMaxLifespanTicks() {
        return this.isFailed() ? FAILED_MAX_LIFESPAN_TICKS : MAX_LIFESPAN_TICKS;
    }
}
