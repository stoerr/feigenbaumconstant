package net.stoerr.feigenbaum.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

import net.stoerr.feigenbaum.util.F;

public class JScienceUtils {

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

    /**
     * Erzeugt Vektor Dimension n aus Werten von func von 0 bis n-1.
     */
    public static <T extends Field<T>> DenseVector<T> makeVector(int n,
            F<Integer, T> func) {
        List<T> l = new ArrayList<T>();
        for (int i = 0; i < n; ++i) {
            l.add(func.call(i));
        }
        return DenseVector.valueOf(l);
    }
    
    public static <T extends Field<T>> List<T> toList(Vector<T> v) {
        List<T> res = new ArrayList<T>(v.getDimension());
        for (int i=0; i<v.getDimension(); ++i) {
            res.add(v.get(i));
        }
        return res;
    }

}
