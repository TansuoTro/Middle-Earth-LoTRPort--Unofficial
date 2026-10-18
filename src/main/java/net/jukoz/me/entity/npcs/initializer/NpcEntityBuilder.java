package net.jukoz.me.entity.npcs.initializer;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.npc_types.NpcTypeLookup;
import org.jetbrains.annotations.Nullable;

public class NpcEntityBuilder {
    NpcEntity entity;
    World world;

    public NpcEntityBuilder(World world, @Nullable BlockPos pos){
        this.world = world;
        this.entity = new NpcEntity(EntitiesME.NPC, this.world);
        if(pos != null)
            this.entity.setPosition(pos.toCenterPos());
    }

    public NpcEntityBuilder withNpcType(Identifier identifier) {
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
