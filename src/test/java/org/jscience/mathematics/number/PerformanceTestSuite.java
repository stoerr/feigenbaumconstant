package org.jscience.mathematics.number;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import javolution.context.LocalContext;
import javolution.lang.MathLib;
import javolution.testing.TestSuite;

import org.jscience.mathematics.number.FloatingPoint;

public class PerformanceTestSuite extends TestSuite {

    protected final NumberHelper<FloatingPoint> _helper = NumberHelper.FLOATINGPOINT;

    /** Executes all testsuites as regression tests. */
    public static void main(String[] args) {
        // enter(REGRESSION);
        enter(LoggingTestContext.LOGGINGTESTCONTEXT);
        try {
            new PerformanceTestSuite().run();
        } finally {
            exit();
        }
    }

    @Override
    public void run() {
        testGeometricRow();
    }

    private void testGeometricRow() {
        LocalContext.enter();
        try {
            FloatingPoint.setDigits(500);
            FloatingPoint sum = _helper.getZero();
            FloatingPoint val = _helper.getOne();
            final FloatingPoint f = _helper.valueOf(3).inverse();
            for (int i = 0; i < MathLib.log(10) * f.getDigits() / MathLib.log(3); ++i) {
                val = val.times(f);
                sum = sum.plus(val);
            }
            System.out.println(sum);
        } finally {
            LocalContext.exit();
        }
    }

}
