package net.jukoz.me.mixin;

import net.jukoz.me.utils.LootModifiers;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {

    public FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/ReloadableRegistries$Lookup;getLootTable(Lnet/minecraft/registry/ResourceKey;)Lnet/minecraft/loot/LootTable;"))
    public ResourceKey<LootTable> onUseFishingRod(ResourceKey<LootTable> key) {
        if(!getWorld().isClient) {
            if(ModDimensions.isInMiddleEarth(getWorld())) {
                return LootModifiers.FISHING_LOOT_TABLE;
            }
        }
        return key;
    }
}
