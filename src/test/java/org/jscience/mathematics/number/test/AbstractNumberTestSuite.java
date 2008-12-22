package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;

import java.util.List;

import org.jscience.mathematics.number.Number;

import javolution.lang.MathLib;
import javolution.testing.TestCase;
import javolution.testing.TestSuite;

/**
 * Common tests for all {@link Number} classes.
 * @author hps
 * @param <T>
 */
public abstract class AbstractNumberTestSuite<T extends Number<T>> extends TestSuite {

    protected final NumberHelper<T> _helper;

    protected AbstractNumberTestSuite(NumberHelper<T> helper) {
        _helper = helper;
    }

    protected abstract List<Pair<Double, T>> getTestValues();

    @Override
    public void run() {
        info(getClass().toString());
        testToString();
        testDoubleValue();
        testPlus();
        testMinus();
        testTimes();
        testCompareTo();
        testOpposite();
        testEquals();
        testPow();
        testEquals();
        testIsLargerThan();
    }

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
            test(new AbstractNumberTest<T>("Testing longValue ", p._x, _helper) {
                @Override
                T operation() throws Exception {
                    return _helper.valueOf(p._y.longValue());
                }
            });
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

    protected void testPow() {
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

}
