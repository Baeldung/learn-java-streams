package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18), TaskStatus.IN_PROGRESS),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), TaskStatus.IN_PROGRESS));

    @Test
    void whenFindingFirstTaskWithDoneStatus_thenTheStreamIsShortCircuited() {
        Optional<Task> firstDoneTask = tasks.stream()
            .peek(task -> System.out.println("Processing task: " + task.getCode()))
            .filter(task -> task.getStatus() == TaskStatus.DONE)
            .findFirst();

        assertTrue(firstDoneTask.isPresent());
    }

    @Test
    void whenFindingAnyTaskWithoutAStatus_thenReturnsEmptyOptional() {
        Optional<Task> firstTaskWithoutStatus = tasks.stream()
            .filter(task -> task.getStatus() == null)
            .findAny();

        assertTrue(firstTaskWithoutStatus.isEmpty());
    }

    @Test
    void whenFindingFirstOnHoldTask_thenReturnsFifthTask() {
        Optional<Task> firstOnHoldTask = tasks.stream()
          .filter(task -> task.getStatus() == TaskStatus.ON_HOLD)
          .findFirst();

        assertTrue(firstOnHoldTask.isPresent());
        assertEquals("T5", firstOnHoldTask.get().getCode());
    }

    @Test
    void whenFindingAnyOnHoldTask_thenReturnedStatusHasCorrectStatus() {
        Optional<Task> anyOnHoldTask = tasks.stream()
            .filter(task -> task.getStatus() == TaskStatus.ON_HOLD)
            .findAny();

        assertTrue(anyOnHoldTask.isPresent());
        assertEquals(TaskStatus.ON_HOLD, anyOnHoldTask.get().getStatus());
    }
}