package ui.gigya;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ui.BasePage;
import ui.Reusable;

import static utilities.Common.*;

public class AmazonAWSPage extends BasePage {
    public AmazonAWSPage(WebDriver driver) {
        super(driver);
        environment = getFromRepository("environment", "gigyaLegacy","/general/environment");
        dataCenter = getFromRepository("dataCenter", "gigyaLegacy","/general/dataCenter");
        loginPageUrl = getFromRepository("loginPageUrl", "gigyaLegacy", "//loginUrlAWS");
        password = getFromRepository("password", "gigyaLegacy", "//amazonAWSPassword");
        if(environment.toLowerCase().contains("stag")) {
            apikey = getFromRepository("apikey", "gigyaLegacy", "//apikeyStaging");
            email = getFromRepository("email", "gigyaLegacy", "//amazonAWSEmail");
        }else {
            apikey = getFromRepository("apikey", "gigyaLegacy", "//apikeyProd");
            email = getFromRepository("email","gigyaLegacy", "//amazonAWSEmailProd");
        }
    }

    String loginPageUrl;
    String apikey;
    String environment;
    String dataCenter;
    String apiKeyInput = "//input[@id='apiKey']";
    Select selectEnv;
    Select selectDataCenter;
    String loadBtn = "//button[@type='submit']";
    String desktopLoginBtn = "//button[@id='submit-button']";
    String userNameInput = "//div[@class='gigya-screen-dialog-main']//input[@name='username']";
    String passwordInput = "//div[@class='gigya-screen-dialog-main']//input[@name='password']";
    String email;
    String password;
    String submitBtn = "//div[@class='gigya-screen-dialog-main']//input[@class='gigya-input-submit']";
    String openDABtn = "//button[@id='open-delegated-button']";


    public void getWebSite() {
        driver.get(loginPageUrl);
    }

    public void setInputs(){
        sendTextToVisibleElement(apiKeyInput,apikey);
        selectEnvironment(environment);
        selectDataCenter(dataCenter);
        clickVisibleAppearsLoop(loadBtn,desktopLoginBtn);
        wait(400);
        clickVisible(desktopLoginBtn);
        sendTextToVisibleElement(userNameInput,email);
        sendTextToVisibleElement(passwordInput,password);
        waitForElementToBeVisible(submitBtn);
        wait(200);
        clickVisible(submitBtn);
        waitForElementToBeVisible(openDABtn);
        wait(300);
        clickVisible(openDABtn);
    }

    public void loginDA(){
        getWebSite();
        setInputs();
    }


    public void selectEnvironment(String environment){
        waitForElementToBeVisible(stringFormat(textElement,environment));
        selectEnv = new Select(driver.findElement(By.id("env")));
        selectEnv.selectByVisibleText(environment);
        wait(200);
    }

    public void selectDataCenter(String dataCenter){
        waitForElementToBeVisible(stringFormat(textElement,dataCenter));
        selectDataCenter = new Select(driver.findElement(By.id("dataCenter")));
        selectDataCenter.selectByVisibleText(dataCenter);
        wait(200);
    }
}
