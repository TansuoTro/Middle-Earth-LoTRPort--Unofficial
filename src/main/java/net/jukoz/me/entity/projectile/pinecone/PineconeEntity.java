package net.jukoz.me.entity.projectile.pinecone;

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

public class PineconeEntity extends AbstractProjectleEntity {
    private Item item = ModResourceItems.PINECONE;
    private float damage;

    public PineconeEntity(EntityType<? extends PineconeEntity> entityType, Level world) {
        super(entityType, world);
    }

    public PineconeEntity(Level world, LivingEntity owner, float dmg) {
        super(ModEntities.PINECONE, owner, world);
        this.damage = dmg;
    }

    public PineconeEntity(Level world, double x, double y, double z) {
        super(ModEntities.PINECONE, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return item;
    }
}
