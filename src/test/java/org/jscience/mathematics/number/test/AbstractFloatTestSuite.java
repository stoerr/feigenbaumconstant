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

    List<Pair<Double, T>> _testValues;

    @Override
    protected List<Pair<Double, T>> getTestValues() {
        if (null == _testValues) {
            _testValues = new ArrayList<Pair<Double, T>>();
            _testValues.add(Pair.make(0.0, _helper.getZero()));
            _testValues.add(Pair.make(1.0, _helper.getOne()));
            for (double d : new double[] { 0.0, 1.0, 0.1, 0.9, -0.1, -0.9, 1.1, 1234.5678, -1234.5678, -9876.5432 }) {
                _testValues.add(Pair.make(d, _helper.valueOf(d)));
            }
        }
        return _testValues;
    }

    @Override
    public void run() {
        super.run();
    }

}
