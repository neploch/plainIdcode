package ui.gigya;

import org.openqa.selenium.WebDriver;
import ui.Reusable;

import static utilities.Common.*;

public class GigyaLoginPage extends Reusable {
    public GigyaLoginPage(WebDriver driver) {
        super(driver);
        environment = getFromRepository("environment","gigyaLegacy","/general/environment");
        if(environment.toLowerCase().contains("stag")) {
            loginPageUrl = getFromRepository("loginPageUrl","gigyaLegacy", "//loginUrlStaging");
            username = getFromRepository("username","gigyaLegacy", "//usernameStaging");
            password = getFromRepository("password","gigyaLegacy", "//passwordStaging");
        }else {
            loginPageUrl = getFromRepository("loginPageUrl","gigyaLegacy", "//loginUrlProd");
            username = getFromRepository("username","gigyaLegacy", "//usernameProd");
            password = getFromRepository("password","gigyaLegacy", "//passwordProd");
        }
    }

    String environment;
    String loginPageUrl;
    String username;
    String password;
    String usernameInput = "//div[@id='screen-set-container_content']//input[@name='username']";
    String passwordInput = "//div[@id='screen-set-container_content']//input[@name='password']";
    String signInBtn = "//div[@id='screen-set-container_content']//input[@class='gigya-input-submit']";
    String tfaCodeInput = "//input[@class='gig-tfa-code-textbox']";
    String tfaSubmitBtn = "//div[@class='gig-tfa-button gig-tfa-button-submit']";

    public void getWebSite() {
        driver.get(loginPageUrl);
    }

    public void login(){
        waitForElementToBeVisible(usernameInput);
        sendTextToVisibleElement(usernameInput,username);
        sendTextToVisibleElement(passwordInput,password);
        clickVisible(signInBtn);
        sendTextToVisibleElement(tfaCodeInput,randomNumberGenerator(6));
        clickVisible(tfaSubmitBtn);
    }

    public void doLogin(){
        getWebSite();
        login();
    }

}
