package net.fabricmc.fabric.api.tag.convention.v1;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** Forge compat shim for the conventional item tags the port uses. */
public final class ConventionalItemTags {
    public static final TagKey<Item> SHIELDS = ItemTags.create(new ResourceLocation("minecraft", "shields"));

    private ConventionalItemTags() {}
}
