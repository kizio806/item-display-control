# 🛡️ Item Display Control

[![CI Status](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml/badge.svg)](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml)
[![SonarCloud Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=kizio806_item-display-control&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kizio806_item-display-control)
[![Codecov Coverage](https://codecov.io/gh/kizio806/item-display-control/graph/badge.svg)](https://codecov.io/gh/kizio806/item-display-control)
[![GitHub Release](https://img.shields.io/github/v/release/kizio806/item-display-control?sort=semver&logo=github)](https://github.com/kizio806/item-display-control/releases)
[![Modrinth](https://img.shields.io/badge/Modrinth-Item%20Display%20Control-00AF5C?logo=modrinth)](https://modrinth.com/mod/item-display-control)
[![License](https://img.shields.io/github/license/kizio806/item-display-control)](LICENSE)
[![Fabric](https://img.shields.io/badge/Loader-Fabric-blue?logo=fabric)](https://fabricmc.net/)
[![NeoForge](https://img.shields.io/badge/Loader-NeoForge-orange?logo=neoforge)](https://neoforged.net/)

**Item Display Control** is a lightweight, **100% client-side** Minecraft mod designed to prevent accidental item placements into display, storage, and utility blocks. Built for seamless building flow, precision decor placement, and safe inventory handling!

---

## 📖 Table of Contents

- [Features](#features)
- [Protection Targets & Block Types](#protection-targets--block-types)
- [Controls & Hotkeys](#controls--hotkeys)
- [Gameplay & Mechanics Safety](#gameplay--mechanics-safety)
- [Supported Versions & Platforms](#supported-versions--platforms)
- [Installation](#installation)
- [Configuration](#configuration)
- [Building from Source](#building-from-source)
- [Wiki & Documentation](#wiki--documentation)
- [License](#license)

---

## ✨ Features

- 🛡️ **Accidental Insertion Prevention:** In vanilla Minecraft, right-clicking near item frames, flower pots, armor stands, or chiseled bookshelves with items in hand frequently leads to unintended placement. Item Display Control intercepts and blocks these unwanted interactions.
- ⚡ **Multi-Loader Support:** Seamless compatibility with both **Fabric** and **NeoForge**.
- 🎯 **Per-Target Granular Protection:** Customize protection for each target type individually (Item Frames, Glow Frames, Armor Stands, Flower Pots, Decorated Pots, Chiseled Bookshelves, Lecterns, Jukeboxes, Campfires, Composters, Respawn Anchors).
- 🔍 **Item Whitelists & Blacklists:** Flexible item filtering system allows restricting insertion to specific item identifiers (or excluding sensitive items like diamonds or Netherite tools).
- 🧱 **Smart Adjacent Placement:** When holding a placeable block and clicking on a protected container, the mod automatically projects and places the block against the clicked adjacent face instead of inserting it.
- 👟 **Sneak Bypass:** Holding the sneak key (`Shift`) immediately bypasses all protection, allowing quick and intentional item insertion whenever needed without toggling settings.
- ⚙️ **In-Game Settings GUI:** Integrated directly into **ModMenu** (Fabric) and the **Mod Options** screen (NeoForge), or opened via hotkey (`O`), complete with a one-click **"Reset to Defaults"** action.
- 💬 **Actionbar HUD Notifications:** Non-intrusive actionbar alerts when toggling the mod on or off, fully localized across 10 languages (English, Polish, German, Spanish, French, Italian, Japanese, Portuguese, Russian, Chinese).
- 🛡️ **100% Client-Side & Pure Vanilla Compatible:** No server installation required. Works on all singleplayer worlds, Paper/Purpur, Folia, and modded multiplayer servers.

---

## 🎯 Protection Targets & Block Types

Item Display Control offers granular protection across all common Minecraft insertion targets:

| Target Category | Default State | Bypass with Sneak | Adjacent Placement | Description & Use Case |
| :--- | :---: | :---: | :---: | :--- |
| **Item Frames & Glow Frames** | Protected | Yes | Yes | Prevents accidental rotation or item insertion on wall decor. |
| **Armor Stands** | Protected | Yes | Yes | Stops accidental armor/weapon swapping when walking near display armor stands. |
| **Flower Pots & Decorated Pots** | Protected | Yes | Yes | Blocks planting flowers, saplings, or dropping loot into decorated pots. |
| **Chiseled Bookshelves & Shelves** | Protected | Yes | Yes | Blocks inserting enchanted books or items into book slots or modded shelves. |
| **Lecterns** | Protected | Yes | Yes | Prevents swapping or inserting books into podium lecterns. |
| **Jukeboxes** | Protected | Yes | Yes | Blocks accidental music disc insertion during general interactions. |
| **Campfires & Soul Campfires** | Protected | Yes | Yes | Prevents placing raw food items on active fires. |
| **Composters** | Protected | Yes | Yes | Blocks dumping crops, seeds, or valuable compostables. |
| **Respawn Anchors** | Protected | Yes | Yes | Prevents accidental glowstone charging in inappropriate dimensions. |

> 💡 **Item Filtering Precedence:** When both whitelist and blacklist are configured, the blacklist takes absolute precedence. Blacklisted items are blocked across all enabled targets regardless of whitelist presence.

---

## ⌨️ Controls & Hotkeys

All keybindings can be customized in Minecraft's **Options → Controls → Key Binds** under the **Item Display Control** category:

| Action | Default Key | Translation Key | Description |
| :--- | :---: | :--- | :--- |
| **Toggle Mod** | `J` | `key.itemdisplaycontrol.toggle` | Instantly enables or disables Item Display Control in-game. |
| **Open Settings** | `O` | `key.itemdisplaycontrol.config` | Opens the Item Display Control configuration screen directly. |

---

## 🛡️ Gameplay & Mechanics Safety

Item Display Control is engineered with strict adherence to client safety and server protocol compliance:

- **Zero Server Footprint:** Completely client-side. Does not modify server tick rates, game rules, or block state packets.
- **Adjacent Face Projection:** Mathematical raycast vector calculation accurately projects the block placement target position without packet spoofing.
- **Spectator & Sneak Immunity:** Automatically passes interaction through when sneaking, in spectator mode, or when hands are empty.
- **Server Anti-Cheat Friendly:** Compatible with **GrimAC**, **Vulcan**, **Polar**, **Matrix**, **Intave**, and **NCP**—all placement actions use standard vanilla client interaction packets.

---

## 📦 Supported Versions & Platforms

We maintain dedicated branches and published releases for every major modern Minecraft release, governed by our synchronized [Release Management & Versioning Policy](docs/releases.md):

| Minecraft Line | Version Tag | Supported Game Versions | Mod Loaders | Java Baseline | Git Branch |
| :--- | :---: | :--- | :--- | :---: | :--- |
| **26.3** *(Latest)* | `v1.0.0+mc26.3.x` | **26.3** | Fabric 0.18.4+ (Fabric API 0.160.6+)<br>NeoForge 26.3.0.3-beta+ | **Java 25** | [`main`](https://github.com/kizio806/item-display-control) |
| **26.2** | `v1.0.0+mc26.2.x` | **26.2** | Fabric 0.18.4+ (Fabric API 0.159.0+)<br>NeoForge 26.2.0.79+ | **Java 25** | [`support/26.2.x`](https://github.com/kizio806/item-display-control/tree/support/26.2.x) |
| **26.1** | `v1.0.0+mc26.1.x` | **26.1**, **26.1.1**, **26.1.2** | Fabric 0.18.4+ (Fabric API 0.155.2+)<br>NeoForge 26.1.2.103+ | **Java 25** | [`support/26.1.x`](https://github.com/kizio806/item-display-control/tree/support/26.1.x) |
| **1.21** | `v1.0.0+mc1.21.9-1.21.11` | **1.21.9**, **1.21.10**, **1.21.11** | Fabric 0.18.4+ (Fabric API 0.134.1+ / 0.138.4+ / 0.141.6+)<br>NeoForge 21.9.16+, 21.10.64+, 21.11.48+ | **Java 21** | [`support/1.21.9-1.21.11`](https://github.com/kizio806/item-display-control/tree/support/1.21.9-1.21.11) |

> 📖 **Full Compatibility & Checksums Matrix:** See [docs/compatibility-matrix.md](docs/compatibility-matrix.md) for detailed verification results across all loader-Minecraft combinations.

---

## 📥 Installation

### Fabric
1. Install [Fabric Loader](https://fabricmc.net/) for your target Minecraft version.
2. Download and place the matching [Fabric API](https://modrinth.com/mod/fabric-api) into your `.minecraft/mods` folder.
3. *(Optional)* Install [ModMenu](https://modrinth.com/mod/modmenu) to access the settings GUI from the mods list.
4. Download `item-display-control-<base>-<mc_range>-fabric.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
5. Place the JAR into `.minecraft/mods` and launch Minecraft.

### NeoForge
1. Install [NeoForge](https://neoforged.net/) for your target Minecraft version.
2. Download `item-display-control-<base>-<mc_range>-neoforge.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
3. Place the JAR into `.minecraft/mods` and launch Minecraft.

---

## ⚙️ Configuration

Settings are saved in `.minecraft/config/itemdisplaycontrol.properties`:

```properties
enabled=true
protectItemFrames=true
protectArmorStands=true
protectFlowerPots=true
protectDecoratedPots=true
protectChiseledBookshelves=true
protectLecterns=true
protectJukeboxes=true
protectCampfires=true
protectComposters=true
protectRespawnAnchors=true
whitelistEnabled=false
whitelistItems=
blacklistEnabled=false
blacklistItems=
```

- **`enabled`:** Master toggle for the entire protection engine (`true` / `false`).
- **`protect*`:** Per-target toggles for individual block and entity categories.
- **`whitelistItems` / `blacklistItems`:** Delimited list of normalized item IDs (e.g. `minecraft:netherite_sword, minecraft:elytra`).
- The configuration uses atomic file operations and asynchronous background I/O with debounce to eliminate disk stutter.

---

## 🛠️ Building from Source

### Prerequisites
- JDK 25 (for `main`, `support/26.2.x`, `support/26.1.x`) or JDK 21 (for `support/1.21.9-1.21.11`)
- Git

### Build & Verification Commands
```bash
# Clone the repository
git clone https://github.com/kizio806/item-display-control.git
cd item-display-control

# Auto-format source code to project style conventions
./gradlew --no-daemon spotlessApply

# Run test suite & architecture boundary checks
./gradlew --no-daemon test

# Run strict release qualification gates (hygiene, metadata, matrix, server safety)
./gradlew --no-daemon strictCheck

# Compile and package production JARs for all loaders
./gradlew --no-daemon buildAll
```

| Command | Purpose |
| :--- | :--- |
| `./gradlew spotlessApply` | Formats all Java source files and build scripts to Spotless standards (4 spaces, Unix newlines, clean imports). |
| `./gradlew test` | Executes unit, integration, and architecture boundary tests across all subprojects. |
| `./gradlew strictCheck` | Comprehensive release-readiness quality gate verifying source hygiene (zero TODOs/suppressions/wildcards), matrix parity, SemVer, metadata contracts, and dedicated-server safety. |
| `./gradlew buildAll` | Assembles both production release JARs and sources JARs for Fabric and NeoForge. |

---

## 📚 Wiki & Documentation

For in-depth guides and developer documentation, visit our wiki and reference docs:

- **[GitHub Wiki](https://github.com/kizio806/item-display-control/wiki)**
  - [Wiki Home](docs/wiki/Home.md)
  - [User Guide](docs/wiki/User-Guide.md)
  - [Configuration Guide](docs/wiki/Configuration.md)
  - [Installation & Compatibility Matrix](docs/wiki/Installation-and-Compatibility.md)
  - [Gameplay & Mechanics Safety](docs/wiki/Gameplay-and-Fair-Play.md)
  - [Development & Release Guide](docs/wiki/Development-and-Release.md)
  - [Troubleshooting & FAQ](docs/wiki/Troubleshooting.md)
- **Technical Documentation:**
  - [Architecture Assessment](docs/architecture.md)
  - [Building from Source](docs/building.md)
  - [Configuration Guide](docs/configuration.md)
  - [Testing Standards](docs/testing.md)
  - [Development Guidelines](docs/development.md)
  - [Installation Guide](docs/installation.md)
  - [Branch Compatibility Matrix](docs/compatibility-matrix.md)
  - [Releases Guide](docs/releases.md)
  - [Modrinth Publishing Guide](docs/modrinth.md)
  - [Troubleshooting & FAQ](docs/troubleshooting.md)
  - [Changelog](CHANGELOG.md)

- **Governance & Policy:**
  - [Contribution Guidelines](CONTRIBUTING.md)
  - [Code of Conduct](CODE_OF_CONDUCT.md)
  - [Support & Channels](SUPPORT.md)
  - [Security Policy](SECURITY.md)

---

## 📄 License

Item Display Control is licensed under the [Apache License 2.0](LICENSE).
