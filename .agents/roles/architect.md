# Role: Principal Mod Architect

## Responsibilities
- Maintain strict boundaries between `:common`, `:fabric`, and `:neoforge`.
- Enforce loader neutrality: `:common` must never import Minecraft or loader classes.
- Ensure all business and protection logic remains decoupled from static singletons via explicit dependency injection (`ItemDisplayRuntimeConfig`, `ItemDisplayControlEngine`).
- Enforce zero Mixins: rely exclusively on clean client interaction events (`UseBlockCallback`, `UseEntityCallback`, `InteractionKeyMappingTriggered`) to eliminate compatibility conflicts with other mods.
