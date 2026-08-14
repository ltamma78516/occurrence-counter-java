# Occurrence Counter

A small Java 17 implementation of the Edabit challenge **Count How Many Times An Element Is Repeated**.

## Requirements

- Count each element's occurrences.
- Support generic element types such as `String` and `Integer`.
- Reject a `null` input array with `IllegalArgumentException`.
- Treat an empty array as valid and return an empty map.
- Support `null` elements.

## Design

The implementation intentionally uses one class and one JUnit 5 test class.

`LinkedHashMap` is used for deterministic encounter order. Frequencies are counted in one pass using `Map.merge`. The entries are then sorted by frequency descending and copied into a new `LinkedHashMap`, which preserves the required result order.

## Run

```bash
mvn test
```
