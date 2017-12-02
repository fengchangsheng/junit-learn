import framework.AssertFailedError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Lucare.Feng on 2017/11/18.
 * 作为记录测试结果相关的类   与执行方法是分不开的
 */
public class MyTestResult {

    private List<Method> methodList = new ArrayList();
    private int current = 0;
    private Map<String, Object> errorMap = new HashMap<>();
    private Map<String, Object> failureMap = new HashMap<>();

    public void run(String clazzName){
        try {
            Class clazz = Class.forName(clazzName);
            Method[] methods = clazz.getMethods();
            Constructor constructor = getConstructor(clazz);
            MyTestCase object;
            if (constructor.getParameterTypes().length == 0) {
                object = (MyTestCase) constructor.newInstance(new Object[0]);
            } else {
                String myName = "lucare.Feng";
                object = (MyTestCase) constructor.newInstance(myName);
            }
            checkAndSaveMethods(methods);
//            MyTestResult testResult = new MyTestResult();
            // 改进前 暴力方法 靠的是反复调用
//            invokeMethods(object);

            invokeMethodNew(object);
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
        }
    }

    private Constructor getConstructor(Class clazz) throws NoSuchMethodException {
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
    private void checkAndSaveMethods(Method[] methods) {
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                methodList.add(method);
            }
        }
    }

    public void invokeMethodNew(MyTestCase object) {
        for (int i = 0; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            runAndRecord(object, method);
            current++;
        }
    }

    /**
     * 对failure和error的处理  不再向上抛出  可保证for循环继续执行
     */
    private void runAndRecord(MyTestCase object, Method method){
        try {
            runBase(object, method);
        } catch (AssertFailedError error) {
            failureMap.put(methodList.get(current).getName(), error);
        } catch (Throwable throwable) {
            errorMap.put(methodList.get(current).getName(), throwable);
        }
    }

    /**
     * 每个测试方法的调用骨架 (setUp -> testXX -> tearDown)
     */
    private void runBase(MyTestCase object, Method method) throws Throwable {
        object.setUp();
        try {
            invokeOne(object, method);
        } finally {
            object.tearDown();
        }
    }

    /**
     * 反射调用测试方法
     */
    private void invokeOne(MyTestCase object, Method method) throws Throwable{
        try {
            method.invoke(object);
        } catch (IllegalAccessException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    /**
     * 这里要保证能记录每个测试方法的运行时异常   并且不影响下一个方法的执行
     * @deprecated
     */
    public void invokeMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        try {
            doInvokes(object);
        } catch (InvocationTargetException et) {
            Throwable throwable = et.getCause();// et.getTargetException();
            if (throwable instanceof AssertFailedError) {
                failureMap.put(methodList.get(current).getName(), et);
            } else {
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

    @Deprecated
    private void doInvokes(Object object) throws InvocationTargetException, IllegalAccessException {
        for (int i = current; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            method.invoke(object);
            current++;
        }
    }

    public Set<Map.Entry<String, Object>> getErrorEntrys(){
        return errorMap.entrySet();
    }

    public Set<Map.Entry<String, Object>> getFailureEntrys(){
        return failureMap.entrySet();
    }

    public int getFailureNum() {
        return failureMap.size();
    }

    public int getErrorNum() {
        return errorMap.size();
    }

    public int getCurrent() {
        return current;
    }
}
