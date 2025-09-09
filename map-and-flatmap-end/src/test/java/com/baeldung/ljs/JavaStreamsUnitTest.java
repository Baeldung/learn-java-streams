package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {

    // @formatter:off
    private final Collection<Task> tasks = List.of(
            new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1), List.of("home", "construction")),
            new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), List.of("school", "reparation")),
            new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30), List.of("restaurant", "construction")),
            new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15), List.of("home", "construction")),
            new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25), List.of("restaurant", "restoration")),
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18), List.of("street", "construction")),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), List.of("bridge", "restoration")),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), List.of("factory", "reparation")));
    // @formatter:on

    @Test
    void givenNumbers_whenSquared_thenReturnSquaredList() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<Integer> expected = List.of(1, 4, 9, 16, 25);
        List<Integer> actual = new ArrayList<>();

        numbers.stream()
            .map(n -> n * n)
            .forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    void givenPrices_whenFormattedToDollarString_thenReturnFormattedList() {
        List<Double> prices = List.of(10.5, 20.0, 99.99);
        List<String> expected = List.of("$10.50", "$20.00", "$99.99");
        List<String> actual = new ArrayList<>();

        prices.stream()
            .map(price -> String.format(Locale.US, "$%.2f", price))
            .forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    void givenTasks_whenMappedToCodes_thenReturnCodeList() {
        List<String> codes = new ArrayList<>();
        List<String> expectedCodes = List.of("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8");

        tasks.stream()
            .map(Task::getCode)
            .forEach(codes::add);

        assertEquals(expectedCodes, codes);
    }

    @Test
    void givenWords_whenChainedMapApplied_thenReturnTransformedList() {
        // @formatter: off
        List<String> fruits = List.of("apple", "banana", "cherry");
        List<String> expected = List.of("Elppa", "Ananab", "Yrrehc");
        List<String> actual = new ArrayList<>();

        fruits.stream()
            .map(fruit -> new StringBuilder(fruit).reverse()
                .toString())
            .map(str -> Character.toUpperCase(str.charAt(0)) + str.substring(1))
            .forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    void givenTasks_whenMapToLabels_thenGetListOfLists() {
        // @formatter:off
        Set<String> expected = Set.of(
            "home", "construction", "school", "reparation",
            "restaurant", "restoration", "street", "bridge", "factory"
        );
        // @formatter:on
        List<List<String>> tasksOutput = new ArrayList<>();

        // Extract the labels field (Stream<List<String>>)
        tasks.stream()
            .map(task -> task.getLabels())
            .forEach(tasksOutput::add);

        // Merge all the label Strings in a single set
        Set<String> allDistinctLabels = new HashSet<>();
        for (List<String> taskLabels : tasksOutput) {
            allDistinctLabels.addAll(taskLabels);
        }

        assertEquals(expected, allDistinctLabels);
    }

    @Test
    void givenTasks_whenFlatMappedToLabels_thenReturnDistinctLabelList() {
        // @formatter:off
        Set<String> expected = Set.of(
                "home", "construction", "school", "reparation",
                "restaurant", "restoration", "street", "bridge", "factory"
        );
        // @formatter:on

        Set<String> actual = new HashSet<>();

        tasks.stream()
            .flatMap(task -> task.getLabels()
                .stream())
            .forEach(actual::add);
        
        // equivalent:
        //        tasks.stream()
        //          .map(Task::getLabels)
        //          .flatMap(Collection::stream)
        //          .forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    void givenTasks_whenFlatMappedAndMapped_thenReturnUppercaseLabels() {
        // @formatter:off
        Set<String> expected = Set.of(
                "HOME", "CONSTRUCTION", "SCHOOL", "REPARATION",
                "RESTAURANT", "RESTORATION", "STREET", "BRIDGE", "FACTORY"
        );
        // @formatter:on

        Set<String> actual = new HashSet<>();

        tasks.stream()
            .flatMap(task -> task.getLabels()
                .stream())
            .map(String::toUpperCase)
            .forEach(actual::add);

        assertEquals(expected, actual);
    }

}