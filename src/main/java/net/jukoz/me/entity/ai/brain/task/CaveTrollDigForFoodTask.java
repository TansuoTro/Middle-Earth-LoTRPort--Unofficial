package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.item.WeaponItemsME;
import java.util.List;

public class CaveTrollDigForFoodTask extends Behavior<CaveTrollEntity> {
    public CaveTrollDigForFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),
                100
        );
    }

    @Override
    protected void run(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setScavenging(true);
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setScavenging(false);
        entity.getBrain().remember(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 2400 + entity.getRandom().nextInt(1200));
        entity.getBrain().remember(MemoryModulesME.ACTION_TIMEOUT, 200);

        List<ItemStack> items = entity.scavengeLootTable.generateLoot(entity.lootWorldContext);

        if(!items.isEmpty()) {
            ItemStack itemStack = items.get(0);
            entity.equipStack(EquipmentSlot.MAINHAND, itemStack);
        }

    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        return hasRequiredMemoryState(entity);
    }
}
