package org.jscience.mathematics.number.test;

import java.lang.reflect.InvocationTargetException;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Number;
import org.jscience.mathematics.number.Real;

/**
 * Quick and dirty implementation of the missing abstraction for the Numbersets (that is, we turn the static methods
 * into object methods for genericity) and generic access to common methods not provided in {@link Number}.
 * @author hps
 * @since 11.12.2008
 * @param <T>
 */
public class NumberHelper<T extends Number<T>> {

    protected final Class<T> numberClass;

    public Class<T> getNumberClass() {
        return numberClass;
    }

    protected NumberHelper(Class<T> clazz) {
        numberClass = clazz;
    }

    public static <T extends Number<T>> NumberHelper<T> getInstance(Class<T> clazz) {
        return new NumberHelper<T>(clazz);
    }

    public T getOne() {
        try {
            return (T) numberClass.getDeclaredField("ONE").get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getZero() {
        try {
            return (T) numberClass.getDeclaredField("ZERO").get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public T getNaN() {
        try {
            return (T) numberClass.getDeclaredField("NaN").get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public T valueOf(double d) {
        try {
            return (T) numberClass.getDeclaredMethod("valueOf", double.class).invoke(null, d);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public T valueOf(CharSequence s) {
        try {
            return (T) numberClass.getDeclaredMethod("valueOf", CharSequence.class).invoke(null, s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static final NumberHelper<LargeInteger> LARGEINTEGER = getInstance(LargeInteger.class);
    public static final NumberHelper<FloatingPoint> FLOATINGPOINT = getInstance(FloatingPoint.class);
    public static final NumberHelper<Real> REAL = getInstance(Real.class);
    public static final NumberHelper<Float64> FLOAT64 = getInstance(Float64.class);
}
