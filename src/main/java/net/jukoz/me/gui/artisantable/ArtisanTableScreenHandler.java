package net.jukoz.me.gui.artisantable;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.MEDurability;
import net.jukoz.me.item.dataComponents.ArtisanDataComponent;
import net.jukoz.me.network.packets.C2S.ArtisanIndexPacket;
import net.jukoz.me.network.packets.S2C.ArtisanRecipePacket;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.state.properties.Property;

public class ArtisanTableScreenHandler extends AbstractContainerMenu {
    private final ContainerLevelAccess context;
    private final DataSlot selectedRecipe;
    private final DataSlot recipesSize;
    private final Level world;
    private List<ArtisanRecipe> availableRecipes;
    private List<ItemStack> outputs;
    private ItemStack inputStack;
    long lastTakeTime;
    private final ArtisanTableSlot[][] inputSlots;
    final Slot outputSlot;
    Runnable contentsChangedListener;
    public final Inventory input;
    final ResultContainer output;
    private Player playerEntity;
    private ArtisanTableInputsShape inputsShape = ArtisanTableInputsShape.ANY;
    private int xp = 0;

    private String disposition;
    private boolean isCreative;

    public ArtisanTableScreenHandler(int syncId, Inventory playerInventory, String disposition) {
        this(syncId, playerInventory, ContainerLevelAccess.EMPTY);

        String[] splited = disposition.split("/");
        this.disposition = splited[0];
        this.isCreative = Boolean.parseBoolean(splited[1]);
    }

    public ArtisanTableScreenHandler(int syncId, Inventory playerInventory, ContainerLevelAccess context, String disposition) {
        this(syncId, playerInventory, context);

        String[] splited = disposition.split("/");
        this.disposition = splited[0];
        this.isCreative = Boolean.parseBoolean(splited[1]);
    }

