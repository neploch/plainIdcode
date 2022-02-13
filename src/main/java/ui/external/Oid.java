package ui.external;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.Reusable;

import java.util.Map;

import static utilities.Common.getRepValue;

public class Oid extends Reusable {
    public Oid(WebDriver driver) {
        super(driver);
        application = getRepValue("application");
    }
    String application;
    String oidUrl = "https://oidcdebugger.com/";
    String authorizeUrlInput = "//input[@id='authorizeUri']";
    String clientIdInput = "//input[@id='clientId']";
    String stateInput = "//input[@id='state']";
    String idTokenInput = "//input[@id='responseType-id_token']";
    String submitBtn = "//a[contains(@class,'debug__form-submit--button')]";

    public void fillOidData(String IDP){
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        switchToNewWindow();
        driver.get(oidUrl);
        String authorizeUrl = "";
        String clientId = "";
        if(IDP.toLowerCase().contains("okta")){
            authorizeUrl = getRepValue("OktaIDPAuthorizationUrl");
            clientId = getRepValue("OktaIDPClientId");
        }else if(IDP.toLowerCase().contains("auth0")){
            authorizeUrl = getRepValue("OAuthAuthorizationUrl");
            clientId = getRepValue("auth0ClientId");
        }
        clickVisible(authorizeUrlInput);
        sendTextToVisibleElement(authorizeUrlInput,authorizeUrl);
        clickVisible(clientIdInput);
        sendTextToVisibleElement(clientIdInput,clientId);
        sendTextToVisibleElement(stateInput,"kuku");
        clickVisible(idTokenInput);
        clickVisible(submitBtn);
    }

    public void assertRoleEntitlementIsInTokenResponse(Map<String,String> map){
        String responseKey = map.get("responseKey");
        String responseValue = map.get("responseValue");
        String expectedXpath = "//div[@title='ID token (decoded)']//*[contains(text(),'%s') and contains(text(),'%s')]";
        expectedXpath = stringFormat(expectedXpath, responseKey,responseValue);
        Assert.assertTrue(waitForElementToBeVisible(expectedXpath,10));
    }
}
