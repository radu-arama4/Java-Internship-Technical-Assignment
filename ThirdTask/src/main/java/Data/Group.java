package Data;

import java.util.ArrayList;
import java.util.List;

public class Group{
    private String groupName = null;
    private List<User> groupUsers = null;
    private List<Task> groupTasks = null;

    public Group(String groupName) {
        this.groupName = groupName;
        groupUsers = new ArrayList<>();
        groupTasks = new ArrayList<>();
    }

    public void addUserToGroup(User user){
        if(groupUsers.contains(user)){
            System.out.println("User already in this group");
        }else {
            groupUsers.add(user);
        }
    }

    public List<User> getGroupUsers() {
        return groupUsers;
    }

    public String getGroupName() {
        return groupName;
    }
}
