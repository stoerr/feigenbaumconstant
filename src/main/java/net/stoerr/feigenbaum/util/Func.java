package net.stoerr.feigenbaum.util;

/**
 * Some sideeffect free function from Arg to Val. (Poor mans closure.)
 */
public interface Func<Arg, Val> {

    Val call(Arg... arg);

}
