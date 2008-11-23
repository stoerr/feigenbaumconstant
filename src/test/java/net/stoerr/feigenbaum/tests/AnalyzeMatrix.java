package net.stoerr.feigenbaum.tests;

import static net.stoerr.feigenbaum.FeigenConstants.FREN;

import java.util.List;

import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import net.stoerr.feigenbaum.approx.Approximation;
import net.stoerr.feigenbaum.approx.DerivableApproximation;
import net.stoerr.feigenbaum.basic.ApReal;
import net.stoerr.feigenbaum.basic.BernsteinPolynomials;
import net.stoerr.feigenbaum.basic.LegendrePolynomials;
import net.stoerr.feigenbaum.basic.NumHelper;
import net.stoerr.feigenbaum.basic.Pair;

public class AnalyzeMatrix {

    private NumHelper<ApReal> h = NumHelper.AP;
    // private Approximation<ApReal> a = new Approximation<ApReal>(new BernsteinPolynomials<ApReal>(40, h));
    private Approximation<ApReal> a = new Approximation<ApReal>(new LegendrePolynomials<ApReal>(40, h));
    private DerivableApproximation<ApReal> derivap = new DerivableApproximation<ApReal>(a.pol);

    /** */
    public static void main(String[] args) {
        ApReal.setDigits(100);
        new AnalyzeMatrix().run1();
    }

    private void run1() {
        DenseVector<ApReal> g = a.initialApproximation(h.wrap(FREN));
        g = h.adjustPrecision(g);
        System.out.println("=== BERNSTEIN ====");
        List<ApReal> vals = derivap.getBernsteinMaxima();
        vals = h.adjustPrecision(vals);
        Pair<DenseMatrix<ApReal>, DenseVector<ApReal>> sys = derivap.equationSystem(g, vals);
        DenseMatrix<ApReal> m = sys.x;
        m = h.adjustPrecision(m);
        // System.out.println(sys.x.inverse());
        visualizeMatrix(m.inverse());
        System.out.println("=== LEGENDRE ====");
        vals = derivap.getLegendreRoots();
        sys = derivap.equationSystem(g, vals);
        m = h.adjustPrecision(sys.x);
        // System.out.println(sys.x);
        // System.out.println(sys.x.inverse());
        visualizeMatrix(m.inverse());
    }

    /** Show evolution of the matrix. */
    private void run2() {
        DenseVector<ApReal> g = a.initialApproximation(h.wrap(FREN));
        // List<ApReal> vals = derivap.getBernsteinMaxima();
        List<ApReal> vals = derivap.getLegendreRoots();
        for (int i = 0; i < 5; ++i) {
            System.out.println(h.d(a.evaluateApproximation(g)));
            Pair<DenseMatrix<ApReal>, DenseVector<ApReal>> sys = derivap.equationSystem(g, vals);
            // System.out.println(sys.x);
            visualizeMatrix(sys.x);
            System.out.println("-----");
            visualizeVector(sys.y);
            g = derivap.improve(g, vals);
        }
    }

    /** */
    private void visualizeMatrixRows(DenseMatrix<ApReal> m) {
        for (int i = 0; i < m.getNumberOfRows(); ++i) {
            DenseVector<ApReal> r = m.getRow(i);
            visualizeVector(r);
        }
    }

    private void visualizeMatrix(DenseMatrix<ApReal> m) {
        double max = 0;
        for (int i = 0; i < m.getNumberOfRows(); ++i) {
            DenseVector<ApReal> r = m.getRow(i);
            for (int j = 0; j < r.getDimension(); ++j) {
                double v = Math.abs(h.d(r.get(j)));
                max = Math.max(max, v);
            }
        }
        for (int i = 0; i < m.getNumberOfRows(); ++i) {
            DenseVector<ApReal> r = m.getRow(i);
            for (int j = 0; j < r.getDimension(); ++j) {
                double v = Math.abs(h.d(r.get(j)));
                // int d = (int) Math.round(9.49 * v / max);
                int d = (int) Math.round(Math.log(9e8*v/max+1)/Math.log(10));
                System.out.print(" " + d);
            }
            System.out.println();
        }
    }

    private void visualizeVector(DenseVector<ApReal> r) {
        double max = 0;
        for (int j = 0; j < r.getDimension(); ++j) {
            double v = Math.abs(h.d(r.get(j)));
            max = Math.max(max, v);
        }
        for (int j = 0; j < r.getDimension(); ++j) {
            double v = Math.abs(h.d(r.get(j)));
            int d = (int) Math.round(9.49 * v / max);
            System.out.print(" " + d);
        }
        System.out.println();
    }

}
