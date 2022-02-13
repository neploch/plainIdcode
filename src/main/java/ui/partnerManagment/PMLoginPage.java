package ui.partnerManagment;

import ui.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utilities.Common.*;

public class PMLoginPage extends BasePage {

    public PMLoginPage(WebDriver driver) {
        super(driver);
        environment = getRepValue("environment");
        tenantName = getRepValue("tenantName");
        loginPageUrl = getRepValue("loginPageUrl");
        baseUrl = getRepValue("baseUrl");
        envDomain = getRepValue("envDomain");
        envDatacenter = getRepValue("envDatacenter");
        baseUrl = String.format(baseUrl,envDatacenter,envDomain);
        loginPageUrl = stringFormat(loginPageUrl,tenantName,baseUrl);
    }

    Environments env = new Environments(driver);


    String loginPageUrl;
    String environment;
    String baseUrl;
    String envDomain;
    String envDatacenter;
    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginBtn = By.id("kc-login");
    String tenantName;

    public void getWebSite() {
        driver.get(loginPageUrl);
    }

    public void login(String userName, String password){
        waitForElementToBeVisible(usernameField,30);
        sendTextToElement(usernameField,userName);
        sendTextToElement(passwordField,password);
        clickOnElement(loginBtn);
        env.waitForTextInElement(env.tenantTitle,tenantName);
    }

    public void getLogin(String userName, String password){
        getWebSite();
        login(userName,password);
    }


}
