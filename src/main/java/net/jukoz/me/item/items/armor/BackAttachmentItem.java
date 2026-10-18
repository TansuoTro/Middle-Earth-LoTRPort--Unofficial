package net.jukoz.me.item.items.armor;

import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class BackAttachmentItem extends Item {

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material) {
        super(settings.armor(material.material(), ArmorItem.Type.CHESTPLATE).maxCount(1));
    }
}
