# Changelog

All notable changes to this project should be documented in this file.

## 1.2.0

### Added
- Updated mod support for Minecraft 26.2 for Fabric and NeoForge.

### Compatibility
- Minecraft: 26.2
- Fabric: Loader 0.18.4+ / Fabric API 0.159.0+26.2
- NeoForge: 26.2.0+



### Added
- Updated mod to target Minecraft 26.1.x.
- Migrated codebase to utilize Java 25 and official Mojang mappings natively for all loaders.

### Changed
- Standardized Gradle build environment, updating to Gradle 9.7.1 and Loom 1.15.5.
- Migrated Fabric GUI implementations to modern extractRenderState APIs.
- Migrated NeoForge GUI implementations to modern extractRenderState APIs.
- Refactored identifier resolution to use natively available Mojang mappings.
- Cleaned up obsolete GameTest implementations targeting old game versions.



### Added
- Introduced the initial multi-loader release for Fabric and NeoForge.
- Added per-target protection toggles for item frames, pots, lecterns, jukeboxes, campfires, composters, respawn anchors, and armor stands.
- Added GUI-managed whitelist and blacklist item filters.

### Changed
- Standardized build, release, and publication metadata around the `jump-delay-fix` project structure.
- Expanded validated game version matrix for both Fabric and NeoForge loaders to Minecraft 1.21.9 - 1.21.11.
