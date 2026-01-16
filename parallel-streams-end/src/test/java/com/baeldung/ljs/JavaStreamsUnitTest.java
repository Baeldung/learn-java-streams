package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

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
    void whenUsingParallelStream_thenReturnsCorrectCount() {
        long taskCount = tasks.parallelStream()
          .count();
        assertEquals(8, taskCount);
    }

    @Test
    void whenUsingParallelMethod_thenReturnsCorrectCount() {
        long taskCount = tasks.stream()
          .parallel()
          .count();
        assertEquals(8, taskCount);
    }

    @Test
    void whenUsingForEachInParallel_thenOrderIsNotGuaranteed() {
        tasks.parallelStream()
          .forEach(t -> System.out.println(t.getCode()));
    }

    // This test is commented out because it's designed to demonstrate an anti-pattern:
    // modifying a non-thread-safe collection (ArrayList) from a parallel stream.
    // This leads to unpredictable behavior, including potential race conditions,
    // data corruption (lost writes), or exceptions (like ArrayIndexOutOfBoundsException),
    // which can make the build unstable. It serves as a good example of what not to do.
    /*
    @Test
    void whenModifyingSharedListInParallel_thenBehaviorIsUnpredictable() {
        boolean inconsistentDetected = false;

        for (int i = 0; i < 100; i++) {
            List<String> sharedList = new ArrayList<>();

            tasks.parallelStream()
              .forEach(t -> sharedList.add(t.getCode()));

            if (sharedList.size() != tasks.size()) {
                inconsistentDetected = true;
                break;
            }
        }

        assertTrue(inconsistentDetected);
    }
    */

    @Test
    void whenCollectingInParallel_thenIsThreadSafe() {
        List<String> codes = tasks.parallelStream()
          .map(Task::getCode)
          .toList();
        assertEquals(8, codes.size());
    }

    @Test
    void whenUsingCustomPool_thenIsIsolatedFromCommonPool() throws InterruptedException, ExecutionException {
        ForkJoinPool customPool = new ForkJoinPool(4);
        try {
            Callable<Long> task = () -> tasks.parallelStream()
              .map(t -> {
                  try {
                      // Simulate some work
                      Thread.sleep(10);
                  } catch (InterruptedException e) {
                      Thread.currentThread()
                        .interrupt();
                  }
                  return t;
              })
              .count();
            Long taskCount = customPool.submit(task)
              .get();
            assertEquals(8, taskCount);

        } finally {
            customPool.shutdown();
        }
    }
}