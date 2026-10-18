package net.jukoz.me.item.items.armor;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class BackAttachmentItem extends Item {

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material) {
        super(settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));
    }
}
