package net.jukoz.me.entity.beasts.trolls;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.items.TrollArmorItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import java.util.List;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.Vec3;

public class TrollEntity extends BeastEntity {
    private int throwCooldown = 100;
    public final AnimationState throwingAnimationState = new AnimationState();

    private int throwingAnimationTimeout = 0;
    private int bondingTries = 0;
    private int bondingTimeout = 0;

    public static final EntityDataAccessor<Boolean> THROWING = SynchedEntityData.registerData(TrollEntity.class, EntityDataSerializers.BOOLEAN);

    // Temporary disabled until next update
    @Override
    public boolean hasArmorSlot() {
        return false;
    }

    public TrollEntity(EntityType<? extends AbstractChestedHorse> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35f)
                .add(Attributes.MAX_HEALTH, 120.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.GENERIC_ATTACK_SPEED, 0.9)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.HORSE_JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(6, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(9, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((BeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((BeastEntity)this));
        this.targetSelector.add(3, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.add(4, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new NearestAttackableTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(6, new NearestAttackableTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(7, new NearestAttackableTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(8, new NearestAttackableTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(9, new NearestAttackableTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(THROWING, false);
    }

    @Override
    public void onTrackedDataSet(EntityDataAccessor<?> data) {
        if(!this.firstUpdate && THROWING.equals(data)) {
            this.throwCooldown = this.throwCooldown == 0 ? 200 : this.throwCooldown;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected void setupAnimationStates() {
        super.setupAnimationStates();

        if(this.isThrowing() && this.throwingAnimationTimeout <= 0) {
            this.throwingAnimationTimeout = 100;
            this.throwingAnimationState.start(this.age);
        }else {
            --this.throwingAnimationTimeout;
        }
        if(isThrowing()) {
            this.setSpeed(0);
        }

        if(!this.isThrowing()) {
            this.throwingAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().lookAt(this.getTarget());
        }

        if(this.isCharging()) {
            chargeAttack();
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }
        }

        if(throwCooldown == 0 && this.getTarget() != null && !isCharging()) {
            if(this.distanceTo(this.getTarget()) >= 25 && !this.isVehicle() && canThrow()) {
                this.setThrowing(true);
                throwCooldown = 200;
            }
        }
        if(this.isThrowing() && canThrow()) {
            this.setVelocity(Vec3.ZERO);
            if(throwCooldown <= 180) {
                throwAttack();
            }
        }
        if(throwCooldown > 0) {
            --this.throwCooldown;
        }

        if (!this.level().isClientSide) {
            if(this.bondingTimeout > 0) {
                this.bondingTimeout--;
            }
        }

        if (this.level().isClientSide) {
            setupAnimationStates();
        }
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ChestedTroll", this.hasChest());
        if (this.hasChest()) {
            ListTag nbtList = new ListTag();
            for(int i = 2; i < this.items.size(); ++i) {
                ItemStack itemStack = this.items.getItem(i);
                if (!itemStack.isEmpty()) {
                    CompoundTag nbtCompound = new CompoundTag();
                    nbtCompound.putByte("Slot", (byte)i);
                    itemStack.writeNbt(nbtCompound);
                    nbtList.add(nbtCompound);
                }
            }
            nbt.put("Items", nbtList);
        }
        if (!this.items.getItem(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getItem(1).writeNbt(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasChest(nbt.getBoolean("ChestedTroll"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag nbtList = nbt.getList("Items", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                CompoundTag nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                if (j >= 2 && j < this.items.size()) {
                    this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
                }
            }
        }
        if (nbt.contains("ArmorItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"));
            if (!itemStack.isEmpty() && this.isHorseArmor(itemStack)) {
                this.items.setStack(1, itemStack);
            }
        }

        if (nbt.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("SaddleItem"));
            if (itemStack.is(Items.SADDLE)) {
                this.items.setStack(0, itemStack);
            }
        }

        this.updateSaddle();
    }

    public boolean canThrow() {
        return !this.isSitting();
    }

    public void setThrowing(boolean throwing) {
        this.entityData.set(THROWING, throwing);
    }

    public boolean isThrowing() {
        return this.entityData.get(THROWING);
    }

    @Override
    public int chargeDuration() {
        return 25;
    }

    @Override
    public Item getBondingItem() {
        return ModFoodItems.COOKED_HORSE;
    }

    public int getBondingTimeout() {
        return bondingTimeout;
    }

    @Override
    public void tryBonding(Player player) {
        if(this.bondingTimeout <= 0) {
            if(random.nextFloat() <= 0.4f) {
                this.bondingTries++;
                if(bondingTries == 3) {
                    if (player instanceof ServerPlayer) {
                        this.setOwnerUuid(player.getUuid());
                        this.setTame(true);
                        this.setTarget(null);
                        CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
                    }
                    this.level().sendEntityStatus(this, EntityEvent.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

                    this.chargeTimeout = 0;
                }
            }
            player.getItemInHand(player.getUsedItemHand()).decrement(1);
            this.bondingTimeout = 40;
        }

    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        return item.getItem() instanceof TrollArmorItem;
    }

    public void throwAttack() {
        Entity target = this.getTarget();
        if(target != null && !this.level().isClientSide) {
            this.setThrowing(false);

            Vec3 rotationVec = this.getRotationVec(1.0f);
            BoulderEntity boulder = new BoulderEntity(ModEntities.BOULDER, this, this.level());
            double x = target.getX() - this.getX();
            double y = target.getBodyY(0.3333333333333333) - boulder.getY();
            double z = target.getZ() - this.getZ();
            double c = Math.sqrt(x * x + z * z);

            boulder.setPosition(this.getX() + rotationVec.x * 2.0f, this.getBodyY(0.75f), boulder.getZ() + rotationVec.z * 2.0f);
            boulder.setVelocity(x * 0.8d, y + c * 0.3d , z * 0.8d, 1.0f, 8 - this.level().getDifficulty().getId() * 4);
            if(boulder != null) {
                this.level().spawnEntity(boulder);
            }
        }
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

        if(!this.isTame() && !this.level().isClientSide) {
            if(targetDir == Vec3.ZERO && this.getTarget() != null) {
                targetDir = new Vec3( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.level().isClientSide) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengers().contains(entity)) {
                entity.damage(entity.damageSources().mobAttack(this), 16.0f);
            }
        }
        this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }
}
