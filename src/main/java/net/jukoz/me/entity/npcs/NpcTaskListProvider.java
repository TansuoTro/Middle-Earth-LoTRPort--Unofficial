package net.jukoz.me.entity.npcs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.jukoz.me.entity.EntitiesME;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.TradeWithVillager;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.util.Tuple;

public class NpcTaskListProvider {
    private static final float WALKING_SPEED = 0.4F;

    public static ImmutableList<Tuple<Integer, ? extends BehaviorControl<? super NpcEntity>>> createIdleTasks(float speed) {
        return ImmutableList.of(
                Tuple.of(2, new RunOne(
                        ImmutableList.of(
                                Tuple.of(InteractWith.create(EntitiesME.NPC, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                
                                Tuple.of(SetWalkTargetFromLookTarget.create(speed, 2), 1),
                                Tuple.of(new JumpOnBed(speed), 1),
                                Tuple.of(new DoNothing(30, 60), 1)))),
                Tuple.of(3, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Tuple.of(3, new GateBehavior(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), GateBehavior.Order.ORDERED, GateBehavior.RunMode.RUN_ONE,
                        ImmutableList.of(Tuple.of(new TradeWithVillager(), 1)))),
                Tuple.of(99, UpdateActivityFromSchedule.create()));
    }

    private static Tuple<Integer, BehaviorControl<LivingEntity>> createFreeFollowTask() {
        return Tuple.of(5, new RunOne(ImmutableList.of(
                Tuple.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), 8),
                Tuple.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2),
                Tuple.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2),
                Tuple.of(SetEntityLookTarget.create(MobCategory.CREATURE, 8.0F), 1),
                Tuple.of(SetEntityLookTarget.create(MobCategory.WATER_CREATURE, 8.0F), 1),
                Tuple.of(SetEntityLookTarget.create(MobCategory.AXOLOTLS, 8.0F), 1),
                Tuple.of(SetEntityLookTarget.create(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                Tuple.of(SetEntityLookTarget.create(MobCategory.WATER_AMBIENT, 8.0F), 1),
                Tuple.of(SetEntityLookTarget.create(MobCategory.MONSTER, 8.0F), 1),
                Tuple.of(new DoNothing(30, 60), 2))));
    }
}
