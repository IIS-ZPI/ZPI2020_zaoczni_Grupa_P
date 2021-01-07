import functions.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    Session session = new Session("test","test","2020-07-06","EUR",5);
    int tab[] = session.printSession();

    @Test
    void printSessionTestGrowthSession() {
        assertEquals(37,tab[0]);
    }
    @Test
    void printSessionTestRelegationSession() {
        assertEquals(37,tab[1]);
    }
    @Test
    void printSessionTestNoChangeSession() {
        assertEquals(2,tab[2]);
    }
}
