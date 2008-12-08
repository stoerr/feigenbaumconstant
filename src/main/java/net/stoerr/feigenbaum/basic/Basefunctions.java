package net.stoerr.feigenbaum.basic;

import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

public interface Basefunctions<T extends Field<T>> {

    T nth(T x, int m);

    /** DenseVector of the polynomials for x */
    DenseVector<T> functionValues(T x);

    /** DenseVector of the first derivations of the polynomials. */
    DenseVector<T> difValues(T x);

    /** Value of approximation with coefficients coeff */
    T value(Vector<T> coeff, T x);

    /** Value of first derivation of approximation with coefficients coeff */
    T diffvalue(Vector<T> coeff, T x);

    /** The actual number of the polynomials */
    int count();
    
    NumHelper<T> getHelper();

}
