package net.stoerr.feigenbaum.tests;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import static javolution.testing.TestContext.REGRESSION;
import static javolution.testing.TestContext.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javolution.testing.TestCase;

import org.jscience.mathematics.number.test.AllNumberTests;
import org.jscience.mathematics.number.test.FloatingPointTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JavolutionJUnit4Adapter {

    protected final TestCase test;

    public JavolutionJUnit4Adapter(TestCase testcase) {
        this.test = testcase;
    }
    
    @org.junit.Test
    public void executeTest() throws Exception {
        enter(REGRESSION);
        try {
            new javolution.testing.TestSuite() {

                @Override
                public void run() {
                    test(test);
                }

            }.run();
        } finally {
            exit();
        }
    }

    @Parameters
    public static Collection<TestCase[]> data() {
        AllNumberTests fp = new AllNumberTests();
        List<TestCase> tests = fp.getTestCases();
        Collection<TestCase[]> res = new ArrayList<TestCase[]>();
        for (TestCase t : tests) {
            res.add(new TestCase[] { t });
        }
        return res;
    }
}
