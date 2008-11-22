/**
 * 
 */
package net.stoerr.feigenbaum.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.FloatingPoint;
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

    T abs(T val);

    double d(T val);

    DenseVector<T> makeVector(double[] ds);

    DenseVector<T> vectorZero(int dimension);

    DenseVector<T> vectorUnity(int dimension, int k);

    F<T, T> wrap(F<Double, Double> f);

    F<Double, Double> unwrap(F<T, T> f);
    
    Comparator<T> comparator();
    
    T sqrt(T x);

    abstract class AbstractNumhelper<T extends Field<T>> implements NumHelper<T> {
        public DenseVector<T> makeVector(double[] ds) {
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

        public DenseVector<T> vectorZero(int dimension) {
            return DenseVector.valueOf(Collections.nCopies(dimension, zero()));
        }

        public DenseVector<T> vectorUnity(int dimension, int k) {
            List<T> l = new ArrayList<T>(Collections.nCopies(dimension, zero()));
            l.set(k, one());
            return DenseVector.valueOf(l);
        }

        public T sqrt(T x) {
            double dx = d(x);
            T w = v(Math.sqrt(dx));
            double off = Double.MAX_VALUE;
            final T half = one().plus(one()).inverse();
            while (true) {
                T wn = x.times(w.inverse()).plus(w).times(half);
                double offn = Math.abs(d(wn.plus(w.opposite())));
                if (offn >= 0.7*off) {
                    return w;
                }
                off = offn;
            }
        }
    }

    NumHelper<FloatingPoint> FP = new AbstractNumhelper<FloatingPoint>() {

        public FloatingPoint v(LargeInteger l) {
            return FloatingPoint.valueOf(l, 0);
        }

        public FloatingPoint one() {
            return FloatingPoint.ONE;
        }

        public FloatingPoint zero() {
            return FloatingPoint.ZERO;
        }

        public FloatingPoint pow(FloatingPoint val, int exp) {
            if (0 == exp) return one();
            return val.pow(exp);
        }

        public double d(FloatingPoint val) {
            return val.doubleValue();
        }

        public FloatingPoint v(double d) {
            if (0 <= d) return FloatingPoint.valueOf(d);
            else return FloatingPoint.valueOf(-d).opposite();
        }

        public FloatingPoint v(long l) {
            return FloatingPoint.valueOf(l);
        }

        public FloatingPoint abs(FloatingPoint val) {
            return val.abs();
        }

        public Comparator<FloatingPoint> comparator() {
            return new Comparator<FloatingPoint>() {
                public int compare(FloatingPoint arg0, FloatingPoint arg1) {
                    return arg0.compareTo(arg1);
                }
            };
        }
    };

    NumHelper<ApReal> AP = new AbstractNumhelper<ApReal>() {

        public ApReal v(LargeInteger l) {
            throw new UnsupportedOperationException();
        }

        public ApReal one() {
            return ApReal.one();
        }

        public ApReal zero() {
            return ApReal.zero();
        }

        public ApReal pow(ApReal val, int exp) {
            if (0 == exp) return one();
            return val.pow(exp);
        }

        public double d(ApReal val) {
            return val.doubleValue();
        }

        public ApReal v(double d) {
            if (0 <= d) return ApReal.valueOf(d);
            else return ApReal.valueOf(-d).opposite();
        }

        public ApReal v(long l) {
            return ApReal.valueOf(l);
        }

        public ApReal abs(ApReal val) {
            return val.abs();
        }

        public Comparator<ApReal> comparator() {
            return new Comparator<ApReal>() {
                public int compare(ApReal arg0, ApReal arg1) {
                    return arg0.compareTo(arg1);
                }
            };
        }
    };
}
