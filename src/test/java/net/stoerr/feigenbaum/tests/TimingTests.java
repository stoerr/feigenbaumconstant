package net.stoerr.feigenbaum.tests;

import javolution.lang.MathLib;

public class TimingTests {

    /**  */
    public static void main(String[] args) {
        long begin = System.nanoTime();
        long next = begin;
        while (next == begin)
            next = System.nanoTime();
        begin = next;
        while (next == begin)
            next = System.nanoTime();
        System.out.println("Nanotime step (ns): " + (next - begin));
        new TimingTests().run1();
        new TimingTests().run1();
        
    }
    
    private void run1() {
        System.out.println(" nix " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                double a = 0.3423;
                double b = 0;
                for (int i = 0; i < runs; ++i) {
                    // empty
                    b = a;
                }
                return 0;
            }
        }));
        System.out.println(" (long) " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                double a = 0.32312;
                double s = 0;
                for (int i = 0; i < runs; ++i) {
                    s = (long) a;
                }
                return (long) s;
            }
        }));
        System.out.println(" trysin " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                double a = 0.32312;
                final double c = 128 / Math.PI;
                double s = 0;
                double as = 0;
                for (int i = 0; i < runs; ++i) {
                    as = c * a;
                    s = (long) as;
                }
                return (long) s;
            }
        }));
    }

    private void run() {
        System.out.println(" nix " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                for (int i = 0; i < runs; ++i) {
                    // empty
                }
                return 0;
            }
        }));
        System.out.println(" timeSinCalc " + timingNS(new Timed() {
            @Override
            public long run(int rounds) {
                double a = 0.123;
                final double c = 20 / Math.PI;
                double s = 0;
                for (int i = 0; i < rounds; ++i) {
                    double a1 = c * a;
                    double fl = Math.rint(a1);
                    // long n = Math.round(a1 - fl);
                    // int k=0;
                    // s = (((0.5343 * a + 0.323123) * a + 0.3432323) * a + 0.4322323) * a + 0.4323;
                }
                return (long) s;
            }
        }));
        System.out.println(" double % " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                double a = 0.984342;
                double b = 0.25454;
                double c = 0;
                for (int i = 0; i < runs; ++i) {
                    c = a % b;
                }
                return (long) c;
            }
        }));
        System.out.println(" double long " + timingNS(new Timed() {
            @Override
            public long run(int runs) {
                double a = 0.984342;
                long c = 0;
                for (int i = 0; i < runs; ++i) {
                    c = (long) a;
                }
                return c;
            }
        }));
        System.out.println(" double * " + timingNS(new Timed() {
            @Override
            public long run(int rounds) {
                double a = 0.123;
                double s = 0;
                for (int i = 0; i < rounds; ++i) {
                    s = a * a;
                }
                return (long) s;
            }
        }));
        System.out.println(" timeSin " + timingNS(new Timed() {
            @Override
            public long run(int rounds) {
                double a = 0.1;
                double s = 0;
                for (int i = 0; i < rounds; ++i) {
                    s = Math.sin(a);
                }
                return (long)s;
            }
        }));
        System.out.println(" timeMathLibSin " + timingNS(new Timed() {
            @Override
            public long run(int rounds) {
                double a = 6.28;
                double s = 0;
                for (int i = 0; i < rounds; ++i) {
                    s = MathLib.sin(a);
                }
                return (long) s;
            }
        }));
        System.out.println(" timeSinRepl " + timingNS(new Timed() {
            @Override
            public long run(int rounds) {
                double a = 0.123;
                double s = 0;
                for (int i = 0; i < rounds; ++i) {
                    s = (((0.5343 * a + 0.323123)*a + 0.3432323) * a +0.4322323)*a + 0.4323;
                }
                return (long)s;
            }
        }));
    }

    /** Number of nanoseconds a run of a {@link Timed} takes. */
    double timingNS(Timed timed) {
        long begin = System.nanoTime();
        long donerounds = 0;
        int rounds = 1000;
        long end = begin;
        while (end - begin < 1000000000L) {
            if (rounds < Integer.MAX_VALUE / 3)
                rounds *= 2;
            timed.run(rounds);
            donerounds += rounds;
            end = System.nanoTime();
        }
        return (end - begin) * 1.0 / donerounds;
    }

    public abstract class Timed {
        public abstract long run(int runs);
    }

}
