/**
 * Hans-Peter Störr, 11.12.2008
 */
package org.jscience.mathematics.number.test;

import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;

import java.lang.reflect.InvocationTargetException;

import javolution.lang.MathLib;
import javolution.testing.TestCase;

import org.jscience.mathematics.number.Number;

public abstract class AbstractNumberTest<T extends Number<T>> extends TestCase {
    final double _expected;
    final NumberHelper<T> _helper;
    final String _description;
    T _value;
    Exception _exception;
    
    AbstractNumberTest(String description, double expected, NumberHelper<T> helper) {
        _expected = expected;
        _helper = helper;
        _description = description;
    }

    @Override
    public final void execute() {
        try {
            _value = operation();
        } catch (InvocationTargetException e) {
            _exception = (Exception) e.getTargetException();
        } catch (Exception e) {
            _exception = e;
        }
    }

    @Override
    public final void validate() {
        super.validate();
        if (null != _exception) {
            _exception.printStackTrace();
        }
        assertEquals(getDescription().toString(), null, _exception);
        assertTrue(getDescription() + ": no value received" , null != _value);
        compareresult();
    }

    void compareresult() {
        final double result = _value.doubleValue();
        if (0 == _expected) {
            assertTrue(getDescription().toString() + " but got " + result, AbstractFloatTestSuite.eps > MathLib.abs(result));
        } else {
            assertTrue(getDescription().toString() + " but got " + result, AbstractFloatTestSuite.eps > MathLib.abs(result / _expected - 1));
        }
    }
    
    @Override
    public CharSequence getDescription() {
        return _description + " expecting " + _expected; 
    }

    /** Should return the value of the operation to test and set _expected to the expected value. */
    abstract T operation() throws Exception;
}