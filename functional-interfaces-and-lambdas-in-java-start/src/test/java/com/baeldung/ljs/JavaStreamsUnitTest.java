package com.baeldung.ljs;

import com.baeldung.ljs.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaStreamsUnitTest {

    @Test
    void givenFunctionalInterfacesAsAnonymousClasses_whenExecutingThem_thenAllReturnCorrectResult() {

        Function<String, Integer> strLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        assertEquals(8, strLength.apply("Baeldung"));

        BiFunction<Integer, Integer, Double> divideToDouble = new BiFunction<Integer, Integer, Double>() {
            @Override
            public Double apply(Integer integer, Integer integer2) {
                return integer.doubleValue() / integer2.doubleValue();
            }
        };
        assertEquals(2.0, divideToDouble.apply(4, 2));

        Consumer<String> printString = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        printString.accept("Hello Baeldung");

        Supplier<String> idGenerator = new Supplier<String>() {
            @Override
            public String get() {
                return UUID.randomUUID().toString();
            }
        };
        assertTrue(idGenerator.get().length() > 0);

        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        };
        assertTrue(isEven.test(4));
    }

    @Test
    void givenFunctionalInterfacesAsLambdas_whenExecutingThem_thenAllReturnCorrectResult() {
        Supplier<String> idGenerator = () -> UUID.randomUUID().toString();

        Function<String, Integer> strLength = s -> s.length();

        BiFunction<Integer, Integer, Double> divideToDouble = (s1,s2) -> s1.doubleValue() / s2.doubleValue();

        Consumer<String> printString = s -> System.out.println(s);

        Predicate<Integer> isEven = i -> i % 2 == 0;
    }

    @Test
    void givenTask_whenBuildingDueYearSummary_thenSummaryConstructed() {
        String summaryConnector = "is due on";

        // Uncommenting the next line would break the lambda below:
//        summaryConnector = "changed connector"; // Compile-time error: not effectively final

        Task t1 = new Task("T1", "Task Name", "Task Description", LocalDate.of(2025, 1, 1));

        Function<Task, String> dueYearSummary = task -> {
            final String name = task.getName();
            final int dueYear = task.getDueDate().getYear();
            return "%s %s %s".formatted(name, summaryConnector, dueYear);
        };

        String summaryValue = dueYearSummary.apply(t1);

        assertEquals("Task Name is due on 2025", summaryValue);
    }

    @Test
    void givenAList_whenUsingMethodReference_thenGetResult() {
        Function<Task, LocalDate> getDueDate = task -> task.getDueDate();
        Function<Task, LocalDate> getDueDateRef = Task::getDueDate;

        Task t1 = new Task("T1", "Task Name", "Task Description", LocalDate.of(2025, 1, 1));
        LocalDate dueDate = getDueDate.apply(t1);
        LocalDate dueDateRef = getDueDateRef.apply(t1);

        assertEquals(dueDateRef, dueDate);
    }

    private final List<Task> tasks = List.of(
            new Task("T1", "Task 1", "Task 1", LocalDate.now()),
            new Task("T2", "Task 2", "Task 2", LocalDate.now()),
            new Task("T3", "Task 3", "Task 3", LocalDate.now()),
            new Task("S1", "Task 4", "Task 4", LocalDate.now())
    );

    @Test
    void givenListOfTasks_whenJoiningCodesUsingStream_thenCorrectStringIsReturned() {
        String combinedCodes = tasks.stream()
                .map(Task::getCode)
                .filter(code -> code.startsWith("T"))
                .collect(Collectors.joining(", "));

        assertEquals("T1, T2, T3", combinedCodes);
    }
}