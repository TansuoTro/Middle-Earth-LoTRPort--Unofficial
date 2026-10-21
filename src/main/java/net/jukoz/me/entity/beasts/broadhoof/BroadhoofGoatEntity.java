package net.jukoz.me.entity.beasts.broadhoof;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.goals.BeastRevengeGoal;
import net.jukoz.me.entity.goals.ChargeAttackGoal;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.utils.ItemTagsME;
import net.jukoz.me.utils.PlayerUtil;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;

public class BroadhoofGoatEntity extends AbstractBeastEntity {
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)BroadhoofGoatEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 0.0);
    private static final float MAX_JUMP_STRENGTH_BONUS = (float)BroadhoofGoatEntity.getChildJumpStrengthBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = BroadhoofGoatEntity.getChildHealthBonus(max -> max - 1);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.INTEGER);
    private static final EntityDataAccessor<Integer> BEADS = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.INTEGER);
    private static final EntityDataAccessor<Boolean> HAIR = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEFT_HORN = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_HORN = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BRUSHED_BEARD = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MOUNTABLE = SynchedEntityData.registerData(BroadhoofGoatEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState jumpAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.BROADHOOF_GOAT.getDimensions().scaled(0.5f);

    public BroadhoofGoatEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(Attributes.GENERIC_MAX_HEALTH, 50.0d)
                .add(Attributes.GENERIC_KNOCKBACK_RESISTANCE, 0.4d)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributesME.SAFE_FALL_DISTANCE.value(), 10.0d)
                .add(EntityAttributesME.POWDERED_SNOW_IMMUNITY.value(), 1.0f)
                .add(Attributes.HORSE_JUMP_STRENGTH, 1);
    }

    @Override
    protected void initAttributes(RandomSource random) {
        this.getAttributeInstance(Attributes.GENERIC_MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(Attributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus(random::nextDouble));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 2.5, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(5, new BreedGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 1.0, Ingredient.fromTag(ItemTagsME.COW_FOOD), false));
        this.goalSelector.add(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(8, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(BEADS, 0);
        this.dataTracker.startTracking(HAIR, false);
        this.dataTracker.startTracking(LEFT_HORN, true);
        this.dataTracker.startTracking(RIGHT_HORN, true);
        this.dataTracker.startTracking(BRUSHED_BEARD, false);
        this.dataTracker.startTracking(MOUNTABLE, true);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        WriteView view = new WriteView(nbt);
        view.putInt("Variant", this.getGoatVariant());
        view.putInt("Beads", this.getGoatBeadsIndex());
        view.putBoolean("Hair", this.hasHair());
        view.putBoolean("HasLeftHorn", this.hasLeftHorn());
        view.putBoolean("HasRightHorn", this.hasRightHorn());
        view.putBoolean("HasBrushedBeard", this.hasBrushedBeard());
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        ReadView view = new ReadView(nbt);
        this.dataTracker.set(VARIANT, view.getInt("Variant", 0));
        this.dataTracker.set(BEADS, view.getInt("Beads", 0));
        this.dataTracker.set(HAIR, view.getBoolean("Hair", false));
        this.dataTracker.set(LEFT_HORN, view.getBoolean("HasLeftHorn", true));
        this.dataTracker.set(RIGHT_HORN, view.getBoolean("HasRightHorn", true));
        this.dataTracker.set(BRUSHED_BEARD, view.getBoolean("HasBrushedBeard", false));
        this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double getChildJumpStrengthBonus(DoubleSupplier randomDoubleGetter) {
        return (double)0.8f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2;
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2 + randomDoubleGetter.getAsDouble() * 0.2) * 0.25;
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.GOOD;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.DWARF);
    }

    @Override
    public InteractionResult interactMob(Player player, InteractionHand hand) {

        ItemStack itemStack = player.getStackInHand(hand);

        if(this.isClientWorld()) { // Client
            if(itemStack.isEmpty()) {
                return super.interactMob(player, hand);
            }
        }
        else { // Server
            for(RaceType race : this.getCompatibleRaces()) { // Check for race
                if(PlayerUtil.isOfRace(player, race) || player.isCreative()) {
                    if(this.isTame()) {
                        if (this.isBreedingItem(itemStack)) { // Feed
                            if(this.getHealth() < this.getMaxHealth()) { // Food provides health
                                if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                                FoodProperties foodComponent = itemStack.getItem().getFoodComponent();
                                float f = foodComponent != null ? (float)foodComponent.getHunger() : 1.0f;
                                this.heal(2.0f * f);
                                return InteractionResult.SUCCESS;
                            }
                            else if (this.getBreedingAge() == 0 && this.canEat()) { // Food provides baby
                                this.eat(player, hand, itemStack);
                                this.lovePlayer(player);
                                return InteractionResult.SUCCESS;
                            }
                        }
                        else if(itemStack.isOf(Items.BRUSH)) { // Brush beard
                            this.setBrushedBeard(true);
                            return InteractionResult.SUCCESS;
                        }
                        else if(hasBrushedBeard()) {
                            if(BroadhoofGoatBeads.isValidMaterial(itemStack)) {
                                this.setGoatBeads(BroadhoofGoatBeads.getBeads(itemStack));
                                if (!player.getAbilities().creativeMode) itemStack.decrement(1);

                                return InteractionResult.SUCCESS;
                            }
                            else if(itemStack.isOf(Items.SHEARS)) { // Un-Brush beard
                                if(getGoatBeads().equals(BroadhoofGoatBeads.NONE)) {
                                    this.setBrushedBeard(false);
                                }
                                else {
                                    this.setGoatBeads(BroadhoofGoatBeads.NONE);
                                }

                                if(!player.isCreative()) {
                                    itemStack.damage(1, player, (p) -> {});
                                }

                                return InteractionResult.SUCCESS;
                            }
                        }
                    }

                    return super.interactMob(player, hand);
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        float animationSpeed = this.limbAnimator.getSpeed();
        float animationProgress = this.limbAnimator.getPos() * (Mth.PI / 180) * 18;

        boolean sprinting = passenger.isSprinting();

        // frequency is calculated by dividing the speed of the animation by the duration of the animation.
        float frequency = sprinting ? (1.2f/0.74f) : 4;

        double y = sprinting ?
                Mth.sin(animationProgress * frequency + Mth.PI / 4) * 0.11 * animationSpeed - 0.05 :
                Mth.cos(animationProgress * frequency) * 0.06 * animationSpeed - 0.05;

        if(this.isSitting()) {
            y = -0.5;
        }

        passenger.setPos(passenger.getX(), passenger.getY() + y, passenger.getZ());
    }

    @Override
    @Nullable
    public AgeableMob createChild(ServerLevel world, AgeableMob entity) {
        BroadhoofGoatEntity broadhoofEntity = (BroadhoofGoatEntity)entity;
        BroadhoofGoatEntity broadhoofEntity2 = EntitiesME.BROADHOOF_GOAT.create(world);
        if (broadhoofEntity2 != null) {
            int i = this.random.nextInt(9);
            BroadhoofGoatColor goatColor = i < 4 ? this.getGoatColor() : (i < 8 ? broadhoofEntity.getGoatColor() : Util.getRandom(BroadhoofGoatColor.values(), this.random));
            i = this.random.nextInt(9);
            BroadhoofGoatPattern goatPattern = i < 4 ? this.getPattern() : (i < 8 ? broadhoofEntity.getPattern() : Util.getRandom(BroadhoofGoatPattern.values(), this.random));
            i = this.random.nextInt(5);
            BroadhoofGoatHorns broadhoofHorns = i < 2 ? this.getHorns() : (i < 4 ? broadhoofEntity.getHorns() : Util.getRandom(BroadhoofGoatHorns.values(), this.random));

            broadhoofEntity2.setGoatVariant(goatColor, goatPattern, broadhoofHorns);
            this.setChildAttributes(entity, broadhoofEntity2);
        }
        return broadhoofEntity2;
    }

    @Override
    protected void setChildAttributes(AgeableMob other, AbstractHorse child) {
        this.setChildAttribute(other, child, Attributes.GENERIC_MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, Attributes.HORSE_JUMP_STRENGTH, MIN_JUMP_STRENGTH_BONUS, MAX_JUMP_STRENGTH_BONUS);
        this.setChildAttribute(other, child, Attributes.GENERIC_MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    public boolean isMountable() {
        return this.dataTracker.get(MOUNTABLE);
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isMountable();
    }

    @Override
    public boolean canBreedWith(Animal other) {
        return other instanceof BroadhoofGoatEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTagsME.GOAT_FOOD);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(!this.getWorld().isClient() && super.tryAttack(target)) {
            this.getWorld().sendEntityStatus(this, EntityEvent.PLAY_ATTACK_SOUND);
            return true;
        }

        return false;
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2,0,0.2));

        if(!this.isTame() && !this.getWorld().isClient) {
            if(targetDir == Vec3.ZERO && this.getTarget() != null) {
                targetDir = new Vec3( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)Mth.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)Mth.abs(this.chargeTimeout - (maxChargeCooldown() - chargeDuration()) - (chargeDuration() * 0.2f)) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity != this.getOwner() && entity != this && !this.getPassengerList().contains(entity) && !this.getWorld().isClient()) {
                entity.damage(entity.getDamageSources().mobAttack(this), getAttackDamage());

                Vec3 velocity = this.getVelocity();
                velocity = velocity.multiply(1.0, 0.0, 1.0);
                velocity = velocity.normalize();
                Vec3 vec3d = velocity.multiply(2);
                if (vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.15, vec3d.z);
                }

                if(this.random.nextInt(10) == 0 && !this.isTame() && !this.isBaby()) {
                    this.dropHorn();
                }

                this.setCharging(false);
            }
        }
        this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }

    @Override
    protected void jump(float strength, Vec3 movementInput) {
        if(this.hasControllingPassenger() && this.getControllingPassenger().isSprinting()) {
            super.jump(strength, movementInput);
        }
        else {
            this.setChargeTimeout(30);
            double d = this.getJumpVelocity();
            Vec3 vec3d = this.getVelocity().multiply(4);
            this.setVelocity(vec3d.x, d, vec3d.z);
            this.setOnGround(false);
            this.velocityDirty = true;
            if (movementInput.z > 0.0) {
                float f = Mth.sin(this.getYaw() * ((float)Math.PI / 180));
                float g = Mth.cos(this.getYaw() * ((float)Math.PI / 180));
                this.setVelocity(this.getVelocity().add(-0.4f * f * strength, 0.0, 0.4f * g * strength));
            }
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger() && !this.getControllingPassenger().isSprinting()) {
            if(!this.isSitting()) {
                this.jumping = true;
                this.playJumpSound();
            }
            else {
                this.setSitting(false);
            }
        }
        else {
            super.startJumping(height);
        }
    }

    @Override
    public int maxChargeCooldown() {
        return 120;
    }

    @Override
    public int chargeDuration() {
        return 16;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    public boolean dropHorn() {
        boolean bl = this.hasLeftHorn();
        boolean bl2 = this.hasRightHorn();
        if (!bl && !bl2) {
            return false;
        }
        EntityDataAccessor<Boolean> trackedData = !bl ? RIGHT_HORN : (!bl2 ? LEFT_HORN : (this.random.nextBoolean() ? LEFT_HORN : RIGHT_HORN));
        this.dataTracker.set(trackedData, false);
        Vec3 vec3d = this.getPos();
        ItemStack itemStack = this.getGoatHornStack();
        double d = Mth.nextBetween(this.random, -0.2f, 0.2f);
        double e = Mth.nextBetween(this.random, 0.3f, 0.7f);
        double f = Mth.nextBetween(this.random, -0.2f, 0.2f);
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), vec3d.getX(), vec3d.getY(), vec3d.getZ(), itemStack, d, e, f);
        this.getWorld().spawnEntity(itemEntity);
        return true;
    }

    public ItemStack getGoatHornStack() {
        RandomSource random = RandomSource.create((long)this.getUuid().hashCode());
        TagKey<Instrument> tagKey = this.random.nextBoolean() ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
        return this.getWorld().getRegistryManager().get(Registries.INSTRUMENT).getEntryList(tagKey)
                .flatMap(list -> list.getRandom(random))
                .map(registryEntry -> InstrumentItem.getStackForInstrument(Items.GOAT_HORN, registryEntry))
                .orElseGet(() -> new ItemStack(Items.GOAT_HORN));
    }

    protected void setupAnimationStates() {
        if(this.isSitting()) {
            this.startSittingAnimationState.startIfNotRunning(this.age);
        }
        if(!this.isSitting() && this.startSittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.stopSittingAnimationState.start(this.age);
        }

        if(!this.isOnGround() && this.hasControllingPassenger()) {
            this.jumpAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.jumpAnimationState.stop();
        }
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    protected float getSaddledSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED) * 0.5f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED) * 2f) : ((float)this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED) * 0.75f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(ItemTagsME.GOAT_FOOD);
    }

    /* VARIANTS */
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        BroadhoofGoatColor color = Util.getRandom(BroadhoofGoatColor.values(), this.random);
        BroadhoofGoatPattern pattern = BroadhoofGoatPattern.PATTERN_COMBINATIONS.get(color).getRandom().getItem();
        BroadhoofGoatHorns horns = Util.getRandom(BroadhoofGoatHorns.values(), this.random);

        this.setGoatVariant(color, pattern, horns);

        this.setHair(this.random.nextBoolean());

        if(!this.getWorld().isClient()) {
            this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
        }

        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    // VARIANTS

    private void setGoatVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    private int getGoatVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setGoatVariant(BroadhoofGoatColor color, BroadhoofGoatPattern pattern, BroadhoofGoatHorns horns) {
        this.setGoatVariant(color.getIndex() & 0xFF | pattern.getIndex() << 8 & 0xFF00 | horns.getId() << 16 & 0xFF0000);
    }

    public BroadhoofGoatColor getGoatColor() {
        return BroadhoofGoatColor.byIndex(this.getGoatVariant() & 0xFF);
    }

    private void setGoatColor(BroadhoofGoatColor color) {
        this.setGoatVariant(color.getIndex() & 0xFF00);
    }

    public BroadhoofGoatPattern getPattern() {
        return BroadhoofGoatPattern.byIndex((this.getGoatVariant() & 0xFF00) >> 8);
    }

    public BroadhoofGoatHorns getHorns() {
        return BroadhoofGoatHorns.byId(this.getGoatVariant() & 0xFF0000);
    }

    public void setGoatBeads(BroadhoofGoatBeads beads) {
        this.dataTracker.set(BEADS, beads.getIndex() & 0xFF);
    }

    public int getGoatBeadsIndex() {
        return this.dataTracker.get(BEADS);
    }

    public BroadhoofGoatBeads getGoatBeads() {
        return BroadhoofGoatBeads.byIndex(getGoatBeadsIndex());
    }

    public boolean hasHair() {
        return this.dataTracker.get(HAIR);
    }

    public void setHair(boolean hair) {
        this.dataTracker.set(HAIR, hair);
    }

    public boolean hasRightHorn() {
        return this.dataTracker.get(RIGHT_HORN);
    }

    public boolean hasLeftHorn() {
        return this.dataTracker.get(LEFT_HORN);
    }

    public void setBrushedBeard(boolean brushedBeard) {
        this.dataTracker.set(BRUSHED_BEARD, brushedBeard);
    }
    public boolean hasBrushedBeard() {
        return this.dataTracker.get(BRUSHED_BEARD);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GOAT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GOAT_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_GOAT_PREPARE_RAM;
    }

    @Override
    public void playAngrySound() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(SoundType group) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_GOAT_LONG_JUMP, 1.0f, 0.7f);
    }
}
