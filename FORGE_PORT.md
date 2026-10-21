# Middle-earth → Forge 1.20.1 移植执行手册

> 分析结论：**可行**。路线 = 转换现有 Fabric 1.20.1 port（分支 port/1.21-content），
> 而不是从上游 NeoForge 1.21.1 重 port（内容层零返工）。
> 本文记录"怎么做"：三个转换轴、已就绪的脚本、顽固点解法、里程碑。
> WIP 骨架在分支 port/forge-1.20.1（27bb2c4 映射重命名 + 654530d Forge 骨架）。

## 0. 转换基数（port 仓库 H:/MinecraftMods/MiddleEarth-1.20.1）

1516 java 文件 / 157,727 行；30 mixin（18 common + 12 client）；1 条 accesswidener；
注册为命令式 Registry.register（343 registerBlock / 363 registerItem）；
网络层 41 文件 / 35 个包，已有 IConnectionToClient|Server + Client|ServerPacketContext
抽象；datagen 85 文件（纯开发期）；compat 65 文件。

## 1. 三个转换轴

| 轴 | 规模 | 方案 |
|---|---|---|
| ① mappings yarn→官方 | 全部 1516 文件、692 个 MC 类 | 脚本机械化（见 §2，已完成一遍） |
| ② Fabric API→Forge API | 178 个文件 | 分区模式化转换（见 §5） |
| ③ 构建/加载器 | 30 mixin + 1 AT + 2 入口 + REI 3 文件 | 一次性骨架（见 §4） |

## 2. 轴①：yarn→官方命名（已机械化，可重跑）

罗塞塔石碑：上游 origin/dev-neoforge（NeoForge 1.21.1，官方命名）与 port
1164/1516 个文件路径完全相同。按路径对齐两侧 import net.minecraft.* 集合，
按简单名双部匹配 + 全局投票，自动提取映射表。

脚本（均在 H:/MinecraftMods/build/agents/）：

| 脚本 | 作用 |
|---|---|
| me_forge_mapping.py | 从 1164 个文件对提取原始投票 → me_forge_mapping_raw.json |
| _build_table.py | 多数票 + 边际规则筛出高置信表 → me_forge_mapping.json（223 条） |
| _overlay.py | 高频类手工 overlay（Vec3d/Text/RegistryKey 等 41 条） |
| me_rename_yarn2official.py | 应用表：import 重写 + FQN 替换 + 简单名重命名 |
| me_env_to_onlyin.py | @Environment(EnvType.CLIENT)→@OnlyIn(Dist.CLIENT) 等（81 文件） |

重跑方法：

    # 1. 本地化 neoforge 参照树（一次性；代理坑见 §7）
    cd H:/MinecraftMods/.tmp_me_upstream
    git config http.proxy http://127.0.0.1:7890 && git config https.proxy http://127.0.0.1:7890
    git fetch origin dev-neoforge
    rm -rf nf_tree && mkdir nf_tree
    git archive origin/dev-neoforge middle-earth/src/main/java | tar -x -C nf_tree --strip-components=4

    # 2. 提取 + 建表 + overlay + 重命名（在 port 仓库干净树上跑）
    python H:/MinecraftMods/build/agents/me_forge_mapping.py
    python H:/MinecraftMods/build/agents/_build_table.py
    python H:/MinecraftMods/build/agents/_overlay.py
    python H:/MinecraftMods/build/agents/me_rename_yarn2official.py
    python H:/MinecraftMods/build/agents/me_env_to_onlyin.py

重命名器安全护栏（改脚本时不要拆）：
- 简单名重命名仅在文件 import 了该 yarn 类、且作用域内无同名类时才生效
- mod 自有同名类（扫描全树 class/interface/enum/record 声明）一律不重命名
- Type/Plane 等嵌套类黑名单（只做 FQN 级）
- new Identifier(a,b) → new ResourceLocation(a,b)

当前残留（编译驱动阶段处理）：Vec3d 13、Text 11、RegistryKey 134、
ActionResult 32、Direction.Type ~267（嵌套类）。

## 3. 编译驱动迭代法（轴①收尾 + 轴②主力）

    cd H:/MinecraftMods/MiddleEarth-1.20.1 && ./gradlew compileJava 2>&1 | grep "error:"

每个编译错误 → 在 .tmp_me_upstream/nf_tree/ 同名文件里查该用法的官方写法 →
按错误签名批量修（参考 fabric port 时期 build/agents/ 的 fix*.py 模式）。
错误量大时 -Xmaxerrs 60000（已在 build.gradle）。

