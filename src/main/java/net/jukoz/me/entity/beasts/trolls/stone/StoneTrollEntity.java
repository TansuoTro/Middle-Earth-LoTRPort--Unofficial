package net.jukoz.me.entity.beasts.trolls.stone;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;

public class StoneTrollEntity extends TrollEntity {
    public static final EntityDataAccessor<Integer> PETRIFYING = SynchedEntityData.registerData(StoneTrollEntity.class, EntityDataSerializers.INTEGER);
    public static final List<ResourceKey<Biome>> darkBiomes = List.of(
            MEBiomeKeys.MORDOR,
            MEBiomeKeys.MORDOR_MOUNTAINS,
            MEBiomeKeys.MORDOR_WASTES
    );
    private final int PETRIFYING_DURATION = 600;

    public StoneTrollEntity(EntityType<? extends AbstractChestedHorse> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new RestrictSunGoal(this));
        this.goalSelector.add(1, new FleeSunGoal(this, 1.2d));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(PETRIFYING, PETRIFYING_DURATION);
    }

    @Override
    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        float h = this.isSitting() ? -0.75f : 0;
        return (double)this.getHeight() - 1.0d + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f) + h;
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        int i = this.getPassengers().indexOf(passenger);
        if (i < 0) {
            return;
        }

        float f = -0.5f; // Z-Offset

        Vec3 vec3d = new Vec3(0.0, 0.0, f).rotateY(-this.bodyYaw * ((float)Math.PI / 180));
        positionUpdater.accept(passenger, this.getX() + vec3d.x, this.getY() + this.getMountedHeightOffset(), this.getZ() + vec3d.z);
    }

    public void setPetrifying(int petrifying) {
        this.entityData.set(PETRIFYING, petrifying);
    }
    public int getPetrifying() {
        return this.entityData.get(PETRIFYING);
    }
    public boolean isPetrified() {
        return this.entityData.get(PETRIFYING) == -1;
    }

    @Override
    public boolean canThrow() {
        return !this.isPetrified() && !this.isSitting();
    }

    @Override
    public boolean canCharge() {
        return !this.isPetrified() && !this.isSitting();
    }

    @Override
    public Item getBondingItem() {
        if(!this.isPetrified()) {
            return super.getBondingItem();
        }
        return null;
    }

    @Override
    protected boolean isAffectedByDaylight() {
        if (this.level().isDay() && !this.level().isClientSide()) {
            float f = this.getLightLevelDependentMagicValue();
            BlockPos blockPos = BlockPos.containing(this.getX(), this.getEyeY(), this.getZ());
            return f > 0.5f && this.level().canSeeSky(blockPos);
        }
        return false;
    }

    @Override
    public void tickMovement() {
        if (this.isAlive() && !this.level().isClientSide() && this.getPetrifying() != -1 && this.level().getBiome(this.getBlockPos()).getKey().isPresent()) {
            ResourceKey<Biome> biomeKey = this.level().getBiome(this.getBlockPos()).getKey().get();

            boolean inDaylight = this.isAffectedByDaylight() && this.getEquippedStack(EquipmentSlot.CHEST).isEmpty() && !darkBiomes.contains(biomeKey);

            if (inDaylight) {
                this.setPetrifying(this.getPetrifying() - 1);
                if(this.getPetrifying() <= 0) {
                    this.turnToStone();
                }
                if(this.age % 10 == 0) {
                    this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0f, 1.0f);
                }
            }
            else {
                this.setPetrifying(PETRIFYING_DURATION);
            }
        }
        if(getPetrifying() != -1 && getPetrifying() < PETRIFYING_DURATION && this.level().isClientSide() && this.age % 3 == 0) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + ((random.nextFloat() * 2f) - 1f), this.getY() + 1d + random.nextFloat(), this.getZ() + ((random.nextFloat() * 2f) - 1f), random.nextFloat() / 8.0f, 0.2f, random.nextFloat() / 8.0f);
        }

        super.tickMovement();
    }

    public void turnToStone() {
        this.setNoAi(true);
        this.convertTo(ModEntities.PETRIFIED_TROLL, true);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Petrifying", this.getPetrifying());
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        this.entityData.set(PETRIFYING, nbt.getInt("Petrifying"));
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if(!this.isPetrified()) {
            super.onDamaged(damageSource);
        }
    }
}
