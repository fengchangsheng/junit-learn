/**
 * Created by Lucare.Feng on 2017/11/4.
 * 断言失败  如何记录   以异常形式抛出？
 * 为何选择继承自Error而不是Exception
 * 这个message干嘛   最终怎么获取
 */
public class MyAssert {

    public static void assertTrue(boolean arg) {
        if (!arg)
            fail();
    }

    public static void assertFalse(boolean arg) {
        assertTrue(!arg);
    }

    public static void assertEquals(String excepted, String actual){
        if (!excepted.equals(actual))
            fail();
    }

    public static void fail(){
        throw new AssertFailedError();
    }

}