## 4. 轴③：Forge 骨架（一次性，WIP 已含）

参考实现：H:/MinecraftMods/BrewinAndChewin/build.gradle（MDG legacyforge 2.0.77 +
Gradle 8.8 + 官方映射 + refmap + AT）。要点：

- build.gradle：id 'net.neoforged.moddev.legacyforge' version '2.0.77'；
  legacyForge { version = "1.20.1-47.4.20"; accessTransformers = [...] }；
  mixin { add sourceSets.main, "me.refmap.json"; config "me.mixins.json" }
- 不要手写 mixin.env.remapRefMap / refMapRemappingFile（MDG 自己会设，
  手写会静默关掉 SRG 重映射）
- META-INF/mods.toml：modId me，[[mixins]] config="me.mixins.json"，
  forge [47,) / minecraft [1.20.1,1.21)
- me.mixins.json：加 "refmap": "me.refmap.json"（包名 net.jukoz.me.mixin 不变）
- META-INF/accesstransformer.cfg：public net.minecraft.client.renderer.HeldItemRenderer$HandRenderType
  （AW 那条的 Forge 版；类路径要用 javap 在 userdev jar 上核实）
- 入口：MiddleEarth → @Mod(MOD_ID) + 构造器跑原 onInitialize() 体
  （命令式注册在构造器里合法）；MiddleEarthClient →
  @Mod.EventBusSubscriber(modid, value = Dist.CLIENT, bus = Bus.MOD) +
  FMLClientSetupEvent 静态 handler（注意 47.4.20 默认 bus 是 FORGE，MOD 总线要显式写）
- REI 三个插件文件先 sourceSets.main.java.exclude 排除，M7 再换 JEI
- 删除 fabric.mod.json / me.accesswidener

## 5. 轴②：Fabric→Forge API 分区映射

| Fabric | Forge 1.20.1 | 规模 |
|---|---|---|
| ClientPlayNetworking/ServerPlayNetworking/PacketType | SimpleChannel + PacketDistributor（PlayerLookup→TRACKING_ENTITY） | ~5 个 hub 类，35 个包体不动 |
| ServerLifecycleEvents 等 ~10 个事件 | ServerLifecycleEvent/RegisterCommandsEvent/LootTableLoadEvent/BiomeLoadingEvent/BlockEvent.BreakEvent 1:1 | ~10 站点 |
| FabricBlockSettings/FabricItemSettings | BlockBehaviour.Properties/Item.Properties | ~10 文件 |
| FabricEntityTypeBuilder/FabricDefaultAttributeRegistry | EntityType.Builder/EntityAttributeCreationEvent | ~10 |
| ExtendedScreenHandlerType/Factory | IForgeMenuType/MenuProvider + 走 mod 自己的开屏 packet | ~10 |
| EntityModelLayerRegistry/ArmorRenderer/ColorProviderRegistry/BlockRenderLayerMap/ParticleFactoryRegistry/ModelLoadingRegistry/ExtraModelProvider | EntityRenderersEvent.RegisterLayerDefinitions/CustomArmorRenderer/ColorHandlerEvent/RenderType 直赋/RegisterParticleFactoriesEvent/ModelEvent.RegisterGeometryLoaders | ~27 文件 |
| FlammableBlockRegistry/FuelRegistry/StrippableBlockRegistry/OxidizableBlocksRegistry | 无 Forge API → mixin 进 FireBlock/AbstractFurnaceBlockEntity 的 map（ForgeBlockEntity 燃料时间是功能路径） | ~6 文件，顽固 |
| DynamicRegistries（DynamicRegistriesME，48 引用文件） | DataPackRegistryEvent（Forge 1.20.1 有；回调形状不同，codec 逐个适配） | 最大风险点 |
| fabric-permissions-api（1 文件） | 删除或降级 op-level | 1 |
| REI 插件（3 文件 + 4 个 category/display） | JEI 插件重写 | M7 |
| datagen 85 文件 | 保留已生成 JSON；源码删除或改 GatherDataEvent | 可延后 |
| 自研 SimpleConfig | 原样可用 | 0 |

## 6. 里程碑

M1 骨架（已 WIP）→ M2 网络 → M3 事件 → M4 动态注册表（风险点单独）→
M5 客户端 → M6 GUI → M7 顽固点 + REI→JEI → M8 runServer boot 验证 +
Rosetta 桥运行验证（对齐 fabric port 的验证清单）。

## 7. 环境坑（本次实测）

