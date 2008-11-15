package net.stoerr.feigenbaum.approx;

import static org.junit.Assert.*;

import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.Vector;
import org.junit.Test;

import net.stoerr.feigenbaum.FeigenConstants;
import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.NumHelper;

public class ApproximationTest extends AbstractJScienceTest<Real> {

    private Approximation<Real> a;

    public ApproximationTest() {
        super(NumHelper.REAL);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = new Approximation<Real>(9, h);
    }

    @Test
    public void testInitialApproximation() {
        Real.setExactPrecision(30);
        final Vector<Real> init = a.initialApproximation(h.wrap(FeigenConstants.FREN));
        System.out
                .println(init);
    }

}
