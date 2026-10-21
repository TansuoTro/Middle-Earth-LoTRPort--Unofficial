package net.jukoz.me.gui.forge;

import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ForgeAlloyingScreenHandler extends AbstractContainerMenu {
    protected final Inventory inventory;
    protected final ContainerData propertyDelegate;
    protected final Level world;
    protected BlockPos pos;

    public ForgeAlloyingScreenHandler(int syncId, Inventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(6));
        this.pos = blockPos;
    }

    public ForgeAlloyingScreenHandler(int syncId, Inventory playerInventory, Inventory inventory, ContainerData delegate) {
        super(ModScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = delegate;
        this.world = playerInventory.player.level();
        this.pos = BlockPos.ORIGIN;
        int maxItemStack = 64;
        checkSize(inventory, 6);

        this.addSlot(new ForgeAlloyingFuelSlot(inventory, this, 0, 35, 61));
        this.addSlot(new ForgeSlot(inventory, 1, 8,  16, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 2, 26, 16, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 3, 44, 16, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 4, 62, 16, maxItemStack));
        this.addSlot(new ForgeOutputSlot(playerInventory.player, inventory, 5, 137, 57, true));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        inventory.onOpen(playerInventory.player);
        addProperties(delegate);
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public ItemStack quickMove(Player player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getItem();
            if(slot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size() - 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return stack;
    }

    @Override
    public boolean canUse(Player player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCooking() {
        return propertyDelegate.get(1) > 0;
    }

    public boolean heatingMode() {
        return propertyDelegate.get(3) == 0;
    }

    public float getScaledCooking() {
        int cooking = this.propertyDelegate.get(1);
        int maxCooking = this.propertyDelegate.get(2);
        if(maxCooking == 0) maxCooking = 200;

        return (float) cooking / maxCooking;
    }

    public float getScaledStoredLiquid() {
        int storedLiquid = this.propertyDelegate.get(4);
        return (float) storedLiquid / ForgeBlockEntity.MAX_STORAGE;
    }

    public int getStoredLiquid() {
        return this.propertyDelegate.get(4);
    }

    public int getCurrentMetal() {
        return this.propertyDelegate.get(5);
    }

    public int checkMaxOutput(){
        int liquid = propertyDelegate.get(4);
        if(liquid >= 432){
            return 5;
        }
        if(liquid >= 288){
            return 4;
        }
        if(liquid >= 144){
            return 2;
        }
        if(liquid >= 16){
            return 1;
        }
        return 0;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public float getScaledProgress() {
        int progress = this.propertyDelegate.get(0);

        return (float) progress / ForgeBlockEntity.MAX_PROGRESS;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 91 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 149));
        }
    }
}
