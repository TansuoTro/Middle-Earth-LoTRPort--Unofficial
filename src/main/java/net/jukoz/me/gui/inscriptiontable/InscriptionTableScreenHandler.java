package net.jukoz.me.gui.inscriptiontable;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.crafting.RecipeManager;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.TagKey;
import net.minecraft.screen.*;
import net.minecraft.world.inventory.Slot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.ToolItemsME;
import net.jukoz.me.item.MEDurability;
import net.jukoz.me.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.jukoz.me.recipe.RecipesME;
import net.jukoz.me.recipe.inscription.InscriptionRecipe;
import net.jukoz.me.recipe.inscription.InscriptionWordBank;
import net.jukoz.me.sound.SoundsME;
import net.jukoz.me.utils.ItemTagsME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.jukoz.me.compat.MEEnchants;

public class InscriptionTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Level world;
    public List<InscriptionRecipe> outputRecipes;
    public Holder<Enchantment> enchant;
    public int level;
    public final Inventory input;

    public Player player;

    private byte[] availableWords;
    public List<String> selectedWords;
    private final Property levelCost;

    private static final ResourceLocation EMPTY_SLOT_CHISEL_TEXTURE = MiddleEarth.of("container/slot/chisel");

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER, syncId);

        this.outputRecipes = new ArrayList<>();
        this.selectedWords = new ArrayList<>();

        this.player = playerInventory.player;

        this.input = new SimpleInventory(3) {
            @Override
            public void markDirty() {
                super.markDirty();
                InscriptionTableScreenHandler.this.updateInput(input);
            }
        };

        this.addSlot(new Slot(this.input, 0, 135, 48) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.INSCRIPTION_CATALYSTS) && !this.hasStack();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.input, 1, 225, 48) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.EARLY_CHISELS);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            public Pair<ResourceLocation, ResourceLocation> getBackgroundSprite() {
                return Pair.of(EMPTY_SLOT_CHISEL_TEXTURE, EMPTY_SLOT_CHISEL_TEXTURE);
            }
        });

        this.addSlot(new Slot(this.input, 2, 180, 48){
            @Override
            public boolean canInsert(ItemStack stack) {
                return (stack.isIn(TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "enchantable/bow"))) ||
                        stack.isIn(TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "enchantable/crossbow")))
                        || stack.isEnchantable() || stack.hasEnchantments()) && !stack.isOf(Items.BOOK);
            }
        });

        this.context = context;
        this.world = playerInventory.player.getWorld();

        this.levelCost = Property.create();
        this.addProperty(this.levelCost);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }

    public int getPlayerLevels(){
        return this.player.experienceLevel;
    }

    public boolean hasGem(){
        return !this.input.getStack(0).isEmpty();
    }

    public boolean hasChisel(){
        return !this.input.getStack(1).isEmpty();
    }

    public boolean hasInput(){
        return !this.input.getStack(2).isEmpty();
    }

    public boolean hasAll(){
        return this.hasGem() && this.hasChisel() && this.hasInput();
    }

    public boolean canUse(Player player) {
        return canUse(this.context, player, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    public List<String> getWords(){
        ItemStack catalyst = this.input.getStack(0);

        List<String> words = new ArrayList<>();

        if(this.hasAll()){
            words.addAll(InscriptionWordBank.wordBank.get(catalyst.getItem()));
            words.addAll(InscriptionWordBank.wordBank.get(null));
        }
        return words;
    }

    public byte[] getAvailableWords() {
        return this.availableWords;
    }

    public void updateAvailableWords(byte[] wordsIndexes) {
        availableWords = wordsIndexes;
    }

    public void updateWords(boolean add, String word, boolean reset){
        boolean foundEnchant = false;
        Holder<Enchantment> resultEnchant = null;
        int resultLevel = 0;
        int resultMaxLevel = 0;
        int resultLevelCost = 0;

        if (!reset){
            if (add){
                if (this.selectedWords.isEmpty()){
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_FIRST, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                } else if(this.selectedWords.size() == 1) {
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_SECOND, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }else if(this.selectedWords.size() == 2) {
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_THIRD, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }
                this.selectedWords.add(word);
            } else {
                this.selectedWords.remove(word);
            }
        }
        if (!this.outputRecipes.isEmpty()){
            for (InscriptionRecipe recipe : this.outputRecipes){
                if (this.selectedWords.size() == 2 && recipe.inputWords.size() == 3 && add){
                    if (Objects.equals(this.selectedWords.get(1), recipe.inputWords.get(1))
                            && recipe.enchant.value().isAcceptableItem(input.getStack(2))
                            && this.selectedWords.get(0).equals(recipe.inputWords.get(0))){
                    }
                }
                if (recipe.inputWords.equals(this.selectedWords)){
                    if (canEnchant(input.getStack(2), recipe.enchant, recipe.level)){
                        foundEnchant = true;
                        resultEnchant = recipe.enchant;
                        resultLevel = recipe.level;
                        resultMaxLevel = recipe.enchant.value().getMaxLevel();
                        resultLevelCost = recipe.levelCost;
                    }
                }
            }

            List<String> words = new ArrayList<>();
            List<String> allowedWords = new ArrayList<>();
            if(this.hasAll()){
                words.addAll(InscriptionWordBank.wordBank.get(this.input.getStack(0).getItem()));
                words.addAll(InscriptionWordBank.wordBank.get(null));
            }

            availableWords = new byte[words.size() + 1];
            words.add(word);
            RecipeManager serverRecipeManager = (RecipeManager) this.world.getRecipeManager();
            List<InscriptionRecipe> availableRecipes = serverRecipeManager.listAllOfType(RecipesME.INSCRIPTION_TABLE)
                    .stream().filter((inscriptionRecipeRecipeEntry -> {
                            if(!inscriptionRecipeRecipeEntry.enchant.value().isAcceptableItem(input.getStack(2))) return false;
                            if(selectedWords.isEmpty()) return true;
                            else return inscriptionRecipeRecipeEntry.inputWords.get(0).equals(selectedWords.get(0));
                    })).toList();

            for(InscriptionRecipe recipe : availableRecipes) {
                List<String> recipeWords = recipe.inputWords;
                for(String availableWord : recipeWords) {
                    if(!allowedWords.contains(availableWord)) {
                        allowedWords.add(availableWord);
                    }
                }
            }

            int index = 0;
            for(String wordElement : words) {
                if(allowedWords.contains(wordElement) || selectedWords.contains(wordElement)) {
                    availableWords[index] = 1;
                }
                index++;
            }

            InscriptionEnchantInfoPacket newPacket;
            if (foundEnchant){
                newPacket = new InscriptionEnchantInfoPacket(resultEnchant.value().getName(resultLevel).getString(), resultLevel, resultMaxLevel, availableWords);
                this.enchant = resultEnchant;
                this.level = resultLevel;
                calculateCost(resultLevelCost, resultEnchant);
            } else {
                newPacket = new InscriptionEnchantInfoPacket("", 0, 0, availableWords);
                this.enchant = null;
                this.level = 0;
                this.levelCost.set(0);
            }
            ServerPlayNetworking.send((ServerPlayer) player, newPacket);
        }
    }

    private void updateInput(Inventory inventory) {
        ItemStack inputChisel = inventory.getStack(1);
        if (this.hasAll()) {
             if (!this.world.isClient){
                updateWords(false, "", true);
                RecipeManager serverRecipeManager = (RecipeManager) this.world.getRecipeManager();
                this.outputRecipes = serverRecipeManager.getAllMatches(RecipesME.INSCRIPTION_TABLE, new SingleStackRecipeInput(inputChisel), this.world);
             }
        } else {
            updateWords(false, "", true);
            this.outputRecipes = new ArrayList<>();
            this.selectedWords = new ArrayList<>();
        }
    }

    private boolean canEnchant(ItemStack stack, Holder<Enchantment> enchant, int level) {
        boolean acceptableItem = enchant.value().isAcceptableItem(stack);
        if (acceptableItem && EnchantmentHelper.isCompatible(MEEnchants.list(stack), enchant.value())){
            return EnchantmentHelper.getLevel(enchant.value(), stack) == level - 1;
        } else {
            if (MEEnchants.has(stack, enchant)){
                return EnchantmentHelper.getLevel(enchant.value(), stack) == level - 1;
            } else {
                return false;
            }
        }
    }

    public void enchantItem(){
        ItemStack stackCatalyst = this.input.getStack(0);
        ItemStack stackChisel = this.input.getStack(1);
        ItemStack stack = this.input.getStack(2);

        if(!MEDurability.hasCustom(stackCatalyst)){
            if (stackCatalyst.isOf(Items.LAPIS_LAZULI)) this.input.setStack(0, ItemStack.EMPTY);
            MEDurability.set(stackCatalyst, 2);
            stackCatalyst.setDamage(stackCatalyst.getDamage() + 1);
        } else {
            if (stackCatalyst.getDamage() == stackCatalyst.getMaxDamage()){
                this.input.setStack(0, ItemStack.EMPTY);
            } else {
                stackCatalyst.setDamage(stackCatalyst.getDamage() + 1);
            }
        }

        if ((stackChisel.getDamage() == stackChisel.getMaxDamage()) && !stackChisel.isOf(ToolItemsME.MITHRIL_CHISEL)){
            this.input.setStack(1, ItemStack.EMPTY);
        } else {
            stackChisel.setDamage(stackChisel.getDamage() + 1);
        }

        if (this.enchant != null && this.level != 0){
            if (!player.isCreative()) {
                player.addExperienceLevels(-this.levelCost.get());
            }
            this.levelCost.set(0);

            stack.addEnchantment(this.enchant.value(), this.level);
            world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_ENCHANT, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        updateWords(false, "", true);
    }

    public void calculateCost(int levelCost, Holder<Enchantment> enchant) {
        ItemStack itemStack = this.input.getStack(2);
        int k = 0;

        if (!itemStack.isEmpty()){
            for (Enchantment enchantEntry : EnchantmentHelper.get(itemStack).keySet()) {
                if (enchantEntry != enchant.value()) {
                    k++;
                }
            }
            this.levelCost.set(levelCost + k);
        }
    }

    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
    }

    public ItemStack quickMove(Player player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            Item item = originalStack.getItem();
            stack = originalStack.copy();
            if(slot < this.input.size()) {
                if(!this.insertItem(originalStack, this.input.size(), this.slots.size(), true)) {
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
        this.dropInventory(player, this.input);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 108 + j * 18, 102 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 108 + i * 18, 160));
        }
    }
}
