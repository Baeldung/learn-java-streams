package com.baeldung.ljs.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Task {

    private String code;

    private String name;

    private String description;

    private LocalDate dueDate;

    private List<String> labels;

    public Task(String code, String name, String description, LocalDate dueDate, List<String> labels) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.labels = labels;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Task [code= " + code + "name=" + name + ", description=" + description + ", dueDate=" + dueDate + ", labels=" + String.join(",", labels) + "]";
    }
}
