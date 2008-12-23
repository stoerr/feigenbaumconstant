/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number.test;

import org.jscience.mathematics.number.Float64;

public class Float64TestSuite extends AbstractFloatTestSuite<Float64> {

    public Float64TestSuite() {
        super(NumberHelper.FLOAT64);
    }

    /**
     * Overridden to do nothing since it has no isZero().
     */
    @Override
    protected void testIsPositive() {
        // not there 8-{
    }

    /**
     * Overridden to do nothing since it has no isZero().
     */
    @Override
    protected void testIsNegative() {
        // not there 8-{
    }

    /**
     * Overridden to do nothing since it has no isZero().
     */
    @Override
    protected void testIsZero() {
        // not there 8-{
    }
}
