package net.jukoz.me.mixin.client;

import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyConstant(method="updateTargetedEntity", constant = @Constant(doubleValue = 3.0))
    public double injectedConstant(double constant) {
        return 6.5f + me$vantageBonus();
    }

    /**
     * The donor's vantage enchantment raises {@code entity_interaction_range}; 1.20.1 has no
     * engine support for it, so the bonus {@code PlayerMixin} stores on that attribute is added
     * to the entity targeting reach here.
     */
    @Unique
    private static double me$vantageBonus() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return 0.0;
        }
        EntityAttributeInstance instance =
                client.player.getAttributeInstance(EntityAttributesME.ENTITY_INTERACTION_RANGE.value());
        if (instance == null) {
            return 0.0;
        }
        EntityAttributeModifier modifier = instance.getModifier(EnchantmentsME.VANTAGE_MODIFIER_ID);
        return modifier == null ? 0.0 : modifier.getValue();
    }
}
