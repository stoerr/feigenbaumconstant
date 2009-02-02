package org.jscience.mathematics.number;

import java.util.List;

import javolution.lang.MathLib;

public class RationalTestSuite extends AbstractNumberTestSuite<Rational> {

    protected RationalTestSuite() {
        super(NumberHelper.RATIONAL);
    }

    @Override
    protected void initTestValues(List<Pair<Double, Rational>> values) {
        values.add(Pair.make(0.0, _helper.getZero()));
        values.add(Pair.make(1.0, _helper.getOne()));
        for (double d : new double[] { 0.0, 1.0, 43234, -9382 }) {
            values.add(Pair.make(d, _helper.valueOf(MathLib.round(d))));
        }
        // FIXME add misc. rational values.
    }

}
