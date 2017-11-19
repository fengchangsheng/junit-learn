import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lucare.Feng on 2017/11/18.
 */
public class MyResultPrint {

    private PrintStream printStream;
    private MyTestResult testResult;

    public MyResultPrint(PrintStream printStream, MyTestResult testResult) {
        this.printStream = printStream;
        this.testResult = testResult;
    }

    public void printResult(long runTime){
        printTime(runTime);
        printError();
        printFailure();
        printFoot();
    }

    private void printTime(long runTime){
        printStream.println();
        printStream.println("run with time " + runTime);
//        printStream.println("run with nanoTime " + runNanoTime);
    }

    private void printError(){
        printStream.println();
        int errorNum = testResult.getErrorNum();
        if (errorNum > 0) {
            printStream.println("There were " + errorNum + " errors:");
            printRecordDetail(testResult.getErrorEntrys());
        }
    }

    private void printFailure() {
        int failureNum = testResult.getFailureNum();
        if (failureNum > 0){
            printStream.println("There were " + failureNum + " failures:");
            printRecordDetail(testResult.getFailureEntrys());
        }
    }

    private void printFoot(){
        int errorNum = testResult.getErrorNum();
        int failureNum = testResult.getFailureNum();
        int current = testResult.getCurrent();
        if (errorNum > 0 || failureNum > 0) {
            printStream.println("FAILURES!!!");
            printStream.println("Tests run: "+ (current+1) +",  Failures: "+ failureNum + ",  Errors: "+ errorNum);
        }
    }

    private void printRecordDetail(Set<Map.Entry<String, Object>> entries) {
        int i = 0;
        for (Map.Entry<String, Object> stringObjectEntry : entries) {
            Throwable throwable = (Throwable) stringObjectEntry.getValue();
            printStream.println(++i + ") " + stringObjectEntry.getKey() + "  " + throwable.getCause());
        }
        printStream.println();
    }

}
