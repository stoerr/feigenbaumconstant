package org.jscience.mathematics.number.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jscience.mathematics.number.FloatingPoint;

import javolution.context.LogContext;
import javolution.lang.MathLib;
import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.*;
import static org.junit.Assert.assertTrue;

public class FloatingPointTestSuite extends AbstractFloatTestSuite<FloatingPoint> {

    public FloatingPointTestSuite() {
        super(NumberHelper.FLOATINGPOINT);
    }

    @Override
    public void run() {
        super.run();
        // testRound();
    }

    public static void main(String[] args) {
        LogContext.enter(LogContext.CONSOLE);
        enter(REGRESSION);
        try {
            new FloatingPointTestSuite().run();
        } finally {
            exit();
        }
    }

    /** FIXME unused */
    protected void testRound() {
        info("  round");
        for (final Pair<Double, FloatingPoint> p : getTestValues()) {
            test(new AbstractNumberTest<FloatingPoint>("Testing round " + p, MathLib.round(p._x), _helper) {
                @Override
                FloatingPoint operation() throws Exception {
                    return FloatingPoint.valueOf(p._y.round(),0);
                }
            });
        }
    }
}
