# Item Display Control Wiki

Welcome to the official wiki for **Item Display Control**.

Item Display Control is a client-side quality-of-life mod for Minecraft that prevents accidental right-click item insertion into common display, storage, and utility targets.

[![CI](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml/badge.svg)](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/kizio806/item-display-control?sort=semver)](https://github.com/kizio806/item-display-control/releases)
[![License](https://img.shields.io/github/license/kizio806/item-display-control)](https://github.com/kizio806/item-display-control/blob/main/LICENSE)

## Quick Navigation

- [Installation and Compatibility](Installation-and-Compatibility.md)
- [User Guide](User-Guide.md)
- [Development and Release](Development-and-Release.md)
- [Troubleshooting](Troubleshooting.md)

## Project Snapshot

- Mod ID: `itemdisplaycontrol`
- Supported loaders: `Fabric`, `NeoForge`
- Java: `21`
- Current active Minecraft patch line: `1.21.x`
- Fabric validated game versions: `1.21.11`
- NeoForge validated game versions: `1.21.9`, `1.21.10`, `1.21.11`
- Current mod version: `1.0.0`
- Default keys: `J` for toggle, `O` for settings

Version metadata is managed in `gradle.properties` and validated during build by `publishReadyCheck`.

## What This Mod Protects

- Item frames and glow item frames
- Armor stands
- Flower pots and decorated pots
- Chiseled bookshelves and shelf-like blocks detected from registry path names
- Lecterns
- Jukeboxes
- Campfires and soul campfires
- Composters
- Respawn anchors

## What Makes This Mod Practical

- Fully client-side implementation
- Shared logic in `common` with thin loader integrations in `fabric` and `neoforge`
- Fast in-game control through keybinds and a settings screen
- Per-target enable or disable toggles
- Global whitelist and blacklist rules for item IDs
- Sneak bypass for intentional interactions
- Adjacent block placement bypass when the held item is a block

## Important Links

- GitHub repository: https://github.com/kizio806/item-display-control
- GitHub releases: https://github.com/kizio806/item-display-control/releases
- Issues: https://github.com/kizio806/item-display-control/issues
- Release process details: https://github.com/kizio806/item-display-control/blob/main/docs/RELEASES.md
- Modrinth publishing copy: https://github.com/kizio806/item-display-control/blob/main/docs/MODRINTH.md
- Changelog: https://github.com/kizio806/item-display-control/blob/main/CHANGELOG.md
