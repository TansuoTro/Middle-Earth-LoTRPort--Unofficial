package net.jukoz.me.item.items.weapons.artefacts;
import net.jukoz.me.entity.EntityAttributesME;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.jukoz.me.item.items.weapons.CustomSpearWeaponItem;
import net.jukoz.me.item.utils.WeaponTypesME;

public class ArtefactCustomSpearWeaponItem extends CustomSpearWeaponItem {

    public ArtefactCustomSpearWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
        this.type = WeaponTypesME.SPEAR;
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
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() == stack.getMaxDamage() - 1) {
            BluntWeapon.blunt(stack);
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            if (stack.getDamage() == stack.getMaxDamage() - 1) {
                BluntWeapon.blunt(stack);
            }
        }
        return true;
    }
}
