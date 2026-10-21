package net.jukoz.me.item.items.weapons.ranged;

import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;
import net.jukoz.me.item.utils.RangedWeaponTypesME;

import java.util.function.Predicate;

public class CustomCrossbowWeaponItem extends CrossbowItem {
    public RangedWeaponTypesME type;

    public CustomCrossbowWeaponItem(RangedWeaponTypesME type, Item.Settings settings) {
        super(settings.maxDamage(type.durability));
        this.type = type;
    }

    public Predicate<ItemStack> getHeldProjectiles() {
        return BOW_PROJECTILES;
    }

    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public Component getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Component.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
