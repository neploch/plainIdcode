package ui.external;

import org.openqa.selenium.WebDriver;
import ui.BasePage;

public class Cocktailale extends BasePage {
    public Cocktailale(WebDriver driver){
        super(driver);
    }

    String email = "inter@gmail.com";
    String password = "Aa123123";

    String cocktailaleUrl = "https://cocktailale.web.app/getStarted";
    String emailInputXpath = "//input[@id='email']";
    String passwordInputXpath = "//input[@id='password']";
    String submitBtnXpath = "//button[text()='Submit']";


    public void getCocktailaleLoginPage(){
        driver.get(cocktailaleUrl);
    }

    public void loginCocktailale(){
        sendTextToVisibleElement(emailInputXpath,email);
        sendTextToVisibleElement(passwordInputXpath,password);
        clickVisible(submitBtnXpath);
    }

}
