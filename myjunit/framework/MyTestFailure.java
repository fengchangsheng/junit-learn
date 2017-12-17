package framework;

import java.io.*;

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

    public String trace() {
        StringWriter stringWriter= new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        getThrowable().printStackTrace(printWriter);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

    public String getFilteredTrace() {
        String exceptionTrace = trace();
        StringReader stringReader = new StringReader(exceptionTrace);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!checkLine(line)) {
                    printWriter.println(line);
                }
            }
        } catch (Exception e) {
            return exceptionTrace;
        }
        return stringWriter.toString();

    }

    private boolean checkLine(String line) {
        if (line.contains("framework.")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
