package net.jukoz.me.utils;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
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
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModFoodItems.RAW_HORSE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());


                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
