import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
    private final ByteArrayOutputStream outputStreamCaptor3 = new ByteArrayOutputStream();

    @Test
    public void main() {
        System.setOut(new PrintStream(outputStreamCaptor));
        String args[] = new String[]{"-incorrectCommand"};
        Main.main(args);
        Assert.assertEquals("Incorrect command! Try again!", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void main2(){
        String args[] = new String[]{"-createUser"};
        System.setOut(new PrintStream(outputStreamCaptor2));
        Main.main(args);
        Assert.assertEquals("Information missing!", outputStreamCaptor2.toString()
                .trim());
    }

    @Test
    public void main3(){
        String args[] = new String[]{};
        System.setOut(new PrintStream(outputStreamCaptor3));
        Main.main(args);
        Assert.assertEquals("", outputStreamCaptor3.toString()
                .trim());
    }

}