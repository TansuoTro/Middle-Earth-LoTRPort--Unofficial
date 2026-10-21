package net.jukoz.me.entity.npcs.initializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.npc_types.NpcTypeLookup;
import org.jetbrains.annotations.Nullable;

public class NpcEntityBuilder {
    NpcEntity entity;
    Level world;

    public NpcEntityBuilder(Level world, @Nullable BlockPos pos){
        this.world = world;
        this.entity = new NpcEntity(EntitiesME.NPC, this.world);
        if(pos != null)
            this.entity.setPosition(pos.toCenterPos());
    }

    public NpcEntityBuilder withNpcType(ResourceLocation identifier) {
        this.entity.prepareNpcIdentifier(identifier);
        return this;
    }

    public NpcEntity build(){
        this.entity.tryToInitializeData();
        return this.entity;
    }

    public NpcEntity forceBuild() {
        this.entity.tryToInitializeData();
        return this.entity;
    }

}
