package net.stoerr.feigenbaum.basic;

import static org.junit.Assert.*;

import org.jscience.mathematics.number.FloatingPoint;
import org.junit.Test;

public class NumHelperTest extends AbstractJScienceTest<FloatingPoint> {
    
    public NumHelperTest() {
        super(NumHelper.FP);
    }

    @Test
    public void testV() {
        for (double x : new double[] {0.523, 17.3, -42, 0.00001}) {
            near(h.d(h.v(x)),x);            
        }
    }

}
