# Modrinth Project & Publishing Guide

This document contains the official description markdown and release metadata for Item Display Control on [Modrinth](https://modrinth.com/mod/item-display-control).

---

## 📋 Modrinth Project Description

*(Copy the markdown below into the Modrinth project description editor)*

```markdown
# 🛡️ Item Display Control

**Item Display Control** is a lightweight, **100% client-side** Minecraft mod designed to prevent accidental item placements into display, storage, and utility blocks. Built for seamless building flow, precision decor placement, and safe inventory handling!

---

## ✨ Features

- 🛡️ **Accidental Insertion Prevention:** Intercepts and blocks unwanted right-click item insertions into item frames, pots, lecterns, jukeboxes, campfires, and armor stands.
- ⚡ **Multi-Loader Support:** Fully native builds for both **Fabric** and **NeoForge**.
- 🎯 **Per-Target Granular Protection:** Customize protection for each target category individually via the in-game settings screen.
- 🔍 **Item Whitelists & Blacklists:** Filter insertions with flexible item ID rules (e.g. block only diamonds, or permit only specific crops).
- 🧱 **Smart Adjacent Placement:** When holding a placeable block, clicking a protected container places the block against the adjacent clicked face instead of inserting it.
- 👟 **Sneak Bypass:** Hold sneak (`Shift`) to temporarily bypass protection for intentional interactions.
- ⚙️ **In-Game Settings GUI:** Integrated directly into **ModMenu** (Fabric) and **Mod Options** (NeoForge), or opened via hotkey (`O`), complete with a one-click **"Reset to Defaults"** button.
- 💬 **Actionbar HUD Notifications:** Clear, non-intrusive actionbar alerts whenever you toggle the mod, translated into 10 languages.
- 🛡️ **100% Client-Side & Pure Vanilla Compatible:** No server installation required—works on vanilla and modded servers alike without sending illegal packets.

---

## 🎯 Protection Targets & Mechanisms

Item Display Control offers granular protection across all common Minecraft insertion targets:

| Target Category | Default State | Bypass with Sneak | Adjacent Placement | Description & Use Case |
| :--- | :---: | :---: | :---: | :--- |
| **Item Frames & Glow Frames** | Protected | Yes | Yes | Prevents accidental rotation or item insertion on wall decor. |
| **Armor Stands** | Protected | Yes | Yes | Stops accidental armor/weapon swapping when walking near display armor stands. |
| **Flower Pots & Decorated Pots** | Protected | Yes | Yes | Blocks planting flowers, saplings, or dropping loot into decorated pots. |
| **Chiseled Bookshelves & Shelves** | Protected | Yes | Yes | Blocks inserting enchanted books or items into book slots or modded shelves. |
| **Lecterns** | Protected | Yes | Yes | Prevents swapping or inserting books into podium lecterns. |
| **Jukeboxes** | Protected | Yes | Yes | Blocks accidental music disc insertion during general interactions. |
| **Campfires & Soul Campfires** | Protected | Yes | Yes | Prevents placing raw food items on active fires. |
| **Composters** | Protected | Yes | Yes | Blocks dumping crops, seeds, or valuable compostables. |
| **Respawn Anchors** | Protected | Yes | Yes | Prevents accidental glowstone charging in inappropriate dimensions. |

> 💡 **Item Filtering Precedence:** When both whitelist and blacklist are configured, the blacklist takes absolute precedence. Blacklisted items are blocked across all enabled targets regardless of whitelist presence.

---

## ⌨️ Controls & Hotkeys

All keybindings can be customized in Minecraft's **Options → Controls → Key Binds** under the **Item Display Control** category:

| Action | Default Key | Translation Key | Description |
| :--- | :---: | :--- | :--- |
| **Toggle Mod** | `J` | `key.itemdisplaycontrol.toggle` | Instantly turn Item Display Control on or off in-game |
| **Open Settings** | `O` | `key.itemdisplaycontrol.config` | Open the Item Display Control configuration screen |

---

## 🛡️ Gameplay & Mechanics Safety

Item Display Control is safe and transparent for multiplayer:

- **Zero Server Footprint:** Completely client-side. Does not modify server tick rates, game rules, or block state packets.
- **Adjacent Face Projection:** Mathematical raycast vector calculation accurately projects the block placement target position without packet spoofing.
- **Spectator & Sneak Immunity:** Automatically passes interaction through when sneaking, in spectator mode, or when hands are empty.
- **Server Anti-Cheat Friendly:** Compatible with **GrimAC**, **Vulcan**, **Polar**, **Matrix**, **Intave**, and **NCP**—all placement actions use standard vanilla client interaction packets.

---

## 📦 Supported Minecraft Versions

| Release Line | Mod Version | Supported Minecraft Versions | Mod Loaders | Java Baseline |
| :--- | :---: | :--- | :--- | :---: |
| **26.3** | `1.0.0` | **26.3** | Fabric 0.18.4+ / NeoForge 26.3.0.3-beta+ | Java 25 |
| **26.2** | `1.0.0` | **26.2** | Fabric 0.18.4+ / NeoForge 26.2.0.79+ | Java 25 |
| **26.1** | `1.0.0` | **26.1**, **26.1.1**, **26.1.2** | Fabric 0.18.4+ / NeoForge 26.1.2.103+ | Java 25 |
| **1.21** | `1.0.0` | **1.21.9**, **1.21.10**, **1.21.11** | Fabric 0.18.4+ / NeoForge 21.9.16+, 21.10.64+, 21.11.48+ | Java 21 |

---

## 📥 Installation

1. Ensure you have the corresponding mod loader installed (**Fabric** + Fabric API, or **NeoForge**).
2. Download the matching JAR for your loader and Minecraft version from the Versions tab.
3. Drop the JAR into your `.minecraft/mods` folder and start Minecraft.
```

---

## ⚙️ Project Metadata & Settings

- **Environment:**
  - Client: **Required**
  - Server: **Unsupported** *(Client-side only, no server installation needed)*
- **Categories / Tags:**
  - `Quality of Life`
  - `Utility`
  - `Management`
  - `Building`
- **License:** Apache-2.0
- **Source Code Repository:** `https://github.com/kizio806/item-display-control`
- **Issue Tracker:** `https://github.com/kizio806/item-display-control/issues`
- **Wiki:** `https://github.com/kizio806/item-display-control/wiki`
