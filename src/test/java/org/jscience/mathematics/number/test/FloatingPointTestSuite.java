package org.jscience.mathematics.number.test;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jscience.mathematics.number.FloatingPoint;

import javolution.context.LogContext;
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

}
