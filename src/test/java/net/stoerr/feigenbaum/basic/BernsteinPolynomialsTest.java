package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.FeigenConstants;
import net.stoerr.feigenbaum.util.F;

import static net.stoerr.feigenbaum.FeigenConstants.*;

import junit.framework.TestCase;

public class BernsteinPolynomialsTest extends AbstractJScienceTest<FloatingPoint> {

    public BernsteinPolynomialsTest() {
        super(NumHelper.REAL);
    }

    private BernsteinPolynomials<FloatingPoint> b2;
    private BernsteinPolynomials<FloatingPoint> b3;
    private BernsteinPolynomials<FloatingPoint> b5;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        b2 = new BernsteinPolynomials<FloatingPoint>(2, h);
        b3 = new BernsteinPolynomials<FloatingPoint>(3, h);
        b5 = new BernsteinPolynomials<FloatingPoint>(5, h);
    }

    public void testNth() {
        near(b2.nth(h.zero(), 0), 1);
        near(b2.nth(h.zero(), 1), 0);
        near(b2.nth(h.zero(), 2), 0);
        near(b2.nth(h.one(), 0), 0);
        near(b2.nth(h.one(), 2), 1);
        near(b2.nth(h.v(0.5), 1), 0.5);
    }

    public void testPolynomials() {
        near(b2.polynomials(h.v(0.5)), new double[] { 0.25, 0.5, 0.25 });
    }

    Vector<FloatingPoint> vec = h.makeVector(new double[] { 4.32, -3.52, 1.24, 0.12 });

    private double f(double x) {
        return h.d(b3.value(vec, h.v(x)));
    }

    private double fa(double x) {
        return h.d(b3.diffvalue(vec, h.v(x)));
    }

    public void testDif() {
        double x = 0.28;
        double e = 0.0000001;
        assertTrue(Math.abs(fa(x) - (f(x + e) - f(x)) / e) < 1e-5);
        final Vector<FloatingPoint> vec5 = b5.raiseCoeff(vec);
        near(b5.value(vec5, h.v(x)), f(x));
    }

    public void dtestPrintFunctions() {
        for (int i = 0; i <= 10; ++i) {
            double x = 0.1 * i;
            double gl = fapprox(x) * fapprox(1)
                    - fapprox(fapprox(x * fapprox(1)));
            System.out.println(x + "\t" + fapprox(x) + "\t" + gl);
            double gl1 = fren(x) * fren(1) - fren(fren(x * fren(1)));
            System.out.println(x + "\t" + fren(x) + "\t" + gl1);
        }
    }

    /** Try to get a first approximation. */
    public void testApproxStart() {
        final int n = 9;
        BernsteinPolynomials<FloatingPoint> b = new BernsteinPolynomials<FloatingPoint>(n, h);
        Vector<FloatingPoint> v = JScienceUtils.makeVector(n + 1,
                new F<Integer, FloatingPoint>() {
                    public FloatingPoint call(Integer arg) {
                        return h.v(fren(1.0 / (n + 1) * arg));
                    }
                });
        System.out.println(v);
        for (int i = 0; i <= 10; ++i) {
            double x = 0.1 * i;
            double ap = h.d(b.value(v, h.v(x)));
            System.out.println(x + "\t" + fren(x) + "\t" + (fren(x) - ap));
        }
    }

}
