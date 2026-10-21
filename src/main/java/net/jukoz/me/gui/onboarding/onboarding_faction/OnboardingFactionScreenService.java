package net.jukoz.me.gui.onboarding.onboarding_faction;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.factions.Faction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OnboardingFactionScreenService {
    private List<Faction> factions = new ArrayList<>();
    public OnboardingFactionScreenService(Level world){
        RegistryAccess registryManager = world.getRegistryManager();
        this.factions = registryManager.get(DynamicRegistriesME.FACTION).stream().toList();
    }

    public List<Faction> getFactionsByDisposition(DispositionType dispositionType){
        List<Faction> foundFactions = new ArrayList<>();
        for(Faction faction : factions){
            if(faction.getDisposition() == dispositionType && faction.isJoinable())
                foundFactions.add(faction);
        }
        foundFactions.sort(Comparator.comparingInt(Faction::getFactionSelectionOrderIndex));
        return foundFactions;
    }
}
