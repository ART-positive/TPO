package task2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.example.task2.CountingSort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CountingSortTest {
    private final CountingSort sorter = new CountingSort();

    @ParameterizedTest
    @MethodSource("provideSortTestCases")
    void testSortingCorrectness(String name, Integer[] input, Integer[] expected) {
        Integer[] result = sorter.sort(input);
        assertArrayEquals(expected, result, name);
    }

    @ParameterizedTest
    @MethodSource("provideTraceSequenceCases")
    void testTraceSequenceMatchesReference(String name, Integer[] input, List<String> expectedSequence) {
        sorter.sort(input);
        List<String> actualSequence = sorter.getStageSequence();

        assertEquals(
                expectedSequence,
                actualSequence,
                String.format("ШОК! Последовательность этапов не совпадает для кейса '%s': ", name));
    }

    static Stream<Arguments> provideTraceSequenceCases() {
        return Stream.of(
                Arguments.of("empty", new Integer[] {}, List.of("EMPTY")),
                Arguments.of("single", new Integer[] {42}, List.of("RANGE", "COUNT", "PREFIX", "RESULT")),
                Arguments.of("multiple", new Integer[] {3, 1, 2}, List.of("RANGE", "COUNT", "PREFIX", "RESULT")),
                Arguments.of("negative", new Integer[] {-5, 0, 3}, List.of("RANGE", "COUNT", "PREFIX", "RESULT")),
                Arguments.of("duplicates", new Integer[] {1, 1, 2, 2}, List.of("RANGE", "COUNT", "PREFIX", "RESULT")));
    }

    @Test
    void testTraceStateContent() {
        sorter.sort(new Integer[] {3, 1, 2});
        List<CountingSort.TraceStep> trace = sorter.getTrace();

        assertEquals(3, trace.getFirst().state().length);
        assertEquals(1, trace.getFirst().state()[0]); // min
        assertEquals(3, trace.get(0).state()[1]); // max
        assertEquals(3, trace.get(0).state()[2]); // range

        // COUNT
        assertArrayEquals(new int[] {1, 1, 1}, trace.get(1).state());
        // PREFIX
        assertArrayEquals(new int[] {1, 2, 3}, trace.get(2).state());
        // RESULT
        assertArrayEquals(new int[] {1, 2, 3}, trace.get(3).state());
    }

    private static Stream<Arguments> provideSortTestCases() {
        return Stream.of(
                Arguments.of("empty", new Integer[] {}, new Integer[] {}),
                Arguments.of("single", new Integer[] {42}, new Integer[] {42}),
                Arguments.of("identical", new Integer[] {5, 5, 5, 5}, new Integer[] {5, 5, 5, 5}),
                Arguments.of("sorted", new Integer[] {1, 2, 3, 4, 5}, new Integer[] {1, 2, 3, 4, 5}),
                Arguments.of("reverse", new Integer[] {5, 4, 3, 2, 1}, new Integer[] {1, 2, 3, 4, 5}),
                Arguments.of("duplicates", new Integer[] {3, 1, 2, 3, 1, 2}, new Integer[] {1, 1, 2, 2, 3, 3}),
                Arguments.of("negative", new Integer[] {-5, -2, 0, 3, -5, 1}, new Integer[] {-5, -5, -2, 0, 1, 3}),
                Arguments.of("with_zeros", new Integer[] {0, 0, 1, 0, 1}, new Integer[] {0, 0, 0, 1, 1}),
                Arguments.of("large_range", new Integer[] {100, 1, 50, 1, 100}, new Integer[] {1, 1, 50, 100, 100}));
    }
}
