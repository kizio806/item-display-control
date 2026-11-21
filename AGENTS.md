# Item Display Control — AI Agent Guidelines & Architecture Manual

This document defines the mandatory operating standards, behavioral guidelines, and development lifecycle for AI agents and human engineers working on the **Item Display Control** codebase.

Future AI agents working in this repository must operate as **experienced senior developers and principal software architects**: questioning assumptions, verifying facts against bytecode and test execution, avoiding shortcuts, and upholding strict technical excellence.

---

## 1. Core Engineering Philosophy & Behavioral Rules

### Mindset & Responsibilities
- **Question Existing Decisions:** Never assume existing code or structure is correct merely because it is already committed. Always verify what the build system compiles and ensure root cleanliness (zero stray `.class` files in the root).
- **Search for Clean Solutions:** Investigate clean abstractions rather than duplicating Minecraft client logic between `fabric/` and `neoforge/`.
- **Prefer Maintainable & Simple Solutions:** Do not introduce heavy frameworks, reflection tricks, bytecode manipulation, or third-party dependencies when simple, pure-Java algorithms suffice.
- **Explain Trade-offs Explicitly:** When proposing changes, outline the pros, cons, and performance implications.
- **Avoid Shortcuts:** Never bypass `./gradlew strictCheck`, never disable quality gates, never suppress compiler warnings (`-Xlint:all -Werror` is enforced), and never leave uncommitted scratch files.

### Critical "Do NOT" Rules
- **Do NOT use Mixins:** Accessor/invoker mixins are strictly banned by `scripts/validate-structure.sh`. The mod relies exclusively on clean client interaction events.
- **Do NOT add stray binary files to root:** Never decompile or place `.class` files into the root directory.
- **Do NOT add trivial or redundant comments:** Meaningful explanatory comments are permitted when clarifying non-obvious algorithms or contracts, but redundant comments that merely restate the code, `TODO`/`FIXME`/`XXX` tags, and wildcard imports are prohibited.
- **Do NOT suppress warnings:** `@SuppressWarnings` and warning-silencing annotations of any kind are strictly forbidden. Resolve deprecations, type mismatches, and linter warnings at the source.
- **Do NOT bypass constructor dependency injection with global static state:** Domain logic must receive dependencies via constructor injection rather than querying static singleton accessors.

---

## 2. Architecture & Module Boundaries

1. **`:common` (Java 25):**
   - Loader-agnostic business logic.
   - Target resolution (`ProtectionTargetResolver`, `ProtectionTarget`).
   - Configuration management (`ItemDisplayConfigStorage`, `ItemDisplayRuntimeConfig`).
   - Internationalization keys (`TranslationKeys`).
   - Feedback interfaces (`ToggleFeedback`).
   - Must remain strictly independent of loader-specific APIs (`net.fabricmc`, `net.neoforged`).
2. **`:fabric` (Fabric Loom):**
   - Fabric client event handlers (`FabricInteractionGuards`, `UseBlockCallback`, `UseEntityCallback`).
   - Keybindings and ModMenu integration.
   - Screen wrappers (`FabricSettingsScreen`).
3. **`:neoforge` (NeoForge ModDev):**
   - NeoForge event subscribers (`NeoForgeInteractionGuards`, `InputEvent.InteractionKeyMappingTriggered`).
   - Keybindings (`NeoForgeKeyMappings`).
   - Screen wrappers (`NeoForgeSettingsScreen`).

---

## 3. Mandatory Quality Gate

Before concluding any task or committing changes, execute the verification suite:

```bash
# 1. Structural layout and source hygiene
./gradlew validateStructure validateSourceHygiene

# 2. Automated formatting check
./gradlew spotlessCheck

# 3. Unit and contract test suite
./gradlew test

# 4. Comprehensive strict quality check
./gradlew strictCheck
```

All commands must pass with exit code 0.
