package net.jukoz.me.item.items;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HotChickenFoodItem extends Item {
    public HotChickenFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, Level world, LivingEntity user) {
        if(world.isClient) return super.finishUsing(stack, world, user);

        if(user instanceof Player playerEntity) {
            if (!stack.isEmpty() && MEComponents.get(stack, MEComponents.TEMPERATURE_DATA) != null) {
                int temperature = MEComponents.get(stack, MEComponents.TEMPERATURE_DATA).temperature();
                float percentage = 1;
                if(stack.getItem().getFoodComponent() != null) {
                    percentage = 15 + (float)stack.getItem().getFoodComponent().getHunger() / 13;
                }
                playerEntity.setFireTicks((int) (temperature * percentage));
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
