package net.jukoz.me.network.handlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.gui.onboarding.OnboardingSelectionScreen;
import net.jukoz.me.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import net.jukoz.me.world.dimension.ModDimensions;

import java.util.List;

/**
 * Client side only
 */
public class OnboardingScreenHandler {
    public static void handle(ClientPacketContext context, boolean havePlayerData, float delay, List<AttributePoolElement> playerAttributes){
        try{
            Level world = context.player().getWorld();
            if(ModDimensions.isInOverworld(world)){
                MinecraftClient client = MinecraftClient.getInstance();
                if(!havePlayerData){
                    var controller = new OnboardingFactionScreenController(world, delay, playerAttributes);
                    controller.open();
                } else {
                    client.setScreen(new OnboardingSelectionScreen(delay, ModServerConfigs.ENABLE_FACTION_RESET, playerAttributes));
                }
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailParsedPacket::Apply - trying to fetch the client data and show appropriate context.",e);
        }
    }
}
