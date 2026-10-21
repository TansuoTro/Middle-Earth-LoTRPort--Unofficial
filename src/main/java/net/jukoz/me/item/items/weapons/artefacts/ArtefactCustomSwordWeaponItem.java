package net.jukoz.me.item.items.weapons.artefacts;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.world.item.Tier;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.item.items.weapons.CustomSwordWeaponItem;

public class ArtefactCustomSwordWeaponItem extends CustomSwordWeaponItem {

    public ArtefactCustomSwordWeaponItem(Tier toolMaterial, Item.Properties settings) {
        super(toolMaterial, settings);
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

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() == stack.getMaxDamage() - 1) {
            BluntWeapon.blunt(stack);
        }
    
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClientSide && state.defaultDestroyTime(world, pos) != 0.0F) {
            if (stack.getDamage() == stack.getMaxDamage() - 1) {
                BluntWeapon.blunt(stack);
            }
        }
        return true;
    }
}
