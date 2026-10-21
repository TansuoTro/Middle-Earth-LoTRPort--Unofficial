package net.jukoz.me.api.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.Pose;

public class EatBerriesTask extends Behavior<LivingEntity> {
    public EatBerriesTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), 90);
    }

    @Override
    protected boolean shouldRun(ServerLevel world, LivingEntity entity) {
        Vec3 rotationVector = entity.getRotationVector().normalize();
        BlockState state = world.getBlockState(entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ())));

        return state.isOf(Blocks.SWEET_BERRY_BUSH) && state.get(SweetBerryBushBlock.AGE) >= 2;
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, LivingEntity entity, long time) {
        return this.shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerLevel world, LivingEntity entity, long time) {
        Vec3 rotationVector = entity.getRotationVector().normalize();
        BlockPos pos = entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ()));

        BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(pos));
        world.spawnParticles(particles, (entity.getX() + pos.getX()) / 2, entity.getY() + 0.5, (entity.getZ() + pos.getZ()) / 2, 10, 0.1, 0.4, 0.1, 1);
    }

    @Override
    protected void run(ServerLevel world, LivingEntity entity, long time) {
        entity.setPose(Pose.DIGGING);
    }

    @Override
    protected void finishRunning(ServerLevel world, LivingEntity entity, long time) {
        world.playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
        entity.setPose(Pose.STANDING);

        Vec3 rotationVector = entity.getRotationVector().normalize();
        BlockPos pos = entity.getBlockPos().add((int)Math.round(rotationVector.getX()), 0, (int)Math.round(rotationVector.getZ()));

        if(world.getBlockState(pos).isOf(Blocks.SWEET_BERRY_BUSH) && world.getBlockState(pos).get(SweetBerryBushBlock.AGE) >= 2) {
            if(world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                world.setBlockState(pos, world.getBlockState(pos).with(SweetBerryBushBlock.AGE, 1));
            }

            ItemStack itemStack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
        }

        // Set cooldown to 3000t = 2min30s
        entity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, 3000);
    }
}
