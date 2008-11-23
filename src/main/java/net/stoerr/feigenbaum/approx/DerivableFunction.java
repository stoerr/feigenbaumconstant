package net.stoerr.feigenbaum.approx;

import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.basic.Basefunctions;
import net.stoerr.feigenbaum.basic.NumHelper;

/**
 * Base class for functions of x that depend on a set of parameters a, and calculate the derivations after x and the
 * members of a.
 * @author hps
 * @since 16.11.2008
 * @param <T>
 */
public abstract class DerivableFunction<T extends Field<T>> {

    /** The main method to calculate the result. */
    public abstract Result<T> call(T x, Vector<T> a);

    public static class Result<T extends Field<T>> {
        public final T y;

        public final T dy;

        public final DenseVector<T> da;

        public Result(T y, T dy, DenseVector<T> da) {
            this.y = y;
            this.dy = dy;
            this.da = da;
        }
    }

    public static <T extends Field<T>> DerivableFunction<T> makeConstant(final T val, final NumHelper<T> h) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                return new Result<T>(val, h.zero(), h.vectorZero(a.getDimension()));
            }

        };
    }

    public static <T extends Field<T>> DerivableFunction<T> makeIdentity(final NumHelper<T> h) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                return new Result<T>(x, h.one(), h.vectorZero(a.getDimension()));
            }

        };
    }

    public static <T extends Field<T>> DerivableFunction<T> makeBernstein(final Basefunctions<T> pol) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                return new Result<T>(pol.value(a, x), pol.diffvalue(a, x), pol.functionValues(x));
            }

        };
    }

    public DerivableFunction<T> add(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                Result<T> myresult = DerivableFunction.this.call(x, a);
                Result<T> otherresult = other.call(x, a);
                return new Result<T>(myresult.y.plus(otherresult.y), myresult.dy.plus(otherresult.dy),
                        myresult.da.plus(otherresult.da));
            }

        };
    }

    public DerivableFunction<T> times(final T factor) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                Result<T> myresult = DerivableFunction.this.call(x, a);
                return new Result<T>(myresult.y.times(factor), myresult.dy.times(factor), myresult.da.times(factor));
            }

        };
    }

    public DerivableFunction<T> times(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                Result<T> m = DerivableFunction.this.call(x, a);
                Result<T> o = other.call(x, a);
                return new Result<T>(m.y.times(o.y), m.dy.times(o.y).plus(m.y.times(o.dy)), m.da.times(o.y).plus(
                        o.da.times(m.y)));
            }

        };
    }

    /** Apply this to the result of other. */
    public DerivableFunction<T> compose(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.Result<T> call(T x, Vector<T> a) {
                Result<T> o = other.call(x, a);
                Result<T> m = DerivableFunction.this.call(o.y, a);
                DenseVector<T> da = m.da.plus(o.da.times(m.dy));
                return new Result<T>(m.y, m.dy.times(o.dy), da);
            }

        };
    }

}
