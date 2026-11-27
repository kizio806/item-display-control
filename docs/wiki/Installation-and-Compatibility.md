# Installation and Compatibility Guide

This guide provides full instructions for installing Item Display Control on Fabric and NeoForge across modern Minecraft releases.

---

## 📦 Compatibility & Version Matrix

Item Display Control provides dedicated versioned artifacts across both loaders:

| Minecraft Version | Release Line | Mod Version | Supported Loaders | Loader Requirements | Java Runtime |
| :--- | :---: | :---: | :--- | :--- | :---: |
| **26.3** | `main` | **`1.0.0`** | **Fabric**<br>**NeoForge** | Fabric Loader >= 0.18.4 (Fabric API >= 0.160.6)<br>NeoForge >= 26.3.0.3-beta | **Java 25** |
| **26.2** | `support/26.2.x` | **`1.0.0`** | **Fabric**<br>**NeoForge** | Fabric Loader >= 0.18.4 (Fabric API >= 0.159.0)<br>NeoForge >= 26.2.0.79 | **Java 25** |
| **26.1 – 26.1.2** | `support/26.1.x` | **`1.0.0`** | **Fabric**<br>**NeoForge** | Fabric Loader >= 0.18.4 (Fabric API >= 0.155.2)<br>NeoForge >= 26.1.2.103 | **Java 25** |
| **1.21.9 – 1.21.11** | `support/1.21.9-1.21.11` | **`1.0.0`** | **Fabric**<br>**NeoForge** | Fabric Loader >= 0.18.4 (Fabric API >= 0.134.1)<br>NeoForge 21.9.16 – 21.11.48 | **Java 21** |

> 🔗 For the complete technical compatibility matrix and artifact checksums, see [docs/compatibility-matrix.md](https://github.com/kizio806/item-display-control/blob/main/docs/compatibility-matrix.md).

---

## ☕ Java Requirements

- **Minecraft 26.x:** Requires **Java 25** (JDK/JRE 25). Modern Minecraft launchers bundle Java 25 automatically.
- **Minecraft 1.21.x:** Requires **Java 21** (JDK/JRE 21).

---

## 📥 Installation Steps

### Option A: Fabric

1. **Install Fabric Loader:** Run the official installer from [fabricmc.net](https://fabricmc.net/) for your target Minecraft version.
2. **Install Fabric API:** Download the matching [Fabric API](https://modrinth.com/mod/fabric-api) version and place it in your `.minecraft/mods` folder.
3. *(Recommended)* **Install ModMenu:** Download [ModMenu](https://modrinth.com/mod/modmenu) to access the settings GUI directly from Minecraft's mod menu.
4. **Download Item Display Control:**
   - Grab the matching versioned JAR (`item-display-control-<base>-<mc_range>-fabric.jar`) from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
5. **Place into Mods Folder:** Move the JAR into `.minecraft/mods`.
6. **Launch Game:** Start Minecraft using your Fabric profile.

---

### Option B: NeoForge

1. **Install NeoForge:** Run the official installer from [neoforged.net](https://neoforged.net/) for your target Minecraft version.
2. **Download Item Display Control:**
   - Grab `item-display-control-<base>-<mc_range>-neoforge.jar` from [Modrinth](https://modrinth.com/mod/item-display-control) or [GitHub Releases](https://github.com/kizio806/item-display-control/releases).
3. **Place into Mods Folder:** Move the JAR into `.minecraft/mods`.
4. **Launch Game:** Start Minecraft using your NeoForge profile. Settings can be accessed via the **Mods** menu or in-game hotkey (`O`).

---

## 💻 Launcher-Specific Instructions

### Prism Launcher
1. Right-click your Minecraft instance and choose **Edit Instance**.
2. Click **Mods** in the left sidebar.
3. Click **Add Mod** (or drag and drop the downloaded `.jar`).
4. Ensure the required Fabric API / NeoForge is checked in the **Version** tab.

### Modrinth App
1. Open your profile in the Modrinth App.
2. Click **Add Content**.
3. Search for **Item Display Control** and click **Install**.

### Official Minecraft Launcher
1. Open the Minecraft Launcher and click the **Installations** tab.
2. Hover over your Fabric or NeoForge profile and click the folder icon to open `.minecraft`.
3. Open the `mods` folder and place the downloaded JAR file inside.

---

## 🌐 Server-Side Status

- **Item Display Control is 100% Client-Side.**
- **Do NOT install on dedicated servers:** The mod has no server-side component. Servers run standard vanilla, Paper, Purpur, or modded engines without requiring Item Display Control.
- Players with Item Display Control installed on their client can connect to any vanilla or modded server freely.
