package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.dataComponents.MountArmorAddonComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;
import net.jukoz.me.utils.ItemTagsME;

public class MountArmorSideSkullAddonRecipe extends CustomRecipe {
    public MountArmorSideSkullAddonRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackString = ItemStack.EMPTY;
        ItemStack itemStackSkull= ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                }
                else if (itemStack2.is(Items.STRING)) {
                    if (!itemStackString.isEmpty()) {
                        return false;
                    }
                    itemStackString = itemStack2;
                }
                else if (itemStack2.is(Items.SKELETON_SKULL)) {
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
    public boolean fits(int width, int height) {
        return width * height >= 3;
    }    @Override
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(ItemTagsME.WARG_ARMORS)) {
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
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON;
    }
}
