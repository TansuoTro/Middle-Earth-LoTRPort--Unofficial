package net.jukoz.me.block.special.structureManager.nest;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.structureManager.StructureManagerBlockEntity;
import net.jukoz.me.block.special.structureManager.features.StructureManagerService;
import net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenData;
import net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_nest";

    private enum SyncedData {
        MANAGER_ID("%s.ManagerId".formatted(ID)),
        NEST_ID("%s.NestId".formatted(ID)),
        SPAWN_RADIUS("%s.SpawnRadius".formatted(ID)),
        IS_ENABLED("%s.IsEnabled".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }
    @Nullable
    protected ResourceLocation managerId;
    @Nullable
    protected ResourceLocation nestId;
    protected int spawnRadius;
    protected boolean isEnabled;

    protected int fails = 0;
    boolean initialized = false;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
    }

    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, Player player) {
        return new StructureNestScreenHandler(syncId, playerInventory, new StructureNestScreenData(this.pos,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                this.spawnRadius,
                this.isEnabled));
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        ReadView view = new ReadView(nbt);
        Optional<ResourceLocation> managerId = view.read(SyncedData.MANAGER_ID.name, ResourceLocation.CODEC);
        managerId.ifPresent(identifier -> this.managerId = identifier);
        Optional<ResourceLocation> nestId = view.read(SyncedData.NEST_ID.name, ResourceLocation.CODEC);
        nestId.ifPresent(identifier -> this.nestId = identifier);
        spawnRadius = view.getInt(SyncedData.SPAWN_RADIUS.name, 0);
        isEnabled = view.getBoolean(SyncedData.IS_ENABLED.name, false);
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        WriteView view = new WriteView(nbt);
        if(nestId != null)
            view.put(SyncedData.NEST_ID.name, ResourceLocation.CODEC, this.nestId);
        if(managerId != null)
            view.put(SyncedData.MANAGER_ID.name, ResourceLocation.CODEC, this.managerId);
        view.put(SyncedData.SPAWN_RADIUS.name, Codec.INT, this.spawnRadius);
        view.put(SyncedData.IS_ENABLED.name, Codec.BOOL, this.isEnabled);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setStructureManagerId(ResourceLocation structureManagerId) {
        this.managerId = structureManagerId;
        updateListeners();
    }

    public void setStructureNestId(ResourceLocation structureNestId) {
        this.nestId = structureNestId;
        updateListeners();
    }

    public void setSpawnRadius(int newRadius) {
        this.spawnRadius = newRadius;
        updateListeners();
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureNestBlockEntity entity) {
        entity.tickEvent(world, blockState);
    }

    private void tickEvent(Level world, BlockState blockState) {
        if(world.isClient)
            return;

        if(!blockState.get(StructureNestBlock.ENABLED)) {
            fails = 0;
            return;
        }

        if(managerId == null || nestId == null || world.getTickOrder() % 20 != 0) // every 1 seconds
            return;

        StructureManagerBlockEntity structureManagerBlockEntity = StructureManagerService.getClosest(world, pos, 20);
        if(structureManagerBlockEntity == null) {
            fails++;
        }
        else {
            if(structureManagerBlockEntity.subscribeNest(this.pos, this.managerId, this.nestId, this.spawnRadius))
            {
                world.breakBlock(pos, false);
                world.removeBlockEntity(pos);
                initialized = true;
                updateListeners();
            } else {
                fails++;
            }
        }
        if(fails >= 12) {
            world.breakBlock(pos, false);
            world.removeBlockEntity(pos);
            updateListeners();
        }
    }

    @Override
    public void writeScreenOpeningData(net.minecraft.server.level.ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf) {
        net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenData.PACKET_CODEC.encode(buf,
                new net.jukoz.me.gui.structuremanager.structurenest.StructureNestScreenData(this.pos,
                        java.util.Optional.ofNullable(this.managerId), java.util.Optional.ofNullable(this.nestId), this.spawnRadius, this.isEnabled));
    }
}
