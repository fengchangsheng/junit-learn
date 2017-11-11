import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * Created by Lucare.Feng on 2017/11/3.
 */
public class DemoTest extends TestCase {

//    public DemoTest() {
//        System.out.println("just no param..");
//    }
//
//    public DemoTest(String a) {
//        System.out.println("just a String param..");
//    }
//
//    public DemoTest(String a, int b){
//        System.out.println("just test...");
//    }

    public void testSimple(){
        assertTrue(true);
        assertTrue(false);
    }

    public void testOther(){
        System.out.println(55555);
//        System.out.println(1/0);
    }

    public static void main(String[] args) {
        try {
//            new DemoTest("a").testOther();
            String[] a = {"DemoTest"};
            TestRunner.main(a);
        } catch (Exception e) {
            e.printStackTrace();
//            String[] a = {"DemoTest"};
//            TestRunner.main(a);
        }
    }

}
