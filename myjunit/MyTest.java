/**
 * Created by Lucare.Feng on 2017/11/4.
 */
public class MyTest extends MyTestCase{

    public void testSimple(){
        assertTrue(true);
        assertTrue(false);
    }

    public static void main(String[] args) {
        MyTestRunner.main(new String[]{"MyTest"});
    }
}
