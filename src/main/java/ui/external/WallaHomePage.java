package ui.external;

import org.openqa.selenium.WebDriver;
import ui.Reusable;

public class WallaHomePage extends Reusable {
    public WallaHomePage (WebDriver driver){
        super(driver);
    }
    String wallaHomePageUrl = "https://www.walla.co.il/";
    String wallaFinanceNavigationLink = "//div[@class='nav']//a[contains(@href,'finance')]";


    public void openWalla(){
        driver.get(wallaHomePageUrl);
        waitForElementToBeVisible(wallaFinanceNavigationLink);
    }

    public void openWallaFinance(){
        clickVisible(wallaFinanceNavigationLink);
    }
}
