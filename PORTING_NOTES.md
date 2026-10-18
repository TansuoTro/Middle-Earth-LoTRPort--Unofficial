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
