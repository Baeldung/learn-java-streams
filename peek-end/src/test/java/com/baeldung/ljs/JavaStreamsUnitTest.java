package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.ljs.domain.model.Task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavaStreamsUnitTest {

    Logger LOG = Logger.getLogger(JavaStreamsUnitTest.class.getName());

    private final Collection<Task> tasks = List.of(new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1)),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20)),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30)),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15)),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25)),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18)),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22)),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10)));

    @BeforeEach
    void setUp() {
        LOG.setLevel(Level.FINE);

        for (Handler handler : LOG.getParent()
            .getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.FINE);
            }
        }
    }

    @Test
    void givenTaskStream_whenUsingPeekToDebug_thenLogTaskName() {
        AtomicInteger counter = new AtomicInteger();
        tasks.stream()
            .filter(task -> task.getName()
                .startsWith("J"))
            .peek(task -> LOG.log(Level.INFO, task.getName()))
            .forEach(task -> counter.incrementAndGet());

        assertEquals(2, counter.get());
    }

    @Test
    void givenTaskStream_whenUsingGatedLoggingWithPeek_thenEfficientInspection() {
        AtomicInteger counter = new AtomicInteger();
        Stream<Task> taskStream = tasks.stream()
            .filter(task -> task.getName()
                .startsWith("J"));

        if (LOG.isLoggable(Level.FINE)) {
            taskStream = taskStream.peek(task -> LOG.log(Level.FINE, () -> "Filtered task: " + task.getName() + " ID: " + task.getCode()));
        }

        Stream<String> nameStream = taskStream.map(Task::getName);

        if (LOG.isLoggable(Level.FINE)) {
            nameStream = nameStream.peek(name -> LOG.log(Level.FINE, () -> "Mapped name: " + name));
        }

        nameStream.forEach(task -> counter.incrementAndGet());

        assertEquals(2, counter.get());
    }

    @Test
    void givenTaskStream_whenFiltering_thenPeekToInspectMatchingTasks() {
        AtomicInteger counter = new AtomicInteger();
        tasks.stream()
            .peek(task -> LOG.log(Level.INFO, task.getName()))
            .filter(task -> task.getName()
                .startsWith("J"))
            .peek(task -> LOG.log(Level.INFO, task.getName()))
            .peek(task -> {
                if (!task.getName()
                    .startsWith("J")) {
                    throw new IllegalArgumentException("Error in Filtered Data");
                }
            })
            .forEach(task -> counter.incrementAndGet());

        assertEquals(2, counter.get());
    }

    @Test
    void givenTaskStream_whenUsingPeekToModifyTasks_thenCorruptedState() {
        AtomicInteger alteredCounter = new AtomicInteger();
        tasks.stream()
            .peek(task -> {
                if (task.getName()
                    .startsWith("J")) {
                    task.setName("CORRUPTED");
                }
            })
            .filter(task -> "CORRUPTED".equals(task.getName()))
            .forEach(task -> alteredCounter.incrementAndGet());

        assertTrue(alteredCounter.get() > 0); // Demonstrates side effect
    }

    @Test
    void givenTaskStream_whenUsingPeekForSideEffects_thenMutateExternalState() {
        AtomicInteger counter = new AtomicInteger();
        tasks.stream()
            .peek(task -> counter.incrementAndGet())
            .forEach(task -> {});

        assertEquals(tasks.size(), counter.get());
    }

}