package net.jukoz.me.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

public abstract class AbstractProjectileEntity extends ThrowableItemProjectile {
    private float damage;

    public AbstractProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public AbstractProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, double d, double e, double f, Level world, ItemStack stack) {
        super(entityType, d, e, f, world);
    }

    public AbstractProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity livingEntity, Level world, ItemStack stack) {
        super(entityType, livingEntity, world);
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(entity.getWorld() instanceof ServerLevel serverWorld) {
            entity.damage(this.getDamageSources().thrown(this, this.getOwner()), this.damage);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient) {
            getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}
