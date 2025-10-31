package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Campaign;
import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;

class JavaStreamsUnitTest {
    private final List<Task> tasks = List.of(
        new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2045, 1, 1), TaskStatus.IN_PROGRESS),
        new Task("D2", "Tommy", "Preparation for the exam", LocalDate.of(2024, 5, 20), TaskStatus.ON_HOLD),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), TaskStatus.DONE),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2040, 6, 30), TaskStatus.IN_PROGRESS),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2020, 11, 15), TaskStatus.DONE),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2070, 9, 25), TaskStatus.ON_HOLD),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2055, 5, 18), TaskStatus.IN_PROGRESS),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
        new Task("D7", "Big Bang restoration", "Restoration of BigBan in London", LocalDate.of(2029, 2, 22), TaskStatus.DONE),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2050, 6, 10), TaskStatus.IN_PROGRESS)
    );

    @Test
    void whenFilteringMappingAndCollecting_thenReturnsCorrectList() {
        List<String> inProgressTaskNames = tasks.stream()
            .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
            .map(Task::getName)
            .collect(Collectors.toList());

        assertEquals(4, inProgressTaskNames.size());
        assertTrue(inProgressTaskNames.contains("John's house construction"));
        assertTrue(inProgressTaskNames.contains("Jane's Jacket factory reparation"));
    }

    @Test
    void whenFilteringMappingAndReduce_thenReturnsConcatenatedCodes() {
        String codes = tasks.stream()
            .map(Task::getCode)
            .filter(code -> code.startsWith("D"))
            .reduce("", String::concat);
        assertEquals("D2D7", codes);
    }

    private final Collection<Campaign> campaigns= List.of(
        new Campaign("Project A", List.of(tasks.getFirst(), tasks.get(1))),
        new Campaign("Project B", List.of(tasks.get(1), tasks.get(2)))
    );

    @Test
    void whenFlatMappingAndFiltering_thenReturnsCorrectTasksInProgress() {
        List<Task> uniqueTasks = campaigns.stream()
            .flatMap(c -> c.getTasks().stream())
            .filter(task -> task.getStatus().equals(TaskStatus.ON_HOLD))
            .distinct()
            .collect(Collectors.toList());

        assertEquals(1, uniqueTasks.size());
    }

    @Test
    void whenGroupingByStatusAndMappingNames_thenReturnsMapOfSets() {
        Map<TaskStatus, Set<String>> taskNamesByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, Collectors.mapping(Task::getName, Collectors.toSet())));

        Set<String> inProgressNames = taskNamesByStatus.get(TaskStatus.IN_PROGRESS);
        assertEquals(3, taskNamesByStatus.size());
        assertEquals(4, inProgressNames.size());
    }

    @Test
    void whenPartitioningByDueDateAndCounting_thenReturnsMapOfCounts() {
        Map<Boolean, Long> taskCountsByDueDate = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getDueDate()
                .getYear() > 2024, Collectors.counting()));

        assertEquals(3L, taskCountsByDueDate.get(false));
        assertEquals(7L, taskCountsByDueDate.get(true));
    }

    @Test
    void whenTeeingMinAndMax_thenReturnsCorrectMap() {
        Map<String, LocalDate> minMaxDueDates = tasks.stream()
            .collect(Collectors.teeing(Collectors.minBy(Comparator.comparing(Task::getDueDate)), Collectors.maxBy(Comparator.comparing(Task::getDueDate)),
                (min, max) -> {
                    Map<String, LocalDate> result = new HashMap<>();
                    min.ifPresent(task -> result.put("min", task.getDueDate()));
                    max.ifPresent(task -> result.put("max", task.getDueDate()));
                    return result;
                }));
        assertEquals(LocalDate.of(2020, 11, 15), minMaxDueDates.get("min"));
        assertEquals(LocalDate.of(2070, 9, 25), minMaxDueDates.get("max"));
    }
}
