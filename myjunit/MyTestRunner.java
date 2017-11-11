import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法需要是public的
 * 需要有构造函数
 * 方法前后的增强  运行时间  打印内容
 * 结果的统计
 * 抛异常是为了处理什么
 */
public class MyTestRunner {

    private static List<Method> methodList = new ArrayList();

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
    public static void checkAndSaveMethods(Method[] methods){
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                methodList.add(method);
            }
        }
    }

    public static void main(String[] args) {
        String clazzName = args[0];
        long startTime= System.currentTimeMillis();
        long startNanoTime = System.nanoTime();

//        System.out.println("Thread.currentThread().getClass()===>"+Thread.currentThread().getClass());
        try {
            Class clazz = Class.forName(clazzName);
            Method[] methods = clazz.getMethods();
            Constructor constructor = getConstructor(clazz);
            Object object;
            if (constructor.getParameterTypes().length == 0) {
                object = constructor.newInstance(new Object[0]);
            }else {
                String myName = "lucare.Feng";
                object = constructor.newInstance(myName);
            }
            checkAndSaveMethods(methods);
            invokeMethods();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        long endTime= System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        long runTime= endTime-startTime;
        long runNanoTime= endNanoTime-startNanoTime;
        System.out.println("run with " +runTime);
        System.out.println("run with nanoTime " +runNanoTime);
    }

    /**
     * TODO: 1. 如何调用
     *       2. 是否区分静态与非静态
     */
    private static void invokeMethods() {
//        for (Method method : methodList) {
//            method.invoke();
//        }
    }

}
