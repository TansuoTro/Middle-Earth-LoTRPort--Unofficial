package net.jukoz.me.entity.humans.rohan;

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
import net.minecraft.world.entity.EntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RohanHumanEntity extends NpcEntity {
    public RohanHumanEntity(EntityType<? extends PathAwareEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(ModWeaponItems.ROHIRRIC_BOW);
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
                .add(Attributes.GENERIC_MAX_HEALTH, 20.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 22.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 24.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 26.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
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
                0x446635,
                0x2b5e2c,
                0x5c5e50,
                0x6b564c
        };
        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
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

        if(random.nextFloat() >= 0.20f){
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
        } else if (val3 < 0.7f && val3 > 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else if (val3 <= 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){
        int[] colors = {
                0x2b5e2c
        };
        int colorIndex = random.nextInt(1);
        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
        ItemStack stack = new ItemStack((Item)item);
        item.setColor(stack, colors[colorIndex]);

        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.ROHIRRIC_MAIL_HELMET));

        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.ROHIRRIC_MAIL_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, stack);
        }

        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.ROHIRRIC_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.ROHIRRIC_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.6f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_BOW));
        } else if (val3 >= 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
        }
    }

    private void knightEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.ROHIRRIC_SCALE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.ROHIRRIC_SCALE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.ROHIRRIC_SCALE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.ROHIRRIC_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_BATTLEAXE));
        } else if (val > 0.45f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
        }
    }

    private void veteranEquipment(RandomSource random){
        if(random.nextFloat() >= 0.10f) {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_BOOTS));
        } else {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EORLING_MARSHALL_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EORLING_MARSHALL_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EORLING_MARSHALL_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EORLING_MARSHALL_BOOTS));
        }
        float val1 = random.nextFloat();
        if(val1 >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_BATTLEAXE));
        } else if (val1 >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
        }
    }

    private void leaderEquipment(RandomSource random) {
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.HORSE_LORD_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.HORSE_LORD_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.HORSE_LORD_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.HORSE_LORD_BOOTS));

        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.ROHIRRIC_LONGSWORD));
        equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.ROHIRRIC_SHIELD));
    }

    public RohanHumanVariant getVariant() {
        return RohanHumanVariant.byId(this.getId());
    }
}
