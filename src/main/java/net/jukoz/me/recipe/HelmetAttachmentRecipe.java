package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.minecraft.component.DataComponentTypes;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.jukoz.me.item.items.armor.HelmetAttachmentItem;
import net.jukoz.me.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;


public class HelmetAttachmentRecipe extends SpecialCraftingRecipe {
    public HelmetAttachmentRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStackHelmet.isEmpty()) {
                        return false;
                    }
                    if (MEComponents.get(itemStack2, MEComponents.HELMET_ATTACHMENT_DATA) != null){
                        return false;
                    }
                    itemStackHelmet = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof HelmetAttachmentItem)) {
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
        ItemStack hood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof HelmetAttachmentItem)) {
                        return ItemStack.EMPTY;
                    }
                    hood = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (hood.get(MEComponents.DYED_COLOR) != null){
                color = hood.get(MEComponents.DYED_COLOR).rgb();
            } else {
                color = 0;
            }
            if (MEComponents.get(hood, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachment().getConstantState() == HelmetAttachmentsStatesME.DOWN){
                return HelmetAttachmentDataComponent.setHelmetAttachmentWithcolor(itemStack, true, MEComponents.get(hood, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachment(), color);
            } else {
                return HelmetAttachmentDataComponent.setHelmetAttachmentWithcolor(itemStack, false, MEComponents.get(hood, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachment(), color);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HELMET_ATTACHMENT;
    }
}
