package net.jukoz.me.block.special.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 1.20.1 recipe inputs are {@link Inventory} instances, not 1.21.2's {@code RecipeInput}.
 */
public class MultipleStackRecipeInput implements Inventory {
    private final List<ItemStack> items;

    public MultipleStackRecipeInput(List<ItemStack> items) {
        this.items = items;
    }

    public List<ItemStack> items() {
        return items;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.getStack(slot);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= size()) {
            throw new IllegalArgumentException("No item for index " + slot);
        }
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return net.minecraft.inventory.Inventories.splitStack(this.items, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return net.minecraft.inventory.Inventories.removeStack(this.items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.items.set(slot, stack);
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(Player player) {
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
