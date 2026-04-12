# Troubleshooting

## Mod Does Not Load

Check:

- You installed the correct loader artifact: Fabric or NeoForge
- Your Minecraft version is in the supported range
- Java runtime is version `21`
- Fabric instances also include Fabric API

## Protection Does Not Trigger

Check:

- The mod is enabled
- You are not sneaking, because sneak intentionally bypasses protection
- The specific target category is still enabled in the settings screen
- The target is one of the currently supported categories

Not every modded block is supported automatically. Outside vanilla targets, the current implementation mainly recognizes shelf-like blocks from registry path names containing `shelf` or `bookshelf`.

## Items Still Go Into Targets

Check the active rule mode:

- With blacklist only, non-listed items are allowed
- With whitelist only, only listed items are allowed
- With both enabled, blacklist wins first and whitelist controls the rest

If you expected a blocked item to stay blocked, confirm the item ID is correct and normalized.

## A Block Did Not Place Next to the Target

The adjacent placement bypass only runs when:

- You are holding a block item
- The clicked face has a valid adjacent position
- Minecraft itself allows that placement

If normal block placement is impossible there, the mod will still block insertion without forcing an invalid placement.

## Settings Seem to Reset

Check:

- Config file path: `config/itemdisplaycontrol.properties`
- The game has permission to write to the config directory
- You closed the settings screen or exited normally so pending changes could flush

## Release Workflow Failed

Checklist:

- Tag matches `mod_version` in `gradle.properties`
- `publishReadyCheck` passes locally
- Modrinth secrets are configured in GitHub repository settings
- Artifact names are unique and present in build output

## Need Help

- Open issue: https://github.com/kizio806/item-display-control/issues
- Include Minecraft version, loader/version, mod version, logs, and reproduction steps
