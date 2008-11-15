package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.structure.Field;

import junit.framework.TestCase;

public abstract class AbstractJScienceTest<T extends Field<T>> extends TestCase {
    
    final NumHelper<T> h;
    
    public AbstractJScienceTest(NumHelper<T> h) {
        this.h = h;
    }
    
    void near(T val, double d) {
        assertEquals(String.valueOf(val), h.d(val), d, 1e-8);
    }
    
}
