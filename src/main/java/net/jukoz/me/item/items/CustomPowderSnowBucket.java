package net.jukoz.me.item.items;

import net.jukoz.me.item.ModToolItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemUsageContext;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

public class CustomPowderSnowBucket extends SolidBucketItem {
    public CustomPowderSnowBucket(Block block, SoundEvent placeSound, Settings settings) {
        super(block, placeSound, settings);
    }

    /*@Override
    public InteractionResult useOnBlock(UseOnContext context) {
        InteractionResult actionResult = super.useOnBlock(context);
        Player playerEntity = context.getPlayer();
        if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
            InteractionHand hand = context.getHand();
            playerEntity.setStackInHand(hand, ModToolItems.BRONZE_BUCKET.getDefaultStack());
        }

        return actionResult;
    }*/
}
