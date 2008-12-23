/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;
import static javolution.testing.TestContext.test;

import java.util.ArrayList;
import java.util.List;

import javolution.lang.MathLib;
import javolution.testing.TestCase;

import org.jscience.mathematics.number.Number;

/**
 * This class contains tests that can be run for all {@link Number} subclasses. The result of the {@link Number}
 * operation is compared with the result of the same operation on double values, which should give roughly the same
 * result. Of course, this does not check wether all digits are correct, but catches many errors already. You might want
 * to add tests for the full precision.
 * @since 22.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 * @param <T> the type of number to test
 */
public abstract class AbstractNumberTestSuite<T extends Number<T>> extends AbstractTestSuite {

    protected final NumberHelper<T> _helper;

    protected AbstractNumberTestSuite(NumberHelper<T> helper) {
        _helper = helper;
    }

    private List<Pair<Double, T>> _testValues;

    protected final List<Pair<Double, T>> getTestValues() {
        if (null == _testValues) {
            _testValues = new ArrayList<Pair<Double, T>>();
            initTestValues(_testValues);
        }
        return _testValues;

    }

    /** Generates a list of values to test with, along with their value as double. */
    protected abstract void initTestValues(List<Pair<Double, T>> values);

    protected void testToString() {
        info("  toString, valueOf(String)");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing toString / valueOf(String) " + p, p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return _helper.valueOf(p._y.toString());
                }
            });
        }
    }

    protected void testDoubleValue() {
        info("  doubleValue");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing doubleValue ", p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return p._y;
                }
            });
        }
    }

    protected void testLongValue() {
        info("  longValue");
        for (final Pair<Double, T> p : getTestValues()) {
            if (Math.abs(p._x) < Long.MAX_VALUE) {
                test(new AbstractNumberTest<T>("Testing longValue ", (long) p._x.doubleValue(), _helper) {
                    @Override
                    T operation() throws Exception {
                        return _helper.valueOf(p._y.longValue());
                    }
                });
            }
        }
    }

    protected void testPlus() {
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
    }

    protected void testMinus() {
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
    }

    protected void testTimes() {
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
    }

    protected void testCompareTo() {
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
    }

    protected void testOpposite() {
        info("  opposite");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing opposite " + p, -p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return p._y.opposite();
                }
            });
        }
    }

    /** The maximum admissible number; at most {@link Double#MAX_VALUE} - we cannot test for more here. */
    protected double getMaxNumber() {
        return Double.MAX_VALUE;
    }

    protected void testPow() {
        info("  pow");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final int exp : new Integer[] { 1, 3, 7, 8, 9 }) {
                final double pow = MathLib.pow(p._x, exp);
                if (getMaxNumber() >= MathLib.abs(pow)) {
                    test(new AbstractNumberTest<T>("Testing pow " + p + ", " + exp, pow, _helper) {
                        @Override
                        T operation() throws Exception {
                            return p._y.pow(exp);
                        }
                    });
                }
            }
        }
    }

    protected void testEquals() {
        info("  equals");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        assertEquals(p + "," + q, p._x.equals(q._x), p._y.equals(q._y));
                    }
                });
            }
        }
    }

    protected void testIsLargerThan() {
        info("  isLargerThan");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        assertEquals(p + "," + q, MathLib.abs(p._x) > MathLib.abs(q._x), p._y.isLargerThan(q._y));
                    }
                });
            }
        }
    }

    protected void testDivide() {
        info("  divide");
        for (final Pair<Double, T> p : getTestValues()) {
            for (final Pair<Double, T> q : getTestValues()) {
                if (0 != q._x) {
                    test(new AbstractNumberTest<T>("Testing divide " + p._x + "," + q._x, p._x / q._x, _helper) {
                        @Override
                        T operation() throws Exception {
                            return _helper.invokeMethod("divide", p._y, q._y);
                        }
                    });
                }
            }
        }
    }

    protected void testAbs() {
        info("  abs");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new AbstractNumberTest<T>("Testing abs " + p, MathLib.abs(p._x), _helper) {
                @Override
                T operation() throws Exception {
                    return _helper.invokeMethod("abs", p._y);
                }
            });
        }
    }

    protected void testIsPositive() {
        info("  isPositive");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new TestCase() {
                @Override
                public void execute() {
                    assertTrue(p.toString(), p._x > 0 == _helper.invokeBooleanMethod("isPositive", p._y));
                }
            });
        }
    }

    protected void testIsNegative() {
        info("  isNegative");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new TestCase() {
                @Override
                public void execute() {
                    assertTrue(p.toString(), p._x < 0 == _helper.invokeBooleanMethod("isNegative", p._y));
                }
            });
        }
    }

    protected void testIsZero() {
        info("  isZero");
        for (final Pair<Double, T> p : getTestValues()) {
            test(new TestCase() {
                @Override
                public void execute() {
                    assertTrue(p.toString(), (p._x == 0) == _helper.invokeBooleanMethod("isZero", p._y));
                }
            });
        }
    }
}
