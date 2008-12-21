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

public abstract class AbstractNumberTestSuite<T extends Number<T>> extends TestSuite {

    public static final double eps = 1e-7;

    final NumberHelper<T> _helper;
    final Class _numberClass;
    List<Pair<Double, T>> _testValues;

    AbstractNumberTestSuite(NumberHelper<T> helper) {
        _helper = helper;
        _numberClass = helper.getNumberClass();
    }

    @Override
    public void run() {
        info(getClass().toString());
        info("  doubleValue");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing doubleValue ", p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return p._y;
                }
            });
        }
        info("  toString, valueOf(String)");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing toString / valueOf(String) " + p, p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return _helper.valueOf(p._y.toString());
                }
            });
        }
        info("  plus");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new AbstractNumberTest<T>("Testing plus " + p._x + "," + q._x, p._x + q._x, _helper) {
                    @Override
                    T operation() throws Exception {
                        return p._y.plus(q._y);
                    }
                });
            }
        }
        info("  minus");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new AbstractNumberTest<T>("Testing minus " + p._x + "," + q._x, p._x - q._x, _helper) {
                    @Override
                    T operation() throws Exception {
                        return p._y.minus(q._y);
                    }
                });
            }
        }
        info("  times");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new AbstractNumberTest<T>("Testing times " + p._x + "," + q._x, p._x * q._x, _helper) {
                    @Override
                    T operation() throws Exception {
                        return p._y.times(q._y);
                    }
                });
            }
        }
        info("  compareTo");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        assertEquals(p + "," + q, p._x.compareTo(q._x), p._y.compareTo(q._y));
                    }
                });
            }
        }
        info("  opposite");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing opposite " + p, -p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return p._y.opposite();
                }
            });
        }
        testEquals();
        info("  pow");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final int exp : new Integer[] { 1, 3, 7, 8, 9 }) {
                test(new AbstractNumberTest<T>("Testing pow " + p + ", " + exp, MathLib.pow(p._x, exp), _helper) {
                    @Override
                    T operation() throws Exception {
                        return p._y.pow(exp);
                    }
                });
            }
        }
        testEquals();
    }

    protected void testEquals() {
        info("  equals");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        try {
                            assertEquals(p + "," + q, p._x.equals(q._x), p._y.equals(q._y));
                        } catch (Throwable t) {
                            boolean cmp = p._y.equals(q._y);
                            assertEquals(p + "," + q, p._x.equals(q._x), cmp);
                        }
                    }
                });
            }
        }
    }

    protected List<Pair<Double, T>> getTestValues() {
        if (null == _testValues) {
            _testValues = new ArrayList<Pair<Double, T>>();
            _testValues.add(Pair.make(0.0, _helper.getZero()));
            _testValues.add(Pair.make(1.0, _helper.getOne()));
            for (double d : new double[] { 0.0, 1.0, 0.1, 0.9, -0.1, -0.9, 1.1, 1234.5678, -9876.5432 }) {
                _testValues.add(Pair.make(d, _helper.valueOf(d)));
            }
        }
        return _testValues;
    }

}
