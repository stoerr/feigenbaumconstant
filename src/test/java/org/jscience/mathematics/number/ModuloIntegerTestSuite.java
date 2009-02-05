package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;
import javolution.context.LocalContext;
import javolution.testing.TestCase;

/**
 * Tests for {@link ModuloInteger}. <br>
 * The tests consist of some tests that do not set a modulus - this checks for obvious bugs - and some thests that do
 * really use the modulus. We override a couple of tests of our super classes since ModuloInteger does not have the
 * corresponding functions.
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
}
