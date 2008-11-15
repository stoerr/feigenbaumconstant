package net.stoerr.feigenbaum.basic;

import static org.junit.Assert.*;

import org.jscience.mathematics.number.Real;
import org.junit.Test;

public class NumHelperTest extends AbstractJScienceTest<Real> {
    
    public NumHelperTest() {
        super(NumHelper.REAL);
    }

    @Test
    public void testV() {
        for (double x : new double[] {0.523, 17.3, -42, 0.00001}) {
            near(h.d(h.v(x)),x);            
        }
    }

}
