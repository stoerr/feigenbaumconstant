package net.stoerr.feigenbaum.tests;

import junit.framework.TestCase;

import net.stoerr.feigenbaum.symbolic.UnspecifiedFunction;
import net.stoerr.feigenbaum.util.Func;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Real;

public class FunctionTest extends TestCase {

    public void testFunction() throws Exception {
        Variable<Real> x = new Variable.Global<Real>("x");
        Polynomial<Real> px = Polynomial.valueOf(Real.ONE, x);
        final Polynomial<Real> f = px.plus(Real.ONE).plus(px.times(px));
        System.out.println(f);
        System.out.println(f.evaluate(Real.ONE));
        final Polynomial<Real> fx = f.differentiate(x);
        System.out.println(fx);
        System.out.println(f.divide(fx));
    }

    public void testUnspecifiedFunction() throws Exception {
        Variable<Real> x = new Variable.Global<Real>("x");
        Variable<Func<Real,Real>> vf = new Variable.Global<Func<Real,Real>>("f");
        UnspecifiedFunction<Real, Real> f = new UnspecifiedFunction<Real, Real>(vf, x);
        System.out.println(f);
        System.out.println(f.differentiate(x));
    }

}
