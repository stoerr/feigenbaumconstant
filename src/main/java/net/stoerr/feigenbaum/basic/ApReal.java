package net.stoerr.feigenbaum.basic;

import javolution.context.LocalContext;
import javolution.text.Text;

import org.apfloat.Apfloat;
import org.jscience.mathematics.number.Number;
import org.jscience.mathematics.structure.Field;

/**
 * {@link Number} wrapper for {@link Apfloat}.
 * @author hps
 * @since 16.11.2008
 */
public class ApReal extends Number<ApReal> implements Field<ApReal> {

    private static final long serialVersionUID = 3870091756068514957L;

    /**
     * Holds precision for new numbers
     */
    private static final LocalContext.Reference<Integer> DIGITS = new LocalContext.Reference<Integer>(new Integer(20));

    private Apfloat rep;

    public ApReal(Apfloat rep) {
        this.rep = rep;
    }

    @Override
    public int compareTo(ApReal that) {
        return rep.compareTo(that.rep);
    }

    @Override
    public Number<ApReal> copy() {
        return new ApReal(rep);
    }

    @Override
    public double doubleValue() {
        return rep.doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
        ApReal other = (ApReal) obj;
        return rep.equals(other.rep);
    }

    @Override
    public int hashCode() {
        return rep.hashCode();
    }

    @Override
    public boolean isLargerThan(ApReal that) {
        return rep.compareTo(that.rep) > 0;
    }

    @Override
    public long longValue() {
        return rep.longValue();
    }

    @Override
    public Text toText() {
        return new Text(rep.toString());
    }

    public ApReal times(ApReal that) {
        return new ApReal(rep.multiply(that.rep));
    }

    public ApReal opposite() {
        return new ApReal(rep.negate());
    }

    public ApReal plus(ApReal that) {
        return new ApReal(rep.add(that.rep));
    }

    public static ApReal valueOf(double d) {
        return new ApReal(new Apfloat(d, getDigits()));
    }

    public static void setDigits(int digits) {
        DIGITS.set(digits);
    }

    public static int getDigits() {
        return DIGITS.get();
    }

    public ApReal inverse() {
        return new ApReal(one().rep.divide(rep));
    }

    public ApReal abs() {
        if (rep.compareTo(zero().rep) < 0) {
            return new ApReal(rep.negate());
        } else {
            return this;
        }
    }
    
    public static ApReal zero() {
        return new ApReal(new Apfloat(0,getDigits()));
    }
    
    public static ApReal one() {
        return new ApReal(new Apfloat(1,getDigits()));
    }
}
