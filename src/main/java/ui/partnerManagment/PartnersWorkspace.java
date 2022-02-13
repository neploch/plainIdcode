package ui.partnerManagment;

import ui.Reusable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.*;

import static utilities.Common.*;

public class PartnersWorkspace extends Reusable {
    public PartnersWorkspace(WebDriver driver) {
        super(driver);
        partners = new Partners(driver);
        multiValueAtt = getRepValue("multiValueAtt");
    }

    Partners partners;

    Map<String,String> data;
    public String [] editablePartnerAttributes = {"City", "Country", "Street", "Zip", "State"};
    public String [] daFontTypes = {"Roboto", "Merriweather", "Fira Sans", "Nunito", "Poppins", "Ubuntu", "Roboto Slab", "Open Sans", "Lato"};
    public String partnersWorkspace = "//div[contains(@class,'PartnerWorkspace')]";

    public String pwSettingsBtn = partnersWorkspace + settingsBtn;
    public By pwSettingsTitle = By.xpath("//h1[contains(@class,'title') and contains(text(),'Partner Workspace Settings')]");
    public By partnersAttributes = By.xpath("//div[@data-test-id='Partners Attributes']");
    public By organizationsAttributes = By.xpath("//div[@data-test-id='Organizations Attributes']");
    public By daUiSettings = By.xpath("//div[contains(@data-test-id,'Look & Feel')]");
    public By settingsDetails = By.xpath("//div[@data-test-id='Details']");
    public By notificationsTemplatesList = By.xpath("//h2[text()='Notification Templates List']//ancestor::div[@class='header']//following::div[contains(@class,'list') and not(contains(@class, 'list-item'))]");
    public String listElementName = "//div[contains(@class,'ObjectList')]//div[contains(@class,'ListItem')]//*";
    public String listElementByName = listElementName + "[contains(text(),'%s')]";
    public By activeListElementName = By.xpath("//div[contains(@class,'ObjectList')]//div[contains(@class,'ListItem')]//i[contains(@class,'active')]");
    public String partnerManagementSettingsAttSection = "//h2[contains(@class,'title') and contains(text(),'Partner Management Settings')]";
    public String organizationManagementSettingsAttSection = "//h2[contains(@class,'title') and contains(text(),'Organization Management Settings')]";
    public String registrationManagementSettingsAttSection = "//h2[contains(@class,'title') and contains(text(),'Registration Management Settings')]";
    public String labelNamedPms = partnerManagementSettingsAttSection + up + up + up + up + "//label[@for='%s']";
    public String labelNamedOms = organizationManagementSettingsAttSection + "/../../..//label[contains(@for,'%s')]";
    public String labelNamedRms = registrationManagementSettingsAttSection + "/../../..//label[contains(@for,'%s')]";
    public String toggleLabelPms = labelNamedPms + up + up + "/div[@data-test-id='NO']";
    public String toggleLabelOms = labelNamedOms + "/../..//div[contains(@class,'toggle')]";
    public String labelCanBeUpdatedPms = labelNamedPms + up + up + "//div[@data-test-id='ALWAYS']" + selected;
    public String toggleIsInactivePms = labelNamedPms + "/../..//div[contains(@class,'toggle') and not (contains(@class,'active'))]";
    public String toggleIsInactiveOms = labelNamedOms + "/../..//div[contains(@class,'toggle') and not (contains(@class,'active'))]";
    public String toggleIsActivePms =  labelNamedPms + "/../..//div[contains(@class,'toggle') and contains(@class,'active')]";
    public String toggleIsActiveOms =  labelNamedOms + "/../..//div[contains(@class,'toggle') and contains(@class,'active')]";
    public String toggleIsDisabledPms = labelNamedPms + "/../..//div[contains(@class,'toggle') and @disabled]";
    public String toggleLabelRms = labelNamedRms + "/../..//div[contains(@class,'toggle')]";
    public String toggleIsInactiveRms = labelNamedRms + "/../..//div[contains(@class,'toggle') and not (contains(@class,'active'))]";
    public String toggleIsActiveRms =  labelNamedRms + "/../..//div[contains(@class,'toggle') and contains(@class,'active')]";
    public String toggleIsDisabledRms = labelNamedRms + "/../..//div[contains(@class,'toggle') and @disabled]";
    public By primaryColorInput = By.xpath("//label[@for='primaryColor']/../..//div[@class='input']");
    public By secondaryColorInput = By.xpath("//label[@for='secondaryColor']/../..//div[@class='input']");
    public By addlanguage = By.xpath("//div[contains(@class,'AddLanguage')]");
    public By languageLabelAvatar = By.xpath("//div[contains(@class,'LanguageLabel')]//span");
    public By communicationMemberAttributes = By.xpath("//div[contains(@class,'communication-view')]//div[@data-test-id='Members Attributes']");
    public By communicationDAUI = By.xpath("//div[contains(@class,'communication-view')]//div[contains(@data-test-id,'Delegated Admin')]");
    public By communicationMembersAndRegistrators = By.xpath("//div[contains(@class,'communication-view')]//div[contains(@data-test-id,'Members & Registrators Notifications')]");
    public String languageSelector = textElement + up + "//div[contains(@class,'avatar')]";
    public String languageActivated = languageSelector + up + up + up + vIcon;
    public String defaultLanguage = "(" + textElement + ")[last()]" + up + "//span[contains(@class,'default-label')]";
    String readOnly = "readOnly";
    String hidden = "hidden";
    String multiValue = "singleValue";
    String required = "required";
    String requestRequired = "requestRequired";
    String isRequired = "is required";
    public String canBeUpdated = "canBeUpdated";
    String multiValueAtt;
    public String attBlock = "//div[@class='form-fields-grid']";
    public String blockInput = "(" + attBlock + "//input" + ")[%d]";
    public String blockToolTip = "(" + attBlock + "//textarea" + ")[%d]";
    final int attBlockAmount = 6;
    String sectionGeneralSelector = "//div[@data-test-id='system']";
    String sectionAddressSelector = "//div[@data-test-id='main']";
    String workspaceIdField = "//*[contains(text(),'Workspace ID')]" + up + up + "//p";
    String environmentId = "//*[contains(text(),'Environment ID')]" + up + up + "//p";
    String identityAttribute = "//div[@class='identity-attribute']//h2";
    String requesterDetailsTitle = "//h2[contains(text(),'Requester Details')]";



