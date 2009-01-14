/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;

import java.util.List;

import javolution.testing.TestCase;

import org.jscience.mathematics.number.LargeInteger;

/**
 * <p>
 * Instantiation of the generic tests of the {@link AbstractFloatTestSuite} for {@link LargeInteger} and some further
 * tests that are specific to {@link LargeInteger}.
 * </p>
 * <p>
 * TODO: Test the special values LargeInteger.FIVE, LargeInteger.LONG_MIN_VALUE, LargeInteger.ONE, LargeInteger.ZERO
 * etc. but we are in the wrong package. 8-}
 * </p>
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class LargeIntegerTestSuite extends AbstractIntegerTestSuite<LargeInteger> {

    /** Sets the {@link NumberHelper}. */
    public LargeIntegerTestSuite() {
        super(NumberHelper.LARGEINTEGER);
    }

    /**
     * Extends by some large test values out of the general integer range.
     * @see org.jscience.mathematics.number.AbstractIntegerTestSuite#initTestValues(java.util.List)
     */
    @Override
    protected void initTestValues(List<Pair<Double, LargeInteger>> values) {
        super.initTestValues(values);
        for (String s : new String[] { "9876543212345678985432123456789876543210",
                "-9876543212345678985432123456789876543210" }) {
            values.add(Pair.make(Double.valueOf(s), _helper.valueOf(s)));
        }
    }

    protected void testDigitLength() {
        test(new TestCase() {
            @Override
            public void execute() {
                assertEquals(1, LargeInteger.ZERO.digitLength());
                assertEquals(1, LargeInteger.ONE.digitLength());
                long val = 10;
                int len = 2;
                while (val < Long.MAX_VALUE / 10) {
                    LargeInteger l = LargeInteger.valueOf(val);
                    assertEquals(l.toString(), len, l.digitLength());
                    assertEquals(l.toString(), len, l.plus(LargeInteger.ONE).digitLength());
                    assertEquals(l.toString(), len - 1, l.plus(LargeInteger.ONE.opposite()).digitLength());
                    val *= 10;
                    len++;
                }
            }
        });
    }

    protected void testBitLength() {
        info("  bitLength");
        test(new TestCase() {
            @Override
            public void execute() {
                assertEquals(0, LargeInteger.ZERO.bitLength());
                assertEquals(1, LargeInteger.ONE.bitLength());
                long val = 2;
                int len = 2;
                while (val < Long.MAX_VALUE / 2) {
                    LargeInteger l = LargeInteger.valueOf(val);
                    assertEquals(l.toString(), len, l.bitLength());
                    assertEquals(l.toString(), len, l.plus(_helper.getOne()).bitLength());
                    assertEquals(l.toString(), len - 1, l.plus(_helper.getOne().opposite()).bitLength());
                    val *= 2;
                    len++;
                }
            }
        });
    }

}
