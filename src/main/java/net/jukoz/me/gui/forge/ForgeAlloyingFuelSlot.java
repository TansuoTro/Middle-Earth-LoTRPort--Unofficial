package net.jukoz.me.gui.forge;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class ForgeAlloyingFuelSlot extends Slot {
    private final ForgeAlloyingScreenHandler handler;

    public ForgeAlloyingFuelSlot(Inventory inventory, ForgeAlloyingScreenHandler handler, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return net.fabricmc.fabric.api.registry.FuelRegistry.INSTANCE.get(stack.getItem()) != null;
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }
}
