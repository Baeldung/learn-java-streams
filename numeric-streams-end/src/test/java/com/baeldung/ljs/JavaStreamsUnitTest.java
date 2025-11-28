package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {
    private final Collection<Task> tasks = List.of(
            new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1)),
            new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
            new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
            new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
            new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));

    @Test
    void whenMappingToIntStream_thenReturnsStreamOfYears() {
        IntStream yearStream = tasks.stream()
            .mapToInt(task -> task.getDueDate()
                .getYear());
        assertEquals(2035, yearStream.max().getAsInt());
    }

    @Test
    void whenCreatingRanges_thenCorrectCountsAreReturned() {
        long countExclusive = IntStream.range(1, 5).count();
        assertEquals(4, countExclusive);

        long countInclusive = IntStream.rangeClosed(1, 5).count();
        assertEquals(5, countInclusive);
    }

    @Test
    void whenCreatingFromValues_thenSumIsCorrect() {
        int sum = IntStream.of(10, 20, 30, 40)
            .sum();
        assertEquals(100, sum);
    }

    @Test
    void whenCreatingFromArray_thenMaxIsCorrect() {
        int[] numbers = { 5, 15, 2, 8 };
        IntStream numberStream = Arrays.stream(numbers);
        assertEquals(15, numberStream.max().getAsInt());
    }

    @Test
    void whenUsingAggregateFunctions_thenCorrectResultsAreReturned() {
        IntStream years = tasks.stream()
            .mapToInt(task -> task.getDueDate()
                .getYear());
        assertEquals(16225, years.sum());

        OptionalDouble average = tasks.stream()
            .mapToInt(task -> task.getDueDate()
                .getYear())
            .average();
        assertEquals(2028.125, average.getAsDouble());

        OptionalInt max = tasks.stream()
            .mapToInt(task -> task.getDueDate()
                .getYear())
            .max();
        assertEquals(2035, max.getAsInt());
    }

    @Test
    void whenUsingSummaryStatistics_thenAllValuesAreCorrect() {
        IntSummaryStatistics stats = tasks.stream()
            .mapToInt(task -> task.getDueDate()
                .getYear())
            .summaryStatistics();

        assertEquals(8, stats.getCount());
        assertEquals(16225, stats.getSum());
        assertEquals(2024, stats.getMin());
        assertEquals(2035, stats.getMax());
        assertEquals(2028.125, stats.getAverage());
    }

    @Test
    void whenBoxingAnIntStream_thenReturnsStreamOfInteger() {
        List<Integer> listOfIntegers = IntStream.of(1, 2, 3)
            .boxed()
            .toList();
        assertTrue(listOfIntegers.get(0) instanceof Integer);
    }

    @Test
    void whenMappingToLongStream_thenReturnsStreamOfEpochDays() {
        LongStream epochDays = tasks.stream()
            .mapToLong(task -> task.getDueDate().toEpochDay());
        long minEpochDay = epochDays.min().getAsLong();

        assertEquals(19723, minEpochDay);
    }

    @Test
    void whenCreatingDoubleStream_thenAverageIsCorrect() {
        DoubleStream heights = DoubleStream.of(1.75, 1.80, 1.65, 1.90);
        double avgHeight = heights.average().getAsDouble();
        assertEquals(1.775, avgHeight);
    }
}
