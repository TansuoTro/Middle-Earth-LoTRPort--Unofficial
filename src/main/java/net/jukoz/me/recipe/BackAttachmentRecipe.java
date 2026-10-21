package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.items.armor.BackAttachmentItem;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;

public class BackAttachmentRecipe extends CustomRecipe {

    public BackAttachmentRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackBackAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
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
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack backAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
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

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT;
    }
}
