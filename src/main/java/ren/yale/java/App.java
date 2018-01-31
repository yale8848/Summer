package ren.yale.java;

import ren.yale.java.controller.Hello;
import ren.yale.java.tools.AnnotationProcessor;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        AnnotationProcessor.get(Hello.class);
        System.out.println( "Hello World!" );
    }
}
