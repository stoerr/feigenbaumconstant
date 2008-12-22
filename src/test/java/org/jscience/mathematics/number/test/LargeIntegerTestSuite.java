package org.jscience.mathematics.number.test;

import java.util.List;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.number.LargeInteger;

import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.testing.TestContext.*;

public class LargeIntegerTestSuite extends AbstractIntegerTestSuite<LargeInteger> {

    public LargeIntegerTestSuite() {
        super(NumberHelper.LARGEINTEGER);
    }

    @Override
    protected void initTestValues(List<Pair<Double, LargeInteger>> values) {
        super.initTestValues(values);
        for (String s : new String[] { "9876543212345678985432123456789876543210",
                "-9876543212345678985432123456789876543210" }) {
            values.add(Pair.make(Double.valueOf(s), _helper.valueOf(s)));
        }
    }

    @Override
    public void run() {
        super.run();
        info("Test Suite for LargeInteger");
        test(bitLengthTest);
        test(digitLengthTest);
    }

    private TestCase bitLengthTest = new TestCase() {

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

    };

    private TestCase digitLengthTest = new TestCase() {

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

    };

}
