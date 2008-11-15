package net.stoerr.feigenbaum.tests;

import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.JScienceUtils;

import junit.framework.TestCase;

public class MiscTests extends TestCase {

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

}
