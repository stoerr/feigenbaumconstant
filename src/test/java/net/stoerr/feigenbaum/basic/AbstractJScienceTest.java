package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.Vector;

import junit.framework.TestCase;

public abstract class AbstractJScienceTest<T extends Field<T>> extends TestCase {

    public final NumHelper<T> h;

    public final double eps = 1e-6;

    public AbstractJScienceTest(NumHelper<T> h) {
        this.h = h;
    }

    protected void near(double val, double d) {
        assertEquals(val, d, eps);
    }

    protected void near(T val, double d) {
        assertEquals(h.d(val), d, eps);
    }

    protected void near(T val, T val2) {
        assertEquals(h.d(val), h.d(val2), eps);
    }

    protected void near(Vector<T> v, double[] ds) {
        String rep = String.valueOf(v);
        for (int i = 0; i < v.getDimension(); ++i) {
            assertEquals(i + ":" + rep, h.d(v.get(i)), ds[i], eps);
        }
    }
}