    public void nameAttributeCantBeNumber(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        clickCreateButton();
        String number = randomNumberGenerator(10);
        sendTextToVisibleElement(nameAttribute,number);
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),nameCantBeANumberTxt));
    }


    public void openPartnersWorkspaceSettings(){
        clickVisibleLoop(pwSettingsBtn);
        waitForElementToBeVisible(pwSettingsTitle);
    }
    public void openPartnersAttributes(){
        clickVisible(partnersAttributes);
    }
    public void openOrganizationsAttributes(){
        clickVisible(organizationsAttributes);
    }
    public void openDaUiSettings(){
        clickVisible(daUiSettings);
    }

    public void openSettingsDetails(){
        clickVisible(settingsDetails);
    }

    public void canBeUpdated(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        String attName = openEditRandomEditableAttribute();
        if (application.equalsIgnoreCase("gigya")) {
            toggleOff(toggleIsActiveOms,toggleLabelOms,toggleIsInactiveOms,readOnly);
        }
        else {
            toggleOff(labelCanBeUpdatedPms,toggleLabelPms,toggleIsInactivePms,canBeUpdated);
        }
        closeToasts();
        getOutFromPartnerWorkspaceSettings();
        selectRandomListItemName(partners.partnerItem);
//        selectRandomScrollableListItemName(partners.partnersRowNames,selectLanguageScrollableCss);
        clickEditButton();
        scrollElementIntoView(By.xpath(String.format(attributeEditDisabled,attName.toLowerCase())));
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(String.format(attributeEditDisabled,attName.toLowerCase()))));
        clickCancelButton();
        clickBackAndWait();
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        waitElementAmountToBeMoreThan(activeListElementName,8);
        editSortableTemplateElementVisible(listElementByName,attName);
        if (application.equalsIgnoreCase("gigya")) {
            toggleOn(toggleIsActiveOms,toggleLabelOms,readOnly);
        }
        else {
            toggleOn(toggleIsActivePms,toggleLabelPms,readOnly);
        }
    }

    public void attLengthLimitations(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        openEditRandomEditableAttribute();
        clickStringBtn();
        clearElement(maxLengthInput);
        sendTextToVisibleElement(maxLengthInput,String.valueOf(randomNumberInRange(10000,1000000)));
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(fieldMessageError)),"Input validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),mustBeValueBetween1to9999),"Input length validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),"9999"),"String attribute upper length limitation 9999 was not found");
        clearElement(maxLengthInput);
        sendTextToVisibleElement(maxLengthInput,"0");
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(fieldMessageError)),"Input validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),mustBeValueBetween1to9999),"Input length validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt)," 1 "),"String attribute lower length limitation 1 was not found");
        clickNumberBtn();
        clearElement(maxValueInput);
        sendTextToVisibleElement(maxValueInput,"1214748364547");
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(fieldMessageError)),"Input validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),mustBeValueBetween1toMaxInteger),"Input length validation error was not found");

        clearElement(maxValueInput);
        sendTextToVisibleElement(maxValueInput,"0");
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(fieldMessageError)),"Input validation error was not found");
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),mustBeValueBetween1toMaxInteger),"Input length validation error was not found");
    }

    public void multiValuedAttImmutable(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        waitElementAmountToBeMoreThan(activeListElementName,8);
        clickTemplateElementVisible(listElementByName,multiValueAtt);
        clickEditButton();
        Assert.assertTrue(waitToggleIsDisabled(toggleIsDisabled,multiValue));
    }

    public void pmsRequired(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        String attName = openEditRandomEditableAttribute();
        if (application.equalsIgnoreCase("gigya")) {
            toggleOn(toggleIsActiveOms,toggleLabelOms,required);
            //insertRandomDefaultValue();
            //clickSaveAndWait();
        } else {
            toggleOn(toggleIsActivePms,toggleLabelPms,required);
        }
        getOutFromPartnerWorkspaceSettings();
        closeToasts();
        clickVisible(partners.createNewPartnerBtn);
        waitForElementToBeVisible(partners.newPartnerTitle);
        clickVisible(memberLimitAttribute);
        wait(400);
        scrollElementIntoView(requesterDetailsTitle);
        waitElementAmountToBeMoreThan(clearInputBtn,3);
        clearAllClearInputs();
        Assert.assertTrue(waitForElementToBeVisible(By.xpath(fieldMessageError)),"Input validation error was not found");
        Assert.assertTrue(validateTextInElements(By.xpath(fieldMessageErrorTxt),attName),"Input attribute validation error was not found");
        Assert.assertTrue(validateTextInElements(By.xpath(fieldMessageErrorTxt),isRequired),"Input required validation error was not found");
        cancelAndApprove();
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        waitElementAmountToBeMoreThan(activeListElementName,8);
        editSortableTemplateElementVisible(listElementByName,attName);
        toggleOff(toggleIsActivePms,toggleLabelPms,toggleIsInactivePms,required);
    }

    public Map<String,String> toggleAttribute(String application, String attName){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        if(attName.length()>1){
            openEditableAttribute(attName);
        }else {
            attName = openEditRandomEditableAttribute();
        }
        String section = toggleSelectors(sectionAddressSelector,sectionGeneralSelector);
        section = (section.contains(sectionGeneralSelector)) ? "General" : "Address";
        clickSaveAndWait();
        data = new HashMap<>();
        data.put("attName",attName);
        data.put("section",section);
        getOutFromPartnerWorkspaceSettings();
        return data;
    }

    public String turnRmsRequiredOff(String application){
        openPartnersWorkspaceSettings();
        if (application.equalsIgnoreCase("gigya")) {
            openOrganizationsAttributes();
        }
        else {
            openPartnersAttributes();
        }
        String attName = openEditRandomEditableAttribute();
        toggleOff(toggleIsActiveRms,toggleLabelRms,toggleIsInactiveRms,requestRequired);
        closeToasts();
        return attName;
    }

    public String turnRmsRequiredON(String attName){
        closeToasts();
        openPartnersWorkspaceSettings();
        openPartnersAttributes();
        openEditableAttribute(attName);
        toggleOn(toggleIsActiveRms,toggleLabelRms,requestRequired);
        closeToasts();
        return attName;
    }

    public void daColorsAndFont(){
        setDaColorsAndFont();
        getOutFromPartnerWorkspaceSettings();
        partners.openDa();
    }

    //Add new language, set it as default, set English back as default, deactivate and remove added language
    public void addLanguage(String application){
        openPartnersWorkspaceSettings();
        clickVisible(addlanguage);
        String language = selectRandomListItemNameInDiv(languageLabelAvatar,"div.html-tooltip .scrollable").split(" ")[0];
        String addedLanguageAvatar = String.format(languageSelector,language);
        clickVisible(addedLanguageAvatar);
        clickVisible(communicationDAUI);
        fillAllRequiredInputsRandomData(5);
        clickVisible(addedLanguageAvatar);
        clickVisible(communicationMemberAttributes);
        fillAllRequiredInputsRandomData(1);
        clickVisible(addedLanguageAvatar);
        if (application.equalsIgnoreCase("gigya")) {
            clickVisible(communicationMembersAndRegistrators);
            waitForElementToBeVisible(notificationsTemplatesList);
            List<String> notificationTemplatesList = getListItems(notificationsTemplatesList);
            for (String template : notificationTemplatesList) {
                clickListItemByName(template);
                fillAllRequiredInputsRandomData(3);
                clickVisible(communicationMembersAndRegistrators);
            }
            clickBackAndWait();
            clickVisible(addedLanguageAvatar);
        }
        scrollElementIntoView(activateBtn);
        clickActivateButton();
        String activeLanguage = String.format(languageActivated,language);
        Assert.assertTrue(waitForElementToBeVisible(activeLanguage));
        String languageAbbreviationXpath = "(" + addedLanguageAvatar + "//span)[last()]";
        String languageAbbreviation = getVisibleElementTextStrip(languageAbbreviationXpath);
        selectSmartOption(languageAbbreviation);
        String setAsDefaultLanguage = String.format(defaultLanguage,language);
        Assert.assertTrue(waitForElementToBeVisible(setAsDefaultLanguage));
        selectSmartOption("EN");
        waitForElementToDisappear(setAsDefaultLanguage);
        clickVisible(addedLanguageAvatar);
        clickDeactivateButton();
        Assert.assertTrue(waitForElementToDisappear(activeLanguage));
        deleteAndConfirm();
        waitForElementToDisappear(addedLanguageAvatar);
    }

    public Map<String,String [] > fillDaDisplayData(){
        openPartnersWorkspaceSettings();
        clickVisible(communicationMemberAttributes);
        waitForElementToBeVisible(editBtn);
        wait(200);
        clickEditButton();
        waitElementAmountToBe(By.xpath(identityAttribute),attBlockAmount);
        wait(1000);
        List<String>attributes = getElementsListTexts(identityAttribute);
        Map<String, String []> data = new HashMap<>();
        for (String attribute :attributes){
            String block = stringFormat(textElement,attribute.strip())  + up;
            clearLocalInputs(block);
            String [] pair = fillDataPair(block);
            data.put(attribute.strip(),pair);
        }
        clickSaveAndWait();
        return data;
    }

    public String [] fillDataPair(String xpath){
        String [] pair = new String[2];
        if(xpath.contains("email") || xpath.contains("login")) {
            pair[0] = randomEmailGenerator();
        }else{
            pair[0] = randomStringGenerator(10);
        }
        pair [1] = randomStringGenerator(8);
        clearLocalInputs(xpath);
        sendTextToElement(xpath + input, pair[0]);
        sendTextToElement(xpath + textarea, pair[1]);
        return pair;
    }

    public Map<String,String> setDaStrings(){
        openPartnersWorkspaceSettings();
        clickVisible(communicationDAUI);
        waitForElementToBeVisible(editBtn);
        wait(200);
        clickEditButton();
        String cherry = "Cherry";
        String pineApple = "PineApple";
        String youTube = "YouTube";
        String eBay = "eBay";
        data = new HashMap<>();
        String logoPic = setValueToOneOfTwo(logoUrl,cherryPic,pineApplePic);
        String logo = (isInitialFirst(logoUrl,cherryPic,pineApplePic))?pineApple:cherry;
        String url = setValueToOneOfTwo(returningUrl,youTubeUrl,eBayUrl);
        String site = (isInitialFirst(returningUrl,youTubeUrl,eBayUrl))?eBay:youTube;
        String titleTxt = logo + " " + site + " Delegated Admin";
        String subTitleTxt = "Enjoy our " + site;
        sendTextToElement(displayName,logo);
        sendTextToElement(title,titleTxt);
        sendTextToElement(returningUrlString, site);
        sendTextToElement(subTitle,subTitleTxt);
        data.put("logo",logo);
        data.put("logoPic",logoPic);
        data.put("titleTxt",titleTxt);
        data.put("returningUrlTxt", site);
        data.put("subTitleTxt",subTitleTxt);
        data.put("url",url);
        clickSaveAndWait();
        return data;
    }

    public Map<String, String> setDaColorsAndFont(){
        openPartnersWorkspaceSettings();
        openDaUiSettings();
        clickEditButton();
        clickVisible(primaryColorInput);
        String primaryRGB = setColorPickerColors();
        clickVisible(secondaryColorInput);
        String secondaryRGB = setColorPickerColors();
        String font = selectRandomFontOption(daFontTypes);
        clickSaveAndWait();
        data = new HashMap<>();
        data.put("primaryRGB",primaryRGB);
        data.put("secondaryRGB",secondaryRGB);
        data.put("font",font);
        return data;
    }

    public Map<String, String> getWorkspaceId(Map<String, String> map){
        openPartnersWorkspaceSettings();
        openSettingsDetails();
        String workspaceId = getVisibleElementTextStrip(workspaceIdField);
        map.put("workspaceId", workspaceId);
        String envId = getVisibleElementTextStrip(environmentId);
        map.put("envId", envId);
        getOutFromPartnerWorkspaceSettings();
        return map;
    }

    public String getRandomEditableAttribute(){
        return getRandomStringFromArray(editablePartnerAttributes);
    }

    public String openEditRandomEditableAttribute(){
        String attName = getRandomEditableAttribute();
        waitElementAmountToBeMoreThan(activeListElementName,8);
        editSortableTemplateElementVisible(listElementByName,attName);
        return  attName;
    }

    public String openEditableAttribute(String attName){
        waitElementAmountToBeMoreThan(activeListElementName,8);
        editSortableTemplateElementVisible(listElementByName,attName);
        return  attName;
    }


    public void getOutFromPartnerWorkspaceSettings(){
        clickVisibleLoop(backBtn);
        clickVisibleLoop(backBtn);
    }



}
