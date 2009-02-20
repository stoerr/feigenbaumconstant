package org.jscience.mathematics.number.util;

import static java.lang.StrictMath.nextAfter;
import static java.lang.StrictMath.nextUp;
import javolution.text.Text;
import javolution.text.TextBuilder;

import org.jscience.mathematics.number.Number;

/**
 * A feeble attempt at double interval arithmetics.
 * @author hps
 * @since 20.02.2009
 */
public class DoubleInterval extends Number<DoubleInterval> {

    public static final DoubleInterval ZERO = new DoubleInterval(0, 0);
    public static final DoubleInterval ONE = new DoubleInterval(1, 1);
    public static final DoubleInterval NaN = new DoubleInterval(Double.NaN, Double.NaN);

    /** SUID */
    private static final long serialVersionUID = -1798040855774523462L;

    private final double _lower;
    private final double _upper;

    public DoubleInterval(double lower, double upper) {
        if (Double.isNaN(upper) || Double.isNaN(lower)) {
            _lower = Double.NaN;
            _upper = Double.NaN;
        } else {
            assert lower <= upper : lower + ">" + upper;
            _lower = lower;
            _upper = upper;
        }
    }

    public static DoubleInterval valueOf(double lower, double upper) {
        return new DoubleInterval(lower, upper);
    }

    /** Rounds one ulp outward for operations that are within at most 1 ulp of the exact value. */
    private static DoubleInterval outward(double lower, double upper) {
        return new DoubleInterval(nextAfter(lower, Double.NEGATIVE_INFINITY), nextUp(upper));
    }

    @Override
    public int compareTo(DoubleInterval that) {
        UnsupportedOperationException e = new UnsupportedOperationException(
                "Unimplemented method called: Number<DoubleInterval>.compareTo");
        throw e;
    }

    @Override
    public Number<DoubleInterval> copy() {
        return valueOf(_lower, _upper);
    }

    @Override
    public double doubleValue() {
        return (_lower + _upper) / 2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DoubleInterval) {
            DoubleInterval that = (DoubleInterval) obj;
            return _lower == that._lower && _upper == that._upper;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(_lower).hashCode() * 13 + Double.valueOf(_upper).hashCode() * 47;
    }

    @Override
    public boolean isLargerThan(DoubleInterval that) {
        return _lower > that._upper;
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public Text toText() {
        javolution.text.TextBuilder buf = TextBuilder.newInstance();
        buf.append("<");
        buf.append(_lower);
        buf.append(",");
        buf.append(_upper);
        buf.append(">");
        Text res = buf.toText();
        TextBuilder.recycle(buf);
        return res;
    }

    public DoubleInterval times(DoubleInterval that) {
        double _l1 = _lower * that._lower;
        double _l2 = _lower * that._upper;
        double _l3 = _upper * that._lower;
        double _l4 = _upper * that._upper;
        return outward(StrictMath.min(StrictMath.min(_l1, _l2), StrictMath.min(_l3, _l4)), StrictMath.max(
                StrictMath.max(_l1, _l2), StrictMath.max(_l3, _l4)));
    }

    public DoubleInterval opposite() {
        return valueOf(-_upper, -_lower);
    }

    public DoubleInterval plus(DoubleInterval that) {
        return outward(_lower + that._lower, _upper + that._upper);
    }

    public boolean contains(double d) {
        return _lower <= d && d <= _upper;
    }

    /** The multiplicative inverse. */
    public DoubleInterval inverse() {
        DoubleInterval res;
        if (contains(0)) {
            res = NaN;
        } else {
            res = outward(1 / _upper, 1 / _lower);
        }
        return res;
    }

    public DoubleInterval divide(DoubleInterval that) {
        return times(that.inverse());
    }

    public DoubleInterval abs() {
        DoubleInterval res;
        if (contains(0)) {
            res = valueOf(0, StrictMath.max(-_lower, _upper));
        } else {
            res = this;
        }
        return res;
    }

    public DoubleInterval min(DoubleInterval that) {
        return valueOf(StrictMath.min(_lower, that._lower), StrictMath.min(_upper, that._upper));
    }

    public DoubleInterval max(DoubleInterval that) {
        return valueOf(StrictMath.max(_lower, that._lower), StrictMath.max(_upper, that._upper));
    }
}
