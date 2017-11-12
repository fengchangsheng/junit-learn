import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 哪些方法可以执行  private static  public
 * 需要有构造函数
 * 方法前后的增强 运行时间  打印内容
 * 结果的统计
 * 运行时异常如何处理
 * 如何保证各个方法的执行互不影响
 */
public class MyTestRunner {

    private static List<Method> methodList = new ArrayList();
    private static int failuresNum = 0;
    private static int errorNum = 0;
    private static List<Exception> exceptions = new ArrayList<>();
    private static Map<String, Object> errorMap = new HashMap<>();

    public static Constructor getConstructor(Class clazz) throws NoSuchMethodException {
        try {
            Constructor constructor = clazz.getConstructor(new Class[]{String.class});
            return constructor;
        } catch (NoSuchMethodException e) {
            //ignore
        }
        return clazz.getConstructor(new Class[0]);
    }

    /**
     * 检测并保存待测试方法(根据约定testA)
     */
    public static void checkAndSaveMethods(Method[] methods) {
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                methodList.add(method);
            }
        }
    }

    public static void main(String[] args) {
        String clazzName = args[0];
        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();

//        System.out.println("Thread.currentThread().getClass()===>"+Thread.currentThread().getClass());
        doRun(clazzName);

        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        long runTime = endTime - startTime;
        long runNanoTime = endNanoTime - startNanoTime;
        System.out.println("run with " + runTime);
        System.out.println("run with nanoTime " + runNanoTime);
    }

    public static void doRun(String clazzName) {
        try {
            Class clazz = Class.forName(clazzName);
            Method[] methods = clazz.getMethods();
            Constructor constructor = getConstructor(clazz);
            Object object;
            if (constructor.getParameterTypes().length == 0) {
                object = constructor.newInstance(new Object[0]);
            } else {
                String myName = "lucare.Feng";
                object = constructor.newInstance(myName);
            }
            checkAndSaveMethods(methods);
            invokeMethods(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            recordFailures();
        }
    }

    public static void recordFailures() {
        System.out.println("There were " + errorNum + " error:");
        for (Exception exception : exceptions) {
            System.out.println(exception.getCause());
        }
        System.out.println("There were " + failuresNum + " failures:");

    }

    private static void invokeMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        try {
            for (Method method : methodList) {
                method.invoke(object);
            }
        } catch (InvocationTargetException et) {
            errorNum++;
            exceptions.add(et);
//            errorMap.put();
//            throw et;
        } catch (IllegalAccessException ea) {
            throw ea;
        }
    }

}
