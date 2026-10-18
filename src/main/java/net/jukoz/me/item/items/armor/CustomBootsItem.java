package net.jukoz.me.item.items.armor;

import net.minecraft.item.equipment.EquipmentType;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.BOOTS).maxCount(1));
    }
}
