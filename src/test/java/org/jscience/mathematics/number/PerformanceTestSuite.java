package org.jscience.mathematics.number;

import net.stoerr.common.test.Timing;
import net.stoerr.common.test.Timing.Timed;
import javolution.context.LocalContext;
import javolution.lang.MathLib;
import javolution.testing.TestSuite;

public class PerformanceTestSuite extends TestSuite {

    protected final NumberHelper<FloatingPoint> _helper = NumberHelper.FLOATINGPOINT;

    /**
     * Executes all testsuites as regression tests.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // enter(REGRESSION);
        /*
         * enter(LoggingTestContext.LOGGINGTESTCONTEXT); try { new PerformanceTestSuite().run(); } finally { exit(); }
         */
        new PerformanceTestSuite().run();
    }

    @Override
    public void run() {
        // testGeometricRow();
        // testDecimal();
        // testBinary();
        // testStuff();
        for (int i = 0; i < 10; ++i) {
            System.out.println("binary: " + Timing.timingNS(new Timed() {
                @Override
                public Object run(int runs) {
                    return testBinary(runs);
                }
            }));
            System.out.println("binary: " + Timing.timingNS(new Timed() {
                @Override
                public Object run(int runs) {
                    return testBinary(runs);
                }
            }));
            System.out.println("decimal: " + Timing.timingNS(new Timed() {
                @Override
                public Object run(int runs) {
                    return testDecimal(runs);
                }
            }));
        }
    }

    private void testGeometricRow() {
        LocalContext.enter();
        try {
            FloatingPoint.setDigits(1000);
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

    final int size = 5000;

    protected Object testDecimal(final int rounds) {
        Long begin = System.nanoTime();
        LargeInteger ten = LargeInteger.valueOf(11);
        LargeInteger l1 = ten;
        for (int i = 0; i < size; ++i)
            l1 = l1.times(ten);
        for (int i = 0; i < rounds; ++i)
            l1.times(l1);
        // System.out.println(l1.times(l1));
        // System.out.println("LargeInteger: " + 1e-9 * (System.nanoTime() - begin));
        return l1;
    }

    protected Object testBinary(final int rounds) {
        Long begin = System.nanoTime();
        DecimalInteger ten = DecimalInteger.valueOf(11);
        DecimalInteger l1 = ten;
        for (int i = 0; i < size; ++i)
            l1 = l1.times(ten);
        for (int i = 0; i < rounds; ++i)
            l1.times(l1);
        // System.out.println(l1.times(l1));
        // System.out.println("DecimalInteger: " + 1e-9 * (System.nanoTime() - begin));
        return l1;
    }

    protected void testStuff() {
        Long begin = System.nanoTime();
        long a = 4399224589879L;
        long b = 434232443L;
        long c;
        final int stuffrounds = 10000000;
        for (int i = 0; i < stuffrounds; ++i) {
            c = a % b;
            // c = a + b;
        }
        final long tim = System.nanoTime() - begin;
        System.out.println("Stuff (ns) " + tim * 1.0 / stuffrounds);
        System.out.println("Gesamt (s) " + 1e-9 * tim);

    }

}
