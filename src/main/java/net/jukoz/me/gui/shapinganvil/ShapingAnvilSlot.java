package net.jukoz.me.gui.shapinganvil;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ShapingAnvilSlot extends Slot {
    private final int MAX_ITEM_COUNT;
    public ShapingAnvilSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, 1);
    }

    public ShapingAnvilSlot(Inventory inventory, int index, int x, int y, int itemCount) {
        super(inventory, index, x, y);
        MAX_ITEM_COUNT = itemCount;
    }

    @Override
    public int getMaxItemCount() {
        return MAX_ITEM_COUNT;
    }
}
