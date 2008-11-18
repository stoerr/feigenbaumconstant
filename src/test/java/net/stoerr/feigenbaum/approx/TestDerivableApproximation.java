package net.stoerr.feigenbaum.approx;

import static net.stoerr.feigenbaum.FeigenConstants.FREN;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.vector.DenseVector;
import org.junit.Test;

import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.NumHelper;

public class TestDerivableApproximation extends AbstractJScienceTest<FloatingPoint> {

    private Approximation<FloatingPoint> a;

    private int digitsold;

    public TestDerivableApproximation() {
        super(NumHelper.FP);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = new Approximation<FloatingPoint>(70, h);
        digitsold = FloatingPoint.getDigits();
        FloatingPoint.setDigits(150);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        FloatingPoint.setDigits(digitsold);
    }

    @Test
    public void testImprove() {
        final DerivableApproximation<FloatingPoint> derivap = new DerivableApproximation<FloatingPoint>(a.pol);
        DenseVector<FloatingPoint> g = a.initialApproximation(h.wrap(FREN));
        FloatingPoint off = a.evaluateApproximation(g);
        System.out.println(off);
        for (int i=0; i<10; ++i) {
            g = derivap.improveLegendre(g);
            off = a.evaluateApproximation(g);
            System.out.println(off);
        }
        final FloatingPoint alpha = h.sqrt(a.pol.value(g, h.one()).inverse());
        System.out.println(alpha);
        System.out.println(alpha.getSignificand());
    }

}
