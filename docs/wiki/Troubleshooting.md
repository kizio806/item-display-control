# Troubleshooting & FAQ

This guide provides solutions to common issues, configuration problems, and error messages when running Item Display Control.

---

## 🔍 Frequently Encountered Issues

### 1. Protection does not trigger or items are still placed into containers
- **Cause:** Mod is toggled off, player is sneaking, target category is disabled, or active item whitelist/blacklist settings allow the interaction.
- **Solution:**
  1. Press the toggle key (**`J`** by default) to ensure Item Display Control is **ON** (check the actionbar message).
  2. Ensure you are not holding down the Sneak key (`Shift`), as sneaking intentionally bypasses all protection.
  3. Open settings (**`O`** by default) and confirm that the target category checkbox (e.g. Item Frames, Flower Pots, Lecterns) is enabled.
  4. If filter lists are enabled, verify that your held item is not in the allowed list or excluded from the blacklist.

---

### 2. Keybinds conflict with another mod
- **Default Keybinds:**
  - **`J`** — Toggle Mod ON / OFF (`key.itemdisplaycontrol.toggle`)
  - **`O`** — Open Settings Screen (`key.itemdisplaycontrol.config`)
- **Solution:**
  1. Go to **Options → Controls → Key Binds**.
  2. Scroll to the **Item Display Control** category.
  3. Reassign the conflicting keys or press `ESC` to unbind them.

---

### 3. How do I completely reset all settings?
- **In-Game Method:**
  1. Open the settings screen by pressing **`O`** (or through ModMenu / NeoForge Mod List).
  2. Click **Reset to Defaults**.
  3. All target toggles and filter lists will be restored to factory defaults immediately.
- **Manual Method:**
  1. Close Minecraft.
  2. Navigate to your `.minecraft/config/` folder.
  3. Delete `itemdisplaycontrol.properties`.
  4. Restart Minecraft.

---

### 4. Game crashes on launch with `UnsupportedClassVersionError` or `ClassNotFoundException`
- **Cause:** Incompatible Java runtime version or mismatched mod loader.
- **Solutions:**
  - **Check Java Version:**
    - Minecraft **26.x** requires **Java 25**.
    - Minecraft **1.21.x** requires **Java 21**.
    - Make sure your launcher instance is configured to use the appropriate JDK.
  - **Check Loader Compatibility:**
    - If you are running **Fabric**, make sure you installed `item-display-control-<base>-<range>-fabric.jar` and have [Fabric API](https://modrinth.com/mod/fabric-api) installed.
    - If you are running **NeoForge**, make sure you installed `item-display-control-<base>-<range>-neoforge.jar`.
    - Do NOT place both Fabric and NeoForge JARs in the same `mods/` directory.

---

### 5. Settings screen doesn't open from the Mods list
- **On Fabric:** Fabric does not have a built-in mods GUI menu. You need to install [ModMenu](https://modrinth.com/mod/modmenu) to access the settings button in the mods list, or simply press the **`O`** key in-game.
- **On NeoForge:** Open **Mods → Item Display Control → Config**, or press **`O`** in-game.

---

### 6. Where can I find diagnostic logs?
Item Display Control logs initialization events with the `itemdisplaycontrol` tag:
1. Open `.minecraft/logs/latest.log`.
2. Search for `Item Display Control`.
3. You should see log lines like:
   ```
   [Client thread/INFO] [itemdisplaycontrol]: Item Display Control Fabric client initialized
   ```

---

## 💬 Getting Further Help

If you encounter an issue not covered here:
- Open an issue on our [GitHub Issue Tracker](https://github.com/kizio806/item-display-control/issues).
- Include your Minecraft version, mod loader version, Item Display Control version, and relevant excerpts from `.minecraft/logs/latest.log`.
