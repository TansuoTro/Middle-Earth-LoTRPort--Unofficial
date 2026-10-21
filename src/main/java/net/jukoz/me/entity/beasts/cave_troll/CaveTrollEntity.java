package net.jukoz.me.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.Map;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.spider.Pouncer;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.sound.SoundsME;
import net.jukoz.me.utils.PlayerUtil;
import net.jukoz.me.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;

// TODO Add sounds
public class CaveTrollEntity extends AbstractBeastEntity {
    public LootTable scavengeLootTable;
    public LootParams lootWorldContext;
    private float smashingStrength; // Used in server-side only
    private float smashingTime; // Used in server-side only
    private float enragedTime; // Used in server-side only
    public static final EntityDataAccessor<Boolean> SCAVENGING = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SMASHING = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.registerData(CaveTrollEntity.class, EntityDataSerializers.INTEGER);
    public final AnimationState chaseAnimationState = new AnimationState();
    public final AnimationState scavengingAnimationState = new AnimationState();
    public final AnimationState startSleepingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState stopSleepingAnimationState = new AnimationState();
    public final AnimationState roaringAnimationState = new AnimationState();
    public final AnimationState smashingAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
        if(scavengeLootTable == null && !world.isClientSide()) {
            if(world instanceof ServerLevel serverWorld) {

                LootTable lootTable = serverWorld.getServer().getLootData().getLootTable(new ResourceLocation(MiddleEarth.MOD_ID, "gameplay/cave_troll_scavenging"));

                if(lootTable != null) {
                    scavengeLootTable = lootTable;

                    lootWorldContext = new LootParams.Builder(serverWorld)
                            .add(LootContextParams.THIS_ENTITY, this)
                            .add(LootContextParams.ORIGIN, this.getPos())
                            .build(LootContextParamSets.CHEST);
                }

            }
        }
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.MAX_HEALTH, 120.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.GENERIC_ATTACK_SPEED, 0.65)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 15.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(SCAVENGING, false);
        this.entityData.define(ROARING, false);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(SMASHING, false);
        this.entityData.define(ENRAGED, false);
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        WriteView view = new WriteView(nbt);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        ReadView view = new ReadView(nbt);
        this.entityData.set(VARIANT, view.getInt("Variant", 0));
    }

    @Nullable
    @Override
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        CaveTrollVariant variant = Util.getRandom(CaveTrollVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(CaveTrollVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    protected void mobTick() {
        ProfilerFiller profiler = this.level().getProfiler();
        profiler.push("caveTrollBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        profiler.swap("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        if(!this.isClientWorld() && this.isAffectedByDaylight()) {
            this.addStatusEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100));
        }

        super.mobTick();
    }

    @Override
    public void tryBonding(Player player) {
        double rand = this.random.nextDouble();

        if(rand < 0.15 || (rand < 0.3 && this.getTameness() <= 0) || player.isCreative()) { // Tame success, chance is twice as high if the troll is feral
            this.tameBeast(player);
            this.level().sendEntityStatus(this, EntityEvent.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else if(rand > 0.7) { // Tame failure (wake up, become enraged)
            this.enragedTime = this.age;
            this.setEnraged(true);
            this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
            this.addStatusEffect(new MobEffectInstance(MobEffects.STRENGTH, 1200));
            this.level().sendEntityStatus(this, EntityEvent.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    @Override
    public InteractionResult interactMob(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!this.level().isClientSide()) { // Server side
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {

                    if(isTrollWeapon(itemStack) && isOwner(player) && this.getMainHandStack().isEmpty()) { // Give the troll a weapon
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack.copyAndEmpty());
                        if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                        return InteractionResult.SUCCESS;
                    }
                    else if(player.isShiftKeyDown() && itemStack.isEmpty() && isOwner(player) && !this.getMainHandStack().isEmpty()) {  // Take weapon away from troll
                        player.giveItemStack(this.getMainHandStack().copyAndEmpty());
                        return InteractionResult.SUCCESS;
                    }
                    else if(canAddPassenger(player) && isTame() && itemStack.isEmpty()) { // Ride if player is compatible and hand is empty
                        putPlayerOnBack(player);
                        return InteractionResult.SUCCESS;
                    }
                    else if(!itemStack.isEmpty()) {
                        return super.interactMob(player, hand);
                    }
                }
            }
        }
        else {  // Client side
            if(!itemStack.isEmpty()) {
                return super.interactMob(player, hand);
            }
        }

        return InteractionResult.PASS; // Player is of incompatible race - don't interact
    }

    @Override
    public boolean canBeLeashedBy(Player player) {
        return false;
    }

    @Override
    public boolean usesTameness() {
        return true;
    }

    public boolean isTrollWeapon(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "troll_weapons")));
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isSleeping() || player.isCreative();
    }

    @Override
    public boolean isMountable() { // This method only determines whether the entity is mountable via the usual horse method
        return false;
    }

    @Override
    public void tameBeast(Player player) {
        if (player instanceof ServerPlayer) {
            this.tameBeast((LivingEntity) player);
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }
    }

    @Override
    public void tameBeast(LivingEntity livingEntity) {
        if(!this.level().isClientSide()) {
            this.setTame(true);
            this.setTameness(75);
            this.stopSleeping();
            this.getBrain().remember(MemoryModulesME.TAME, true);
            this.getBrain().erase(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN);
            this.getBrain().erase(MemoryModulesME.FOOD_EATEN_COUNT);
            this.getBrain().erase(MemoryModuleType.NEAREST_ATTACKABLE);
            this.getBrain().erase(MemoryModuleType.ATTACK_TARGET);
            this.setOwnerUuid(livingEntity.getUuid());
        }

    }

    @Override
    public void tickMovement() {
        if(this.level().isClientSide) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetInBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetInBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }

            if(this.isSmashing() && this.hasControllingPassenger()) {
                if(this.age - this.smashingTime > 30) {
                    smashAttack(smashingStrength);
                }
            }

            if(this.isEnraged() && this.age - this.enragedTime > 1200) {
                this.setEnraged(false);
            }
        }

        super.tickMovement();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {   // Allow 3 people to ride the troll
        if(this.isSitting()) {
            return false;
        }
        return this.isSaddled() ? getPassengers().size() < 3 : getPassengers().isEmpty();
    }

    @Override
    protected float getSaddledSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.2f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.25f) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.2f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.MoveFunction positionUpdater)  {
        super.updatePassengerPosition(passenger, positionUpdater);
        List<Entity> passengerList = this.getPassengers();
        boolean saddled = this.isSaddled();
        boolean sprinting = this.isCharging();

        if(this.getControllingPassenger() != null) {
            sprinting = this.getControllingPassenger().isSprinting() || this.isCharging();
        }

        float animationSpeed = this.limbAnimator.getSpeed();
        float animationProgress = this.limbAnimator.getPos() * (Mth.PI / 180) * 18;
        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (2f/1.25f) : (10f/1.75f);

        if(passenger.equals(this.getControllingPassenger()) || !saddled) { // Passenger 1 - Controlling ============================================================================
            double y = sprinting ?
                    -Mth.cos(2 * frequency * animationProgress) * 0.06 * animationSpeed + 0.1 : // height when sprinting
                    Mth.sin(2 * frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    Mth.sin(frequency * animationProgress - (4f/15f)*Mth.PI) * 0.225 : // side-to-side movement when sprinting
                    Mth.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(!saddled) {
                y -= 0.32;
                front += 0.5;
            }

            if(this.level().isClientSide() && this.smashingAnimationState.isRunning()) {
                float time = (this.smashingAnimationState.getTimeRunning() / 2000.0F) * 2 * Mth.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getTimeRunning() < 1000) {
                    front -= Mth.sin(time) * 0.3;
                }
                else {
                    front -= Mth.sin(time) * 2f;
                    y += Mth.sin(time) * 0.3f;
                }

            }

            double x = Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side - Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;
            double z = Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side + Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;

            passenger.setPos(passenger.getX() + x, passenger.getY() + y, passenger.getZ() + z);
        }
        else { // Passenger 2 or 3 - Side ==============================================================================
            double y = sprinting ?
                    -Mth.cos(frequency * animationProgress) * 0.06 * animationSpeed : // height when sprinting
                    Mth.sin(frequency * animationProgress) * 0.02; // height when walking

            double side = sprinting ?
                    Mth.sin(frequency * animationProgress - (4f/15f)*Mth.PI) * 0.15 : // side-to-side movement when sprinting
                    Mth.sin(frequency * animationProgress) * 0.04; // side-to-side movement when walking

            double front = sprinting ?
                    0.35 : // front-back movement when sprinting
                    0; // front-back movement when walking

            if(passengerList.size() >= 3 && passenger.equals(passengerList.get(2))) {   // The left passenger (2) moves inverted to the right one (1)
                y = -y;
            }

            y = sprinting ? y + 0.15 : y; // Add offset if sprinting

            if(this.level().isClientSide()) {
                float time = (this.smashingAnimationState.getTimeRunning() / 2000.0F) * 2 * Mth.PI; // Goes from 0 to 2Pi over the duration of the animation
                if(this.smashingAnimationState.getTimeRunning() < 1000) {
                    front -= Mth.sin(time) * 0.3;
                }
                else {
                    front -= Mth.sin(time) * 1.8f;
                    y += Mth.sin(time) * 0.3f;
                }

            }

            double x = Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side - Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;
            double z = Mth.sin((float)Math.toRadians(this.getVisualRotationYInDegrees())) * side + Mth.cos((float)Math.toRadians(this.getVisualRotationYInDegrees())) * front;

            passenger.setPos(passenger.getX() + x, passenger.getY() + y, passenger.getZ() + z);
        }
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    protected void setupAnimationStates() {
        if(this.isScavenging()) { // Looking for food
            this.scavengingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.scavengingAnimationState.stop();
        }

        if(this.isSitting()) { // Sitting
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        else if(this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.startIfNotRunning(this.age);
        }
        if(this.stopSittingAnimationState.getTimeRunning() > 3000) {
            this.stopSittingAnimationState.stop();
        }

        if(this.isSleeping()) { // Sleeping
            if(!this.startSleepingAnimationState.isRunning() && !this.sleepingAnimationState.isRunning()) {
                this.startSleepingAnimationState.startIfNotRunning(this.age);
            }
            if (this.startSleepingAnimationState.getTimeRunning() > 5000) {
                this.sleepingAnimationState.startIfNotRunning(this.age);
                this.startSleepingAnimationState.stop();
            }
        }
        else if(this.startSleepingAnimationState.isRunning() || this.sleepingAnimationState.isRunning()) {
            this.startSleepingAnimationState.stop();
            this.sleepingAnimationState.stop();
            this.stopSleepingAnimationState.startIfNotRunning(this.age);
        }
        if(this.stopSleepingAnimationState.getTimeRunning() > 5000) {
            this.stopSleepingAnimationState.stop();
        }

        if(this.isRoaring()) {
            this.roaringAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.roaringAnimationState.stop();
        }

        if(this.isSmashing()) {
            this.smashingAnimationState.startIfNotRunning(this.age);
        }
        else if(smashingAnimationState.getTimeRunning() >= 2000) {
            smashingAnimationState.stop();
        }
    }

    @Override
    public int chargeDuration() {
        return 30;
    }

    @Override
    public int maxChargeCooldown() {
        return 300;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));
        Vec3 direction = Vec3.ZERO;
        LivingEntity target = this.getTarget();
        int difficulty = this.hasControllingPassenger() ? this.level().getDifficulty().getId() : 0;

        if(!this.isTame() && !this.level().isClientSide) { // Charge Attack for wild Troll
            if(target != null) { // Has attack target memory
                direction = this.getPos().relativize(target.getPos()); // Vector from Troll to target entity
            }

            this.setYaw((float) Math.toDegrees(Math.atan2(-direction.x, direction.z))); // Turning the troll into the right direction
            this.setChargeVelocity(direction);
        }
        else if (this.level().isClientSide && this.hasControllingPassenger()) { // Charge Attack for tamed Troll
            this.setChargeVelocity(this.getRotationVector());
        }

        for(Entity entity : entities) { // Check through all nearby entities
            if(entity != this.getOwner() && !this.getPassengers().contains(entity)) { // Exclude passengers and owner
                if(getWorld() instanceof ServerLevel serverWorld) {
                    entity.damage(entity.damageSources().mobAttack(this), 10.0f + difficulty * 2);
                }

            }
        }
        this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 0.1, 1);
    }

    public void smashAttack(float strength) { // Strength goes from 0 to 100
        setSmashing(false);
        AABB box = new AABB(this.getPos().subtract(5,0,5), this.getPos().add(5,1,5));

        double weaponDamage = 0;
        for (Map.Entry<Attribute, AttributeModifier> entry : this.getMainHandStack().getAttributeModifiers(EquipmentSlot.MAINHAND).entries()) {
            if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                weaponDamage = entry.getValue().getValue();
            }
        }

        List<Entity> entities = this.level().getEntities(this, box);
        Level world = this.level();
        int difficulty = this.hasControllingPassenger() ? world.getDifficulty().getId() : 0;

        if(world instanceof ServerLevel serverWorld) {
            for(Entity entity : entities) {
                if(this.isValidTarget(entity)) {
                    entity.damage(this.damageSources().mobAttack(this),  (float)weaponDamage + (strength / 12.5f) + (difficulty * 2));
                }
            }

            for(int x = -5; x <= 5; x++) { // Spawn particles on affected blocks
                for(int z = -5; z <= 5; z++) {
                    BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(new BlockPos(this.getBlockX() + x, this.getBlockY() - 1, this.getBlockZ() + z)));
                    serverWorld.spawnParticles(particles, this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z, 7, 0.5, 0.3, 0.5, 0.2);
                }
            }

            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1, 0.4f);
            if(weaponDamage > 0) {
                this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1, 0.1f);
            }
        }
    }

    @Override
    protected void jump(float strength, Vec3 movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.jump(strength, movementInput);
        }
        else if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            setChargeTimeout(300);
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.playJumpSound();
                this.setSmashing(true);
                this.smashingTime = this.age;
                this.smashingStrength = height;
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.startJumping(height);
        }
    }

    public void startSleeping() {
        if (this.isPassenger()) {
            this.stopRiding();
        }

        this.setSleeping(true);
        this.setVelocity(Vec3.ZERO);
        this.velocityDirty = true;

        this.brain.erase(MemoryModuleType.WALK_TARGET);
        this.brain.erase(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        this.setSleeping(false);
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean isSleeping) {
        this.entityData.set(SLEEPING, isSleeping);
    }

    public boolean isEnraged() {
        return this.entityData.get(ENRAGED);
    }

    public void setEnraged(boolean isEnraged) {
        this.entityData.set(ENRAGED, isEnraged);
    }

    public boolean isSmashing() {
        return this.entityData.get(SMASHING);
    }

    public void setSmashing(boolean isSmashing) {
        this.entityData.set(SMASHING, isSmashing);
    }

    @Override
    public void setSitting(boolean sitting) {
        if(!this.level().isClientSide()) {
            if(sitting) {
                this.getBrain().remember(MemoryModulesME.SITTING, true);
                this.getBrain().erase(MemoryModuleType.WALK_TARGET);
            }
            else {
                this.getBrain().erase(MemoryModulesME.SITTING);
            }

            this.ejectPassengers();
        }

        super.setSitting(sitting);
    }

    public boolean isScavenging() {
        return this.entityData.get(SCAVENGING);
    }

    public void setScavenging(boolean isDigging) {
        this.entityData.set(SCAVENGING, isDigging);
    }
    public boolean isRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean isRoaring) {
        this.entityData.set(ROARING, isRoaring);
    }

    @Nullable
    private LivingEntity getTargetInBrain() {
        return this.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    public Brain<CaveTrollEntity> getBrain() {
        return (Brain<CaveTrollEntity>)super.getBrain();
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return ImmutableList.of(RaceType.SNAGA, RaceType.GOBLIN, RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "troll_food")));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "chains")));
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof Player || target instanceof Pouncer;
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            return this.distanceTraveled + 1.0f;
        }
        return this.distanceTraveled + 0.25f;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsME.CAVE_TROLL_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsME.CAVE_TROLL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getEatSound() {
        return super.getEatSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsME.CAVE_TROLL_IDLE;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            this.playSound(SoundsME.CAVE_TROLL_STEP, 1.5f, 1.0f);
        }
        else {
            this.playSound(SoundsME.CAVE_TROLL_STEP, 1.0f, 1.0f);
        }
    }

    @Nullable
    protected SoundEvent getRoarSound() {
        return SoundsME.CAVE_TROLL_ROAR;
    }

    public void playRoarSound() {
        this.playSound(this.getRoarSound(), 1.0f, 1.0f);
    }

    public static boolean canSpawn(EntityType<CaveTrollEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean canSpawn(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
