package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.Item;
import net.jukoz.me.item.dataComponents.ArmorTierDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class ArmorItem extends Item {

    private final ExtendedArmorMaterial material;

    public ArmorItem(ExtendedArmorMaterial material, Settings settings) {
        super(((net.jukoz.me.item.MESettings) settings).component(MEComponents.ARMOR_TIER_DATA, new ArmorTierDataComponent(material.tier())));
        this.material = material;
    }

    public ExtendedArmorMaterial getMaterial() {
        return material;
    }
}
