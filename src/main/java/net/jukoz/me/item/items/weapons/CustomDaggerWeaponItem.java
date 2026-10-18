package net.jukoz.me.item.items.weapons;

import net.jukoz.me.item.MEComponents;

import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.SneakAttackDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = new Identifier(MiddleEarth.MOD_ID, "entity_interaction_range");
    private static final int SNEAK_ATTACK_TIME = 40;

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.DAGGER, settings);
        this.type = WeaponTypesME.DAGGER;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof PlayerEntity playerEntity) {
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
        Vector2f direction = new Vector2f((float) target.getRotationVector().x, (float) target.getRotationVector().z).normalize();
        Vector3f attackerPosDifference = target.getPos().add(attacker.getPos().multiply(-1)).toVector3f();
        Vector2f attackerDirection = new Vector2f(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.1f;
    }
}
