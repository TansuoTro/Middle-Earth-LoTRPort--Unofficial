package net.jukoz.me.recipe;
import net.jukoz.me.item.MEComponents;

import net.minecraft.component.DataComponentTypes;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;


public class HelmetAttachmentRemovalRecipe extends SpecialCraftingRecipe {

    public HelmetAttachmentRemovalRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (!itemStack.getItem().getRecipeRemainder().isEmpty()) {
                defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder().getItem()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
            }else if (MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA) != null){
                ItemStack helmetAttachment = new ItemStack(Registries.ITEM.get(new Identifier(MiddleEarth.MOD_ID, MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachment().getName())));
                MEComponents.set(helmetAttachment, MEComponents.HELMET_ATTACHMENT_DATA, itemStack.get(MEComponents.HELMET_ATTACHMENT_DATA));
                helmetAttachment.set(MEComponents.DYED_COLOR, new DyedColorComponent(MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachmentColor()));
                defaultedList.set(i, helmetAttachment);
            }
        }

        return defaultedList;
    }


    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem && MEComponents.get(itemStack2, MEComponents.HELMET_ATTACHMENT_DATA) != null) {
                    if (!itemStackHelmet.isEmpty()) {
                        return false;
                    }
                    itemStackHelmet = itemStack2;
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return false;
                    }
                    itemStackHood = itemStack2;
                }
            }
        }
        return !itemStackHelmet.isEmpty() && !itemStackHood.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem && MEComponents.get(itemStack2, MEComponents.HELMET_ATTACHMENT_DATA) != null) {
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
            MEComponents.remove(itemStack, MEComponents.HELMET_ATTACHMENT_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HELMET_ATTACHMENT_REMOVAL;
    }
}
