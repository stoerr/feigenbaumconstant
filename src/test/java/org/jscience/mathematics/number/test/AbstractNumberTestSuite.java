package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;

import java.util.ArrayList;
import java.util.List;

import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import org.jscience.mathematics.number.Number;

import static javolution.testing.TestContext.*;

public abstract class AbstractNumberTestSuite<T extends Number<T>> extends TestSuite {

    public static final double eps = 1e-7;

    final NumberHelper<T> _helper;
    final Class _numberClass;
    List<Pair<Double, T>> _testvalues;

    AbstractNumberTestSuite(NumberHelper<T> helper) {
        _helper = helper;
        _numberClass = helper.getNumberClass();
    }

    @Override
    public void run() {
        info(getClass().toString());
        makeTestValues();
        for (final Pair<Double, T> p : _testvalues) {
            test(new AbstractNumberTest<T>("Testing doubleValue ", p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return p._y;
                }
            });
        }
        for (final Pair<Double, T> p : _testvalues) {
            test(new AbstractNumberTest<T>("Testing toString / valueOf(String) ", p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return _helper.valueOf(p._y.toString());
                }
            });
        }
    }

    void makeTestValues() {
        _testvalues = new ArrayList<Pair<Double, T>>();
        _testvalues.add(Pair.make(0.0, _helper.getZero()));
        _testvalues.add(Pair.make(1.0, _helper.getOne()));
        for (double d : new double[] { 0.0, 1.0, 0.1, 0.9, -0.1, -0.9, 1.1, 1234, -9876 }) {
            info("Adding Testvalue " + d);
            _testvalues.add(Pair.make(d, _helper.valueOf(d)));
        }
    }

}
