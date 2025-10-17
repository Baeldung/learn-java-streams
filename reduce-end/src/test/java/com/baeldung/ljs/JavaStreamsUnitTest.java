package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    void givenNumbers_whenReduced_thenReturnsSum() {
        Optional<Integer> sum = Stream.of(1, 2, 3, 4, 5)
            .reduce((a, b) -> a + b);

        assertEquals(15, sum.orElseThrow());
    }

    @Test
    void givenEmptyStream_whenReduced_thenReturnsEmptyOptional() {
        Optional<Integer> sum = Stream.<Integer> empty()
            .reduce((a, b) -> a + b);

        assertTrue(sum.isEmpty());
    }

    @Test
    void givenNumbers_whenReducedWithIdentity_thenReturnsProduct() {
        Integer product = Stream.of(1, 2, 3, 4, 5)
            .reduce(1, (a, b) -> a * b);

        assertEquals(120, product);
    }

    @Test
    void givenEmptyStream_whenReducedWithIdentity_thenReturnsIdentity() {
        Integer product = Stream.<Integer> empty()
            .reduce(1, (a, b) -> a * b);

        assertEquals(1, product);
    }

    @Test
    void givenTasks_whenReducedWithIdentity_thenReturnsLatestDueDate() {
        LocalDate lastDueDate = tasks.stream()
            .map(Task::getDueDate)
            .reduce(LocalDate.MIN, (due1, due2) -> due1.isAfter(due2) ? due1 : due2);

        assertEquals(LocalDate.of(2035, 5, 18), lastDueDate);
    }

    @Test
    void givenNumbers_whenReducedWithCombiner_thenReturnsBigIntegerProduct() {
        BigInteger product = Stream.of(1, 2, 3)
            .reduce(BigInteger.ONE, (bigInt, nr) -> bigInt.multiply(BigInteger.valueOf(nr)), (bigInt1, bigInt2) -> bigInt1.multiply(bigInt2));

        assertEquals(BigInteger.valueOf(6), product);
    }

}