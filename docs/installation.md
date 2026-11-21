# Installation Guide — Item Display Control

Item Display Control is a client-side Minecraft mod available for both **Fabric** and **NeoForge**. It protects players from accidentally inserting items into item frames, pots, lecterns, jukeboxes, campfires, armor stands, and shelf-like blocks.

---

## ☕ System Requirements

- **Minecraft Java Edition:** Supported versions include 1.21.9–1.21.11, 26.1.x, and 26.2.x.
- **Java Runtime:**
  - Minecraft 26.x: **Java 25** (bundled by modern launchers)
  - Minecraft 1.21.x: **Java 21**
- **Mod Loader:** Either [Fabric Loader](https://fabricmc.net/) (>= 0.18.4) or [NeoForge](https://neoforged.net/)

---

## 📥 Fabric Installation

1. Install **Fabric Loader** for your target Minecraft version via the official installer.
2. Download the matching **Fabric API** from [Modrinth](https://modrinth.com/mod/fabric-api) and place it into your `.minecraft/mods` directory.
3. *(Optional but recommended)* Install **[ModMenu](https://modrinth.com/mod/modmenu)** to access configuration screens directly from the in-game mods menu.
4. Download `itemdisplaycontrol-fabric-<version>.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
5. Place the downloaded `.jar` file into your `.minecraft/mods` directory.
6. Launch Minecraft using your Fabric profile.

---

## 📥 NeoForge Installation

1. Install **NeoForge** for your target Minecraft version via the official installer.
2. Download `itemdisplaycontrol-neoforge-<version>.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
3. Place the downloaded `.jar` file into your `.minecraft/mods` directory.
4. Launch Minecraft using your NeoForge profile. Access the in-game settings screen with the default hotkey (**`O`**) or through NeoForge's Mods menu.

---

## 🌐 Server Compatibility & Client Safety

- **Client-Side Only:** Item Display Control runs entirely on the client. It does not require server-side installation and is 100% compatible with Vanilla, Paper, Purpur, Spigot, and Folia servers.
- **Sneak Bypass:** Holding the Sneak key (default `Shift`) temporarily bypasses protection, allowing intentional item placement without toggling settings.
- **Adjacent Block Placement:** When holding a placable block, clicking on a protected container safely places the block against the clicked face instead of inserting the item into the container.
