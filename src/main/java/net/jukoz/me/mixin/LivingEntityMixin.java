package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.item.items.shields.CustomSiegeShieldItem;
import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.jukoz.me.network.packets.S2C.PacketLivingEntityData;
import net.jukoz.me.resources.datas.biome_events.BiomeEventDataLookup;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.jukoz.me.compat.MEEnchants;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private static boolean stackTraceLock = false;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract ItemStack getStackInHand(InteractionHand hand);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract @NotNull ItemStack getWeaponStack();

    @Shadow
    protected abstract float getMovementSpeed(float slipperiness);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("RETURN"), cancellable = true)
    public void getAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
        if (entity != null) {
            ItemStack chestplate = this.getEquippedStack(EquipmentSlot.CHEST);
            if(!chestplate.isEmpty()) {
                Holder<Enchantment> enchantmentRegistryEntry = entity.getWorld().getRegistryManager()
                        .get(Registries.ENCHANTMENT).getEntry(EnchantmentsME.STEALTHY_TRAIL).orElseThrow();
                boolean hasEnchant = MEEnchants.has(chestplate, enchantmentRegistryEntry);
                int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry.value(), chestplate);
                if(hasEnchant) {
                    double original = cir.getReturnValue();
                    double viewDistance = original + Math.max(-0.9f, -0.2f * level);
                    cir.setReturnValue(viewDistance);
                }
            }
        }
    }

    @Inject(method = "getEquippedStack", at = @At("HEAD"), cancellable = true)
    public void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if(stackTraceLock) return;
        stackTraceLock = true;
        boolean twoHanded = false;
        ItemStack stackMainHand = this.getStackInHand(InteractionHand.MAIN_HAND);
        ItemStack stackOffHand = this.getStackInHand(InteractionHand.OFF_HAND);

        if(stackMainHand != null){
            if ((stackMainHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackMainHand.getItem()).type.twoHanded))
                    || (stackMainHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackMainHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }
        if(stackOffHand != null){
            if ((stackOffHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackOffHand.getItem()).type.twoHanded))
                    || (stackOffHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackOffHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }

        if (slot == EquipmentSlot.OFFHAND) {
            if (twoHanded) {
                cir.setReturnValue(ItemStack.EMPTY);
                cir.cancel();
            }
        }
        stackTraceLock = false;
    }

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"), index = 1, argsOnly = true)
    public final StatusEffectInstance addStatusEffect(StatusEffectInstance effect) {
        if(effect.getEffectType().getCategory().equals(StatusEffectCategory.HARMFUL)) {
            float ailmentLevel = 0;
            Level world = this.getWorld();
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.HEAD), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.CHEST), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.LEGS), world);
            ailmentLevel += getAilmentProtectionLevel(this.getEquippedStack(EquipmentSlot.FEET), world);
            if(ailmentLevel > 0) {
                float scale = 0.08f * ailmentLevel;
                scale = 1 - Math.min(0.8f, scale);
                int scaledDuration = Math.max(1, Math.round(effect.getDuration() * scale));
                effect = new StatusEffectInstance(effect.getEffectType(), scaledDuration, effect.getAmplifier(),
                        effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon());
            }
        }
        return effect;
    }

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("TAIL"))
    public final void addStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if(!this.getWorld().isClient) {
            for (ServerPlayer player : ((ServerLevel) this.getWorld()).getPlayers()) {
                ServerPlayNetworking.send(player, new PacketLivingEntityData(this.getId(), effect));
            }
        }
    }

    private static int getAilmentProtectionLevel(ItemStack itemStack, Level world) {
        int level = 0;
        Holder<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().get(Registries.ENCHANTMENT)
                .getEntry(EnchantmentsME.AILMENT_PROTECTION).orElseThrow();
        boolean hasEnchant = MEEnchants.has(itemStack, enchantmentRegistryEntry);
        if(hasEnchant) {
            level = EnchantmentHelper.getLevel(enchantmentRegistryEntry.value(), itemStack);
        }
        return level;
    }

    @Inject(at = @At("HEAD"), method = "drop")
    protected void drop(ServerLevel world, DamageSource damageSource, CallbackInfo callbackInfo) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                this.equipStack(equipmentSlot, ItemStack.EMPTY);
            }
        }
    }

    @Inject(method = "getMovementSpeed()F", at = @At("RETURN"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity npcEntity){
            float currentValue = cir.getReturnValue();
            float modifier = 1f;
            float fightingModifier = 1f;
            if(getControllingPassenger().getVehicle() instanceof HorseEntity){
                currentValue = 0.5f;
                fightingModifier = 2f;
            }
            if(npcEntity.isFighting()){
                cir.setReturnValue(Math.max(currentValue * fightingModifier, 0.5f));
            }
            cir.setReturnValue(Math.max(currentValue * modifier, 0.25f));
        }
    }
    @WrapOperation(method = "applyClimbingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean ScaffoldingDescendLogic(BlockState state, Block block, Operation<Boolean> original) {
        return original.call(state, block)
                || block == Blocks.SCAFFOLDING && state.isOf(ModDecorativeBlocks.REINFORCED_SCAFFOLDING);
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void onRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        if (!(((Entity)this) instanceof LivingEntity livingEntity)) {
            return;
        }
        BiomeEventDataLookup.removeEntity(livingEntity.getType(), livingEntity.getUuid());
    }
}
