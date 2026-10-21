package net.jukoz.me.gui.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;

public class ForgeOutputSlot extends Slot {
    private final Player player;
    private int amount;

    private final boolean isEnabled;

    public ForgeOutputSlot(Player player, Inventory inventory, int index, int x, int y, boolean isEnabled) {
        super(inventory, index, x, y);
        this.player = player;
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getItem().getCount());
        }
        return super.takeStack(amount);
    }

    @Override
    public void onTakeItem(Player player, ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
    }

    @Override
    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean canTakeItems(Player playerEntity) {
        return isEnabled;
    }
}
