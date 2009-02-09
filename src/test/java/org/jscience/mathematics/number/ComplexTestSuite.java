package org.jscience.mathematics.number;

/**
 * A simple testsuite for complex that tests the operations for {@link Complex} numbers with zero real value by
 * leveraging {@link AbstractFloatTestSuite}. More tests are needed.
 * @author hps
 * @since 02.02.2009
 */
public class ComplexTestSuite extends AbstractFloatTestSuite<Complex> {

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
}
