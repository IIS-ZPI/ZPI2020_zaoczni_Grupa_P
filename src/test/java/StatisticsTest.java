import functions.Statistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsTest {
    Statistics statistics = new Statistics("2020-07-06","EUR",5);
    float tab[] = statistics.calculate();

    @Test
    void calculateTestMediana() {
        assertEquals(4.473800182342529,tab[0]);
    }

    @Test
    void calculateTestOdchylenie() {
        assertEquals(0.05885609984397888,tab[1]);
    }

    @Test
    void calculateTestZmiennosc() {
        assertEquals(1.3149752616882324,tab[2]);
    }

    @Test
    void calculateTestDominanta() {
        assertEquals(4.4745001792907715,tab[3]);
    }

}
