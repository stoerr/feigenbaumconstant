package net.stoerr.feigenbaum.basic;

import junit.framework.TestCase;

public class GenericsHelperTest extends TestCase {
    
    public void testParameterClass() {
        Class clazz = GenericsHelper.parameterClass("bla");
        System.out.println(clazz);
        assertEquals(clazz.toString(), String.class, clazz);
    }

}
