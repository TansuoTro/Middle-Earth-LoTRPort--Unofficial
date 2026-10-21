package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;

public class PineCofferBlockEntity extends ChestBlockEntity {
    public PineCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PINE_COFFER, pos, state);

        this.setInvStackList(NonNullList.withSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("pine_coffer").toLanguageKey("screen"));
    }
}
