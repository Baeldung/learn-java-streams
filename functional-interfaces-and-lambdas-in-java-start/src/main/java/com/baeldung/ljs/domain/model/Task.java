package com.baeldung.ljs.domain.model;

import java.time.LocalDate;

public class Task {

    private String code;

    private String name;

    private String description;

    private LocalDate dueDate;

    public Task(String code, String name, String description, LocalDate dueDate) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task [code= " + code + "name=" + name + ", description=" + description + ", dueDate=" + dueDate + "]";
    }
}
