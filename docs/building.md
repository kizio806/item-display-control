# Building from Source

This guide details how to clone, compile, test, and package **Item Display Control** from source code.

---

## ☕ Prerequisites

- **Java Development Kit (JDK):**
  - **Java 25** (for `main`, `support/26.2.x`, `support/26.1.x`)
  - **Java 21** (for `support/1.21.9-1.21.11`)
  - We recommend [Eclipse Temurin](https://adoptium.net/) or standard OpenJDK distributions.
- **Git:** Required to clone the repository and evaluate commit metadata.
- **Internet Access:** Required on initial build to download Gradle, Minecraft game libraries, mappings, and loader dependencies.

---

## 📥 Cloning the Repository

```bash
git clone https://github.com/kizio806/item-display-control.git
cd item-display-control
```

---

## 🛠️ Build Commands

### 1. Auto-Format Code (Spotless)
```bash
./gradlew --no-daemon spotlessApply
```
Formats all Java sources and build scripts to match project style guidelines (4 spaces, Unix line endings, sorted imports).

### 2. Run Test Suite
```bash
./gradlew --no-daemon test
```
Executes unit tests and architecture boundary checks across all modules.

### 3. Comprehensive Quality Gate
```bash
./gradlew --no-daemon strictCheck
```
Executes all validation gates:
- Source hygiene (no TODOs, no suppressions, no wildcard imports).
- Directory structure and module boundary validation.
- Semantic Versioning and Minecraft matrix consistency.
- NeoForge dedicated server bytecode safety.
- Production metadata verification.

### 4. Package Production Release JARs
```bash
./gradlew --no-daemon buildAll
```
Compiles and packages release and sources JARs for all mod loaders:
- `fabric/build/libs/item-display-control-<base>-<range>-fabric.jar`
- `fabric/build/libs/item-display-control-<base>-<range>-fabric-sources.jar`
- `neoforge/build/libs/item-display-control-<base>-<range>-neoforge.jar`
- `neoforge/build/libs/item-display-control-<base>-<range>-neoforge-sources.jar`

---

## 🎮 Running in Development

To test the mod locally inside a Minecraft client:
- **Fabric:** `./gradlew :fabric:runClient`
- **NeoForge:** `./gradlew :neoforge:runClient`
