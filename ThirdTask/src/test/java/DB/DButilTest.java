package DB;

import Data.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DButilTest {

    @Test
    public void getGroups() {
        //with no connection to database
        assertEquals(DButil.getGroups().size(), 0);
    }

    @Test
    public void getUsers() {
        //with no connection to database
        assertEquals(DButil.getUsers().size(), 0);
    }

}