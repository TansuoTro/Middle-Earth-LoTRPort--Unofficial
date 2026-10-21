package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;

public class ChestnutCofferBlockEntity extends ChestBlockEntity {
    public ChestnutCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHESTNUT_COFFER, pos, state);

        this.setInvStackList(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("chestnut_coffer").toTranslationKey("screen"));
    }
}
