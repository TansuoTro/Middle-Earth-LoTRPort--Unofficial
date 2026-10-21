package net.jukoz.me.item.items.armor;

import net.minecraft.world.item.ArmorItem;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material.material(), ArmorItem.Type.BOOTS, settings.maxCount(1));
    }
}
