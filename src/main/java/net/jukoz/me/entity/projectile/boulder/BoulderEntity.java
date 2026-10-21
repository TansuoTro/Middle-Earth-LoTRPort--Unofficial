package net.jukoz.me.entity.projectile.boulder;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class BoulderEntity extends ThrowableProjectile {

    public BoulderEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
    }

    public BoulderEntity(EntityType<? extends ThrowableProjectile> type, LivingEntity owner, Level world) {
        super(type, owner, world);
    }
    public BoulderEntity(Level world, double x, double y, double z) {
        super(ModEntities.BOULDER, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if(this.getOwner() != null)  {
            Entity entity2 = this.getOwner();
            if (entity2 instanceof LivingEntity && entity2 != null) {
                entity.damage(this.damageSources().mobProjectile(this, (LivingEntity) entity2), 10.0f);
                this.applyDamageEffects((LivingEntity)entity2, entity);
            }
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.level().isClientSide) {
            this.level().createExplosion((Entity)this, this.getX(), this.getY(), this.getZ(), 0.5f, false, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }
}
