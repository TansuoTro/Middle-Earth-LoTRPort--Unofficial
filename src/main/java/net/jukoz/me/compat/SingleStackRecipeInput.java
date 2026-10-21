package net.jukoz.me.compat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

/**
 * 1.20.1 stand-in for 1.21.8's {@code net.minecraft.recipe.input.SingleStackRecipeInput}.
 *
 * <p>Implements {@link Inventory} so it satisfies {@code Recipe<SingleStackRecipeInput>}
 * generic bounds and {@code RecipeManager} matching. The donor {@code item()} accessor is
 * retained so recipe bodies keep their shape.
 */
public class SingleStackRecipeInput implements Inventory {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);

    public SingleStackRecipeInput(ItemStack stack) {
        this.stacks.set(0, stack);
    }

    /** Donor accessor for the single input stack. */
    public ItemStack item() {
        return stacks.get(0);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stacks.get(0).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot == 0 ? stacks.get(0) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack taken = stacks.get(0).split(amount);
        markDirty();
        return taken;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack removed = stacks.get(0);
        stacks.set(0, ItemStack.EMPTY);
        markDirty();
        return removed;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0) {
            stacks.set(0, stack);
            markDirty();
        }
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
        stacks.set(0, ItemStack.EMPTY);
        markDirty();
    }
}
