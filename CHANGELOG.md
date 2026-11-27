# Changelog

All notable changes to this project will be documented in this file.

## 1.0.0+mc26.2.x

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

### Changed
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
- Minecraft: 26.2
- Fabric: Loader 0.18.4+ / Fabric API 0.159.0+26.2
- NeoForge: 26.2.0.79+
- Java: 25
