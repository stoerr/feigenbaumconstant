package org.jscience.mathematics.number.test;

import javolution.testing.AssertionException;
import javolution.testing.TestCase;
import javolution.testing.TestContext;

public class LoggingTestContext extends TestContext {
    
    public static final Class<LoggingTestContext> LOGGINGTESTCONTEXT = LoggingTestContext.class; 

    // Overrides.
    public void doTest(TestCase testCase) {
        testCase.prepare();
        try {
            testCase.execute();
            testCase.validate();
        } finally {
            testCase.cleanup();
        }
    }

    // Overrides.
    public boolean doAssertEquals(String message, Object expected, Object actual) {
        if (((expected == null) && (actual != null))
                || ((expected != null) && (!expected.equals(actual)))) 
            throw new AssertionException(message, expected, actual);  
        return true;
    }

    public boolean isInfoLogged() {
        return true;
    }

    public void logInfo(CharSequence message) {
        System.out.print("[info] ");
        System.out.println(message);
    }

    public boolean isWarningLogged() {
        return true;
    }

    public void logWarning(CharSequence message) {
        System.out.print("[warning] ");
        System.out.println(message);
    }

    public boolean isErrorLogged() {
        return true;
    }

    public void logError(Throwable error, CharSequence message) {
        System.out.print("[error] ");
        if (error != null) {
            System.out.print(error.getClass().getName());
            System.out.print(" - ");
        }
        String description = (message != null) ? message.toString()
                : (error != null) ? error.getMessage() : "";
        System.out.println(description);
        if (error != null) {
            error.printStackTrace();
        }
    }

}
