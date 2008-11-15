/**
 * 
 */
package net.stoerr.feigenbaum.basic;

import java.util.ArrayList;
import java.util.List;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.util.F;

public interface NumHelper<T extends Field<T>> {
    T v(LargeInteger l);

    T v(double d);

    T v(long l);

    T zero();

    T one();

    T pow(T val, int exp);

    double d(T val);

    Vector<T> makeVector(double[] ds);

    F<T, T> wrap(F<Double, Double> f);

    F<Double, Double> unwrap(F<T, T> f);

    abstract class AbstractNumhelper<T extends Field<T>> implements
            NumHelper<T> {
        public Vector<T> makeVector(double[] ds) {
            List<T> l = new ArrayList<T>();
            for (int i = 0; i < ds.length; ++i) {
                l.add(v(ds[i]));
            }
            return DenseVector.valueOf(l);
        }

        public F<T, T> wrap(final F<Double, Double> f) {
            return new F<T, T>() {
                public T call(T arg) {
                    return v(f.call(d(arg)));
                }
            };
        }

        public F<Double, Double> unwrap(final F<T, T> f) {
            return new F<Double, Double>() {
                public Double call(Double arg) {
                    return d(f.call(v(arg)));
                }
            };
        }

    }

    NumHelper<Real> REAL = new AbstractNumhelper<Real>() {

        public Real v(LargeInteger l) {
            return Real.valueOf(l, 0, 0);
        }

        public Real one() {
            return Real.ONE;
        }

        public Real zero() {
            return Real.ZERO;
        }

        public Real pow(Real val, int exp) {
            if (0 == exp)
                return one();
            return val.pow(exp);
        }

        public double d(Real val) {
            return val.doubleValue();
        }

        public Real v(double d) {
            if (0 <= d)
                return Real.valueOf(d);
            else
                return Real.valueOf(-d).opposite();
        }

        public Real v(long l) {
            return Real.valueOf(l);
        }

    };

}