/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number.test;

import java.lang.reflect.InvocationTargetException;

import javolution.lang.MathLib;

import org.jscience.mathematics.number.*;
import org.jscience.mathematics.number.Number;

/**
 * Quick and dirty implementation of the missing abstraction for the Numbersets such that static constants like
 * {@link FloatingPoint#ONE} and static methods like {@link FloatingPoint#valueOf(double)} can be used with generic
 * tests.<br>
 * The quick and dirty part about it is that all this is implemented mainly by reflection and some methods do not work
 * for all subclasses of {@link Number} because the corresponding methods are missing.
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 * @since 11.12.2008
 * @param <T> the subclass of {@link Number} the helper works for.
 */
@SuppressWarnings("unchecked")
public class NumberHelper<T extends Number<T>> {

    protected final Class<T> _numberClass;

    /** Returns the {@link Class} of {@link Number} that this helper applies to. */
    public Class<T> getNumberClass() {
        return _numberClass;
    }

    protected NumberHelper(Class<T> clazz) {
        _numberClass = clazz;
    }

    /**
     * Makes a {@link RuntimeException} of e and throws it. If it is an {@link Error} it is just rethrown as well, if it
     * is an {@link InvocationTargetException} we throw the cause to get rid of the annoying wrapping.
     */
    private RuntimeException rethrowException(Throwable t) {
        if (t instanceof RuntimeException) { throw (RuntimeException) t; }
        if (t instanceof InvocationTargetException) {
            InvocationTargetException te = (InvocationTargetException) t;
            throw rethrowException(te.getTargetException());
        }
        if (t instanceof Error) { throw (Error) t; }
        throw new RuntimeException(t.toString(), t);
    }

    /** Returns the value of a static field. */
    public T invokeStaticField(String method) {
        try {
            return (T) _numberClass.getField(method).get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** The value 1. */
    public T getOne() {
        return invokeStaticField("ONE");
    }

    /** The value 0. */
    public T getZero() {
        return invokeStaticField("ZERO");
    }

    /** The value NaN. */
    public T getNaN() {
        return invokeStaticField("NaN");
    }

    /** Invokes a static method with one argument. */
    public <Arg> T invokeStaticMethod(String method, Class<Arg> clazz, Arg arg) {
        try {
            return (T) _numberClass.getDeclaredMethod(method, clazz).invoke(null, arg);
        } catch (Exception e) {
            throw rethrowException(e);
        }
    }

    /** Provides access to the static valueOf(double) method. */
    public T valueOf(double d) {
        return invokeStaticMethod("valueOf", double.class, d);
    }

    /** Provides access to the static valueOf(CharSequence) method. */
    public T valueOf(CharSequence s) {
        return invokeStaticMethod("valueOf", CharSequence.class, s);
    }

    /** Invokes a method without arguments on arg. */
    public T invokeMethod(String method, T arg) {
        try {
            return (T) _numberClass.getDeclaredMethod(method).invoke(arg);
        } catch (Exception e) {
            throw rethrowException(e);
        }

    }

    /** Invokes a method with one other argument of type T on arg1, arg2. */
    public T invokeMethod(String method, T arg1, T arg2) {
        try {
            return (T) _numberClass.getDeclaredMethod(method, _numberClass).invoke(arg1, arg2);
        } catch (Exception e) {
            throw rethrowException(e);
        }

    }

    /** Invokes a method without argument on arg. */
    public boolean invokeBooleanMethod(String method, T arg) {
        try {
            return (Boolean) _numberClass.getDeclaredMethod(method).invoke(arg);
        } catch (Exception e) {
            throw rethrowException(e);
        }

    }

    /**
     * {@link NumberHelper} for integer classes.
     */
    protected static class IntegerHelper<T extends Number<T>> extends NumberHelper<T> {

        protected IntegerHelper(Class<T> clazz) {
            super(clazz);
        }

        /**
         * We approximate this with valueOf(long).
         */
        @Override
        public T valueOf(double d) {
            try {
                return (T) _numberClass.getDeclaredMethod("valueOf", long.class).invoke(null, MathLib.round(d));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** The {@link NumberHelper} for {@link LargeInteger}. */
    public static final NumberHelper<LargeInteger> LARGEINTEGER = new IntegerHelper<LargeInteger>(LargeInteger.class);

    /** The {@link NumberHelper} for {@link Integer64}. */
    public static final NumberHelper<Integer64> INTEGER64 = new IntegerHelper<Integer64>(Integer64.class);

    /** The {@link NumberHelper} for {@link FloatingPoint}. */
    public static final NumberHelper<FloatingPoint> FLOATINGPOINT = new NumberHelper<FloatingPoint>(FloatingPoint.class);

    /** The {@link NumberHelper} for {@link Real}. */
    public static final NumberHelper<Real> REAL = new NumberHelper<Real>(Real.class);

    /** The {@link NumberHelper} for {@link Float64}. */
    public static final NumberHelper<Float64> FLOAT64 = new NumberHelper<Float64>(Float64.class);
}
