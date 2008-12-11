package org.jscience.mathematics.number.test;

import org.jscience.mathematics.number.LargeInteger;

import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.testing.TestContext.*;

public class LargeIntegerTestSuite extends TestSuite {
    
    protected final NumberHelper<LargeInteger> helper = NumberHelper.LARGEINTEGER;

    @Override
    public void run() {
        TestContext.info("Test Suite for LargeInteger");
        TestContext.test(zeroTest);
    }
    
    public static void main(String[] args) {
        new LargeIntegerTestSuite().run();
    }

    private TestCase zeroTest = new TestCase() {

        @Override
        public void execute() {
            try {
                LargeInteger val = helper.getOne();
                System.out.println(val);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
    };

}
