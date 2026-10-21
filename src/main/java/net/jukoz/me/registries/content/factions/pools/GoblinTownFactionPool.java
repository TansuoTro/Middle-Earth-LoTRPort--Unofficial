package net.jukoz.me.registries.content.factions.pools;

import net.minecraft.world.item.DyeColor;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.BannerPatternsME;
import net.jukoz.me.registries.content.factions.FactionRegistry;
import net.jukoz.me.registries.content.npctypes.pools.GoblinTownNpcTypePool;
import net.jukoz.me.resources.datas.common.AffinityLevel;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.InitialDiplomacy;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.common.NpcRank;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class GoblinTownFactionPool {
    public final static Faction GOBLIN_TOWN;

    static {
        GOBLIN_TOWN = new Faction(FactionRegistry.GOBLIN_TOWN, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            GoblinTownNpcTypePool.GOBLIN
                    ));
                    put(NpcRank.MILITIA, List.of(
                            GoblinTownNpcTypePool.SCOUT
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            GoblinTownNpcTypePool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            GoblinTownNpcTypePool.RIDER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            GoblinTownNpcTypePool.VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            GoblinTownNpcTypePool.VETERAN
                    ));
                }},
                new BannerData(DyeColor.BLACK, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.BROWN),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH_GRADIENT, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.GOBLIN_SKULL, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of("goblin_town", "goblin_town"), new Vector2d(1583, 869))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.WOODLAND_REALM, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.HOBGOBLIN_TRIBES_GUNDABAD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.HOSTILE),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );

    }
}
