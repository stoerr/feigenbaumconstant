package net.stoerr.feigenbaum.basic;

public class Pair<T1, T2> {

    public final T1 x;
    public final T2 y;

    private Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    public static <T1, T2> Pair<T1, T2> make(T1 x, T2 y) {
        return new Pair<T1, T2>(x, y);
    }

}
