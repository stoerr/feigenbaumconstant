package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;

import java.util.Arrays;
import java.util.List;

import javolution.testing.TestCase;

/**
 * Testsuite for {@link Complex}. It inherits many tests from {@link AbstractFloatTestSuite} that test the operations
 * for {@link Complex} numbers with zero real value - these are basically simple smoketests. <br>
 * TODO: more tests are needed.
 * @author hps
 * @since 02.02.2009
 */
public class ComplexTestSuite extends AbstractFloatTestSuite<Complex> {

    protected static final double EPSILON = 1e-17;
    private static final Complex[] TESTVALUES = new Complex[] { Complex.valueOf(3.2, 1.7), Complex.valueOf(-3.72, 2.8),
            Complex.valueOf(-95.3, -4.2), Complex.valueOf(0.3, -4.2), Complex.valueOf(0, 1.7), Complex.valueOf(0, -5),
            Complex.valueOf(3, 0), Complex.valueOf(-42, 0) };

    ComplexTestSuite() {
        super(NumberHelper.COMPLEX);
    }

    @Override
    protected void testAbs() {
        // Complex has no abs, which is a bug in itself. 8-)
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

    protected List<Complex> getComplexTestvalues() {
        return Arrays.asList(TESTVALUES);
    }

    protected void testEquals2() {
        info("  equals2");
        for (final Complex c : getComplexTestvalues()) {
            for (final Complex d : getComplexTestvalues()) {
                doTest(new TestCase() {
                    @Override
                    public void execute() {
                        if (c == d) {
                            assertTrue(c.toString() + ", " + d.toString(), c.equals(d, EPSILON));
                        } else {
                            assertTrue(c.toString() + ", " + d.toString(), !c.equals(d, EPSILON));
                        }
                    }
                });
            }
        }
    }

    protected void testToString2() {
        info("  toString2 ");
        for (final Complex c : getComplexTestvalues()) {
            doTest(new TestCase() {
                @Override
                public void execute() {
                    Complex cn = Complex.valueOf(c.toString());
                    assertTrue(c.toString(), cn.equals(c, EPSILON));
                }
            });
        }
    }

    /**
     * We execute quite a lot of operations in one go and compare with the known result. Not the best test, but quick to
     * write.
     */
    protected void testOperationChain() {
        info("  operationChain");
        doTest(new TestCase() {
            @Override
            public void execute() {
                // fixme
            }
        });
    }
}
