package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

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
    void givenTasks_whenAnyOnHold_thenReturnsTrue() {
        boolean hasOnHoldTask = tasks.stream()
          .anyMatch(task -> task.getStatus() == TaskStatus.ON_HOLD);
        assertTrue(hasOnHoldTask);
    }

    @Test
    void givenTasks_whenAllDueDatesAfter2023_thenReturnsTrue() {
        boolean allDueDatesAfter2023 = tasks.stream()
          .allMatch(task -> task.getDueDate()
            .getYear() > 2023);
        assertTrue(allDueDatesAfter2023);
    }

    @Test
    void givenTasks_whenNoneWithTodoStatus_thenReturnsTrue() {
        boolean noTasksToDo = tasks.stream()
          .noneMatch(task -> task.getStatus() == TaskStatus.TO_DO);
        assertTrue(noTasksToDo);
    }

    @Test
    void givenTasks_whenAnyMatchFindsDueDateIn2025_thenStopsEarly() {
        boolean hasDueDateIn2025 = tasks.stream()
          .peek(task -> System.out.println(task.getCode()))
          .anyMatch(task -> task.getDueDate()
            .getYear() == 2025);
        assertTrue(hasDueDateIn2025);
    }
}