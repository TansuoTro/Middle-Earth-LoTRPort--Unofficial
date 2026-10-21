package net.jukoz.me.item.items.weapons.ranged;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.jukoz.me.entity.projectile.WebbedEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;

public class PebbleItem extends Item{
    public static final float DAMAGE = 2f;
    private static final float BASE_STRENGTH = 0.6f;
    private static final float CHARGE_STRENGTH = 0.9f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength
    public PebbleItem(Settings settings) {
        super(settings);
    }

    public net.minecraft.util.TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return net.minecraft.util.TypedActionResult.success(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player)) {
            return;
        }
        Player playerEntity = (Player)user;
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        float percentage = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {
            PebbleEntity pebbleEntity = new PebbleEntity(world, user, DAMAGE * percentage);
            pebbleEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.spawnEntity(pebbleEntity);
            world.playSoundFromEntity(null, pebbleEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        return;
    }
}
