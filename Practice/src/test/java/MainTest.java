
import junit.framework.TestCase;

public class MainTest extends TestCase {

    public void test_get_size_package() {
        String path = "src/main/resources/for test/test data.txt";
        long testData = Main.getSizePackage(path);
        assertEquals(4,testData);
    }

    public void test_human_readable_format_500_byte() {
        long testCount = 500;
        String result = Main.humanReadableFormat(testCount);
        assertEquals("500 байт",result);
    }

    public void test_human_readable_format_0_byte(){
        long testCount = 0;
        String result = Main.humanReadableFormat(testCount);
        assertEquals("0 байт",result);
    }

    public void test_human_readable_format_100_milliard_byte(){
        long testCount = 100000000000L;
        String result = Main.humanReadableFormat(testCount);
        assertEquals("93,13 ГБ",result);
    }
}
