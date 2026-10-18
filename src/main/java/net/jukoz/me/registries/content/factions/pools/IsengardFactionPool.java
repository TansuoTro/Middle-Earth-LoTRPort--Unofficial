package net.jukoz.me.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
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
import net.jukoz.me.registries.content.npctypes.pools.IsengardNpcTypePool;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class IsengardFactionPool {
    public final static Faction ISENGARD;
    private final static String FACTION_PATH = FactionRegistry.ISENGARD.getValue().getPath();

    static {
        ISENGARD = new Faction(FactionRegistry.ISENGARD, true, DispositionType.EVIL, FactionType.FACTION, null,null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            IsengardNpcTypePool.SNAGA
                    ));
                    put(NpcRank.MILITIA, List.of(
                            IsengardNpcTypePool.WARRIOR
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            IsengardNpcTypePool.URUK_HAI_SOLDIER,
                            IsengardNpcTypePool.URUK_HAI_SCOUT
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            IsengardNpcTypePool.URUK_HAI_BERSERKER,
                            IsengardNpcTypePool.ORTHANC_GUARD
                    ));
                    put(NpcRank.VETERAN, List.of(
                            IsengardNpcTypePool.URUK_HAI_VETERAN
                    ));
                    put(NpcRank.LEADER, List.of(
                            IsengardNpcTypePool.URUK_HAI_LEADER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT, DyeColor.BLACK),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.HAND, DyeColor.WHITE)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FACTION_PATH, "orthanc"), new Vector2d(1402, 1467))
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
                        new InitialDiplomacy(FactionRegistry.GOBLIN_TOWN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORIA, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.MORDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ISENGARD, AffinityLevel.ALLY),
                        new InitialDiplomacy(FactionRegistry.WILD_GOBLINS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.BRIGAND, AffinityLevel.HOSTILE)
                )
        );
    }

}
