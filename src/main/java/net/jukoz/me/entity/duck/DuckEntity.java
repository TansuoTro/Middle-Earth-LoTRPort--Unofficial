package net.jukoz.me.entity.duck;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;

public class DuckEntity extends Animal {
    public static final Ingredient SEEDS_BREEDING_INGREDIENT
            = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS,
            ModResourceItems.BELL_PEPPER_SEEDS, ModResourceItems.CUCUMBER_SEEDS, ModResourceItems.FLAX_SEEDS,
            ModResourceItems.LETTUCE_SEEDS, ModResourceItems.PIPEWEED_SEEDS, ModResourceItems.TOMATO_SEEDS);
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0F;
    public int eggLayTime;

    public DuckEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new PanicGoal(this, 1.15));
        this.goalSelector.add(3, new BreedGoal(this, 1.0));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, SEEDS_BREEDING_INGREDIENT, false));

        this.goalSelector.add(5, new FollowParentGoal(this, 1.05));
        this.goalSelector.add(6, new RandomStrollGoal(this, 1.0F));

        this.goalSelector.add(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.add(8, new RandomLookAroundGoal(this));

        this.goalSelector.add(9, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 0.9, 1.2));
    }

    public static AttributeSupplier.Builder createDuckAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    public void tickMovement() {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (this.isOnGround() ? -1.0F : 4.0F) * 0.3F;
        this.maxWingDeviation = Mth.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.isOnGround() && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed *= 0.9F;
        Vec3 vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 2.0F);
    }

    @Nullable
    public DuckEntity createChild(ServerLevel serverWorld, AgeableMob passiveEntity) {
        return (DuckEntity) ModEntities.DUCK.create(serverWorld);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return SEEDS_BREEDING_INGREDIENT.test(stack);
    }

    public DuckVariant getVariant() {
        return DuckVariant.byId(this.getId());
    }

    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushedByFluids() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
}
