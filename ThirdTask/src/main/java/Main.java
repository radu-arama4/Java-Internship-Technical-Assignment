
import DB.DButil;
import static Controller.Controller.*;
import Data.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Connection to the DataBase
        DButil.connectToDb();

        //Retrieving the data from the database
        setGroups(DButil.getGroups());
        setUsers(DButil.getUsers());

        //Checking if the main function didn't get any arguments
        if(args.length==0){
            return;
        }

        //Checking all the accepted variants of arguments
        //Calling the methods from Controller
        switch (args[0]){
            case "-createUser":
                createUser(args);
                break;
            case "-showAllUsers":
                showAllUsers();
                break;
            case "-addTask":
                addTask(args);
                break;
            case "-showTasks":
                showTasks(args);
                break;
            case "-createGroup":
                createGroup(args);
                break;
            case "-addUserToGroup":
                addToGroup(args);
                break;
            case "-addTaskToGroup"://db
                addGroupTask(args);
                break;
            default:
                System.out.println("Incorrect command! Try again!");
                break;
        }

        //Disconnecting from the database
        DButil.disconnectFromDb();
    }
}
