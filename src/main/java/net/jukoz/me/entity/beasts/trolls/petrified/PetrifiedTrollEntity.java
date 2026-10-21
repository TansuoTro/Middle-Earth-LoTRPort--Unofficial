package net.jukoz.me.entity.beasts.trolls.petrified;

import net.jukoz.me.item.items.CustomSpawnEggItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PetrifiedTrollEntity extends Mob {

    public PetrifiedTrollEntity(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
        this.setAiDisabled(true);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 30.0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setBodyYaw(this.getYaw());
    }

    @Override
    public boolean canBeLeashedBy(Player player) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld().isClient) return false;
        Entity entity = source.getAttacker();
        if(entity instanceof Player playerEntity) {
            if(playerEntity.isCreative()) {
                super.setHealth(0);
                return true;
            }
            if(playerEntity.getMainHandStack().getItem() instanceof PickaxeItem) {
                return super.damage(source, 10.0f);
            }
        }
        return false;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
    }

    @Override
    @Nullable
    public ItemStack getPickBlockStack() {
        CustomSpawnEggItem statue = CustomSpawnEggItem.forEntity(this.getType());
        if (statue == null) {
            return null;
        }
        return new ItemStack(statue);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
}
