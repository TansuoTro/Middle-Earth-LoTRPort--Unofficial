# Middle-earth 1.20.1 Fabric 基準工程

## 這個目錄是什麼

上游 `Jukoz/middle-earth` 的 **1.20.1 Fabric 最後狀態**，作為把 1.0.2 內容回移植到 1.20.1 的基準。

- 來源 commit：`a5cbd1a24a2d55a19494c2f73d1db642396b22ae`（2024-05-13，`mod_version = alpha-1.4.2-1.20.1`）
- 這個 commit 就是 Modrinth 上 `Middle-earth-alpha-1.4.2-1.20.1.jar` 的原始碼
- 1.20.1 的時間窗很短：5/13 還是 1.20.1，5/30 已經跳到 1.20.6，所以這是 1.20.1 的終點

## 建置環境

| 項目 | 版本 |
|---|---|
| Minecraft | 1.20.1 |
| Yarn | 1.20.1+build.10 |
| Fabric Loader | 0.16.14 |
| Fabric API | 0.92.12+1.20.1（1.20.1 線最終版） |
| Fabric Loom | 1.11.8 |
| Gradle | 8.14 |
| Java | 17（`options.release = 17`） |

上游原版是 loom 1.1-SNAPSHOT + Gradle 8.0.1 + Fabric API 0.92.0，已升級到與上游 1.21.8 分支同一代的工具鏈（他們用 Gradle 8.14 + loom 1.11）。

## 狀態

- [x] 基準工程可編譯（`gradlew build` 通過，產出 `Middle-earth-1.4.2-1.20.1-port.0.jar`）
- [x] 開發客戶端可啟動到主選單（`gradlew runClient`）
- [x] 修掉上游的啟動卡死（見下）
- [ ] 方塊／物品註冊層
- [ ] 世界生成資料
- [ ] 程式系統

## 已修復：上游的啟動卡死

`MiddleEarthMapGeneration` 在 `generateBiomes` / `generateHeight` 裡先呼叫 `awaitTermination(...)` 才呼叫 `shutdown()`。
`awaitTermination` 只會在 executor 被 shutdown 之後才回傳，所以它永遠等不到，只能等逾時：

- biomes：卡 20 分鐘
- heights：卡 15 分鐘

也就是**全新安裝第一次啟動會卡 35 分鐘**。已改成先 `shutdown()` 再 `awaitTermination()`，逾時改走 `shutdownNow()`。

實測（冷 `run/data`）：biome 生成 44 秒、height 生成約 4 分鐘，之後進入主選單。快取寫在 `run/data/me/alpha-1.4.0-1.20.1/`，第二次啟動直接跳過。

## 建置前提：先跑 datagen

repo 沒有提交 `src/main/generated`（上游也一樣，靠 CI 生成）。少了它：

- `data/me/worldgen/biome/*.json` 不存在 → 開世界時
  `Unbound values in registry ... [me:blue_mountains]` 直接崩潰
- blockstates / models / loot_tables / recipes 也不完整

第一次建置一定要先跑：

```bash
gradlew runDatagenClient
```

實測產出 17,783 個檔案、106 個 biome。跑完 `gradlew runServer` 可以正常啟動
（`Done (24.512s)!`），代表 registry 綁定正確。

## 識別碼（尚未變更）

| 項目 | 值 |
|---|---|
| modid | `me` |
| package | `net.jukoz.me` |
| maven group | `net.jukoz` |
| assets / data 命名空間 | `assets/me`, `data/me` |

上游 1.21.8 已改成 `middle-earth` / `net.sevenstars.middleearth`。
**決定：維持 `me`。** 改名要動 2800 個檔案、對功能零收益；搬資料時用腳本把 `middle-earth:` 改寫成 `me:` 即可。

## 與 1.21.8（`main`）的落差

| 項目 | 1.20.1 | 1.21.8 |
|---|---:|---:|
| Java 檔 | 443 | 1258 |
| 方塊 | 2397 | 6597 |
| 純物品 | 425 | 1253 |
| 生態域 | 106 | 269 |
| 結構 `.nbt` | 33 | 356 |

新增的系統（1.20.1 完全沒有）：陣營 18、NPC 階級 100、種族 8、Artisan Table、Attachment、Inscription、15 個附魔、Structure Manager／巢穴、7 個自訂屬性、食物裝盤、三型盾牌。

## MEComponents 墊片

1.20.1 沒有 data component registry（1.20.5 才引入）。為了不讓 700+ 個呼叫點全部重寫，
在 `net.jukoz.me.item` 建了一層 NBT 墊片：

