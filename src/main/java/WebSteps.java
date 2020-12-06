import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class WebSteps implements MainSteps{

    enum browserType
    {
        Chrome,
        Firefox,
        InternetExplorer
    }
    public ThreadLocal<WebDriver> driver=new ThreadLocal<>();
    public String page;
    public Parser parser=new Parser();
    Boolean isLocal;
    static browserType browserType;
    String nodeURL;

    public WebDriver getDriver(){
        return driver.get();

    }
    @After
    public void tearDown(){
        getDriver().quit();
    }
    public void initializeDriver(String browser,Boolean isLocal) throws MalformedURLException
    {
        nodeURL= parser.readConfigInfo(isLocal);
        switch (browser)
        {
            case "Firefox":
                browserType= WebSteps.browserType.Firefox;
                break;
            case "InternetExplorer":
                browserType= WebSteps.browserType.InternetExplorer;
                break;
            default:
                browserType= WebSteps.browserType.Chrome;
                break;
        }

        if(isLocal)
        {
            if(browserType.toString().equals("Chrome"))
            {
                System.setProperty("webdriver.chrome.driver",  Paths.get("").toAbsolutePath().toString()+"\\src\\drivers\\chromedriver.exe");
                driver.set(new ChromeDriver());
            }
            else if(browserType.toString().equals("InternetExplorer"))
            {
                System.setProperty("webdriver.ie.driver", Paths.get("").toAbsolutePath().toString()+"\\src\\drivers\\IEDriverServer.exe");
                driver.set(new InternetExplorerDriver());
            }
            else if(browserType.toString().equals("Firefox"))
            {
                System.setProperty("webdriver.gecko.driver", Paths.get("").toAbsolutePath().toString()+"\\src\\drivers\\geckodriver.exe");
                driver.set(new FirefoxDriver());
            }
        }
        else
        {
              if(browserType.toString().equals("Chrome"))
            {
                ChromeOptions capability = new ChromeOptions();
                driver.set(new RemoteWebDriver(new URL("http://192.168.1.26:10903/wd/hub"),capability));
            }
            else if(browserType.toString().equals("InternetExplorer"))
            {
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.introduceFlakinessByIgnoringSecurityDomains();
                driver.set(new RemoteWebDriver(new URL(nodeURL),options));
            }
            else if(browserType.toString().equals("Firefox"))
            {
                FirefoxOptions capabilities =  new FirefoxOptions();
                capabilities.setCapability("marionette", true);
                capabilities.setCapability("networkConnectionEnabled", true);
                capabilities.setCapability("browserConnectionEnabled", true);
                driver.set(new RemoteWebDriver(new URL("http://192.168.1.24:10904/wd/hub"),capabilities));
            }
        }

        if(!isLocal)
        {
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        System.out.println("*************Before Scenario***********");

    }

    public String seePage(String page)
    {
        if(parser.isPageExist(page))
        {
            System.out.println(page+" sayfası bulundu!");
            return page;
        }
        else
        {
            Assert.fail("Sayfa bulunamadı! '"+page+"'");
        }
        return null;
    }

    public void openUrl(String url)
    {
        this.driver.get().navigate().to(url);
        System.out.println(url+" adresine gidildi.");
    }

    public WebElement findElement(String elem)
    {
        WebElement object=null;
        String element=parser.getElement(page,elem);

       try
       {
           if(element!=null)
           {
               if (element.startsWith("//") || element.startsWith("(//"))
               {
                   object = driver.get().findElement(By.xpath(element));
                   System.out.println("Nesne bulundu : " + element);
               }
               else if (element.startsWith("#") || element.startsWith("."))
               {
                   object = driver.get().findElement(By.cssSelector(element));
                   System.out.println("Nesne bulundu : " + element);
               }
           }
           else if(element==null)
           {
               object= driver.get().findElement(By.xpath("//*[text()='"+elem+"'or contains(text(),'"+elem+"')]"));
           }
           if (object==null){
               System.out.println("Nesne bulunamadı : "+elem);
               Assert.fail("Nesne bulunamadı : "+elem);
           }
           return  object;
       }
       catch (Exception e)
       {
           System.out.println("Nesne bulunamadı : "+elem);
           Assert.fail("Nesne bulunamadı : "+elem);
           return null;
       }
    }

    public WebElement waitElement(String elem,int timeout)
    {
        WebElement object=null;
        String element =parser.getElement(page,elem);

        if(element!=null)
        {
            if(element.startsWith("//"))
            {
                object = new WebDriverWait(driver.get(), timeout)
                        .until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
                System.out.println("Nesne bulundu : "+element);
            }
            else if(element.startsWith("#")||element.startsWith("."))
            {
                object = new WebDriverWait(driver.get(), timeout)
                        .until(ExpectedConditions.elementToBeClickable(By.cssSelector(element)));
                System.out.println("Nesne bulundu : "+element);
            }
        }
        else if(element==null)
        {
            object = new WebDriverWait(driver.get(), timeout)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='"+elem+"'or contains(text(),'"+elem+"')]")));
        }

        if (object==null)
        {
            System.out.println("Nesne gelmedi...!!! : "+element);
            Assert.fail("Nesne gelmedi...!!! : "+element);
        }
        return object;
    }

    public void clickElement(String element)
    {
        WebElement object=waitElement(element,30);

        if(object!=null)
        {
            object.click();
            System.out.println("Nesneye tıklandı-->"+element);
        }
        else
        {
            System.out.println("Nesneye tıklanamadı-->"+element);
            Assert.fail("nesneye tıklanamadı");
        }
    }

    public void enterText(String text,String element)
    {
        WebElement object;
        object=findElement(element);

        if(object!=null)
        {
            object.sendKeys(text);
            System.out.println("Metin girildi.");
        }
    }

    public void clickKeyboard(String key)
    {
        Actions action = new Actions(driver.get());
        switch (key)
        {
            case "ENTER":
                action.sendKeys(Keys.ENTER).build().perform();
                System.out.println(key+ "'a tıklandı.");
                break;
            case "TAB":
                action.sendKeys(Keys.TAB).build().perform();
                System.out.println(key+ "'a tıklandı.");
                break;
            case "PAGE_DOWN":
                action.sendKeys(Keys.PAGE_DOWN).build().perform();
                System.out.println(key+ "'a tıklandı.");
                break;
            case "PAGE_UP":
                action.sendKeys(Keys.PAGE_UP).build().perform();
                System.out.println(key+ "'a tıklandı.");
                break;
            default:
                System.out.println("");
        }

    }

    public void clickKeyboardWithElement(String key,String element)
    {
        WebElement object = findElement(element);

        if(object!=null)
        {
            switch (key)
            {
                case "ENTER":
                    object.sendKeys(Keys.ENTER);
                    break;
                case "TAB":
                    object.sendKeys(Keys.TAB);
                    break;
                case "PAGE_DOWN":
                    object.sendKeys(Keys.PAGE_DOWN);
                    break;
                case "PAGE_UP":
                    object.sendKeys(Keys.PAGE_UP);
                    break;
                default:
                    System.out.println("");
            }
        }
    }

    @Override
    public void mouseHover(String element)
    {
        Actions actions = new Actions(driver.get());
        WebElement elem=null;
        elem = waitElement(element,5);
        actions.moveToElement(elem).perform();
    }
}
