# Item Display Control Wiki

Welcome to the official **Item Display Control** documentation and wiki.

**Item Display Control** is a lightweight, **100% client-side** Minecraft mod designed to prevent accidental item placements into display, storage, and utility blocks on both **Fabric** and **NeoForge**.

---

## 📚 Wiki Directory

| Page | Description |
| :--- | :--- |
| **[User Guide](User-Guide.md)** | Controls (`J`, `O`), protected targets, sneak & adjacent placement bypasses, item filter rules, and in-game settings. |
| **[Configuration](Configuration.md)** | Explains `.minecraft/config/itemdisplaycontrol.properties`, per-target properties, item filtering precedence, and persistence. |
| **[Installation & Compatibility](Installation-and-Compatibility.md)** | Supported Minecraft versions (1.21.9–1.21.11, 26.1.x, 26.2.x, 26.3), Java runtime requirements (Java 21 vs 25), and installation guides. |
| **[Gameplay & Mechanics Safety](Gameplay-and-Fair-Play.md)** | Deep dive into how client-side interaction interception works, adjacent placement mathematics, and fair play compliance. |
| **[Troubleshooting & FAQ](Troubleshooting.md)** | Solutions for placement issues, keybinding conflicts, Java version errors, and configuration resets. |
| **[Development & Release](Development-and-Release.md)** | Architecture overview, multi-branch workflow, Gradle build commands, strict test gates, and automated release pipeline. |

---

## ⚡ Quick Reference

### In-Game Keybinds (Defaults)

| Key | Action | Details |
| :---: | :--- | :--- |
| **`J`** | **Toggle Mod** | Instantly turns Item Display Control ON or OFF. |
| **`O`** | **Open Settings** | Directly opens the in-game configuration screen. |

> *All keybinds are fully rebindable in Minecraft's **Options → Controls → Key Binds** under the **Item Display Control** category.*

### Protection Targets Overview

- **Item Frames & Glow Frames:** Blocks accidental item insertion and rotation.
- **Armor Stands:** Prevents swapping equipped armor or held weapons.
- **Flower & Decorated Pots:** Blocks planting flowers or inserting items into decorated pots.
- **Chiseled Bookshelves & Shelves:** Prevents placing books or items into shelf slots.
- **Lecterns & Jukeboxes:** Blocks swapping books or inserting music discs.
- **Campfires, Composters & Respawn Anchors:** Prevents placing raw food, dumping compostables, or accidental anchor charging.

---

## 🌐 Project Links

- **Source Code:** [GitHub Repository](https://github.com/kizio806/item-display-control)
- **Mod Releases:** [GitHub Releases](https://github.com/kizio806/item-display-control/releases)
- **Modrinth Project:** [Modrinth Page](https://modrinth.com/mod/item-display-control)
- **Issue Tracker:** [GitHub Issues](https://github.com/kizio806/item-display-control/issues)
