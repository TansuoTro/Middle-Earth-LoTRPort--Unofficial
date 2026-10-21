package net.jukoz.me.item.items.shields;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.MESettings;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.ShieldTypesME;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import java.util.HashSet;

public class CustomShieldItem extends ShieldItem {
    public final ShieldTypesME type;
    public final static HashSet<CustomShieldItem> instances = new HashSet<>();

    public CustomShieldItem(ShieldTypesME type, Item.Settings settings) {
        super(((settings instanceof MESettings meSettings ? meSettings : new MESettings())
                .maxCount(1).maxDamage(type.durability)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name))));
        this.type = type;
        instances.add(this);
    }
}
