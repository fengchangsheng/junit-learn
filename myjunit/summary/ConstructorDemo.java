package summary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by fengcs on 2017/11/11.
 * point : 不确定构造函数种类的情况下使用反射创建对象
 * Class.newInstance() 只能够调用无参的构造函数，即默认的构造函数
 * Constructor.newInstance() 可以根据传入的参数，调用任意构造构造函数
 */
public class ConstructorDemo {

    public ConstructorDemo(String a){
        System.out.println("one String param ...");
    }

    public void doSomething(){
        System.out.println("do......something");
    }

    public static Constructor getConstructor(Class clazz) throws NoSuchMethodException {
        try {
            Constructor constructor = clazz.getConstructor(new Class[]{String.class});
            return constructor;
        } catch (NoSuchMethodException e) {
            //ignore
        }
        return clazz.getConstructor(new Class[0]);
    }

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("summary.ConstructorDemo");
            Constructor constructor = getConstructor(clazz);
            Object object;
            if (constructor.getParameterTypes().length == 0) {
                object = constructor.newInstance(new Object[0]);
            }else {
                String myName = "lucare.Feng";
                object = constructor.newInstance(myName);
            }
            // 如果这个类集成在框架中  可以直接使用下面这个方法创建对象
            // 但是作为测试类(DemoTest extends TestCase)  我们只能借鉴junit的方法
//            object = clazz.newInstance();
            if (object instanceof ConstructorDemo) {
                ConstructorDemo constructorDemo = (ConstructorDemo) object;
                constructorDemo.doSomething();
            }
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
        }
    }
}
