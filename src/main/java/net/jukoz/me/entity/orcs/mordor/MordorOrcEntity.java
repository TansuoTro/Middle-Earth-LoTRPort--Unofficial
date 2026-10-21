package net.jukoz.me.entity.orcs.mordor;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.world.entity.EntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class MordorOrcEntity extends NpcEntity {
    public MordorOrcEntity(EntityType<? extends NpcEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("snaga")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
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
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(Attributes.GENERIC_MAX_HEALTH, 20.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void militiaEquipment(RandomSource random){
        int[] colors = {
                0x544949,
                0x563a3a,
                0x463a3a,
                0x3b3634
        };
        DyeableLeatherItem item = (DyeableLeatherItem)ModEquipmentItems.GAMBESON;
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        item.setColor(leatherHelmet, colors[0]);
        item.setColor(leatherChestplate, colors[1]);
        item.setColor(leatherLeggings, colors[2]);
        item.setColor(leatherBoots, colors[3]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.RUSTY_KETTLE_HAT));
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }
        equipStack(EquipmentSlot.CHEST, leatherChestplate);
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float val3 = random.nextFloat();
        if(val3 >= 0.7f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.RUSTY_KETTLE_HAT));
        }
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_BOOTS));

        float val3 = random.nextFloat();
        if(val3 >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_SCIMITAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    public MordorOrcVariant getVariant() {
        return MordorOrcVariant.byId(this.getId());
    }
}
