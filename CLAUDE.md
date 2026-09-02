# agentpim

Java/Maven project. See `pom.xml` for build and dependencies (Java 17, JUnit 5, AssertJ).

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
