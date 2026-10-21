package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

/** Configured key/loot vault with a durable per-player reward ledger. */
public class VaultBlockEntity extends BlockEntity {
    private CompoundTag config = new CompoundTag();
    private final Set<UUID> rewarded = new HashSet<>();

    public VaultBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.VAULT, pos, state); }

    @Override public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        config = nbt.getCompound("config").copy();
        rewarded.clear();
        for (Tag element : nbt.getList("MERewardedPlayers", Tag.INT_ARRAY_TYPE)) {
            rewarded.add(NbtUtils.toUuid(element));
        }
    }
    @Override protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        nbt.put("config", config.copy());
        ListTag players = new ListTag();
        rewarded.stream().sorted().forEach(uuid -> players.add(NbtUtils.fromUuid(uuid)));
        nbt.put("MERewardedPlayers", players);
    }

    public boolean unlock(Player player, InteractionHand hand) {
        if (!(world instanceof ServerLevel server) || player.isSpectator() || rewarded.contains(player.getUuid())) return false;
        ItemStack key = ItemStack.fromNbt(config.getCompound("key_item"));
        ItemStack held = player.getItemInHand(hand);
        if (key.isEmpty() || !held.is(key.getItem()) || held.getCount() < key.getCount()) return false;
        ResourceLocation tableId = ResourceLocation.tryParse(config.getString("loot_table"));
        if (tableId == null) return false;
        var parameters = new LootParams.Builder(server)
                .add(LootContextParams.ORIGIN, Vec3.ofCenter(pos))
                .add(LootContextParams.THIS_ENTITY, player).luck(player.getLuck()).build(LootContextParamSets.CHEST);
        var loot = server.getServer().getLootData().getLootTable(tableId).generateLoot(parameters);
        if (loot.isEmpty()) return false;
        rewarded.add(player.getUuid());
        if (!player.isCreative()) held.decrement(key.getCount());
        // Mark the ledger before giving rewards; repeated interaction cannot duplicate it.
        markDirty();
        for (ItemStack stack : loot) {
            if (!player.getInventory().insertStack(stack)) player.dropItem(stack, false);
        }
        server.setBlock(pos, getBlockState().with(VaultBlock.PHASE, VaultBlock.Phase.ACTIVE), (Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS));
        server.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundSource.BLOCKS, 1.0f, 0.8f);
        return true;
    }
}
