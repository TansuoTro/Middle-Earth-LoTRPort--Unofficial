package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * Forge compat shim for Fabric's {@code FabricBlockLootTableProvider}: a block-loot sub-provider
 * whose {@code generate()} is the port's entry point, with Fabric's helper spellings forwarded
 * onto vanilla's {@link BlockLootSubProvider}.
 */
public abstract class FabricBlockLootTableProvider extends BlockLootSubProvider {
    protected FabricBlockLootTableProvider() {
        super();
    }

    public FabricBlockLootTableProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output) {
        this();
    }

    /** Fabric's entry point; implements vanilla's abstract block-loot generation. */
    @Override
    public abstract void generate();

    // ---- Fabric helper spellings -------------------------------------------------------

    public void dropSelf(Block block) {
        super.dropSelf(block);
    }

    public void add(Block block, java.util.function.Function<Block, net.minecraft.world.level.storage.loot.LootTable.Builder> factory) {
        super.add(block, factory);
    }

    public void slabDrops(Block block) {
        super.add(block, b -> createSlabItemTable(b));
    }

    public void verticalSlabDrops(Block block) {
        super.add(block, b -> createSlabItemTable(b));
    }

    public void doorDrops(Block block) {
        super.add(block, b -> createDoorTable(b));
    }

    public void largeDoorDrops(Block block) {
        doorDrops(block);
    }

    public void dropsWithSilkTouch(Block block, ItemLike drop) {
        super.add(block, b -> createSingleItemTableWithSilkTouch(b, drop));
    }

    public void oreDrops(Block block, Item drop) {
        super.add(block, b -> createOreDrop(b));
    }

    public void leavesDrops(Block block, Block sapling, float chance) {
        super.add(block, b -> createLeavesDrops(block, sapling, chance));
    }

    public void cropDrops(Block block, Item grown, Item seed) {
        super.add(block, b -> createCropDrops(block, grown, seed));
    }

    public void createGrassDrops(Block block, Item seed) {
        super.add(block, b -> createGrassDrops(seed));
    }

    public void dropNothing(Block block) {
    }
}
