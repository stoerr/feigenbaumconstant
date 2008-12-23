/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number.test;

/**
 * A pair of objects
 * @author hps
 * @since 11.12.2008
 */
public final class Pair<T1, T2> {

    final T1 _x;
    final T2 _y;

    private Pair(T1 x, T2 y) {
        _x = x;
        _y = y;
    }

    public static <T1, T2> Pair<T1, T2> make(T1 x, T2 y) {
        return new Pair<T1, T2>(x, y);
    }

    @Override
    public String toString() {
        return "{" + _x + "," + _y + "}";
    }
}
