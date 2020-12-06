import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
        plugin={"html:target/cucumber-html-report","html:target/site/cucumber-pretty", "json:target/cucumber.json"

              },
        tags={"@guray2"}

)

public class Runner2 {



}