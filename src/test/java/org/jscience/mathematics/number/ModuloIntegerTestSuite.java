package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;
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

    protected void testConstants() {
        info(" constants");
        test(new TestCase() {
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
