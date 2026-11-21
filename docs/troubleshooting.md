# Troubleshooting Guide — Item Display Control

This guide addresses common issues, misconfigurations, and behavior diagnostics when using **Item Display Control**.

---

## 🔍 Mod Does Not Load

### Checklist:
- **Loader Matching:** Confirm you downloaded the artifact matching your mod loader (`fabric` for Fabric / Quilt, `neoforge` for NeoForge).
- **Fabric API:** When using Fabric, ensure the correct version of Fabric API is installed in `mods/`.
- **Java Version:** Verify your launcher uses Java 25 (or Java 21 on older Minecraft versions).
- **Game Version Compatibility:** Verify your Minecraft version is within the supported range in [Installation Guide](installation.md).

---

## 🛡️ Protection Does Not Seem to Trigger

### Checklist:
1. **Global Toggle State:** Ensure protection is toggled ON. The default toggle hotkey is **`J`** (displays an action-bar notification when pressed).
2. **Sneak Bypass Active:** Holding the sneak key (default `Shift`) intentionally bypasses all protection to let you place items intentionally. Release sneak to restore protection.
3. **Specific Category Disabled:** Open the in-game settings screen (**`O`**) and confirm the target container category (e.g. Item Frames, Flower Pots, Armor Stands, Chiseled Bookshelves) is enabled.
4. **Custom Modded Containers:** Standard vanilla targets are supported out of the box. Third-party modded blocks are recognized if their registry IDs contain keywords like `shelf` or `bookshelf`.

---

## 📦 Filter Lists (Blacklist / Whitelist) Interaction

### Rules:
- **Blacklist Only:** All items can be inserted *except* items matching IDs in the blacklist.
- **Whitelist Only:** *Only* items explicitly listed in the whitelist can be inserted; everything else is blocked.
- **Both Active:** The blacklist takes precedence. If an item is blacklisted, it is blocked regardless of whitelist presence.
- Ensure item IDs are normalized vanilla or modded identifiers (e.g. `minecraft:netherite_sword`, `minecraft:elytra`).

---

## ⚙️ Configuration File Not Saving

- The configuration file is saved at `.minecraft/config/itemdisplaycontrol.properties`.
- Ensure the game process has write permissions to the `config/` directory.
- Changes made in the settings screen flush to disk when exiting the GUI or shutting down the game normally. Force-killing the game client (`SIGKILL`) may prevent unflushed config writes.
