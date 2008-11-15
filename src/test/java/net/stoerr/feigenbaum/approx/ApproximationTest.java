package net.stoerr.feigenbaum.approx;

import static org.junit.Assert.*;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.vector.Vector;
import org.junit.Test;

import net.stoerr.feigenbaum.FeigenConstants;
import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.NumHelper;

public class ApproximationTest extends AbstractJScienceTest<FloatingPoint> {

    private Approximation<FloatingPoint> a;

    public ApproximationTest() {
        super(NumHelper.REAL);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = new Approximation<FloatingPoint>(9, h);
    }

    @Test
    public void testInitialApproximation() {
        FloatingPoint.setDigits(30);
        final Vector<FloatingPoint> init = a.initialApproximation(h.wrap(FeigenConstants.FREN));
        System.out
                .println(init);
        for (int i=0; i<=10; ++i) {
            FloatingPoint x = h.v(i*0.1);
        }
    }

}
