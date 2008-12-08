package net.stoerr.feigenbaum.approx;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.vector.Vector;
import org.junit.Test;

import net.stoerr.feigenbaum.FeigenConstants;
import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.ApReal;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;
import net.stoerr.feigenbaum.util.F;

import static net.stoerr.feigenbaum.FeigenConstants.*;

public class ApproximationTest extends AbstractJScienceTest<FloatingPoint> {

    private Approximation<FloatingPoint> a;

    private int digitsold;

    public ApproximationTest() {
        super(NumHelper.FP);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = new Approximation<FloatingPoint>(new BernsteinPolynomials<FloatingPoint>(7, h));
        digitsold = FloatingPoint.getDigits();
        // FloatingPoint.setDigits(30);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        FloatingPoint.setDigits(digitsold);
    }

    public void testInitialApproximation() {
        double maxdif = checkInitialApprox(FeigenConstants.FREN);
        System.out.println("Maxdif FREN" + maxdif);
        assertTrue(maxdif < 1e-7);
        maxdif = checkInitialApprox(FeigenConstants.FAPPROX);
        System.out.println("Maxdif FAPPROX" + maxdif);
        assertTrue(maxdif < 1e-5);
    }

    private double checkInitialApprox(final F<Double, Double> f) {
        double maxdif = 0;
        final Vector<FloatingPoint> init = a.initialApproximation(h.wrap(f));
        System.out.println(init);
        for (int i = 0; i <= 10; ++i) {
            final double dx = i * 0.1;
            FloatingPoint x = h.v(dx);
            final double fv = f.call(dx);
            final FloatingPoint iv = a.pol.value(init, x);
            double dif = Math.abs(fv - h.d(iv));
            // System.out.println(dx + "\t" + fv + "\t" + dif + "\t" + iv);
            maxdif = Math.max(dif, maxdif);
        }
        return maxdif;
    }
    
    public void dtestImprovement() {
        Vector<FloatingPoint> g = a.initialApproximation(h.wrap(FREN));
        FloatingPoint off = a.evaluateApproximation(g);
        System.out.println(off);
        for (int i=0; i<10; ++i) {
            g = a.improveApproximation(g);
            off = a.evaluateApproximation(g);
            System.out.println(off);
            
        }
    }
}
