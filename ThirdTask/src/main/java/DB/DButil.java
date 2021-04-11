package DB;

import Data.Group;
import Data.Task;
import Data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//Class containing static methods responsible for all interactions with the Database
public class DButil {
    //Providing the credentials for the connection to the database
    //Could be declared in a .property file as well
    static private final String url = "jdbc:mysql://127.0.0.1:3306/users?user=root?allowPublicKeyRetrieval=" +
            "true&useSSL=false&serverTimezone=Europe/Warsaw";
    static private final String user = ""; // insert the user
    static private final String password = ""; // insert the password
    static private Connection connection = null;

    private static List<Group> groups = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public static List<Group> getGroups(){
        return groups;
    }

    public static List<User> getUsers(){
        return users;
    }

    //Method for connecting to the database
    //It assigns the connection to the connection variable, which is available for all the methods from this class
    public static void connectToDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            getGroups(connection);
            getUsers(connection);
            getTasks(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Method for disconnecting from the database
    public static void disconnectFromDb() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    //Method for storing a new user in the database
    public static boolean addUserToDB(User usr){
        try {
            String firstName = usr.getFirstName();
            String lastName = usr.getLastName();
            String userName = usr.getUserName();
            String query = "INSERT INTO user(firstName,lastName,userName) " +
                    "VALUES('" + firstName + "','"+ lastName +"','" + userName + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method for storing a new group to the database
    public static boolean addGroupToDB(Group group){
        try {
            String groupName = group.getGroupName();
            String query = "INSERT INTO users.group(groupName) " +
                    "VALUES('" + groupName + "')";
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Method for adding an existing user to a group
    public static boolean addUserToGroupDB(User user, Group group){
        try {
            String groupName = group.getGroupName();
            String userName = user.getUserName();

            String query = "UPDATE user SET groupName = '" + groupName +
                    "' WHERE userName LIKE '" + userName + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Method for adding a task to a specific group
    public static boolean addTaskToGroupDB(Task task, Group group){
        List<User> groupUsers = group.getGroupUsers();

        for(User usr:groupUsers){
            try {
                String taskTitle = task.getTask_Title();
                String taskDescription = task.getDescription();
                String userName = usr.getUserName();

                String query = "INSERT INTO task(Task_Title,Description,userName) " +
                        "VALUES('" + taskTitle + "','"+ taskDescription +"','" + userName + "')";

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    //Method for adding a task to a specific user
    public static boolean addTaskToUser(Task task, User usr){
        try {
            String userName = usr.getUserName();
            String taskTitle = task.getTask_Title();
            String taskDescription = task.getDescription();

            String query = "INSERT INTO task(Task_Title,Description,userName) " +
                    "VALUES('" + taskTitle + "','"+ taskDescription +"','" + userName + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method for retrieving the users from the database
    public static List<User> getUsers(Connection connection) throws SQLException {
        final String query = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String userName = rs.getString("userName");
            String groupName = rs.getString("groupName");

            User newUser = new User(firstName, lastName, userName);
            users.add(newUser);

            if(groupName!=null) {
                for (Group group : groups) {
                    if (groupName.equals(group.getGroupName())) {
                        group.addUserToGroup(newUser);
                    }
                }
            }

        }
        rs.close();
        return users;
    }

    //Method for retrieving the tasks from the database
    public static void getTasks(Connection connection) throws SQLException {
        final String query = "SELECT * FROM task";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            String Task_Title = rs.getString("Task_Title");
            String Description = rs.getString("Description");
            String userName = rs.getString("userName");

            for(User user:users){
                if (user.getUserName().equals(userName)){
                    user.addTask(new Task(Task_Title, Description));
                }
            }
        }
        rs.close();
    }

    //Method for retrieving the groups from the database
    public static void getGroups(Connection connection) throws SQLException {
        final String query = "SELECT * FROM `group`";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            String groupName = rs.getString("groupName");
            groups.add(new Group(groupName));
        }
        rs.close();
    }

}
