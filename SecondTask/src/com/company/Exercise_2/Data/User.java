package com.company.Exercise_2.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String FirstName = null;
    private String LastName = null;
    private String userName = null;

    private List<Task> tasks = null;

    public User(String firstName, String lastName, String userName) {
        FirstName = firstName;
        LastName = lastName;
        this.userName = userName;
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void showUserTasks(){
        System.out.println("Tasks of " + userName + ":");
        if(tasks.size()==0){
            System.out.println("NONE");
            return;
        }
        for(Task task:tasks){
            System.out.println("Task Title: " + task.getTask_Title() +
                    "\nDescription: " + task.getDescription() + "\n");
        }
    }

    public int nrOfTasks(){
        return tasks.size();
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getUserName() {
        return userName;
    }
}
