package Data;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class UserTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void nrOfTasks() {
        User A = new User("A", "B", "C");
        A.addTask(new Task("New", "Task"));
        A.addTask(new Task("New2", "Task2"));
        assertEquals(A.nrOfTasks(), 2);
    }

}