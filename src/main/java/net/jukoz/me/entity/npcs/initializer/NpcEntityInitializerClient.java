package net.jukoz.me.entity.npcs.initializer;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.entity.npcs.NpcEntity;

public class NpcEntityInitializerClient {
    public static void initializeNpcEntity(ClientWorld clientWorld, NpcEntity npcEntity){
        initializeForClient(clientWorld, npcEntity);
    }

    private static void initializeForClient(Level clientWorld, NpcEntity npcEntity) {
        if(NpcEntityInitializerUtil.characterIdentifierExist(clientWorld, npcEntity.getNpcTypeIdentifier())){
            NpcGenerator.generateCharacterTextures(clientWorld, npcEntity);
        }
    }
}
