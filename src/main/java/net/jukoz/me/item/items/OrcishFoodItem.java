package net.jukoz.me.item.items;

import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
                user.addStatusEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 1));
                user.addStatusEffect(new MobEffectInstance(MobEffects.NAUSEA, 20 * 10, 0));

            }
        }
        return super.finishUsing(stack, world, user);
    }
}
