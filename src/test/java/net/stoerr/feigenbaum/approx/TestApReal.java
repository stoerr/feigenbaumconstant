package net.stoerr.feigenbaum.approx;

import net.stoerr.feigenbaum.basic.ApReal;

import junit.framework.TestCase;

public class TestApReal extends TestCase {

    public void testLargerThan() {
        ApReal n1 = ApReal.valueOf(3.2423);
        ApReal n2 = ApReal.valueOf(-9.342);
        assertTrue(n1.isGreaterThan(n2));
        assertTrue(n2.isLargerThan(n1));
    }

    public void testOps() {
        ApReal n1 = ApReal.valueOf(3.12345678);
        ApReal n2 = ApReal.valueOf(-3.12345679);
        System.out.println(n1);
        System.out.println(n1.plus(n2));
        System.out.println(n1.plus(n2).minus(n2));
    }

    public void testPrecision() {
        ApReal n1 = ApReal.valueOf(1025.32);
        System.out.println(n1.rep.precision());
        System.out.println(n1.rep.radix());
    }

}
