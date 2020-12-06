import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

public class MySteps{

    WebSteps websteps;
    @After
    public void tearDown(Scenario scenario)
    {
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) websteps.driver).getScreenshotAs(OutputType.BYTES);
            // embed it in the report.
            scenario.embed(screenshot, "image/png");
        }
        websteps.driver.get().quit();
        System.out.println("Driver kapatıldı");
    }

    @Before
    public void beforeScenario() throws MalformedURLException
    {/*long threadId = Thread.currentThread().getId();
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);*/
        websteps=new WebSteps();

    }

    @When("^I initialize ([^\"]*) driver and run test local=([^\"]*)$")
    public  void initializeChromeDriver(String browser,Boolean isLocal) throws MalformedURLException
    {
        websteps.initializeDriver(browser,isLocal);
    }

    @When("^I go to ([^\"]*) url$")
    public  void gotoURL(String url)
    {
        websteps.openUrl(url);
    }

    @When("^I see ([^\"]*) page")
    public  void sePage(String page)
    {
        //Defining page
        websteps.page= websteps.seePage(page);
    }

    @When("^I wait ([^\"]*) element (-?\\d+) seconds$")
    public void waitElement(String element,int timeout)
    {
        websteps.waitElement(element,timeout);
    }

    @When("^I wait (-?\\d+) seconds$")
    public void waitFor(int timeout)
    {
        for (int i=0;i<timeout;i++){
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @When("^I click ([^\"]*) element$")
    public  void clickElement(String element)
    {
        //elemente tıklar
        websteps.clickElement(element);
    }

    @When("^I enter ([^\"]*) text to ([^\"]*) text area$")
    public  void enterText(String text,String element)
    {
        WebElement object;
        object=websteps.findElement(element);

        if(object!=null)
        {
            object.sendKeys(text);
            System.out.println("Metin girildi.");
        }
    }

    @When("^I click ([^\"]*) keyboard button$")
    public  void clickKeyboard(String key)
    {
       websteps.clickKeyboard(key);

    }

    @When("^I tap ([^\"]*) keyboard button with ([^\"]*) element$")
    public void clickKeyboardWithElement(String key,String element)
    {
      websteps.clickKeyboardWithElement(key,element);
    }

    @When("^I send text ([^\"]*) to ([^\"]*) element$")
    public  void sendText(String value,String element)
    {
        //Input alanına text girer
        websteps.enterText(value,element);
    }

    @When("^I mouse hover to ([^\"]*) element$")
    public void mouseHoverAndClick(String element)
    {   //önce üzerine gelir sonra tıklar
        websteps.mouseHover(element);
    }
}
