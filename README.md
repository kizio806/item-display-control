# Item Display Control

[![CI](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml/badge.svg)](https://github.com/kizio806/item-display-control/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/kizio806/item-display-control?sort=semver)](https://github.com/kizio806/item-display-control/releases)
[![Modrinth](https://img.shields.io/badge/Modrinth-release%20ready-00AF5C?logo=modrinth)](docs/MODRINTH.md)
[![License](https://img.shields.io/github/license/kizio806/item-display-control)](LICENSE)

Client-side Minecraft mod that blocks accidental item placement into item frames, glow item frames, armor stands, flower pots, decorated pots, chiseled bookshelves, shelf-like blocks, lecterns, jukeboxes, campfires, composters, respawn anchors, and other common insertion targets.

The repository follows the same GitHub-facing structure used across `jump-delay-fix` and `event-hub`: explicit maintainer metadata, top-level technical docs, `docs/wiki` source pages, and release/publication guides kept inside the repo.

## Highlights

- Global on/off toggle
- In-game settings screen
- Per-target protection toggles
- Global whitelist and blacklist item filters in the GUI
- Sneak bypass for intentional interactions
- Adjacent block placement bypass when you are holding a placeable block
- Client-only implementation for Fabric and NeoForge

## Supported Platforms

- Fabric Loader
- NeoForge

Current version metadata is managed in `gradle.properties`:

- `minecraft_version`: base compile target
- `fabric_game_versions`: Fabric validated game versions
- `neoforge_game_versions`: NeoForge validated game versions
- `modrinth_game_versions`: shared Fabric/NeoForge versions published on Modrinth
- `fabric_minecraft_version_range`: Fabric runtime support range
- `minecraft_version_range`: NeoForge runtime support range

Current validated support line:

- Fabric: `1.21.11`
- NeoForge: `1.21.9`, `1.21.10`, `1.21.11`

## Architecture

Multi-project Gradle layout:

- `common`: shared interaction rules, config, and target protection logic
- `fabric`: Fabric bootstrap, keybinds, UI integration, and client adapters
- `neoforge`: NeoForge bootstrap, keybinds, UI integration, and client adapters

Design goals:

- Shared behavior in `common`
- Thin loader integration layers
- No server-side requirement for client-only features

## Config

Runtime settings are saved to:

```text
config/itemdisplaycontrol.properties
```

Every protected target has its own boolean toggle, so you can disable only the categories you do not want.
Whitelist and blacklist item filters are global across all enabled targets.

## Build

Requirements:

- Java `21`
- Gradle wrapper (`./gradlew`)

Main commands:

```bash
./gradlew --no-daemon clean buildAll strictCheck
./gradlew --no-daemon publishReadyCheck
```

`publishReadyCheck` runs:

- Structure and contract validation (`validateStructure`)
- SemVer validation (`mod_version`)
- Minecraft version matrix validation
- Common tests + coverage threshold verification
- Fabric runtime and metadata checks
- NeoForge production metadata checks
- NeoForge dedicated-server safety checks

`strictCheck` runs the hard quality gate used by CI and release:

- `publishReadyCheck`
- `validateSourceHygiene` (`src/main/java` + `src/test/java`):
  no `//` or `/* */` comments, no `TODO`/`FIXME`/`XXX`, no wildcard imports
- Java compilation with `-Xlint:all -Werror`

## Release Workflow

Tag-based release (`vX.Y.Z`) is fully automated by `.github/workflows/release.yml`:

1. Build + verify artifacts
2. Generate release changelog from git history
3. Publish GitHub Release with Fabric/NeoForge jars + SHA256 checksums
4. Publish to Modrinth (if secrets are configured)

Required repository secrets:

- `MODRINTH_TOKEN`
- `MODRINTH_PROJECT_ID`

## Project Docs

- Technical docs index: [docs/Home.md](docs/Home.md)
- Wiki home: [docs/wiki/Home.md](docs/wiki/Home.md)
- Code style: [docs/Code-Style.md](docs/Code-Style.md)
- Architecture assessment: [docs/Architecture-Assessment.md](docs/Architecture-Assessment.md)
- Release guide: [docs/RELEASES.md](docs/RELEASES.md)
- Modrinth guide: [docs/MODRINTH.md](docs/MODRINTH.md)
- Changelog: [CHANGELOG.md](CHANGELOG.md)
- Contributing: [CONTRIBUTING.md](CONTRIBUTING.md)
- Security: [SECURITY.md](SECURITY.md)
