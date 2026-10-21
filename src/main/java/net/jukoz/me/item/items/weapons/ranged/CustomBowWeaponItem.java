package net.jukoz.me.item.items.weapons.ranged;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.RangedWeaponTypesME;
import java.util.function.Predicate;

public class CustomBowWeaponItem extends BowItem {
    public RangedWeaponTypesME type;

    public CustomBowWeaponItem(RangedWeaponTypesME type, Item.Properties settings) {
        super(((net.jukoz.me.item.MESettings) settings).maxDamage(type.durability)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
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
            return Component.translatable(this.getTranslationKey()).formatted(ChatFormatting.GOLD);
        }
        return super.getName(stack);
    }
}
