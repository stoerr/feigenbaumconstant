package net.stoerr.feigenbaum.approx;

import static net.stoerr.feigenbaum.FeigenConstants.FREN;

import java.util.List;

import org.jscience.mathematics.vector.DenseVector;
import org.junit.Test;

import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.ApReal;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;

public class TestDerivableApproximation extends AbstractJScienceTest<ApReal> {

    private Approximation<ApReal> a;

    private int digitsold;

    public TestDerivableApproximation() {
        super(NumHelper.AP);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = new Approximation<ApReal>(new BernsteinPolynomials<ApReal>(10, h));
        digitsold = ApReal.getDigits();
        ApReal.setDigits(30);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ApReal.setDigits(digitsold);
    }

    @Test
    public void testImprove() {
        final DerivableApproximation<ApReal> derivap = new DerivableApproximation<ApReal>(a.pol);
        DenseVector<ApReal> g = a.initialApproximation(h.wrap(FREN));
        ApReal off = a.evaluateApproximation(g);
        List<ApReal> roots = derivap.getLegendreRoots();
        System.out.println(off);
        for (int i=0; i<10; ++i) {
            g = derivap.improve(g, roots);
            off = a.evaluateApproximation(g);
            System.out.println(off);
        }
        final ApReal alpha = h.sqrt(a.pol.value(g, h.one()).inverse());
        System.out.println(alpha);
    }

}
