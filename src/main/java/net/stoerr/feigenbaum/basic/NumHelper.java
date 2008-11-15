/**
 * 
 */
package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;

public interface NumHelper<T> {
    T v(LargeInteger l);
    T v(double d);

    T zero();

    T one();
    
    T pow(T val, int exp);
    
    double d(T val);

    NumHelper<Real> REAL = new NumHelper<Real>() {

        public Real v(LargeInteger l) {
            return Real.valueOf(l, 0, 0);
        }

        public Real one() {
            return Real.ONE;
        }

        public Real zero() {
            return Real.ZERO;
        }

        public Real pow(Real val, int exp) {
            if (0 == exp) return one();
            return val.pow(exp);
        }

        public double d(Real val) {
            return val.doubleValue();
        }

        public Real v(double d) {
            return Real.valueOf(d);
        }

    };

}