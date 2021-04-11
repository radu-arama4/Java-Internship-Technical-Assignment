package Data;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GroupTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    Group myGroup = new Group("GroupName");

    @Test
    public void addUserToGroup() {
        System.setOut(new PrintStream(outputStreamCaptor));
        User firstUser = new User("A", "B", "C");
        myGroup.addUserToGroup(firstUser);
        myGroup.addUserToGroup(firstUser);
        Assert.assertEquals("User already in this group", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void getGroupUsers() {
        List<User> users = new ArrayList<>();
        User firstUser = new User("A", "B", "C");
        User secondUser = new User("X", "Y", "Z");
        myGroup.addUserToGroup(firstUser);
        users.add(firstUser);
        assertEquals(myGroup.getGroupUsers(), users);
        myGroup.addUserToGroup(secondUser);
        users.add(secondUser);
        assertEquals(myGroup.getGroupUsers(), users);
    }

    @Test
    public void getGroupName() {
        assertEquals(myGroup.getGroupName(), "GroupName");
    }
}