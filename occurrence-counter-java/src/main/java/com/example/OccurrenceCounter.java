package com.example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OccurrenceCounter {

    public <T> Map<T, Integer> countRepetitions(T[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Elements must not be null");
        }

        Map<T, Integer> counts = new LinkedHashMap<>();

        for (T element : elements) {
            counts.merge(element, 1, Integer::sum);
        }

        List<Map.Entry<T, Integer>> entries =
                new ArrayList<>(counts.entrySet());

        entries.sort(
                Map.Entry.<T, Integer>comparingByValue().reversed()
        );

        Map<T, Integer> result = new LinkedHashMap<>();

        for (Map.Entry<T, Integer> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
