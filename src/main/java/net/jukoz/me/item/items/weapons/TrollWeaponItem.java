package net.jukoz.me.item.items.weapons;
import net.jukoz.me.item.MEComponents;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;

public class TrollWeaponItem extends SwordItem {

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, (int) WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed,
                settings.component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
    }
}
