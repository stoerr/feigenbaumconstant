package org.jscience.mathematics.number.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jscience.mathematics.number.FloatingPoint;

import javolution.context.LogContext;
import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

import static javolution.testing.TestContext.*;

public class FloatingPointTestSuite extends AbstractNumberTestSuite<FloatingPoint> {

    public FloatingPointTestSuite() {
        super(NumberHelper.FLOATINGPOINT);
    }

    @Override
    public void run() {
        info("Test Suite for FloatingPoint");
        super.run();
        test(zeroTest);
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

    private TestCase zeroTest = new TestCase() {

        @Override
        public void execute() {
            try {
                FloatingPoint val = _helper.getOne();
                System.out.println(val);
                val = _helper.valueOf(0.12);
                System.out.println(val);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    };

}
