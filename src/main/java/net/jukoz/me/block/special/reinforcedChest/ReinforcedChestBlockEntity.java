package net.jukoz.me.block.special.reinforcedChest;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ReinforcedChestBlockEntity extends ChestBlockEntity {

    private final ContainerOpenersCounter stateManager = new ContainerOpenersCounter(){
        @Override
        protected void onContainerOpen(Level world, BlockPos pos, BlockState state) {
            ReinforcedChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_OPEN);
        }

        @Override
        protected void onContainerClose(Level world, BlockPos pos, BlockState state) {
            ReinforcedChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_CLOSE);
        }

        @Override
        protected void onViewerCountUpdate(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            ReinforcedChestBlockEntity.this.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
        }

        @Override
        protected boolean isPlayerViewing(Player player) {
            if (player.currentScreenHandler instanceof ChestMenu) {
                Inventory inventory = ((ChestMenu)player.currentScreenHandler).getInventory();
                return inventory == ReinforcedChestBlockEntity.this || inventory instanceof CompoundContainer && ((CompoundContainer)inventory).isPart(ReinforcedChestBlockEntity.this);
            }
            return false;
        }
    };

    public ReinforcedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REINFORCED_CHEST, pos, state);

        this.setInvStackList(NonNullList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    protected AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory) {
        return ChestMenu.createGeneric9x6(syncId, playerInventory, this);
    }

    static void playSound(Level world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        double d = (double)pos.getX() + 0.5;
        double e = (double)pos.getY() + 0.5;
        double f = (double)pos.getZ() + 0.5;

        world.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f - 1.0f);
    }

    protected void onViewerCountUpdate(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }

    @Override
    public void onOpen(Player player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(Player player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected Component getContainerName() {
        return super.getContainerName();
    }

    @Override
    public int size() {
        return 54;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + ".reinforced_chest" );
    }
}
