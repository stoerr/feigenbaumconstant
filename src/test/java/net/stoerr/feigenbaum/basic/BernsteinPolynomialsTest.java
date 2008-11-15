package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.Real;

import junit.framework.TestCase;

public class BernsteinPolynomialsTest extends AbstractJScienceTest<Real> {
    
    public BernsteinPolynomialsTest() {
        super(NumHelper.REAL);
    }

    private BernsteinPolynomials<Real> b2;
    private BernsteinPolynomials<Real> b3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        b2 = new BernsteinPolynomials<Real>(2,h);
        b3 = new BernsteinPolynomials<Real>(3,h);
    }

    public void testNth() {
        near(b2.nth(h.zero(), 0),1);
        near(b2.nth(h.zero(), 1),0);
        near(b2.nth(h.zero(), 2),0);
        near(b2.nth(h.one(), 0),0);
        near(b2.nth(h.one(), 2),1);
        near(b2.nth(h.v(0.5),1),0.5);
    }

}
