package net.jukoz.me.block.special.shapingAnvil.stoneanvil;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;

import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;

import java.util.Optional;

public class StoneAnvilBlockEntity extends ShapingAnvilBlockEntity {

    public StoneAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_ANVIL, pos, state);
    }

    public StoneAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void bonk(ShapingAnvilBlockEntity entity, ServerWorld world, ItemStack tool) {
        ItemStack input = entity.getStack(0);

        ArmorTrim trim = MEArmorTrim.get(input);
        if (trim != null){
            Optional<RegistryKey<ArmorTrimMaterial>> armorTrimMaterialRegistryKey = trim.material().getKey();
            if(armorTrimMaterialRegistryKey.isPresent()) {
                RegistryKey<ArmorTrimMaterial> armorTrimMaterial = armorTrimMaterialRegistryKey.get();
                if (!armorTrimMaterial.getValue().equals(MiddleEarth.of("bronze")) &&
                        !armorTrimMaterial.getValue().equals(MiddleEarth.of("crude")) &&
                        !armorTrimMaterial.getValue().equals(MiddleEarth.of("iron"))) {
                    return;
                }
            }
        }

        super.bonk(entity, world, tool);
    }
}
