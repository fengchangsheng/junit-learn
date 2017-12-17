import junit.DemoTwoTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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

//    private void testPrivateMethod(){
//        System.out.println("private method");
//    }


    @Override
    protected void setUp() throws Exception {
        System.out.println("===============>> hello, i have set up.");
    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("<<============== bye, i have tear down.");
    }

    public static void testStaticMethod(){
        System.out.println("static method");
    }

    public void testError(){
        System.out.println("testError");
//        System.out.println(1/0);
    }

    public void testSimple(){
        System.out.println("testSimple has invoke..");
        assertTrue(true);
        assertTrue(false);
    }

    public void testOther(){
        System.out.println(55555);
        System.out.println(1/0);
    }

//    public static Test suite(){
//        System.out.println(" is a suite collection.");
//        TestSuite suite = new TestSuite(DemoTest.class, "testError");
////        suite.addTest(new DemoTest("testError"));
////        suite.addTest(new DemoTest("testSimple"));
////        suite.addTestSuite(DemoTest.class);
////        suite.addTestSuite(DemoTwoTest.class);
//        return suite;
//    }

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
