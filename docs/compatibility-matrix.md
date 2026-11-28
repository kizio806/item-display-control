# Item Display Control — Compatibility Matrix

This document is the **canonical compatibility reference** for the Item Display Control project across all actively maintained branches and supported Minecraft versions.

Last verified: **2026-11-28** | Verification toolchain: Gradle 8.14/9.9.2, OpenJDK 17, 21 & 25.

---

## 1. Active Development & Support Branches

| Branch | Minecraft Line | Mod Version | Default Compile Target | Java Baseline | Artifact Strategy | Status |
|---|---|---|---|---|---|---|
| [`main`](https://github.com/kizio806/item-display-control) | **26.3** | `1.0.0` | Minecraft 26.3 | **Java 25** | Single artifact per loader | **Active (Primary)** |
| [`support/26.2.x`](https://github.com/kizio806/item-display-control/tree/support/26.2.x) | **26.2** | `1.0.0` | Minecraft 26.2 | **Java 25** | Single artifact per loader | **LTS Support** |
| [`support/26.1.x`](https://github.com/kizio806/item-display-control/tree/support/26.1.x) | **26.1.x** (26.1 – 26.1.2) | `1.0.0` | Minecraft 26.1.2 | **Java 25** | Unified line artifact per loader | **LTS Support** |
| [`support/1.21.9-1.21.11`](https://github.com/kizio806/item-display-control/tree/support/1.21.9-1.21.11) | **1.21.9 – 1.21.11** | `1.0.0` | Minecraft 1.21.11 | **Java 21** | Unified line artifact per loader | **LTS Support** |
| [`support/1.21.0-1.21.8`](https://github.com/kizio806/item-display-control/tree/support/1.21.0-1.21.8) | **1.21.0 – 1.21.8** | `1.0.0` | Minecraft 1.21.8 | **Java 21** | Unified line artifact per loader | **LTS Support** |
| [`support/1.20.5-1.20.6`](https://github.com/kizio806/item-display-control/tree/support/1.20.5-1.20.6) | **1.20.5 – 1.20.6** | `1.0.0` | Minecraft 1.20.6 | **Java 21** | Unified line artifact per loader | **LTS Support** |
| [`support/1.20.2-1.20.4`](https://github.com/kizio806/item-display-control/tree/support/1.20.2-1.20.4) | **1.20.2 – 1.20.4** | `1.0.0` | Minecraft 1.20.4 | **Java 17** | Unified line artifact per loader | **LTS Support** |
| [`support/1.20.0-1.20.1`](https://github.com/kizio806/item-display-control/tree/support/1.20.0-1.20.1) | **1.20.0 – 1.20.1** | `1.0.0` | Minecraft 1.20.1 | **Java 17** | Unified line artifact per loader | **LTS Support** |

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
| 17 | `support/1.21.0-1.21.8` | **Fabric** | **1.21.0** | Fabric API `>=0.100.8`<br>Range `>=1.21.0 <1.21.9` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.0-1.21.8-fabric.jar` | **VERIFIED** |
| 18 | `support/1.21.0-1.21.8` | **Fabric** | **1.21.8** | Fabric API `>=0.100.8`<br>Range `>=1.21.0 <1.21.9` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.0-1.21.8-fabric.jar` | **VERIFIED** |
| 19 | `support/1.21.0-1.21.8` | **NeoForge** | **1.21.0** | NeoForge `21.0.167+`<br>Range `[21.0,21.9)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.0-1.21.8-neoforge.jar` | **VERIFIED** |
| 20 | `support/1.21.0-1.21.8` | **NeoForge** | **1.21.8** | NeoForge `21.8.57+`<br>Range `[21.0,21.9)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.21.0-1.21.8-neoforge.jar` | **VERIFIED** |
| 21 | `support/1.20.5-1.20.6` | **Fabric** | **1.20.5** | Fabric API `>=0.97.7`<br>Range `>=1.20.5 <1.20.7` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.5-1.20.6-fabric.jar` | **VERIFIED** |
| 22 | `support/1.20.5-1.20.6` | **Fabric** | **1.20.6** | Fabric API `>=0.97.7`<br>Range `>=1.20.5 <1.20.7` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.5-1.20.6-fabric.jar` | **VERIFIED** |
| 23 | `support/1.20.5-1.20.6` | **NeoForge** | **1.20.5** | NeoForge `20.5.21-beta+`<br>Range `[20.5,20.7)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.5-1.20.6-neoforge.jar` | **VERIFIED** |
| 24 | `support/1.20.5-1.20.6` | **NeoForge** | **1.20.6** | NeoForge `20.6.144`<br>Range `[20.5,20.7)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.5-1.20.6-neoforge.jar` | **VERIFIED** |
| 25 | `support/1.20.2-1.20.4` | **Fabric** | **1.20.2** | Fabric API `>=0.91.0`<br>Range `>=1.20.2 <1.20.5` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.2-1.20.4-fabric.jar` | **VERIFIED** |
| 26 | `support/1.20.2-1.20.4` | **Fabric** | **1.20.4** | Fabric API `>=0.91.0`<br>Range `>=1.20.2 <1.20.5` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.2-1.20.4-fabric.jar` | **VERIFIED** |
| 27 | `support/1.20.2-1.20.4` | **NeoForge** | **1.20.2** | NeoForge `20.2.86+`<br>Range `[20.2,20.5)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.2-1.20.4-neoforge.jar` | **VERIFIED** |
| 28 | `support/1.20.2-1.20.4` | **NeoForge** | **1.20.4** | NeoForge `20.4.253`<br>Range `[20.2,20.5)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.2-1.20.4-neoforge.jar` | **VERIFIED** |
| 29 | `support/1.20.0-1.20.1` | **Fabric** | **1.20.0** | Fabric API `>=0.83.0`<br>Range `>=1.20.0 <1.20.2` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.0-1.20.1-fabric.jar` | **VERIFIED** |
| 30 | `support/1.20.0-1.20.1` | **Fabric** | **1.20.1** | Fabric API `>=0.83.0`<br>Range `>=1.20.0 <1.20.2` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.0-1.20.1-fabric.jar` | **VERIFIED** |
| 31 | `support/1.20.0-1.20.1` | **NeoForge** | **1.20.0** | NeoForge/Forge `47.x`<br>Range `[47,48)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.0-1.20.1-neoforge.jar` | **VERIFIED** |
| 32 | `support/1.20.0-1.20.1` | **NeoForge** | **1.20.1** | NeoForge/Forge `47.3.0`<br>Range `[47,48)` | PASS (`-Werror`) | `item-display-control-1.0.0-mc1.20.0-1.20.1-neoforge.jar` | **VERIFIED** |

---

## 3. Architecture & Technical Boundary Justification

### Minecraft 1.20.2–1.20.4 vs 1.20.0–1.20.1 Hard Platform Split
A separate release line is required between Minecraft 1.20.1 and 1.20.2 due to fundamental platform divergence:
1. **Forge to NeoForge Rebrand & Package Split:**
   - In Minecraft 1.20.0–1.20.1, NeoForge was a transitional fork of Forge 47, using the legacy `net.minecraftforge.*` package namespace, `META-INF/mods.toml` manifest, and legacy plugin `net.neoforged.moddev.legacyforge`.
   - In Minecraft 1.20.2, NeoForge completely severed ties with the old Forge codebase, adopting `net.neoforged.*`, `META-INF/neoforge.mods.toml`, and native Mojang official runtime mappings.
2. **Vanilla `Screen.renderBackground` Signature Change:**
   - In 1.20.0–1.20.1, `Screen.renderBackground(DrawContext)` took a single parameter. In 1.20.2, Mojang expanded it to 4 parameters `(DrawContext, int, int, float)`.
3. **Conclusion:** `1.20.2–1.20.4` constitutes a cohesive, unified line spanning all minor revisions of the re-architected NeoForge 20.x runtime prior to the Java 21 engine transition.

### Minecraft 1.20.5/1.20.6 vs 1.20.4 Hard Binary Boundary
A separate release line is required between Minecraft 1.20.4 and 1.20.5 due to runtime and platform architecture breaks:
1. **Java Runtime Requirement:**
   - Minecraft 1.20.5 raised the Java runtime requirement to **Java 21** (class bytecode version 65).
   - Minecraft 1.20.4 and earlier vanilla installations execute on **Java 17** (class bytecode version 61). Bytecode compiled for Java 21 causes `UnsupportedClassVersionError` on 1.20.4 clients.
2. **NeoForge Event Model Architecture:**
   - NeoForge 20.6 replaced legacy `TickEvent.ClientTickEvent` with `net.neoforged.neoforge.client.event.ClientTickEvent.Post`. NeoForge 20.4 lacks `ClientTickEvent.Post`, while NeoForge 20.6 removed `TickEvent`.
3. **Internal Component Rewrite:**
   - Minecraft 1.20.5 underwent a major engine rewrite introducing the item component architecture.
4. **Conclusion:** Unifying 1.20.4 and 1.20.5 into a single binary is impossible without unsafe reflection. `1.20.5–1.20.6` constitutes a natural, unified binary compatibility boundary.

### Minecraft 1.21.8 vs 1.21.9 Hard Binary Boundary
A separate release line is required between Minecraft 1.21.8 and 1.21.9 due to breaking upstream API and platform changes:
1. **KeyBinding/KeyMapping Category API:**
   - In Minecraft 1.21.9 ("The Copper Age"), Mojang introduced typed categories: `KeyBinding.Category` (Fabric) and `KeyMapping.Category` (NeoForge), replacing `String category` parameters in `KeyBinding`/`KeyMapping` constructors.
   - Running 1.21.9 bytecode on 1.21.8 or earlier throws `NoSuchMethodError` or `NoSuchFieldError`.
2. **NeoForge FMLEnvironment Dist Access:**
   - In NeoForge 21.9+, `FMLEnvironment.getDist()` method was introduced and direct public field access to `FMLEnvironment.dist` was restricted/deprecated.
3. **Conclusion:** `1.21.9–1.21.11` and `1.21.0–1.21.8` represent the two natural, mathematically minimal release boundaries covering the 1.21.x series.

### Unified 1.21.0–1.21.8 Single Release Architecture
The entire range from 1.21.0 through 1.21.8 is packaged into **exactly ONE release**:
- **Fabric Intermediary Parity:** Both `isFallFlying()` and `isGliding()` remap to the exact same descriptor in Fabric Intermediary.
- **NeoForge Binary Compatibility:** Throughout NeoForge 21.0 to 21.8, player interaction events and keymappings maintain binary compatibility.

---

## 4. Verification Procedures

To reproduce verification for any branch:

```bash
# Verify formatting, source hygiene, and structure
./gradlew spotlessApply validateStructure validateSourceHygiene

# Run full unit and integration test suite
./gradlew test --rerun

# Run strict release qualification gates
./gradlew strictCheck publishReadyCheck buildAll
```
