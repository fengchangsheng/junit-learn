import java.io.PrintStream;
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
        printStream.println("run with time " + runTime);
//        printStream.println("run with nanoTime " + runNanoTime);
    }

    private void printError(MyTestResult testResult){
        printStream.println();
        int errorNum = testResult.getErrorNum();
        if (errorNum > 0) {
            printStream.println("There were " + errorNum + " errors:");
            printRecordDetail(testResult.getErrorEntrys());
        }
    }

    private void printFailure(MyTestResult testResult) {
        int failureNum = testResult.getFailureNum();
        if (failureNum > 0){
            printStream.println("There were " + failureNum + " failures:");
            printRecordDetail(testResult.getFailureEntrys());
        }
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
