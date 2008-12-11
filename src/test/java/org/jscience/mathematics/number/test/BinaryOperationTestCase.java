package org.jscience.mathematics.number.test;

import java.lang.reflect.Method;

import org.jscience.mathematics.number.Number;

public class BinaryOperationTestCase<T extends Number<T>> extends AbstractNumberTest<T> {

    final T _arg1;
    final T _arg2;
    String _operationname;

    BinaryOperationTestCase(T arg1, T arg2, double expected, String operationname, NumberHelper<T> helper) {
        super(expected, helper);
        _arg1 = arg1;
        _arg2 = arg2;
        _operationname = operationname;
    }

    @Override
    T operation() throws Exception {
        Method m = _arg1.getClass().getMethod(_operationname, _helper.getNumberClass());
        return (T) m.invoke(_arg1, _arg2);
    }
}
