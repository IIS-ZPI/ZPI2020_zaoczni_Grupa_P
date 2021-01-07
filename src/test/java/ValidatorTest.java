import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    Validator validator = new Validator();
    @Test
    void getNumberTest() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        assertEquals(1,validator.getNumber());
    }

    @Test
    void getNumberInvalidInputTest() {
        ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class,() -> validator.getNumber());
    }

    @Test
    void getNumberOutOfRangeTest() {
        ByteArrayInputStream in = new ByteArrayInputStream("55".getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class,() -> validator.getNumber());
    }

    @Test
    void getFunctionTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        assertEquals(1,validator.getFunction());
    }

    @Test
    void getFunctionInvalidInputTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class,() -> validator.getFunction());
    }

    @Test
    void getFunctionOutOfRangeTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("55".getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class,() -> validator.getFunction());
    }

    @Test
    void getPeriodTimeOneWeekTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.WEEK_OF_MONTH,-1);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }
    @Test
    void getPeriodTimeTwoWeeksTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.WEEK_OF_MONTH,-2);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }
    @Test
    void getPeriodTimeOneMonthTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.MONTH,-1);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }

    @Test
    void getPeriodTimeThreeMonthsTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.MONTH,-3);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }

    @Test
    void getPeriodTimeHalfOfYearTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.MONTH,-6);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }

    @Test
    void getPeriodTimeOneYearTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("6".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.YEAR,-1);

        String periodTime = formatter.format(calendar.getTime());

        assertEquals(periodTime,validator.getPeriodTime());
    }

    @Test
    void getPeriodTimeOutOfRangeTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("7".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.YEAR,-1);

        String periodTime = formatter.format(calendar.getTime());

        assertThrows(NoSuchElementException.class,() -> validator.getPeriodTime());
    }

    @Test
    void getPeriodTimeInvaliInputTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
        System.setIn(in);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.YEAR,-1);

        String periodTime = formatter.format(calendar.getTime());

        assertThrows(NoSuchElementException.class,() -> validator.getPeriodTime());
    }
}
