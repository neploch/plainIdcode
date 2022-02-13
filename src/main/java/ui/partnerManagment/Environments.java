package ui.partnerManagment;

import ui.Reusable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static utilities.Common.*;

public class Environments extends Reusable {

    public Environments(WebDriver driver){
        super(driver);
        environment = getRepValue("environment");
        IDP = getRepValue("IDP");
        tenantName = getRepValue("tenantName");
        partners = new Partners(driver);
        OktaIDPApiKey = getRepValue("OktaIDPApiKey");
        OktaIDPClientId = getRepValue("OktaIDPClientId");
        OktaIDPClientSecret = getRepValue("OktaIDPClientSecret");
        OktaIDPIssuer = getRepValue("OktaIDPIssuer");
        OktaIDPAuthorizationUrl = getRepValue("OktaIDPAuthorizationUrl");
        OktaIDPGroupId = getRepValue("OktaIDPGroupId");
        OktaIDPPartnerClaimName = getRepValue("OktaIDPPartnerClaimName");
        OktaIDPTokenUrl = getRepValue("OktaIDPTokenUrl");
        OktaIDPJwksUrl = getRepValue("OktaIDPJwksUrl");
        oktaMetaDataUrl = getRepValue("oktaMetaDataUrl");
        auth0ClientId = getRepValue("auth0ClientId");
        auth0ClientSecret = getRepValue("auth0ClientSecret");
        auth0MetaDataUrl = getRepValue("auth0MetaDataUrl");
        auth0DomainName = getRepValue("auth0DomainName");
        auth0Audience = getRepValue("auth0Audience");
    }
    Partners partners;

    Map<String,String> map;

    String environment;
    String IDP;
    String OktaIDPApiKey;
    String OktaIDPClientId;
    String OktaIDPClientSecret;
    String OktaIDPIssuer;
    String OktaIDPAuthorizationUrl;
    String OktaIDPGroupId;
    String OktaIDPPartnerClaimName;
    String OktaIDPTokenUrl;
    String OktaIDPJwksUrl;
    String oktaMetaDataUrl;
    String auth0ClientId;
    String auth0ClientSecret;
    String auth0MetaDataUrl;
    String auth0DomainName;
    String auth0Audience;

    String tenantName;

    public String tenantTitle = "//div[@class='header']//h1";
    public String environmentNameTemplate = "//h2[contains(text(),'%s')]";
    public String environmentName = "//div[contains(@class,'EditableLabel')]//h2";
    public String siteGroupName = "//div[contains(@class, 'tab-item')]";
    public String envTabTmp = environmentNameTemplate + up + up;
    public String envTab;
    public String addEnvBtn = "//div[contains(@class,'tabs')]" + createBtn;
    public String createEnvForm = "//div[contains(@class,'CreateEnv')]";
    public String createEnvInput = "//div[contains(@class,'create-env-input')]" + input;
    public String tab = "//div[contains(@class,'Tab-')]";
    public String editEnvInput = tab+input;
    public String tabNamed = tab + titleTextElement;
    public String createEnvBtn = createEnvForm + createBtn;
    public String envSettings = tab + settingsBtnId;
    public String siteGroupOptions = "//div[contains(@class, 'tab-item')]/div[@class='toolbox']";
    public String settingsOption = "//span[text()='Settings']";
    public By settingsDetails = By.xpath("//div[@data-test-id='Details']");
    public String tabImg = tab + img;
    public By IDTSettings = By.xpath("//div[@data-test-id='Partners Identity Provider (IDP)']");
    public String IDTSelectOkta = "//div[@data-test-id='OKTA']" + segmentBtn;
    public String IDTSelectAuth0 = "//div[@data-test-id='AUTH0']" + segmentBtn;
    public By authorizationKeys = By.cssSelector("div[data-test-id='Authorization Keys']");
    public By details = By.cssSelector("div[data-test-id='Details']");
    public String envClientIdField = "//*[contains(text(),'Client ID')]" + up + up + "//p";
    public String envSecretKeyValue = "//div[contains(@class,'KeyValue')]//input";
    public String envSecretKeyValueLast = "(//div[contains(@class,'KeyValue')]//input)[last()]";
    public String environmentIdField = "//label[contains(text(),'Environment ID')]" + up + up + "//p";
    public String addSecretBtn = "//div[contains(@class,'add-button')]//button";
    public String clientSecretsInput = "//input[contains(@name,'clientSecrets')]";


