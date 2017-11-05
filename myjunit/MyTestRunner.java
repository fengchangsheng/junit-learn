/**
 * Created by Lucare.Feng on 2017/11/4.
 */
import java.lang.reflect.Method;
public class MyTestRunner {

    public static void main(String[] args) {
//        String mthodName = args[0];
        String clazzName = args[0];
        System.out.println(clazzName);
        long startTime= System.currentTimeMillis();
        long startNanoTime = System.nanoTime();
        Class clazz = Thread.currentThread().getClass();
        try {
            Object object = Class.forName(clazzName).newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
//                method.invoke(object,);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        long endTime= System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        long runTime= endTime-startTime;
        long runNanoTime= endNanoTime-startNanoTime;
        System.out.println("run with " +runTime);
        System.out.println("run with nanoTime " +runNanoTime);
    }

}
