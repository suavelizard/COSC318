package tests;

import org.junit.Test;
import junit.framework.TestSuite;

/**
 * Created by Zane on 2014-11-06.
 */
public class AllTests {
    @Test
    public static void main (String[] args){
        junit.textui.TestRunner.run(suite());
    }
    public static junit.framework.Test suite (){
        TestSuite suite = new TestSuite("All JUnit Tests");
        return suite;
    }

}