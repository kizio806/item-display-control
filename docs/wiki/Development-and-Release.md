# Development and Release

## Repository Layout

- `common` -> loader-agnostic config, rule evaluation, and shared interaction behavior
- `fabric` -> Fabric bootstrap, keybinds, interaction guards, and UI integration
- `neoforge` -> NeoForge bootstrap, keybinds, interaction guards, and UI integration
- `scripts` -> version probing and metadata update helpers
- `docs/wiki` -> source pages for GitHub Wiki
- `.github/workflows` -> CI and release automation

## Local Build

Requirements:

- Java `21`
- Gradle wrapper

Build commands:

```bash
./gradlew --no-daemon clean buildAll
./gradlew --no-daemon publishReadyCheck
```

`publishReadyCheck` includes:

- SemVer validation for `mod_version`
- Minecraft version matrix validation
- Common tests
- Fabric runtime dependency compatibility validation
- Fabric production metadata validation
- NeoForge production metadata validation
- NeoForge dedicated server safety validation

## Multi-Version Update Flow

1. Probe candidate Minecraft versions:

```bash
./scripts/probe-minecraft-versions.sh --versions "1.21.9,1.21.10,1.21.11,1.21.12"
```

2. Update metadata consistently:

```bash
./scripts/set-minecraft-version.sh \
  --base-minecraft 1.21.11 \
  --fabric-supported-versions "1.21.11" \
  --neoforge-supported-versions "1.21.9,1.21.10,1.21.11" \
  --mod-version 1.0.0
```

3. Verify:

```bash
./gradlew --no-daemon clean buildAll publishReadyCheck
```

## Release Process

Release is tag-driven using `.github/workflows/release.yml`.

1. Update `CHANGELOG.md`
2. Commit changes
3. Create tag `vX.Y.Z`
4. Push tag

Example:

```bash
git add -A
git commit -m "chore(release): v1.0.0"
git tag v1.0.0
git push origin main --tags
```

Pipeline steps:

- Build and verification
- Release notes generation from git history
- GitHub Release with Fabric and NeoForge jars plus checksums
- Modrinth publication when secrets are configured

## Required Secrets

- `MODRINTH_TOKEN`
- `MODRINTH_PROJECT_ID`

No secrets should be hardcoded in repository files.

## Versioning Policy

- Semantic Versioning: `MAJOR.MINOR.PATCH`
- Use `PATCH` for fixes and compatibility updates
- Use `MINOR` for backward-compatible feature additions
- Use `MAJOR` for breaking config or behavior changes
