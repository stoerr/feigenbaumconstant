package net.stoerr.feigenbaum.tests;

import static javolution.testing.TestContext.*;

import java.util.List;

import javolution.testing.TestCase;

import org.jscience.mathematics.number.FloatingPointTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JavolutionJUnitAdapter extends TestSuite {

    public static Test suite() {
        FloatingPointTestSuite fp = new FloatingPointTestSuite();
        TestSuite suite = new TestSuite(fp.getClass().getName());
        List<TestCase> tests = fp.getTestCases();
        for (final TestCase t : tests) {
            Test test = new junit.framework.TestCase(t.toString()) {
                public void testJavolutionTest() {
                    enter(REGRESSION);
                    try {
                        new javolution.testing.TestSuite() {

                            @Override
                            public void run() {
                                test(t);
                            }

                        }.run();
                    } finally {
                        exit();
                    }
                }
            };
            suite.addTest(test);
        }
        return suite;
    }

}
