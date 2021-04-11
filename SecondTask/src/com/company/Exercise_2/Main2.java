package com.company.Exercise_2;

import com.company.Exercise_2.Data.Task;
import com.company.Exercise_2.Data.User;
import static com.company.Exercise_2.Storage.FileIO.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    static private File file = null;
    static private List<User> users = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException{

        //Providing path for the file where data is stored
        //Depending on where you run this app (in Intellij/from the cmd with jar) the path changes
        //The current path is set in order to work with the jar
        String filePath = new File("").getAbsolutePath();
        //int d = filePath.indexOf("out");
        System.out.println(filePath);
        filePath = filePath.concat("\\storage.txt");
        //filePath = filePath.substring(0,d);
        //filePath = filePath.concat("\\src\\com\\company\\Exercise_2\\Storage\\storage.txt");
        file = new File(filePath);

        //Checking if there are no arguments
        if(args.length==0){
            return;
        }

        //Extracting the stored users in users List
        users = extractUsers(file);

        //Verifying the arguments
        switch (args[0]){
            case "-createUser":
                createUser(args);
                break;
            case "-showAllUsers":
                for (User user:users){
                    System.out.println(user.getFirstName() + " " + user.getLastName() +
                            " " + user.nrOfTasks());
                }
                break;
            case "-addTask":
                addTask(args);
                break;
            case "-showTasks":
                showTasks(args);
                break;
            default:
                System.out.println("Incorrect command! Try again!");
                break;
        }
    }


    //Bellow are methods for parsing and validating the arguments
    //When they need to store the data, they call the method storeusers from Storage.FileIO

    public static boolean checkDuplicatedUserNames(String userName){
        for(User user:users){
            if(user.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public static void showTasks(String[] arguments){
        if(arguments.length>2){
            System.out.println("Too many arguments!");
            return;
        }

        String userName = null;
        String arg = arguments[1];

        if(arg.startsWith("-un='") && arg.endsWith("'")){
            userName = arg.substring(5, arg.length()-1);
        }

        for (User user:users){
            if(user.getUserName().equals(userName)){
                user.showUserTasks();
                return;
            }
        }

        System.out.println("No such user!");
    }

    public static void addTask(String[] arguments){

        String userName = null;
        String taskTitle = null;
        String taskDescription = null;

        StringBuilder taskT = new StringBuilder();
        StringBuilder taskD = new StringBuilder();
        String prev = "";

        int count = 0;

        for(String arg:arguments){
            if(arg.startsWith("-un='") && arg.endsWith("'")){
                userName = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-tt='") && arg.endsWith("'")){
                taskTitle = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-tt='")){
                prev = "-tt='";
                taskT.append(arg, 5, arg.length());
                taskT.append(" ");
                count++;
            }
            else if(prev.equals("-tt='")){
                if(arg.endsWith("'")){
                    taskT.append(arg, 0, arg.length()-1);
                    taskTitle = taskT.toString();
                    prev = "";
                }else {
                    taskT.append(arg).append(" ");
                }
            }
            else if(arg.startsWith("-td='") && arg.endsWith("'")){
                taskDescription = arg.substring(5, arg.length()-1);
                count++;
            }
            else if(arg.startsWith("-td='")){
                prev = "-td='";
                taskD.append(arg, 5, arg.length());
                taskD.append(" ");
                count++;
            }
            else if(prev.equals("-td='")){
                if(arg.endsWith("'")){
                    taskD.append(arg, 0, arg.length()-1);
                    taskDescription = taskD.toString();
                    prev = "";
                }else {
                    taskD.append(arg).append(" ");
                }
            }
        }

        if(count>3){
            System.out.println("Too many arguments!");
            return;
        }

        if(userName==null || taskTitle==null || taskDescription==null){
            System.out.println("Information missing!");
        }else{
            for(User user : users){
                if (user.getUserName().equals(userName)){
                    user.addTask(new Task(taskTitle, taskDescription));
                    try {
                        storeUsers(users, file);
                        System.out.println("Task added to " + userName);
                        return;
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            System.out.println("No such user!");
        }

    }

    public static void createUser(String[] arguments){
        if (arguments.length>4){
            System.out.println("Too many arguments!");
            return;
        }

        String firstName = null;
        String lastName = null;
        String userName = null;

        for(String arg:arguments){
            if(arg.startsWith("-fn='") && arg.endsWith("'")){
                firstName = arg.substring(5, arg.length()-1);
            }
            else if(arg.startsWith("-ln='") && arg.endsWith("'")){
                lastName = arg.substring(5, arg.length()-1);
            }
            else if(arg.startsWith("-un='") && arg.endsWith("'")){
                userName = arg.substring(5, arg.length()-1);
            }
        }

        if(firstName==null || lastName==null || userName==null){
            System.out.println("Information missing!");
        }else if(!checkDuplicatedUserNames(userName)){
            users.add(new User(firstName, lastName, userName));
            try {
                storeUsers(users, file);
                System.out.println("New User " + userName + " added!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("This username already exists!");
        }
    }
}
