package Controller;

import Data.Group;
import Data.Task;
import Data.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static DB.DButil.*;

//Class with methods for parsing and validating the arguments from the Main.main method
//After parsing, if it has to be stored some data, are called the methods from DBUtil
public class Controller {

    static private List<User> users = new ArrayList<>();
    static private List<Group> groups = new ArrayList<>();

    public static void setUsers(List<User> users) {
        Controller.users = users;
    }

    public static void setGroups(List<Group> groups) {
        Controller.groups = groups;
    }

    public static boolean checkDuplicatedUserNames(String userName){
        for(User user:users){
            if(user.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    //-showAllUsers
    public static void showAllUsers(){
        for (User user:users){
            System.out.println(user.getFirstName() + " " + user.getLastName() +
                    " " + user.nrOfTasks());
        }
    }

    //-createUser
    public static void createUser(String[] arguments){
        if (arguments.length>4){
            System.out.println("Too many arguments!");
            System.out.println(Arrays.toString(arguments));
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
            //users.add(new User(firstName, lastName, userName));
            if(addUserToDB(new User(firstName, lastName, userName))){
                System.out.println("New User " + userName + " added!");
            }
        }else {
            System.out.println("This username already exists!");
        }
    }

    //-addTask
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

                    if(addTaskToUser(new Task(taskTitle, taskDescription), user)){
                        System.out.println("Task added to " + userName);
                        return;
                    }
                    //user.addTask(new Task(taskTitle, taskDescription));
                }
            }
            System.out.println("No such user!");
        }
    }

    //-showTasks
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

    //-createGroup
    public static void createGroup(String[] arguments){
        if(arguments.length>2){
            System.out.println("Too many arguments!");
            return;
        }

        String groupName = null;

        if(arguments[1].startsWith("-gn='") && arguments[1].endsWith("'")){
            groupName = arguments[1].substring(5, arguments[1].length()-1);
            //groups.add(new Group(groupName));
            if(addGroupToDB(new Group(groupName))){
                System.out.println("New group " + groupName + " created!");
            }
        }
        System.out.println("Incorrect argument or already existing group!");
    }

    //-addUserToGroup
    public static void addToGroup(String[] arguments){
        if(arguments.length>3){
            System.out.println("Too many arguments!");
            return;
        }

        String groupName = null;
        String userName = null;

        for(String arg:arguments){
            if(arg.startsWith("-gn='") && arg.endsWith("'")){
                groupName = arg.substring(5, arg.length()-1);
            }
            else if(arg.startsWith("-un='") && arg.endsWith("'")){
                userName = arg.substring(5, arg.length()-1);
            }
        }

        if(groupName!=null || userName!=null){
            User addedUser = null;
            for (User user:users){
                if(user.getUserName().equals(userName)){
                    addedUser = user;
                    break;
                }
            }
            if(addedUser==null){
                System.out.println("No such user!");
                return;
            }
            for(Group group:groups){
                if(group.getGroupName().equals(groupName)){
                    if(addUserToGroupDB(addedUser, group)){
                        System.out.println("User " + userName + " added to group " + groupName);
                        return;
                    }
                }
            }
            System.out.println("No such group!");
        }else {
            System.out.println("Missing information!");
        }
    }

    //-addTaskToGroup
    public static void addGroupTask(String[] arguments){
        String groupName = null;
        String taskTitle = null;
        String taskDescription = null;

        StringBuilder taskT = new StringBuilder();
        StringBuilder taskD = new StringBuilder();
        String prev = "";

        int count = 0;

        for(String arg:arguments){
            if(arg.startsWith("-gn='") && arg.endsWith("'")){
                groupName = arg.substring(5, arg.length()-1);
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

        System.out.println(groupName + " " + taskTitle + " " + taskDescription);

        if(groupName==null || taskTitle==null || taskDescription==null){
            System.out.println("Information missing!");
        }else{
            for(Group group : groups){
                if (group.getGroupName().equals(groupName)){
                    if (addTaskToGroupDB(new Task(taskTitle, taskDescription), group)){
                        System.out.println("Task added to group " + groupName);
                        return;
                    }
                }
            }
            System.out.println("No such group!");
        }
    }
}
