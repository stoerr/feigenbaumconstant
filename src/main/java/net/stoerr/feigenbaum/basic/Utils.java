package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;

public class Utils {

    public static LargeInteger over(int n, int m) {
        LargeInteger mul = LargeInteger.ONE;
        if (m > n / 2) {
            m = n - m;
        }
        for (int i = n - m + 1; i <= n; ++i) {
            mul = mul.times(i);
        }
        LargeInteger div = LargeInteger.ONE;
        for (int i = 2; i <= m; ++i) {
            div = div.times(i);
        }
        return mul.divide(div);
    }

    public static interface Converter<T> {
        public T value(LargeInteger l);
    }

    public static final Converter<Real> REALCONV = new Converter<Real>() {

        public Real value(LargeInteger l) {
            return Real.valueOf(l, 0, 0);
        }

    };

}
