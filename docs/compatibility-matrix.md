# Item Display Control — Compatibility Matrix

This document is the **canonical compatibility reference** for the Item Display Control project across all actively maintained branches and supported Minecraft versions.

Last verified: **2026-11-27** | Verification toolchain: Gradle 8.14/9.9.2, OpenJDK 21 & 25.

---

## 1. Active Development & Support Branches

| Branch | Minecraft Line | Mod Version | Default Compile Target | Java Baseline | Artifact Strategy | Status |
|---|---|---|---|---|---|---|
| [`main`](https://github.com/kizio806/item-display-control) | **26.3** | `1.0.0` | Minecraft 26.3 | **Java 25** | Single artifact per loader | **Active (Primary)** |
| [`support/26.2.x`](https://github.com/kizio806/item-display-control/tree/support/26.2.x) | **26.2** | `1.0.0` | Minecraft 26.2 | **Java 25** | Single artifact per loader | **LTS Support** |
| [`support/26.1.x`](https://github.com/kizio806/item-display-control/tree/support/26.1.x) | **26.1.x** (26.1 – 26.1.2) | `1.0.0` | Minecraft 26.1.2 | **Java 25** | Unified line artifact per loader | **LTS Support** |
| [`support/1.21.9-1.21.11`](https://github.com/kizio806/item-display-control/tree/support/1.21.9-1.21.11) | **1.21.9 – 1.21.11** | `1.0.0` | Minecraft 1.21.11 | **Java 21** | Unified line artifact per loader | **LTS Support** |

---

## 2. Complete Combination Matrix & Verification Results

Every declared combination of Mod Loader and Minecraft version is independently compiled, probed, tested, and verified against strict release gates (`test`, `publishReadyCheck`, `strictCheck`, dedicated server safety).

| # | Branch | Mod Loader | Minecraft Version | Dependency Versions | Compilation & Tests | Production Artifact | Verification Status |
|---|---|---|---|---|---|---|---|
| 1 | `main` | **Fabric** | **26.3** | Fabric API `0.160.6+26.3`<br>Fabric Loader `>=0.18.4` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.3.x-fabric.jar` | **VERIFIED** |
| 2 | `main` | **NeoForge** | **26.3** | NeoForge `26.3.0.3-beta`<br>Loader `[26,)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.3.x-neoforge.jar` | **VERIFIED** |
| 3 | `support/26.2.x` | **Fabric** | **26.2** | Fabric API `0.159.0+26.2`<br>Fabric Loader `>=0.18.4` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.2.x-fabric.jar` | **VERIFIED** |
| 4 | `support/26.2.x` | **NeoForge** | **26.2** | NeoForge `26.2.0.79`<br>Loader `[26.2,26.3)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.2.x-neoforge.jar` | **VERIFIED** |
| 5 | `support/26.1.x` | **Fabric** | **26.1** | Fabric API `0.155.2+26.1.2`<br>Range `>=26.1 <26.2` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-fabric.jar` | **VERIFIED** |
| 6 | `support/26.1.x` | **Fabric** | **26.1.1** | Fabric API `0.155.2+26.1.2`<br>Range `>=26.1 <26.2` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-fabric.jar` | **VERIFIED** |
| 7 | `support/26.1.x` | **Fabric** | **26.1.2** | Fabric API `0.155.2+26.1.2`<br>Range `>=26.1 <26.2` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-fabric.jar` | **VERIFIED** |
| 8 | `support/26.1.x` | **NeoForge** | **26.1** | NeoForge `26.1.2.103`<br>Range `[26.1,26.2)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-neoforge.jar` | **VERIFIED** |
| 9 | `support/26.1.x` | **NeoForge** | **26.1.1** | NeoForge `26.1.2.103`<br>Range `[26.1,26.2)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-neoforge.jar` | **VERIFIED** |
| 10 | `support/26.1.x` | **NeoForge** | **26.1.2** | NeoForge `26.1.2.103`<br>Range `[26.1,26.2)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc26.1.x-neoforge.jar` | **VERIFIED** |
| 11 | `support/1.21.9-1.21.11` | **Fabric** | **1.21.9** | Fabric API `>=0.134.1`<br>Range `>=1.21.9 <1.21.12` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-fabric.jar` | **VERIFIED** |
| 12 | `support/1.21.9-1.21.11` | **Fabric** | **1.21.10** | Fabric API `>=0.134.1`<br>Range `>=1.21.9 <1.21.12` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-fabric.jar` | **VERIFIED** |
| 13 | `support/1.21.9-1.21.11` | **Fabric** | **1.21.11** | Fabric API `>=0.134.1`<br>Range `>=1.21.9 <1.21.12` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-fabric.jar` | **VERIFIED** |
| 14 | `support/1.21.9-1.21.11` | **NeoForge** | **1.21.9** | NeoForge `21.9.16+`<br>Range `[21.9,21.12)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-neoforge.jar` | **VERIFIED** |
| 15 | `support/1.21.9-1.21.11` | **NeoForge** | **1.21.10** | NeoForge `21.10.64+`<br>Range `[21.9,21.12)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-neoforge.jar` | **VERIFIED** |
| 16 | `support/1.21.9-1.21.11` | **NeoForge** | **1.21.11** | NeoForge `21.11.48+`<br>Range `[21.9,21.12)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.9-1.21.11-neoforge.jar` | **VERIFIED** |

---

## 3. Architecture & Technical Boundary Justification

### Multi-Loader Separation
- **Common Module (`:common`):** Pure Java domain logic. Zero references to `net.minecraft`, `net.fabricmc`, or `net.neoforged`. Contains target resolution, projection math, configuration storage, and runtime state.
- **Fabric Module (`:fabric`):** Thin adapter integrating with `UseBlockCallback` and `UseEntityCallback`.
- **NeoForge Module (`:neoforge`):** Thin adapter subscribing to `InputEvent.InteractionKeyMappingTriggered`.

### Dedicated Server Safety
Item Display Control is 100% client-side. Dedicated server safety is verified by class constant-pool bytecode inspection (`verifyDedicatedServerSafe`) and loader metadata (`environment: client`, `displayTest: IGNORE_SERVER_VERSION`).

---

## 4. Verification Procedures

To reproduce verification:

```bash
# Verify formatting, source hygiene, and structure
./gradlew spotlessApply validateStructure validateSourceHygiene

# Run full unit and integration test suite
./gradlew test --rerun

# Run strict release qualification gates
./gradlew strictCheck publishReadyCheck buildAll
```
