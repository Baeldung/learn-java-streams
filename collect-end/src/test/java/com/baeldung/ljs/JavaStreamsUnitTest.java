package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void whenJoiningStrings_thenReturnsSingleString() {
        List<String> names = List.of("John", "Thomas", "Emma");

        String allNames = names.stream()
            .collect(Collectors.joining(", "));

        assertEquals("John, Thomas, Emma", allNames);
    }

    @Test
    void whenCollectingStrings_thenReturnsListWithAllElements() {
        List<String> names = List.of("John", "Thomas", "Emma");

        List<String> uppercaseNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        assertEquals(3, uppercaseNames.size());
        assertTrue(uppercaseNames.containsAll(List.of("JOHN", "THOMAS", "EMMA")));
    }

    @Test
    void whenJoinStringsMultiline_thenReturnsEachStringOnANewLine() {
        Collector<String, StringBuilder, String> collectMultiline = Collector.of(StringBuilder::new, (result, newElement) -> result.append(newElement)
            .append("\n"), StringBuilder::append, StringBuilder::toString);

        String result = Stream.of("John", "Thomas", "Emma")
            .collect(collectMultiline);

        assertEquals("John\nThomas\nEmma\n", result);
    }

    @Test
    void whenFetchingTaskCodesByDueDate_thenReturnsCorrectCodes() {
        String taskCodes = this.tasks.stream()
            .filter(task -> task.getDueDate().getYear() == 2028)
            .map(Task::getCode)
            .collect(Collectors.joining(", "));

        assertEquals("T4, T8", taskCodes);
    }

}