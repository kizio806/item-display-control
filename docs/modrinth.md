# Modrinth Publishing Guide

## Short Copy

### GitHub About

```text
Client-side Minecraft mod that blocks accidental item placement into frames, pots, lecterns, jukeboxes, campfires, and other common insertion targets.
```

### Modrinth Summary

```text
Stops accidental right-click item insertion into frames, pots, lecterns, jukeboxes, campfires, armor stands, and other common targets.
```

## Long Modrinth Description

```markdown
# Item Display Control

Item Display Control is a client-side Minecraft utility mod focused on one very specific problem: accidental right-click item insertion.

If you have ever put the wrong item into an item frame, flower pot, decorated pot, lectern, jukebox, campfire, composter, respawn anchor, or armor stand by mistake, this mod blocks that interaction before it happens.

## What It Does

- Blocks risky right-click item use on protected targets
- Lets you toggle the entire mod on or off instantly
- Includes an in-game settings screen
- Lets you enable or disable protection per target type
- Supports global whitelist and blacklist item rules
- Keeps intentional interactions available by holding sneak
- Stays fully client-side, so the server does not need the mod installed

## Supported Targets

- Item frames and glow item frames
- Armor stands
- Flower pots
- Decorated pots
- Chiseled bookshelves and shelf-like blocks
- Lecterns
- Jukeboxes
- Campfires and soul campfires
- Composters
- Respawn anchors

## Smart Placement Behavior

When you are holding a placeable block, the mod does not just cancel the click. It also tries to place that block on the adjacent face instead, which helps preserve normal building flow around protected targets.

## Item Rules

The whitelist and blacklist are global across all enabled targets.

- No whitelist or blacklist enabled: protected targets reject all item insertion
- Blacklist enabled only: listed items are blocked, other items are allowed
- Whitelist enabled only: only listed items are allowed
- Both enabled: blacklisted items stay blocked, and the whitelist controls what else may be inserted

## Controls

- `J` toggles protection
- `O` opens the settings screen

On Fabric, the settings screen is also available through Mod Menu when that mod is installed.

## Compatibility

- Client-side only
- Loader support: Fabric and NeoForge
- Release metadata is driven by `gradle.properties`
- Published Modrinth versions should match `modrinth_game_versions`
```

## Recommended Tags

- Client-side
- Utility
- QoL
- Management

## Changelog Structure

Use this structure in release notes:

```markdown
## Version x.y.z

### Added
- ...

### Changed
- ...

### Fixed
- ...
```

## Release Integration

Modrinth publishing is handled by `.github/workflows/release.yml` using repository secrets.
