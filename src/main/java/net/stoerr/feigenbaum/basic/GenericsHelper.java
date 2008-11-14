package net.stoerr.feigenbaum.basic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * Helperklasse für generische Typen
 */
public abstract class GenericsHelper<T> {
    
    public abstract Class<T> getParameterClass();

    public static <T> Class<T> parameterClass(T paramClass) {
        GenericsHelper<T> helper = new GenericsHelper<T>() {
            public Class<T> getParameterClass() {
                ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
                TypeVariable<Class<T>> typeT = (TypeVariable<Class<T>>) t.getActualTypeArguments()[0];
                System.out.println(typeT);
                System.out.println(Arrays.asList(typeT.getBounds()));
                System.out.println(typeT.getGenericDeclaration());
                return typeT.getGenericDeclaration();
            }
        };
        return helper.getParameterClass();
    }
    
}