    public void selectEnvironment(String envName){
        envTab = String.format(envTabTmp,envName);
        wait(500);
        clickVisibleDelay(By.xpath(envTab));
    }

    public String createEnvironment(){
        return createEnvironment("");
    }

    public String createEnvironment(String name){
        name = (name.length()>0)?name:randomStringGenerator(12);
        waitElementAmountToBeMoreThan(tab,0);
        closeElement(gotItBtn);
        clickVisible(addEnvBtn);
        clearAllClearInputs();
        sendTextToElement(createEnvInput,name);
        clickVisible(createEnvBtn);
        String createdTab = stringFormat(tabNamed,name);
        closeToasts();
        closeElement(gotItBtn);
        waitForElementToBeVisible(createdTab);
        waitForElementToBeVisible(toastSuccess);
        Assert.assertTrue(waitForElementToBeVisible(toastCreatedSuccessfully));
        return name;
    }

    public void editEnvironment(){
        waitElementAmountToBeMoreThan(environmentName,0);
        wait(300);
        closeElement(gotItBtn);
        clickRandomListElement(environmentName);
        closeElement(gotItBtn);
        jsClickIfPresented(editIcon);
        String name = randomStringGenerator(10);
        waitForElementToBeClickable(editEnvInput);
        sendTextAction(name);
        clickVisible(tenantTitle);
        Assert.assertTrue(waitForTextInElements(environmentName,name),stringFormat("Could not find tab with %s name",name));
    }

    public void createEnvironmentWithExistingName(){
         String name = getRandomListItemName(environmentName);
         createEnvironment(name);
    }
    public void editEnvDetails(String application){
        waitElementAmountToBeMoreThan(partners.activePartnerName,0, 6);
        if (application.equalsIgnoreCase("gigya")) {
            clickVisible(siteGroupOptions);
            clickVisible(settingsOption);
        }
        else {
            clickVisible(envSettings);
        }
        clickVisible(settingsDetails);
        clickEditButton();
        map = fillRandomNameDescription();
        String logoPic = setValueToOneOfTwo(logoUrl,cherryPic,pineApplePic);
        clickVisibleDisappear(saveBtn);
        clickBackAndWait();
        clickBackButton();
        if (application.equalsIgnoreCase("gigya")) {
            Assert.assertTrue(waitForTextInElements(siteGroupName,map.get("name")));
        }
        else {
            Assert.assertTrue(waitForTextInElements(environmentName,map.get("name")));
            Assert.assertTrue(waitForAttributeToContainRefresh(tabImg,"src",logoPic));
        }
    }

    public void setEnvIDP(){
        clickVisible(envSettings);
        clickVisible(IDTSettings);
        clickCreateButton();
        if(IDP.toLowerCase().contains("okta")) {
            fillOktaIDPForm();
        }else if(IDP.toLowerCase().contains("auth")) {
            fillAuth0IDPForm();
        }
        clickBackAndWait();
        clickBackAndWait();
    }

    public Map<String, String> setIDP(){
        closeElement(gotItBtn);
//        setEnvIDP();
        map = getEnvRTParams();
        clickBackAndWait();
        clickBackButton();
        return map;
    }

    public Map<String, String> getEnvRTParams(){
        map = new HashMap<>();
        clickVisibleTry(envSettings);
        clickVisible(authorizationKeys);
        clickVisibleAppearsLoop(editBtn,cancelBtn);
//        clickEditButton();
//        clickAddButton();
//        waitForElementToBeVisible(regenerateBtn);
        clickVisible(regenerateBtn);
        String secret = getElementValue(envSecretKeyValue);
        String envClientId = getElementTextStrip(envClientIdField);
        clickVisibleDisappear(saveBtn);

        map.put("secret",secret);
        map.put("envClientId",envClientId);
        return map;
    }

