package org.jscience.mathematics.number.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jscience.mathematics.number.FloatingPoint;

import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.testing.TestContext.*;

public class FloatingPointTestSuite extends TestSuite {
    
    protected final NumberHelper<FloatingPoint> helper = NumberHelper.FLOATINGPOINT;

    @Override
    public void run() {
        final Method[] m = FloatingPoint.class.getDeclaredMethods();
        System.out.println(Arrays.asList(m));
        TestContext.info("Test Suite for LargeInteger");
        TestContext.test(zeroTest);
    }
    
    public static void main(String[] args) {
        new FloatingPointTestSuite().run();
    }

    private TestCase zeroTest = new TestCase() {

        @Override
        public void execute() {
            try {
                FloatingPoint val = helper.getOne();
                System.out.println(val);
                val = helper.valueOf(0.12);
                System.out.println(val);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
    };

}
