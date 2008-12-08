package net.stoerr.feigenbaum.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

/**
 * (n+1) p(n+1) = (2*n+1)*x*p(n)-n*p(n-1)
 * @author hps
 * @since 17.11.2008
 * @param <T>
 */
public class LegendrePolynomials<T extends Field<T>> implements Basefunctions<T> {

    public final NumHelper<T> h;
    public final Variable<T> x;
    public final Polynomial<T> px;
    public final List<Polynomial<T>> pol;
    public final List<Polynomial<T>> dpol;

    public LegendrePolynomials(int n, NumHelper<T> h) {
        this.h = h;
        this.x = new Variable.Global<T>("x");
        this.px = Polynomial.valueOf(h.one(), x);
        T nc = h.one();
        int nci = 1;
        final T two = h.one().plus(h.one());
        final Polynomial<T> y = px.times(two).plus(h.one().opposite());
        Polynomial<T> pl = Polynomial.valueOf(h.one(), Term.valueOf(x, 0));
        Polynomial<T> p = px.times(two).plus(h.one().opposite());
        pol = new ArrayList<Polynomial<T>>();
        dpol = new ArrayList<Polynomial<T>>();
        pol.add(pl);
        dpol.add(pl.differentiate(x));
        pol.add(p);
        dpol.add(p.differentiate(x));
        while (nci++ <= n) {
            Polynomial<T> pn = pl.times(nc);
            final Polynomial<T> pb = p.times(y).times(two.times(nc).plus(h.one()));
            pn = pb.minus(pn);
            pn = pn.times(nc.plus(h.one()).inverse());
            pol.add(pn);
            dpol.add(pn.differentiate(x));
            pl = p;
            p = pn;
            nc = nc.plus(h.one());
        }
    }

    public List<T> roots(int n) {
        Polynomial<T> p = pol.get(n);
        Polynomial<T> dp = dpol.get(n);
        List<T> res = new ArrayList<T>();
        List<T> rest = new ArrayList<T>();
        T wl = h.v(1.4 / n / n);
        wl = taylorroot(wl, p, dp);
        T w = h.v(7.5 / n / n);
        w = taylorroot(w, p, dp);
        res.add(wl);
        rest.add(h.one().plus(wl.opposite()));
        res.add(w);
        rest.add(h.one().plus(w.opposite()));
        for (int i = 2; i < n / 2; ++i) {
            double dwn = 2 * Math.sqrt(h.d(w)) - Math.sqrt(h.d(wl));
            dwn = dwn * dwn;
            wl = w;
            w = h.v(dwn);
            w = taylorroot(w, p, dp);
            res.add(w);
            rest.add(h.one().plus(w.opposite()));
        }
        if (1 == n % 2) {
            res.add(h.one().plus(h.one()).inverse());
        }
        Collections.reverse(rest);
        res.addAll(rest);
        return res;
    }

    private T taylorroot(T w1, final Polynomial<T> p, final Polynomial<T> dp) {
        T w = w1;
        double off = Double.MAX_VALUE;
        while (true) {
            T y = p.evaluate(w);
            T dy = dp.evaluate(w);
            T wn = w.plus(y.times(dy.inverse()).opposite());
            double offn = Math.abs(h.d(y));
            if (offn >= off / 2) { return w; }
            w = wn;
            off = offn;
        }
    }

    public int count() {
        return pol.size();
    }

    public DenseVector<T> functionValues(T x) {
        List<T> res = new ArrayList<T>();
        for (Polynomial<T> p : pol) {
            if (0 == p.getVariables().size()) {
                res.add(p.evaluate());
            } else {
                res.add(p.evaluate(x));
            }
        }
        return DenseVector.valueOf(res);
    }

    public DenseVector<T> difValues(T x) {
        List<T> res = new ArrayList<T>();
        for (Polynomial<T> p : dpol) {
            if (0 == p.getVariables().size()) {
                res.add(p.evaluate());
            } else {
                res.add(p.evaluate(x));
            }
        }
        return DenseVector.valueOf(res);
    }

    public T nth(T x, int m) {
        return pol.get(m).evaluate(x);
    }

    public T value(Vector<T> coeff, T x) {
        return coeff.times(functionValues(x));
    }

    public T diffvalue(Vector<T> coeff, T x) {
        return coeff.times(difValues(x));
    }

    public NumHelper<T> getHelper() {
        return h;
    }

}
