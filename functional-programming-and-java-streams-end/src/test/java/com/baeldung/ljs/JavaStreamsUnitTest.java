package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {

    private final List<Task> tasks = List.of(
        new Task("T1", "Task 1", "Task 1", LocalDate.now()),
        new Task("T2", "Task 2", "Task 2", LocalDate.now()),
        new Task("T3", "Task 3", "Task 3", LocalDate.now()),
        new Task("S1", "Task 4", "Task 4", LocalDate.now())
    );

    String joinCodesImperatively(List<Task> tasks) {
        List<String> codes = new ArrayList<>();
        tasks.forEach(t -> {
            if (t.getCode().startsWith("T")) {
                codes.add(t.getCode());
            }
        });
        return String.join(", ", codes);
    }

    String joinCodesWithStream(List<Task> tasks) {
        return tasks.stream()
            .map(Task::getCode)
            .filter(code -> code.startsWith("T"))
            .collect(Collectors.joining(", "));
    }

    @Test
    void givenListOfTasks_whenJoiningCodesUsingImperative_thenCorrectStringIsReturned() {
        String combinedCodes = joinCodesImperatively(tasks);
        assertEquals("T1, T2, T3", combinedCodes);
    }

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenCorrectStringIsReturned() {
        String combinedCodes = joinCodesWithStream(tasks);
        assertEquals("T1, T2, T3", combinedCodes);
    }

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenGetTheSameAsIterativeApproach() {
        assertEquals(joinCodesWithStream(tasks), joinCodesImperatively(tasks));
    }
}