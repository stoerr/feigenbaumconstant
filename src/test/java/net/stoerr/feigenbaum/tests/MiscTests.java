package net.stoerr.feigenbaum.tests;

import org.jscience.mathematics.number.Real;

import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.JScienceUtils;
import net.stoerr.feigenbaum.basic.NumHelper;

import junit.framework.TestCase;

public class MiscTests extends AbstractJScienceTest<Real> {

    public MiscTests() {
        super(NumHelper.REAL);
    }

    public void testBernstein() {
        for (int i = 2; i < 200; i+=2) {
            double fact = Math.pow(0.5, i);
            for (int j = i / 2 + 1; j <= i; ++j) {
                fact *= j;
            }
            for (int j = 1; j <= i / 2; ++j) {
                fact /= j;
            }
            System.out.println(i + "\t" + fact);
        }
    }
    
    public void testOver() {
        assertEquals(6,JScienceUtils.over(4, 2).intValue());
    }
    
    public void testInverse() {
        System.out.println(h.v(15).inverse());
    }
    
    public void testPrecision() {
        Real n1 = h.v(1);
        Real n10 = h.v(1.0);
        Real n05 = h.v(0.5);
        Real n0 = h.v(0);
        Real n15 = n1.plus(n05);
        System.out.println(n15);
    }

}
