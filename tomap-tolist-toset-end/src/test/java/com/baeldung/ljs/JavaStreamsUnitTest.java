package com.baeldung.ljs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18),
            TaskStatus.IN_PROGRESS),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), TaskStatus.IN_PROGRESS));

    @Test
    void whenCollectingTaskCodesToList_thenAllCodesArePresentInOrder() {
        List<String> taskCodeList = tasks.stream()
            .map(Task::getCode)
            .collect(Collectors.toList());

        assertEquals(List.of("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8"), taskCodeList);
    }

    @Test
    void whenCollectingToUnmodifiableList_thenModificationNotAllowed() {
        List<String> unmodifiableList = tasks.stream()
            .map(Task::getCode)
            .collect(Collectors.toUnmodifiableList());

        assertTrue(unmodifiableList.contains("T1"));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("T9"));
    }

    @Test
    void whenUsingStreamToList_thenUnmodifiableListIsReturned() {
        List<String> taskCodes = tasks.stream()
            .map(Task::getCode)
            .toList();

        assertTrue(taskCodes.contains("T2"));
        assertThrows(UnsupportedOperationException.class, () -> taskCodes.remove("T2"));
    }

    @Test
    void whenCollectingToSet_thenOnlyUniqueElementsArePresent() {
        Set<String> uniqueTaskCodes = Stream.concat(tasks.stream().map(Task::getCode), Stream.of("T1"))
            .collect(Collectors.toSet());

        assertEquals(tasks.size(), uniqueTaskCodes.size());
    }

    @Test
    void whenCollectingToUnmodifiableSet_thenModificationNotAllowed() {
        Set<String> unmodifiableSet = tasks.stream()
            .map(Task::getCode)
            .collect(Collectors.toUnmodifiableSet());

        assertEquals(unmodifiableSet.size(), tasks.size());
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableSet.remove("T1"));
    }

    @Test
    void whenCollectingToMap_thenCorrectMappingsArePresent() {
        Map<String, String> taskCodeToName = tasks.stream()
            .collect(Collectors.toMap(Task::getCode, Task::getName));

        assertEquals("John's house construction", taskCodeToName.get("T1"));
        assertEquals("Lily's house construction", taskCodeToName.get("T4"));
    }

    @Test
    void whenCollectingToMapWithMergeFunction_thenDuplicatesHandled() {
        Map<TaskStatus, String> statusToNames = tasks.stream()
            .collect(Collectors.toMap(Task::getStatus, Task::getName, (n1, n2) -> n1 + ", " + n2));

        assertEquals(
            "John's house construction, " + "Flower Cafe construction, " + "West Outer Ring street construction, " + "Jane's Jacket factory reparation",
            statusToNames.get(TaskStatus.IN_PROGRESS));
    }

    @Test
    void whenCollectingToTreeMap_thenKeysAreSorted() {
        TreeMap<String, String> sortedMap = tasks.stream()
            .collect(Collectors.toMap(Task::getCode, Task::getName, (a, b) -> a, TreeMap::new));

        assertEquals("T1", sortedMap.firstKey());
    }

    @Test
    void whenCollectingToUnmodifiableMap_thenModificationNotAllowed() {
        Map<String, String> unmodifiableMap = tasks.stream()
            .collect(Collectors.toUnmodifiableMap(Task::getCode, Task::getName));

        assertEquals("John's house construction", unmodifiableMap.get("T1"));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("T7", "Extra Task"));
    }

    @Test
    void whenCollectingToLinkedList_thenCorrectTypeIsReturned() {
        LinkedList<String> taskLinkedList = tasks.stream()
            .map(Task::getCode)
            .collect(Collectors.toCollection(LinkedList::new));

        assertInstanceOf(LinkedList.class, taskLinkedList);
    }

    @Test
    void whenCollectingToPreSizedArrayList_thenCorrectTypeIsReturned() {
        ArrayList<String> arrayList = tasks.stream()
            .map(Task::getName)
            .collect(Collectors.toCollection(() -> new ArrayList<>(tasks.size())));

        assertEquals(tasks.size(), arrayList.size());
    }

    @Test
    void whenCollectingToTreeSetWithComparator_thenElementsAreSorted() {
        TreeSet<Task> sortedByName = tasks.stream()
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Task::getName))));

        assertEquals("Bee Steak House restoration", sortedByName.first().getName());
    }
}