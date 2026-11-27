# Development and Release Guide

This document describes the project architecture, developer environment setup, code hygiene standards, and automated release pipeline for Item Display Control.

---

## 🏛️ Project Architecture

The project is structured as a multi-project Gradle build:

```
item-display-control/
├── common/                  # Platform-agnostic core logic
│   └── src/main/java/com/kizio/itemdisplaycontrol/common/
│       ├── api/             # ToggleFeedback interface
│       ├── config/          # ItemDisplayRuntimeConfig and ItemDisplayConfigStorage
│       ├── engine/          # ItemDisplayControlEngine business logic
│       ├── i18n/            # TranslationKeys constants
│       ├── model/           # ProtectionTarget enum and properties
│       └── target/          # ProtectionTargetResolver
├── fabric/                  # Fabric client bootstrap, events, keybinds & GUI
└── neoforge/                # NeoForge client bootstrap, events, keybinds & GUI
```

- **`common`:** Completely decoupled from loader APIs. Must never import `net.fabricmc.*` or `net.neoforged.*`.
- **`fabric`:** Registers interaction guards via `UseBlockCallback` / `UseEntityCallback`, registers keybindings with Fabric API, and hooks into ModMenu.
- **`neoforge`:** Registers interaction guards via `InputEvent.InteractionKeyMappingTriggered`, handles NeoForge client bus key registrations, and integrates with NeoForge's mod options screen.

---

## 🌿 Multi-Branch Maintenance Strategy

We maintain dedicated long-term branches corresponding to distinct Minecraft API generations:

| Branch | Minecraft Line | Mod Version | Java Baseline | Key Dependency Targets |
| :--- | :--- | :---: | :---: | :--- |
| **`main`** | **26.3** | `1.0.0` | **Java 25** | Fabric Loader 0.18.4+, NeoForge 26.3.0.3-beta+ |
| **`support/26.2.x`** | **26.2** | `1.0.0` | **Java 25** | Fabric API 0.159.0+, NeoForge 26.2.0.79+ |
| **`support/26.1.x`** | **26.1.x** | `1.0.0` | **Java 25** | Fabric API 0.155.2+, NeoForge 26.1.2.103+ |
| **`support/1.21.9-1.21.11`** | **1.21.9–1.21.11** | `1.0.0` | **Java 21** | Fabric API 0.134.1+, NeoForge 21.9.16 – 21.11.48 |

> ⚠️ **Strict Porting Rule:** Never perform blind cherry-picks across branches. Minecraft API, mappings, and loader APIs diverge across lines. Always backport or forward-port with version-specific adaptation.

---

## 🧼 Source Code Hygiene & Rules

To maintain high code quality and prevent technical debt, the repository strictly enforces automated hygiene checks:

1. **Comment Hygiene:** High-value explanatory comments are permitted for complex algorithms or contracts, while redundant narrative comments are forbidden.
2. **No Wildcard Imports:** Explicit class imports are required (e.g., no `import java.util.*;`).
3. **No TODO Markers:** Source files must not contain `TODO`, `FIXME`, or `XXX`.
4. **No Suppressed Warnings:** `@SuppressWarnings` is strictly forbidden across all modules.
5. **Strict Compiler Flags:** All Java modules compile with `-Werror -Xlint:all`.
6. **Enforcement Scripts:**
   ```bash
   ./scripts/validate-source-hygiene.sh
   ./scripts/validate-structure.sh
   ```

---

## 🛠️ Local Build & Test Commands

### 1. Compile All Loaders
```bash
./gradlew --no-daemon clean buildAll
```
Generates release JARs:
- `fabric/build/libs/item-display-control-<base>-<range>-fabric.jar`
- `neoforge/build/libs/item-display-control-<base>-<range>-neoforge.jar`

### 2. Run Test Suite
```bash
./gradlew --no-daemon test
```
Executes unit tests across all subprojects, including config serialization, target resolution, and loader contract tests.

### 3. Run Strict Quality Gates
```bash
./gradlew --no-daemon strictCheck publishReadyCheck
```
Verifies:
- Loader metadata consistency (`fabric.mod.json`, `neoforge.mods.toml`).
- Structure and documentation completeness.
- Source hygiene rules.
- SemVer version boundaries.

---

## 🚀 Release Process & Tooling

Releases are managed using the automated release tool `scripts/release.sh`:

### 1. Dry Run Verification
Before cutting any release, perform a complete dry-run validation on the target branch:
```bash
./scripts/release.sh --dry-run
```
This performs a full clean build, runs all test suites, and verifies changelogs and git status without making any commits or tags.

### 2. Publishing a Release
When ready to publish:
```bash
./scripts/release.sh --publish
```
The script will:
1. Verify working directory cleanliness.
2. Ensure the branch matches the version in `gradle.properties`.
3. Check `CHANGELOG.md` for the current version section.
4. Execute `clean buildAll strictCheck publishReadyCheck`.
5. Create an annotated git tag `v<version>`.
6. Push the tag to GitHub.

### 3. Automated CI/CD Publishing
Once a tag is pushed, `.github/workflows/release.yml` triggers automatically:
- Builds release artifacts using the exact Java version detected from `gradle.properties`.
- Publishes a formatted GitHub Release with release notes and SHA-256 checksums.
- Publishes **two distinct, loader-separated entries** to **Modrinth**:
  1. `Item Display Control <version> for Fabric` (`<version>-fabric`, loader: `fabric`, matching full game version range).
  2. `Item Display Control <version> for NeoForge` (`<version>-neoforge`, loader: `neoforge`, matching full game version range).
- **Universal Rule:** Never combine Fabric and NeoForge into a single release entry, and never produce multiple separate JARs for different patch versions on the same branch.

---

## 📖 Wiki Synchronization

The contents of `docs/wiki/` are automatically synchronized to the live [GitHub Wiki](https://github.com/kizio806/item-display-control/wiki) on every push to `main` via `.github/workflows/wiki-sync.yml`.
