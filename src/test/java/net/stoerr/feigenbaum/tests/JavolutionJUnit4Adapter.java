package net.stoerr.feigenbaum.tests;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import static javolution.testing.TestContext.REGRESSION;
import static javolution.testing.TestContext.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jscience.mathematics.number.test.AllNumberTests;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JavolutionJUnit4Adapter {

    protected final javolution.testing.TestCase test;

    public JavolutionJUnit4Adapter(javolution.testing.TestCase testcase) {
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
    public static Collection<javolution.testing.TestCase[]> data() {
        javolution.testing.TestSuite fp = new AllNumberTests();
        List<javolution.testing.TestCase> tests = fp.getTestCases();
        Collection<javolution.testing.TestCase[]> res = new ArrayList<javolution.testing.TestCase[]>();
        for (javolution.testing.TestCase t : tests) {
            res.add(new javolution.testing.TestCase[] { t });
        }
        return res;
    }
}
