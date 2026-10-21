package net.jukoz.me.utils;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class ItemUtil {

    public static ResourceLocation getIdentifier(Item item){
        return Registries.ITEM.getId(item);
    }
}
