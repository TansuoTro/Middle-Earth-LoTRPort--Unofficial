package net.jukoz.me.block.special.sack;

import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.gui.sack.SackScreenHandler;
import org.jetbrains.annotations.Nullable;
import java.util.stream.IntStream;

public class SackBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;

    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SACK, pos, state);
        this.inventory = NonNullList.ofSize(size(), ItemStack.EMPTY);
        this.setInvStackList(NonNullList.ofSize(this.size(), ItemStack.EMPTY));
        this.stateManager = new ContainerOpenersCounter() {
            protected void onContainerOpen(Level world, BlockPos pos, BlockState state) {
                SackBlockEntity.this.setOpen(state, true);
            }

            protected void onContainerClose(Level world, BlockPos pos, BlockState state) {
                SackBlockEntity.this.setOpen(state, false);
            }

            protected void onViewerCountUpdate(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isPlayerViewing(Player player) {
                if (player.currentScreenHandler instanceof ChestMenu) {
                    Inventory inventory = ((ChestMenu)player.currentScreenHandler).getInventory();
                    return inventory == SackBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    protected AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory) {
        return new SackScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("sack").toTranslationKey("screen"));
    }

    @Override
    protected Component getContainerName() {
        return Component.translatable(MiddleEarth.of("sack").toTranslationKey("screen"));
    }

    public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        ReadView view = new ReadView(nbt);
        this.readInventoryNbt(view);
    }

    protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        WriteView view = new WriteView(nbt);
        if (!this.serializeLootTable(view.nbt())) {
            ContainerHelper.writeNbt(view.nbt(), this.inventory, false);
        }

    }

    public void readInventoryNbt(ReadView readView) {
        this.inventory = NonNullList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(readView.nbt())) {
            ContainerHelper.readNbt(readView.nbt(), this.inventory);
        }
    }

    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        // Keep empty
    }

    public void onOpen(Player player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void onClose(Player player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    protected NonNullList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    protected void setHeldStacks(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), (BlockState)state.with(BarrelBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    @Override
    protected NonNullList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(NonNullList<ItemStack> list) {
        this.inventory = list;
    }

    public void generateLoot(Player player) {
        this.checkLootInteraction(player);
    }
}
