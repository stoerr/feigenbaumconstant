package net.stoerr.feigenbaum.util;

/**
 * Some sideeffect free function from Arg to Val. (Poor mans closure.)
 */
public interface F<Arg, Val> {

    Val call(Arg arg);

}
