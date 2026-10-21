package net.jukoz.me.entity.crab;

import net.jukoz.me.entity.ModEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemConvertible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CrabEntity extends WaterCreatureEntity {

    private static final Ingredient BREEDING_INGREDIENT
            = Ingredient.ofItems(Items.SEA_PICKLE, Items.SALMON, Items.COD);

    public CrabEntity(EntityType<? extends WaterCreatureEntity> entityType, Level world) {
        super(entityType, world);
        new AquaticMoveControl(this, 85, 10, 0.2F, 0.1F, true);
    }


    protected void initGoals() {
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new TemptGoal(this, 1.3, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, Player.class, 6.0F));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.3, true));
        this.goalSelector.add(7, new FleeEntityGoal<>(this, DolphinEntity.class, 8.0F, 1.0, 1.4));
        this.targetSelector.add(1, (new RevengeGoal(this)));
    }

    public static DefaultAttributeContainer.Builder createCrabAttributes() {
        return Mob.createMobAttributes().add(Attributes.GENERIC_MAX_HEALTH, 10.0).add(Attributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 5f);
    }

    @Override
    public int getAir() {
        return 1000;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.2F, 1.8F);
    }

    @Nullable
    public CrabEntity createChild(ServerLevel serverWorld, PassiveEntity passiveEntity) {
        return (CrabEntity) ModEntities.CRAB.create(serverWorld);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public CrabVariant getVariant() {
        return CrabVariant.byId(this.getId());
    }


    public boolean canBreatheInWater() {
        return true;
    }

    public boolean isPushedByFluids() {
        return false;
    }
}
