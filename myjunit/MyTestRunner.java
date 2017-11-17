import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
    private static int failureNum = 0;
    private static int errorNum = 0;
    private static int current = 0;
    private static Map<String, Object> errorMap = new HashMap<>();
    private static Map<String, Object> failureMap = new HashMap<>();

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
        System.out.println();
        System.out.println("run with time " + runTime);
        System.out.println("run with nanoTime " + runNanoTime);
    }

    /**
     * 如果每个测试方法都绑定到一当前TestClass上
     * @param clazzName
     */
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
//            e.printStackTrace();
        } finally {
            printRecord();
        }
    }

    /**
     * 打印内容
     * failure和error的输出格式相同  如何设计  根据type判断不好
     * 对单双数目的单词形态  通过if来判断吗
     */
    public static void printRecord() {
        System.out.println();
        if (errorNum > 0) {
            System.out.println("There were " + errorNum + " errors:");
            printRecordDetail(1);
        }

        if (failureNum > 0){
            System.out.println("There were " + failureNum + " failures:");
            printRecordDetail(2);
        }

        if (errorNum > 0 || failureNum > 0) {
            System.out.println("FAILURES!!!");
            System.out.println("Tests run: "+ (current+1) +",  Failures: "+ failureNum + ",  Errors: "+ errorNum);
        }
    }

    private static void printRecordDetail(int type){
        Set<Map.Entry<String,Object>> entries = null;
        if (type == 1) {
            entries = errorMap.entrySet();
        } else {
            entries = failureMap.entrySet();
        }

        int i = 0;
        for (Map.Entry<String, Object> stringObjectEntry : entries) {
            Throwable throwable = (Throwable) stringObjectEntry.getValue();
            System.out.println(++i + ") " + stringObjectEntry.getKey() + "  "+ throwable.getCause());
        }
        System.out.println();
    }


    /**
     * 这里要保证能记录每个测试方法的运行时异常   并且不影响下一个方法的执行
     * @param object
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void invokeMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        try {
            doInvokes(object);
        } catch (InvocationTargetException et) {
            Throwable throwable = et.getCause();
            if (throwable instanceof AssertFailedError) {
                failureNum++;
                failureMap.put(methodList.get(current).getName(), et);
            } else {
                errorNum++;
                errorMap.put(methodList.get(current).getName(), et);
            }
            if (current != methodList.size()-1) {//测试方法没有执行完   继续执行下一个
                current++;
                invokeMethods(object);
            }
        } catch (IllegalAccessException ea) {
            throw ea;
        }
    }

    private static void doInvokes(Object object) throws InvocationTargetException, IllegalAccessException {
        for (int i = current; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            method.invoke(object);
            current++;
        }
    }

    /**
     * TODO: 私有方法的判断 归为failure
     */

}
