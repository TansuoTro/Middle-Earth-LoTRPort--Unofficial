package net.jukoz.me.gui.alloyfurnace;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;

public class AlloyFurnaceFuelSlot extends Slot {
    private final AlloyFurnaceScreenHandler handler;

    public AlloyFurnaceFuelSlot(Inventory inventory, AlloyFurnaceScreenHandler handler, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }
}
