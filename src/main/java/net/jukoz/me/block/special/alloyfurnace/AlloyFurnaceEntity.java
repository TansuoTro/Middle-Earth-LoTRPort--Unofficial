package net.jukoz.me.block.special.alloyfurnace;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreenHandler;
import net.jukoz.me.network.ModNetworks;
import net.jukoz.me.recipe.AlloyRecipe;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.Optional;

public class AlloyFurnaceEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private static final String ID = "alloy_furnace";
    public static final int MAX_PROGRESS = 1200;
    public static final int FUEL_SLOT = 0;
    public static final int OUTPUT_SLOT = 5;
    private final NonNullList<ItemStack> inventory =
            NonNullList.withSize(6, ItemStack.EMPTY);
    protected final ContainerData propertyDelegate;
    protected final Map<Item, Integer> fuelTimeMap = AbstractFurnaceBlockEntity.getFuel();
    private int progress = 0;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public AlloyFurnaceEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FURNACE, pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlloyFurnaceEntity.this.progress;
                    case 1 -> AlloyFurnaceEntity.this.fuelTime;
                    case 2 -> AlloyFurnaceEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlloyFurnaceEntity.this.progress = value;
                    case 1 -> AlloyFurnaceEntity.this.fuelTime = value;
                    case 2 -> AlloyFurnaceEntity.this.maxFuelTime = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new AlloyFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        ContainerHelper.writeNbt(nbt, inventory);
        nbt.putInt(ID + ".progress", progress);
        nbt.putInt(ID + ".fuel-time", fuelTime);
        nbt.putInt(ID + ".max-fuel-time", maxFuelTime);
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        ContainerHelper.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt(ID + ".progress");
        fuelTime = nbt.getInt(ID + ".fuel-time");
        maxFuelTime = nbt.getInt(ID + ".max-fuel-time");
    }

    public ItemStack getRenderStack() {
        return this.getItem(OUTPUT_SLOT);
    }
    public void setInventory(NonNullList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void markDirty() {
        if(world != null && !world.isClientSide()) {
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

    protected boolean isFuel(Item item) {
        for (Item item1: fuelTimeMap.keySet()) {
            if(item1.equals(item)) return true;
        }
        return false;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == FUEL_SLOT) {
            return isFuel(stack.getItem());
        }
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot != OUTPUT_SLOT;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ContainerHelper.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ContainerHelper.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public boolean canPlayerUse(Player player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, AlloyFurnaceEntity entity) {
        if(world.isClientSide()) return;
        entity.fuelTime = Math.max(0, entity.fuelTime - 1);
        boolean progress = false;
        if(hasRecipe(entity)) {
            if(entity.hasFuel(entity)) {
                entity.progress++;
                progress = true;
                markDirty(world, blockPos, blockState); // Reloads the origin in this chunk, for sync & saving.
                if(entity.progress >= MAX_PROGRESS) {
                    craftItem(entity);
                    entity.progress = 0;
                }
            }
        }
        if (!progress){
            entity.progress = Math.max(entity.progress - 2, 0);
            markDirty(world, blockPos, blockState);
        }
        boolean isCooking = entity.fuelTime > 0;
        blockState = blockState.setValue(AbstractFurnaceBlock.LIT, isCooking);
        world.setBlock(blockPos, blockState, (Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS));
    }

    private static void craftItem(AlloyFurnaceEntity entity) {
        SimpleContainer inventory1 = new SimpleContainer(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getItem(i));
        }

        Optional<AlloyRecipe> match = entity.level().getRecipeManager()
                .getFirstMatch(AlloyRecipe.Type.INSTANCE, inventory1, entity.level());
        if(match.isEmpty()) throw new RuntimeException("Somehow... you crafted an item without recipe?!");

        if(hasRecipe(entity)) {
            for (int i = 1; i <= 4; i++) {
                entity.removeStack(i, 1);
            }
            entity.setStack(OUTPUT_SLOT, new ItemStack(match.get().output.getRegistryEntry(),
                    entity.getItem(OUTPUT_SLOT).getCount() + match.get().output.getCount()));
            entity.markDirty();
        }
    }

    private static boolean hasRecipe(AlloyFurnaceEntity entity) {
        SimpleContainer inventory1 = new SimpleContainer(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getItem(i));
        }

        Optional<AlloyRecipe> match = entity.level().getRecipeManager()
                .getFirstMatch(AlloyRecipe.Type.INSTANCE, inventory1, entity.level());
        if(match.isEmpty()) return false;

        return canInsertAmountIntoOutput(inventory1, match.get().output.getCount())
                && canInsertRecipeIntoOutput(inventory1, match.get().output.getItem());
    }

    private boolean hasFuel(AlloyFurnaceEntity entity) {

        SimpleContainer inventory1 = new SimpleContainer(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory1.setStack(i, entity.getItem(i));
        }

        Item fuelItem = inventory1.getItem(FUEL_SLOT).getItem();
        if(this.fuelTime > 0) return true;
        else {
            if(isFuel(fuelItem)) {
                getFuel(entity, fuelItem);
                return true;
            } else return false;
        }
    }

    private void getFuel(AlloyFurnaceEntity entity, Item fuelItem) {
        fuelTime = Math.round(fuelTimeMap.get(fuelItem) / 16);
        maxFuelTime = fuelTime;
        if(fuelItem == Items.LAVA_BUCKET) {
            entity.removeStack(FUEL_SLOT);
            entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultInstance());
        }
        else entity.getItem(FUEL_SLOT).decrement(1);
    }

    private static boolean canInsertAmountIntoOutput(SimpleContainer inventory1, int count) {
        return inventory1.getItem(OUTPUT_SLOT).getMaxCount() > inventory1.getItem(OUTPUT_SLOT).getCount() + (count- 1);
    }
    private static boolean canInsertRecipeIntoOutput(SimpleContainer inventory1, Item item) {
        return inventory1.getItem(OUTPUT_SLOT).getItem() == item || inventory1.getItem(OUTPUT_SLOT).isEmpty();
    }
}
