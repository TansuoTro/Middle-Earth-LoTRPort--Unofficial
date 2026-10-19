package net.jukoz.me.item.items.armor;

import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class BackAttachmentItem extends Item implements net.minecraft.item.DyeableItem {

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material) {
        super(settings.maxCount(1));
    }
}
