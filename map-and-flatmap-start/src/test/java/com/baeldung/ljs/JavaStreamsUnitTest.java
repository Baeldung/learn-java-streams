package com.baeldung.ljs;

import com.baeldung.ljs.domain.model.Task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

class JavaStreamsUnitTest {
    private final Collection<Task> tasks = List.of(
            new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1), List.of("home", "construction")),
            new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), List.of("school", "reparation")),
            new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30), List.of("restaurant", "construction")),
            new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15), List.of("home", "construction")),
            new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25), List.of("restaurant", "restoration")),
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18), List.of("street", "construction")),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), List.of("bridge", "restoration")),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), List.of("factory", "reparation")));
}