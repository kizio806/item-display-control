# Troubleshooting & FAQ

---

## 🛠️ Common Issues and Solutions

### 1. Protection does not trigger or items are still placed into containers
- **Cause:** Mod is toggled off, player is sneaking, target category is disabled, or active item whitelist/blacklist settings allow the interaction.
- **Solution:**
  1. Press the toggle key (**`J`** by default) to ensure Item Display Control is **ON** (check the actionbar message).
  2. Ensure you are not holding down the Sneak key (`Shift`), as sneaking intentionally bypasses all protection.
  3. Open settings (**`O`** by default) and confirm that the target category checkbox (e.g. Item Frames, Flower Pots, Lecterns) is enabled.
  4. If filter lists are enabled, verify that your held item is not in the allowed list or excluded from the blacklist.

---

### 2. Keybindings conflict with other mods
- **Default Keybinds:**
  - **`J`** — Toggle Item Display Control (ON / OFF)
  - **`O`** — Open Settings GUI directly
- **Solution:** Open Minecraft Options → Controls → Key Binds, scroll down to the **Item Display Control** category, and reassign conflicting keys.

---

### 3. Resetting Configuration
- **In-Game:** Open the settings screen (**`O`** by default or via ModMenu / NeoForge Mod List) and click **Reset to Defaults**.
- **Manual:** Delete `.minecraft/config/itemdisplaycontrol.properties` while the game is closed.

---

### 4. Game crash on launch (`UnsupportedClassVersionError` or `ClassNotFoundException`)
- **Check Java Version:**
  - Minecraft 26.x requires **Java 25**.
  - Minecraft 1.21.x requires **Java 21**.
- **Check Loader Compatibility:**
  - Fabric requires Fabric Loader >= 0.18.4 and compatible Fabric API.
  - NeoForge requires compatible NeoForge version.
- Ensure you placed only one loader build of Item Display Control in your `mods/` directory.

---

### 5. Need more help?
- File an issue on GitHub: [https://github.com/kizio806/item-display-control/issues](https://github.com/kizio806/item-display-control/issues)
