package com.company.Exercise_2.Data;

import java.io.Serializable;

public class Task implements Serializable {
    private String Task_Title = null;
    private String Description = null;

    public Task(String task_Title, String description) {
        Task_Title = task_Title;
        Description = description;
    }

    public String getTask_Title() {
        return Task_Title;
    }

    public String getDescription() {
        return Description;
    }
}
