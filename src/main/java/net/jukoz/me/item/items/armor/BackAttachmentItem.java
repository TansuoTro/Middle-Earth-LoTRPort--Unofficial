package net.jukoz.me.item.items.armor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;

public class BackAttachmentItem extends Item implements net.minecraft.world.item.DyeableLeatherItem {

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material) {
        super(settings.maxCount(1));
    }
}
