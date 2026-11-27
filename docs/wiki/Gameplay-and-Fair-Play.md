# Interaction Mechanics & Fair Play Guide

This document explains the technical mechanics of **Item Display Control**, how it intercepts client-side interactions, adjacent block placement mathematics, and compliance with multiplayer server rules.

---

## 🔬 How Item Display Control Operates

### The Vanilla Mechanism
In vanilla Minecraft, right-clicking on interactive containers or decor entities (such as item frames, flower pots, armor stands, or chiseled bookshelves) executes an interaction check. If the player's held item matches insertion criteria, the item is immediately consumed from hand and placed into the block or entity.

This frequently causes unintended item loss or placement during routine movement, combat, or construction.

### What Item Display Control Intercepts
Item Display Control hooks into client interaction events before network packets are dispatched:
1. It inspects whether the mod is enabled globally.
2. It verifies that the player is not sneaking (`!player.isSneaking()`) and not in spectator mode.
3. It resolves whether the targeted block or entity is an active protected target.
4. It evaluates item filter rules (whitelist / blacklist).
5. If the interaction should be blocked, the client interaction event returns `ActionResult.FAIL` or `InteractionResult.FAIL`, cancelling packet transmission to the server.
6. If the player is holding a placeable block, it calculates the adjacent block position and attempts standard block placement.

---

## 🛡️ Anti-Cheat Compatibility Matrix

Because Item Display Control operates strictly through standard client interaction event cancellations without synthetic packet spoofing, it is fully compatible with server-side validation engines:

| Anti-Cheat Solution | Tested Status | Recommended Settings | Notes |
| :--- | :---: | :---: | :--- |
| **GrimAC** | Compatible | Default | GrimAC validates block placement reach and angles. Standard raycasting ensures compliance. |
| **Vulcan** | Compatible | Default | Standard block interact and place checks pass cleanly. |
| **Polar** | Compatible | Default | Validated in standard multiplayer testing. |
| **Matrix** | Compatible | Default | Fully compatible with default interaction configs. |
| **Intave** | Compatible | Default | Raycast and interaction vectors align with vanilla bounds. |
| **NoCheatPlus (NCP)** | Compatible | Default | Standard interact checks pass cleanly. |

> [!NOTE]
> Item Display Control does not alter player reach, reach distance, or interaction angles. All raycast projections and block placement contexts adhere strictly to vanilla Minecraft geometry.

---

## 📐 Smart Adjacent Placement Mathematics

When right-clicking on a protected container while holding a placeable block item, the mod performs intelligent adjacent face projection:

1. **Hit Result Normal:** Extracts the targeted block position and the specific clicked face direction (`Direction`).
2. **Offset Calculation:** Computes the adjacent block coordinate:
   $$\vec{P}_{\text{adjacent}} = \vec{P}_{\text{target}} + \vec{D}_{\text{face}}$$
3. **Placement Validation:** Instantiates an `ItemPlacementContext` / `BlockPlaceContext` to verify whether the player can legally place a block at that location according to world bounds and player collision boxes.
4. **Placement Invocation:** If valid, standard block placement executes smoothly without placing the item inside the protected container.

---

## ⚖️ Multiplayer Server Policies

- **Is Item Display Control a Cheat?**
  No. Item Display Control does not grant illegitimate advantages, reach extension, speed, or flight. It merely acts as a client-side safety guard preventing accidental right-click insertion into decorative and storage blocks.
- **Fair Play Recommendation:**
  Always review the rules of competitive tournaments or servers you join. When in doubt, you can instantly turn off the mod by pressing **`J`**.
