package net.jukoz.me.gui.sack;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public class SackScreenHandler extends AbstractContainerMenu {
	private static final int CONTAINER_SIZE = 9;
	private static final int INVENTORY_START = 9;
	private static final int INVENTORY_END = 36;
	private static final int HOTBAR_START = 36;
	private static final int HOTBAR_END = 45;
	private final Inventory inventory;

	public SackScreenHandler(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, new SimpleContainer(9));
	}

	public SackScreenHandler(int syncId, Inventory playerInventory, Inventory inventory) {
		super(MenuType.GENERIC_3X3, syncId);
		checkSize(inventory, CONTAINER_SIZE);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
		this.add3x3Slots(inventory, 62, 17);
		this.addPlayerInventory(playerInventory);
		this.addPlayerHotbar(playerInventory);
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

	protected void add3x3Slots(Inventory inventory, int x, int y) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				int k = j + i * 3;
				this.addSlot(new SackSlot(inventory, k, x + j * 18, y + i * 18));
			}
		}
	}

	public boolean canUse(Player player) {
		return this.inventory.canPlayerUse(player);
	}

	public ItemStack quickMove(Player player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = (Slot)this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getItem();
			itemStack = itemStack2.copy();
			if (slot < 9) {
				if (!this.insertItem(itemStack2, INVENTORY_START, HOTBAR_END, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, INVENTORY_START, false)) {
				return ItemStack.EMPTY;
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

	public void onClosed(Player player) {
		super.onClosed(player);
		this.inventory.onClose(player);
	}
}
