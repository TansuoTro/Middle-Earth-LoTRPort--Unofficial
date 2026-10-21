package net.jukoz.me.item.items.weapons;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.jukoz.me.compat.BluntWeapon;
import net.jukoz.me.item.utils.WeaponTypesME;
import net.minecraft.world.item.Tier;
import net.minecraft.world.entity.EquipmentSlot;

public class CustomSpearWeaponItem extends ReachWeaponItem {
    private static final float BASE_STRENGTH = 0.75f;
    private static final float CHARGE_STRENGTH = 1.75f;
    private static final int STRENGTH_CHARGE_TIME = 20;

    public float pullProgress = 0;
    public WeaponTypesME type;

    public CustomSpearWeaponItem(Tier toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.SPEAR, settings);
        this.type = WeaponTypesME.SPEAR;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!BluntWeapon.isBlunted(stack)) {
            // The donor's WEAPON component carries damagePerHit = 1; 1.20.1's Item.postHit does not
            // damage the stack, so the per-hit durability loss lives here.
            stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(net.minecraft.world.entity.EquipmentSlot.MAINHAND));
        }
        return true;
    }

    /*public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return InteractionResultHolder.success(itemStack, world.isClient());
    }

    @Override
    public UseAnim getUseAction(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 48000;
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
        pullProgress = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {
            float damage = getAttackDamage() * pullProgress;
            if(getAttackDamage() >= 2.0) {
                damage += 10.0f;
            }
            SpearEntity spearEntity = new SpearEntity(world, stack, user, damage);
            spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * pullProgress), 1.0f);
            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 0.7f);

            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }*/
}
