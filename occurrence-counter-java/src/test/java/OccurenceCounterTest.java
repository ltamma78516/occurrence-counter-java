import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.OccurrenceCounter;

class OccurenceCounterTest {

    OccurrenceCounter occurrenceCounter = new OccurrenceCounter();

    @ParameterizedTest
    @MethodSource("repetitionExamples")
    void shouldCountOccurrencesAndSortByFrequency(
            String[] input,
            Map<String, Integer> expected,
            List<String> expectedOrder) {

        Map<String, Integer> result =
                occurrenceCounter.countRepetitions(input);

        assertEquals(expected, result);
        assertEquals(expectedOrder, new ArrayList<>(result.keySet()));
    }

    static Stream<Arguments> repetitionExamples() {
        return Stream.of(
                Arguments.of(
                        new String[]{"cat", "dog", "cat", "cow", "cow", "cow"},
                        orderedMap("cow", 3, "cat", 2, "dog", 1),
                        List.of("cow", "cat", "dog")
                ),
                Arguments.of(
                        new String[]{"Infinity", "null", "Infinity", "null", "null"},
                        orderedMap("null", 3, "Infinity", 2),
                        List.of("null", "Infinity")
                ),
                Arguments.of(
                        new String[]{"cat", "dog", "bird"},
                        orderedMap("cat", 1, "dog", 1, "bird", 1),
                        List.of("cat", "dog", "bird")
                )
        );
    }

    @Test
    void shouldSupportNumericElements() {
        Integer[] input = {1, 5, 5, 5, 12, 12, 0, 0, 0, 0, 0, 0};

        Map<Integer, Integer> result =
                occurrenceCounter.countRepetitions(input);

        assertEquals(
                orderedMap(0, 6, 5, 3, 12, 2, 1, 1),
                result
        );
        assertEquals(
                List.of(0, 5, 12, 1),
                new ArrayList<>(result.keySet())
        );
    }

    @Test
    void shouldReturnEmptyMapForEmptyInput() {
        Map<String, Integer> result =
                occurrenceCounter.countRepetitions(new String[]{});

        assertEquals(Map.of(), result);
        assertEquals(List.of(), new ArrayList<>(result.keySet()));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(
                Map.of("cat", 1),
                occurrenceCounter.countRepetitions(new String[]{"cat"})
        );
    }

    @Test
    void shouldPreserveFirstSeenOrderWhenFrequenciesAreEqual() {
        String[] input = {
                "cat", "dog", "bird",
                "cat", "dog", "bird"
        };

        Map<String, Integer> result =
                occurrenceCounter.countRepetitions(input);

        assertEquals(
                List.of("cat", "dog", "bird"),
                new ArrayList<>(result.keySet())
        );
    }

    @Test
    void shouldHandleNullElements() {
        String[] input = {"cat", null, "cat", null, null};

        Map<String, Integer> result =
                occurrenceCounter.countRepetitions(input);

        assertEquals(
                orderedMap(null, 3, "cat", 2),
                result
        );
        assertEquals(
                java.util.Arrays.asList(null, "cat"),
                new ArrayList<>(result.keySet())
        );
    }

    @Test
    void shouldRejectNullInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> occurrenceCounter.countRepetitions(null)
        );
    }

    @Test
    void shouldNotModifyInput() {
        String[] input = {"cat", "dog", "cat"};
        String[] original = input.clone();

        occurrenceCounter.countRepetitions(input);

        assertArrayEquals(original, input);
    }

    private static <T> Map<T, Integer> orderedMap(Object... values) {
        Map<T, Integer> result = new LinkedHashMap<>();

        for (int i = 0; i < values.length; i += 2) {
            @SuppressWarnings("unchecked")
            T key = (T) values[i];
            result.put(key, (Integer) values[i + 1]);
        }

        return result;
    }
}