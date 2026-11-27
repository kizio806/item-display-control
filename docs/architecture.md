# Architecture Assessment

Assessment date: `2026-11-27`

## Executive Verdict

`item-display-control` has a technically sound architecture and is a coherent long-term project.

Why:

- core runtime logic stays in loader-agnostic `common`
- Fabric and NeoForge modules act as focused platform adapters
- release and compatibility gates are automated through Gradle + CI
- test, metadata, structure, and publication checks pass on the current matrix

## Scope And Validation

This assessment was validated locally with:

```bash
./gradlew --no-daemon test
./gradlew --no-daemon publishReadyCheck
```

Result on `2026-11-27`: both commands completed successfully.

## Repository Consistency & Structural Standards

This repository maintains strict engineering consistency through:

- canonical maintainer, release, and security policies (`CONTRIBUTING.md`, `SECURITY.md`)
- unified documentation and wiki layout (`docs/` and `docs/wiki/`)
- automated version-matrix verification and SemVer quality gates
- pre-publication structure validation gate (`validateStructure`)
- explicit line-ending, formatting, and editor rules (`.gitattributes`, `.editorconfig`, Spotless)

## Multi-Loader Artifact & Packaging Contract

Item Display Control enforces a strict **Single JAR per Loader** architectural invariant across all supported Minecraft lines:
- **Module Separation:** Core interaction rules and target resolution (`ProtectionTargetResolver`, `ProtectionTarget`), configuration management (`ItemDisplayRuntimeConfig`, `ItemDisplayConfigStorage`), and feedback interfaces (`ToggleFeedback`) reside exclusively in `:common` as pure Java. No Minecraft or loader classes may be imported in `:common`.
- **Thin Platform Adapters:** `:fabric` and `:neoforge` provide only thin event listeners (`UseBlockCallback` / `UseEntityCallback` on Fabric, `InteractionKeyMappingTriggered` on NeoForge), keybinding registration, and configuration screen adapters.
- **Single-JAR Multi-Version Packaging:** Rather than fragmenting releases into multiple patch-specific JARs, each loader module compiles a single production artifact (`item-display-control-<base_mc>-<ver>-fabric.jar` and `item-display-control-<base_mc>-<ver>-neoforge.jar`) that covers the entire patch line via verified dependency ranges (`fabric_minecraft_version_range` and `minecraft_version_range`).
- **Platform Publication Parity:** Modrinth and GitHub Releases always receive two separate, clean releases per tag (`<version>-fabric` and `<version>-neoforge`), preventing launcher ambiguity while maintaining unified download management.

## Technical Strengths

- clear package naming (`com.kizio.itemdisplaycontrol`)
- deterministic configuration lifecycle with asynchronous debounce and flush-on-shutdown safeguards
- explicit target model (`ProtectionTarget`) with isolated resolver logic and item filter evaluations
- dedicated-server safety checks for NeoForge constant-pool bytecode references

## Risks To Watch

- newly introduced Minecraft blocks or decor entities in future major updates require target resolver updates
- upstream Minecraft loader/toolchain releases may require periodic metadata range updates
- Gradle 10 compatibility should be tracked (Gradle 10 deprecation warnings are present in build output)

## Recommended Next Quality Milestones

1. Keep loader contract tests active for each supported Minecraft patch update.
2. Extend integration assertions for adjacent face placement vector calculations under edge angle interactions.
3. Track and resolve Gradle deprecations ahead of the Gradle 10 release.
