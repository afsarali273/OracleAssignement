package com.oracle;

import com.oracle.test.JsonValidationTest;
import org.testng.TestNG;


public class TestRunner {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();

        testNG.setTestClasses(new Class[]{JsonValidationTest.class});
        testNG.setVerbose(2);
        testNG.run();
    }
}
