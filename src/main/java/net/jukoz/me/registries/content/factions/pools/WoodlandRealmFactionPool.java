package net.jukoz.me.registries.content.factions.pools;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.util.DyeColor;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.BannerPatternsME;
import net.jukoz.me.registries.content.factions.FactionRegistry;
import net.jukoz.me.registries.content.npctypes.pools.WoodlandRealmNpcTypePool;
import net.jukoz.me.resources.datas.common.AffinityLevel;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.FactionType;
import net.jukoz.me.resources.datas.common.NpcRank;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.InitialDiplomacy;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.List;

public class WoodlandRealmFactionPool {
    public final static Faction WOODLAND_REALM;

    static {
        WOODLAND_REALM = new Faction(FactionRegistry.WOODLAND_REALM, true, DispositionType.GOOD, FactionType.FACTION, null, null,
                new HashMap<>(){{
                    put(NpcRank.CIVILIAN, List.of(
                            WoodlandRealmNpcTypePool.RANGER
                    ));
                    put(NpcRank.MILITIA, List.of(
                            WoodlandRealmNpcTypePool.RANGER
                    ));
                    put(NpcRank.SOLDIER, List.of(
                            WoodlandRealmNpcTypePool.WARRIOR
                    ));
                    put(NpcRank.KNIGHT, List.of(
                            WoodlandRealmNpcTypePool.RANGER
                    ));
                    put(NpcRank.VETERAN, List.of(
                            WoodlandRealmNpcTypePool.RANGER
                    ));
                    put(NpcRank.LEADER, List.of(
                            WoodlandRealmNpcTypePool.COMMANDER
                    ));
                }},
                new BannerData(DyeColor.WHITE, List.of(
                        new BannerData.BannerPatternWithColor(BannerPatternsME.CLOTH, DyeColor.GREEN),
                        new BannerData.BannerPatternWithColor(BannerPatterns.GRADIENT_UP, DyeColor.GRAY),
                        new BannerData.BannerPatternWithColor(BannerPatternsME.ELK, DyeColor.BROWN)
                )),
                new SpawnDataHandler(List.of(
                        new SpawnData(MiddleEarth.of(FactionRegistry.WOODLAND_REALM.getValue().getPath(),  "elvenkings_halls"), new Vector2d(1957, 766))
                )), List.of(), List.of(),
                List.of(
                        new InitialDiplomacy(FactionRegistry.LOTHLORIEN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.GONDOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.ROHAN, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.SHIRE, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.LONGBEARDS_EREBOR, AffinityLevel.FRIENDLY),
                        new InitialDiplomacy(FactionRegistry.DALE, AffinityLevel.FRIENDLY),
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
