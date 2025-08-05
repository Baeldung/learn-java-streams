package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import com.baeldung.ljs.domain.model.Task;

class JavaStreamsUnitTest {

    @Test
    void givenFunctionalInterfacesAsLambdas_whenExecutingThem_thenAllReturnCorrectResult() {
        Supplier<String> idGenerator = () -> UUID.randomUUID()
            .toString();

        Function<String, Integer> strLength = s -> s.length();

        BiFunction<Integer, Integer, Double> divideToDouble = (s1, s2) -> s1.doubleValue() / s2.doubleValue();

        Consumer<String> printString = s -> System.out.println(s);

        Predicate<Integer> isEven = s -> s % 2 == 0;

        // Supplier
        String newId = idGenerator.get();
        assertTrue(!newId.isEmpty());

        // Function
        Integer lengthOfString = strLength.apply("test");
        assertEquals(4, lengthOfString);

        // BiFunction
        Double result = divideToDouble.apply(5, 2);
        assertEquals(2.5, result);

        // Consumer
        printString.accept("Showing from consumer");

        // Predicate
        boolean is10Even = isEven.test(10);
        boolean is11Even = isEven.test(11);
        assertTrue(is10Even);
        assertFalse(is11Even);
    }

    @Test
    void givenTask_whenBuildingDueYearSummary_thenSummaryConstructed() {
        String summaryConnector = "is due on";

        // Uncommenting the next line would break the lambda below:
        // summaryConnector = "changed connector"; // Compile-time error: not effectively final Unchanged:  // suffix = "overdue"; // Compile-time error: not effectively final

        Task t1 = new Task("T1", "Task Name", "Task Description", LocalDate.of(2050, 1, 1));

        Function<Task, String> dueYearSummary = task -> {
            final String name = task.getName();
            final int dueYear = task.getDueDate()
                .getYear();
            return "%s %s %s".formatted(name, summaryConnector, dueYear);
        };

        String summaryValue = dueYearSummary.apply(t1);

        assertEquals("Task Name is due on 2050", summaryValue);
    }

    @Test
    void givenAList_whenUsingMethodReference_thenGetResult() {
        Function<Task, LocalDate> getDueDate = task -> task.getDueDate();
        Function<Task, LocalDate> getDueDateRef = Task::getDueDate;

        Task t1 = new Task("T2", "Task 2 Name", "Task 2 Description", LocalDate.of(2050, 1, 1));
        LocalDate dueDate = getDueDate.apply(t1);
        LocalDate dueDateRef = getDueDateRef.apply(t1);

        assertEquals(dueDate, dueDateRef);
    }

}
