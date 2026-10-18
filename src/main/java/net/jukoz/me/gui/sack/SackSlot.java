package net.jukoz.me.gui.sack;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.screen.slot.Slot;
import net.jukoz.me.item.DecorativeItemsME;

public class SackSlot extends Slot {
    public SackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(stack.isOf(DecorativeItemsME.SACK) || (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock)) return false;
        return super.canInsert(stack);
    }
}
