package net.jukoz.me.item.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SmithingHammerItem extends Item {

    private final float attackSpeed;

    public SmithingHammerItem(Settings settings, Tier material, float speed) {
        super(settings.maxCount(1).maxDamage(material.getDurability()));
        this.attackSpeed = speed;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> base = super.getAttributeModifiers(slot);
        if (slot != EquipmentSlot.MAINHAND) {
            return base;
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(base);
        builder.put(Attributes.GENERIC_ATTACK_SPEED, new AttributeModifier(
                java.util.UUID.fromString("fa233e1c-4180-4865-b01b-bcce9785aca3"), "Tool modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public boolean canMine(ItemStack stack, BlockState state, Level world, BlockPos pos, LivingEntity user) {
        return !(user instanceof Player player && player.isCreative());
    }
}
