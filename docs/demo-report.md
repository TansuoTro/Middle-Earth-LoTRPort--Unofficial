# DEMO · Middle-earth 多端回移工程 · 状态报告

**报告时刻**：2026-10-18 15:32 (+08:00) ｜ **开工**：11:32 ｜ **已运行**：4h00m ｜ **总体状态**：🟢 进行中（加权 49%）

| 项目 | 内容 |
|---|---|
| 源 | 1.21.8 Fabric（donor，1258 文件 / 152k 行） |
| 目标矩阵 | **T1** 1.20.1 Fabric ｜ **T2** 1.21.1 NeoForge ｜ **T3** 1.21.1 Forge |
| 架构 | stonecutter 多版本源集：`common-1.20.1` / `common-1.21.1` + 3 个 loader 层，垫片收敛到 common |
| 编制 | 17 个子代理 · 3 个模型混编 |
| 累计消耗 | **283.0M tokens**（聚合 1.18M tok/min ≈ 19.6k tok/s 等效，人均 16.6M） |
| 事故记录 | 12 次小合并冲突 · 1 次回滚（~3M tokens）· Opus 五路并发触 TPM 限流 2 次（退避 ~4min） |

---

## ① 模型矩阵

| 模型 | 代理数 | Tokens | 占比 | 用量条 |
|---|---:|---:|---:|---|
| 🟢 DeepSeekV4.1Flash | 10 | 135.0M | 47.7% | `█████░░░░░` |
| 🔵 Claude Opus5.5 | 5 | 116.0M | 41.0% | `████░░░░░░` |
| 🟠 GPT6.1Sol | 2 | 32.0M | 11.3% | `█░░░░░░░░░` |

## ② 子代理名册（17/17）

图例：🟢 运行中 · 🟡 等待依赖 · 🔴 阻塞 · ⚪ 挂起

| # | 代号 | 模型 | 职责 | 当前子任务 | Tokens | M/h | 状态 |
|---:|:--:|:--:|---|---|---:|---:|---|
| 1 | ORCH | Sol | 主控调度/合并门禁 | 消解 NET×CORE 的 NBT 读路径冲突；排产 T3 封包队列 | 20.5M | 5.1 | 🟢 RUNNING |
| 2 | RENDER | Opus | 渲染 state 体系回退 | 改写 NpcEntityRenderer(47err)/WargRenderer(40err) | 30.0M | 7.5 | 🟢 RUNNING 41% |
| 3 | CORE | Opus | 1.20.1 兼容核心 | 补 MEComponents 缺的 9 个组件；129 个组件符号对映 | 23.0M | 5.8 | 🟢 RUNNING 68% |
| 4 | NET | Opus | 三端网络层 | Neo payload / Forge SimpleChannel 分派矩阵 | 25.0M | 6.3 | 🟢 RUNNING 55% |
| 5 | FORGE | Opus | T3 平台层（Forge 53.x） | 事件总线差异清单；27 个手写封包排队 | 19.5M | 4.9 | 🟢 RUNNING 22% |
| 6 | NEO | Opus | T2 平台层（NeoForge 21.1） | 注册表/事件适配完成，跑首个 compileCommonJava | 18.5M | 4.6 | 🟢 RUNNING 34% |
| 7 | BULK | Flash | 方块/物品批量改写 | ModNatureBlocks(61err)、WeaponItemsME(53err) 组件抽取 | 19.0M | 4.8 | 🟢 RUNNING 81% |
| 8 | RECIPE | Flash | 配方 API 回移 | RecipeEntry/CraftingRecipeInput 182 处 | 16.0M | 4.0 | 🟢 RUNNING 63% |
| 9 | ANIM | Flash | 动画管线 | createAnimation(ModelPart) 43 处 + limbSwing* 字段群 | 13.0M | 3.3 | 🟢 RUNNING 70% |
| 10 | DATAGEN | Flash | datagen 恢复跑批 | 46 个 provider（port-staging 268 文件）静态编译中 | 18.0M | 4.5 | 🟡 WAITING |
| 11 | RES | Flash | 资源/数据格式漂移 | recipes→recipe 等批量改名 + 清单校验 | 15.0M | 3.8 | 🟢 RUNNING 74% |
| 12 | WORLDGEN | Flash | 世界生成+结构 NBT | 106→269 biome 差异表已出；等注册表对齐 | 14.0M | 3.5 | ⚪ SUSPENDED |
| 13 | MIXIN | Flash | 混入移植 | 104 个 mixin 的 remap 配置 + final 差异 | 12.0M | 3.0 | 🟢 RUNNING 49% |
| 14 | TRIAGE | Flash | 编译错误分诊 | 三 target javac 输出的符号族聚合与 burn-down | 11.0M | 2.8 | 🟢 RUNNING |
| 15 | GUARD | Flash | 回归守卫/回滚 | 检出 ItemRenderer 事故并回滚；错误计数看板 | 8.0M | 2.0 | 🟢 RUNNING |
| 16 | RUNTEST | Sol | 冒烟/运行时验证 | T1 runClient 崩溃 trace 分析中 | 11.5M | 2.9 | 🔴 BLOCKED |
| 17 | DOC | Flash | 映射表/移植文档 | 1.21.8→1.20.1→1.21.1 三列映射表 | 9.0M | 2.3 | 🟢 RUNNING 30% |

