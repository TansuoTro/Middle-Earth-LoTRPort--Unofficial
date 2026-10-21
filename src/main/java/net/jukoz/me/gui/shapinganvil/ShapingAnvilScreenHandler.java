package net.jukoz.me.gui.shapinganvil;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.network.packets.C2S.AnvilIndexPacket;
import net.jukoz.me.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.jukoz.me.network.packets.S2C.ShapingAnvilRecipePacket;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.recipe.RecipesME;
import java.util.List;
import net.minecraft.world.level.block.state.properties.Property;

public class ShapingAnvilScreenHandler extends AbstractContainerMenu {
    private final Inventory inventory;
    private final ContainerData propertyDelegate;
    protected BlockPos pos;
    private final Level world;
    private List<AnvilShapingRecipe> availableRecipes;
    private ItemStack outputStack;
    private Player player;
    private Property selectedIndex;
    private Property recipesSize;

    public ShapingAnvilScreenHandler(int syncId, Inventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleContainer(1), new SimpleContainerData(2));
        this.pos = blockPos;
    }

    public ShapingAnvilScreenHandler(int syncId, Inventory playerInventory, Inventory inventory, ContainerData delegate) {
        super(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.pos = BlockPos.ORIGIN;
        this.world = playerInventory.player.getWorld();
        this.outputStack = ItemStack.EMPTY;
        this.player = playerInventory.player;

        this.selectedIndex = Property.create();
        this.recipesSize = Property.create();

        this.addProperty(selectedIndex).set(-1);
        this.addProperty(recipesSize).set(0);

        this.addSlot(new ShapingAnvilSlot(inventory, 0, 136, 33){
            @Override
            public void markDirty() {
                super.markDirty();
                if(player.getWorld().isClient) {
                    ShapingAnvilScreen screen = (ShapingAnvilScreen)Minecraft.getInstance().currentScreen;
                    if(screen != null) screen.clearOutputs();
                }
                ShapingAnvilScreenHandler.this.updateStack(ShapingAnvilScreenHandler.this.inventory);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);

    }

    @Override
    public ItemStack quickMove(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void updateScreen(){
        this.updateStack(this.inventory);
    }

    public void updateStack(Inventory inventory) {
        ItemStack input = inventory.getStack(0);
        if (!this.world.isClient){
            RecipeManager serverRecipeManager = (RecipeManager) this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getAllMatches(RecipesME.ANVIL_SHAPING, new SingleStackRecipeInput(input), this.world);
            recipesSize.set(this.availableRecipes.size());
            int index = 0;
            for(AnvilShapingRecipe recipe : availableRecipes) {
                ShapingAnvilRecipePacket newPacket = new ShapingAnvilRecipePacket(index++, recipe.getOutput());
                ServerPlayNetworking.send((ServerPlayer) player, newPacket);
            }
        }
    }

    public int getAvailableRecipeCount() {
        int count = 0;
        ItemStack input = inventory.getStack(0);
        if (!this.world.isClient){
            RecipeManager serverRecipeManager = (RecipeManager) this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getAllMatches(RecipesME.ANVIL_SHAPING, new SingleStackRecipeInput(input), this.world);
            count = this.availableRecipes.size();
        }
        return count;
    }

    public List<AnvilShapingRecipe> getAvailableRecipes() {
        return availableRecipes;
    }

    public int getSelectedRecipe() {
        return selectedIndex.get();
    }
    public void setSelectedRecipe(int index) {
        selectedIndex.set(index);
        AnvilIndexPacket anvilIndexPacket = new AnvilIndexPacket(index, getPos().getX(), getPos().getY(), getPos().getZ());
        ClientPlayNetworking.send(anvilIndexPacket);
    }

    public boolean onButtonClick(Player player, int id) {
        this.player = player;
        return this.isInBounds(id);
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.recipesSize.get();
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

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
