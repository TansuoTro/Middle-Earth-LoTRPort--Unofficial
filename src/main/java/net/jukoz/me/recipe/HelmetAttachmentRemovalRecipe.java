package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;

public class HelmetAttachmentRemovalRecipe extends CustomRecipe {

    public HelmetAttachmentRemovalRecipe(ResourceLocation id, CraftingBookCategory category) {
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
            }else if (MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA) != null){
                ItemStack helmetAttachment = new ItemStack(Registries.ITEM.get(new ResourceLocation(MiddleEarth.MOD_ID, MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachment().getName())));
                MEComponents.set(helmetAttachment, MEComponents.HELMET_ATTACHMENT_DATA, MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA));
                MEComponents.set(helmetAttachment, MEComponents.DYED_COLOR, new DyedColorComponent(MEComponents.get(itemStack, MEComponents.HELMET_ATTACHMENT_DATA).helmetAttachmentColor()));
                defaultedList.set(i, helmetAttachment);
            }
        }

        return defaultedList;
    }

    @Override
    public boolean matches(CraftingContainer input, Level world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
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
    public ItemStack craft(CraftingContainer input, RegistryAccess lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStack(i);
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

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HELMET_ATTACHMENT_REMOVAL;
    }
}
