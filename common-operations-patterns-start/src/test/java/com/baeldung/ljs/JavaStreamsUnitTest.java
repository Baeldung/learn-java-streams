package com.baeldung.ljs;

import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

class JavaStreamsUnitTest {
    private final List<Task> tasks = List.of(
        new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2045, 1, 1), TaskStatus.IN_PROGRESS),
        new Task("D2", "Tommy", "Preparation for the exam", LocalDate.of(2024, 5, 20), TaskStatus.ON_HOLD),
        new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), TaskStatus.DONE),
        new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2040, 6, 30), TaskStatus.IN_PROGRESS),
        new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2020, 11, 15), TaskStatus.DONE),
        new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2070, 9, 25), TaskStatus.ON_HOLD),
        new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2055, 5, 18), TaskStatus.IN_PROGRESS),
        new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
        new Task("D7", "Big Bang restoration", "Restoration of BigBan in London", LocalDate.of(2029, 2, 22), TaskStatus.DONE),
        new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2050, 6, 10), TaskStatus.IN_PROGRESS)
    );
}
