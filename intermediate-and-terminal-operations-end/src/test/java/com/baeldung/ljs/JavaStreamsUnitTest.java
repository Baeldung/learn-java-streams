package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {

    private final Collection<Task> tasks = List.of(new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1)),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));

    @Test
    void whenMappingStream_thenReturnsAStreamOfTaskCodes() {
        List<String> codes = tasks.stream()
            .map(Task::getCode)
            .toList();

        List<String> expectedCodes = List.of("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8");
        assertTrue(codes.containsAll(expectedCodes));
    }

    @Test
    void whenTryingToUseAConsumedStream_thenExceptionIsThrown() {
        Stream<String> taskCodeStream = tasks.stream()
            .map(Task::getCode);

        List<String> codes = taskCodeStream.toList();
        assertThrows(IllegalStateException.class, () -> taskCodeStream.forEach(System.out::println));
    }

    @Test
    void whenCollectingAStreamAndStreamingAgain_thenNoExceptionIsThrown() {
        Stream<String> taskCodeStream = tasks.stream()
            .map(Task::getCode);

        List<String> codes = taskCodeStream.toList();

        assertDoesNotThrow(() -> codes.stream()
            .forEach(System.out::println));
    }

}