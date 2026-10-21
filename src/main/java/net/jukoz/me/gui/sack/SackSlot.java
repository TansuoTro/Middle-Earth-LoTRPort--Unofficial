package net.jukoz.me.gui.sack;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.inventory.Slot;
import net.jukoz.me.item.DecorativeItemsME;

public class SackSlot extends Slot {
    public SackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(stack.is(DecorativeItemsME.SACK) || (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock)) return false;
        return super.canInsert(stack);
    }
}
