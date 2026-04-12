# User Guide

## Default Keybinds

- `J` -> Toggle Item Display Control
- `O` -> Open settings

On Fabric, the same settings screen is also exposed through Mod Menu when that mod is installed.

## Protected Targets

Item Display Control can guard these targets:

- Item frames and glow item frames
- Armor stands
- Flower pots
- Decorated pots
- Chiseled bookshelves
- Shelf-like blocks whose registry path matches `shelf` or `bookshelf` naming
- Lecterns
- Jukeboxes
- Campfires and soul campfires
- Composters
- Respawn anchors

Each target category can be turned on or off independently in the settings screen.

## Core Behavior

When the mod is enabled and a target is protected, right-click item use on that target is blocked by default.

Important exceptions:

- Hold sneak to bypass protection for intentional interactions
- If you are holding a placeable block, the mod tries to place it on the adjacent block face instead of feeding it into the protected target

## Whitelist and Blacklist Rules

The whitelist and blacklist are global across all enabled targets.

- Neither mode enabled: all insertion into protected targets is blocked
- Blacklist enabled only: listed items are blocked, everything else is allowed
- Whitelist enabled only: only listed items are allowed
- Both enabled: blacklist still blocks listed items first, then whitelist decides what else is allowed

## Item List Format

The settings screen accepts item IDs separated by commas, spaces, or semicolons.

Examples:

- `minecraft:diamond, minecraft:emerald`
- `minecraft:tnt; minecraft:lava_bucket`
- `diamond emerald`

If you omit the namespace, the mod assumes `minecraft:`.

## Configuration File

- File location: `config/itemdisplaycontrol.properties`
- The file stores the global enable state, per-target toggles, and whitelist and blacklist entries

Changes are saved automatically, and pending changes are flushed when you close the settings screen or leave the game.
