package net.jukoz.me.entity.ai.brain.task.npc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.jukoz.me.entity.npcs.NpcEntity;
import java.util.function.Predicate;

public class NpcRangedAttackTask {
    public static <T extends NpcEntity> OneShot<T> create(int cooldown) {
        return create((target) -> true, cooldown);
    }

    public static <T extends NpcEntity> OneShot<T> create(Predicate<T> targetPredicate, int cooldown) {
        return BehaviorBuilder.task((context) -> context.group(
                context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                context.absent(MemoryModuleType.ATTACK_COOLING_DOWN),
                context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS)).apply(context,
                (lookTarget, attackTarget, attackCoolingDown, visibleMobs) -> (world, entity, time) -> {
            LivingEntity targetEntity = context.getValue(attackTarget);
            entity.lookAtEntity(targetEntity, 30.0F, 30.0F);

            boolean targetPredicateResult = targetPredicate.test(entity);
            boolean isHoldingRangedWeapon = isHoldingUsableRangedWeapon(entity);
            boolean isInAttackRange = entity.isInAttackRange(targetEntity);
            boolean canSeeIt = (context.getValue(visibleMobs)).contains(targetEntity);

            if (targetPredicateResult && isHoldingRangedWeapon && isInAttackRange && canSeeIt) {
                lookTarget.remember(new EntityTracker(targetEntity, true));
                attackCoolingDown.remember(true, cooldown);

                entity.aim();
                if(entity.isReadyToShoot()){
                    entity.shootAt(targetEntity);
                    entity.getBrain().erase(MemoryModuleType.ATTACK_TARGET);
                    entity.stopAiming();
                }
                return true;
            } else {
                boolean canForget = false;

                if(targetEntity.isDead())
                    canForget = true;
                else {
                    float distanceToEntity = entity.distanceTo(targetEntity);
                    if(distanceToEntity > 50 )
                        canForget = true;
                }

                if(canForget)
                    entity.getBrain().erase(MemoryModuleType.ATTACK_TARGET);

                return false;
            }
        }));
    }

    private static boolean isHoldingUsableRangedWeapon(Mob mob) {
        return mob.isHolding((stack) -> {
            Item item = stack.getItem();
            return item instanceof ProjectileWeaponItem && mob.canUseRangedWeapon((ProjectileWeaponItem)item);
        });
    }
}
