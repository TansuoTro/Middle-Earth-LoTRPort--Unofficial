package net.jukoz.me.entity.orcs.misties;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.item.DyeableItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MistyGoblinEntity extends NpcEntity {
    public MistyGoblinEntity(EntityType<? extends NpcEntity> entityType, Level world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("snaga")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("warrior")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
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

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(RandomSource random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void militiaEquipment(RandomSource random){
        int[] colors = {
                0x5c544b,
                0x574a3b,
                0x484138,
                0x3b3630
        };
        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
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
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(RandomSource random){
        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.RUSTY_KETTLE_HAT));
        }
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.6f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SCIMITAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    public MistyGoblinVariant getVariant() {
        return MistyGoblinVariant.byId(this.getId());
    }
}
