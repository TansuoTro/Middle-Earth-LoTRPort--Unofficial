package net.jukoz.me.resources.datas.factions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.FactionType;
import net.jukoz.me.resources.datas.common.NpcRank;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.InitialDiplomacy;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import net.jukoz.me.resources.datas.npc_types.NpcTypeLookup;
import net.jukoz.me.resources.datas.npc_types.data.WeightedGearData;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import java.util.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.RandomSource;

public class Faction {
    private static HashMap<DispositionType, List<Integer>> FactionSelectionOrderIndexPerDisposition;

    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Faction::getIdValue),
            Codec.INT.fieldOf("faction_selection_order_index").forGetter(Faction::getFactionSelectionOrderIndex),
            Codec.BOOL.fieldOf("joinable").forGetter(Faction::getJoinable),
            Codec.STRING.fieldOf("disposition").forGetter(Faction::getDispositionString),
            Codec.STRING.fieldOf("faction_type").forGetter(Faction::getFactionTypeString),
            ResourceLocation.CODEC.optionalFieldOf("parent_faction").forGetter(Faction::getParentFactionIdentifier),
            Codec.list(ResourceLocation.CODEC).optionalFieldOf("subfaction").forGetter(Faction::getSubfactionIds),
            CompoundTag.CODEC.optionalFieldOf("npcs").forGetter(Faction::getNpcValues),
            CompoundTag.CODEC.optionalFieldOf("banner").forGetter(Faction::getBannerNbt),
            CompoundTag.CODEC.optionalFieldOf("spawns").forGetter(Faction::getSpawnDataNbt),
            Codec.list(Codec.STRING).optionalFieldOf("command_join").forGetter(Faction::getJoinCommands),
            Codec.list(Codec.STRING).optionalFieldOf("command_leave").forGetter(Faction::getLeaveCommands),
            Codec.list(InitialDiplomacy.CODEC).fieldOf("initial_diplomacy").forGetter(Faction::getInitialDiplomaciesNbtList)
    ).apply(instance, Faction::new));

    public static final PacketCodec<net.minecraft.network.FriendlyByteBuf, Faction> PACKET_CODEC = PacketCodecs.codec(CODEC);
    public static final Codec<Holder<Faction>> ENTRY_CODEC = RegistryFileCodec.of(DynamicRegistriesME.FACTION, CODEC);
    public static final PacketCodec<FriendlyByteBuf, Holder<Faction>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.FACTION, PACKET_CODEC);

    private final ResourceLocation id;
    private final Integer factionSelectionOrderIndex;
    private final String translatableKey;
    private final boolean joinable;
    private final DispositionType dispositionType;
    private final FactionType factionType;
    private final ResourceLocation parentFactionId;
    private final HashMap<NpcRank, List<ResourceLocation>> npcDatasByRank;
    private final BannerData bannerData;
    private final SpawnDataHandler spawnDataHandler;
    private final List<InitialDiplomacy> initialDiplomacies;

    private List<ResourceLocation> subFactions = null;
    private List<String> joinCommands;
    private List<String> leaveCommands;
    private List<Race> races = null;
    private List<Component> descriptions = null;
    private Component raceList = null;

    public Faction(String id, Integer factionSelectionOrderIndex, Boolean joinable, String disposition, String factionType,
                   Optional<ResourceLocation> parentFaction, Optional<List<ResourceLocation>> newSubFactions, Optional<CompoundTag> npcs,
                   Optional<CompoundTag> bannerDataNbt, Optional<CompoundTag> spawnsNbt, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands, List<InitialDiplomacy> initialDiplomaciesNbt) {
        this.id = MiddleEarth.fetchId(id);

        this.factionSelectionOrderIndex = factionSelectionOrderIndex; // TODO : Validation, rework this part in the future

        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.joinable = joinable;
        this.dispositionType = DispositionType.valueOf(disposition.toUpperCase());

        this.factionType = FactionType.valueOf(factionType.toUpperCase());
        this.parentFactionId = parentFaction.orElse(null);

        this.initialDiplomacies = initialDiplomaciesNbt;

        if(newSubFactions.isPresent()){
            this.subFactions = new ArrayList<>();
            this.subFactions.addAll(newSubFactions.get());
        }

        this.npcDatasByRank = new HashMap<>();
        if(npcs.isPresent()){
            ListTag list = npcs.get().getList("ranks", Tag.COMPOUND_TYPE);
            for(int i = 0; i < list.size(); i++){
                CompoundTag rankCompound = list.getCompound(i);
                String rankName = rankCompound.getString("rank").toUpperCase();
                try{
                    NpcRank rank = NpcRank.valueOf(rankName);
                    ListTag npcDataList = rankCompound.getList("pool", Tag.COMPOUND_TYPE);
                    List<ResourceLocation> dataList = new ArrayList<>();
                    for(int j = 0; j < npcDataList.size(); j++){
                        dataList.add(MiddleEarth.fetchId(npcDataList.getString(j)));
                    }
                    this.npcDatasByRank.put(rank, dataList);
                } catch (Exception ignored){

                }
            }
        }

        this.bannerData = (bannerDataNbt.isEmpty()) ? null : new BannerData(bannerDataNbt);
        this.spawnDataHandler = new SpawnDataHandler(spawnsNbt);

        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));

        this.raceList = null;
        this.descriptions = null;

        verifyData();
    }

    public Faction(ResourceKey<Faction> faction, Boolean joinable, DispositionType dispositionType, FactionType factionType, ResourceLocation parentFactionId,
                   List<ResourceLocation> subFactions, HashMap<NpcRank, List<NpcType>> npcDatas, BannerData bannerData, SpawnDataHandler spawnDataHandler,
                   List<String> joinCommand, List<String> leaveCommand,
                   List<InitialDiplomacy> initialDiplomacies)
    {
        this.id = faction.getValue();

        if(FactionSelectionOrderIndexPerDisposition == null)
            FactionSelectionOrderIndexPerDisposition = new HashMap<>();
        if(FactionSelectionOrderIndexPerDisposition.containsKey(dispositionType)){
            this.factionSelectionOrderIndex = FactionSelectionOrderIndexPerDisposition.get(dispositionType).size();
            List<Integer> orderList = new ArrayList<>(FactionSelectionOrderIndexPerDisposition.get(dispositionType));
            orderList.add(this.factionSelectionOrderIndex);
            FactionSelectionOrderIndexPerDisposition.put(dispositionType, orderList);
        }
        else {
            int initialIndex = 0;
            this.factionSelectionOrderIndex = initialIndex;
            FactionSelectionOrderIndexPerDisposition.put(dispositionType, List.of(initialIndex));
        }
        this.initialDiplomacies = initialDiplomacies;

        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.joinable = joinable;
        this.dispositionType = dispositionType;
        this.factionType = factionType;
        this.parentFactionId = parentFactionId;
        this.subFactions = subFactions;
        if(npcDatas == null || npcDatas.isEmpty()){
            this.npcDatasByRank = null;
        } else{
            this.npcDatasByRank = new HashMap<>();
            for(NpcRank rank : npcDatas.keySet()){
                List<ResourceLocation> listOfIdentifiers = new ArrayList<>();
                for(NpcType data : npcDatas.get(rank)){
                    listOfIdentifiers.add(data.getId());
                }
                this.npcDatasByRank.put(rank, listOfIdentifiers);
            }
        }
        this.bannerData = bannerData;;
        this.spawnDataHandler = spawnDataHandler;
        this.joinCommands = joinCommand;
        this.leaveCommands = leaveCommand;
        this.raceList = null;
        this.descriptions = null;

        verifyData();
    }

    private void verifyData(){
        if(this.id.toString().contains("dorwinion")){
            throw new RuntimeException("There is no dorwinion in Middle-earth");
        }
        if(this.id.toString().toLowerCase().contains("dorw")){
            throw new RuntimeException("Do not even try... We are watching you");
        }

        // Need these data for a functional faction
        if((this.factionType == FactionType.SUBFACTION) || (this.factionType == FactionType.FACTION) && (subFactions == null || subFactions.isEmpty())){
            if(this.npcDatasByRank == null || this.npcDatasByRank.isEmpty()){
                //throw new RuntimeException("PlayerFactionPayload [%s] is missing their npc data, make sure they have at least 1 available npc data per rank.".formatted(id));
            } else {
                if(!npcDatasByRank.containsKey(NpcRank.MILITIA)
                        || !npcDatasByRank.containsKey(NpcRank.SOLDIER)
                        || !npcDatasByRank.containsKey(NpcRank.KNIGHT)
                        || !npcDatasByRank.containsKey(NpcRank.VETERAN)
                        || !npcDatasByRank.containsKey(NpcRank.LEADER)) {
                    //throw new RuntimeException("PlayerFactionPayload [%s] is missing their npc data, make sure they have at least 1 npc data per rank.".formatted(id));
                }
            }
            if(this.bannerData == null){
                //throw new RuntimeException("PlayerFactionPayload [%s] is missing their banner data, make sure they have one.".formatted(id));
            }
        }
    }

    private String getIdValue() {
        return this.id.toString();
    }

    public Integer getFactionSelectionOrderIndex() {
        return this.factionSelectionOrderIndex;
    }

    private Boolean getJoinable() {
        return joinable;
    }

    private Optional<ResourceLocation> getParentFactionIdentifier() {
        if(this.parentFactionId == null)
            return Optional.empty();
        return Optional.of(this.parentFactionId);
    }

    public Faction getParentFaction(Level world){
        if(world == null || factionType != FactionType.SUBFACTION || parentFactionId == null)
            return null;
        try{
            return FactionLookup.getFactionById(world, parentFactionId);
        } catch (FactionIdentifierException e){
            return null;
        }
    }

    public Faction getParentFaction(HolderLookup.WrapperLookup lookup){
        if(factionType != FactionType.SUBFACTION || parentFactionId == null)
            return null;
        try{
            Faction test = lookup.getWrapperOrThrow(DynamicRegistriesME.FACTION).getOrThrow(ResourceKey.of(DynamicRegistriesME.FACTION, this.parentFactionId)).value();
            return test;
        } catch (Exception e){
            return null;
        }
    }

    private Optional<List<ResourceLocation>> getSubfactionIds() {
        if(this.subFactions == null)
            return Optional.empty();
        return Optional.of(subFactions);
    }
    private Optional<CompoundTag> getBannerNbt() {
        if(this.bannerData == null)
            return Optional.empty();
        return this.bannerData.getNbt();
    }
    private Optional<CompoundTag> getSpawnDataNbt() {
        if(this.spawnDataHandler == null)
            return Optional.empty();

        return this.spawnDataHandler.serializeNbt();
    }

    private List<InitialDiplomacy> getInitialDiplomaciesNbtList() {
        return initialDiplomacies;
    }

    public Optional<CompoundTag> getNpcValues() {
        if(this.npcDatasByRank == null || this.npcDatasByRank.isEmpty())
            return Optional.empty();
        CompoundTag nbtCompound = new CompoundTag();
        ListTag ranks = new ListTag();
        for(NpcRank rank : this.npcDatasByRank.keySet()){
            CompoundTag rankNbt = new CompoundTag();
            rankNbt.putString("rank", rank.toString().toUpperCase());
            ListTag identifiers = new ListTag();
            for(ResourceLocation npcDataIdentifier : this.npcDatasByRank.get(rank).stream().toList()) {
                identifiers.add(StringTag.of(npcDataIdentifier.toString()));
            }
            rankNbt.put("pool", identifiers);
            ranks.add(rankNbt);
        }
        nbtCompound.put("ranks", ranks);
        return Optional.of(nbtCompound);
    }

    public Optional<List<String>> getJoinCommands() {
        if(this.joinCommands == null)
            return Optional.empty();
        return Optional.of(this.joinCommands);
    }

    public Optional<List<String>> getLeaveCommands() {
        if(this.leaveCommands == null)
            return Optional.empty();
        return Optional.of(this.leaveCommands);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public NpcType getRandomGear(Level world, NpcRank npcRank, Race race) {
        if(!this.npcDatasByRank.containsKey(npcRank))
            return null;
        List<NpcType> npcTypeList = NpcTypeLookup.getAllNpcTypesFromRace(world, getNpcPoolFromRank(npcRank), race.getId());
        if(npcTypeList.isEmpty())
            return null;
        RandomSource random = new RandomSource();
        return npcTypeList.get(random.nextInt(0, npcTypeList.size()));
    }

    public WeightedGearData getPreviewGear(Level world, Race selectedRace){
        if(selectedRace == null)
            return WeightedGearData.Create();

        List<ResourceLocation> identifiersToUse = new ArrayList<>();
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.MILITIA));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.SOLDIER));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.KNIGHT));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.VETERAN));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.LEADER));

        List<NpcType> npcTypeList = NpcTypeLookup.getAllNpcTypesFromRace(world, identifiersToUse, selectedRace.getId());
        if(npcTypeList.isEmpty())
            return WeightedGearData.Create();
        RandomSource random = new RandomSource();
        NpcType foundNpcType = npcTypeList.get(random.nextInt(0, npcTypeList.size()));
        return foundNpcType.getGear();
    }

    private List<ResourceLocation> getNpcPoolFromRank(NpcRank npcRank) {
        return this.npcDatasByRank.get(npcRank);
    }

    public DyeColor getBaseBannerColor(){
        if(bannerData == null) return BannerData.DEFAULT_DYE;
        return bannerData.getBaseDye();
    }

    public List<BannerData.BannerPatternWithColor> getBannerPatternsWithColors(Level world) {
        if(bannerData == null) return null;
        return bannerData.getBannerPatternsWithColors(world);
    }

    public ItemStack getBannerItem(HolderLookup.WrapperLookup wrapper){
        if(bannerData == null) return ItemStack.EMPTY;
        return bannerData.getBannerItem(wrapper, Component.translatable("block.%s.faction_banner".formatted(MiddleEarth.MOD_ID), getFullName()).formatted(ChatFormatting.GOLD));
    }

    public List<ResourceLocation> getSubFactions(){
        return subFactions;
    }

    public HashMap<NpcRank, List<ResourceLocation>> getAllNpcDatas(){
        return npcDatasByRank;
    }

    public Faction getSubfaction(Level world, int index){
        if(world == null || this.subFactions == null || index >= this.subFactions.size())
            return null;
        return getSubfactionById(world, subFactions.get(index));
    }

    public DispositionType getDisposition(){
        return dispositionType;
    }
    public String getDispositionString(){
        return dispositionType.name();
    }
    public String getFactionTypeString(){
        return factionType.name();
    }

    public FactionType getFactionType(){
        return factionType;
    }

    public SpawnDataHandler getSpawnData() {
        return spawnDataHandler; }

    public ResourceLocation getId() {
        return id;
    }

    public String getName() {
        return id.getPath();
    }

    public MutableComponent getFullName() {
        return MutableComponent.of(new TranslatableContents(translatableKey, "", TranslatableContents.EMPTY_ARGUMENTS));
    }

    public MutableComponent tryGetShortName() {
        String target = translatableKey.concat(".fallback");
        String fallback = Component.translatable(translatableKey).getString();
        return MutableComponent.of(new TranslatableContents(target, fallback, TranslatableContents.EMPTY_ARGUMENTS));
    }

    public Faction getSubfactionById(Level world, ResourceLocation id) {
        if(subFactions == null)
            return null;
        return world.getRegistryManager().get(DynamicRegistriesME.FACTION).get(id);
    }

    public List<Race> getRaces(Level world) {
        if(races != null) return races;

        List<ResourceLocation> allRaceIds = new ArrayList<>();
        for(NpcRank rank : this.npcDatasByRank.keySet()){
            List<NpcType> datas = NpcTypeLookup.getAllNpcTypes(world, this.npcDatasByRank.get(rank));
            for(NpcType data : datas){
                if(data != null)
                    allRaceIds.add(data.getRace());
            }
        }
        races = RaceLookup.getAllRaces(world, allRaceIds);
        return races;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public List<Component> getDescription() {
        descriptions = new ArrayList<>();
        boolean hasDescription = true;

        String base = MiddleEarth.createAggregate('.', "description", MiddleEarth.MOD_ID, id.getPath(), "description_%s");

        while(hasDescription){
            String langPath = base.formatted(descriptions.size());
            Component text = Component.translatable(langPath);
            if(!Objects.equals(text.getString(), langPath)){
                descriptions.add(text);
            } else {
                hasDescription = false;
            }
        }

        return descriptions;
    }

    public Component getRaceListText(Level world) {
        if(raceList == null){
            StringBuilder raceListStringBuilder = new StringBuilder();
            if(races == null)
                races = getRaces(world);
            for(Race race : races){
                raceListStringBuilder.append(race.getFullName().getString());
                if(race != races.get(races.size()-1))
                    raceListStringBuilder.append(", ");
            }
            raceList = Component.literal(raceListStringBuilder.toString());
        }
        return raceList;
    }

    public int getSpawnAmount(){
        if(spawnDataHandler == null || spawnDataHandler.getSpawnList() == null)
            return 0;
        return spawnDataHandler.getSpawnList().size();
    }

    public MEBannerPatterns getBannerPatternComponents(HolderGetter<BannerPattern> bannerPatternLookup) {
        if(bannerData == null)
            return null;
        return bannerData.getBannerPatternComponents(bannerPatternLookup);
    }

    public ResourceLocation getRandomNpcDataIdentifier() {
        List<ResourceLocation> allNpcIds = new ArrayList<>();
        for (List<ResourceLocation> npcIds : this.npcDatasByRank.values())
            allNpcIds.addAll(npcIds);
        if(allNpcIds.size() == 0)
            return null;

        RandomSource random = new RandomSource();
        return allNpcIds.get(random.nextInt(allNpcIds.size()));
    }

    public boolean isHostileToward(ResourceLocation playerFaction) {
        for(InitialDiplomacy diplomacy : initialDiplomacies){
            if(playerFaction == null || diplomacy.isHostileToward(playerFaction)){
                return true;
            }
        }
        return false;
    }
}