    public String getEnvironmentId(){
        clickVisibleTry(envSettings);
        clickVisible(details);
        String environmentId = getVisibleElementTextStrip(environmentIdField);
        clickVisible(logo);
        return environmentId;
    }

    public String regenerateSecret(){
        clickVisible(envSettings);
        clickVisible(authorizationKeys);
        clickEditButton();
        clickRegenerateButton();
        String secret = getElementValue(envSecretKeyValue);
        clickVisibleDisappear(saveBtn);
        clickBackAndWait();
        clickBackAndWait();
        return secret;
    }

    public String addSecret(){
        waitForElementPresence(envSettings);
        clickVisibleTry(envSettings);
        clickVisible(authorizationKeys);
        clickEditButton();
        waitForElementToBeVisible(regenerateBtn);
        scrollElementIntoView(addSecretBtn);
        clickVisible(addSecretBtn);
        String secret = getElementValue(envSecretKeyValueLast);
        clickVisibleDisappear(saveBtn);
        clickBackAndWait();
        clickBackAndWait();
        return secret;
    }

    public String removeSecret(){
        clickVisible(envSettings);
        clickVisible(authorizationKeys);
        clickEditButton();
        waitForElementToBeVisible(regenerateBtn);
        while (getElementsListAmount(removeBtn)>1){
            clickVisible(clientSecretsInput,2);
            scrollPageUp();
            try {
                clickVisible(removeBtn,2);
            }catch (Throwable t){
                jsClick(removeBtn);
            }
            clickAlertPrimaryBtn();
            wait(300);
        }
        clickRegenerateButton();
        String secret = getElementValue(envSecretKeyValue);
//        String lastRemoveBtn = stringFormat(lastElement,removeBtn);
//        while (getElementsListAmount(removeBtn)>1) {
//            pageDown();
//            wait(400);
//            clickAction(lastRemoveBtn);
//            clickAlertPrimaryBtn();
//            waitElementAmountToBe(removeBtn, 1,5);
//            wait(1000);
//        }
        wait(1000);
        clickVisibleLoop(saveBtn);
        clickBackAndWait();
        clickBackAndWait();
        return secret;
    }

    public void fillOktaIDPForm(){
        waitForElementToBeVisible(IDPDisplayName);
        insertIDPDisplayName("OKTA");
        clickVisible(IDTSelectOkta);
        insertApiKey(OktaIDPApiKey);
        insertGroupId(OktaIDPGroupId);
        insertPartnerClaimName(OktaIDPPartnerClaimName);
        clickVisible(partnerClaimName);
        scrollElementIntoView(IDPClientId);
        wait(100);
        scrollElementIntoView(IDPClientId);
        insertIDPClientId(OktaIDPClientId);
        insertIDPClientSecret(OktaIDPClientSecret);
        insertIDPMetaDataUrl(oktaMetaDataUrl);
        clickVisible(importDataBtn);
        waitForElementToBeVisible(toastSuccess);
        closeToasts();


//        insertIDPTokenUrl(OktaIDPTokenUrl);
//        insertIDPJwksUrl(OktaIDPJwksUrl);
//        insertIDPIssuer(OktaIDPIssuer);
        clickSaveAndWait();
        waitForElementToBeVisible(toastSuccess);
        closeToasts();
    }

    public void fillAuth0IDPForm(){
        waitForElementToBeVisible(IDPDisplayName);
        insertIDPDisplayName("Auth0");
        clickVisible(IDTSelectAuth0);
        insertIDPDomainNameUrl(auth0DomainName);
        insertIDPAudienceUrl(auth0Audience);
        clickVisible(IDPAudienceUrl);
//        wait(1000);
        scrollElementIntoView(IDPClientSecret);
        wait(100);
        scrollElementIntoView(IDPClientSecret);
        insertIDPClientId(auth0ClientId);
        insertIDPClientSecret(auth0ClientSecret);
//        scrollElementIntoView(IDPDomainNameUrl);
        insertIDPMetaDataUrl(auth0MetaDataUrl);
        clickVisible(importDataBtn);
        waitForElementToBeVisible(toastSuccess);
        closeToasts();
        clickSaveAndWait();
        waitForElementToBeVisible(toastSuccess);
        closeToasts();
    }

}
