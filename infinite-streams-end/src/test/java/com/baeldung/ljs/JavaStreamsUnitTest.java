package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;

class JavaStreamsUnitTest {

    @Test
    void whenUsingStreamIterate_thenCreatesInfiniteStreamOfEvenNumbers() {
        Stream<Integer> evenNumberStream = Stream.iterate(0, n -> n + 2);
        Iterator<Integer> evenNumbers = evenNumberStream.iterator();

        assertNotNull(evenNumberStream);
        assertEquals(0, evenNumbers.next());
        assertEquals(2, evenNumbers.next());
        assertEquals(4, evenNumbers.next());
        assertEquals(6, evenNumbers.next());
    }

    @Test
    void whenUsingStreamGenerate_thenCreatesInfiniteStreamOfRandomNumbers() {
        Stream<Integer> randomNumberStream = Stream.generate(() -> new Random().nextInt(100));
        Iterator<Integer> randomNumbers = randomNumberStream.iterator();

        assertNotNull(randomNumberStream);
        assertNotNull(randomNumbers.next());
        assertNotNull(randomNumbers.next());
        assertNotNull(randomNumbers.next());
        assertNotNull(randomNumbers.next());
    }

    @Test
    void whenUsingStreamIterateWithLimit_thenCreatesListOfEvenNumbers() {
        List<Integer> evenNumbers = Stream.iterate(0, n -> n + 2)
            .limit(10)
            .toList();

        assertEquals(10, evenNumbers.size());
        assertEquals(18, evenNumbers.get(9));
    }

    @Test
    void whenUsingStreamIterateWithTakeWhile_thenStopsStreamAtCondition() {
        List<Integer> evenNumbers = Stream.iterate(0, n -> n + 2)
            .takeWhile(n -> n < 20)
            .toList();

        List<Integer> expected = List.of(0, 2, 4, 6, 8, 10, 12, 14, 16, 18);
        assertEquals(expected, evenNumbers);
    }

    @Test
    void whenUsingStreamIterateWithPredicate_thenCreatesListOfEvenNumbers() {
        Stream<Integer> evenNumberStreamWithPredicate = Stream.iterate(0, n -> n < 20, n -> n + 2);
        List<Integer> evenNumbers = evenNumberStreamWithPredicate.toList();

        assertEquals(10, evenNumbers.size());
        assertEquals(18, evenNumbers.get(9));
    }

    @Test
    void whenUsingIntStreamIterate_thenFindsFirstEvenNumber() {
        IntStream evenNumberIntStream = IntStream.iterate(0, n -> n + 2);

        OptionalInt first = evenNumberIntStream.findFirst();

        assertTrue(first.isPresent());
        assertEquals(0, first.getAsInt());
    }

    @Test
    void whenUsingIntStreamGenerate_thenFindsAnyRandomNumber() {
        IntStream randomIntStream = IntStream.generate(() -> new Random().nextInt(100));

        OptionalInt any = randomIntStream.findAny();

        assertTrue(any.isPresent());
        assertTrue(any.getAsInt() >= 0 && any.getAsInt() < 100);
    }

    @Test
    void whenUsingStreamGenerate_thenChecksIfAnyRandomNumberIsAbove50() {
        Stream<Integer> randomStream = Stream.generate(() -> new Random().nextInt(100));

        boolean anyAbove50 = randomStream.anyMatch(n -> n > 50);

        assertTrue(anyAbove50);
    }

    @Test
    void whenUsingStreamIterate_thenChecksAllMaintenanceTasksAreInProgress() {
        Stream<Task> maintenanceTaskStream = Stream.iterate(
            new Task("M1", "Annual maintenance", "Yearly building maintenance", LocalDate.of(2025, 1, 1), TaskStatus.IN_PROGRESS),
            task -> new Task(task.getCode(), task.getName(), task.getDescription(), task.getDueDate()
                .plusYears(1), task.getStatus()));

        boolean allDone = maintenanceTaskStream.allMatch(task -> task.getStatus() == TaskStatus.DONE);

        assertFalse(allDone);
    }

    @Test
    void whenUsingStreamGenerate_thenChecksNoTaskIsDoneInitially() {
        Stream<Task> defaultTaskStream = Stream.generate(() -> new Task("Code", "Default Task", "Default description", LocalDate.now(), TaskStatus.TO_DO));

        boolean noneToDo = defaultTaskStream.noneMatch(task -> task.getStatus() == TaskStatus.TO_DO);

        assertFalse(noneToDo);
    }

}