package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.dataComponents.MountArmorAddonComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import net.jukoz.me.utils.ItemTagsME;

public class MountArmorSideSkullAddonRecipe extends SpecialCraftingRecipe {
    public MountArmorSideSkullAddonRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackString = ItemStack.EMPTY;
        ItemStack itemStackSkull= ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                }
                else if (itemStack2.isOf(Items.STRING)) {
                    if (!itemStackString.isEmpty()) {
                        return false;
                    }
                    itemStackString = itemStack2;
                }
                else if (itemStack2.isOf(Items.SKELETON_SKULL)) {
                    if (!itemStackSkull.isEmpty()) {
                        return false;
                    }
                    itemStackSkull = itemStack2;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackString.isEmpty() && !itemStackSkull.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                }
            }
        }

        boolean topArmorAddons = false;
        if(MEComponents.get(itemStack, MEComponents.MOUNT_ARMOR_DATA) != null) {
            topArmorAddons = MEComponents.get(itemStack, MEComponents.MOUNT_ARMOR_DATA).topArmorAddon();
        }

        ItemStack output = itemStack.copyWithCount(1);

        MEComponents.set(output, MEComponents.MOUNT_ARMOR_DATA, new MountArmorAddonComponent(topArmorAddons, true));

        return output;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON;
    }
}
