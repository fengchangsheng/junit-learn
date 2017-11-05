/**
 * Created by Lucare.Feng on 2017/11/4.
 */
public class MyAssert {

    public static boolean assertTrue(boolean arg){
        return arg ? true : false;
    }

    public static boolean assertFalse(boolean arg){
        return !assertTrue(arg);
    }

}
