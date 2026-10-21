package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.nbt.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

/** Finite dungeon encounters driven by the donor's normal_config NBT. */
public class TrialSpawnerBlockEntity extends BlockEntity {
    private CompoundTag configuration = new CompoundTag();
    private final Set<UUID> participants = new HashSet<>();
    private final Map<UUID, BlockPos> alive = new HashMap<>();
    private final Map<UUID, Integer> absentTicks = new HashMap<>();
    private int spawned;
    private long nextSpawn;
    private long cooldownUntil;

    public TrialSpawnerBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.TRIAL_SPAWNER, pos, state); }

    @Override public void readNbt(CompoundTag nbt) {
        super.readNbt(nbt);
        configuration = nbt.copy();
        spawned = nbt.getInt("METotalSpawned");
        nextSpawn = nbt.getLong("MENextSpawn");
        cooldownUntil = nbt.getLong("MECooldownUntil");
        participants.clear(); alive.clear(); absentTicks.clear();
        for (Tag element : nbt.getList("MEParticipants", Tag.INT_ARRAY_TYPE)) participants.add(NbtUtils.toUuid(element));
        for (Tag element : nbt.getList("MEAlive", Tag.COMPOUND_TYPE)) {
            CompoundTag entry = (CompoundTag) element;
            if (entry.containsUuid("UUID")) alive.put(entry.getUuid("UUID"), BlockPos.fromLong(entry.getLong("Pos")));
        }
    }
    @Override protected void writeNbt(CompoundTag nbt) {
        super.writeNbt(nbt);
        for (String key : List.of("normal_config", "ominous_config", "spawn_data", "ejecting_loot_table", "required_player_range", "target_cooldown_length")) {
            if (configuration.contains(key)) nbt.put(key, configuration.get(key).copy());
        }
        nbt.putInt("METotalSpawned", spawned); nbt.putLong("MENextSpawn", nextSpawn); nbt.putLong("MECooldownUntil", cooldownUntil);
        ListTag players = new ListTag(); participants.stream().sorted().forEach(uuid -> players.add(NbtUtils.fromUuid(uuid))); nbt.put("MEParticipants", players);
        ListTag mobs = new ListTag();
        alive.forEach((uuid, position) -> { CompoundTag entry = new CompoundTag(); entry.putUuid("UUID", uuid); entry.putLong("Pos", position.asLong()); mobs.add(entry); });
        nbt.put("MEAlive", mobs);
    }

    private CompoundTag config() { return configuration.getCompound("normal_config"); }
    private int number(String key, int fallback, int limit) {
        return config().contains(key, Tag.NUMBER_TYPE) ? Mth.clamp((int)Math.ceil(config().getDouble(key)), 1, limit) : fallback;
    }
    private int total() { return number("total_mobs", 6, 128) + Math.max(0, participants.size()-1) * number("total_mobs_added_per_player", 2, 32); }
    private int simultaneous() { return number("simultaneous_mobs", 2, 32) + Math.max(0, participants.size()-1) * (config().contains("simultaneous_mobs_added_per_player") ? number("simultaneous_mobs_added_per_player", 1, 8) : 1); }
    private void phase(TrialSpawnerBlock.Phase phase) {
        if (getBlockState().get(TrialSpawnerBlock.PHASE) != phase) world.setBlock(pos, getBlockState().with(TrialSpawnerBlock.PHASE, phase), (Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS));
    }

    public static void tick(Level world, BlockPos pos, BlockState state, TrialSpawnerBlockEntity trial) {
        if (!(world instanceof ServerLevel server) || world.getDifficulty() == Difficulty.PEACEFUL) return;
        trial.serverTick(server);
    }
    private void serverTick(ServerLevel server) {
        long time = server.getTime();
        if (time < cooldownUntil) { phase(TrialSpawnerBlock.Phase.COOLDOWN); return; }
        int range = configuration.contains("required_player_range") ? Mth.clamp(configuration.getInt("required_player_range"), 1, 64) : 14;
        var nearby = server.getPlayers(player -> !player.isSpectator() && !player.isCreative() && player.distanceTo(Vec3.ofCenter(pos)) <= range*range);
        if (participants.isEmpty() && nearby.isEmpty()) { phase(TrialSpawnerBlock.Phase.WAITING_FOR_PLAYERS); return; }
        for (ServerPlayer player : nearby) if (participants.add(player.getUuid())) markDirty();
        // Missing entities in unloaded chunks remain in the encounter ledger. A short
        // grace period also prevents entity-load ordering from completing a trial.
        for (var iterator = alive.entrySet().iterator(); iterator.hasNext();) {
            var entry = iterator.next(); Entity mob = server.getEntity(entry.getKey());
            if (mob != null && mob.isAlive()) { entry.setValue(mob.blockPosition()); absentTicks.remove(entry.getKey()); }
            else if (mob != null || (server.isChunkLoaded(entry.getValue()) && absentTicks.merge(entry.getKey(), 1, Integer::sum) >= 100)) {
                iterator.remove(); absentTicks.remove(entry.getKey()); markDirty();
            }
        }
        if (spawned >= total() && alive.isEmpty()) { reward(server); return; }
        phase(TrialSpawnerBlock.Phase.ACTIVE);
        if (nearby.isEmpty() || spawned >= total() || alive.size() >= simultaneous() || time < nextSpawn) return;
        nextSpawn = time + number("ticks_between_spawn", 40, 1200);
        CompoundTag entityData = chooseEntity(server);
        if (entityData.isEmpty()) return;
        int spawnRange = number("spawn_range", 4, 16);
        for (int attempt=0; attempt<12; attempt++) {
            double x = pos.getX() + 0.5 + (server.random.nextDouble()-server.random.nextDouble())*spawnRange;
            double y = pos.getY() + server.random.nextInt(3)-1;
            double z = pos.getZ() + 0.5 + (server.random.nextDouble()-server.random.nextDouble())*spawnRange;
            Entity entity = EntityType.loadEntityWithPassengers(entityData.copy(), server, e -> { e.refreshPositionAndAngles(x,y,z,server.random.nextFloat()*360,0); return e; });
            if (entity == null) break;
            if (!server.isSpaceEmpty(entity)) continue;
            if (entity instanceof Mob mob) {
                // InitializationData is consumed by NPC readNbt. Unconfigured mobs need vanilla initialization.
                if (entityData.getSize() == 1) mob.initialize(server, server.getEffectiveDifficulty(mob.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                mob.setPersistenceRequired();
            }
            if (!server.spawnNewEntityAndPassengers(entity)) continue;
            alive.put(entity.getUuid(), entity.blockPosition()); spawned++; markDirty();
            server.spawnParticles(ParticleTypes.FLAME,x,y+0.5,z,15,0.3,0.5,0.3,0.01);
            break;
        }
    }

    private CompoundTag chooseEntity(ServerLevel server) {
        ListTag potentials = config().getList("spawn_potentials", Tag.COMPOUND_TYPE);
        int sum = 0; for (Tag element : potentials) sum += Math.max(1, ((CompoundTag)element).getInt("weight"));
        if (sum > 0) {
            int chosen = server.random.nextInt(sum);
            for (Tag element : potentials) {
                CompoundTag potential = (CompoundTag) element; chosen -= Math.max(1,potential.getInt("weight"));
                if (chosen < 0) return potential.getCompound("data").getCompound("entity");
            }
        }
        return configuration.getCompound("spawn_data").getCompound("entity");
    }

    private void reward(ServerLevel server) {
        ListTag tables = config().getList("loot_tables_to_eject", Tag.COMPOUND_TYPE);
        int totalWeight = 0; for (Tag e : tables) totalWeight += Math.max(1,((CompoundTag)e).getInt("weight"));
        for (UUID playerId : participants) {
            String tableName = configuration.getString("ejecting_loot_table");
            if (totalWeight > 0) {
                int roll = server.random.nextInt(totalWeight);
                for (Tag e : tables) { CompoundTag entry=(CompoundTag)e; roll-=Math.max(1,entry.getInt("weight")); if(roll<0){tableName=entry.getString("data");break;} }
            }
            ResourceLocation table = ResourceLocation.tryParse(tableName);
            if (table != null) {
                var builder = new LootParams.Builder(server).add(LootContextParams.ORIGIN, Vec3.ofCenter(pos));
                ServerPlayer player = server.getServer().getPlayerList().getPlayer(playerId);
                if (player != null) builder.add(LootContextParams.THIS_ENTITY, player).luck(player.getLuck());
                for (var stack : server.getServer().getLootData().getLootTable(table).generateLoot(builder.build(LootContextParamSets.CHEST))) Block.dropStack(server,pos.above(),stack);
            }
        }
        int delay = configuration.contains("target_cooldown_length") ? Math.max(1,configuration.getInt("target_cooldown_length")) : 36000;
        cooldownUntil = server.getTime()+delay;
        participants.clear(); alive.clear(); spawned=0; markDirty(); phase(TrialSpawnerBlock.Phase.COOLDOWN);
    }
}
