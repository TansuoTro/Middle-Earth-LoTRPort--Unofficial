package net.jukoz.me.block.special.crockpot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.BlockPos;
import net.jukoz.me.gui.ModScreenHandlers;

public class CrockpotScreenHandler extends AbstractContainerMenu {
    final Inventory inventory;
    private final ContainerData propertyDelegate;
    protected BlockPos pos;

    public CrockpotScreenHandler(int syncId, Inventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleContainer(5), new SimpleContainerData(1));
        this.pos = blockPos;
    }

    public CrockpotScreenHandler(int syncId, Inventory playerInventory,
                                 Inventory inventory, ContainerData propertyDelegate) {
        super(ModScreenHandlers.CROCKPOT_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.pos = BlockPos.ORIGIN;

        checkSize(inventory, 5);

        this.addSlot(new Slot(inventory, 0, 48, 44));
        this.addSlot(new Slot(inventory, 1, 66, 44));
        this.addSlot(new Slot(inventory, 2, 48, 26));
        this.addSlot(new Slot(inventory, 3, 66, 26));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        inventory.onOpen(playerInventory.player);
        addProperties(propertyDelegate);
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    @Override
    public ItemStack quickMove(Player player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == CrockpotBlockEntity.OUTPUT_SLOT) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(Player player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
