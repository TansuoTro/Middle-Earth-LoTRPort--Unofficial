package net.jukoz.me.item.items.weapons.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;

import java.util.List;


public class ArtefactUtils {
    public static <T extends Entity> boolean isInBound(Level world, Entity entity, Class<T> entityClass, int range){
        return !world.getEntitiesByClass(entityClass, entity.getBoundingBox().expand(range), Entity::isAlive).isEmpty();
    }

    public static List<Component> getArtefactLore(String item){
        return List.of(
                Component.translatable("tooltip.%s.%s_lore_0".formatted(MiddleEarth.MOD_ID, item)).formatted(Formatting.GRAY),
                Component.translatable("tooltip.%s.%s_lore_1".formatted(MiddleEarth.MOD_ID, item)).formatted(Formatting.GRAY)
        );
    }
}
