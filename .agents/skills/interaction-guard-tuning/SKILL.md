---
name: interaction-guard-tuning
description: Safely adjust item placement guards, target resolution, and bypass vector projections without breaking vanilla interaction semantics.
---

# Interaction Guard & Bypass Tuning

## Overview
`item-display-control` prevents unintended item insertions into decorative and utility blocks (item frames, armor stands, lecterns, chiseled bookshelves, etc.) while providing smart bypasses for intentional player actions.

## Guidelines
1. **Never Interfere with Sneak (Shift):**
   - If player is sneaking (`player.isShiftKeyDown()`), bypass all protection and allow vanilla interaction.
2. **Adjacent Placement Bypass:**
   - When holding a placeable block item against a protected target, use `ProjectionMath.clampToBlock` and `ProjectionMath.projectAxis` to project the placement coordinate to the adjacent block face.
3. **Loader Event Interception:**
   - On Fabric: Intercept `UseBlockCallback` and `UseEntityCallback`.
   - On NeoForge: Intercept `PlayerInteractEvent.RightClickBlock` and `PlayerInteractEvent.EntityInteract`.
4. **No Side Effects on Server:**
   - The mod is 100% client-side. Never send fake packets or alter server state directly.
