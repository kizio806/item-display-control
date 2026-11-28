# Changelog

All notable changes to this project will be documented in this file.

## 1.0.0+mc26.3.x

### Added
- **Synchronized Support Branch Parity**: Established mathematically minimal support branches covering Minecraft 1.20.0 through 26.3:
  - `main` (`1.0.0+mc26.3.x`): Minecraft 26.3+ (Java 25)
  - `support/26.2.x` (`1.0.0+mc26.2.x`): Minecraft 26.2.x (Java 25)
  - `support/26.1.x` (`1.0.0+mc26.1.x`): Minecraft 26.1.0–26.1.2 (Java 25)
  - `support/1.21.9-1.21.11` (`1.0.0+mc1.21.9-1.21.11`): Minecraft 1.21.9–1.21.11 (Java 21)
  - `support/1.21.0-1.21.8` (`1.0.0+mc1.21.0-1.21.8`): Minecraft 1.21.0–1.21.8 (Java 21)
  - `support/1.20.5-1.20.6` (`1.0.0+mc1.20.5-1.20.6`): Minecraft 1.20.5–1.20.6 (Java 21)
  - `support/1.20.2-1.20.4` (`1.0.0+mc1.20.2-1.20.4`): Minecraft 1.20.2–1.20.4 (Java 17)
  - `support/1.20.0-1.20.1` (`1.0.0+mc1.20.0-1.20.1`): Minecraft 1.20.0–1.20.1 (Java 17)
- Canonical compatibility matrix (`docs/compatibility-matrix.md`) detailing multi-branch support, loader dependencies, artifact generation strategies, and verification results across all 32 supported combinations.
- Comprehensive 9-step procedure for porting and adding new Minecraft versions documented in `docs/development.md`.
- Contract tests for Fabric and NeoForge keybindings and status messaging.
- In-game settings screen with one-click "Reset to Defaults" button for both Fabric and NeoForge.
- Granular per-target placement protection for item frames, glow frames, armor stands, flower pots, decorated pots, chiseled bookshelves, shelf-like blocks, lecterns, jukeboxes, campfires, composters, and respawn anchors.
- Global whitelist and blacklist item filters with delimiter support (commas, spaces, semicolons).
- Sneak-to-bypass functionality allowing instant intentional interaction by holding sneak (`Shift`).
- Adjacent block placement bypass automatically projecting and placing held blocks against the clicked adjacent container face.
- Actionbar HUD status notifications localized across 10 languages (English, Polish, German, Spanish, French, Italian, Japanese, Portuguese, Russian, and Simplified Chinese).

### Changed
- **Unified Global Versioning Standard**: Migrated versioning architecture to synchronized Semantic Versioning with Minecraft compatibility build metadata (`1.0.0+mc<RANGE>`).
- Zero Loader Duplication: Extracted shared client classes (`SettingsScreen`, `StatusMessages`, `ClientPlacementHelper`) to `common/src/client/java`, compiling the shared client sources directly into Fabric and NeoForge loader artifacts without redundant duplication.
- Metrics & Duplication Transparency: Removed `sonar.cpd.exclusions` from build configuration and SonarCloud analysis, ensuring honest duplication tracking across loaders.
- Added comprehensive Javadoc documentation across all public interfaces, domain models, and client helper classes.
- Enhanced loader test suites with unit and contract tests for `ClientPlacementHelper` vector projection, face targeting, and interaction guard registration.
- Standardized multi-loader release JAR outputs to `item-display-control-<BASE_VERSION>-<MC_METADATA>-fabric.jar` and `item-display-control-<BASE_VERSION>-<MC_METADATA>-neoforge.jar`.
- Fully decoupled `:common` module with zero Minecraft or loader imports, enforcing constructor dependency injection in `ItemDisplayControlEngine`.
- Asynchronous, debounced configuration persistence with atomic file replacement in `ItemDisplayConfigStorage`.
- Dedicated server safety enforced via class constant-pool bytecode inspection in `:neoforge`.
- Pinned all GitHub Actions across all workflow definitions (`ci.yml`, `codeql.yml`, `release.yml`, `sonarcloud.yml`, `wiki-sync.yml`, `labeler.yml`) to immutable full commit SHAs.

### Compatibility
- Minecraft: 26.3.0+
- Fabric: Loader 0.18.4+ / Fabric API 0.155.2+26.3
- NeoForge: 26.3.0.4+
- Java: 25
