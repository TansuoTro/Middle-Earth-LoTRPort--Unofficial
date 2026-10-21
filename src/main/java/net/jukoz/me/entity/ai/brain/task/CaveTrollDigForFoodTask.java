package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.collection.Pool;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.item.WeaponItemsME;

import java.util.List;

public class CaveTrollDigForFoodTask extends MultiTickTask<CaveTrollEntity> {
    public CaveTrollDigForFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
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
