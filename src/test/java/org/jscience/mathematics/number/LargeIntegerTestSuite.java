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

import java.math.BigInteger;
import java.util.List;

import javolution.testing.TestCase;

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

    /** Tests that the hashCode is equal to mod(1327144033). */
    protected void testHashcode2() {
        info(" hashCode2");
        final long p = 1327144033;
        final LargeInteger pi = LargeInteger.valueOf(p);
        test(new TestCase() {
            @Override
            public void execute() {
                assertEquals("-1", LargeInteger.valueOf(-1), LargeInteger.valueOf(LargeInteger.valueOf(-1).hashCode()));
                final LargeInteger two = LargeInteger.valueOf(2);
                LargeInteger v = LargeInteger.ONE;
                long expectedHash = 1;
                for (int i = 1; i < 99; ++i) {
                    final int hashCode = v.hashCode();
                    assertEquals("" + i, v.mod(pi), LargeInteger.valueOf(hashCode));
                    v = v.times(two);
                    expectedHash = (expectedHash * 2) % p;
                }
                LargeInteger l = LargeInteger.valueOf("8478329283923484839827239782987591381");
                assertEquals(l.mod(pi), LargeInteger.valueOf(l.hashCode()));
            }
        });
    }

    protected void testDigitLength() {
        info(" digitLength");
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

    protected void testHexadecimal() {
        info(" hexadecimal");
        test(new TestCase() {
            @Override
            public void execute() {
                assertEquals("6a8af7ae5a6759aa49fa43b8b4cd49cf655e41795ba270e613a557", LargeInteger.valueOf(
                        "43829182938374882394282398298374848392872392839238754323223782743").toText(16).toString());
                assertEquals("43829182938374882394282398298374848392872392839238754323223782743", LargeInteger.valueOf(
                        "6a8af7ae5a6759aa49fa43b8b4cd49cf655e41795ba270e613a557", 16).toString());
            }
        });
        for (final int radix : new int[] { 2, 10, 16, 36 }) {
            for (final Pair<Double, LargeInteger> p : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        String val = p._y.toText(radix).toString();
                        assertEquals("hexadecimal " + p, p._y, LargeInteger.valueOf(val, radix));
                    }
                });
            }
        }
    }

    protected void testBigInteger() {
        info(" biginteger");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            test(new TestCase() {
                @Override
                public void execute() {
                    String val = p._y.toString();
                    final BigInteger bi = new BigInteger(val);
                    assertEquals("" + p, p._y, LargeInteger.valueOf(bi));
                }
            });
        }
    }

    protected void testToByteArray() {
        info(" toByteArray");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            test(new TestCase() {
                @Override
                public void execute() {
                    byte[] buf = new byte[1000]; // large enough for everything.
                    for (int i = 0; i < buf.length; ++i)
                        buf[i] = 42; // must not matter
                    final int offset = 16;
                    int num = p._y.toByteArray(buf, offset);
                    assertEquals("" + p, p._y, LargeInteger.valueOf(buf, offset, num));
                }
            });
        }
    }

}
