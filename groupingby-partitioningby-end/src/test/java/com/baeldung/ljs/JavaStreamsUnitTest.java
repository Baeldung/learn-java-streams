package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;

class JavaStreamsUnitTest {

    private final Collection<Task> tasks = List.of(
        new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1), TaskStatus.IN_PROGRESS),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), TaskStatus.DONE),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30), TaskStatus.IN_PROGRESS),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15), TaskStatus.DONE),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25), TaskStatus.ON_HOLD),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18),
            TaskStatus.IN_PROGRESS),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), TaskStatus.IN_PROGRESS));

    @Test
    void givenTasks_whenGroupedByStatus_thenOrganizedByStatus() {
        Map<TaskStatus, List<Task>> tasksByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus));

        assertEquals(3, tasksByStatus.size());
        assertEquals(4, tasksByStatus.get(TaskStatus.IN_PROGRESS).size());
        assertEquals(2, tasksByStatus.get(TaskStatus.DONE).size());
        assertEquals(2, tasksByStatus.get(TaskStatus.ON_HOLD).size());
    }

    @Test
    void givenTasks_whenGroupedByStatus_thenCountPerStatus() {
        Map<TaskStatus, Long> taskCountByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        assertEquals(4, taskCountByStatus.get(TaskStatus.IN_PROGRESS));
        assertEquals(2, taskCountByStatus.get(TaskStatus.DONE));
        assertEquals(2, taskCountByStatus.get(TaskStatus.ON_HOLD));
    }

    @Test
    void givenTasks_whenPartitionedByDueDate_thenSeparatedByDate() {
        Map<Boolean, List<Task>> tasksByDueDate = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getDueDate().getYear() > 2025));

        List<Task> futureTasks = tasksByDueDate.get(true);
        List<Task> earlierTasks = tasksByDueDate.get(false);

        assertEquals(5, futureTasks.size());
        assertEquals(3, earlierTasks.size());
    }

    @Test
    void givenTasks_whenPartitionedByDueDate_thenCounted() {
        Map<Boolean, Long> taskCountByDate = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getDueDate().getYear() > 2025,
                Collectors.counting()));

        assertEquals(5, taskCountByDate.get(true));
        assertEquals(3, taskCountByDate.get(false));
    }

    @Test
    void givenTasks_whenGroupedByStatusAndMappedToNames_thenUniqueNamesPerStatus() {
        Map<TaskStatus, Set<String>> taskNamesByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, Collectors.mapping(Task::getName, Collectors.toSet())));

        Set<String> inProgressNames = taskNamesByStatus.get(TaskStatus.IN_PROGRESS);

        assertEquals(3, taskNamesByStatus.size());
        assertEquals(4, inProgressNames.size());
    }

    @Test
    void givenTasks_whenGroupedByStatusWithEnumMap_thenEnumMapCreated() {
        Map<TaskStatus, List<Task>> tasksByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, () -> new EnumMap<>(TaskStatus.class), Collectors.toList()));

        assertEquals(4, tasksByStatus.get(TaskStatus.IN_PROGRESS).size());
    }

    @Test
    void givenTasks_whenGroupedByStatus_thenLatestTaskPerStatus() {
        Map<TaskStatus, Task> latestTaskPerStatus = tasks.stream()
            .collect(Collectors.groupingBy(
                Task::getStatus,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(Task::getDueDate)), Optional::get)));

        assertEquals("T6", latestTaskPerStatus.get(TaskStatus.IN_PROGRESS).getCode());
        assertEquals("T4", latestTaskPerStatus.get(TaskStatus.DONE).getCode());
        assertEquals("T5", latestTaskPerStatus.get(TaskStatus.ON_HOLD).getCode());
    }

    @Test
    void givenTasks_whenPartitionedAndMapped_thenNamesSeparated() {
        Map<Boolean, Set<String>> taskNamesByOverdue = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getDueDate()
                .isBefore(LocalDate.ofYearDay(2025, 200)), Collectors.mapping(Task::getName, Collectors.toSet())));

        assertTrue(taskNamesByOverdue.containsKey(true));
    }

    @Test
    void givenTasks_whenPartitionedThenGrouped_thenCountsPerStatusPerPartition() {
        Map<Boolean, Map<TaskStatus, Long>> counts = tasks.stream()
            .collect(Collectors.partitioningBy(
                t-> t.getDueDate().isBefore(LocalDate.of(2030, 1, 1)),
                Collectors.groupingBy(Task::getStatus, Collectors.counting())));

        Map<TaskStatus, Long> before2030 = counts.get(true);
        Map<TaskStatus, Long> from2030 = counts.get(false);

        assertEquals(3L, before2030.get(TaskStatus.IN_PROGRESS));
        assertEquals(2L, before2030.get(TaskStatus.DONE));
        assertEquals(1L, before2030.get(TaskStatus.ON_HOLD));

        assertEquals(1L, from2030.get(TaskStatus.IN_PROGRESS));
        assertEquals(1L, from2030.get(TaskStatus.ON_HOLD));
        assertNull(from2030.get(TaskStatus.DONE));
    }
}