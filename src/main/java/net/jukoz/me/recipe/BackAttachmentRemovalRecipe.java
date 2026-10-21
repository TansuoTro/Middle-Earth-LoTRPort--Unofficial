package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;

public class BackAttachmentRemovalRecipe extends CustomRecipe {

    public BackAttachmentRemovalRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public NonNullList<ItemStack> getRemainder(CraftingContainer input) {
        NonNullList<ItemStack> defaultedList = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getItem(i);
            if (!itemStack.getCraftingRemainingItem().isEmpty()) {
                defaultedList.set(i, new ItemStack(itemStack.getCraftingRemainingItem().getItem()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
            } else if (MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA) != null){
                ItemStack backAttachment = new ItemStack(Registries.ITEM.get(new ResourceLocation(MiddleEarth.MOD_ID, MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA).backAttachment().getName())));
                MEComponents.set(backAttachment, MEComponents.BACK_ATTACHMENT_DATA, MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA));
                MEComponents.set(backAttachment, MEComponents.DYED_COLOR, new DyedColorComponent(MEComponents.get(itemStack, MEComponents.BACK_ATTACHMENT_DATA).backAttachmentColor()));
                defaultedList.set(i, backAttachment);
            }
        }

        return defaultedList;
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackShears = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && MEComponents.get(itemStack2, MEComponents.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!itemStack2.is(Items.SHEARS)) {
                        return false;
                    }
                    itemStackShears = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackShears.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && MEComponents.get(itemStack2, MEComponents.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!itemStack2.is(Items.SHEARS)) {
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

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL;
    }
}
