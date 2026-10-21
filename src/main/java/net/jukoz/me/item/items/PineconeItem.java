package net.jukoz.me.item.items;

import net.jukoz.me.entity.projectile.pinecone.PineconeEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PineconeItem extends PebbleItem{
    public static final float DAMAGE = 2f;
    private static final float BASE_STRENGTH = 0.6f;
    private static final float CHARGE_STRENGTH = 0.9f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength
    public PineconeItem(Settings settings) {
        super(settings);
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
            PineconeEntity pineconeEntity = new PineconeEntity(world, user, DAMAGE * percentage);
            pineconeEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.spawnEntity(pineconeEntity);
            world.playSoundFromEntity(null, pineconeEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }
}
