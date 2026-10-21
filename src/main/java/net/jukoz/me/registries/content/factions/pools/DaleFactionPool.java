package net.jukoz.me.registries.content.factions.pools;

import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.item.DyeColor;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.BannerPatternsME;
import net.jukoz.me.registries.content.factions.FactionRegistry;
import net.jukoz.me.resources.datas.common.AffinityLevel;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.InitialDiplomacy;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.common.NpcRank;
import net.jukoz.me.registries.content.npctypes.pools.DalishNpcTypePool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class DaleFactionPool {
    public final static Faction DALE;
    private final static String FACTION_PATH = FactionRegistry.DALE.getValue().getPath();

    static {
        DALE = new Faction(FactionRegistry.DALE, true, DispositionType.GOOD, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                        DalishNpcTypePool.PEASANT
                    ));
                    put(NpcRank.MILITIA, List.of(
                        DalishNpcTypePool.MILITIA
                    ));
                    put(NpcRank.SOLDIER, List.of(
                        DalishNpcTypePool.SOLDIER,
                        DalishNpcTypePool.ARCHER
                    ));
                    put(NpcRank.KNIGHT, List.of(
                        DalishNpcTypePool.KNIGHT,
                        DalishNpcTypePool.ELITE_ARCHER
                    ));
                    put(NpcRank.VETERAN, List.of(
                        DalishNpcTypePool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                        DalishNpcTypePool.SERGEANT
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLUE),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.BELL, DyeColor.YELLOW)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FACTION_PATH, "capital"), new Vector2d(2021, 727)),
                        new SpawnData(MiddleEarth.of(FACTION_PATH, "esgaroth"), new Vector2d(2007, 757))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );
    }

}
