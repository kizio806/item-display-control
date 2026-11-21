# Item Display Control

[![CI](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml/badge.svg)](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/kizio806/item-display-control?sort=semver)](https://github.com/kizio806/item-display-control/releases)
[![Modrinth](https://img.shields.io/badge/Modrinth-release%20ready-00AF5C?logo=modrinth)](docs/modrinth.md)
[![Java Version](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk)](gradle.properties)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-26.3-brightgreen?logo=minecraft)](gradle.properties)
[![License](https://img.shields.io/github/license/kizio806/item-display-control)](LICENSE)

Item Display Control is a client-side Minecraft mod designed to enhance your gameplay experience by preventing accidental item placements. It blocks unintended insertions into item frames, glow item frames, armor stands, flower pots, decorated pots, chiseled bookshelves, shelf-like blocks, lecterns, jukeboxes, campfires, composters, respawn anchors, and other common insertion targets.

## Highlights

- **Global Toggle:** Easily turn the mod on or off.
- **In-Game Settings:** Intuitive GUI to manage preferences on the fly.
- **Per-Target Protection:** Toggle protection for specific block types individually.
- **Item Filters:** Configure global whitelist and blacklist item filters directly from the GUI.
- **Smart Bypasses:** 
  - Sneak to bypass protection for intentional interactions.
  - Adjacent block placement bypass when holding a placeable block.
- **Client-Side Only:** No server installation required. Works out of the box for Fabric and NeoForge.

## Supported Platforms & Versions

We maintain dedicated branches and published releases for every major modern Minecraft release, governed by our synchronized [Release Management & Versioning Policy](docs/releases.md):

| Minecraft Line | Version Tag | Supported Game Versions | Mod Loaders | Java Baseline | Git Branch |
| :--- | :---: | :--- | :--- | :---: | :--- |
| **26.3** *(Latest)* | `v2.0.0+mc26.3.x` | **26.3** | Fabric 0.18.4+ (Fabric API 0.160.6+)<br>NeoForge 26.3.0.3-beta+ | **Java 25** | [`main`](https://github.com/kizio806/item-display-control) |
| **26.2** | `v2.0.0+mc26.2.x` | **26.2** | Fabric 0.18.4+ (Fabric API 0.159.0+)<br>NeoForge 26.2.0.79+ | **Java 25** | [`support/26.2.x`](https://github.com/kizio806/item-display-control/tree/support/26.2.x) |
| **26.1** | `v2.0.0+mc26.1.x` | **26.1**, **26.1.1**, **26.1.2** | Fabric 0.18.4+ (Fabric API 0.155.2+)<br>NeoForge 26.1.2.103+ | **Java 25** | [`support/26.1.x`](https://github.com/kizio806/item-display-control/tree/support/26.1.x) |
| **1.21** | `v2.0.0+mc1.21.9-1.21.11` | **1.21.9**, **1.21.10**, **1.21.11** | Fabric 0.18.4+ (Fabric API 0.134.1+ / 0.138.4+ / 0.141.6+)<br>NeoForge 21.9.16+, 21.10.64+, 21.11.48+ | **Java 21** | [`support/1.21.9-1.21.11`](https://github.com/kizio806/item-display-control/tree/support/1.21.9-1.21.11) |

## Installation

1. Download the latest release from the [Releases page](https://github.com/kizio806/item-display-control/releases) or [Modrinth](https://modrinth.com/mod/item-display-control):
   - `item-display-control-<base>-<range>-fabric.jar` for Fabric
   - `item-display-control-<base>-<range>-neoforge.jar` for NeoForge
2. Ensure you have the correct mod loader installed (Fabric + Fabric API, or NeoForge) for your Minecraft version.
3. Place the downloaded `.jar` file into your `.minecraft/mods` folder.
4. Launch the game.

## Configuration

Runtime settings are automatically saved to:
```text
config/itemdisplaycontrol.properties
```

You can configure the mod in-game using the settings screen (default key: `O`) or manually edit the properties file. Every protected target has its own toggle, and item filters apply globally across all enabled targets.

## Building from Source

**Requirements:**
- Java 25
- Gradle wrapper (`./gradlew`)

**Commands:**
```bash
# Clean, build, and run strict checks
./gradlew --no-daemon clean buildAll strictCheck

# Run release publication checks
./gradlew --no-daemon publishReadyCheck
```

## Architecture

The project uses a multi-project Gradle layout:
- `common`: Contains shared interaction rules, configuration, and target protection logic.
- `fabric`: Fabric bootstrap, keybinds, UI integration, and client adapters.
- `neoforge`: NeoForge bootstrap, keybinds, UI integration, and client adapters.

## Project Documentation

- **Wiki:** [docs/wiki/Home.md](docs/wiki/Home.md)
- **Development & Code Style:** [docs/development.md](docs/development.md)
- **Architecture:** [docs/architecture.md](docs/architecture.md)
- **Releases Guide:** [docs/releases.md](docs/releases.md)
- **Modrinth Guide:** [docs/modrinth.md](docs/modrinth.md)
- **Changelog:** [CHANGELOG.md](CHANGELOG.md)
- **Contributing:** [CONTRIBUTING.md](CONTRIBUTING.md)
- **Code of Conduct:** [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- **Support:** [SUPPORT.md](SUPPORT.md)
- **Roadmap:** [ROADMAP.md](ROADMAP.md)
- **Security:** [SECURITY.md](SECURITY.md)

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
