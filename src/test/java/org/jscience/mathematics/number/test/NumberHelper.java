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
 * Quick and dirty implementation of the missing abstraction for the Numbersets (that is, we turn the static methods
 * into object methods for genericity) and generic access to common methods not provided in {@link Number}.
 * @author hps
 * @since 11.12.2008
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class NumberHelper<T extends Number<T>> {

    protected final Class<T> numberClass;

    public Class<T> getNumberClass() {
        return numberClass;
    }

    protected NumberHelper(Class<T> clazz) {
        numberClass = clazz;
    }

    /** Returns the value of a static field */
    public T invokeStaticField(String method) {
        try {
            return (T) numberClass.getField(method).get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getOne() {
        return invokeStaticField("ONE");
    }

    public T getZero() {
        return invokeStaticField("ZERO");
    }

    public T getNaN() {
        return invokeStaticField("NaN");
    }

    /** Invokes a static method with one argument */
    public <Arg> T invokeStaticMethod(String method, Class<Arg> clazz, Arg arg) {
        try {
            return (T) numberClass.getDeclaredMethod(method, clazz).invoke(null, arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T valueOf(double d) {
        return invokeStaticMethod("valueOf", double.class, d);
    }

    public T valueOf(CharSequence s) {
        return invokeStaticMethod("valueOf", CharSequence.class, s);
    }

    /** Invokes a method without arguments on arg */
    public T invokeMethod(String method, T arg) {
        try {
            return (T) numberClass.getDeclaredMethod(method).invoke(arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /** Invokes a method with one other argument of type T on arg1, arg2 */
    public T invokeMethod(String method, T arg1, T arg2) {
        try {
            return (T) numberClass.getDeclaredMethod(method, numberClass).invoke(arg1, arg2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /** Invokes a method without argument on arg */
    public boolean invokeBooleanMethod(String method, T arg) {
        try {
            return (Boolean) numberClass.getDeclaredMethod(method).invoke(arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

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
                return (T) numberClass.getDeclaredMethod("valueOf", long.class).invoke(null, MathLib.round(d));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final NumberHelper<LargeInteger> LARGEINTEGER = new IntegerHelper<LargeInteger>(LargeInteger.class);
    public static final NumberHelper<Integer64> INTEGER64 = new IntegerHelper<Integer64>(Integer64.class);

    public static final NumberHelper<FloatingPoint> FLOATINGPOINT = new NumberHelper<FloatingPoint>(FloatingPoint.class);
    public static final NumberHelper<Real> REAL = new NumberHelper<Real>(Real.class);
    public static final NumberHelper<Float64> FLOAT64 = new NumberHelper<Float64>(Float64.class);
}
