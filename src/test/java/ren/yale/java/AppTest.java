package ren.yale.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ren.yale.java.controller.Hello;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        SummerServer summerServer =SummerServer.create(8080);

        summerServer.getSummerRouter().registerResource(Hello.class);

        summerServer.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
