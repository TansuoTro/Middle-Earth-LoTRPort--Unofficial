package net.jukoz.me.utils;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.resources.ResourceLocation;

public class LootModifiers {

    private static final ResourceLocation HORSE_LOOT_TABLE_IDENTIFIER = new ResourceLocation("minecraft", "entities/horse");
    public static final ResourceKey<Registry<LootTable>> LOOT_TABLE_REGISTRY =
            ResourceKey.ofRegistry(new ResourceLocation("minecraft", "loot_table"));
    public static final ResourceKey<LootTable> FISHING_LOOT_TABLE =
            ResourceKey.of(LOOT_TABLE_REGISTRY, new ResourceLocation("me", "gameplay/fishing"));

    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(HORSE_LOOT_TABLE_IDENTIFIER.equals(id)){
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantValue.create(1))
                        .conditionally(LootItemRandomChanceCondition.builder(1f))
                        .with(LootItem.builder(ModFoodItems.RAW_HORSE))
                        .apply(SetItemCountFunction.builder(UniformGenerator.create(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
