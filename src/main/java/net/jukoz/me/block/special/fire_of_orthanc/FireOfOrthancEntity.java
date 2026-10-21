package net.jukoz.me.block.special.fire_of_orthanc;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;

public class FireOfOrthancEntity extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> FUSE;
    private static final EntityDataAccessor<BlockState> BLOCK_STATE;
    private static final int DEFAULT_FUSE = 16;
    public static final float EXPLOSION_FORCE = 12.31f;
    public static final BlockState DEFAULT_BLOCK_STATE = ModDecorativeBlocks.FIRE_OF_ORTHANC.defaultBlockState();
    protected boolean chainReaction = false;
    @Nullable
    private LivingEntity causingEntity;

    public FireOfOrthancEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public FireOfOrthancEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter, boolean instant) {
        this(EntitiesME.FIRE_OF_ORTHANC, world);
        this.setPosition(x, y, z);
        double d = world.random.nextDouble() * 6.2831854820251465;
        this.setVelocity(-Math.sin(d) * 0.02, 0.20000000298023224, -Math.cos(d) * 0.02);

        if(instant) {
            this.setFuse(6);
            chainReaction = true;
        }
        else this.setFuse(DEFAULT_FUSE);

        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.causingEntity = igniter;
    }

    @Override
    protected void initDataTracker() {
        this.entityData.define(FUSE, DEFAULT_FUSE);
        this.entityData.define(BLOCK_STATE, ModDecorativeBlocks.FIRE_OF_ORTHANC.defaultBlockState());
    }

    public void explode() {
        this.level().createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), EXPLOSION_FORCE, Level.ExplosionInteraction.TNT);
        tryKillOwner();
    }

    private void tryKillOwner() {
        Entity owner = this.getOwner();
        Level world = this.level();
        if(owner instanceof LivingEntity ownerEntity && world instanceof ServerLevel serverWorld) {
            if(ownerEntity instanceof Player playerEntity){
                if(!playerEntity.isCreative()){
                    playerEntity.kill();
                }
            } else {
                ownerEntity.kill();
            }
        }
    }

    @Override
    protected void readCustomDataFromNbt(CompoundTag nbt) {
        ReadView view = new ReadView(nbt);
        this.setFuse(view.getShort("fuse", (short) 0));
        this.setBlock((BlockState)view.read("block_state", BlockState.CODEC).orElse(DEFAULT_BLOCK_STATE));

    }

    @Override
    protected void writeCustomDataToNbt(CompoundTag nbt) {
        WriteView view = new WriteView(nbt);
        view.putShort("fuse", (short)this.getFuse());
        view.put("block_state", BlockState.CODEC, this.getBlockState());
    }

    protected double getGravity() {
        return 0.04f;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public void tick() {
        this.setVelocity(this.getVelocity().add(0.0, -this.getGravity(), 0.0));
        this.move(MoverType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide && !chainReaction) {
                for(int j = 0; j < 4; j++) {
                    this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.8f, this.getZ(),
                            (Math.random() - 0.5f) * 0.3f, 0.4f, (Math.random() - 0.5f) * 0.5f);
                }
            }
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return causingEntity;
    }

    public void setFuse(int fuse) {
        this.entityData.set(FUSE, fuse);
    }

    public int getFuse() {
        return this.entityData.get(FUSE);
    }

    public void setBlock(BlockState state) {
        this.entityData.set(BLOCK_STATE, state);
    }

    public BlockState getBlockState() {
        return this.entityData.get(BLOCK_STATE);
    }

    static {
        FUSE = SynchedEntityData.registerData(FireOfOrthancEntity.class, EntityDataSerializers.INTEGER);
        BLOCK_STATE = SynchedEntityData.registerData(FireOfOrthancEntity.class, EntityDataSerializers.BLOCK_STATE);
    }
}
