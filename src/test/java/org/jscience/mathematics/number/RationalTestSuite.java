package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.test;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javolution.lang.MathLib;
import javolution.testing.TestCase;

public class RationalTestSuite extends AbstractNumberTestSuite<Rational> {

    protected RationalTestSuite() {
        super(NumberHelper.RATIONAL);
    }

    @Override
    protected void initTestValues(List<Pair<Double, Rational>> values) {
        values.add(Pair.make(0.0, _helper.getZero()));
        values.add(Pair.make(1.0, _helper.getOne()));
        for (double d : new double[] { 0.0, 1.0, 43234, -9382 }) {
            values.add(Pair.make(d, _helper.valueOf(MathLib.round(d))));
        }
        for (long numerator : new long[] { 0, 1, 3, 7, 67, 35 * 67 }) {
            for (long denominator : new long[] { 1, 3, 67, 23 * 67 }) {
                values.add(Pair.make(numerator * 1.0 / denominator, Rational.valueOf(numerator, denominator)));
                values.add(Pair.make(-numerator * 1.0 / denominator, Rational.valueOf(-numerator, denominator)));
            }
        }
    }

    protected void testRound() {
        info(" round");
        for (final Pair<Double, Rational> p : getTestValues()) {
            test(new AbstractNumberTest<Rational>("Testing round " + p, MathLib.round(p._x), _helper) {
                @Override
                Rational operation() throws Exception {
                    return Rational.valueOf(p._y.round(), LargeInteger.ONE);
                }
            });
        }
    }

    protected void testTimesLong() {
        info(" timeslong");
        for (final Pair<Double, Rational> p : getTestValues()) {
            for (final Pair<Double, Rational> q : getTestValues()) {
                final long ql = q._y.getDividend().longValue();
                test(new AbstractNumberTest<Rational>("Testing round " + p + ", " + ql, p._x * ql, _helper) {
                    @Override
                    Rational operation() throws Exception {
                        return p._y.times(ql);
                    }
                });
            }
        }
    }

    protected void testValueOfNoDiv() {
        info(" valueOfNoDiv");
        for (final Pair<Double, Rational> p : getTestValues()) {
            final long v = p._y.getDividend().longValue();
            test(new AbstractNumberTest<Rational>("Testing[ valueOfNoDiv " + v, v, _helper) {
                @Override
                Rational operation() throws Exception {
                    return Rational.valueOf(v + "");
                }
            });
        }
    }

    protected void testNormalization() {
        info(" normalization");
        test(new TestCase() {
            @Override
            public void execute() {
                Rational norm = Rational.valueOf(123 * 43423, 839 * 43423);
                assertEquals(" normalize " + norm, 123, norm.getDividend().longValue());
                assertEquals(" normalize " + norm, 839, norm.getDivisor().longValue());
            }
        });
    }
}
