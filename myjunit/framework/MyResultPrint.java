package framework;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lucare.Feng on 2017/11/18.
 */
public class MyResultPrint {

    private PrintStream printStream;

    public MyResultPrint(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printResult(long runTime, MyTestResult testResult){
        printTime(runTime);
        printError(testResult);
        printFailure(testResult);
        printFoot(testResult);
    }

    private void printTime(long runTime){
        printStream.println();
        printStream.println("Time: " + runTime);
//        printStream.println("run with nanoTime " + runNanoTime);
    }

    private void printError(MyTestResult testResult){
        printRecordDetail(testResult.getErrorList(), testResult.getErrorNum(), "error");
    }

    private void printFailure(MyTestResult testResult) {
        printRecordDetail(testResult.getFailureList(), testResult.getFailureNum(),  "failure");
    }

    private void printFoot(MyTestResult testResult){
        int errorNum = testResult.getErrorNum();
        int failureNum = testResult.getFailureNum();
        int current = testResult.getCurrent();
        if (testResult.isSuccessful()) {
            printStream.print("OK");
            printStream.println(" (" + current + " test" + (current == 1 ? "": "s") + ")");
        } else {
            printStream.println("FAILURES!!!");
            printStream.println("Tests run: " + current + ",  Failures: " + failureNum + ",  Errors: " + errorNum);
        }
    }

    private void printRecordDetail(List<MyTestFailure> testFailures, int count, String type){
        if (count == 0) {
            return;
        }
        if (count == 1) {
            printStream.println("There was " + count + " "+ type + ":");
        } else {
            printStream.println("There were " + count + " "+ type + "s:");
        }

        int i = 0;
        for (MyTestFailure myTestFailure : testFailures) {
            printStream.println(++i + ") " + myTestFailure.getMethodName() + "  " + myTestFailure.getThrowable());
            printStream.println(myTestFailure.getFilteredTrace());
        }
        printStream.println();
    }

    @Deprecated
    private void printRecordDetail(Set<Map.Entry<String, Object>> entries) {
        int i = 0;
        for (Map.Entry<String, Object> stringObjectEntry : entries) {
            Throwable throwable = (Throwable) stringObjectEntry.getValue();
            // 这里将异常归为两类  AssertFailedError 和 Throwable    两种类型getCause()都为null
            // 不再是之前的InvocationTargetException类型的可以 getCause (getTargetException)
            printStream.println(++i + ") " + stringObjectEntry.getKey() + "  " + throwable);
        }
        printStream.println();
    }

}
