package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {

    private final List<Task> tasks = List.of(
        new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1)),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));

    @Test
    void givenTasks_whenLimit3Elements_thenReturn3Elements() {

        List<Task> firstThreeElements = new ArrayList<>();
        tasks.stream()
            .limit(3)
            .forEach(task -> {
                System.out.println(task);
                firstThreeElements.add(task);
            });

        assertEquals(3, firstThreeElements.size());
    }

    @Test
    void givenTasks_whenLimitWasSetWithDifferentValues() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .limit(tasks.size() + 1)
            .forEach(task -> {
                System.out.println(task);
                result.add(task);
            });

        assertEquals(result.size(), tasks.size());

        List<Task> result1 = new ArrayList<>();
        tasks.stream()
            .limit(0)
            .forEach(task -> {
                System.out.println(task);
                result1.add(task);
            });

        assertEquals(0, result1.size());

        assertThrows(IllegalArgumentException.class, () -> tasks.stream().limit(-1).toList());
    }

    @Test
    void givenTasks_whenSkip2Elements_thenReturnElementsStartingAtIndex2() {

        List<Task> elementsWithoutFirst2 = new ArrayList<>();

        tasks.stream()
            .skip(2)
            .forEach(task -> {
                System.out.println(task);
                elementsWithoutFirst2.add(task);
            });

        assertEquals(tasks.get(2), elementsWithoutFirst2.get(0));
        assertEquals(6, elementsWithoutFirst2.size());
    }

    @Test
    void givenTasks_whenSkipWasSetWithDifferentValues() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .skip(tasks.size() + 1)
            .forEach(task -> {
                System.out.println(task);
                result.add(task);
            });

        assertEquals(0, result.size());

        List<Task> result1 = new ArrayList<>();
        tasks.stream()
            .skip(0)
            .forEach(task -> {
                System.out.println(task);
                result1.add(task);
            });

        assertEquals(tasks.size(), result1.size());

        assertThrows(IllegalArgumentException.class, () -> tasks.stream().skip(-1).toList());
    }

    static long offset(int page, int size) {
        return ((long) (page - 1 ) * size);
    }

    @Test
    void givenTasks_whenUsingLimitAndSkip_thenReturnPage2() {
        int page = 2;
        int pageSize = 3;

        List<Task> result = new ArrayList<>();
        tasks.stream()
            .skip(offset(page, pageSize))
            .limit(pageSize)
            .forEach(task -> {
                System.out.println(task);
                result.add(task);
            });

        assertEquals(3, result.size());
    }

    @Test
    void givenTasks_whenUsingLimitFirstAndSkip_thenReturn1Element() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .limit(3)
            .skip(2)
            .forEach(task -> {
                System.out.println(task);
                result.add(task);
            });

        assertEquals(1, result.size());
    }

    @Test
    void givenTasks_whenSkipFirstAndLimit_thenReturn3Elements() {
        List<Task> result = new ArrayList<>();
        tasks.stream()
            .skip(2)
            .limit(3)
            .forEach(task -> {
                System.out.println(task);
                result.add(task);
            });

        assertEquals(3, result.size());
    }
}
