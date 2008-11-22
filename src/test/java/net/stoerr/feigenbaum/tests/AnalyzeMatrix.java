package net.stoerr.feigenbaum.tests;

import static net.stoerr.feigenbaum.FeigenConstants.FREN;

import java.util.List;

import org.jscience.mathematics.number.FloatingPoint;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import net.stoerr.feigenbaum.approx.Approximation;
import net.stoerr.feigenbaum.approx.DerivableApproximation;
import net.stoerr.feigenbaum.basic.ApReal;
import net.stoerr.feigenbaum.basic.NumHelper;
import net.stoerr.feigenbaum.basic.Pair;

public class AnalyzeMatrix {

    private NumHelper<FloatingPoint>  h = NumHelper.FP;
    private Approximation<FloatingPoint> a = new Approximation<FloatingPoint>(40, h);
    private DerivableApproximation<FloatingPoint> derivap = new DerivableApproximation<FloatingPoint>(a.pol);

    /** */
    public static void main(String[] args) {
        FloatingPoint.setDigits(60);
        new AnalyzeMatrix().run2();
    }

    private void run1() {
        DenseVector<FloatingPoint> ginit = a.initialApproximation(h.wrap(FREN));
        DenseVector<FloatingPoint> g;
        System.out.println("=== BERNSTEIN ====");
        List<FloatingPoint> vals = derivap.getBernsteinMaxima();
        g = derivap.improve(ginit, vals);
        g = derivap.improve(g, vals);
        Pair<DenseMatrix<FloatingPoint>,DenseVector<FloatingPoint>> sys = derivap.equationSystem(g, vals);
        // System.out.println(sys.x);
        visualizeMatrix(sys.x);
        System.out.println("=== LEGENDRE ====");
        vals = derivap.getLegendreRoots();
        g = derivap.improve(ginit, vals);
        g = derivap.improve(g, vals);
        sys = derivap.equationSystem(g, vals);
        // System.out.println(sys.x);
        visualizeMatrix(sys.x);
    }
    
    /** Show evolution of the matrix. */
    private void run2() {
        DenseVector<FloatingPoint> g = a.initialApproximation(h.wrap(FREN));
        // List<FloatingPoint> vals = derivap.getBernsteinMaxima();
        List<FloatingPoint> vals = derivap.getLegendreRoots();
        for (int i=0; i<5; ++i) {
            System.out.println(h.d(a.evaluateApproximation(g)));
            Pair<DenseMatrix<FloatingPoint>,DenseVector<FloatingPoint>> sys = derivap.equationSystem(g, vals);
            // System.out.println(sys.x);
            visualizeMatrix(sys.x);
            System.out.println("-----");
            visualizeVector(sys.y);
            g = derivap.improve(g, vals);            
        }        
    }

    /** */
    private void visualizeMatrix(DenseMatrix<FloatingPoint> m) {
        for (int i=0; i<m.getNumberOfRows(); ++i) {
            DenseVector<FloatingPoint> r = m.getColumn(i);
            visualizeVector(r);
        }
    }

    private void visualizeVector(DenseVector<FloatingPoint> r) {
        double max=0;
        for (int j =0; j<r.getDimension(); ++j) {
            double v =  Math.abs(h.d(r.get(j)));
            max = Math.max(max,v);
        }
        for (int j =0; j<r.getDimension(); ++j) {
            double v =  Math.abs(h.d(r.get(j)));
            int d = (int) Math.round(9.49*v/max);
            System.out.print(" " + d);
        }
        System.out.println();
    }

}