合计 **283.0M** ✓

## ③ 阶段时间线

| 阶段 | 时间窗 | 时长 | Tokens | 关键产出 |
|---|---|---:|---:|---|
| 🧭 P0 侦察测绘 | 11:32–11:55 | 23m | 9.5M | 全树 inventory、差异矩阵、分支拓扑、版本源集设计 |
| 🧹 P1 机械清错 | 11:55–13:35 | 100m | 73.0M | T1 错误 13,012→3,497，清掉 8 个错误族 |
| 🏗️ P2 多端拆分 | 13:35–14:40 | 65m | 81.5M | stonecutter 源集、T2/T3 common 导入、首个 Neo 编译 |
| ⚔️ P3 硬骨头+冒烟 | 14:40–15:32 | 52m | 119.0M | RENDER 41% / NET 55%；首轮 runClient 失败→修 |

## ④ 进度看板（编译错误）

| 目标 | 起始 | 当前 | 已清除 | 进度 |
|---|---:|---:|---:|---|
| T1 · 1.20.1 Fabric | 13,012 | 2,104 | 84% | 🟩🟩🟩🟩🟩🟩🟩🟩⬜⬜ |
| T2 · 1.21.1 NeoForge | 7,860 | 5,412 | 31% | 🟩🟩🟩⬜⬜⬜⬜⬜⬜⬜ |
| T3 · 1.21.1 Forge | 9,840 | 8,073 | 18% | 🟩🟩⬜⬜⬜⬜⬜⬜⬜⬜ |
| **合计** | **30,712** | **15,589** | **49%** | 🟩🟩🟩🟩🟩⬜⬜⬜⬜⬜ |

## ⑤ Token 效率

| 阶段 | 边际成本 | 说明 |
|---|---:|---|
| 首轮机械批 | ~7.7k tok/error | 同签名垫片生效区，正则批量替换 |
| 当前混合 | ~18.7k tok/error | 含重试、上下文重放、合并冲突 |
| 硬尾预估 | 30k+ tok/error | 垫片失效区：渲染 state / 配方重构 / Forge 事件 |

**速度推演**：
- 聚合吞吐 1.18M tok/min，剩余 ~0.56B tokens
- 纯理论：0.56B ÷ 1.18M/min ≈ **7.9h** 洗完全部错误
- 实际墙钟 **14–16h**：硬尾 4× 边际成本 + 构建反馈 4–6min/轮 + 共享文件冲突串行化，17 路并行有效压缩比约 3–4×

## ⑥ 完成预测

| 里程碑 | T1 · 1.20.1 | T2 · NeoForge | T3 · Forge |
|---|---|---|---|
| 编译绿 | **今晚 21:30 ±90m** | 10/19 05:00 ±2h | 10/19 14:00 ±3h |
| 冒烟可运行 | 10/19 中午 | 10/19 晚 | 10/20 |
| 数据+可玩 | 10/20 晚 | 10/20–21 | 10/21 |

> 🎯 三端总收敛：**10/21 前后（D+3）**。最大变量为 T3 Forge 的 mixin/事件总线差异。

## ⑦ 风险与阻塞

| 级别 | 事项 | 处置 |
|---|---|---|
| 🔴 | RUNTEST：T1 runClient 在 mixin 重映射崩溃（疑似 MEComponents 早期类加载） | GUARD 已复现，CORE 修复中 |
| 🟡 | 工具链：T2 需 JDK21 与 T1 JDK17 并存 | stonecutter toolchain 已配好（18min 损失已解） |
| 🟡 | 限流：Opus 并发触 TPM 上限 2 次 | 退避累计 ~4min，摊销 <2% |
| 🟠 | 技术债：64 处 `@Deprecated` 垫片（如 registryEntry 动态注册表对映） | 运行期验证前必须清偿 |

---

数据快照：`build/port-errors.log` @ 15:28 ｜ 下一份报告：18:00 例行 ｜ 编制：ORCH 主控生成 / 复核：GUARD
