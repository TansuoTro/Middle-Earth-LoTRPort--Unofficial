package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;

public class SpruceCofferBlockEntity extends ChestBlockEntity {
    public SpruceCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPRUCE_COFFER, pos, state);

        this.setInvStackList(NonNullList.withSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("spruce_coffer").toLanguageKey("screen"));
    }
}
