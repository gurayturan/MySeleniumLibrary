import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;

import java.io.IOException;
import java.net.MalformedURLException;

public interface MainSteps {

    void initializeDriver(String browser,Boolean isLocal) throws MalformedURLException;

    String seePage(String page);

    void openUrl(String url);

    WebElement findElement(String element1);

    WebElement waitElement(String element,int timeout);

    void clickElement(String element);

    void enterText(String text,String element);

    void clickKeyboard(String key);

    void clickKeyboardWithElement(String key,String element);

    void mouseHover(String element);
}
