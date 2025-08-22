package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.ljs.domain.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class JavaStreamsUnitTest {

    private final Collection<Task> tasks = List.of(new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1)),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));

    static boolean isDueAfter2027(Task task) {
        return task.getDueDate()
            .getYear() > 2027;
    }

    @Test
    void givenTaskStream_whenFilteringTasksWithDueDateAfter2027_thenReturnFilteredList() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .filter(task -> task.getDueDate()
                .getYear() > 2027)
            .forEach(result::add);

        assertEquals(5, result.size());
    }

    @Test
    void givenTaskStream_whenFilteringTasksWithMethodReference_thenReturnFilteredList() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .filter(JavaStreamsUnitTest::isDueAfter2027)
            .forEach(result::add);

        assertEquals(5, result.size());
    }

    @Test
    void givenStream_whenFilteringUsingMoreThanOnePredicate_thenReturnFutureTasks() {
        Predicate<Task> isConstructionTask = t -> t.getName()
            .contains("construction");

        List<Task> result = new ArrayList<>();
        tasks.stream()
            .filter(isConstructionTask.and(JavaStreamsUnitTest::isDueAfter2027))
            .forEach(result::add);

        assertEquals(2, result.size());
    }

    @Test
    void givenTaskStream_whenFilteringWithMultipleConditions_thenReturnFutureTasks() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .filter(JavaStreamsUnitTest::isDueAfter2027)
            // potentially other operations
            .filter(t -> t.getName().contains("construction"))
            .forEach(result::add);

        assertEquals(2, result.size());
    }

}