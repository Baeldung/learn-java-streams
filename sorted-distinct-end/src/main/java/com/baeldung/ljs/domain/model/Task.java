package com.baeldung.ljs.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task implements Comparable<Task>{
    
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task other)) return false;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    @Override
    public int compareTo(Task other) {
        return this.getDueDate().compareTo(other.getDueDate());
    }
}
