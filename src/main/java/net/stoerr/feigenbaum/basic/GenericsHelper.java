package net.stoerr.feigenbaum.basic;

/**
 * Helperklasse für generische Typen
 */
public class GenericsHelper<T> {

    public final Class<T> paramClass;
    
    private GenericsHelper(Class<T> paramClass) {
        this.paramClass = paramClass;
    }
    
}
