package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

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
    void whenUsingWindowFixed_thenGroupsTasksInPairs() {
        List<List<Task>> taskPairs = tasks.stream()
            .gather(Gatherers.windowFixed(2))
            .toList();

        assertEquals(4, taskPairs.size());
        assertEquals(2, taskPairs.get(0)
            .size());
        assertEquals(2, taskPairs.get(1)
            .size());
        assertEquals(2, taskPairs.get(2)
            .size());
        assertEquals(2, taskPairs.get(3)
            .size());
    }

    @Test
    void givenTaskCodes_whenUsingWindowFixed_thenGroupsTasksIntoChunksOfThree() {
        List<List<String>> chunks = tasks.stream()
            .map(Task::getCode)
            .gather(Gatherers.windowFixed(3))
            .toList();

        List<String> firstChunk = chunks.get(0);
        assertEquals("T1", firstChunk.get(0));
        assertEquals("T2", firstChunk.get(1));
        assertEquals("T3", firstChunk.get(2));

        List<String> secondChunk = chunks.get(1);
        assertEquals("T4", secondChunk.get(0));
        assertEquals("T5", secondChunk.get(1));
        assertEquals("T6", secondChunk.get(2));
    }

    @Test
    void givenTaskCodes_whenUsingWindowSliding_thenCreatesOverlappingTaskGroups() {
        List<List<String>> chunks = tasks.stream()
            .map(Task::getCode)
            .gather(Gatherers.windowSliding(3))
            .toList();

        List<String> firstChunk = chunks.get(0);
        assertEquals("T1", firstChunk.get(0));
        assertEquals("T2", firstChunk.get(1));
        assertEquals("T3", firstChunk.get(2));

        List<String> secondChunk = chunks.get(1);
        assertEquals("T2", secondChunk.get(0));
        assertEquals("T3", secondChunk.get(1));
        assertEquals("T4", secondChunk.get(2));
    }

    @Test
    void givenTaskCodes_whenUsingScan_thenCreatesCumulativeResults() {
        List<String> cumulativeCodes = tasks.stream()
            .map(Task::getCode)
            .gather(Gatherers.scan(() -> "", (result, newCode) -> result.isEmpty() ? newCode : result + ", " + newCode))
            .toList();

        assertEquals("T1", cumulativeCodes.get(0));
        assertEquals("T1, T2", cumulativeCodes.get(1));
        assertEquals("T1, T2, T3", cumulativeCodes.get(2));
        assertEquals("T1, T2, T3, T4, T5, T6, T7, T8", cumulativeCodes.getLast());
    }

    @Test
    void givenTaskCodes_whenUsingCustomGatherer_thenCreatesCumulativeResults() {
        List<String> cumulativeCodes = tasks.stream()
            .gather(commaSeparatedTaskCodes())
            .toList();

        assertEquals("T1", cumulativeCodes.get(0));
        assertEquals("T1, T2", cumulativeCodes.get(1));
        assertEquals("T1, T2, T3", cumulativeCodes.get(2));
        assertEquals("T1, T2, T3, T4, T5, T6, T7, T8", cumulativeCodes.getLast());
    }

    private static Gatherer<Task, StringBuilder, String> commaSeparatedTaskCodes() {
        return Gatherer.ofSequential(() -> new StringBuilder(), (builder, task, downstream) -> {
            if (!builder.isEmpty()) {
                builder.append(", ");
            }
            builder.append(task.getCode());
            downstream.push(builder.toString());
            return true;
        }, (builder, downstream) -> {
            downstream.push(builder.toString());
        });
    }
}