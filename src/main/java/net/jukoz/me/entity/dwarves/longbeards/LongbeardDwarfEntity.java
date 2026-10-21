package net.jukoz.me.entity.dwarves.longbeards;

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
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.world.entity.EntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
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

public class LongbeardDwarfEntity extends NpcEntity {
    public LongbeardDwarfEntity(EntityType<? extends NpcEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
        }else if (name.contains("elite")) {
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
                .add(Attributes.GENERIC_MAX_HEALTH, 22.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 24.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 26.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 28.0)
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
                2113882
        };
        int colorIndex = random.nextInt(1);

        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
        ItemStack leatherHelmet = new ItemStack((Item)(DyeableItem)Items.LEATHER_HELMET);
        item.setColor(leatherHelmet, colors[colorIndex]);

        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_CLOAK_HOOD));
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }

        float val1 = random.nextFloat();
        if(val1 >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.FUR_CLOAK));

        }

        float val2 = random.nextFloat();

        if(val2 >= 0.50f){
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_LEGGINGS));
        }

        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){

        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_CLOAK_HOOD));
        }

        float val1 = random.nextFloat();
        if(val1 >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_MAIL_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.CHAINMAIL_FUR_CLOAK));
        }

        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        }
    }

    private void knightEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_SCALE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_SCALE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_SCALE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_BATTLEAXE));
        } else if (val < 0.75f && val > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        } else if (val <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        }
    }

    private void veteranEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_PLATE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_PLATE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_PLATE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_BATTLEAXE));
        } else if (val < 0.75f && val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LONGBEARD_SHIELD));
        }
    }

    private void leaderEquipment(RandomSource random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_BOOTS));

        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LONGBEARD_BATTLEAXE));
    }

    public LongbeardDwarfVariant getVariant() {
        return LongbeardDwarfVariant.byId(this.getId());
    }

}
