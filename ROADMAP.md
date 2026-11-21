# Project Roadmap — Item Display Control

This document tracks current accomplishments, active development goals, and future directions for **Item Display Control**.

---

## Current Release (v1.2.x — Decoupling & Projection Engine)
- [x] **Decoupled Architecture**: Isolated `ItemDisplayControlEngine` state from the static facade.
- [x] **Pure Math Projection**: Extracted `ProjectionMath` to handle coordinate clamping and axis adjustments independently of Minecraft dependencies.
- [x] **Strict Verification**: Enforced Google Java Format via Spotless, ArchUnit module boundary rules, and JaCoCo coverage gates.
- [x] **Multi-Branch Support**: Unified the engine across `main` (26.3), `support/26.2.x`, `support/26.1.x`, and `support/1.21.9-1.21.11`.

---

## Upcoming Milestones

### Phase 2: User Experience Polish (v1.3.0)
- [ ] **Interactive Visual Feedback**: Optional subtle particle or action-bar feedback when an item insertion is blocked.
- [ ] **Enhanced Filter Presets**: Built-in default filter profiles (e.g. Builder, Redstone, Survival).
- [ ] **Config GUI Improvements**: Searchable item list with icon rendering inside the in-game settings menu.

### Phase 3: Minecraft Modernization (v1.4.0)
- [ ] **Upstream Release Tracking**: Rapid alignment with new Minecraft snapshots and experimental features.
- [ ] **Automated Headless Interaction Tests**: Headless client smoke testing to verify click interception across loader updates.
