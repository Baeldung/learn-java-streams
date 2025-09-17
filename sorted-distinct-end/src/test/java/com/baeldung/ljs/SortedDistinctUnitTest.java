package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class SortedDistinctUnitTest {
    private Task task1 = new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1));
    // @formatter:off
    private final Collection<Task> tasks = List.of(
            task1,
            new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
            new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
            new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
            new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));
    // @formatter:on
    
    @Test
    void givenDuplicateCodesAndDifferentDates_whenDistinct_thenKeepsFirstEncountered() {
        List<Task> sample = List.of(
            new Task("T1", "Alpha", "...", LocalDate.of(2026, 1, 1)),
            new Task("T1", "Beta",  "...", LocalDate.of(2024, 1, 1)),
            new Task("T2", "Gamma", "...", LocalDate.of(2025, 3, 15))
        );

        List<Task> unique = new ArrayList<>();

        sample.stream()
              .distinct()
              .forEach(unique::add);

        assertEquals(2, unique.size());
        assertEquals("T1", unique.get(0).getCode());
        assertEquals(LocalDate.of(2026, 1, 1), unique.get(0).getDueDate());
        assertEquals("T2", unique.get(1).getCode());
    }
    
    @Test
    void givenTaskDescriptions_whenMappingWordsAndDistinct_thenAllWordsAreUnique() {
        List<String> words =
            tasks.stream()
                 .map(Task::getDescription)
                 .flatMap(desc -> Arrays.stream(desc.split("\\s+")))
                 .map(w -> w.replaceAll("[^A-Za-z']","")) // strip punctuation
                 .filter(w -> !w.isBlank())
                 .map(String::toLowerCase)
                 .distinct()
                 .toList();
        assertEquals(Set.copyOf(words).size(), words.size());
    }
    
    @Test
    void givenTasks_whenSortedNaturally_thenOrderedByDueDateAscending() {
        List<String> orderedCodes = 
            tasks.stream()
                 .sorted()
                 .map(Task::getCode)
                 .toList();

        assertEquals(List.of("T1", "T2", "T3", "T8", "T4", "T7", "T5", "T6"), orderedCodes);
    }
    
    @Test
    void givenTasks_whenSortedWithInlineLambda_thenOrderedByNameAscending() {
        List<String> orderedCodes = 
            tasks.stream()
                 .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                 .map(Task::getCode)
                 .toList();
        
        assertEquals(List.of("T5", "T3", "T7", "T8", "T1", "T4", "T2", "T6"), orderedCodes);
    }
    
    @Test
    void givenTasks_whenSortedByDateThenCodeDescending_thenComparatorReversed() {
        Comparator<Task> byDateThenCodeDescending =
            Comparator.comparing(Task::getDueDate)
                      .thenComparing(Task::getCode)
                      .reversed();

        List<String> orderedCodes = 
            tasks.stream()
                 .sorted(byDateThenCodeDescending)
                 .map(Task::getCode)
                 .toList();

        assertEquals(List.of("T6", "T5", "T7", "T4", "T8", "T3", "T2", "T1"), orderedCodes);
    }
    
    @Test
    void givenDuplicatedData_whenDistinctAndSort_thenUniqueAndOrdered() {
        Task task0 = new Task("T0", "New Task", "New task description", LocalDate.of(2000, 1, 1));
        Task secondaryTask2 = new Task("T2", "Different Name", "Different Description", LocalDate.of(2030, 1, 1));
        List<Task> secondarySource = List.of(task1, secondaryTask2, task0);

        List<Task> distinctSortedTasks = 
            Stream.concat(tasks.stream(), secondarySource.stream())
                  .distinct()
                  .sorted()
                  .toList();

        assertEquals(9, distinctSortedTasks.size());
        assertSame(task1, distinctSortedTasks.get(1));
        assertSame(task0, distinctSortedTasks.get(0));
        assertEquals("T2", distinctSortedTasks.get(2).getCode());
        assertNotEquals(secondaryTask2.getName(), distinctSortedTasks.get(2).getName());
    }
}
