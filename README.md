# Item Display Control

[![CI](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml/badge.svg)](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/kizio806/item-display-control?sort=semver)](https://github.com/kizio806/item-display-control/releases)
[![Modrinth](https://img.shields.io/badge/Modrinth-release%20ready-00AF5C?logo=modrinth)](docs/modrinth.md)
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

This branch (`main`) is currently targeting **Minecraft 26.2**.

- **Fabric Loader:** 0.18.4+ (Fabric API 0.159.0+26.2)
- **NeoForge:** 26.2.0.79+
- **Minecraft:** 26.2

*(See `gradle.properties` for the exact current version metadata.)*

## Installation

1. Download the latest release from the [Releases page](https://github.com/kizio806/item-display-control/releases) or [Modrinth](https://modrinth.com/mod/item-display-control).
2. Ensure you have the correct mod loader installed (Fabric + Fabric API, or NeoForge) for Minecraft 26.2.
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
- **Security:** [SECURITY.md](SECURITY.md)

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
