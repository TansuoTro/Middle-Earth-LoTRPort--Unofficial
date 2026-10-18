package net.jukoz.me.block.special.plate;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.jukoz.me.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity implements SingleStackInventory {
    private ItemStack food = ItemStack.EMPTY;
    @Nullable
    private Identifier lootTable;
    private long lootTableSeed;
    private boolean blockPlaced = false;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLATE, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("placed", blockPlaced);

        if (!this.writeLootTableToData(nbt) && !this.food.isEmpty()) {
            nbt.put("item", this.food.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.food = ItemStack.EMPTY;
        this.blockPlaced = nbt.getBoolean("placed");
        if (!this.readLootTableFromData(nbt)) {
            this.food = ItemStack.fromNbt(nbt.getCompound("item"));
        }
    }

    public void setLootTable(Identifier lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
    }

    private boolean readLootTableFromData(NbtCompound nbt) {
        this.lootTable = nbt.contains("LootTable") ? Identifier.tryParse(nbt.getString("LootTable")) : null;
        this.lootTableSeed = nbt.getLong("LootTableSeed");
        return this.lootTable != null;
    }

    private boolean writeLootTableToData(NbtCompound nbt) {
        if (this.lootTable == null) {
            return false;
        }
        nbt.putString("LootTable", this.lootTable.toString());
        if (this.lootTableSeed != 0L) {
            nbt.putLong("LootTableSeed", this.lootTableSeed);
        }
        return true;
    }

    public void setBlockPlaced() {
        blockPlaced = true;
    }

    public boolean isBlockPlaced() {
        return blockPlaced;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PlateBlockEntity blockEntity) {
        if(blockEntity.blockPlaced) {
            blockEntity.generateItem((ServerWorld) world);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public boolean canPlayerUse(net.minecraft.entity.player.PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.getStack();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return this.removeStack(slot);
    }

    @Override
    public ItemStack getStack() {
        return food;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.setStack(stack);
    }

    @Override
    public void setStack(ItemStack stack) {
        this.food = stack;
        update();
    }

    public void generateItem(ServerWorld world) {
        if (this.lootTable != null && blockPlaced) {
            LootTable lootTable = world.getServer().getLootManager().getLootTable(this.lootTable);
            LootContextParameterSet lootContext = new LootContextParameterSet.Builder(world).build(LootContextTypes.EMPTY);
            ObjectArrayList<ItemStack> lootList = lootTable.generateLoot(lootContext, this.lootTableSeed);
            ItemStack itemLoot = ItemStack.EMPTY;
            if(!lootList.isEmpty()) itemLoot = lootList.get(world.getRandom().nextInt(lootList.size()));

            this.food = itemLoot;
            this.lootTable = null;
            blockPlaced = false;
            update();
        }
    }

    public void update() {
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
