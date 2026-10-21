package net.jukoz.me.entity.hobbits.shire;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.items.PebbleItem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class ShireHobbitEntity extends NpcEntity {
    private static float FLEE_DISTANCE = 8f;
    private static float FLEE_SPEED_MIN = 0.8f;
    private static float FLEE_SPEED_MAX = 1.2f;

    public ShireHobbitEntity(EntityType<? extends NpcEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("civilian")){
            this.setRank(RANK.CIVILIAN);
        }else if(name.contains("bounder")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        }else if (name.contains("shirriff")) {
            this.setRank(RANK.SOLDIER);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        RandomSource random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new FloatGoal(this));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, TrollEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, MordorBlackUrukEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, MistyHobgoblinEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, MordorOrcEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, MistyGoblinEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, BanditHumanEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, MirkwoodSpiderEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new AvoidEntityGoal<>(this, NazgulEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new RangedAttackGoal(this, 1.0, 12, 24, 20.0f));
        this.goalSelector.add(++i, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(++i, new RandomLookAroundGoal(this));
        i = 1;
        this.targetSelector.add(++i, new HurtByTargetGoal(this).setGroupRevenge());
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, NazgulEntity.class, true));
    }

    public static AttributeSupplier.Builder setSoldierAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }
    public static AttributeSupplier.Builder setKnightAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.MAX_HEALTH, 22.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.ATTACK_DAMAGE, 1.5);
    }

    @Override
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty) {
        super.initEquipment(random, localDifficulty);

        switch (this.getRank()){
            case CIVILIAN -> civilianEquipement(random);
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void civilianEquipement(RandomSource random){
        float randomVal = random.nextFloat();
        if(randomVal < 0.03f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
        } else if (randomVal < 0.15f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModResourceItems.PEBBLE));
        } else if(randomVal < 0.20f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.APPLE));
        }  else if(randomVal < 0.25f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CARROT));
        }

        randomVal = random.nextFloat();
        if (randomVal < 0.15f) {
            int[] colors = {
                    0x375c23,
                    0x535c23,
                    0x665723
            };
            int colorIndex = random.nextInt(3);
            DyeableLeatherItem itemHood = (DyeableLeatherItem)ModEquipmentItems.CLOAK_HOOD;
            DyeableLeatherItem item = (DyeableLeatherItem)ModEquipmentItems.CLOAK;
            ItemStack stack = new ItemStack((Item)item);
            item.setColor(stack, colors[colorIndex]);

            if(randomVal < 0.07f) {
                ItemStack stackHood = new ItemStack((Item)itemHood);
                itemHood.setColor(stackHood, colors[colorIndex]);
                equipStack(EquipmentSlot.HEAD, stackHood);
            }
            equipStack(EquipmentSlot.CHEST, stack);
        }
    }

    private void militiaEquipment(RandomSource random){
        if(random.nextFloat() < 0.5f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        }
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GAMBESON));
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.IRON_DAGGER));
    }

    private void soldierEquipment(RandomSource random){
        float  randomVal = random.nextFloat();
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        if(randomVal < 0.50f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_BROWN));
        } else {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_GREEN));
        }
    }

    public ShireHobbitVariant getVariant() {
        return ShireHobbitVariant.byId(this.getId());
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        Item item = ModResourceItems.PEBBLE;
        ItemStack itemStack = new ItemStack(item);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - this.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        PebbleEntity pebbleEntity = new PebbleEntity(getWorld(), this, PebbleItem.DAMAGE);
        pebbleEntity.setItem(itemStack);
        pebbleEntity.setVelocity(d, e + g * (double)0.2f, f, 0.8f, 14 - getWorld().getDifficulty().getId() * 4);
        getWorld().spawnEntity(pebbleEntity);
        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.05f + 0.8f));
    }
}
