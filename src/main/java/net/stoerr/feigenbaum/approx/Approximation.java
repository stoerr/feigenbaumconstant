package net.stoerr.feigenbaum.approx;

import java.util.ArrayList;
import java.util.List;

import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;
import net.stoerr.feigenbaum.util.F;

/**
 * @author hps
 * @since 15.11.2008
 */
public class Approximation<T extends Field<T>> {

    public final NumHelper<T> h;

    public final BernsteinPolynomials<T> pol;

    private final int num;

    public Approximation(int n, NumHelper<T> h) {
        this.h = h;
        this.pol = new BernsteinPolynomials<T>(n, h);
        num = pol.count();
    }

    /** Make a decent initial approximation based on f */
    public DenseVector<T> initialApproximation(F<T, T> f) {
        List<DenseVector<T>> rows = new ArrayList<DenseVector<T>>();
        List<T> values = new ArrayList<T>();
        for (int i = 0; i < num; ++i) {
            T x = h.v(i * 1.0 / num);
            rows.add(pol.polynomials(x));
            values.add(f.call(x));
        }
        DenseVector<T> vals = DenseVector.valueOf(values);
        DenseMatrix<T> matrix = DenseMatrix.valueOf(rows);
        DenseVector<T> res = (DenseVector<T>) matrix.solve(vals);
        return res;
    }

    public Vector<T> improveApproximation(Vector<T> g) {
        List<DenseVector<T>> rows = new ArrayList<DenseVector<T>>();
        List<T> values = new ArrayList<T>();
        FeigenbaumFunction f = new FeigenbaumFunction();
        f.g = g;
        for (int i = 0; i < num; ++i) {
            T x = h.v(i * 1.0 / num);
            values.add(f.offset(g, x));
            rows.add(f.variation(x));
        }
        DenseVector<T> vals = DenseVector.valueOf(values);
        DenseMatrix<T> matrix = DenseMatrix.valueOf(rows);
        Vector<T> res = matrix.solve(vals);
        System.out.println(vals);
        System.out.println(matrix.times(res));
        return g.plus(res.opposite());
    }
    
    public T evaluateApproximation(Vector<T> g) {
        T res = h.zero();
        FeigenbaumFunction f = new FeigenbaumFunction();
        f.g = g;
        for (int i = 0; i < num; ++i) {
            T x = h.v(i * 1.0 / num);
            T off = f.offset(g, x);
            res = res.plus(h.abs(off)); 
        }
        return res.times(h.v(num).inverse());
    }

    class FeigenbaumFunction {

        final T one = h.one();

        public Vector<T> g;

        public T g(T x) {
            return pol.value(g, x);
        }

        public T gd(T x) {
            return pol.diffvalue(g, x);
        }

        public T offset(Vector<T> d, T x) {
            final T g1 = g(one);
            final T gxmg1 = g(x).times(g1);
            final T xg1 = x.times(g1);
            return g(g(xg1)).plus(gxmg1.opposite());
        }

        public DenseVector<T> variation(T x) {
            final T g1 = g(one);
            DenseVector<T> res = pol.polynomials(x).times(g1);
            T xg1 = x.times(g1);
            T gdgxg1 = gd(g(xg1)); // g'(g(x*g(1)))
            DenseVector<T> dxg1 = pol.difpolynomials(xg1);
            res = res.plus(dxg1.times(gdgxg1).opposite());
            DenseVector<T> d1 = pol.polynomials(one);
            T gx = g(x);
            T gdxg1 = gd(xg1); // g'(x*g(1))
            res = res.plus(d1.times(gx));
            res = res.plus(d1.times(gdxg1.times(gdgxg1)).opposite());
            return res;
        }

    }

}
