package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;
import javolution.context.LocalContext;
import javolution.lang.MathLib;
import javolution.testing.TestCase;

/**
 * Tests for {@link ModuloInteger}. <br>
 * The tests consist of some tests that do not set a modulus - this checks for obvious bugs - and some thests that do
 * really use the modulus. We override a couple of tests of our super classes since ModuloInteger does not have the
 * corresponding functions. <br>
 * Problem: der Test kann ausgeführt werden, wenn der Modulus schon längst wieder zurückgesetzt wurde. 8-}
 * @author hps
 * @since 01.02.2009
 */
public class ModuloIntegerTestSuite extends AbstractIntegerTestSuite<ModuloInteger> {

    ModuloIntegerTestSuite() {
        super(NumberHelper.MODULOINTEGER);
    }

    /**
     * Calculates the expected value modulo {@link ModuloInteger#getModulus()}.
     * @see org.jscience.mathematics.number.AbstractNumberTestSuite#normalizeExpected(double)
     */
    @Override
    protected double normalizeExpected(double val) {
        LargeInteger mod = ModuloInteger.getModulus();
        if (null == mod) return val;
        double m = mod.longValue();
        final double res = ((val % m) + m) % m;
        return res;
    }

    @Override
    protected void doTest(TestCase t) {
        LocalContext.enter();
        super.doTest(t);
        try {
            ModuloInteger.setModulus(LargeInteger.valueOf(9719));
            super.doTest(t);
        } finally {
            LocalContext.exit();
        }
    }

    protected void testConstants() {
        info(" constants");
        doTest(new TestCase() {
            @Override
            public void execute() {
                assertEquals(ModuloInteger.valueOf(LargeInteger.valueOf(1)), ModuloInteger.ONE);
                assertEquals(ModuloInteger.valueOf(LargeInteger.valueOf(0)), ModuloInteger.ZERO);
            }
        });
    }

    @Override
    protected void testAbs() {
        // not implemented.
    }

    @Override
    protected void testDivide() {
        // not implemented.
    }

    @Override
    protected void testIsNegative() {
        // not implemented.
    }

    @Override
    protected void testIsPositive() {
        // not implemented.
    }

    @Override
    protected void testIsZero() {
        // not implemented.
    }

    @Override
    protected void testByteValue() {
        // omitted, since we have trouble with the modulus here.
    }

    @Override
    protected void testShortValue() {
        // omitted, since we have trouble with the modulus here.
    }

    @Override
    protected void testLongValue() {
        // omitted, since we have trouble with the modulus here.
    }

    @Override
    protected void testDoubleValue() {
        // omitted, since we have trouble with the modulus here.
    }

    @Override
    protected void testPow() {
        info("  pow");
        for (final Pair<Double, ModuloInteger> p : getTestValues()) {
            for (final int exp : new Integer[] { 1, 3, 7, 8, 9 }) {
                double pow = MathLib.pow(p._x, exp);
                if (null != ModuloInteger.getModulus()) {
                    double mod = ModuloInteger.getModulus().doubleValue();
                    pow = 1;
                    for (int i = 0; i < exp; ++i) {
                        pow = (pow * p._x) % mod;
                    }
                    pow = (pow + mod) % mod;
                }
                if (getMaxNumber() >= MathLib.abs(pow)) {
                    doTest(new AbstractNumberTest<ModuloInteger>("Testing pow " + p + ", " + exp, pow, _helper, this) {
                        @Override
                        ModuloInteger operation() throws Exception {
                            return p._y.pow(exp);
                        }
                    });
                }
            }
        }
    }
}
