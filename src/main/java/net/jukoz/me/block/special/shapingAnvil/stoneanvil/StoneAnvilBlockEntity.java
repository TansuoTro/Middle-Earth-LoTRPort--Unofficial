package net.jukoz.me.block.special.shapingAnvil.stoneanvil;

import net.jukoz.me.compat.MEArmorTrim;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.ArmorTrimMaterial;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import java.util.Optional;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class StoneAnvilBlockEntity extends ShapingAnvilBlockEntity {

    public StoneAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_ANVIL, pos, state);
    }

    public StoneAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void bonk(ShapingAnvilBlockEntity entity, ServerLevel world, ItemStack tool) {
        ItemStack input = entity.getStack(0);

        ArmorTrim trim = MEArmorTrim.get(input);
        if (trim != null){
            Optional<ResourceKey<TrimMaterial>> armorTrimMaterialRegistryKey = trim.getMaterial().getKey();
            if(armorTrimMaterialRegistryKey.isPresent()) {
                ResourceKey<TrimMaterial> armorTrimMaterial = armorTrimMaterialRegistryKey.get();
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
