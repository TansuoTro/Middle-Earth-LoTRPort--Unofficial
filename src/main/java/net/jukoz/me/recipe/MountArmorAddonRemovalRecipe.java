package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;
import net.jukoz.me.item.EquipmentItemsME;
import net.jukoz.me.utils.ItemTagsME;

public class MountArmorAddonRemovalRecipe extends CustomRecipe {

    public MountArmorAddonRemovalRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public NonNullList<ItemStack> getRemainder(CraftingContainer input) {
        NonNullList<ItemStack> defaultedList = NonNullList.ofSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getStack(i);
            if (!itemStack.getRecipeRemainder().isEmpty()) {
                defaultedList.set(i, new ItemStack(itemStack.getRecipeRemainder().getItem()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
                break;
            }
        }
        return defaultedList;
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackShears = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(ItemTagsME.WARG_ARMORS) && MEComponents.get(itemStack2, MEComponents.MOUNT_ARMOR_DATA) != null) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return false;
                    }
                    itemStackShears = itemStack2;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackShears.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(ItemTagsME.WARG_ARMORS) && MEComponents.get(itemStack2, MEComponents.MOUNT_ARMOR_DATA) != null) {
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
            MEComponents.remove(itemStack, MEComponents.MOUNT_ARMOR_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL;
    }
}
