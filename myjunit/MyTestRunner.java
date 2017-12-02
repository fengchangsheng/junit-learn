
/**
 * 哪些方法可以执行  private static  public
 * 需要有构造函数
 * 方法前后的增强 运行时间  打印内容
 * 结果的统计
 * 运行时异常如何处理
 * 如何保证各个方法的执行互不影响
 */
public class MyTestRunner {

    private static MyResultPrint resultPrint;

    public static void main(String[] args) {
        String clazzName = args[0];
        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();

        MyTestResult testResult = doRun(clazzName);

        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        long runTime = endTime - startTime;
        long runNanoTime = endNanoTime - startNanoTime;
        resultPrint = new MyResultPrint(System.out);
        resultPrint.printResult(runTime, testResult);
    }

    public static MyTestResult doRun(String clazzName) {
        MyTestResult testResult = new MyTestResult();
        testResult.run(clazzName);
        return testResult;
    }

}
