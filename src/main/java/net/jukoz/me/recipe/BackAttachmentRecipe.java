package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.items.armor.BackAttachmentItem;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.world.level.Level;


public class BackAttachmentRecipe extends SpecialCraftingRecipe {

    public BackAttachmentRecipe(ResourceLocation id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory input, Level world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackBackAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    if (MEComponents.get(itemStack2, MEComponents.BACK_ATTACHMENT_DATA) != null){
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return false;
                    }
                    itemStackBackAttachment = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackBackAttachment.isEmpty();
    }

    @Override
    public ItemStack craft(RecipeInputInventory input, DynamicRegistryManager lookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack backAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return ItemStack.EMPTY;
                    }
                    backAttachment = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (MEComponents.get(backAttachment, MEComponents.DYED_COLOR) != null){
                color = MEComponents.get(backAttachment, MEComponents.DYED_COLOR).rgb();
            } else {
                color = 0;
            }
            return BackAttachmentDataComponent.setBackAttachmentWithColor(itemStack,
                    MEComponents.get(backAttachment, MEComponents.BACK_ATTACHMENT_DATA).backAttachment(),
                    color);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT;
    }
}
