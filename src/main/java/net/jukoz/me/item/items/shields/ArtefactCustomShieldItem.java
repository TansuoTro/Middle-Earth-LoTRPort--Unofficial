package net.jukoz.me.item.items.shields;

import net.minecraft.world.item.Item;
import net.jukoz.me.item.utils.ShieldTypesME;
import net.minecraft.world.item.ItemStack;

public class ArtefactCustomShieldItem extends CustomShieldItem {

    public ArtefactCustomShieldItem(ShieldTypesME type, Item.Properties settings) {
        super(type, settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if(stack.getDamage() == stack.getMaxDamage() - 1) {
            return false;
        } else if( stack.getDamage() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