```
MEComponent<T>   id + Codec + 預設值
MEComponents     get / set / contains / remove / getOrDefault / defaultFor
```

- 存放位置：`ItemStack.tag.MEComponents.<id>`
- 型別安全：`MEComponents.get(stack, MEComponents.FACTION_DATA)` 回傳 `FactionDataComponent`
- 已移植 5 個 value class：Faction / Race / Temperature / Cooldown / SneakAttack（tooltip 先留空，等陣營 registry 到位）
- `ComponentCodecs.alternative(...)` 補上 1.20.1 沒有的 `Codec.withAlternative`

### 相容層：`MESettings`

donor 的寫法是 `new Item.Settings().maxCount(1).component(KEY, V)`，而 1.20.1 的
`Item.Settings` 只有 8 個方法、沒有 component。做法不是改 961 個呼叫點，而是換掉那個類別：

```java
public class MESettings extends Item.Settings {
    public <T> MESettings component(MEComponent<T> key, T value) { ... return this; }
    @Override public MESettings maxCount(int n) { super.maxCount(n); return this; }  // 8 個都覆寫
}
```

關鍵是**共變回傳型別**：每個原版方法都覆寫成回傳 `MESettings`，否則
`maxCount(1)` 會退回 `Item.Settings`，後面的 `.component(...)` 就接不上。

這樣整條鏈原封不動就能編譯，轉換只需要一條規則：

| 轉換 | 次數（EquipmentItemsME） |
|---|---:|
| `new Item.Settings()` → `new MESettings()` | 537 |
| `DataComponentTypesME.X` → `MEComponents.X` | 519 |

`ItemMixin` 在 `Item.<init>` 的 RETURN 攔一次，把 `MESettings` 收集到的 component
交給 `MEComponents.captureDefaults`。物品建立後就不可變，所以模板存在
identity map 裡，由 `get()` 回落——**不寫進每個 stack 的 NBT**，避免 NBT 膨脹。

### 為什麼要有 `defaultFor`

掃描後發現 donor 的 component 用法其實分成兩種：

| 用法 | 次數 | 說明 |
|---|---:|---|
| `new Item.Settings().component(KEY, V)` | **961** | 物品定義期，集中在 2 個檔 |
| `stack.set/get(KEY)` | ~90 | 執行期讀寫 |

1.20.1 的 `Item.Settings` 沒有 `component(...)`，物品註冊後也不可變。
所以 `MEComponents.defaultFor(item, key, value)` 把「物品預設 component」存進一張
identity map，`get()` 在 stack 沒有覆寫時回落到物品預設值——不需要 mixin，
也不需要改 `Item.Settings` 的鏈式 API。

下一步：寫一支腳本把 961 個 `.component(KEY, V)` 從 Settings 鏈裡抽出來，
產生對應的 `MEComponents.defaultFor(...)` 呼叫。

## 移植策略

### 重要修正：資料層不能先搬

原本以為可以先整批搬 `worldgen` / `structure` / `loot_table`，但這些資料全部引用方塊與物品的 registry id。
1.20.1 只有 2397 個方塊，1.21.8 有 6597 個；先搬結構只會得到一堆 unknown block。

**所以順序必須是：方塊／物品註冊 → datagen → 世界生成資料。**

### 階段 A：方塊／物品註冊層

- 從 `main` 搬 `block/registration/`、`item/`、`registries/`
- 這一代的註冊是 Fabric 風格（`Registry.register`），概念上可直接對應 1.20.1
- 主要 delta 在 MC API：`DataComponents`（1.20.5+）、`Item.Properties`、`BlockBehaviour`、`Holder<ArmorMaterial>`
- 搬完跑 `runDatagen`，讓 blockstates / models / loot_table / recipe 重新生成

### 階段 B：世界生成資料

registry 對齊後才搬：

- `worldgen`（biome / configured_feature / placed_feature / structure / structure_set / template_pool / processor_list）
- `structure` 的 `.nbt`
- 注意 1.21 的格式漂移：`recipes`→`recipe`、`structures`→`structure`、`advancements`→`advancement`、`loot_tables`→`loot_table`、`IntProvider` 的 `value` 包裝、structure NBT 的 DataVersion

### 階段 C：程式系統

按價值排序，一個一個來：

1. Artisan Table
2. NPC 基礎（1 個 entity + 階級資料，先用 NBT 不用 components）
3. 陣營 + 開局選擇
4. Attachment / Inscription
5. Structure Manager / 巢穴
6. 自訂屬性
7. 15 個附魔（1.20.1 是 code 註冊，不是 datapack）

### 不要做的事

