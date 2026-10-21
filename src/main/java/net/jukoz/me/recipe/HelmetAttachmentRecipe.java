package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.jukoz.me.item.items.armor.HelmetAttachmentItem;
import net.jukoz.me.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;

public class HelmetAttachmentRecipe extends CustomRecipe {
    public HelmetAttachmentRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
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
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack hood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
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
            if (MEComponents.get(hood, MEComponents.DYED_COLOR) != null){
                color = MEComponents.get(hood, MEComponents.DYED_COLOR).rgb();
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

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HELMET_ATTACHMENT;
    }
}
