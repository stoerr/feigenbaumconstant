package net.stoerr.feigenbaum.approx;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;

import net.stoerr.feigenbaum.approx.DerivableFunction.Result;
import net.stoerr.feigenbaum.basic.AbstractJScienceTest;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;
import net.stoerr.feigenbaum.util.F;

import junit.framework.TestCase;

public class TestDerivableFunction extends AbstractJScienceTest<FloatingPoint> {

    private int digitsold;
    private BernsteinPolynomials<FloatingPoint> b5;
    private DenseVector<FloatingPoint> a;
    private final FloatingPoint epsilon = h.v(0.000001);

    public TestDerivableFunction() {
        super(NumHelper.FP);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        digitsold = FloatingPoint.getDigits();
        // FloatingPoint.setDigits(30);
        b5 = new BernsteinPolynomials<FloatingPoint>(3, h);
        a = DenseVector.valueOf(new FloatingPoint[] { h.v(4.23), h.v(-3.25), h.v(1.29), h.v(0.37) });
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        FloatingPoint.setDigits(digitsold);
    }

    public FloatingPoint derivation(F<FloatingPoint, FloatingPoint> f, FloatingPoint x) {
        final FloatingPoint x1 = x.plus(epsilon);
        final FloatingPoint x2 = x.plus(epsilon.opposite());
        FloatingPoint v1 = f.call(x1);
        FloatingPoint v2 = f.call(x2);
        return v1.plus(v2.opposite()).divide(epsilon.times(h.v(2)));
    }

    public void testCompose() {
        final DerivableFunction<FloatingPoint> cfunc = DerivableFunction.makeConstant(h.v(2.91), h);
        final DerivableFunction<FloatingPoint> xfunc = DerivableFunction.makeIdentity(h);
        final DerivableFunction<FloatingPoint> polfunc = DerivableFunction.makeBernstein(b5);
        final DerivableFunction<FloatingPoint> combifunc = polfunc.compose(cfunc.times(xfunc));
        for (final DerivableFunction<FloatingPoint> testfunc : new DerivableFunction[] { cfunc, xfunc, polfunc,
                combifunc }) {
            for (final double xd : new double[] { 0, 0.3253, 0.690, 1 }) {
                FloatingPoint x = h.v(xd);
                Result<FloatingPoint> result = testfunc.call(x, a);
                System.out.println(xd + "\t" + h.d(result.y) + "\t" + h.d(result.dy));
                F<FloatingPoint, FloatingPoint> f = new F<FloatingPoint, FloatingPoint>() {
                    public FloatingPoint call(FloatingPoint arg) {
                        return testfunc.call(arg, a).y;
                    }
                };
                FloatingPoint realderiv = derivation(f, x);
                near(result.dy, realderiv);
            }
        }
    }
}
