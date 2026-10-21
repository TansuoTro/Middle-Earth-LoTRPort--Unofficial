package net.jukoz.me.entity.barrel;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import java.util.function.Supplier;

public class BarrelEntity extends ChestBoat {
    public BarrelEntity(EntityType<? extends ChestBoat> type, Level world, Supplier<Item> itemSupplier) {
        super(type, world);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }

}
