package net.jukoz.me.entity.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityGroup;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.entity.TrackedDataHandlerRegistryME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.npcs.data.NpcData;
import net.jukoz.me.entity.npcs.data.NpcInitializationData;
import net.jukoz.me.entity.npcs.data.NpcTextureData;
import net.jukoz.me.entity.npcs.initializer.NpcEntityInitializer;
import net.jukoz.me.entity.npcs.initializer.NpcSpawnEggHelper;
import net.jukoz.me.entity.npcs.renderer.NpcRenderedPart;
import net.jukoz.me.entity.spider.Pouncer;
import net.jukoz.me.entity.spider.larva.ShelobiteLarvaEntity;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.jukoz.me.entity.spider.spawn.SpawnOfShelobEntity;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.common.EntityCategories;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import net.jukoz.me.resources.datas.npc_types.data.LootData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.ItemTagsME;
import net.jukoz.me.utils.SpawnUtil;
import net.jukoz.me.entity.snail.SnailEntity;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class NpcEntity extends PathfinderMob implements CrossbowAttackMob {
    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String IS_FIGHTING = "IsFighting";
    }
    // [TrackedDatas]
    private static final EntityDataAccessor<NpcData> NPC_DATA;
    private static final EntityDataAccessor<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final EntityDataAccessor<NpcTextureData> NPC_TEXTURE_DATA;
    private static final EntityDataAccessor<Boolean> IS_FIGHTING;

    private static final EntityDataAccessor<Integer> USING_ITEM = SynchedEntityData.registerData(NpcEntity.class, EntityDataSerializers.INTEGER);
    private static final EntityDataAccessor<Boolean> CROSSBOW_CHARGING = SynchedEntityData.registerData(NpcEntity.class, EntityDataSerializers.BOOLEAN);

    private final CustomBowAttackGoal bowAttackGoal = new CustomBowAttackGoal<>(this, 1.0, 20, 16.0F);
    private final NpcCrossBowAttackGoal crossBowAttackGoal = new NpcCrossBowAttackGoal<>(this, 1.0, 11.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        @Override
        public void stop() {
            super.stop();
            NpcEntity.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            NpcEntity.this.setAttacking(true);
        }
    };

    public final AnimationState walkingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState aimingState = new AnimationState();
    public final AnimationState attackState = new AnimationState();
    public final AnimationState swingState = new AnimationState();

    public NpcEntity(EntityType<NpcEntity> entityType, Level world) {
        super(entityType, world);
        this.updateAttackType();
    }

    @Nullable
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        this.saveSpawnReason(spawnReason);
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    // [IsFighting]
    public void setFighting(boolean state){
        this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getFighting(){
        return this.dataTracker.get(IS_FIGHTING);
    }
    // [IsBlocking] // TODO
    public void setBlocking(boolean state){
        //this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getBlocking(){
        return false; //this.dataTracker.get(IS_FIGHTING);
    }
    // [NpcTextureData]
    public void saveNpcTextureData(NpcTextureData npcTextureData) {
        this.dataTracker.set(NPC_TEXTURE_DATA, npcTextureData);
    }
    public NpcTextureData retrieveNpcTextureData() {
        return this.dataTracker.get(NPC_TEXTURE_DATA);
    }
    public boolean hasTextureData(){
        return retrieveNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }
    public boolean shouldRefreshVisuals() {
        return this.retrieveNpcTextureData().needToBeRefreshed();
    }
    // [NpcInitializationData]
    public void saveNpcInitializationData(NpcInitializationData npcInitializationData) {
        this.dataTracker.set(NPC_INITIALIZATION_DATA, npcInitializationData);
    }
    public NpcInitializationData retrieveNpcInitializationData() {
        return this.dataTracker.get(NPC_INITIALIZATION_DATA);
    }
    public void prepare() {
        NpcInitializationData npcInitializationData = retrieveNpcInitializationData();
        boolean result = npcInitializationData.tryToInitialize(this);
        if(result)
            resetInitializationData();
    }

    public void resetInitializationData() {
        this.dataTracker.set(NPC_INITIALIZATION_DATA, new NpcInitializationData());
    }
    // [NpcTypeIdentifier]
    public void prepareNpcIdentifier(ResourceLocation npcTypeIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withType(npcTypeIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }
    // [NpcData]
    public void saveNpcData(NpcData npcData) {
        this.dataTracker.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.dataTracker.get(NPC_DATA);
    }
    // [NpcType]
    public NpcType getNpcType(){
        return retrieveNpcData().getNpcType();
    }
    public void saveNpcType(Holder<NpcType> npcType){
        NpcData newNpcData = this.retrieveNpcData().withType(npcType);
        this.saveNpcData(newNpcData);
    }
    // [Loot Data]
    public LootData retrieveLootData(){
        NpcType npcType = retrieveNpcData().getNpcType();
        if(npcType == null)
            return null;
        return npcType.getLootData();
    }
    // [Category]
    public EntityCategories getNpcCategory() {
        return retrieveNpcData().getCategory();
    }
    public void saveCategory(EntityCategories category) {
        NpcData newNpcData = this.retrieveNpcData().withCategory(category);
        this.saveNpcData(newNpcData);
    }
    // [MobSpawnType]
    private void saveSpawnReason(MobSpawnType spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }
    public MobSpawnType getSpawnReason() {
        return this.retrieveNpcData().getSpawnReason();
    }
    // [StructureManager]
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(blockEntity.getPos());
        this.saveNpcData(newNpcData);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return EntityAttributesME.addDefaults(Mob.createMobAttributes())
                .add(Attributes.GENERIC_ATTACK_SPEED, 4.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new HurtByTargetGoal(this));
        this.targetSelector.add(3, new TargetPlayerDiplomacyGoal(this));
        this.targetSelector.add(4, new NpcDoorInteractGoal(this, true));
        this.targetSelector.add(5, new TargetNPCDiplomacyGoal(this));
        this.targetSelector.add(6, new NearestAttackableTargetGoal<>(this, SnowTrollEntity.class, true));
        this.targetSelector.add(7, new NearestAttackableTargetGoal<>(this, SpawnOfShelobEntity.class, true));
        this.targetSelector.add(8, new NearestAttackableTargetGoal<>(this, ShelobiteScuttlerEntity.class, true));
        this.targetSelector.add(9, new NearestAttackableTargetGoal<>(this, ShelobiteLarvaEntity.class, true));
        this.targetSelector.add(10, new NearestAttackableTargetGoal<>(this, AbstractHorse.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        this.dataTracker.startTracking(NPC_DATA, new NpcData());
        this.dataTracker.startTracking(NPC_TEXTURE_DATA, new NpcTextureData());
        this.dataTracker.startTracking(IS_FIGHTING, false);
        this.dataTracker.startTracking(CROSSBOW_CHARGING, false);
        this.dataTracker.startTracking(USING_ITEM, 0);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        WriteView view = new WriteView(nbt, this.getWorld().getRegistryManager());
        view.put(KeyStrings.DATA, NpcData.CODEC, this.retrieveNpcData());
        view.put(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC, this.retrieveNpcInitializationData());
        view.put(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC, this.retrieveNpcTextureData());
        view.put(KeyStrings.IS_FIGHTING, Codec.BOOL, this.getFighting());
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        ReadView view = new ReadView(nbt, this.getWorld().getRegistryManager());
        NpcData foundNpcData = view.read(KeyStrings.DATA, NpcData.CODEC).orElse(new NpcData());
        NpcInitializationData foundNpcInitializationData = view.read(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC).orElse(new NpcInitializationData());
        NpcTextureData foundNpcTextureData = view.read(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC).orElse(new NpcTextureData());
        boolean isFighting = view.read(KeyStrings.IS_FIGHTING, Codec.BOOL).orElse(false);

        String npcDataId = view.read("NpcDataId", Codec.STRING).orElse(null);
        if(npcDataId != null){
            foundNpcInitializationData = foundNpcInitializationData.withType(MiddleEarth.fetchId(npcDataId));
            foundNpcData = new NpcData();
            foundNpcTextureData = new  NpcTextureData();
            isFighting = false;
        }

        this.dataTracker.set(NPC_DATA, foundNpcData);
        this.dataTracker.set(NPC_INITIALIZATION_DATA, foundNpcInitializationData);
        this.dataTracker.set(NPC_TEXTURE_DATA, foundNpcTextureData);
        this.dataTracker.set(IS_FIGHTING, isFighting);

        foundNpcInitializationData.tryToInitialize(this);

        this.updateAttackType();
    }

    @Override
    public boolean canUseRangedWeapon(ProjectileWeaponItem weapon) {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        if(getNpcType() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(getWorld(), getNpcType().getId());
    }
    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);
            ItemStack itemStack = this.getMainHandStack();
            if (itemStack.isOf(Items.BOW) || itemStack.isIn(ItemTagsME.BOW)) {
                int i = 30;
                if (this.getWorld().getDifficulty() != Difficulty.HARD) {
                    i = 20;
                }
                this.bowAttackGoal.setAttackInterval(i);
                this.goalSelector.add(4, this.bowAttackGoal);
            } if (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW)) {
                this.goalSelector.add(4, this.crossBowAttackGoal);
            } else {
                this.goalSelector.add(4, this.meleeAttackGoal);
            }
        }
    }

    public void updateTargetGoals() {
        //if (this.getWorld() != null && !this.getWorld().isClient) {
        //    if(getFaction().getDisposition() == DispositionType.EVIL) {
        //        this.targetSelector.add(10, new NearestAttackableTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        //        this.targetSelector.add(10, new NearestAttackableTargetGoal<>(this, GreatHornEntity.class, true));
        //    } else {
        //        this.targetSelector.add(5, new NearestAttackableTargetGoal<>(this, CaveTrollEntity.class, true));
        //        this.targetSelector.add(5, new NearestAttackableTargetGoal<>(this, StoneTrollEntity.class, true));
        //        this.targetSelector.add(6, new NearestAttackableTargetGoal<>(this, WargEntity.class, true));
        //    }
        //}
    }

    //region [DATA TRANSFER]
    // GETTERS
    public ResourceLocation getNpcTypeIdentifier(){
        return retrieveNpcData().getNpcTypeId();
    }
    public ResourceLocation getFactionIdentifier(){
        Faction faction = getFaction();
        if(faction == null) return null;
        return faction.getId();
    }
    public BlockPos getStructureManagerHostPos() {
        return this.retrieveNpcData().getStructureManagerPos();
    }
    //endregion

    public void tryToInitializeData(){
        if(Objects.equals(getBlockPos(), new BlockPos(0, 0, 0))) // 0,0,0 is what's used for commands, needs to be delayed
            return;

        this.prepare();

        Level world = getWorld();
        if(world.isClient)
            return;

        if(world instanceof ServerLevel serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isOf(DamageTypes.IN_WALL) && this.hasVehicle()) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected void mobTick() {
        tryToInitializeData();
        //CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
       // if(runtimeData != null)
           // runtimeData.tick(this, world);

        /*ProfilerFiller profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();*/
        super.mobTick();

        if(hasVehicle()){
            setYaw(getVehicle().getYaw());
            setPitch(getVehicle().getPitch());
        }
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);

        Vec3 offset = new Vec3(
                0.0,
                -0.3,
                -0.25
        );

        Vec3 rotated = rotatePoint(offset, this.getYaw());
        passenger.setPos(passenger.getX() + rotated.x, passenger.getY() + rotated.y, passenger.getZ() + rotated.z);
    }

    private static Vec3 rotatePoint(Vec3 point, float yaw) {
        return point.rotateY(-yaw * ((float)Math.PI / 180F));
    }

    @Override
    public boolean isUsingItem() {
        boolean value = super.isUsingItem();
        if(!value) {
            return this.dataTracker.get(USING_ITEM) > 0;
        }
        return value;
    }

    public void setNpcFlag(int mask, boolean value) {
        setLivingFlag(mask, value);

        if(value) {
            int i = this.dataTracker.get(USING_ITEM) + 1;
            this.dataTracker.set(USING_ITEM, i);
        } else {
            this.dataTracker.set(USING_ITEM, 0);
        }
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return NpcBrain.create(dynamic);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>) super.getBrain();
    }

    public float getFightingMovementSpeed(){
        double currentSpeed = this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED);
        return (float) (currentSpeed);
    }

    public boolean isFighting() {
        boolean isFighting = getFighting();

        this.setSprinting(isFighting);
        if(this.hasVehicle() && getVehicle() instanceof AbstractHorse abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(!this.getWorld().isClient) {

        } else {
            setupAnimationStates();
        }

    }

    private void setupAnimationStates() {
        if(this.forwardSpeed > 0) {
            this.walkingState.startIfNotRunning(this.age);
        } else {
            this.idleState.startIfNotRunning(this.age);
        }

        int bowPullProgress = this.getItemUseTime();
        if(bowPullProgress > 0) {
            this.aimingState.startIfNotRunning(this.age);
        }

    }

    @Override
    protected boolean couldAcceptPassenger() {
        return !this.hasVehicle();
    }

    @Override
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty) {
        // Overrides vanilla init equipment (gold sets???)
    }

    @Override
    public void onDeath(DamageSource source) {
        if(getVehicle() != null && getVehicle() instanceof LivingEntity vehicleEntity && vehicleEntity.getControllingPassenger() == this){
            vehicleEntity.removeAllPassengers();
            if(vehicleEntity instanceof AbstractHorse abstractHorseEntity){
                abstractHorseEntity.setTame(false);
                abstractHorseEntity.resetLoveTicks();
                abstractHorseEntity.setSprinting(false);
                abstractHorseEntity.setOwnerUuid(null);
            }
            if(vehicleEntity instanceof AbstractBeastEntity abstractBeastEntity){
                abstractBeastEntity.resetTameness();
            }
        }
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos != null){
            StructureManagerBlockEntity.triggerDeathSignal(structureManagerPos, this);
        }
        super.onDeath(source);
    }

    private boolean canDropLoot(DamageSource damageSource, boolean causedByPlayer){
        boolean canDropLoot = false;
        if(!causedByPlayer)
            return canDropLoot;
        if(damageSource == null)
            return canDropLoot;

        if(damageSource.getAttacker() instanceof Player player){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                canDropLoot = true;
            else if(data.getFaction() == null)
                canDropLoot = true;
            else{
                try{
                    Faction faction = FactionLookup.getFactionById(getWorld(), data.getFaction());
                    if(faction.isHostileToward(this.getFactionIdentifier()))
                        canDropLoot = true;
                } catch (FactionIdentifierException e){
                    canDropLoot = true;
                }
            }
        }

        return canDropLoot;
    }

    protected void drop(DamageSource damageSource) {
        if(!canDropLoot(damageSource, damageSource.getAttacker() != null && damageSource.getAttacker().isPlayer()))
            return;
        super.drop(damageSource);
    }

    public int getXpToDrop() {
        LootData lootData = retrieveLootData();
        if(lootData == null)
            return 1;
        return lootData.getExperience((ServerLevel) this.getWorld());
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        if (!(this.getWorld() instanceof ServerLevel serverWorld) || getNpcType() == null)
            return;

        LootTable lootTable = serverWorld.getServer().getLootManager().getLootTable(getNpcType().getId().withPrefixedPath("entities/"));

        if (lootTable != null) {
            LootParams.Builder builder = (new LootParams.Builder(serverWorld)).add(LootContextParams.THIS_ENTITY, this)
                    .add(LootContextParams.ORIGIN, this.getPos())
                    .add(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .addOptional(LootContextParams.KILLER_ENTITY, damageSource.getAttacker())
                    .addOptional(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getSource());
            if (damageSource.getAttacker() instanceof Player playerEntity) {
                builder = builder.add(LootContextParams.LAST_DAMAGE_PLAYER, playerEntity).luck(playerEntity.getLuck());
            }

            LootParams lootContextParameterSet = builder.build(LootContextParamSets.ENTITY);
            lootTable.generateLoot(lootContextParameterSet, this.getLootTableSeed(), (stack) -> this.dropStack(stack));
        }
    }

    @Override
    public boolean isPersistent() {
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos == null)
            return super.isPersistent();
        if(getWorld().getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity structureManagerBlockEntity)
            return true;
        return super.isPersistent();
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
    }

    protected Faction getFaction(){
        NpcData data = retrieveNpcData();
        if(data == null)
            return null;
        ResourceLocation factionId = data.getFaction();
        if(factionId == null)
            return null;
        try {
            return FactionLookup.getFactionById(getWorld(), factionId);
        } catch (FactionIdentifierException e) {
            return null;
        }
    }

    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        float reach = 1.75f;
        try{
            Optional<Double> damageOpt = Optional.of(this.getAttributeValue(EntityAttributesME.ENTITY_INTERACTION_RANGE));
            reach = damageOpt.get().floatValue();
        } catch (Exception ignored){}

        if(this.getVehicle() != null || entity.getVehicle() != null) {
            reach += 0.5f;
        }

        return this.distanceTo(entity) <= reach;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.getWorld().sendEntityStatus(this, EntityEvent.PLAY_ATTACK_SOUND);
        boolean targetDamaged;
        float damage = 1.0f;
        try{
            damage = (float) this.getAttributeValue(Attributes.GENERIC_ATTACK_DAMAGE);
        } catch (Exception ignored){}
        ItemStack itemStack = this.getMainHandStack();
        DamageSource damageSource = this.getDamageSources().mobAttack(this);

        MobType group = target instanceof LivingEntity livingTarget ? livingTarget.getGroup() : MobType.DEFAULT;
        float enchantmentDamage = EnchantmentHelper.getAttackDamage(itemStack, group);
        float finalDamage = damage + enchantmentDamage;

        if(hasVehicle() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            mountEntity.tryAttack(target);
        }

        targetDamaged = target.damage(damageSource, finalDamage);
        if (targetDamaged) {
            LivingEntity livingEntity;
            int g = EnchantmentHelper.getKnockback(this);
            if (g > 0 && target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                livingEntity.takeKnockback(g * 0.5f, Mth.sin(this.getYaw() * ((float)Math.PI / 180)), -Mth.cos(this.getYaw() * ((float)Math.PI / 180)));
                this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                itemStack.getItem().postHit(itemStack, livingEntity, this);
            }
            EnchantmentHelper.onTargetDamaged(this, target);
            this.onAttacking(target);

        }
        return targetDamaged;
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        Entity entity = this.getControllingVehicle();
        if (entity instanceof PathfinderMob pathAwareEntity) {
            this.bodyYaw = pathAwareEntity.bodyYaw;
        }
    }

    @Override
    protected Component getDefaultName() {
        if(this.getNpcTypeIdentifier() == null) {
            return Component.translatable("npc_type."+ MiddleEarth.MOD_ID +".npc");
        }
        return Component.translatable(this.getNpcTypeIdentifier().toTranslationKey("npc_type"));
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return shouldTarget(this, target) && super.canTarget(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        // TODO : datadriven
        if(target instanceof SnailEntity || target instanceof Monster || target instanceof SnowTrollEntity || target instanceof Pouncer)
            return true;
        if(!npcEntity.isTeammate(target)){
            return true;
        }
        return false;
    }

    public int getTickAttackSpeedCooldown(){
        if(!this.getAttributes().hasAttribute(Attributes.GENERIC_ATTACK_SPEED))
            return 1;
        return (int)this.getAttributes().getValue(Attributes.GENERIC_ATTACK_SPEED);
    }

    @Override
    public boolean canBeLeashedBy(Player player) {
        return false;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        // No drop allowed
    }

    //public void releaseTicketFor(MemoryModuleType<GlobalPos> destination) {
    //    this.releaseTicketFor(MemoryModuleType.HOME);
    //}

    public float getWidthScale() {
        try{
            return (float) this.getAttributeValue(EntityAttributesME.WIDTH_SCALE);
        }
        catch (Exception ignored){
            return 1.0f;
        }
    }

    /*@Override
    public boolean isInAttackRange(LivingEntity entity) {
        CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
        if(runtimeData == null)
            return false;
        return runtimeData.getCombatArchetypeData().isInOptimalRange(this, entity.getBlockPos());
    }*/

    protected AbstractArrow createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    public boolean isAiming() {
        int i = this.getItemUseTime();
        return i > 0;
    }

    public void aim() {
        var currentItem = getActiveItem();
        if(currentItem.isEmpty()){
            clearActiveItem();
            setCurrentHand(InteractionHand.MAIN_HAND);
        }
    }

    public void stopAiming() {
        var currentItem = getActiveItem();
        if(currentItem.isEmpty()){
            this.clearActiveItem();
            return;
        }
        this.clearActiveItem();
    }

    public boolean isReadyToShoot() {
        return getMainHandStack() != null;
        //int i = this.getItemUseTime();
        //if (i >= 20) {
        //    return true;
        //}
        //return false;
    }

    private void shootAt(LivingEntity target, float pullProgress, float powerModifier) {
        if (!isReadyToShoot())
            return;

        ItemStack weapon = this.getMainHandStack();
        ItemStack projectileStack = this.getProjectileType(weapon);
        if(projectileStack.isEmpty()) projectileStack = new ItemStack(Items.ARROW, 1);

        AbstractArrow projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);

        double distanceX = target.getX() - this.getX();
        double distanceZ = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

        double distanceY = target.getBodyY(0.3F) - projectile.getY();

        float scale = 1.0f;
        float velocity = 1.6F * powerModifier;

        // Reduce the vanilla arc compensation for larger entities and faster arrows.
        double arcCompensation = horizontalDistance * (0.2F / (scale * powerModifier));

        if (this.getWorld() instanceof ServerLevel serverWorld) {
            projectile.setVelocity(
                    distanceX,
                    distanceY + arcCompensation,
                    distanceZ,
                    velocity,
                    (float) (14 - serverWorld.getDifficulty().getId() * 4)
            );
            serverWorld.spawnEntity(projectile);
        }

        this.playSound(
                SoundEvents.ENTITY_ARROW_SHOOT,
                1.0F,
                1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        stopAiming();
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    @Override
    public void shoot(LivingEntity target, ItemStack crossbow, Projectile projectile, float multiShotSpray) {
        this.shootAt(target, 1, 1.0f);
    }

    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    public void shootAt(LivingEntity livingEntity) {
        try{
            this.shootAt(livingEntity, BowItem.getPullProgress(getItemUseTime()), 2f);
        } catch (IllegalArgumentException e){
            this.shootAt(livingEntity, CustomLongbowWeaponItem.getPullProgressLongbow(getItemUseTime()), 3f);
        }
    }
    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, 1.25f);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CROSSBOW_CHARGING);
    }

    @Override
    public void setCharging(boolean charging) {
        this.dataTracker.set(CROSSBOW_CHARGING, charging);
    }

    @Override
    public void postShoot() {
        this.dataTracker.set(CROSSBOW_CHARGING, false);
    }

    @Override
    public boolean isTeammate(Entity other) {
        if(other instanceof NpcEntity npc){
            return !isHostileToward(npc);
        }
        else if(other instanceof Player player){
            return !isHostileTowardPlayer(player);
        }
        return !isHostileToward(other);
    }

    private boolean isHostileTowardPlayer(Player player) {
        if(player.getWorld().isClient || !player.canTakeDamage())
            return false;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
        if(playerData == null || playerData.getFaction() == null)
            return true;
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        if(ownFaction.isHostileToward(playerData.getFaction()))
            return true;
        return false;
    }

    private boolean isHostileToward(Entity other) {
        if(other instanceof SnailEntity || other instanceof Monster || other instanceof TrollEntity || other instanceof Pouncer)
            return true;

        if(!other.hasPassengers())
            return false;

        if(other.getControllingPassenger() instanceof NpcEntity npc && isHostileToward(npc)) {
            return true;
        }
        return false;
    }

    private boolean isHostileToward(NpcEntity npc) {
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        ResourceLocation otherNpcFaction = npc.getFactionIdentifier();
        if(otherNpcFaction == null)
            return true;
        if(ownFaction.isHostileToward(otherNpcFaction))
            return true;
        return false;
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    static {
        NPC_DATA = SynchedEntityData.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA);
        NPC_INITIALIZATION_DATA = SynchedEntityData.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_INITIALIZATION_DATA);
        NPC_TEXTURE_DATA = SynchedEntityData.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_TEXTURE_DATA);
        IS_FIGHTING = SynchedEntityData.registerData(NpcEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
