# agentpim

Java/Maven project. See `pom.xml` for build and dependencies (Java 25, JUnit 5, AssertJ).

## Module structure

This is a multi-module Maven reactor, deliberately built as hexagonal
architecture (ports & adapters) from the start:

- **`agentpim-core`** — the hexagon center: the fixed-timestep `Simulation`
  engine, the `SimulationEntity` interface, and the Dominion ECS integration
  (`EcsWorld`). Knows nothing about specific unit types (no "aircraft",
  "F-16", or "tank" in here) and nothing about how entities get defined or
  controlled from the outside (no JSON, no REST, no MCP).
- **`agentpim-app`** — the composition root: the runnable entry point that
  wires adapters into the core and produces the executable jar.
- Adapter modules are added as they're built, one per external technology
  (e.g. `agentpim-adapter-json` for JSON-defined unit templates,
  `agentpim-adapter-rest`, `agentpim-adapter-mcp`). Each depends on
  `agentpim-core`'s application layer (ports); `agentpim-core` never depends
  on an adapter.
- Shared build config (Java version, dependency versions, Checkstyle,
  plugin versions) lives in the root `pom.xml`; child modules inherit it and
  only declare what's specific to them.

## Testing conventions

- Test method names follow **Given-When-Then**, e.g. `givenEmptyList_whenSizeCalled_thenReturnsZero()`.
- Every test body has exactly three sections, in this order, separated by a single blank line:
  1. Given — setup/initialization
  2. When — the execution/action under test
  3. Then — assertions
- No comments inside test code, ever — not even to label the Given/When/Then sections. The blank lines are the only separators.
- Use AssertJ for all assertions (`assertThat(...)`), not plain JUnit assertions. Use its richer API where it fits — e.g. `assertThat(list).containsExactly(...)`, `.hasSize(...)`, `.extracting(...)` — instead of asserting on raw sizes or looping manually.
- No parameterized tests (`@ParameterizedTest`). Write every case out as its own explicit test method.
- Mock as little as possible. Only introduce a mock (Mockito or otherwise) when a real collaborator can't reasonably be used in the test (e.g. network, filesystem, time, external services). Prefer real objects.

## General coding conventions

- Write genuinely object-oriented code — behavior belongs on objects, not in static helpers operating on data bags.
- Apply design patterns where they earn their keep. In particular, prefer the **Strategy pattern** over large if-else/switch cascades that branch on type or mode.
