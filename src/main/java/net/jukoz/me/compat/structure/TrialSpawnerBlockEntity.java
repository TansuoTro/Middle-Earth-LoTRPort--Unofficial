package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.context.*;
import net.minecraft.nbt.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import java.util.*;

/** Finite dungeon encounters driven by the donor's normal_config NBT. */
public class TrialSpawnerBlockEntity extends BlockEntity {
    private NbtCompound configuration = new NbtCompound();
    private final Set<UUID> participants = new HashSet<>();
    private final Map<UUID, BlockPos> alive = new HashMap<>();
    private final Map<UUID, Integer> absentTicks = new HashMap<>();
    private int spawned;
    private long nextSpawn;
    private long cooldownUntil;

    public TrialSpawnerBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.TRIAL_SPAWNER, pos, state); }

    @Override public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        configuration = nbt.copy();
        spawned = nbt.getInt("METotalSpawned");
        nextSpawn = nbt.getLong("MENextSpawn");
        cooldownUntil = nbt.getLong("MECooldownUntil");
        participants.clear(); alive.clear(); absentTicks.clear();
        for (NbtElement element : nbt.getList("MEParticipants", NbtElement.INT_ARRAY_TYPE)) participants.add(NbtHelper.toUuid(element));
        for (NbtElement element : nbt.getList("MEAlive", NbtElement.COMPOUND_TYPE)) {
            NbtCompound entry = (NbtCompound) element;
            if (entry.containsUuid("UUID")) alive.put(entry.getUuid("UUID"), BlockPos.fromLong(entry.getLong("Pos")));
        }
    }
    @Override protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        for (String key : List.of("normal_config", "ominous_config", "spawn_data", "ejecting_loot_table", "required_player_range", "target_cooldown_length")) {
            if (configuration.contains(key)) nbt.put(key, configuration.get(key).copy());
        }
        nbt.putInt("METotalSpawned", spawned); nbt.putLong("MENextSpawn", nextSpawn); nbt.putLong("MECooldownUntil", cooldownUntil);
        NbtList players = new NbtList(); participants.stream().sorted().forEach(uuid -> players.add(NbtHelper.fromUuid(uuid))); nbt.put("MEParticipants", players);
        NbtList mobs = new NbtList();
        alive.forEach((uuid, position) -> { NbtCompound entry = new NbtCompound(); entry.putUuid("UUID", uuid); entry.putLong("Pos", position.asLong()); mobs.add(entry); });
        nbt.put("MEAlive", mobs);
    }

    private NbtCompound config() { return configuration.getCompound("normal_config"); }
    private int number(String key, int fallback, int limit) {
        return config().contains(key, NbtElement.NUMBER_TYPE) ? MathHelper.clamp((int)Math.ceil(config().getDouble(key)), 1, limit) : fallback;
    }
    private int total() { return number("total_mobs", 6, 128) + Math.max(0, participants.size()-1) * number("total_mobs_added_per_player", 2, 32); }
    private int simultaneous() { return number("simultaneous_mobs", 2, 32) + Math.max(0, participants.size()-1) * (config().contains("simultaneous_mobs_added_per_player") ? number("simultaneous_mobs_added_per_player", 1, 8) : 1); }
    private void phase(TrialSpawnerBlock.Phase phase) {
        if (getCachedState().get(TrialSpawnerBlock.PHASE) != phase) world.setBlockState(pos, getCachedState().with(TrialSpawnerBlock.PHASE, phase), Block.NOTIFY_ALL);
    }

    public static void tick(World world, BlockPos pos, BlockState state, TrialSpawnerBlockEntity trial) {
        if (!(world instanceof ServerWorld server) || world.getDifficulty() == Difficulty.PEACEFUL) return;
        trial.serverTick(server);
    }
    private void serverTick(ServerWorld server) {
        long time = server.getTime();
        if (time < cooldownUntil) { phase(TrialSpawnerBlock.Phase.COOLDOWN); return; }
        int range = configuration.contains("required_player_range") ? MathHelper.clamp(configuration.getInt("required_player_range"), 1, 64) : 14;
        var nearby = server.getPlayers(player -> !player.isSpectator() && !player.isCreative() && player.squaredDistanceTo(Vec3d.ofCenter(pos)) <= range*range);
        if (participants.isEmpty() && nearby.isEmpty()) { phase(TrialSpawnerBlock.Phase.WAITING_FOR_PLAYERS); return; }
        for (ServerPlayerEntity player : nearby) if (participants.add(player.getUuid())) markDirty();
        // Missing entities in unloaded chunks remain in the encounter ledger. A short
        // grace period also prevents entity-load ordering from completing a trial.
        for (var iterator = alive.entrySet().iterator(); iterator.hasNext();) {
            var entry = iterator.next(); Entity mob = server.getEntity(entry.getKey());
            if (mob != null && mob.isAlive()) { entry.setValue(mob.getBlockPos()); absentTicks.remove(entry.getKey()); }
            else if (mob != null || (server.isChunkLoaded(entry.getValue()) && absentTicks.merge(entry.getKey(), 1, Integer::sum) >= 100)) {
                iterator.remove(); absentTicks.remove(entry.getKey()); markDirty();
            }
        }
        if (spawned >= total() && alive.isEmpty()) { reward(server); return; }
        phase(TrialSpawnerBlock.Phase.ACTIVE);
        if (nearby.isEmpty() || spawned >= total() || alive.size() >= simultaneous() || time < nextSpawn) return;
        nextSpawn = time + number("ticks_between_spawn", 40, 1200);
        NbtCompound entityData = chooseEntity(server);
        if (entityData.isEmpty()) return;
        int spawnRange = number("spawn_range", 4, 16);
        for (int attempt=0; attempt<12; attempt++) {
            double x = pos.getX() + 0.5 + (server.random.nextDouble()-server.random.nextDouble())*spawnRange;
            double y = pos.getY() + server.random.nextInt(3)-1;
            double z = pos.getZ() + 0.5 + (server.random.nextDouble()-server.random.nextDouble())*spawnRange;
            Entity entity = EntityType.loadEntityWithPassengers(entityData.copy(), server, e -> { e.refreshPositionAndAngles(x,y,z,server.random.nextFloat()*360,0); return e; });
            if (entity == null) break;
            if (!server.isSpaceEmpty(entity)) continue;
            if (entity instanceof MobEntity mob) {
                // InitializationData is consumed by NPC readNbt. Unconfigured mobs need vanilla initialization.
                if (entityData.getSize() == 1) mob.initialize(server, server.getLocalDifficulty(mob.getBlockPos()), SpawnReason.TRIGGERED, null, null);
                mob.setPersistent();
            }
            if (!server.spawnNewEntityAndPassengers(entity)) continue;
            alive.put(entity.getUuid(), entity.getBlockPos()); spawned++; markDirty();
            server.spawnParticles(ParticleTypes.FLAME,x,y+0.5,z,15,0.3,0.5,0.3,0.01);
            break;
        }
    }

    private NbtCompound chooseEntity(ServerWorld server) {
        NbtList potentials = config().getList("spawn_potentials", NbtElement.COMPOUND_TYPE);
        int sum = 0; for (NbtElement element : potentials) sum += Math.max(1, ((NbtCompound)element).getInt("weight"));
        if (sum > 0) {
            int chosen = server.random.nextInt(sum);
            for (NbtElement element : potentials) {
                NbtCompound potential = (NbtCompound) element; chosen -= Math.max(1,potential.getInt("weight"));
                if (chosen < 0) return potential.getCompound("data").getCompound("entity");
            }
        }
        return configuration.getCompound("spawn_data").getCompound("entity");
    }

    private void reward(ServerWorld server) {
        NbtList tables = config().getList("loot_tables_to_eject", NbtElement.COMPOUND_TYPE);
        int totalWeight = 0; for (NbtElement e : tables) totalWeight += Math.max(1,((NbtCompound)e).getInt("weight"));
        for (UUID playerId : participants) {
            String tableName = configuration.getString("ejecting_loot_table");
            if (totalWeight > 0) {
                int roll = server.random.nextInt(totalWeight);
                for (NbtElement e : tables) { NbtCompound entry=(NbtCompound)e; roll-=Math.max(1,entry.getInt("weight")); if(roll<0){tableName=entry.getString("data");break;} }
            }
            Identifier table = Identifier.tryParse(tableName);
            if (table != null) {
                var builder = new LootContextParameterSet.Builder(server).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos));
                ServerPlayerEntity player = server.getServer().getPlayerManager().getPlayer(playerId);
                if (player != null) builder.add(LootContextParameters.THIS_ENTITY, player).luck(player.getLuck());
                for (var stack : server.getServer().getLootManager().getLootTable(table).generateLoot(builder.build(LootContextTypes.CHEST))) Block.dropStack(server,pos.up(),stack);
            }
        }
        int delay = configuration.contains("target_cooldown_length") ? Math.max(1,configuration.getInt("target_cooldown_length")) : 36000;
        cooldownUntil = server.getTime()+delay;
        participants.clear(); alive.clear(); spawned=0; markDirty(); phase(TrialSpawnerBlock.Phase.COOLDOWN);
    }
}
