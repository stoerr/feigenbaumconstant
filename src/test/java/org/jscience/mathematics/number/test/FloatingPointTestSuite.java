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
import static javolution.testing.TestContext.test;
import javolution.lang.MathLib;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.number.LargeInteger;

/**
 * Instantiation of the generic tests of the {@link AbstractFloatTestSuite} for {@link FloatingPoint} and some further
 * tests that are specific to {@link FloatingPoint}.
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class FloatingPointTestSuite extends AbstractFloatTestSuite<FloatingPoint> {

    /** Sets the needed helper class. */
    public FloatingPointTestSuite() {
        super(NumberHelper.FLOATINGPOINT);
    }

    /**
     * Calls all tests defined here. <br>
     * Attention: when subclassing do not forget to call super.run()!
     */
    @Override
    public void run() {
        super.run();
        testIsZero();
        testRound();
    }

    protected void testRound() {
        info("  round");
        for (final Pair<Double, FloatingPoint> p : getTestValues()) {
            test(new AbstractNumberTest<FloatingPoint>("Testing round " + p, MathLib.round(p._x), _helper) {
                @Override
                FloatingPoint operation() throws Exception {
                    final LargeInteger rounded = p._y.round();
                    return FloatingPoint.valueOf(rounded, 0);
                }
            });
        }
    }
}
