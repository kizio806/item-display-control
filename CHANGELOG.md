# Changelog

All notable changes to this project will be documented in this file.

## 1.0.0+mc1.21.0-1.21.8

### Added
- Unified support for Minecraft 1.21.0 through 1.21.8 across Fabric and NeoForge under a single artifact per loader.
- Multi-version compatibility layer covering Fabric API `>=0.100.8` and NeoForge `21.0` through `21.8`.

### Changed
- Adapted Fabric keybinding categories to the Minecraft 1.21.0-1.21.8 string category standard.

## 1.0.0+mc1.21.9-1.21.11

### Added
- Canonical compatibility matrix (`docs/compatibility-matrix.md`) detailing multi-branch support, loader dependencies, artifact generation strategies, and verification results across all supported combinations.
- Comprehensive 9-step procedure for porting and adding new Minecraft versions documented in `docs/development.md`.
- Contract tests for Fabric and NeoForge keybindings and status messaging.
- In-game settings screen with one-click "Reset to Defaults" button for both Fabric and NeoForge.
- Granular per-target placement protection for item frames, glow frames, armor stands, flower pots, decorated pots, chiseled bookshelves, shelf-like blocks, lecterns, jukeboxes, campfires, composters, and respawn anchors.
- Global whitelist and blacklist item filters with delimiter support (commas, spaces, semicolons).
- Sneak-to-bypass functionality allowing instant intentional interaction by holding sneak (`Shift`).
- Adjacent block placement bypass automatically projecting and placing held blocks against the clicked adjacent container face.
- Actionbar HUD status notifications localized across 10 languages (English, Polish, German, Spanish, French, Italian, Japanese, Portuguese, Russian, and Simplified Chinese).
- Headless GameTests verifying bootstrap stability across Fabric and NeoForge game test harnesses.

### Changed
- Metrics & Duplication Transparency: Removed `sonar.cpd.exclusions` from build configuration and SonarCloud analysis, ensuring honest duplication tracking across loaders.
- Added comprehensive Javadoc documentation across all public interfaces, domain models, and client helper classes.
- Standardized multi-loader release JAR outputs to `item-display-control-<BASE_VERSION>-<MC_METADATA>-fabric.jar` and `item-display-control-<BASE_VERSION>-<MC_METADATA>-neoforge.jar`.
- Fully decoupled `:common` module with zero Minecraft or loader imports, enforcing constructor dependency injection in `ItemDisplayControlEngine`.
- Asynchronous, debounced configuration persistence with atomic file replacement in `ItemDisplayConfigStorage`.
- Dedicated server safety enforced via class constant-pool bytecode inspection in `:neoforge`.
- Pinned all GitHub Actions across all workflow definitions (`ci.yml`, `codeql.yml`, `release.yml`, `sonarcloud.yml`, `wiki-sync.yml`, `labeler.yml`) to immutable full commit SHAs.

### Compatibility
- Minecraft: 1.21.9, 1.21.10, 1.21.11
- Fabric: Loader 0.18.4+ / Fabric API 0.134.1+1.21.11
- NeoForge: 21.11.48+
- Java: 21
