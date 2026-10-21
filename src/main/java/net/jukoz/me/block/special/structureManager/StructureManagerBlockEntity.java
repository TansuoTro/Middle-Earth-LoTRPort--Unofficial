package net.jukoz.me.block.special.structureManager;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.structureManager.features.SpawnNestManager;
import net.jukoz.me.block.special.structureManager.features.StructureManagerService;
import net.jukoz.me.block.special.structureManager.features.StructureNestList;
import net.jukoz.me.gui.structuremanager.StructureManagerScreenData;
import net.jukoz.me.gui.structuremanager.StructureManagerScreenHandler;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_manager";

    private enum SyncedData {
        ENABLED("%s.Enabled".formatted(ID)),
        TO_INITIALIZE("%s.ToInitialize".formatted(ID)),
        SPAWN_NEST_LIST("%s.Nests".formatted(ID)),
        IDENTIFIER("%s.ResourceLocation".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }

    // Synced Data
    private boolean enabled;
    private boolean toInitialize;
    @Nullable
    protected ResourceLocation structureManagerIdentifier;
    private StructureNestList structureNestList;
    private boolean wellnessChecked;

    boolean firstTick = true;
    // Runtime
    private StructureManagerData managerData;
    private boolean worldWasSet = false;
    private boolean registered = false;
    private final Map<SpawnNestManager, List<BlockPos>> bedsByNest = new LinkedHashMap<>();

    @Override
    public void markRemoved() {
        StructureManagerService.unregister(this);
        super.markRemoved();
    }

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        // Default values
        this.enabled = false;
        this.toInitialize = false;
        this.structureManagerIdentifier = null;
        this.structureNestList = null;
        this.firstTick = true;
        this.wellnessChecked = false;
    }

    // region [Basic Overrides]
    public void updateData(ResourceLocation structureManagerId, boolean isActive, boolean toInitialize) {
        this.structureManagerIdentifier = structureManagerId;
        this.enabled = isActive;
        this.toInitialize = toInitialize;
        updateListeners();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, Player player) {
        return new StructureManagerScreenHandler(syncId, playerInventory,
                new StructureManagerScreenData(this.pos, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier))
        );
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        WriteView view = new WriteView(nbt);
        view.putBoolean(SyncedData.ENABLED.name, this.enabled);
        view.putBoolean(SyncedData.TO_INITIALIZE.name, this.toInitialize);
        if(structureManagerIdentifier != null)
            view.put(SyncedData.IDENTIFIER.name, ResourceLocation.CODEC, this.structureManagerIdentifier);
        if(structureNestList != null)
            view.put(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC, this.structureNestList);
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        ReadView view = new ReadView(nbt);
        this.enabled = view.getBoolean(SyncedData.ENABLED.name, false);
        this.toInitialize = view.getBoolean(SyncedData.TO_INITIALIZE.name, false);
        view.read(SyncedData.IDENTIFIER.name, ResourceLocation.CODEC)
                .ifPresent(x -> structureManagerIdentifier = x);
        view.read(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC)
                .ifPresent(x -> structureNestList = x);
    }
    // endregion


    @Override
    public void setWorld(Level world) {
        super.setWorld(world);
        worldWasSet = true;
    }

    public void showAllEntities() {
        if(structureNestList == null)
            return;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            for(UUID uuid : nest.getEntityUuids()){
                if((world instanceof net.minecraft.server.level.ServerLevel sw ? sw.getEntity(uuid) : null) instanceof LivingEntity livingEntity){
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10*20));
                }
            }
        }
    }

    public void respawnAllEntities() {
        if(structureNestList == null)
            return;
        if(world == null || world.isClient)
            return;
        for(var nest : structureNestList.getManagers()){
            nest.forceRespawn(managerData, (ServerLevel) world, pos);
        }
    }

    public boolean subscribeNest(BlockPos nestPos, ResourceLocation managerId, ResourceLocation nestId, int spawnRadius) {
        if(!enabled || managerId == null || structureManagerIdentifier == null || managerData == null || managerId.compareTo(this.structureManagerIdentifier) != 0)
            return false;

        SpawnNestNodeData data = managerData.getNpcSpawnNest(nestId);
        if (data == null || this.structureNestList == null) return false;
        if (this.structureNestList.getManagers().stream().anyMatch(nest -> nest.getOriginPos().equals(nestPos))) return true;
        SpawnNestManager manager = new SpawnNestManager(data, nestPos, spawnRadius);
        this.structureNestList.addNest(manager);
        markDirty();
        return true;
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;
        if(entity.getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity
                && !blockEntity.isRemoved() && blockEntity.structureNestList != null){
            blockEntity.structureNestList.computeDeath(entity);
            blockEntity.world.markDirty(pos);
        }
    }

    private void tickEvent(Level world, BlockPos blockPos, BlockState blockState) {
        if (world.isClient) return;
        if(!world.isClient && worldWasSet){
            tryToInitializeManager(world);
            this.worldWasSet = false;
        }

        if (!world.isClient && !this.registered) {
            StructureManagerService.register(this);
            this.registered = true;
        }

        if(!enabled)
            return;

        ServerLevel serverWorld = (ServerLevel) world;
        if(structureNestList == null)
            return;

        long timeOfDay = serverWorld.getTime() % 24000;
        long gameTick = serverWorld.getTime();
        if(((timeOfDay > 0 && timeOfDay < 11000) || (timeOfDay > 12000 && timeOfDay < 23000)) && wellnessChecked)
            wellnessChecked = false;

        boolean haveToDoWellnessCheck = ((timeOfDay > 11000 && timeOfDay < 12000) || (timeOfDay >= 23000)) && !wellnessChecked;
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(managerData == null)
                managerData = StructureManagerService.getStructureManagerData(serverWorld, structureManagerIdentifier);
            if(haveToDoWellnessCheck){
                data.doWellnessCheck(managerData, serverWorld, blockPos);
            }
            data.tick(managerData, gameTick, serverWorld, blockPos);
        }
        if(haveToDoWellnessCheck && !wellnessChecked)
            wellnessChecked = true;
        if (gameTick % 200 == 0) redistributeBeds();
    }

    private void tryToInitializeManager(Level world){
        if(world.isClient)
            return;
        if(!toInitialize || enabled)
            return;
        if(structureManagerIdentifier == null)
            return;

        this.managerData = StructureManagerService.getStructureManagerData(world, structureManagerIdentifier);
        if(structureNestList == null)
            this.structureNestList = new StructureNestList();
        if(managerData == null) {
            return;
        };

        this.toInitialize = false;
        this.enabled = true;
    }

    public void setInitializationState(boolean toInitialize) {
        this.toInitialize = toInitialize;
        updateListeners();
    }

    public void setActiveState(boolean activate) {
        this.enabled = activate;
        updateListeners();
    }

    public void setStructureManagerId(ResourceLocation identifier) {
        this.structureManagerIdentifier = identifier;
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public void fetchBeds(){
        bedsByNest.clear();
        if (!(world instanceof ServerLevel) || structureNestList == null || structureManagerIdentifier == null) return;
        StructureManagerData data = StructureManagerService.getStructureManagerData(world, structureManagerIdentifier);
        if (data == null) return;
        for (SpawnNestManager nest : structureNestList.getManagers()) {
            SpawnNestNodeData node = data.getNpcSpawnNest(nest.getId());
            if (node == null) continue;
            int radius = Math.max(0, node.getBedRadius());
            BlockPos origin = nest.getOriginPos();
            List<BlockPos> beds = new ArrayList<>();
            for (BlockPos candidate : BlockPos.iterate(origin.add(-radius, -5, -radius), origin.add(radius, 5, radius))) {
                if (!world.isChunkLoaded(candidate)) continue;
                BlockState state = world.getBlockState(candidate);
                if (state.getBlock() instanceof BedBlock && state.get(BedBlock.PART) == BedPart.HEAD) {
                    beds.add(candidate.toImmutable());
                }
            }
            beds.sort(Comparator.comparingDouble(origin::getSquaredDistance));
            bedsByNest.put(nest, beds);
        }
    }

    public void redistributeBeds(){
        if (!(world instanceof ServerLevel serverWorld)) return;
        fetchBeds();
        Set<BlockPos> claimed = new HashSet<>();
        bedsByNest.forEach((nest, beds) -> nest.retainBeds(beds, claimed, serverWorld));
        bedsByNest.forEach((nest, beds) -> nest.assignBeds(beds, claimed, serverWorld));
        markDirty();
    }

    @Override
    public void writeScreenOpeningData(net.minecraft.server.level.ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf) {
        net.jukoz.me.gui.structuremanager.StructureManagerScreenData.PACKET_CODEC.encode(buf,
                new net.jukoz.me.gui.structuremanager.StructureManagerScreenData(this.pos, this.enabled, this.toInitialize, java.util.Optional.ofNullable(this.structureManagerIdentifier)));
    }
}
