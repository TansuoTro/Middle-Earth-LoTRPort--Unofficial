package net.jukoz.me.item.items.weapons;
import net.jukoz.me.item.MEComponents;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;

public class TrollWeaponItem extends Item {

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(settings.sword(toolMaterial, WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
    }
}
