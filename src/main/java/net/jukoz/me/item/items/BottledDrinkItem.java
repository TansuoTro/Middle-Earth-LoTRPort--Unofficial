package net.jukoz.me.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Bottled drink (birch water, maple syrup).
 *
 * <p>The 1.21.8 donor describes these with a {@code ConsumableComponent} plus
 * {@code useRemainder(GLASS_BOTTLE)}: a two second drink that leaves the glass bottle behind,
 * and for birch water a {@code RemoveEffectsConsumeEffect} curing poison. 1.20.1 has neither
 * component, so the same behaviour lives here: the drink action and its length, the optional
 * drink sound override, the poison cure and the bottle handed back on
 * {@link #finishUsing} — the same shape as vanilla {@code HoneyBottleItem}.
 */
public class BottledDrinkItem extends Item {
    private static final int DRINK_TIME = 40; // donor consumeSeconds(2.0F)

    private final boolean curePoison;
    @Nullable
    private final SoundEvent drinkSound;

    public BottledDrinkItem(Settings settings, boolean curePoison, @Nullable SoundEvent drinkSound) {
        super(settings);
        this.curePoison = curePoison;
        this.drinkSound = drinkSound;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (!world.isClient && this.curePoison) {
            user.removeStatusEffect(StatusEffects.POISON);
        }
        // donor useRemainder(Items.GLASS_BOTTLE)
        if (result.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.getInventory().insertStack(bottle)) {
                player.dropItem(bottle, false);
            }
        }
        return result;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return DRINK_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return this.drinkSound == null ? super.getDrinkSound() : this.drinkSound;
    }

    @Override
    public SoundEvent getEatSound() {
        return this.drinkSound == null ? super.getEatSound() : this.drinkSound;
    }
}
