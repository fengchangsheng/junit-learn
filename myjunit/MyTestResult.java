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
    private int failureNum = 0;
    private int errorNum = 0;
    private int current = 0;
    private Map<String, Object> errorMap = new HashMap<>();
    private Map<String, Object> failureMap = new HashMap<>();

    public void run(String clazzName){
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
//            MyTestResult testResult = new MyTestResult();
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

    /**
     * 这里要保证能记录每个测试方法的运行时异常   并且不影响下一个方法的执行
     * @param object
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeMethods(Object object) throws InvocationTargetException, IllegalAccessException {
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
        return failureNum;
    }

    public void setFailureNum(int failureNum) {
        this.failureNum = failureNum;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
