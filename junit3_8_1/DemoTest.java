import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * Created by Lucare.Feng on 2017/11/3.
 */
public class DemoTest extends TestCase {

    public void testSimple(){
        assertTrue(true);
        assertTrue(false);
    }

    public static void main(String[] args) {
        String[] a = {"DemoTest"};
        TestRunner.main(a);
    }

}
