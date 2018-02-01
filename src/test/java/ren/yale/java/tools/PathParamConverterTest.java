package ren.yale.java.tools;

import junit.framework.TestCase;

/**
 * Created by yale on 2018/2/1.
 */
public class PathParamConverterTest extends TestCase {
    public void testConverter() throws Exception {
        PathParamConverter.converter("/{a}/{b}/{c}");
        PathParamConverter.converter("/{a}/ab/{c}");
        PathParamConverter.converter("/a/ab/");
    }

}