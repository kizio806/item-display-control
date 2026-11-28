# Release Management & Versioning Standards

This document defines the authoritative versioning architecture, multi-branch release lifecycle, multi-loader packaging invariants, platform publishing standards, and validation rules for **Item Display Control**.

---

## 1. Authoritative Versioning Policy

Item Display Control follows an engineering-grade versioning policy based on [Semantic Versioning 2.0.0](https://semver.org/) combined with SemVer build metadata for Minecraft compatibility lines.

### Version Format

```text
MAJOR.MINOR.PATCH+mc<MINECRAFT_RANGE>
```

Examples:
- `1.0.0+mc26.3.x` (active on `main`)
- `1.0.0+mc26.2.x` (active on `support/26.2.x`)
- `1.0.0+mc26.1.x` (active on `support/26.1.x`)
- `1.0.0+mc1.21.9-1.21.11` (active on `support/1.21.9-1.21.11`)

### Increment Semantics

- **MAJOR (`X.0.0`)**: Incremented when introducing breaking changes affecting users, public APIs, configuration persistence schemas, or public integration contracts.
- **MINOR (`x.Y.0`)**: Incremented when introducing backward-compatible new functionality, new protection targets, or platform capabilities.
- **PATCH (`x.y.Z`)**: Incremented for backward-compatible bug fixes, performance optimizations, security hardening, and maintenance updates.

*Note:* Minecraft version changes or internal refactoring alone do **NOT** alter the base `MAJOR.MINOR.PATCH` sequence. Minecraft compatibility is captured exclusively in the `+mc<RANGE>` metadata suffix.

---

## 2. Critical Global Synchronization Rule

The defining principle of this versioning architecture is **global base version synchronization**:

> **Every active support branch must use the exact same base project version.**

```text
main:                    1.0.0+mc26.3.x
support/26.2.x:          1.0.0+mc26.2.x
support/26.1.x:          1.0.0+mc26.1.x
support/1.21.9-1.21.11:  1.0.0+mc1.21.9-1.21.11
```

### Backport and Parity Mandate

1. When a new feature, bug fix, or security update is introduced on `main`, the change must be adapted and backported across all active support branches (`support/26.2.x`, `support/26.1.x`, `support/1.21.9-1.21.11`).
2. When a version increment occurs (e.g., from `1.0.0` to `1.0.1` or `1.1.0`), the increment is applied to **all** active branches in lockstep.
3. **Never** independently increment the base version on a single branch.
4. **Never** publish a higher base version for one active branch while leaving another active branch on an older base version.
5. If a platform-specific API difference prevents equivalent implementation on an older Minecraft line, stop and document the technical limitation rather than silently creating version divergence.

---

## 3. Branching Strategy & Lifecycle

The repository maintains dedicated long-term branches corresponding to distinct Minecraft compatibility lines:

| Branch | Minecraft Line | Status | Maintenance Policy |
| :--- | :--- | :--- | :--- |
| `main` | Minecraft 26.3.x (26.3+) | **Active (Primary)** | Receives all new features, core architecture updates, and fixes. |
| `support/26.2.x` | Minecraft 26.2.x | **Active Support** | Synchronized features, backported bug fixes, and security updates. |
| `support/26.1.x` | Minecraft 26.1.x (26.1–26.1.2) | **Active Support** | Synchronized features, backported bug fixes, and security updates. |
| `support/1.21.9-1.21.11` | Minecraft 1.21.9–1.21.11 | **Active Support** | Synchronized features, backported bug fixes, and security updates. |

### End-of-Life (EOL) Policy

- A Minecraft compatibility line remains supported as long as it has meaningful community adoption or until upstream toolchains deprecate essential dependencies.
- Retiring a support branch requires explicit deprecation announcement in `CHANGELOG.md` .
- Historical tags and releases for retired lines are preserved indefinitely and are never deleted or rewritten.

---

## 4. Production Artifacts & Naming Standard

Every active Minecraft compatibility line produces **EXACTLY TWO production artifacts**:
1. **Fabric JAR**: `item-display-control-<BASE_VERSION>-<MC_METADATA>-fabric.jar`
2. **NeoForge JAR**: `item-display-control-<BASE_VERSION>-<MC_METADATA>-neoforge.jar`

Examples on `main`:
- `item-display-control-1.0.0-mc26.3.x-fabric.jar`
- `item-display-control-1.0.0-mc26.3.x-neoforge.jar`

Examples on `support/1.21.9-1.21.11`:
- `item-display-control-1.0.0-mc1.21.9-1.21.11-fabric.jar`
- `item-display-control-1.0.0-mc1.21.9-1.21.11-neoforge.jar`

### Multi-Version JAR Invariant
A single JAR covers its entire compatibility range:
- **Fabric (`fabric.mod.json`)**: Declares `fabric_minecraft_version_range` covering all patch versions in the line (e.g. `>=26.3 <26.4` or `>=1.21.9 <1.21.12`).
- **NeoForge (`neoforge.mods.toml`)**: Declares `minecraft_version_range` covering all patch versions in the line (e.g. `[26.3,26.4)` or `[1.21,1.22)`).

---

## 5. Git Tags and GitHub Releases

Every Minecraft compatibility line is released independently, pointing to the exact commit on its respective branch.

### Git Tag Convention

```text
v<BASE_VERSION>+mc<MINECRAFT_RANGE>
```

Examples:
- `v1.0.0+mc26.3.x`
- `v1.0.0+mc26.2.x`
- `v1.0.0+mc26.1.x`
- `v1.0.0+mc1.21.9-1.21.11`

### GitHub Release Title Convention

```text
v<BASE_VERSION> — Minecraft <MINECRAFT_RANGE>
```

Examples:
- `v1.0.0 — Minecraft 26.3.x`
- `v1.0.0 — Minecraft 26.2.x`
- `v1.0.0 — Minecraft 26.1.x`
- `v1.0.0 — Minecraft 1.21.9–1.21.11`

Each GitHub Release contains **only** the artifacts built for its compatibility line:
- 1 Fabric JAR
- 1 NeoForge JAR
- Associated SHA-256 checksums (`FABRIC-SHA256SUMS.txt`, `NEOFORGE-SHA256SUMS.txt`)

---

## 6. Modrinth & Platform Publishing Conventions

To prevent launcher confusion and preserve clean update channels:

1. **Independent Loader Entries**: Every release publishes separate entries for Fabric and NeoForge.
   - Fabric Version ID: `<BASE_VERSION>+mc<MINECRAFT_RANGE>.fabric` (e.g. `1.0.0+mc26.3.x.fabric`)
   - NeoForge Version ID: `<BASE_VERSION>+mc<MINECRAFT_RANGE>.neoforge` (e.g. `1.0.0+mc26.3.x.neoforge`)
2. **Platform Game Versions**: The published version lists all Minecraft patch versions verified by the line (e.g. `['1.21.9', '1.21.10', '1.21.11']`).
3. **Prohibited Patterns**:
   - Never combine Fabric and NeoForge into a single release entry.
   - Never publish redundant single-patch duplicate JARs.
   - Never publish external platform metadata that exceeds the internal constraints in `fabric.mod.json` or `neoforge.mods.toml`.

---

## 7. Automated Validation & Safety Gates

Continuous integration enforces release readiness and prevents accidental divergence:

```bash
# 1. Run full verification gates and build artifacts
./gradlew strictCheck publishReadyCheck buildAll

# 2. Test release readiness in dry-run mode
./scripts/release.sh --dry-run
```

The verification pipeline automatically enforces:
- SemVer compliance with mandatory `+mc<RANGE>` metadata (`verifySemverVersion`).
- Multi-version matrix consistency across loader ranges (`verifyMinecraftVersionMatrix`).
- Deterministic production artifact naming and existence (`verifyReleaseArtifacts`).
- Bytecode inspection and client safety contracts (`FabricMetadataContractTest`, `NeoForgeMetadataContractTest`).
- Source hygiene, comment standards, and spotless formatting (`validateSourceHygiene`, `spotlessCheck`).

### Explicit Release Trigger

Releases are **never** triggered by normal pushes to branches. Publication occurs exclusively through:
1. Pushing an annotated Git tag matching `v*+mc*`.
2. Manual workflow execution via GitHub Actions `workflow_dispatch`.
