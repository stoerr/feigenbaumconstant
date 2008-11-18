package net.stoerr.feigenbaum.basic;

import static org.junit.Assert.*;

import java.util.List;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.number.FloatingPoint;
import org.junit.After;
import org.junit.Before;

public class TestLegendrePolynomials extends AbstractJScienceTest<FloatingPoint> {

    public TestLegendrePolynomials() {
        super(NumHelper.FP);
    }

    private LegendrePolynomials<FloatingPoint> l;

    @Override
    @Before
    public void setUp() throws Exception {
        l = new LegendrePolynomials<FloatingPoint>(h,10);
    }

    public void testPol() {
        for (Polynomial<FloatingPoint> p : l.pol) {
            // System.out.println(p);
        }
    }
    
    public void testRoots() {
        final List<FloatingPoint> r9 = l.roots(9);
        // System.out.println(r9);
        assertEquals(9,r9.size());
        final List<FloatingPoint> r10 = l.roots(10);
        // System.out.println(r10);
        assertEquals(10,r10.size());
    }

}
