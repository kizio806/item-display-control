# Configuration Persistence and Concurrency Safety

## Purpose
`item-display-control` writes client configuration and server-specific profile memory to disk on disconnect, screen close, and JVM shutdown. If configuration writing is not atomic, a crash could corrupt the user's config file. Furthermore, client tick events and keybinding input handlers must handle concurrent profile cycles and toggles without deadlocks or race conditions.

## Standard
- **Atomic Disk Writes:** When saving configuration in `ConfigStorage`, always write first to a temporary file (`Files.createTempFile`) and replace the target file atomically using `StandardCopyOption.ATOMIC_MOVE` (with fallback to `REPLACE_EXISTING` where atomic moves are not supported by the underlying filesystem).
- **Server ID Encoding:** Server identifiers (IPs, domains, ports) can contain characters invalid for standard Java Properties keys (`:`, `=`, spaces). Always encode server keys using Base64 (`encodeServerId`) before saving, and decode safely on load.
- **Shutdown Hook Safety:** The JVM shutdown hook in `ItemDisplayControl` must catch and swallow non-fatal runtime exceptions to prevent crashing the game shutdown process.
- **Thread Safety:** `ItemDisplayControl` is accessed from the client main render thread, interaction events, and shutdown threads. All configuration mutations must use proper synchronization or atomic references.

## Do:
```java
// Correct: Safe atomic writing pattern
Path temporaryFile = Files.createTempFile(targetDirectory, "itemdisplaycontrol-", ".tmp");
try (OutputStream output = Files.newOutputStream(temporaryFile)) {
    properties.store(output, "ItemDisplayControl configuration");
    try {
        Files.move(temporaryFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    } catch (AtomicMoveNotSupportedException exception) {
        Files.move(temporaryFile, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
```

## Don't:
```java
// Bad: Directly truncating and writing into the target config file
try (OutputStream output = Files.newOutputStream(filePath)) {
    properties.store(output, null); // If the game crashes mid-write, config is corrupt!
}
```

## Verification:
An AI agent must run the configuration unit tests:
```bash
./gradlew :common:test --tests "com.kizio.itemdisplaycontrol.common.config.*"
```
Output must pass with all tests successful.
