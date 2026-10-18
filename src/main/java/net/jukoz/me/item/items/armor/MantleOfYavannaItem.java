package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;

import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MantleOfYavannaItem extends BackAttachmentItem {
    private static final float COLOR_LERP = 0.06f;
    private static final int DEFAULT_FOLIAGE = 5875248;

    public MantleOfYavannaItem(Settings settings, ExtendedArmorMaterial material) {
        super(settings.maxCount(1), material);
    }

        public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        DyedColorComponent dyedColorComponent = MEComponents.get(stack, MEComponents.DYED_COLOR);
        if(dyedColorComponent == null) return;

        int targetColor = 4624722;
        int currentColor = dyedColorComponent.rgb();
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
        }

        if(currentColor != targetColor) {
            Color colorA = new Color(MEComponents.get(stack, MEComponents.DYED_COLOR).rgb());
            Color colorB = new Color(targetColor);
            Color lerpColor = new Color(MathHelper.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                    MathHelper.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                    MathHelper.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
            currentColor = lerpColor.getRGB();
            MEComponents.set(stack, MEComponents.DYED_COLOR, new DyedColorComponent(currentColor));
        }
    }

    public static ItemStack applyBackAttachmentColor(ItemStack stack, ServerWorld world, Entity entity, BackAttachmentDataComponent backAttachmentDataComponent) {
        int targetColor;
        int currentColor = backAttachmentDataComponent.backAttachmentColor();
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
            if(targetColor == 0) targetColor = DEFAULT_FOLIAGE;

            if(currentColor != targetColor) {
                Color colorA = new Color(currentColor);
                Color colorB = new Color(targetColor);
                Color lerpColor = new Color(MathHelper.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                        MathHelper.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                        MathHelper.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
                currentColor = lerpColor.getRGB();

                MEComponents.set(stack, MEComponents.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachmentDataComponent.backAttachment(), currentColor));
            }
        }
        return stack;
    }
}
