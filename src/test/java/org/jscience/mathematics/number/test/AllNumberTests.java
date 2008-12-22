package org.jscience.mathematics.number.test;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import static javolution.testing.TestContext.REGRESSION;
import javolution.context.LogContext;
import javolution.testing.TestSuite;

/**
 * Executes all testsuites for org.jscience.mathematics.number.
 * @author hps
 * @since 14.12.2008
 */
public class AllNumberTests extends TestSuite {

    /** Here all test suites of the package org.jscience.mathematics.number should be executed. */
    @Override
    public void run() {
        new Integer64TestSuite().run();
        new Float64TestSuite().run();
        new LargeIntegerTestSuite().run();
        new FloatingPointTestSuite().run();
        new RealTestSuite().run();
    }

    /** Executes all testsuites as regression tests. */
    public static void main(String[] args) {
        // enter(REGRESSION);
        enter(LoggingTestContext.LOGGINGTESTCONTEXT);
        try {
            new AllNumberTests().run();
        } finally {
            exit();
        }
    }

}
