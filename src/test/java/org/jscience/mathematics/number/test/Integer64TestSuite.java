/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number.test;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.test;
import javolution.lang.MathLib;

import org.jscience.mathematics.number.Integer64;
import org.jscience.mathematics.number.LargeInteger;

public class Integer64TestSuite extends AbstractIntegerTestSuite<Integer64> {

    public Integer64TestSuite() {
        super(NumberHelper.INTEGER64);
    }

    @Override
    protected double getMaxNumber() {
        return MathLib.pow(2, 63);
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
