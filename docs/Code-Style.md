# Code Style

## Shared Baseline

This repository follows the same repository style as `jump-delay-fix` and `event-hub`.

Practical rule:

- `jump-delay-fix` defines the baseline for release/docs structure
- `item-display-control` and `event-hub` should keep that same top-level standard unless a technical difference requires otherwise

## Package Naming

- root package: `com.kizio.itemdisplaycontrol`
- use `com.kizio.<project>` for future repositories
- keep author naming as `kizio`
- do not encode temporary account suffixes such as `kizio806` into code namespaces

## Module Boundaries

- `common` owns loader-agnostic protection rules, config, and tests
- `fabric` and `neoforge` should stay thin and adapter-oriented
- do not duplicate shared behavior across loader modules unless loader APIs force a real divergence

## Naming Rules

- use direct names over clever names
- booleans should read as predicates such as `isEnabled`, `isTargetProtected`, `shouldBypassWithSneak`
- keep `mod_id`, package names, and generated metadata aligned

## File And Repo Naming

- repository docs should expose the same top-level files across projects:
  `CHANGELOG.md`, `CONTRIBUTING.md`, `SECURITY.md`, `docs/Home.md`, `docs/Code-Style.md`, `docs/MODRINTH.md`, `docs/RELEASES.md`, `docs/wiki/*`
- docs should describe what the repository actually targets
- release automation should publish the same verified artifacts described by the README and docs

## Hard Quality Gates

- `strictCheck` is mandatory for CI and release workflows
- Java sources in `src/main/java` and `src/test/java` must not contain `//` or `/* */` comments
- Java sources must not contain `TODO`, `FIXME`, or `XXX` markers
- wildcard imports are forbidden
- compilation is enforced with `-Xlint:all -Werror`

## Documentation Style

- short sections
- no filler
- explain why a structure exists, not only what files exist
