package org.example.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CountingSort {
    public record TraceStep(String stage, int[] state) {}

    private final List<TraceStep> traceLog = new ArrayList<>();

    public Integer[] sort(Integer[] input) {
        traceLog.clear();

        if (input == null || input.length == 0) {
            traceLog.add(new TraceStep("EMPTY", new int[] {0}));
            return input;
        }

        int min = Collections.min(Arrays.asList(input));
        int max = Collections.max(Arrays.asList(input));
        int range = max - min + 1;

        traceLog.add(new TraceStep("RANGE", new int[] {min, max, range}));

        // Подсчёт частот
        int[] count = new int[range];
        for (int val : input) {
            count[val - min]++;
        }
        traceLog.add(new TraceStep("COUNT", count.clone()));

        // Префиксные суммы
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }
        traceLog.add(new TraceStep("PREFIX", count.clone()));

        // Ответы считаем
        Integer[] output = new Integer[input.length];
        for (int i = input.length - 1; i >= 0; i--) {
            int val = input[i];
            int pos = --count[val - min];
            output[pos] = val;
        }
        traceLog.add(
                new TraceStep("RESULT", Arrays.stream(output).mapToInt(v -> v).toArray()));

        return output;
    }

    public List<TraceStep> getTrace() {
        return new ArrayList<>(traceLog);
    }

    public List<String> getStageSequence() {
        return traceLog.stream().map(TraceStep::stage).toList();
    }
}
