package net.stoerr.feigenbaum.approx;

import java.util.ArrayList;
import java.util.List;

import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.approx.Approximation.FeigenbaumFunction;
import net.stoerr.feigenbaum.approx.DerivableFunction.Result;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;

public class DerivableApproximation<T extends Field<T>> {
    
    private final BernsteinPolynomials<T> pol;
    private final NumHelper<T> h;

    public DerivableApproximation(BernsteinPolynomials<T> pol) {
        this.pol = pol;
        this.h = pol.h;
    }

    public DerivableFunction<T> feigbaumgl() {
        DerivableFunction<T> one = DerivableFunction.makeConstant(h.one(), h);
        DerivableFunction<T> f = DerivableFunction.makeBernstein(pol);
        DerivableFunction<T> f1 = f.compose(one);
        DerivableFunction<T> x = DerivableFunction.makeIdentity(h);
        DerivableFunction<T> f1fx = f1.times(f.compose(x));
        DerivableFunction<T> ffxf1 = f.compose(f.compose(x.times(f1)));
        DerivableFunction<T> gl = ffxf1.add(f1fx.times(h.one().opposite()));
        return gl;
    }

    public DenseVector<T> improve(DenseVector<T> a) {
        List<DenseVector<T>> rows = new ArrayList<DenseVector<T>>();
        List<T> values = new ArrayList<T>();
        final int num =a.getDimension();
        DerivableFunction<T> gl = feigbaumgl();
        for (int i = 0; i < num; ++i) {
            T x = h.v(i * 1.0 / num);
            Result<T> result = gl.call(x, a);
            values.add(result.y);
            rows.add(result.da);
        }
        DenseVector<T> vals = DenseVector.valueOf(values);
        DenseMatrix<T> matrix = DenseMatrix.valueOf(rows);
        Vector<T> res = matrix.solve(vals);
        // System.out.println(vals);
        // System.out.println(matrix.times(res));
        return a.plus(res.opposite());
    }

}