- 不要 `git merge main`：命名空間、套件、資料夾全變，只會產生上萬個衝突
- 不要從 `main`(1.21.8) 直接降版：要同時吃 item model definition、render pipeline、equipment assets、data components 四顆炸彈

## 授權

上游是 ARR（All Rights Reserved）。本移植已取得作者授權；授權證明請放在 `AUTHORIZATION.md`，並且不要使用 `net.jukoz` / `net.sevenstars` 當發布用的 group。

---

# 全樹匯入之後：現況與工作清單

## 目前狀態（branch port/1.21-content）

- donor middle-earth 1258 檔 + sevenstars-api 19 檔已全部匯入。
- src/main/java 共 1473 檔：453 檔是原本 1.20.1 基準、1055 檔是 donor 改寫後搬入，
  其餘是手寫的相容層。
- **222 檔屬於「合併案」**：donor 有這個檔案、1.20.1 基準也有，兩邊內容不同。
  這些沒有自動搬（promote 會跳過），1.21 新加的成員要手動併進舊檔。
- 編譯現況：**13012 個錯誤 / 1164 個檔案**。這是真實數字（-Xmaxerrs 60000）。

## 工具（tools/port.py）

| 子命令 | 用途 |
|---|---|
| inventory | 掃 donor，產出 markdown 報告 |
| import | 改寫 donor 檔並寫到目的樹；**不覆蓋已存在的檔** |
| promote | 把 port-staging 裡改寫好的檔搬進 src/main/java；已存在的跳過（合併案） |
| retouch | 對已搬入的樹重跑一次改寫規則（規則改版後必跑） |
| errors | 編譯、解析 javac 輸出、按 symbol 分群，並把「只是還沒搬」的錯誤分開 |

port-staging/ 是 gitignored 的暫存樹，可隨時用 import 重建。

改寫規則的坑（都已修）：

- 絕對路徑含冒號，用 [^:] 開頭的樣式永遠對不到 → 改成 .+? 再要求 .java。
- Windows 上 ./gradlew.bat 不能執行（cmd 不認 ./）→ 用 gradlew.bat。
- 這台機器 JVM locale 是 zh_CN，javac 講「错误」不是 error → 兩者都收，並在
  gradle.properties 強制 -Duser.language=en。
