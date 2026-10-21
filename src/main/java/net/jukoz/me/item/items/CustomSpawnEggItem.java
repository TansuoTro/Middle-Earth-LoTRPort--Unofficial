package net.jukoz.me.item.items;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUsageContext;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CustomSpawnEggItem extends Item {
    private static final Map<EntityType<? extends Mob>, CustomSpawnEggItem> SPAWN_EGGS = Maps.newIdentityHashMap();
    private final EntityType<?> type;
    public CustomSpawnEggItem(EntityType<? extends Mob> type, Settings settings) {
        super(settings);
        this.type = type;
        SPAWN_EGGS.put(type, this);
    }

    @Override
    public InteractionResult useOnBlock(UseOnContext context) {
        Level world = context.getWorld();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
        EntityType<?> entityType2 = this.getEntityType(itemStack.getNbt());
        if (entityType2.spawnFromItemStack((ServerLevel)world, itemStack, context.getPlayer(), blockPos2, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
            itemStack.decrement(1);
            world.emitGameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = SpawnEggItem.raycast(world, user, ClipContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (!(world instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemStack);
        }
        BlockHitResult blockHitResult2 = blockHitResult;
        BlockPos blockPos = blockHitResult2.getBlockPos();
        if (!(world.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos, blockHitResult2.getSide(), itemStack)) {
            return InteractionResultHolder.fail(itemStack);
        }
        EntityType<?> entityType = this.getEntityType(itemStack.getNbt());
        Object entity = entityType.spawnFromItemStack((ServerLevel)world, itemStack, user, blockPos, MobSpawnType.SPAWN_EGG, false, false);
        if (entity == null) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        world.emitGameEvent((Entity)user, GameEvent.ENTITY_PLACE, ((Entity)entity).getPos());
        return InteractionResultHolder.consume(itemStack);
    }

    public boolean isOfSameEntityType(@Nullable CompoundTag nbt, EntityType<?> type) {
        return Objects.equals(this.getEntityType(nbt), type);
    }

    @Nullable
    public static CustomSpawnEggItem forEntity(@Nullable EntityType<?> type) {
        return SPAWN_EGGS.get(type);
    }

    public static Iterable<CustomSpawnEggItem> getAll() {
        return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
    }

    public EntityType<?> getEntityType(@Nullable CompoundTag nbt) {
        CompoundTag nbtCompound;
        if (nbt != null && nbt.contains("EntityTag", Tag.COMPOUND_TYPE) && (nbtCompound = nbt.getCompound("EntityTag")).contains("id", Tag.STRING_TYPE)) {
            return EntityType.get(nbtCompound.getString("id")).orElse(this.type);
        }
        return this.type;
    }

    @Override
    public FeatureFlagSet getRequiredFeatures() {
        return this.type.getRequiredFeatures();
    }

    public Optional<Mob> spawnBaby(Player user, Mob entity, EntityType<? extends Mob> entityType, ServerLevel world, Vec3 pos, ItemStack stack) {
        if (!this.isOfSameEntityType(stack.getNbt(), entityType)) {
            return Optional.empty();
        }
        Mob mobEntity = entity instanceof AgeableMob ? ((AgeableMob)entity).createChild(world, (AgeableMob)entity) : entityType.create(world);
        if (mobEntity == null) {
            return Optional.empty();
        }
        mobEntity.setBaby(true);
        if (!mobEntity.isBaby()) {
            return Optional.empty();
        }
        mobEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0f, 0.0f);
        world.spawnEntityAndPassengers(mobEntity);
        if (stack.hasCustomName()) {
            mobEntity.setCustomName(stack.getName());
        }
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        return Optional.of(mobEntity);
    }
}
