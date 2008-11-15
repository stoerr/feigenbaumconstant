package net.stoerr.feigenbaum.basic;

import java.util.Collections;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.util.Func;

public class BernsteinPolynomials<T extends Field<T>> {

    public final Vector<T> factors;

    public final NumHelper<T> conv;

    public final int n;

    public BernsteinPolynomials(final int n, final NumHelper<T> conv) {
        this.conv = conv;
        this.n = n;
        factors = JScienceUtils.makeVector(n + 1, new Func<Integer, T>() {

            public T call(Integer... arg) {
                return conv.v(JScienceUtils.over(n, arg[0]));
            }
        });
    }

    public T nth(T x, int m) {
        if (m < 0 || m > n)
            return conv.zero();
        T val = factors.get(m);
        val = val.times(conv.pow(x, m)).times(
                conv.pow(conv.one().plus(x.opposite()), n - m));
        return val;
    }

}
