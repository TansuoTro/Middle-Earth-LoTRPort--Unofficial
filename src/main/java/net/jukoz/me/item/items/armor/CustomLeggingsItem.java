package net.jukoz.me.item.items.armor;

import net.minecraft.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class CustomLeggingsItem extends ArmorItem {

    public CustomLeggingsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), ArmorItem.Type.LEGGINGS).maxCount(1));
    }
}
