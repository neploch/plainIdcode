package ui.external;

import org.openqa.selenium.WebDriver;
import ui.BasePage;

public class CocktailaleInner extends BasePage {

    public CocktailaleInner(WebDriver driver){
        super(driver);
    }

    String interviewLabel = "//a[contains(@href,'me')]";

    public void inCocktailaleInner(){
        waitForElementToBeVisible(interviewLabel);
    }
}
