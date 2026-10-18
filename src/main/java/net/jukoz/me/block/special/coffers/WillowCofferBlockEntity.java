package net.jukoz.me.block.special.coffers;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;

public class WillowCofferBlockEntity extends ChestBlockEntity {
    public WillowCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WILLOW_COFFER, pos, state);

        this.setInvStackList(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.of("willow_coffer").toTranslationKey("screen"));
    }
}
