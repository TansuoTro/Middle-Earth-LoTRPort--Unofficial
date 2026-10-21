package net.jukoz.me.item.items.weapons;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.BluntWeapon;
import net.jukoz.me.item.dataComponents.SneakAttackDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;
import net.minecraft.client.model.geom.builders.UVPair;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final ResourceLocation ENTITY_INTERACTION_RANGE_MODIFIER_ID = new ResourceLocation(MiddleEarth.MOD_ID, "entity_interaction_range");
    private static final int SNEAK_ATTACK_TIME = 40;

    public CustomDaggerWeaponItem(Tier toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.DAGGER, settings);
        this.type = WeaponTypesME.DAGGER;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof Player playerEntity) {
            int timer = 0;
            SneakAttackDataComponent sneakAttackDataComponent = MEComponents.get(stack, MEComponents.SNEAK_ATTACK_DATA);
            if(sneakAttackDataComponent != null) timer = sneakAttackDataComponent.timer();

            if(playerEntity.isSneaking()) {
                MEComponents.set(stack, MEComponents.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(Math.min(timer + 1, SNEAK_ATTACK_TIME)));
            } else {
                MEComponents.set(stack, MEComponents.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(0));
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!BluntWeapon.isBlunted(stack)) {
            // The donor's WEAPON component carries damagePerHit = 1; 1.20.1's Item.postHit does not
            // damage the stack, so the per-hit durability loss lives here.
            stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        SneakAttackDataComponent sneakAttackDataComponent = MEComponents.get(stack, MEComponents.SNEAK_ATTACK_DATA);
        if(sneakAttackDataComponent != null) MEComponents.set(stack, MEComponents.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(0));
        return true;
    }

    public static boolean canSneakAttack(ItemStack stack) {
        SneakAttackDataComponent sneakAttackDataComponent = MEComponents.get(stack, MEComponents.SNEAK_ATTACK_DATA);
        if(sneakAttackDataComponent != null) {
            int timer = sneakAttackDataComponent.timer();
            return (timer >= SNEAK_ATTACK_TIME);
        } else return false;
    }

    public static boolean canBackStab(Entity target, Entity attacker) {
        UVPair direction = new UVPair((float) target.getRotationVector().x, (float) target.getRotationVector().z).normalize();
        Vector3f attackerPosDifference = target.getPos().add(attacker.getPos().multiply(-1)).toVector3f();
        UVPair attackerDirection = new UVPair(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.1f;
    }
}
