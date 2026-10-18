package net.jukoz.me.recipe;
import net.jukoz.me.item.MEComponents;

import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;


public class BackAttachmentRemovalRecipe extends SpecialCraftingRecipe {

    public BackAttachmentRemovalRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(RecipeInputInventory input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getStack(i);
            if (!itemStack.getRecipeRemainder().isEmpty()) {
                defaultedList.set(i, new ItemStack(itemStack.getRecipeRemainder().getItem()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
            } else if (MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA) != null){
                ItemStack backAttachment = new ItemStack(Registries.ITEM.get(new Identifier(MiddleEarth.MOD_ID, MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA).backAttachment().getName())));
                MEComponents.set(backAttachment, MEComponents.BACK_ATTACHMENT_DATA, MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA));
                backAttachment.set(MEComponents.DYED_COLOR, new DyedColorComponent(MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA).backAttachmentColor()));
                defaultedList.set(i, backAttachment);
            }
        }

        return defaultedList;
    }


    @Override
    public boolean matches(RecipeInputInventory input, World world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackShears = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && MEComponents.get(itemStack2, MEComponents.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return false;
                    }
                    itemStackShears = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackShears.isEmpty();
    }

    @Override
    public ItemStack craft(RecipeInputInventory input, DynamicRegistryManager lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && MEComponents.get(itemStack2, MEComponents.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (!itemStack.isEmpty()) {
            MEComponents.remove(itemStack, MEComponents.BACK_ATTACHMENT_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL;
    }
}
