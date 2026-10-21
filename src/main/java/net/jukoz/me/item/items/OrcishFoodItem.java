package net.jukoz.me.item.items;

import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OrcishFoodItem extends Item {
    public OrcishFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, Level world, LivingEntity user) {
        if(world.isClient) return super.finishUsing(stack, world, user);

        if(user instanceof Player playerEntity) {
            RaceType raceType = RaceUtil.getRaceType(playerEntity);
            if(raceType != RaceType.GOBLIN && raceType != RaceType.SNAGA && raceType != RaceType.ORC && raceType != RaceType.URUK) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 20 * 30, 1));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0));

            }
        }
        return super.finishUsing(stack, world, user);
    }
}
