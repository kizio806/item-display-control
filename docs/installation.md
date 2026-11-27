# Installation Guide

Item Display Control is a client-side mod compatible with both Fabric and NeoForge.

---

## ☕ System Requirements

- **Minecraft Java Edition:** Supported versions include 1.21.9–1.21.11, 26.1.x, 26.2, and 26.3.
- **Java Runtime:**
  - Minecraft 26.x: **Java 25** (bundled by modern launchers)
  - Minecraft 1.21.x: **Java 21**
- **Mod Loader:** Either [Fabric Loader](https://fabricmc.net/) (>= 0.18.4) or [NeoForge](https://neoforged.net/)

---

## 📥 Fabric Installation

1. Install **Fabric Loader** for your target Minecraft version using the official installer.
2. Download the matching **Fabric API** from [Modrinth](https://modrinth.com/mod/fabric-api) and place it into your `.minecraft/mods` directory.
3. *(Optional but recommended)* Install **[ModMenu](https://modrinth.com/mod/modmenu)** to configure settings directly from the in-game mods list.
4. Download `item-display-control-<base>-<mc_range>-fabric.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
5. Place the downloaded `.jar` file into your `.minecraft/mods` folder.
6. Launch Minecraft using your Fabric profile.

---

## 📥 NeoForge Installation

1. Install **NeoForge** for your target Minecraft version using the official installer.
2. Download `item-display-control-<base>-<mc_range>-neoforge.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
3. Place the downloaded `.jar` file into your `.minecraft/mods` folder.
4. Launch Minecraft using your NeoForge profile. Settings can be accessed via the **Mods** menu or directly with the in-game hotkey (**`O`**).

---

## 🌐 Server Compatibility & Client Safety

- **Client-Side Only:** Item Display Control does NOT need to be installed on servers. It works when joining standard vanilla, Paper, Purpur, Folia, or modded servers.
- **Sneak Bypass:** Holding the Sneak key (default `Shift`) temporarily bypasses protection, allowing intentional item placement without changing settings.
- **Adjacent Block Placement:** When holding a placeable block, clicking on a protected container safely places the block against the clicked adjacent face instead of inserting the item into the container.