- python3 是微软商店坏 stub（exit 49 无输出）→ 用 python 或 py -3
- Write 工具对含反斜杠转义的内容会吃掉转义 → 正则等内容用 Edit 写，写完 Read 验校；
  长文档也可能写到错误内容，写完必须用 bash head/md5 验校
- git promisor 抓取绕过 -c http.proxy= → 在 clone 里设 repo 级 proxy；
  gradle 也要 systemProp.http(s).proxyHost/Port（已写入 gradle.properties）
- heredoc 里多层引号会炸 → 长文本用 Write/Edit，bash 只跑简单命令
- git ls-tree 路径对齐时注意 strip-components 与包前缀（net/sevenstars/middleearth/）

## 8. 已实证存在的 Forge 1.20.1 API（本机工程）

SimpleChannel（SignPicture-Rebornified 47.4.20、HideAndSeek、ImprovedMobs）、
DataPackRegistryEvent（FaceMohist Forge 1.20.1 源码）、
IForgeMenuType（AstralSorcery worktrees）。

## 9. 进度（2026-10-21 第一轮推进）

分支 `port/forge-1.20.1` 已**重置到当前 Fabric 内容**再转换（原来的 WIP 基于很旧的 `a7d20cb`，落后约 100 个提交）：

| 提交 | 内容 |
|---|---|
| `8dd308a` | 在当前 port 上重跑 yarn→official 映射（1534 文件，改 1345）+ `@OnlyIn`（81 文件） |
| `17e7b99` | **M1 骨架**：MDG legacyforge 2.0.77 / Forge 47.4.20、`META-INF/mods.toml`、`me.mixins.json` 加 refmap、`accesstransformer.cfg`、`@Mod` 入口（`MiddleEarth` 构造函数 + `MiddleEarthClient` 的 `FMLClientSetupEvent`/`Bus.MOD`）；删除 `fabric.mod.json`/`me.accesswidener` |
| `5271ea1` | 映射表扩到当前树（228 条）+ 批量把 `AbstractBlock` 残留改成 `BlockBehaviour`（1013 处 = 0） |

旧 WIP 保留在分支 `forge-wip-20261020`（`654530d`）。

### 编译基线（首次 Forge 编译）

`./gradlew compileJava` → **26,140 errors / 100 warnings**（第一轮 26,288）。错误分布：

| 类型 | 数量 | 说明 |
|---|---|---|
| cannot find symbol | ~21,400 | 绝大多数是下面缺包/方法差异的级联 |
| package … does not exist | 3,869 | 映射表只覆盖 228 个类；其余仍是 yarn 包名 |
| method does not override/implement | 809 | 方法签名差异（mappings 轴②） |
| Fabric API imports | 156 | `net.fabricmc.*`（轴②） |

缺包 Top：`net.minecraft.block`(126)、`net.minecraft.item`(89)、`net.minecraft.client.render.entity.model`(71)、`net.minecraft.entity.attribute`(70)、`net.minecraft.sound`(69)、`net.minecraft.entity`(68)、`net.minecraft.screen`(67)、`net.minecraft.entity.effect`(63)、`net.minecraft.entity.data`(56)、`net.minecraft.util.math`(55)、`net.minecraft.state.property`(53)…

### 下一步（按性价比）

1. **扩映射表**：对每个仍在用的 `net.minecraft.*` FQN，用 `nf_tree` 的同路径 import 对照补表（`me_forge_mapping.py` → `_build_table.py` → `_overlay.py`，脚本已支持 `ME_FORGE_ROOT` 指向任意工作树）；再跑 `me_rename_yarn2official.py`。这一步能一次性消掉大部分 3,869 缺包 + 级联。
2. **方法签名差异**（809）：按错误签名批量改（参考 fabric port 时期 `build/agents/fix*.py`）。
3. **Fabric API → Forge**（156 文件）：按 §5 分区表替换；先从网络 hub（`ModNetworks`）和注册 builder 起。
4. 收尾后再进 M2–M8。

### 工具变更

`me_rename_yarn2official.py` / `me_env_to_onlyin.py` 的 `ROOT` 改为 `os.environ.get("ME_FORGE_ROOT", <默认主工作树>)`，可在任意 worktree 上跑：

    ME_FORGE_ROOT='H:/MinecraftMods/MiddleEarth-1.20.1-forge/src/main/java' python me_rename_yarn2official.py

工作树：`H:/MinecraftMods/MiddleEarth-1.20.1-forge`（`port/forge-1.20.1`），与 Fabric 主工作树隔离，互不影响。