    public ArtisanTableScreenHandler(int syncId, Inventory playerInventory, final ContainerLevelAccess context) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);
        this.selectedRecipe = DataSlot.create();
        this.recipesSize = DataSlot.create();
        this.availableRecipes = Lists.newArrayList();
        this.outputs = new ArrayList<>();
        this.inputStack = ItemStack.EMPTY;
        this.contentsChangedListener = () -> {
        };

        this.disposition = "neutral";
        this.isCreative = false;

        this.input = new SimpleContainer(9) {
            public void markDirty() {
                super.markDirty();
                outputs.clear();
                ArtisanTableScreenHandler.this.onContentChanged(this);
                ArtisanTableScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new ResultContainer();
        this.context = context;
        this.world = playerInventory.player.level();
        this.playerEntity = playerInventory.player;

        inputSlots = new ArtisanTableSlot[3][3];
        int index = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                inputSlots[y][x] = (ArtisanTableSlot) this.addSlot(new ArtisanTableSlot(this.input, index++, 13 + 18 * x, 16 + 18 * y));
            }
        }

        this.outputSlot = this.addSlot(new Slot(this.output, 9, 165, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(Player player, ItemStack itemStack) {
                ArtisanTableScreenHandler.this.playerEntity = player;
                itemStack.onCraft(player.level(), player, itemStack.getCount());
                ArtisanTableScreenHandler.this.output.unlockLastRecipe(player, this.getInputStacks());
                player.addExperience(getExperience());
                ArtisanTableScreenHandler.this.xp = 0;

                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        ArtisanTableSlot slot = inputSlots[y][x];
                        if (x == 0 && y == 0) itemStack = slot.takeStack(1);
                        else slot.takeStack(1);
                    }
                }

                if (!itemStack.isEmpty()) {
                    ArtisanTableScreenHandler.this.setupResultSlot(player);
                }

                long l = world.getTime();
                if (ArtisanTableScreenHandler.this.lastTakeTime != l) {
                    world.playSound(null, (BlockPos) player.blockPosition(), SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ArtisanTableScreenHandler.this.lastTakeTime = l;
                }

                super.onTakeItem(player, itemStack);
            }

            private int getExperience() {
                return ArtisanTableScreenHandler.this.xp;
            }

            private List<ItemStack> getInputStacks() {
                List<ItemStack> stacks = new ArrayList<>();
                for (ArtisanTableSlot[] row : inputSlots) {
                    for (ArtisanTableSlot slot : row) {
                        stacks.add(slot.getItem());
                    }
                }
                return stacks;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addProperty(this.selectedRecipe);
        this.addProperty(this.recipesSize);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<ArtisanRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public List<ItemStack> getAvailableOutputs() {
        return this.outputs;
    }

    public int getAvailableOutputsSize() {
        return this.recipesSize.get();
    }

    public void addRecipeOutput(int index, ItemStack itemStack) {
        boolean exists = outputs.stream().contains(item -> itemStack.getItem().equals(item.getItem()));
        if (exists) return;

        while (outputs.size() < index) {
            outputs.add(ItemStack.EMPTY);
        }
        outputs.add(itemStack);
    }

    public void setSelectedRecipe(int index) {
        selectedRecipe.set(index);
        ArtisanIndexPacket artisanIndexPacket = new ArtisanIndexPacket(index, this.syncId);
        ClientPlayNetworking.send(artisanIndexPacket);
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public boolean canCraft() {
        return !this.availableRecipes.isEmpty();
    }

    public boolean canUse(Player player) {
        this.playerEntity = player;
        return canUse(this.context, player, ModDecorativeBlocks.ARTISAN_TABLE);
    }

    public boolean onButtonClick(Player player, int id) {
        this.playerEntity = player;
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.setupResultSlot(player);
        }
        return true;
    }

    private boolean isInBounds(int id) {
        if (this.world.isClientSide) {
            return id >= 0 && id < this.outputs.size();
        } else {
            return id >= 0 && id < this.availableRecipes.size();
        }
    }

    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlots[0][0].getItem();
        this.inputStack = itemStack.copy();
        this.updateInput(inventory);
    }

    public void updateIndex(int index) {
        this.selectedRecipe.set(index);
        this.setupResultSlot(this.playerEntity);
    }

    public void changeTab(String shapeId) {
        if (this.playerEntity != null) {
            this.dropInventory(this.playerEntity, this.input);
            this.outputSlot.setStack(ItemStack.EMPTY);
        }

        ArtisanTableInputsShape inputsShape = ArtisanTableInputsShape.getShape(shapeId);
        if (inputsShape == null) return;
        this.inputsShape = inputsShape;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                ArtisanTableSlot slot = inputSlots[y][x];
                InputType inputType = this.inputsShape.getInputType(x, y);
                if (inputType == null) continue;
                else if (inputType == InputType.NONE) slot.setEnabled(false);
                else slot.setEnabled(true);
                slot.setInputType(inputType);
            }
        }

        this.outputs.clear();
        this.updateInput(this.input);
    }

    private void updateInput(Inventory inventory) {
        String currentCategory = this.inputsShape.getId();
        if (currentCategory == null) return;

        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            ArtisanTableSlot slot = inputSlots[i / 3][i % 3];
            if (slot.isEnabled()) {
                inputs.add(inventory.getItem(i));
            }
        }
        this.availableRecipes = Lists.newArrayList();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        if (!inputs.isEmpty() && !this.world.isClientSide) {
            this.availableRecipes = this.world.getRecipeManager()
                    .getAllMatches(ModRecipes.ARTISAN_TABLE, new MultipleStackRecipeInput(inputs), this.world);
        }

        List<ArtisanRecipe> filteredRecipes = new ArrayList<>();
        for (ArtisanRecipe recipe : this.availableRecipes) {
            String recipeCategory = recipe.getCategory();
            // Legacy recipes without a category show up under every tab.
            if (recipeCategory != null && !recipeCategory.equals(currentCategory)) continue;

            DispositionType recipeDisposition = parseDisposition(recipe.getDisposition());
            if (recipeDisposition == DispositionType.NEUTRAL
                    || recipeDisposition == parseDisposition(this.disposition)
                    || this.isCreative) {
                filteredRecipes.add(recipe);
            }
        }
        this.availableRecipes = filteredRecipes;

        int index = 0;
        for (ArtisanRecipe recipe : availableRecipes) {
            if (this.playerEntity instanceof ServerPlayer serverPlayer) {
                ArtisanRecipePacket artisanRecipePacket = new ArtisanRecipePacket(index, recipe.getOutput(this.world.registryAccess()));
                ServerPlayNetworking.send(serverPlayer, artisanRecipePacket);
            }
            index++;
        }
        this.recipesSize.set(availableRecipes.size());
    }

    private static DispositionType parseDisposition(String disposition) {
        if (disposition == null) return DispositionType.NEUTRAL;
        try {
            return DispositionType.valueOf(disposition.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DispositionType.NEUTRAL;
        }
    }

    void setupResultSlot(Player player) {
        this.playerEntity = player;
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            ArtisanRecipe artisanRecipe = this.availableRecipes.get(this.selectedRecipe.get());
            ItemStack itemStack = artisanRecipe.craft(this.input, this.world.registryAccess());
            // Donor populateResult: stamp the crafter for the tooltip and give crafted tools
            // +25% durability.
            if (this.playerEntity != null) {
                MEComponents.set(itemStack, MEComponents.ARTISAN_DATA, new ArtisanDataComponent(this.playerEntity.getUuid()));
            }
            if (itemStack.getMaxDamage() > 0) {
                MEDurability.set(itemStack, (int) (itemStack.getMaxDamage() * 1.25));
            }
            this.xp = artisanRecipe.getXp();

            if (itemStack.isItemEnabled(this.world.enabledFeatures())) {
                this.output.setLastRecipe(artisanRecipe);
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            this.xp = 0;
        }
        this.sendContentUpdates();
    }

    public MenuType<?> getType() {
        return ModScreenHandlers.ARTISAN_SCREEN_HANDLER;
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    public ItemStack quickMove(Player player, int slot) {
        this.playerEntity = player;
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if (invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getItem();
            Item item = originalStack.getItem();
            stack = originalStack.copy();
            if (slot == 9) {
                item.onCraft(originalStack, player.level(), player);
                if (!this.insertItem(originalStack, this.input.size() + 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                invSlot.onQuickTransfer(originalStack, stack);
            } else if (slot < this.input.size()) {
                if (!this.insertItem(originalStack, this.input.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.input.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }

            invSlot.onTakeItem(player, originalStack);
            this.sendContentUpdates();
        }
        return stack;
    }

    public void onClosed(Player player) {
        super.onClosed(player);
        this.output.removeStack(0);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 36 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 36 + i * 18, 142));
        }
    }
}
