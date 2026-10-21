package net.jukoz.me.entity.elves.galadhrim;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GaladhrimElfEntity extends NpcEntity{

    public GaladhrimElfEntity(EntityType<? extends PathAwareEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(ModWeaponItems.LORIEN_BOW);
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        RandomSource random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    public static DefaultAttributeContainer.Builder setSoldierAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 25.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 27.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 29.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 31.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(RandomSource random, LocalDifficulty localDifficulty) {
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
                0x767b85,
                0x747983,
                0x61626e,
                0x505258
        };
        DyeableItem item = (DyeableItem)ModEquipmentItems.TUNIC_CLOAK;
        ItemStack tunicCloak = new ItemStack(ModEquipmentItems.TUNIC_CLOAK);
        ItemStack hood = new ItemStack(ModEquipmentItems.CLOAK_HOOD);
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        item.setColor(hood, colors[0]);
        item.setColor(tunicCloak, colors[0]);
        item.setColor(leatherHelmet, colors[1]);
        item.setColor(leatherChestplate, colors[1]);
        item.setColor(leatherLeggings, colors[2]);
        item.setColor(leatherBoots, colors[3]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, hood);
            equipStack(EquipmentSlot.CHEST, tunicCloak);
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
            equipStack(EquipmentSlot.CHEST, leatherChestplate);
        }
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float rand = random.nextFloat();
        if(rand >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (rand > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_BATTLESTAFF));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){
        
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_MAIL_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_MAIL_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_MAIL_BOOTS));

        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_BOW));
        } else if (val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        }
    }

    private void knightEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_SCALE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_SCALE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_SCALE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else if (val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        }
    }

    private void veteranEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_PLATE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_PLATE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_PLATE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_PLATE_BOOTS));

        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_LONGSWORD));
        }
    }

    private void leaderEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_COMMANDER_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_COMMANDER_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_COMMANDER_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_COMMANDER_BOOTS));

        if (random.nextFloat() < 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_PIKE));
        }
    }
    public GaladhrimElfVariant getVariant() {
        return GaladhrimElfVariant.byId(this.getId());
    }
}
