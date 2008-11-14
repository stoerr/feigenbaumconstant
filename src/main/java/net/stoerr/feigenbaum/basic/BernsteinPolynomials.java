package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.structure.Field;

public class BernsteinPolynomials<T extends Field<T>> {

    private final LargeInteger[] factors;
    private Utils.Converter<T> conv;

    public BernsteinPolynomials(int n, Utils.Converter<T> conv) {
        factors = new LargeInteger[n];
        for (int i = 0; i < n; ++i) {
            factors[i] = Utils.over(n, i);
        }
        this.conv = conv;
    }
    
    public T nth(T x, int n) {
        return x.times(conv.value(factors[n]));
    }
    
}
