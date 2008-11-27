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
    public abstract CallResult<T> call(T x, Vector<T> a);

    public static class CallResult<T extends Field<T>> {
        public final T y;
        public final T dx;
        public final DenseVector<T> da;

        public CallResult(T y, T dx, DenseVector<T> da) {
            this.y = y;
            this.dx = dx;
            this.da = da;
        }
    }

    /** Calculates first and second derivation in the direction of da */
    public abstract Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da);

    public static class Dir2nd<T extends Field<T>> {
        public final T y;
        public final T dx;
        public final T dxx;
        public final Vector<T> da;
        public final Vector<T> day;
        public final Vector<T> daa;

        public Dir2nd(T y, T dx, T ddx, Vector<T> da, Vector<T> day, Vector<T> daa) {
            this.y = y;
            this.dx = dx;
            this.dxx = ddx;
            this.da = da;
            this.day = day;
            this.daa = daa;
        }
    }

    public static <T extends Field<T>> DerivableFunction<T> makeConstant(final T val, final NumHelper<T> h) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                return new CallResult<T>(val, h.zero(), h.vectorZero(a.getDimension()));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                final DenseVector<T> vz = h.vectorZero(a.getDimension());
                return new Dir2nd<T>(val, h.zero(), h.zero(), vz, vz, vz);
            }

        };
    }

    public static <T extends Field<T>> DerivableFunction<T> makeIdentity(final NumHelper<T> h) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                return new CallResult<T>(x, h.one(), h.vectorZero(a.getDimension()));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                final DenseVector<T> vz = h.vectorZero(a.getDimension());
                return new Dir2nd<T>(x, h.one(), h.zero(), vz, vz, vz);
            }

        };
    }

    public static <T extends Field<T>> DerivableFunction<T> makeBased(final Basefunctions<T> pol) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                return new CallResult<T>(pol.value(a, x), pol.diffvalue(a, x), pol.functionValues(x));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                final NumHelper<T> h = pol.getHelper();
                T first = h.zero();
                for (int i = 0; i < pol.count(); ++i) { // FIXME

                }
                return new Dir2nd<T>(pol.value(a, x), first, h.zero(), h.vectorZero(a.getDimension()));
            }
        };
    }

    public DerivableFunction<T> add(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                CallResult<T> myresult = DerivableFunction.this.call(x, a);
                CallResult<T> otherresult = other.call(x, a);
                return new CallResult<T>(myresult.y.plus(otherresult.y), myresult.dx.plus(otherresult.dx),
                        myresult.da.plus(otherresult.da));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                Dir2nd<T> my = DerivableFunction.this.directional2nd(x, a, da);
                Dir2nd<T> o = other.directional2nd(x, a, da);
                return new Dir2nd<T>(my.y.plus(o.y), my.dx.plus(o.dx), my.dxx.plus(o.dxx), my.da.plus(o.da),
                        my.day.plus(o.day), my.daa.plus(o.daa));
            }
        };
    }

    public DerivableFunction<T> times(final T factor) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                CallResult<T> myresult = DerivableFunction.this.call(x, a);
                return new CallResult<T>(myresult.y.times(factor), myresult.dx.times(factor), myresult.da.times(factor));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                Dir2nd<T> my = DerivableFunction.this.directional2nd(x, a, da);
                return new Dir2nd<T>(my.y.times(factor), my.dx.times(factor), my.dxx.times(factor),
                        my.da.times(factor), my.day.times(factor), my.daa.times(factor));
            }
        };
    }

    public DerivableFunction<T> times(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                CallResult<T> m = DerivableFunction.this.call(x, a);
                CallResult<T> o = other.call(x, a);
                return new CallResult<T>(m.y.times(o.y), m.dx.times(o.y).plus(m.y.times(o.dx)), m.da.times(o.y).plus(
                        o.da.times(m.y)));
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                Dir2nd<T> my = DerivableFunction.this.directional2nd(x, a, da);
                Dir2nd<T> o = other.directional2nd(x, a, da);
                final T dupl = my.dx.times(o.dx);
                final T second = my.dxx.times(o.y).plus(dupl.plus(dupl)).plus(my.y.times(o.dxx));
                Vector<T> ya = my.da.times(o.y).plus(o.da.times(my.y));
                Vector<T> yay;
                Vector<T> yaa;
                return new Dir2nd<T>(my.y.times(o.y), my.dx.times(o.y).plus(my.y.times(o.dx)), second, ya, yay, yaa);
            }
        };
    }

    /** Apply this to the result of other. */
    public DerivableFunction<T> compose(final DerivableFunction<T> other) {
        return new DerivableFunction<T>() {

            @Override
            public net.stoerr.feigenbaum.approx.DerivableFunction.CallResult<T> call(T x, Vector<T> a) {
                CallResult<T> o = other.call(x, a);
                CallResult<T> m = DerivableFunction.this.call(o.y, a);
                DenseVector<T> da = m.da.plus(o.da.times(m.dx));
                return new CallResult<T>(m.y, m.dx.times(o.dx), da);
            }

            @Override
            public Dir2nd<T> directional2nd(T x, Vector<T> a, Vector<T> da) {
                Dir2nd<T> g = other.directional2nd(x, a, da);
                Dir2nd<T> f = DerivableFunction.this.directional2nd(g.y, a, da);
                final T second = g.dx.times(g.dx).times(f.dxx).plus(g.dxx.times(f.dx));
                return new Dir2nd<T>(f.y, g.dx.times(f.dx), second);
            }
        };
    }

}
