# User Guide

This guide details everything you need to know about using **Item Display Control** in Minecraft, including default controls, protected targets, smart bypasses, the settings interface, and HUD feedback.

---

## ⌨️ In-Game Controls & Keybindings

Item Display Control registers dedicated key mappings in Minecraft's standard controls system.

| Action | Default Key | Translation Key | Description |
| :--- | :---: | :--- | :--- |
| **Toggle Mod** | **`J`** | `key.itemdisplaycontrol.toggle` | Instantly toggles the mod ON or OFF in-game. |
| **Open Settings** | **`O`** | `key.itemdisplaycontrol.config` | Opens the graphical configuration interface directly. |

### Customizing Keybinds
To customize any of these keys:
1. Open Minecraft **Options → Controls → Key Binds**.
2. Scroll to the **Item Display Control** category.
3. Click any keybind and press your preferred key, or press `ESC` to unbind.

---

## 🎯 Protected Targets & Block Types

Item Display Control allows you to independently toggle protection for every common insertion target:

### 1. Item Frames & Glow Item Frames
- **Description:** Blocks right-click item insertion and subsequent frame rotation.
- **Why It Matters:** Wall decor and storage sorting labels often get altered when right-clicking nearby chests or doors with items in hand.

### 2. Armor Stands
- **Description:** Prevents equipping or swapping armor and weapons onto armor stands.
- **Why It Matters:** Walking past display armor while holding valuable weapons or armor pieces can accidentally swap items without confirmation.

### 3. Flower Pots & Decorated Pots
- **Description:** Blocks planting saplings, mushrooms, flowers, or dropping items into decorated pots.
- **Why It Matters:** Flower pots placed on countertops often absorb held items accidentally.

### 4. Chiseled Bookshelves & Shelves
- **Description:** Prevents placing books, enchanted tomes, or modded shelf items into slots.
- **Why It Matters:** Interacting with enchanting setups or library decor often results in misplaced items.

### 5. Lecterns
- **Description:** Blocks inserting books onto podium lecterns.
- **Why It Matters:** Prevents accidental book placements when right-clicking villager podiums.

### 6. Jukeboxes
- **Description:** Blocks inserting music discs into jukeboxes.
- **Why It Matters:** Accidental disc insertion triggers music playback and inventory changes.

### 7. Campfires & Soul Campfires
- **Description:** Blocks placing cookable food items onto active campfires.
- **Why It Matters:** Useful when campfires are used purely as decorative smoke chimneys.

### 8. Composters
- **Description:** Blocks dumping compostables (seeds, vegetables, leaves).
- **Why It Matters:** Prevents losing valuable food or farming supplies while working near composter blocks.

### 9. Respawn Anchors
- **Description:** Blocks inserting glowstone into respawn anchors.
- **Why It Matters:** Prevents accidental explosive charging in the Overworld or End dimensions.

---

## 🛡️ Smart Bypass Mechanics

### 1. Sneak to Bypass
Holding down the Sneak key (default: `Shift`) immediately bypasses all protection guards. When sneaking, right-clicking on any protected target behaves identically to vanilla Minecraft, allowing intentional insertion at any time.

### 2. Adjacent Block Placement
When holding a placeable block item and right-clicking on a protected container (such as a flower pot or chiseled bookshelf), the mod automatically calculates the clicked face normal and attempts to place the block against the adjacent surface. This ensures normal construction and building flow is never interrupted.

---

## 🔍 Whitelist & Blacklist Filtering Rules

You can restrict protection using item ID filters in the configuration screen:

- **Neither Enabled (Default):** All items are blocked from insertion into enabled protected targets.
- **Blacklist Only:** Only item IDs in the blacklist are blocked. All other items can be inserted freely.
- **Whitelist Only:** Only item IDs in the whitelist are permitted. All other items are blocked.
- **Both Enabled:** The blacklist takes precedence. If an item is blacklisted, it is blocked regardless of whitelist presence.

### Item ID Format
Item IDs can be entered separated by commas, spaces, or semicolons:
- `minecraft:diamond, minecraft:emerald`
- `minecraft:netherite_sword; minecraft:elytra`
- If the `minecraft:` namespace is omitted, the mod defaults to `minecraft:`.

---

## ⚙️ In-Game Settings Screen

You can access the settings screen in three ways:
1. Press the **`O`** key in-game (default keybind).
2. On **Fabric**: via **Mods → Item Display Control → Configure** (requires [ModMenu](https://modrinth.com/mod/modmenu)).
3. On **NeoForge**: via **Mods → Item Display Control → Config** directly from the built-in mod list.

### Available Options:
- **Mod State:** Toggle the entire mod `ON` or `OFF`.
- **Target Category Checkboxes:** Independently enable or disable protection for each target type.
- **Whitelist / Blacklist Toggles & Fields:** Configure item filter rules.
- **Reset to Defaults:** Restores factory configuration with a single click:
  - Enables the mod.
  - Re-enables all target categories.
  - Clears whitelist and blacklist rules.

---

## 💬 Actionbar HUD Feedback

Whenever you toggle the mod with `J`, Item Display Control displays a non-intrusive actionbar notification above your hotbar:

- Example: `Item Display Control: ON` / `Item Display Control: OFF`

Actionbar messages are fully localized in:
- English (`en_us`)
- Polish (`pl_pl`)
- German (`de_de`)
- Spanish (`es_es`)
- French (`fr_fr`)
- Italian (`it_it`)
- Japanese (`ja_jp`)
- Portuguese (`pt_br`)
- Russian (`ru_ru`)
- Chinese (`zh_cn`)
