package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.test;

import java.util.ArrayList;
import java.util.List;

import org.jscience.mathematics.number.Number;

/**
 * Common tests for all integer {@link Number} classes.
 * @author hps
 * @since 22.12.2008
 * @param <T>
 */
public abstract class AbstractIntegerTestSuite<T extends Number<T>> extends AbstractNumberTestSuite<T> {

    AbstractIntegerTestSuite(NumberHelper<T> helper) {
        super(helper);
    }

    List<Pair<Double, T>> _testValues;

    @Override
    protected List<Pair<Double, T>> getTestValues() {
        if (null == _testValues) {
            _testValues = new ArrayList<Pair<Double, T>>();
            _testValues.add(Pair.make(0.0, _helper.getZero()));
            _testValues.add(Pair.make(1.0, _helper.getOne()));
            for (double d : new double[] { 0, 1, -1, 33, 12345678, -12345678, 87654321 }) {
                _testValues.add(Pair.make(d, _helper.valueOf(d)));
            }
        }
        return _testValues;
    }
    
}
