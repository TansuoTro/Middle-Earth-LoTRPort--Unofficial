package net.jukoz.me.item.items.weapons;

import net.jukoz.me.item.MEComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import org.jetbrains.annotations.Nullable;

public class HotComponentProperty implements BooleanProperty {
    public static final MapCodec<HotComponentProperty> CODEC = MapCodec.unit(new HotComponentProperty());

    public MapCodec<HotComponentProperty> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        TemperatureDataComponent temperatureDataComponent = MEComponents.get(stack, MEComponents.TEMPERATURE_DATA);
        return temperatureDataComponent != null && temperatureDataComponent.temperature() > 0;
    }
}
