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
import static javolution.testing.TestContext.assertTrue;
import static javolution.testing.TestContext.test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import javolution.testing.TestCase;

/**
 * <p>
 * Instantiation of the generic tests of the {@link AbstractFloatTestSuite} for {@link LargeInteger} and some further
 * tests that are specific to {@link LargeInteger}.
 * </p>
 * <p>
 * We do not test the trivial methods plus(long), minus(long). FIXME: Missing test für Karazuba
 * </p>
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class LargeIntegerTestSuite extends AbstractIntegerTestSuite<LargeInteger> {

    private final Random rnd = new Random();

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
        values.add(Pair.make(Double.valueOf(Integer.MIN_VALUE), _helper.valueOf(Integer.MIN_VALUE)));
        values.add(Pair.make(Double.valueOf(Integer.MAX_VALUE), _helper.valueOf(Integer.MAX_VALUE)));
        values.add(Pair.make(Double.valueOf(Long.MIN_VALUE), _helper.valueOf(Long.MIN_VALUE)));
        values.add(Pair.make(Double.valueOf(Long.MAX_VALUE), _helper.valueOf(Long.MAX_VALUE)));
    }

    protected void testConstants() {
        info(" constants");
        test(new TestCase() {
            @Override
            public void execute() {
                assertEquals(LargeInteger.valueOf(1), LargeInteger.ONE);
                assertEquals(LargeInteger.valueOf(0), LargeInteger.ZERO);
                assertEquals(LargeInteger.valueOf(5), LargeInteger.FIVE);
                assertEquals(LargeInteger.valueOf(Long.MIN_VALUE), LargeInteger.LONG_MIN_VALUE);
            }
        });
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
                        assertEquals("hexadecimal (" + radix + ") " + p + " : " + val, p._y, LargeInteger.valueOf(val,
                                radix));
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

    protected void testCompareToLong() {
        info(" compareToLong");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            for (final Pair<Double, LargeInteger> q : getTestValues()) {
                final long ql = q._x.longValue();
                test(new TestCase() {
                    @Override
                    public void execute() {
                        final Double qd = new Double(ql);
                        int expected = p._x.compareTo(qd);
                        int res = p._y.compareTo(ql);
                        assertEquals(p + "," + q, expected, res);
                    }
                });
            }
        }
    }

    protected void testEqualsLong() {
        info(" equalsLong");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            for (final Pair<Double, LargeInteger> q : getTestValues()) {
                test(new TestCase() {
                    @Override
                    public void execute() {
                        boolean expected = p._x.equals(new Double(q._x.longValue()));
                        boolean res = p._y.equals(q._x.longValue());
                        assertEquals(p + "," + q, expected, res);
                    }
                });
            }
        }
    }

    protected void testTimesLong() {
        info(" timesLong");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            for (final Pair<Double, LargeInteger> q : getTestValues()) {
                final long ql = q._x.longValue();
                test(new AbstractNumberTest<LargeInteger>("Testing timesLong " + p + "," + ql, ql * p._x, _helper) {
                    @Override
                    LargeInteger operation() throws Exception {
                        return p._y.times(ql);
                    }
                });
            }
        }
    }

    protected void testDivideLong() {
        info(" divideInt");
        for (final Pair<Double, LargeInteger> p : getTestValues()) {
            for (final Pair<Double, LargeInteger> q : getTestValues()) {
                final int qi = q._x.intValue();
                if (0 != qi) {
                    test(new AbstractNumberTest<LargeInteger>("Testing divideInt " + p + "," + qi, p._x, _helper) {
                        @Override
                        LargeInteger operation() throws Exception {
                            return p._y.times(qi).divide(qi);
                        }
                    });
                }
            }
        }
    }

    /** This is a probabilistic test - it micht fail very rarely */
    protected void testGCD() {
        info(" gcd");
        test(new TestCase() {
            @Override
            public void execute() {
                for (int i = 0; i < 10; ++i) {
                    BigInteger bi1 = makePrime(133);
                    BigInteger bi2 = makePrime(95);
                    BigInteger bi3 = makePrime(52);
                    final LargeInteger f = _helper.valueOf(bi3);
                    assertEquals(bi1 + "\n" + bi2 + "\n" + bi3, f, _helper.valueOf(bi1).times(f).gcd(
                            _helper.valueOf(bi2).times(f)));
                }
            }
        });
    }

    private BigInteger makePrime(int bits) {
        BigInteger res;
        do {
            res = new BigInteger(bits, rnd).nextProbablePrime();
        } while (!res.isProbablePrime(20));
        return res;
    }
    
}