- Identifier.of( 用字串取代會吃到 CategoryIdentifier.of( → 規則改成帶邊界錨定的 regex。
- unwrap_call 的括號起點與深度都算錯，會把外層宣告吃掉 → 已重寫。

## 已完成的相容層

| 位置 | 內容 |
|---|---|
| MiddleEarth | LOGGER、of/ofPrefix/ofPath/ofVanillaPath/append/createAggregate/compareId/fetchId（約 500 個呼叫點） |
| compat/NbtCompat | 1.21.5 的 Optional 化 NBT 存取（asCompound / asNbtList / getCompound / getString 後面的 .get()） |
| EntityAttributesME | 補 GENERIC_ 字首改名；1.20.5 才有的屬性（scale、entity_interaction_range…）註冊成 mod 屬性 |
| item/MEComponents + MESettings | 1.21 data component 的 NBT 墊片，讓 Item.Settings.component(...) 連鎖原樣可用 |
| pattern switch | 三處改成 instanceof 串（本 mod 目標 Java 17） |

**註冊不等於生效**：scale、entity_interaction_range 在 1.20.1 沒有任何引擎程式會讀，
數值存在但沒有行為。要真的生效得另外寫 mixin。

## 錯誤分佈（按 package）

| 錯誤數 | package | 說明 |
|---|---|---|
| 10928 | datageneration | **已延後**：46 個新 provider 先搬回 port-staging，讓 runtime 先綠 |
| 3264 | block | 多為 ModelTransform.origin、SHORT_GRASS 之類的 1.21.2 改名 |
| 2540 | client | 1.21.2 新 render pipeline（BipedEntityRenderState、LoadedEntityModels、TextureMap） |
| 874 | entity | |
| 550 | network | PacketCodec / RegistryByteBuf / PacketCodecs，1.20.5 的 codec 化網路層 |
| 542 | item | DataComponentTypes、DyedColorComponent、AttributeModifierSlot |
| 300 | recipe | ComponentsIngredient（1.20.5+ 的原版類別） |

## 下一個工作順序

1. **MEComponents 補齊 9 個缺的 component**（BACK_ATTACHMENT_DATA 234、
   HELMET_ATTACHMENT_DATA 98…）——墊片已經在了，缺的是資料類別。
2. **ModelTransform.origin → of**（2580 個錯誤、302 檔，全在 renderer/model）。
   1.21.2 把 of(x,y,z,pitch,yaw,roll) 拆成 builder；1.20.1 沒有 scale 欄位。
3. **DataComponentTypes（650）**：原版 component，1.20.1 沒有，要逐一對應到原生 API
   （DYED_COLOR 對 DyeableItem、TRIM 對 ArmorTrim、LORE 對 NBT…）。
4. **PacketCodec / PacketCodecs / RegistryByteBuf（約 700）**：整個網路層要重寫成 PacketByteBuf。
5. **client 的 render state 體系**：這是最大的一塊，1.21.2 把 entity renderer 從
   「直接拿 entity」改成「render state + model」，沒有捷徑。

## 尚未決定的兩件事

- **命名空間**：donor 用 middle-earth，1.20.1 基準用 me。目前 MOD_ID 仍是 me，
  所以基準的資源照舊可用；但 donor 的資源全部是 middle-earth:。
  匯入資源時要二選一：改 donor 的資源，或把基準的 assets/me、data/me 改名。
- **of-beasts-and-wild-things**：donor 的第三個子專案（62 檔、自己的 mod id），
  middle-earth 只從 4 個地方引用它。目前未匯入。

## 本輪已清掉的家族（18866 → 13012）

| 原錯誤數 | 家族 | 做法 |
|---|---|---|
| 2580 | ModelTransform.origin | → pivot(x,y,z)，同一個零旋轉 transform |
| 482 | Transformation.Targets.MOVE_ORIGIN | → TRANSLATE |
| 432 | NoteBlockInstrument | → Instrument（常數完全相同） |
| 368 | DataComponentTypes.DYED_COLOR | → MEComponents.DYED_COLOR + DyeableItemMixin |
| 332 | BACK/HELMET_ATTACHMENT_DATA | 補註冊到 MEComponents |
| 268 | DyedColorComponent | compat 版本 + 讀取 mixin |
| 236 | RenderPipelines | 去掉 drawTexture 的第一個參數 |
| 194 | AnimationDefinition | → Animation |
| 182 | ScheduledTickView | 改寫 44 個 getStateForNeighborUpdate |
| 190 | Text.withColor(int) | → METext.withColor |
| 152 | Blocks.SHORT_GRASS | → Blocks.GRASS |
| 118 | AttributeModifierSlot | 尚未處理（1.20.5+ 概念） |
| 102 | EquipmentType | → ArmorItem.Type（BODY 丟棄） |
| 82 | Identifier.ofVanilla | → new Identifier("minecraft", x) |
| 76 | Block.createColumnShape | → createCuboidShape(8-r, y1, 8-r, 8+r, y2, 8+r) |
| 44 | ToolMaterial 抽象 | compat/SimpleToolMaterial（標籤→挖掘等級） |
| 44 | NbtCompound.getString(k, d) | → NbtCompat.getString |
| 38 | NbtCompound.getList(k) | 補上元素型別 9 |

## 剩下的硬骨頭

1. **網路層**（RegistryByteBuf 274、PacketCodecs 220、PacketCodec 294、Id 170
   ≈ 960）：1.20.5 把封包改成 codec 化 + CustomPayload，1.20.1 是 PacketByteBuf
   手寫序列化。41 個檔要重寫，沒有機械解。
2. **DataComponentTypes 其餘 368**：WEAPON / TOOL / FOOD / EQUIPPABLE / CONSUMABLE /
   BLOCKS_ATTACKS / TRIM / LORE 等，每一個都要對到 1.20.1 的 item 類別行為或 NBT。
3. **client render state**（BipedEntityRenderState 84、LoadedEntityModels 84…）：
   1.21.2 把 entity renderer 拆成 render state + model，這是整包重寫。
4. **動畫**：1.20.1 沒有 Animation#createAnimation(ModelPart)，動畫要改成
   AnimationHelper.animate(model, animation, age, speed, vector)，每個 model 都要動。
5. **AttributeModifierSlot 118**：1.20.5+ 的欄位概念，1.20.1 靠 EquipmentSlot 傳遞。

---

# 第二階段：網路層與相容層（2026-10-18）

## 錯誤量

| 時間點 | 錯誤 | 檔案 |
|---|---|---|
| 上一輪結束 | 5,429（原報表 10,858 是把 gradle 重列的重複算進去） | 571 |
| 本輪結束 | **4,506** | 544 |

> 提醒：`tools/port.py errors` 現在會把 javac 原始輸出存到
> `build/port-errors.log`，並且只認行首無縮排的診斷行——gradle 在失敗後會把
> 同一批錯誤再列一次（縮排兩格），先前因此把總數算成兩倍。

## 本輪清掉的家族

| 原錯誤數 | 家族 | 做法 |
|---|---|---|
| ~960 | 網路層（CustomPayload / PacketCodec / PayloadTypeRegistry） | 27 個封包改寫成 `FabricPacket` + `PacketType` + `PacketByteBuf` 手寫序列化；`ModServerNetworkHandler`/`ModClientNetworkHandler` 改用 `PlayPacketHandler` |
| ~1300 | `FactionDataComponent(RegistryKey<Faction>)` 建構子被匯入時吃掉 | 補回建構子（`faction.getValue()`） |
| 497 | 地表生物群系三個檔 | `MEBiomeKeys` 換成 donor 版（270 個 key），`register()` 裡的 `TranslationEntries` 先註記為 datagen 待辦 |
| 94 | EnchantmentsME | 1.21 的 `Enchantment.builder` 在 1.20.1 不存在；改寫成 15 個 `Registries.ENCHANTMENT` 註冊（rarity / target / slots / power 曲線），屬性型效果留待手動套用 |
| 77 | `net.minecraft.storage.ReadView/WriteView` | `compat/nbt/ReadView`、`WriteView`（NbtCompound 後端），18 個檔的 `readData/writeData` 改接 `readNbt/writeNbt` |
| 57 | `Block#getCodec()`（1.20.5+） | 刪掉 54 個覆寫，1.20.1 方塊沒有 codec |
| 31 | `onUse(...)` 少 `Hand` 參數 | 補參數、補 `super.onUse(..., hand, hit)` |
| 60 | ArmorMaterialsME | `compat/SimpleArmorMaterial`（1.21 record → 1.20.1 介面），`EquipmentType` → `ArmorItem.Type` |
| 45 | `DataComponentTypesME` | 刪除；`MEComponents` 早就取代它，實測沒有任何常數引用 |
| 9 | `appendTooltip` 五參數版 | 改成 1.20.1 的 `(ItemStack, TooltipContext, List<Text>, TooltipType)` |
| 9 | `METext.withColor(METext, …)` | 先前的替換把 receiver 也吃進去，修掉 |
| 55 | ModDimensions | 換成 donor 的 API（`ME_WORLD_KEY`、`getDimensionHeight`、`teleportPlayerToOverworld`、`isInOverworld`），維度 id 仍用基準的 `me:middle-earth` |

## 下一個工作順序（依錯誤數）

1. **`PacketCodec` / `PacketCodecs` / `RegistryByteBuf`（≈150）**：1.20.1 完全沒有
   這組類別。作法是把 `compat/net/PacketCodec`（`of/tuple/unit`）與 `PacketCodecs`
   （`STRING/INTEGER/BOOLEAN/LONG/optional/indexed/codec/toList`）補起來，
   再把 `RegistryByteBuf` 全部換成 `PacketByteBuf`。
   **未解**：`PacketCodecs.registryEntry(dynamicKey)` 在 1.20.1 沒有執行期動態註冊表可查，
   目前只能寫入 Identifier，解碼端需要一個由 datapack 載入時填充的對照表。
2. **`net.minecraft.component.type.*`（71）+ `net.minecraft.component.*`（63）**：
   `AttributeModifiersComponent`（10）、`ToolComponent`（9）、`FoodComponent`（6）、
   `BannerPatternsComponent`（11）… 每一個都要對到 1.20.1 的 item 行為或 NBT。
3. **client render state（57 + `render` 44 + `setAngles` 18 + `createRenderState` 14）**：
   1.21.2 的 `EntityRenderer<T, S>` 拆成 createRenderState/updateRenderState，
   1.20.1 直接吃 entity。20 個 renderer、15 個 model 要一起改。
4. **`AttributeModifierSlot` / `EntityAttribute` vs `RegistryEntry<EntityAttribute>`（59 + 46）**。
5. **datagen（`TranslationEntries` 26）**：46 個 provider 還壓在 `port-staging`。

## 已知的執行期地雷（編譯過了但行為不對）

- `EnchantmentsME`：celerity / grounded / stalwart / vantage 的屬性加成在 1.20.1 沒有對應機制，
  目前只是一個沒有數值效果的附魔。
- `MEBiomeKeys.register()` 不再把生物群系名字寫進 `TranslationEntries`，翻譯要等 datagen。
- `ArtisanTableScreenHandler` 仍是基準的 6 格版本，donor 的 3×3 + 分頁版本還沒合併；
  `ArtisanIndexPacket` / `ArtisanTableTabPacket` 呼叫的 `updateIndex` / `changeTab` /
  `addRecipeOutput` 因此還缺。
