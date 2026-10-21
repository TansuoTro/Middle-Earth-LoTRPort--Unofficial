package net.jukoz.me.entity.projectile.smoke;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.projectile.Projectile;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;

public class SmokeRingProjectileEntity extends Projectile {
    public static final int MAX_LIFESPAN_TICKS = 40;
    public static final int FAILED_MAX_LIFESPAN_TICKS = 14;
    private static final EntityDataAccessor<Boolean> FAILED = SynchedEntityData.registerData(SmokeRingProjectileEntity.class, EntityDataSerializers.BOOLEAN);
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
        this.entityData.define(FAILED, false);
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
            this.move(MoverType.SELF, this.getVelocity());
            return;
        }

        if (checkEntityCollision() || checkFanBlockCollision()) {
            return;
        }

        this.move(MoverType.SELF, this.getVelocity());
    }

    private boolean checkEntityCollision() {
        Vec3 start = this.getPos();
        Vec3 end = this.getPos().add(this.getVelocity());

        EntityHitResult hit = ProjectileUtil.getEntityCollision(this.level(),
                this,
                start,
                end,
                this.getBoundingBox().getMovementArea(this.getVelocity()).expand(ENTITY_BOX_EXPANSION),
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
        return ModCollisionUtils.checkBlockFanCollision(this.level(),
                this.getBoundingBox(),
                this.getVelocity(),
                this,
                this::onCollision);
    }

    public void setFailed(boolean failed) {
        this.entityData.set(FAILED, failed);
    }

    public boolean isFailed() {
        return this.entityData.get(FAILED);
    }

    public int getMaxLifespanTicks() {
        return this.isFailed() ? FAILED_MAX_LIFESPAN_TICKS : MAX_LIFESPAN_TICKS;
    }
}
