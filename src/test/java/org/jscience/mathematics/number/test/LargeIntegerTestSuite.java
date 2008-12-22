package org.jscience.mathematics.number.test;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.number.LargeInteger;

import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.testing.TestContext.*;

public class LargeIntegerTestSuite extends AbstractNumberTestSuite<LargeInteger> {
    
    public LargeIntegerTestSuite() {
        super(NumberHelper.LARGEINTEGER);
    }

    @Override
    public void run() {
        super.run();
        info("Test Suite for LargeInteger");
        test(zeroTest);
        test(bitLengthTest);
        test(digitLengthTest);
    }

    public static void main(String[] args) {
        enter(REGRESSION);
        try {
            new LargeIntegerTestSuite().run();
        } finally {
            exit();
        }
    }

    private TestCase zeroTest = new TestCase() {

        @Override
        public void execute() {
            try {
                LargeInteger val = _helper.getOne();
                System.out.println(val);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    };

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
