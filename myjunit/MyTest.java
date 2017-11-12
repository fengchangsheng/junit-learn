/**
 * Created by Lucare.Feng on 2017/11/4.
 */
public class MyTest extends MyTestCase{

    public void testError1(){
        System.out.println("error method 1");
        System.out.println(1/0);
    }

    public void testSimple(){
        System.out.println("testSimple has invoke..");
        assertTrue(true);
        assertTrue(false);
    }

    public void testError(){
        System.out.println("error method");
        System.out.println(1/0);
    }

    private void testPrivateMethod(){
        System.out.println("private method");
    }

    public static void main(String[] args) {
        MyTestRunner.main(new String[]{"MyTest"});
    }
}
