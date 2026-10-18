package net.jukoz.me.item.items.armor;

import net.minecraft.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), ArmorItem.Type.BOOTS).maxCount(1));
    }
}
