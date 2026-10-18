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

## 識別碼（尚未變更）

| 項目 | 值 |
|---|---|
| modid | `me` |
| package | `net.jukoz.me` |
| maven group | `net.jukoz` |
| assets / data 命名空間 | `assets/me`, `data/me` |

**待決**：上游 1.21.8 已改成 `middle-earth` / `net.sevenstars.middleearth`。
從 `main` 搬資料時，每一份 JSON 的 `middle-earth:` 都要改寫成 `me:`，資料夾也要改名。
目前刻意保持 `me` 不動，讓基準工程先能原封不動編譯；是否對齊上游命名留到資料搬遷前再決定。

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

### 階段 A：資料驅動層
從 `main` 整批搬，幾乎不動程式：
- `worldgen`（biome / configured_feature / placed_feature / structure / structure_set / template_pool / processor_list）
- `structure` 的 `.nbt`
- `loot_table` / `recipe` / `advancement` / `tags`
- `banner_pattern` / `painting_variant` / `trim_material` / `trim_pattern`
- 全部 `assets/`

注意 1.21 的格式漂移：`recipes`→`recipe`、`structures`→`structure`、`advancements`→`advancement`、`loot_tables`→`loot_table`、`IntProvider` 的 `value` 包裝、structure NBT 的 DataVersion。

### 階段 B：程式系統
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
