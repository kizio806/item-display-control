# Installation and Compatibility

## Requirements

- Minecraft Java Edition
- Java `25`
- `Fabric Loader` plus Fabric API, or `NeoForge`

## Current Compatibility

- Active patch line: `26.2`
- Fabric validated game versions: `26.2`
- NeoForge validated game versions: `26.2`
- Published shared Modrinth versions: `26.2`
- Fabric range in metadata: `>=26.2 <26.3`
- NeoForge range in metadata: `[26.2,26.3)`

If you try to run on a newer patch that is not mapped or validated yet, the build metadata checks are designed to block release until support is confirmed.

## One-JAR Multi-Version Strategy

This project follows the same multi-version publishing model as `jump-delay-fix`: one release may target multiple patch versions in the same Minecraft line when mappings, loaders, and runtime behavior remain compatible.

The strategy is enforced by Gradle checks:

- `verifyMinecraftVersionMatrix`
- `publishReadyCheck`

## Install from GitHub Releases

1. Open https://github.com/kizio806/item-display-control/releases
2. Pick the latest release
3. Download the correct loader artifact: `itemdisplaycontrol-fabric-...jar` or `itemdisplaycontrol-neoforge-...jar`
4. Put the jar in your `mods` folder
5. Start Minecraft

## Install from Modrinth

1. Open the Modrinth project page after the public listing is live
2. Choose the correct Minecraft version and loader
3. Download the latest compatible file
4. Put the jar in your `mods` folder
5. Start Minecraft

## Verify Correct Setup

- The mod appears in the mods list
- Keybinds are visible in controls under `Item Display Control`
- Default keys respond in-game: `J` for toggle and `O` for settings
- On Fabric, Mod Menu shows a config button when Mod Menu is installed

## Common Installation Mistakes

- Using the wrong loader jar for your instance
- Missing required loader or Fabric API dependency
- Using an unsupported Minecraft patch version
- Running with an outdated Java runtime instead of Java 25
