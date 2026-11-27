# Testing Standards

This document defines testing strategies, architectural boundaries, and coverage policies for **Item Display Control**.

---

## 🎯 Testing Philosophy

We uphold high testing standards to ensure stability across Minecraft and mod loader versions:

- **Meaningful Behavioral Tests:** Every test must validate observable contracts, domain logic, edge-case conditions, or regressions. We strictly prohibit fake tests written solely to boost line coverage numbers.
- **Fast JVM Execution:** Domain logic in `:common` is 100% decoupled from Minecraft and mod loader runtime classes. The full unit test suite runs in under 2 seconds without launching Minecraft.
- **Dedicated Server Safety:** Loader modules undergo constant-pool bytecode inspection to ensure zero client-only class references exist in server-reachable paths.

---

## 🧪 Test Suites Overview

### 1. `:common` Unit Tests
Located in `common/src/test/java/com/kizio/itemdisplaycontrol/common/`:
- **`ItemDisplayControlTest` & `ItemDisplayControlEngineTest`:** Validates master toggle states, target resolution, sneak bypass logic, and whitelist/blacklist precedence rules.
- **`ItemDisplayConfigStorageTest` & `ItemDisplayRuntimeConfigTest`:** Verifies configuration parsing, default values, atomic disk persistence, and debounced writes.
- **`ProtectionTargetTest`:** Validates target categorization and identifier resolution.
- **`ToggleFeedbackTest`:** Verifies feedback interface contracts and listener invocations.
- **`LocalizationCompletenessTest`:** Enforces that all 10 language JSON resource files match `en_us.json` in key presence and format placeholders.

### 2. Loader Contract Tests
Located in `fabric/src/test/java/` and `neoforge/src/test/java/`:
- **`FabricKeyMappingsTest` & `NeoForgeKeyMappingsTest`:** Confirms safe keybinding handling when uninitialized.
- **`FabricStatusMessagesTest` & `NeoForgeStatusMessagesTest`:** Verifies safe actionbar messaging when the Minecraft client player instance is not yet ready.

---

## 📊 Running Tests & Quality Verification

```bash
# Run all unit and integration tests
./gradlew test

# Generate JaCoCo coverage reports
./gradlew jacocoTestReport

# Enforce quality gates and coverage verification
./gradlew strictCheck
```

Coverage reports are generated at:
- `common/build/reports/jacoco/test/html/index.html`
- `fabric/build/reports/jacoco/test/html/index.html`
- `neoforge/build/reports/jacoco/test/html/index.html`
