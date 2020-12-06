

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.runtime.model.CucumberFeature;
import org.junit.runner.JUnitCore;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import gherkin.events.PickleEvent;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class Parallel
{
    @BeforeMethod
    public void beforeMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
    }

    @Test
    public void testMethodsOne() {
        long id = Thread.currentThread().getId();
        JUnitCore junit = new JUnitCore();
        junit.run(Runner.class);
        System.out.println("Simple test-method One. Thread id is: " + id);
    }


    @Test
    public void testMethodsTwo() {
        long id = Thread.currentThread().getId();
        Runner runner1=new Runner();
        JUnitCore junit = new JUnitCore();
        junit.run(Runner2.class);
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }

    @AfterMethod
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }
}