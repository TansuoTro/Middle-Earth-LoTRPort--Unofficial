package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.*;
import net.minecraft.nbt.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import java.util.*;

/** Configured key/loot vault with a durable per-player reward ledger. */
public class VaultBlockEntity extends BlockEntity {
    private NbtCompound config = new NbtCompound();
    private final Set<UUID> rewarded = new HashSet<>();

    public VaultBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.VAULT, pos, state); }

    @Override public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        config = nbt.getCompound("config").copy();
        rewarded.clear();
        for (NbtElement element : nbt.getList("MERewardedPlayers", NbtElement.INT_ARRAY_TYPE)) {
            rewarded.add(NbtHelper.toUuid(element));
        }
    }
    @Override protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("config", config.copy());
        NbtList players = new NbtList();
        rewarded.stream().sorted().forEach(uuid -> players.add(NbtHelper.fromUuid(uuid)));
        nbt.put("MERewardedPlayers", players);
    }

    public boolean unlock(PlayerEntity player, Hand hand) {
        if (!(world instanceof ServerWorld server) || player.isSpectator() || rewarded.contains(player.getUuid())) return false;
        ItemStack key = ItemStack.fromNbt(config.getCompound("key_item"));
        ItemStack held = player.getStackInHand(hand);
        if (key.isEmpty() || !held.isOf(key.getItem()) || held.getCount() < key.getCount()) return false;
        Identifier tableId = Identifier.tryParse(config.getString("loot_table"));
        if (tableId == null) return false;
        var parameters = new LootContextParameterSet.Builder(server)
                .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                .add(LootContextParameters.THIS_ENTITY, player).luck(player.getLuck()).build(LootContextTypes.CHEST);
        var loot = server.getServer().getLootManager().getLootTable(tableId).generateLoot(parameters);
        if (loot.isEmpty()) return false;
        rewarded.add(player.getUuid());
        if (!player.isCreative()) held.decrement(key.getCount());
        // Mark the ledger before giving rewards; repeated interaction cannot duplicate it.
        markDirty();
        for (ItemStack stack : loot) {
            if (!player.getInventory().insertStack(stack)) player.dropItem(stack, false);
        }
        server.setBlockState(pos, getCachedState().with(VaultBlock.PHASE, VaultBlock.Phase.ACTIVE), Block.NOTIFY_ALL);
        server.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0f, 0.8f);
        return true;
    }
}
