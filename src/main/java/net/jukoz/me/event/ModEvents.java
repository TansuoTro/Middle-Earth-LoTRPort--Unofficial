package net.jukoz.me.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.dimension.ModDimensions;
import java.util.Objects;
import net.jukoz.me.compat.MEEnchants;
import net.jukoz.me.compat.MEDirection;
import com.mojang.math.Axis;
import net.minecraft.nbt.NbtUtils;

public class ModEvents {
    private static final String GOT_STARTER_ITEM = MiddleEarth.MOD_ID + ".received_starter_item";

    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayer player = serverPlayNetworkHandler.getPlayer();

            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                return;
            if(data != null && data.getRace() != null){
                RaceUtil.reset(player);
                boolean isInMiddleEarth = ModDimensions.isInMiddleEarth(player.getWorld());
                if(isInMiddleEarth){
                    RaceUtil.initializeRace(player);
                } else if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    RaceUtil.initializeRace(player);
                }
            }

            if(!player.getCommandTags().contains(GOT_STARTER_ITEM)) {
                ItemStack starterItem = new ItemStack(ResourceItemsME.PLAYER_BOOK);
                player.getInventory().offerOrDrop(starterItem);
                player.addCommandTag(GOT_STARTER_ITEM);
            }
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if(entity instanceof Player playerEntity) {
                ItemStack stack = Objects.requireNonNull(playerEntity.getStackInHand(playerEntity.getActiveHand()));
                Holder<Enchantment> enchantmentRegistryEntry = world.getRegistryManager()
                        .get(Registries.ENCHANTMENT).getEntry(EnchantmentsME.BEHEADING).orElseThrow();
                boolean hasEnchant = MEEnchants.has(stack, enchantmentRegistryEntry);

                if (hasEnchant) {
                    ItemStack drop = ItemStack.EMPTY;
                    if(killedEntity instanceof Player killedPlayer) {
                        drop = new ItemStack(Items.PLAYER_HEAD);
                        net.minecraft.nbt.NbtUtils.writeGameProfile(drop.getOrCreateSubNbt("SkullOwner"), killedPlayer.getGameProfile());
                    }
                    if(!drop.isEmpty()) {
                        killedEntity.dropStack(drop);
                    }
                }
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getStackInHand(playerEntity.getActiveHand()));
            
            Holder<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().get(Registries.ENCHANTMENT).getEntry(EnchantmentsME.HEWING).orElseThrow();
            boolean hasEnchant = MEEnchants.has(stack, enchantmentRegistryEntry);
            int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry.value(), stack);
            float hardness = blockState.getBlock().getHardness();

            if (hasEnchant) {
                if (!playerEntity.isCreative() && !playerEntity.isSneaking()) {
                    
                    if (stack.isSuitableFor(blockState)){
                        if (MEDirection.facing(playerEntity) == Direction.DOWN || MEDirection.facing(playerEntity) == Direction.UP){
                            if (level == 1){
                                level1BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            }
                        } else {
                            if (level == 1){
                                level1Break(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2Break(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3Break(world, playerEntity, blockPos, stack, hardness);
                            }
                        }
                    }
                }
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getStackInHand(playerEntity.getActiveHand()));
            
            Holder<Enchantment> enchantmentRegistryEntry = world.getRegistryManager()
                    .get(Registries.ENCHANTMENT).getEntry(EnchantmentsME.TREE_FELLER).orElseThrow();
            boolean hasEnchant = MEEnchants.has(stack, enchantmentRegistryEntry);
            int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry.value(), stack);
            float hardness = blockState.getBlock().getHardness();

            if (hasEnchant) {
                if (!playerEntity.isCreative() && !playerEntity.isSneaking()) {
                    
                    if (stack.isSuitableFor(blockState)){
                        int[] blockCount = new int[]{16};
                        if(level == 2) blockCount[0] = 64;
                        else if(level == 3) blockCount[0] = 256;
                        breakTopLogs(world, playerEntity, blockPos, stack, hardness, blockCount);
                    }
                }
            }
        });
    }

    private static void breakTopLogs(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness, int[] attempts) {
        BlockPos offsetY = blockPos.offset(Direction.Axis.Y, 1);
        for(int z = -2; z < 2; z++) {
            BlockPos offsetZ = offsetY.offset(Direction.Axis.Z, z);
            for(int x = -2; x < 2; x++) {
                BlockPos offset = offsetZ.offset(Direction.Axis.X, x);
                if(world.getBlockState(offset).isIn(BlockTags.LOGS)) {
                    if(attempts[0]-- <= 0) return;
                    breakTopLogs(world, player, new BlockPos(offset), stack, hardness, attempts);
                }
            }
        }
        breakAndDamage(world, player, blockPos, stack, hardness);
    }

    private static void level1BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        float yaw = player.getYaw();
        if((yaw >= -45 && yaw <= 45) || yaw <= -135 || yaw >= 135){
            breakAndDamage(world, player, blockPos.offset(Direction.NORTH), stack, hardness);
            breakAndDamage(world, player, blockPos.offset(Direction.SOUTH), stack, hardness);
        } else {
            breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
            breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);
        }
    }

    private static void level2BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        breakAndDamage(world, player, blockPos.offset(Direction.NORTH), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.SOUTH), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
    }

    private static void level3BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosNorth = blockPos.offset(Direction.NORTH);
        BlockPos blockPosSouth = blockPos.offset(Direction.SOUTH);

        breakAndDamage(world, player, blockPosNorth, stack, hardness);
        breakAndDamage(world, player, blockPosNorth.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosNorth.offset(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);

        breakAndDamage(world, player, blockPosSouth, stack, hardness);
        breakAndDamage(world, player, blockPosSouth.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosSouth.offset(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
    }

    private static void level1Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level2Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);

        breakAndDamage(world, player, blockPos.offset(MEDirection.facing(player).rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(MEDirection.facing(player).rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level3Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosUp.offset(MEDirection.facing(player).rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPosUp.offset(MEDirection.facing(player).rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(MEDirection.facing(player).rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(MEDirection.facing(player).rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
        breakAndDamage(world, player, blockPosDown.offset(MEDirection.facing(player).rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPosDown.offset(MEDirection.facing(player).rotateYCounterclockwise()), stack, hardness);
    }

    private static void breakAndDamage(Level world, Player player, BlockPos blockpos, ItemStack stack, float hardness){
        
        BlockState blockState = world.getBlockState(blockpos);

        if (!blockState.isAir()) {
            if (stack.isSuitableFor(blockState)) {
                if (blockState.getBlock().getHardness() <= hardness) {
                    BlockEntity blockEntity = blockState.hasBlockEntity()
                            ? world.getBlockEntity(blockpos)
                            : null;

                    Block.dropStacks(blockState, world, blockpos, blockEntity, player, stack);
                    world.breakBlock(blockpos, false, player);
                    stack.postMine(world, blockState, blockpos, player);
                }
            }
        }
    }
}
