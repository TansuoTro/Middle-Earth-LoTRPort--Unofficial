package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
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

        public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        DyedColorComponent dyedColorComponent = MEComponents.get(stack, MEComponents.DYED_COLOR);
        if(dyedColorComponent == null) return;

        int targetColor = 4624722;
        int currentColor = dyedColorComponent.rgb();
        Holder<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
        }

        if(currentColor != targetColor) {
            Color colorA = new Color(MEComponents.get(stack, MEComponents.DYED_COLOR).rgb());
            Color colorB = new Color(targetColor);
            Color lerpColor = new Color(Mth.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                    Mth.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                    Mth.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
            currentColor = lerpColor.getRGB();
            MEComponents.set(stack, MEComponents.DYED_COLOR, new DyedColorComponent(currentColor));
        }
    }

    public static ItemStack applyBackAttachmentColor(ItemStack stack, ServerLevel world, Entity entity, BackAttachmentDataComponent backAttachmentDataComponent) {
        int targetColor;
        int currentColor = backAttachmentDataComponent.backAttachmentColor();
        Holder<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
            if(targetColor == 0) targetColor = DEFAULT_FOLIAGE;

            if(currentColor != targetColor) {
                Color colorA = new Color(currentColor);
                Color colorB = new Color(targetColor);
                Color lerpColor = new Color(Mth.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                        Mth.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                        Mth.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
                currentColor = lerpColor.getRGB();

                MEComponents.set(stack, MEComponents.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachmentDataComponent.backAttachment(), currentColor));
            }
        }
        return stack;
    }
}
