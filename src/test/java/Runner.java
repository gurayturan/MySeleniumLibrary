import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
        plugin={ "html:target/cucumber-report-html","pretty:target/cucumber-pretty.txt","usage:target/cucumber-usage.json", "junit:target/cucumber- results.xml"},
        tags={"@guray"}

)

public class Runner  {



}