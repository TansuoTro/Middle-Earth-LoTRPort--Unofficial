package net.jukoz.me.entity.humans.gondor;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class GondorHumanEntity extends NpcEntity{

    public GondorHumanEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(ModWeaponItems.GONDOR_BOW);
        }else if (name.contains("knight")) {
            this.setRank(RANK.KNIGHT);
        }else if (name.contains("veteran")) {
            this.setRank(RANK.VETERAN);
        }else if (name.contains("leader")) {
            this.setRank(RANK.LEADER);
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

    public static AttributeSupplier.Builder setSoldierAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }
    public static AttributeSupplier.Builder setKnightAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.MAX_HEALTH, 22.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 2.5);
    }
    public static AttributeSupplier.Builder setVeteranAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0);
    }
    public static AttributeSupplier.Builder setLeaderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.MAX_HEALTH, 26.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 4;
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, NazgulEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
            case KNIGHT -> knightEquipment(random);
            case VETERAN -> veteranEquipment(random);
            case LEADER -> leaderEquipment(random);
        }
    }

    private void militiaEquipment(RandomSource random){
        int[] colors = {
                0x57565f,
                0x4d4a57,
                0x444448,
                0x2f2d31
        };
        DyeableLeatherItem item = (DyeableLeatherItem)ModEquipmentItems.GAMBESON;
        ItemStack gambeson = new ItemStack(ModEquipmentItems.GAMBESON);
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        item.setColor(gambeson, colors[1]);
        item.setColor(leatherHelmet, colors[0]);
        item.setColor(leatherChestplate, colors[1]);
        item.setColor(leatherLeggings, colors[2]);
        item.setColor(leatherBoots, colors[3]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.CHEST, gambeson);
        } else  {
            equipStack(EquipmentSlot.CHEST, leatherChestplate);
        }
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float val3 = random.nextFloat();
        if(val3 >= 0.7f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 >= 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){
        int[] colors = {
                0x4d4a57
        };
        int colorIndex = random.nextInt(1);
        DyeableLeatherItem item = (DyeableLeatherItem)ModEquipmentItems.GAMBESON;
        ItemStack stack = new ItemStack((Item)item);
        item.setColor(stack, colors[colorIndex]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        }
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, stack);
        }

        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_BOOTS));

        float val3 = random.nextFloat();
        if(val3 >= 0.6f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BOW));
        } else if (val3 > 0.30f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void knightEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BATTLEAXE));
        } else if (val > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void veteranEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BATTLEAXE));
        } else if (val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void leaderEquipment(RandomSource random){
        float randomVal =random.nextFloat();
        if(randomVal < 0.50f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS));

            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS));

            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
        }
    }

    public GondorHumanVariant getVariant() {
        return GondorHumanVariant.byId(this.getId());
    }

}
