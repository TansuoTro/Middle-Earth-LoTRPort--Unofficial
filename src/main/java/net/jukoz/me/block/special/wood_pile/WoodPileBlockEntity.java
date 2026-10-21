package net.jukoz.me.block.special.wood_pile;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.network.ModNetworks;
import net.jukoz.me.utils.ImplementedInventory;
import net.jukoz.me.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import static net.jukoz.me.block.special.wood_pile.WoodPileBlock.STAGE;

public class WoodPileBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private static final String ID = "wood_pile";
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public WoodPileBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOOD_PILE, pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, Player player) {
        return new WoodPileScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }
    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        if(this.hasAmount(5)){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 2)
                    .with(BlockStateProperties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(BlockStateProperties.HORIZONTAL_FACING)));
        }  else if (this.isEmpty()){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 0)
                    .with(BlockStateProperties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(BlockStateProperties.HORIZONTAL_FACING)));
        } else if (!this.hasAmount(5) && !isEmpty()){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 1)
                    .with(BlockStateProperties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(BlockStateProperties.HORIZONTAL_FACING)));
        }
    }
    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    public boolean hasAmount(int amount) {
        int result = 0;
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                result++;
            }
        }
        return result >= amount;
    }

    @Override
    public void markDirty() {
        if(world != null && !world.isClient()) {
            FriendlyByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) world, getPos())) {
                ServerPlayNetworking.send(player, ModNetworks.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    @Override
    public void clear() {
        inventory.clear();
    }


    @Override
    public boolean canPlayerUse(Player player) {
        return Inventory.canPlayerUse(this, player);
    }
}