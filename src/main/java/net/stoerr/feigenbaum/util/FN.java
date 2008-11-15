package net.stoerr.feigenbaum.util;

/**
 * Some sideeffect free function of arbitrarily many Arg to Val. (Poor mans
 * closure.)
 */
public interface FN<Arg, Val> {

    Val call(Arg... arg);

}
