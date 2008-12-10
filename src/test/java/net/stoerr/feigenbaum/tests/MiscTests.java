package net.stoerr.feigenbaum.tests;

import org.jscience.mathematics.number.FloatingPoint;

import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.JScienceUtils;
import net.stoerr.feigenbaum.basic.NumHelper;

import junit.framework.TestCase;

public class MiscTests extends AbstractJScienceTest<FloatingPoint> {

    public MiscTests() {
        super(NumHelper.FP);
    }

    public void testBernstein() {
        for (int i = 2; i < 200; i += 2) {
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
        assertEquals(6, JScienceUtils.over(4, 2).intValue());
    }

    public void testInverse() {
        System.out.println(h.v(15).inverse());
    }

    public void testPrecision() {
        FloatingPoint n1 = h.v(1);
        FloatingPoint n10 = h.v(1.0);
        FloatingPoint n05 = h.v(0.5);
        FloatingPoint n0 = h.v(0);
        FloatingPoint n15 = n1.plus(n05);
        System.out.println(n15);
    }

    public void testFP() {
        FloatingPoint n1 = h.zero();
        FloatingPoint n2 = h.v(0.0001);
        FloatingPoint nm1 = n1.plus(n2.opposite());
        near(nm1, -0.0001);
    }

    /**
     * Problem: if (this._exponent > that._exponent) return that.plus(this); public FloatingPoint minus(FloatingPoint
     * that) {
     */
    public void testBug1() {
        FloatingPoint r = FloatingPoint.ZERO.minus(FloatingPoint.valueOf(0.1));
        near(r, 0.1);
    }

    public void testBug2() {
        final FloatingPoint val = FloatingPoint.valueOf(0.09);
        double d = val.doubleValue();
        assertEquals("0.899999999999999967",val.toString());
        // Das Problem ist durch LargeInteger.digitLength bedingt.
        // Noch ein Bug: compareTo mit 0.5
    }
    
    public void testBug3() {
        assertEquals(-1,new Double(0).compareTo(0.5));
        assertEquals(-1,FloatingPoint.ZERO.compareTo(FloatingPoint.valueOf(0.5)));
        assertEquals(1,new Double(1).compareTo(0.5));
        assertEquals(1,FloatingPoint.ONE.compareTo(FloatingPoint.valueOf(0.5)));
    }
    
}
