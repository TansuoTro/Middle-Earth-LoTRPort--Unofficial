package net.jukoz.me.item.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SkewerItem extends Item {
    public SkewerItem(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, Level world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if(user instanceof Player){
            if(((Player) user).getAbilities().creativeMode){
                return itemStack;
            }
            ((Player) user).giveItemStack(Items.STICK.getDefaultInstance());
            return itemStack;
        } else {
            return itemStack;
        }
    }
}
