package net.jukoz.me.entity.projectile.pebble;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.projectile.AbstractProjectleEntity;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

public class PebbleEntity extends AbstractProjectleEntity {
    private Item item = ModResourceItems.PEBBLE;
    private float damage;

    public PebbleEntity(EntityType<? extends PebbleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public PebbleEntity(Level world, LivingEntity owner, float dmg) {
        super(ModEntities.PEBBLE, owner, world);
        this.damage = dmg;
    }

    public PebbleEntity(Level world, double x, double y, double z) {
        super(ModEntities.PEBBLE, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return item;
    }
}
