package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;

import java.util.ArrayList;
import java.util.List;

import javolution.lang.MathLib;
import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import org.jscience.mathematics.number.Number;

import static javolution.testing.TestContext.*;

/**
 * Common tests for all floating point {@link Number} classes.
 * @author hps
 * @since 22.12.2008
 * @param <T>
 */
public abstract class AbstractFloatTestSuite<T extends Number<T>> extends AbstractNumberTestSuite<T> {

    public static final double eps = 1e-7;

    AbstractFloatTestSuite(NumberHelper<T> helper) {
        super(helper);
    }

    @Override
    protected void initTestValues(List<Pair<Double, T>> values) {
        values.add(Pair.make(0.0, _helper.getZero()));
        values.add(Pair.make(1.0, _helper.getOne()));
        values.add(Pair.make(0.0, _helper.valueOf(1234.9384).minus(_helper.valueOf(1234.9384))));
        for (double d : new double[] { 0.0, 1.0, 0.1, 0.9, -0.1, -0.9, 1.1, 1234.5678, -1234.5678, -9876.5432 }) {
            values.add(Pair.make(d, _helper.valueOf(d)));
        }
    }

    /**
     * Calls all tests defined here. <br>
     * Attention: when subclassing do not forget to call super.run()!
     */
    @Override
    public void run() {
        super.run();
        testInverse();
        testSqrt();
    }

    protected void testInverse() {
        info("  inverse");
        for (final Pair<Double, T> p : getTestValues()) {
            if (0 != p._x) {
                test(new AbstractNumberTest<T>("Testing inverse " + p, 1.0 / p._x, _helper) {
                    @Override
                    T operation() throws Exception {
                        return _helper.invokeMethod("inverse", p._y);
                    }
                });
            }
        }
    }

    protected void testSqrt() {
        info("  sqrt");
        for (final Pair<Double, T> p : getTestValues()) {
            if (0 < p._x) { // FIXME Another Bug: 0 should work but does not.
                test(new AbstractNumberTest<T>("Testing sqrt " + p, MathLib.sqrt(p._x), _helper) {
                    @Override
                    T operation() throws Exception {
                        return _helper.invokeMethod("sqrt", p._y);
                    }
                });
            }
        }
    }

}
