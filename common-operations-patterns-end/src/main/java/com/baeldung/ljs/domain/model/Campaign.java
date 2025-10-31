package com.baeldung.ljs.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Campaign {

    private String name;

    private List<Task> tasks = new ArrayList<>();

    public Campaign(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public Campaign() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Campaign [name=" + name + ", tasks=" + tasks + "]";
    }
}