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

    public Approximation(int n, NumHelper<T> h) {
        this.h = h;
        this.pol = new BernsteinPolynomials<T>(n, h);
    }
    
    /** Make a decent initial approximation based on f */
    public Vector<T> initialApproximation(F<T, T> f) {
        final int num = pol.count();
        List<DenseVector<T>> rows = new ArrayList<DenseVector<T>>();
        List<T> values = new ArrayList<T>();
        for (int i=0; i<num; ++i) {
            T x = h.v(i*1.0/num);
            rows.add(pol.polynomials(x));
            values.add(f.call(x));
        }
        DenseVector<T> vals = DenseVector.valueOf(values);
        DenseMatrix<T> matrix = DenseMatrix.valueOf(rows);
        Vector<T> res = matrix.solve(vals);
        return res;
    }
    
    
    
}
