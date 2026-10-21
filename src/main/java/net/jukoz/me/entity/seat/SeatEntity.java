package net.jukoz.me.entity.seat;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.SeatBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SeatEntity extends Entity {    public SeatEntity(EntityType<?> type, Level world) {
        super(type, world);
    }    @Override
    protected void initDataTracker() {
    }    protected Item asItem() {
        return null;
    }    @Override
    public void tick() {
        if(!this.level().isClientSide){
            Level world = this.level();
            BlockPos pos = this.getBlockPos();
            if (!this.isVehicle() && this.level().getBlockState(pos).is(TagKey.create(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "seat")))){
                world.setBlock(this.getBlockPos(), world.getBlockState(pos).with(SeatBlock.OCCUPIED, false));
                this.discard();
            } else if(!this.isVehicle() || !this.level().getBlockState(pos).is(TagKey.create(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "seat")))){
                this.discard();
            }
        }
    }    @Override
    protected void readCustomDataFromNbt(CompoundTag nbt) {
    }    @Override
    protected void writeCustomDataToNbt(CompoundTag nbt) {
    }    @Override
    protected void removePassenger(Entity passenger) {
        Level world = passenger.getWorld();
        if (!world.isClientSide){
            BlockPos pos = this.getBlockPos();
            passenger.requestTeleport(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            super.removePassenger(passenger);
        }
    }

}
