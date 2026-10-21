package net.jukoz.me.entity.beasts.trolls.cave;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class CaveTrollEntity extends TrollEntity {
    public CaveTrollEntity(EntityType<? extends AbstractChestedHorse> entityType, Level world) {
        super(entityType, world);
    }

    public CaveTrollVariant getVariant() {
        return CaveTrollVariant.byId(this.getId());
    }
}
