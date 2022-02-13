package ui.external;

import org.openqa.selenium.WebDriver;
import ui.BasePage;

public class BiziLoginPage extends BasePage {
    public BiziLoginPage(WebDriver driver){
        super(driver);
    }

    public String BiziHomePageUrl = "https://qa-cdn.bizi.co.il/#login";
    public String usernameInput = "//input[@name='username']";
    public String passwordInput = "//input[@name='password']";
    public String loginBtn = "//button[@id='dashboard-login-page-proceed-button']";

    String username = "Create_Big_Company@bizi.co.il";
    String password = "Aa!12345";

    public void getBiziHomePage(){
        driver.get(BiziHomePageUrl);
    }

    public void loginBizi(){
        sendTextToVisibleElement(usernameInput,username);
        sendTextToVisibleElement(passwordInput,password);
        clickVisible(loginBtn);
    }

}
