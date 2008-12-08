package net.stoerr.feigenbaum.tests;

import junit.framework.TestCase;

import net.stoerr.feigenbaum.symbolic.UnspecifiedFunction;
import net.stoerr.feigenbaum.util.F;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.FloatingPoint;

public class FunctionTest extends TestCase {

    public void testFunction() throws Exception {
        Variable<FloatingPoint> x = new Variable.Global<FloatingPoint>("x");
        Polynomial<FloatingPoint> px = Polynomial.valueOf(FloatingPoint.ONE, x);
        final Polynomial<FloatingPoint> f = px.plus(FloatingPoint.ONE).plus(px.times(px));
        System.out.println(f);
        System.out.println(f.evaluate(FloatingPoint.ONE));
        final Polynomial<FloatingPoint> fx = f.differentiate(x);
        System.out.println(fx);
        System.out.println(f.divide(fx));
    }

    public void testUnspecifiedFunction() throws Exception {
        Variable<FloatingPoint> x = new Variable.Global<FloatingPoint>("x");
        Variable<F<FloatingPoint,FloatingPoint>> vf = new Variable.Global<F<FloatingPoint,FloatingPoint>>("f");
        UnspecifiedFunction<FloatingPoint, FloatingPoint> f = new UnspecifiedFunction<FloatingPoint, FloatingPoint>(vf, x);
        System.out.println(f);
        System.out.println(f.differentiate(x));
    }

}
