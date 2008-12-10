package net.stoerr.feigenbaum;

import static net.stoerr.feigenbaum.FeigenConstants.fapprox;

import net.stoerr.feigenbaum.util.F;

public class FeigenConstants {

    public static final double alpha = 2.502907875095892;

    public static final double delta = 4.66921060910299;

    /**
     * * Approximation of the searched function 1 - 1.527633x^2 + 0.104815x^4 -
     * 0.0267057x^6 + .... . More exact value: 1.000000000000000000000000000 -
     * 1.527632997036301454035890310x^2 + 0.1048151947873037332167426137x^4 +
     * 0.02670567052519335403265209493x^6 - 0.003527409660908709170234190769x^8 +
     * 0.00008160096654753174517219048643x^10 +
     * 0.00002528508423396353617626255186x^12 -
     * 0.000002556317166278493846353254082x^14 -
     * 0.00000009651271550891203216372576758x^16 +
     * 0.00000002819346397450409137075662724x^18 -
     * 0.0000000002773051160799011724373112613x^20 -
     * 0.0000000003028427022130566329838808729x^22 +
     * 2.670589280748075553964724751 E-11x^24 + 9.962291641028482309516423738
     * E-13x^26 - 3.624202982904156082231272345 E-13x^28 +
     * 2.179657744827070432205070008 E-14x^30 + 1.529232899480963381203627083
     * E-15x^32 - 3.184728789952787897482240499 E-16x^34 +
     * 1.134672106212041869027732120 E-17x^36
     */
    public static double fapprox(double x) {
        double y = x * x;
        // return 1 - 1.52763 * y + 0.104815 * y * y - 0.0267057 * y * y * y;
        return 1.000000000000000000000000000 - 1.527632997036301454035890310
                * p(x, 2) + 0.1048151947873037332167426137 * p(x, 4)
                + 0.02670567052519335403265209493 * p(x, 6)
                - 0.003527409660908709170234190769 * p(x, 8)
                + 0.00008160096654753174517219048643 * p(x, 10)
                + 0.00002528508423396353617626255186 * p(x, 12)
                - 0.000002556317166278493846353254082 * p(x, 14)
                - 0.00000009651271550891203216372576758 * p(x, 16)
                + 0.00000002819346397450409137075662724 * p(x, 18)
                - 0.0000000002773051160799011724373112613 * p(x, 20)
                - 0.0000000003028427022130566329838808729 * p(x, 22)
                + 2.670589280748075553964724751e-11 * p(x, 24)
                + 9.962291641028482309516423738e-13 * p(x, 26)
                - 3.624202982904156082231272345e-13 * p(x, 28)
                + 2.179657744827070432205070008e-14 * p(x, 30)
                + 1.529232899480963381203627083e-15 * p(x, 32)
                - 3.184728789952787897482240499e-16 * p(x, 34)
                + 1.134672106212041869027732120e-17 * p(x, 36);
    }

    private static double p(double x, int n) {
        return Math.pow(x, n);
    }

    /**
     * Transformation for better handling: sqr(fapprox(sqrt(x)); satifies same
     * equation f(x)*f(1) == f(f(x*f(1)))
     */
    public static double fren(double x) {
        double v = fapprox(Math.sqrt(x));
        return v * v;
    }
    
    public static final F<Double, Double> FAPPROX =new F<Double, Double>() {
        public Double call(Double arg) {
            return fapprox(arg);
        }
    };

    public static final F<Double, Double> FREN =new F<Double, Double>() {
        public Double call(Double arg) {
            return fren(arg);
        }
    };
}
