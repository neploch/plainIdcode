package ui.external;

import org.openqa.selenium.WebDriver;
import ui.BasePage;

public class WallaFinance extends BasePage {
    public WallaFinance(WebDriver driver)
    {
        super(driver);
    }

    String wallaFinanceUrl = "https://finance.walla.co.il/";
    String USDRateXpath = "//li[.//h3[contains(text(),'דולר $')]]//div[@class='rate']";

    public void waitPageLanded(){
//        sendEscapeKey();
        waitForUrlContains(wallaFinanceUrl);
    }

    public double getUSDRate(){
        return Double.parseDouble(getVisibleElementText(USDRateXpath));
    }
}
