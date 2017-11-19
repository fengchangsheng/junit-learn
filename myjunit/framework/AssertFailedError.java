package framework;

/**
 * Created by fengcs on 2017/11/14.
 */
public class AssertFailedError extends Error{

    public AssertFailedError() {
    }

    public AssertFailedError(String message) {
        super(message);
    }
}
