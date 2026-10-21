package net.jukoz.me.entity.goose;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.duck.DuckEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.TryFindLand;
import net.minecraft.world.entity.ai.behavior.TryFindLandNearWater;
import net.minecraft.util.Tuple;

public class GooseBrain {
    private static final UniformInt WALK_TOWARD_ADULT_RANGE = UniformInt.create(5, 16);
    private static final UniformInt WALKING_SPEED = UniformInt.create(5, 16);

    public GooseBrain() {
    }

    protected static void initialize(GooseEntity goose, RandomSource random) {
    }

    protected static Brain<?> create(GooseEntity goose, Brain<GooseEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(goose, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GooseEntity> brain) {

        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new Swim(1.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.GAZE_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Tuple.of(0, SetEntityLookTargetSometimes.follow(EntityType.PLAYER, 6.0F, UniformInt.create(30, 60))),
                Tuple.of(0, new AnimalMakeLove(ModEntities.GOOSE, 1.0F)),
                Tuple.of(1, new FollowTemptation((goose) -> {return 1.25F;})),
                Tuple.of(2, BabyFollowAdult.create(WALKING_SPEED, 1.25F)),
                Tuple.of(3, TryFindLandNearWater.create(6, 1.0F)),
                Tuple.of(4, TryFindLand.create(8, 1.0F)),
                Tuple.of(5, new RunOne(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Tuple.of(RandomStroll.create(1.0F), 1),
                        Tuple.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 1))))));
    }

    private static void addFightActivities(GooseEntity goose, Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.FIGHT,10, ImmutableList.of(
                StopAttackingIfTargetInvalid.create((target) -> true),
                SetEntityLookTarget.create((entity) -> {
                    if(!goose.isBaby()){
                        return isTargeting(goose, entity);
                    }
                    return false;
                }, (float)goose.getAttributeValue(Attributes.GENERIC_FOLLOW_RANGE)),
                MeleeAttack.create(18)),
                MemoryModuleType.ATTACK_TARGET);
    }

    public static boolean isTargeting(GooseEntity goose, LivingEntity entity) {
        return goose.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).filter((entityx) -> {
            return entityx == entity;
        }).isPresent();
    }

    public static void updateActivities(GooseEntity goose) {
        goose.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.CORE, Activity.FIGHT));
    }

    public static Ingredient getTemptItems() {
        return DuckEntity.SEEDS_BREEDING_INGREDIENT;
    }
}
