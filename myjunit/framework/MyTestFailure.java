package framework;

/**
 * Created by fengcs on 2017/12/6.
 * 由相同结构的Map演化为 List<MyTestFailure>
 */
public class MyTestFailure {

    private String methodName;
    private Throwable throwable;

    public MyTestFailure(String methodName, Throwable throwable) {
        this.methodName = methodName;
        this.throwable = throwable;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
