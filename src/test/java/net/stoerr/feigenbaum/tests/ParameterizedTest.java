package net.stoerr.feigenbaum.tests;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import static javolution.testing.TestContext.REGRESSION;
import static javolution.testing.TestContext.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javolution.testing.TestCase;

import org.jscience.mathematics.number.AllNumberTests;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Let's try out Junit4 parameterized tests.
 * @author hps
 * @since 22.12.2008
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {

    private String name;

    public ParameterizedTest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + name;
    }

    @org.junit.Test
    public void executeTest() throws Exception {
        // nix.
    }

    @Parameters
    public static Collection<String[]> data() {
        List<String[]> res = new ArrayList<String[]>();
        res.add(new String[] { "thefirst" });
        res.add(new String[] { "thesecond" });
        return res;

    }

}
