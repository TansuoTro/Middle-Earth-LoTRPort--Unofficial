package net.jukoz.me.block.special.coffers;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;

public class ChestnutCofferBlockEntity extends ChestBlockEntity {
    public ChestnutCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHESTNUT_COFFER, pos, state);

        this.setHeldStacks(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.of("chestnut_coffer").toTranslationKey("screen"));
    }
}
