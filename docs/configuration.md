# Configuration Guide

Item Display Control provides a lightweight, resilient configuration system designed to persist your preferences without causing disk micro-stutters or frame drops.

---

## 📁 File Location & Format

Configuration is stored in standard Java Properties format (`key=value`) at:

```
.minecraft/config/itemdisplaycontrol.properties
```

*(On custom launchers like Prism Launcher, Modrinth App, or CurseForge, this file is located inside your instance profile's `config/` directory.)*

---

## ⚙️ Configuration Properties

Here is an example of a generated `itemdisplaycontrol.properties` file:

```properties
enabled=true
protectItemFrames=true
protectArmorStands=true
protectFlowerPots=true
protectDecoratedPots=true
protectChiseledBookshelves=true
protectLecterns=true
protectJukeboxes=true
protectCampfires=true
protectComposters=true
protectRespawnAnchors=true
whitelistEnabled=false
whitelistItems=
blacklistEnabled=false
blacklistItems=
```

### Property Breakdown

| Key | Value Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `enabled` | `boolean` (`true` / `false`) | `true` | Master toggle for the entire protection engine. |
| `protectItemFrames` | `boolean` (`true` / `false`) | `true` | Guards standard item frames and glow item frames. |
| `protectArmorStands` | `boolean` (`true` / `false`) | `true` | Guards armor stands from accidental armor/weapon swaps. |
| `protectFlowerPots` | `boolean` (`true` / `false`) | `true` | Guards standard flower pots from planting. |
| `protectDecoratedPots` | `boolean` (`true` / `false`) | `true` | Guards decorated pots from inserting items. |
| `protectChiseledBookshelves` | `boolean` (`true` / `false`) | `true` | Guards chiseled bookshelves and shelf-like blocks. |
| `protectLecterns` | `boolean` (`true` / `false`) | `true` | Guards lecterns from book placement or swapping. |
| `protectJukeboxes` | `boolean` (`true` / `false`) | `true` | Guards jukeboxes from inserting music discs. |
| `protectCampfires` | `boolean` (`true` / `false`) | `true` | Guards regular campfires and soul campfires. |
| `protectComposters` | `boolean` (`true` / `false`) | `true` | Guards composters from accidental filling. |
| `protectRespawnAnchors` | `boolean` (`true` / `false`) | `true` | Guards respawn anchors from glowstone charging. |
| `whitelistEnabled` | `boolean` (`true` / `false`) | `false` | When `true`, only items in `whitelistItems` are allowed. |
| `whitelistItems` | `String` (comma/space-separated) | `""` | List of allowed item IDs (e.g. `minecraft:netherite_sword`). |
| `blacklistEnabled` | `boolean` (`true` / `false`) | `false` | When `true`, items in `blacklistItems` are blocked. |
| `blacklistItems` | `String` (comma/space-separated) | `""` | List of blocked item IDs (e.g. `minecraft:diamond`). |

---

## 🔍 Filtering Rules & Precedence

1. **Neither Enabled (Default):** All item insertions into protected targets are blocked.
2. **Blacklist Only:** Listed items are strictly blocked; all other items can be inserted normally.
3. **Whitelist Only:** Only items matching listed IDs are permitted; all other items are blocked.
4. **Both Enabled:** The blacklist takes precedence. If an item is blacklisted, it is blocked regardless of whitelist presence.

---

## 🚀 Performance & Thread Safety

- **Asynchronous I/O:** Disk writes are dispatched to a dedicated daemon thread (`itemdisplaycontrol-config-io`), ensuring that saving settings never freezes the main Minecraft render thread.
- **Debounced Writes:** Rapid in-game adjustments (such as toggling multiple checkboxes) are debounced by 10 game ticks to avoid redundant file operations.
- **Atomic Operations:** Uses atomic file replacement (or safe temporary file fallback) to prevent file corruption in case of unexpected system power loss or crashes.
- **Shutdown & Disconnect Flush:** A registered JVM shutdown hook and world disconnect listener ensure that any pending configuration changes are saved before the game closes.

---

## 🔄 Resetting Configuration

### Method 1: In-Game Settings Screen
Open the settings screen (`O` key by default, or via ModMenu / Mod List) and click the **Reset to Defaults** button.

### Method 2: Manual File Deletion
1. Close Minecraft.
2. Navigate to your `.minecraft/config/` directory.
3. Delete `itemdisplaycontrol.properties`.
4. Launch Minecraft. The file will be regenerated with factory defaults on first save.
