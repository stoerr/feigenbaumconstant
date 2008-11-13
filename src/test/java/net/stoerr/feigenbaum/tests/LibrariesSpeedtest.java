package net.stoerr.feigenbaum.tests;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatContext;
import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.number.Real;

import junit.framework.TestCase;

public class LibrariesSpeedtest extends TestCase {

    final int rounds = 100;
    final int precision = 2000;

    /**
     * 100r 10000p : 1.3s , 20000p : 3.3s, 40000p: 5s , 80000p: 16532, 160000p:
     * 28.5
     */
    public void testApFloat() throws Exception {
        Apfloat sum = new Apfloat(0, precision);
        Apfloat f = new Apfloat(16, precision).divide(new Apfloat(17, precision));
        Apfloat s = new Apfloat(1, precision);
        System.out.println(f.doubleValue());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < rounds; ++i) {
            s = s.multiply(f);
            sum = sum.add(s);
        }
        long end = System.currentTimeMillis();
        System.out.println(s.doubleValue());
        System.out.println(sum.doubleValue());
        System.out.println(sum.toString().length());
        System.out.println(sum);
        System.out.println("testApFloat: " + (end - begin) + " ms");
    }

    /** 100r 10000p : 5.2s, 20000p: 21s , 40000p: 81s */
    public void testJScienceFloatingPoint() throws Exception {
        FloatingPoint sum = FloatingPoint.valueOf(0);
        sum.setDigits(precision);
        FloatingPoint f = FloatingPoint.valueOf(16);
        f.setDigits(precision);
        FloatingPoint f1 = FloatingPoint.valueOf(17);
        f1.setDigits(precision);
        f = f.divide(f1);
        FloatingPoint s = FloatingPoint.valueOf(1);
        s.setDigits(precision);
        System.out.println(f.doubleValue());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < rounds; ++i) {
            s = s.times(f);
            sum = sum.plus(s);
        }
        long end = System.currentTimeMillis();
        System.out.println(s.doubleValue());
        System.out.println(sum.doubleValue());
        System.out.println(sum.toString().length());
        System.out.println("testJScienceFloatingPoint: " + (end - begin) + " ms");
    }

    /** 100r 10000p : 6.5s , 20000p: 24.5s , 40000p: 100s */
    public void testJScienceReal() throws Exception {
        Real sum = Real.valueOf(0);
        sum.setExactPrecision(precision);
        Real f = Real.valueOf(16);
        f.setExactPrecision(precision);
        Real f1 = Real.valueOf(17);
        f1.setExactPrecision(precision);
        f = f.divide(f1);
        Real s = Real.valueOf(1);
        s.setExactPrecision(precision);
        System.out.println(f.doubleValue());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < rounds; ++i) {
            s = s.times(f);
            sum = sum.plus(s);
        }
        long end = System.currentTimeMillis();
        System.out.println(s.doubleValue());
        System.out.println(sum.doubleValue());
        System.out.println(sum.toString().length());
        System.out.println(sum);
        System.out.println("testJScienceReal: " + (end - begin) + " ms");
    }

    /**
     * 100 rounds: prec. 2000: 1.5s, 5000: 6.7s , prec. 10000: 23.6s
     *
     * @throws Exception
     */
    public void testBigdecimal() throws Exception {
        MathContext ctx = new MathContext(precision, RoundingMode.HALF_EVEN);
        BigDecimal sum = new BigDecimal(0, ctx);
        BigDecimal f = new BigDecimal(16, ctx).divide(new BigDecimal(17, ctx), ctx);
        BigDecimal s = new BigDecimal(1, ctx);
        System.out.println(f.doubleValue());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < rounds; ++i) {
            s = s.multiply(f, ctx);
            sum = sum.add(s, ctx);
        }
        long end = System.currentTimeMillis();
        System.out.println(s.doubleValue());
        System.out.println(sum.doubleValue());
        System.out.println(sum.toString().length());
        System.out.println("testBigDecimal: " + (end - begin) + " ms");
    }

}
