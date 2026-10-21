package net.jukoz.me.resources.datas.npc_types;

import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.resources.datas.npc_types.data.WeightedGearData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NpcUtil {
    public static void equipAll(LivingEntity entity, WeightedGearData data) {
        entity.equipStack(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        entity.equipStack(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        entity.equipStack(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        entity.equipStack(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));

        ItemStack mainHandItem = data.get(EquipmentSlot.MAINHAND);
        ItemStack offhandItem = data.get(EquipmentSlot.OFFHAND);

        if(mainHandItem != null && mainHandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else if(offhandItem != null && offhandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        } else {
            entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, offhandItem);
        }
    }
}
