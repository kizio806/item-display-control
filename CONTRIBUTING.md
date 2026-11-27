# Contributing to Item Display Control

Thank you for your interest in contributing to **Item Display Control**! This guide outlines our architecture, development setup, code hygiene standards, and pull request workflow to help you get started quickly.

---

## 🎯 Architecture & Invariants

Item Display Control is a lightweight, 100% client-side Minecraft mod designed to prevent accidental item insertions. The codebase enforces strict architectural separation:

1. **`:common` Module (Pure Java Library):**
   - Contains core interaction rules and protection resolution (`ProtectionTargetResolver`, `ProtectionTarget`), configuration management (`ItemDisplayRuntimeConfig`, `ItemDisplayConfigStorage`), and feedback interfaces (`ToggleFeedback`).
   - **Strict Invariant:** Must remain 100% loader-agnostic and Minecraft-free. Never import `net.fabricmc.*`, `net.neoforged.*`, or `net.minecraft.*` in `:common`. This is permanently verified by structure validation checks.
   - **Constructor Dependency Injection:** Core components must receive dependencies (such as `ItemDisplayRuntimeConfig`) via constructor injection rather than querying static singleton accessors.

2. **`:fabric` and `:neoforge` Modules (Thin Platform Adapters):**
   - Provide client event listeners (`UseBlockCallback` and `UseEntityCallback` on Fabric, `InteractionKeyMappingTriggered` on NeoForge), keybindings, and settings screens.
   - **No Mixins:** The mod does **NOT** use Mixins (accessors and invokers are banned by `scripts/validate-structure.sh`).
   - **Dedicated Server Safety:** Non-client classes in `:neoforge` must never reference client-only packages (verified by `:neoforge:verifyDedicatedServerSafe`).

3. **Single-JAR Multi-Version Packaging:**
   - Every release branch compiles **exactly one Fabric JAR** and **exactly one NeoForge JAR** covering the full supported patch line via `./gradlew buildAll`. Never create patch-fragmented JARs.

---

## 🗺️ Supported Branches & Version Matrix

Development occurs on `main` and dedicated support branches covering major Minecraft versions:

| Branch | Minecraft Line | Supported Minecraft Versions | Java Baseline |
|---|---|---|---|
| **`main`** | 26.3 | 26.3 | **Java 25** |
| **`support/26.2.x`** | 26.2 | 26.2 | **Java 25** |
| **`support/26.1.x`** | 26.1 | 26.1, 26.1.1, 26.1.2 | **Java 25** |
| **`support/1.21.9-1.21.11`** | 1.21 | 1.21.9, 1.21.10, 1.21.11 | **Java 21** |

---

## 🛠️ Local Development Setup

### 1. Prerequisites
- **Git**
- **JDK 21 or 25** (matching the branch you are working on, e.g. Eclipse Temurin)

### 2. Clone the Repository
```bash
git clone https://github.com/kizio806/item-display-control.git
cd item-display-control
```

### 3. Verification Commands Explained

Run the following Gradle commands to validate your changes:

| Command | Purpose & Checks Performed |
|---|---|
| `./gradlew spotlessApply` | Formats all Java source files and build scripts to Spotless standards (4 spaces, Unix line endings, clean imports). |
| `./gradlew test` | Executes all unit, integration, and architecture contract tests across all modules (`:common`, `:fabric`, `:neoforge`). |
| `./gradlew strictCheck` | Executes full release-readiness quality gates: validates source hygiene (no TODOs/suppressions/wildcard imports), directory structure, SemVer compliance, version matrices, metadata contracts, and dedicated-server safety. |
| `./gradlew buildAll` | Compiles and packages both production release JARs and sources JARs for Fabric and NeoForge. |

---

## 🧹 Code Hygiene Standards (Hard Gates)

Every pull request must pass the automated source hygiene checks (`./gradlew validateSourceHygiene`):

- **Zero Suppression Annotations:** `@SuppressWarnings` is strictly forbidden. Resolve warnings, deprecations, and type issues at the source.
- **Zero Wildcard Imports:** `import java.util.*;` is prohibited. Use explicit single-class imports.
- **Zero Task Markers:** No `TODO`, `FIXME`, or `XXX` tags in committed Java code.
- **Compiler Flags:** Compiles under Java with `-Xlint:all` and `-Werror`. Do not introduce compiler warnings.
- **Meaningful Tests:** Never write tests solely to boost line coverage. Every test must validate real behavior, observable contracts, boundary values, error handling, or regressions.

---

## 📝 Commit Conventions

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <short description>
```

**Common types:**
- `feat`: New user-facing or runtime feature
- `fix`: Bug fix or edge-case correction
- `refactor`: Internal code improvement without behavior change
- `test`: Adding or strengthening behavioral tests
- `build`: Gradle build scripts, dependencies, or toolchains
- `ci`: GitHub Actions workflows or pipeline hardening
- `docs`: Documentation, wiki, or markdown guides

**Examples:**
```
fix(guards): prevent accidental item frame interaction when holding food items
test(config): add round-trip test for delimited item filter lists
docs(readme): document verification commands in building from source section
```

---

## 🚀 Pull Request Workflow

1. **Create a Branch:**
   ```bash
   git checkout -b fix/issue-description
   # or
   git checkout -b feat/feature-description
   ```
2. **Implement & Test:**
   - Make your changes adhering to project standards.
   - Run `./gradlew spotlessApply` to ensure formatting compliance.
   - Run `./gradlew test strictCheck buildAll` to verify all quality gates pass locally.
3. **Open a Pull Request:**
   - Target `main` for new features and current 26.x changes.
   - For bug fixes affecting older Minecraft versions, specify in the PR description which `support/*` branches require backporting.
   - Complete all items in the [Pull Request Template](.github/pull_request_template.md).
