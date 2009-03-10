package net.stoerr.feigenbaum.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.util.F;

public class BernsteinPolynomials<T extends Field<T>> implements Basefunctions<T> {

    public final DenseVector<T> factors;

    public final NumHelper<T> h;

    public final int n;

    public BernsteinPolynomials(final int n, final NumHelper<T> conv) {
        this.h = conv;
        this.n = n;
        factors = JScienceUtils.makeVector(n + 1, new F<Integer, T>() {

            public T call(Integer arg) {
                return JScienceUtils.over(n, arg, h);
            }
        });
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#nth(T, int)
     */
    public T nth(T x, int m) {
        if (m < 0 || m > n)
            return h.zero();
        T val = factors.get(m);
        val = val.times(h.pow(x, m)).times(
                h.pow(h.one().plus(x.opposite()), n - m));
        return val;
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#polynomials(T)
     */
    public DenseVector<T> functionValues(T x) {
        List<T> res = JScienceUtils.toList(factors);
        T v = h.one();
        for (int i = 1; i <= n; ++i) {
            v = v.times(x);
            res.set(i, res.get(i).times(v));
        }
        T nx = h.one().plus(x.opposite());
        v = h.one();
        for (int i = n - 1; i >= 0; --i) {
            v = v.times(nx);
            res.set(i, res.get(i).times(v));
        }
        return DenseVector.valueOf(res);
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#difpolynomials(T)
     */
    public DenseVector<T> difValues(T x) {
        final DenseVector<T> pol = functionValues(x);
        return JScienceUtils.makeVector(n + 1, new F<Integer, T>() {
            public T call(Integer m) {
                T val = h.v(2 * m - n).times(pol.get(m));
                if (m > 0) {
                    val = val.plus(h.v(n - m + 1).times(pol.get(m - 1)));
                }
                if (m < n) {
                    val = val.plus(h.v(-m - 1).times(pol.get(m + 1)));
                }
                return val;
            }
        });
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#value(org.jscience.mathematics.vector.Vector, T)
     */
    public T value(Vector<T> coeff, T x) {
        return coeff.times(functionValues(x));
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#diffvalue(org.jscience.mathematics.vector.Vector, T)
     */
    public T diffvalue(Vector<T> coeff, T x) {
        return coeff.times(difValues(x));
    }

    /** Extends the coefficients vector to a higher degree */
    public DenseVector<T> raiseCoeff(Vector<T> v) {
        final int n1 = v.getDimension() - 1;
        final int n2 = n;
        if (n1 >= n2)
            throw new IllegalArgumentException(n1 + ">=" + n2);
        List<T> l = JScienceUtils.toList(v);
        int n = n1;
        while (n < n2) {
            n = n + 1;
            final T inverse = h.v(n).inverse();
            List<T> l1 = new ArrayList<T>(Collections.nCopies(n + 1, h.zero()));
            for (int m = 0; m < n; ++m) {
                final T b = l.get(m);
                T b1 = l1.get(m);
                b1 = b1.plus(b.times(h.v(n - m)).times(inverse));
                l1.set(m, b1);
                T b2 = l1.get(m + 1);
                b2 = b2.plus(b.times(h.v(m + 1)).times(inverse));
                l1.set(m + 1, b2);
            }
            l = l1;
        }
        return DenseVector.valueOf(l);
    }

    /* (non-Javadoc)
     * @see net.stoerr.feigenbaum.basic.Basefunctions#count()
     */
    public int count() {
        return n+1;
    }

    public NumHelper<T> getHelper() {
        return h;
    }
}
