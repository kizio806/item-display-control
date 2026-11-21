# Architecture Context — Item Display Control

## Multi-Loader Structure
```text
item-display-control/
├── common/       # ProtectionTarget, ItemDisplayControlEngine, ProjectionMath, ItemDisplayRuntimeConfig (Loader-agnostic)
├── fabric/       # FabricInteractionGuards (UseBlockCallback, UseEntityCallback), FabricSettingsScreen, ModMenu
└── neoforge/     # NeoForgeInteractionGuards, NeoForgeKeyMappings, NeoForgeSettingsScreen
```

## Key Invariants
- `ItemDisplayControlEngine` is fully decoupled from static state and handles all configuration, target blocking, and whitelist evaluations.
- `ItemDisplayControl` is a convenience static facade delegating directly to `DEFAULT_ENGINE`.
- `ProjectionMath` operates purely on Java primitive types (`double`, `int`, `boolean`) and is completely isolated from Minecraft classes.
- Zero platform classes in `:common`.
