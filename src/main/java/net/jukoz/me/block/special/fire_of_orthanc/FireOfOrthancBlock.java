package net.jukoz.me.block.special.fire_of_orthanc;

import net.minecraft.world.InteractionResult;
import net.jukoz.me.item.DecorativeItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.Explosion;
import org.jetbrains.annotations.Nullable;

public class FireOfOrthancBlock extends Block {
    public static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 12, 14);

    public FireOfOrthancBlock(Settings settings) {
        super(settings);
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isOf(DecorativeItemsME.TORCH_OF_ORTHANC)) {
            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            explode(world, pos, player);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            Item item = stack.getItem();
            if (!player.isCreative()) {
                if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    stack.damage(1, player, (p) -> p.sendEquipmentBreakStatus(hand == InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND));
                } else {
                    stack.decrement(1);
                }
            }
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return InteractionResult.SUCCESS;
        }
    }

    public void explode(Level world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter, true);
            world.spawnEntity(fireOfOrthancEntity);
            world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        } else {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.25F, 0.8F);
        }
    }

    public void onDestroyedByExplosion(Level world, BlockPos pos, Explosion explosion) {
        if (!world.isClient) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, explosion.getCausingEntity(), false);
            int i = fireOfOrthancEntity.getFuse();
            fireOfOrthancEntity.setFuse((short)(world.random.nextInt(i / 2) + i / 2));
            world.spawnEntity(fireOfOrthancEntity);
        }
    }

    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }
}
