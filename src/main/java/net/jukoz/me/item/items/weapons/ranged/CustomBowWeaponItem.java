package net.jukoz.me.item.items.weapons.ranged;
import net.jukoz.me.item.MEComponents;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.RangedWeaponTypesME;

import java.util.function.Predicate;

public class CustomBowWeaponItem extends BowItem {
    public RangedWeaponTypesME type;

    public CustomBowWeaponItem(RangedWeaponTypesME type, Item.Settings settings) {
        super(((net.jukoz.me.item.MESettings) settings).maxDamage(type.durability)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
    }

    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
