package ui.partnerManagment;

import org.openqa.selenium.WebElement;
import ui.Reusable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.Common.*;


public class Partners extends Reusable {
    public Partners(WebDriver driver) {
        super(driver);
        application = getRepValue("application");
        IDP = getRepValue("IDP");
    }

    public Map<String,String> partnerData;
    public Map<String,String> data;
    public String [] partnerAttributes = {"name","description", "external_id","type", "member_limit", "street_address", "city", "state", "zip_code", "country"};
    String deletePartner = "Delete Partner";
    String deleteOrganization = "Delete Organization";
    String declineRequest = "Decline Request";
    String suspendPartner = "Suspend Partner";
    String suspendOrganization = "Suspend Organization";
    String activatePartner = "Activate Partner";
    String activateOrganization = "Activate Organization";
    String partnerDeletedSuccessfully = "Partner deleted successfully";
    String organizationDeletedSuccessfully = "Organization deleted successfully";
    String declinedSuccessfully = "declined successfully";
    String application;
    String IDP;


    public String partnerItem = "//div[contains(@class,'partner-item')]";
    public String partnerItemNamed = partnerItem + textElement;
    public String requestItem = "//div[contains(@class,'partnerrequest')]";
    public String requestItemNamed = requestItem + textElement;
    public String partnersView = "//div[contains(@class,'PartnersView')]";
    public String registrationRequestsTab = "//div[@data-test-id='Registration-Requests']";
    public String partnersTab = "//div[@data-test-id='Partners']";
    public String partnersCounter = partnersTab +"//span[contains(@class,'Counter')]";
    public String registrationRequestsCounter = registrationRequestsTab + "//span[contains(@class,'Counter')]";
    public String declineBtnTmpl = listItemNamedTmpl + "/.." +declineBtn;
//    public By approveRegistrationRequestBtn = By.xpath("//button[contains(@class,'create')]");
    public By registrationRequestTitle = By.xpath("//div[contains(@class,'title-container')]/h1");
    public By newPartnerTitle = By.xpath("//div[contains(@class,'title-container')]/h1[contains(text(),'New ')]");
    public By partnersListTitle = By.xpath("//div[contains(@class,'title-container')]/h1[text()='Partners List']");
    public By organizationsListTitle = By.xpath("//div[contains(@class,'title-container')]/h1[text()='Organizations List']");
    public By titleStatusApproved = By.xpath("//div[contains(@class,'title-container')]//div[@class='title-children']//*[@status='APPROVED']");
    public By registrationRequestStatusRegistrationRequest = By.xpath("//div[contains(@class,'title-container')]//div[@class='title-children']//*[@status='REGISTRATION_REQUEST']");
    String partnerNamedTmpl = partnersView + listItemNamedTmpl;
    String partnerNamedRow = partnersView + rowNamed;
    String approvedPartner = partnerItemNamed + "/../.." + statusApproved;
    String activePartnerName = row + statusApproved + "/../../../../div/span";
    public By alertMessage = By.xpath("//div[contains(@class,'Alert')]//*[contains(@class,'message')]");
    public String createNewPartnerBtn = partnersView + createBtn;
    public String createNewPartnerBtnId = partnersView + createBtnTestId;
    public String partnersRowNames = partnersView + row + "//span[contains(@class,'cell')]";
    public By fullViewBtn = By.xpath("//button[contains(@class,'full-table-button')]");

    String partnerDetailsTmpl = "//label[@for='attributes.%s']/../..//p[contains(text(),'%s')]";
    public String topNotice = "//div[contains(@class,'top-notice')]";
    public String topNoticeTxt = topNotice + "//p";
    public String partnerNameAlreadyInUseTxt = "Partner name already in use";
    public String partnerIdField = "((//div[contains(@class,'TableRowDiv')])[1]//div[contains(@class,'TableRowCell')])[3]";
//    public String partnerRow = row + partnerItem;
//    public String requestRow = row + requestItem;
    public String totalMembers = "//span[contains(text(),'Total Members')]";



    public String approveRegistrationRequest(String name, Boolean delete){
        selectPartnersTab();
        waitForTextInElement(partnersCounter,"(");
        int initialPartnersAmount = extractIntFromElement(partnersCounter);
        selectRegistrationRequestsTab();
        String requestNamed = stringFormat(requestItemNamed,name);
        clickVisibleTry(requestNamed);
        waitForElementToBeVisible(registrationRequestStatusRegistrationRequest);
        waitForTextInElement(registrationRequestTitle,name);
        clickCreateButton();
        waitForElementToBeVisible(newPartnerTitle);
//        fillOktaRequesterRandomData(true);
        clickSaveButton(1000);
        waitForTextInElement(registrationRequestTitle,name);
        waitFofTitleStatusApproved();
        validateNameInTitle(name);
        String url = driver.getCurrentUrl();
        String partnerId = url.split("/")[10];
        clickVisibleTry(backBtn);
        selectPartnersTab();
        closeToasts();
        Assert.assertTrue(validateActivePartner(name),String.format("Could not find %s activated member in partners tab",name));
        if(delete) {
            clickHoverableBtnLoop(name, deleteBtn);
            String xpath;
            if (application.toLowerCase().contains("partner")) {
                xpath = stringFormat(textElement, deletePartner);
            } else {
                xpath = stringFormat(textElement, deleteOrganization);
            }
            waitForElementToBeVisible(xpath);
            waitForTextInElement(alertMessage, name);
            clickAlertPrimaryBtn();
            waitForElementToDisappear(requestNamed);
            Assert.assertTrue(waitForTextInElement(partnersCounter, Integer.toString(initialPartnersAmount)), stringFormat("Could not find initial partners amount %d after deleting the partner", initialPartnersAmount));
        }
        return partnerId;
    }

    public void removeRedundantPartners(){
        waitForTextInElement(partnersCounter,"(");
        waitElementAmountToBeMoreThan(partnerItem,0,3);
        wait(200);
        while (getElementsListAmount(partnerItem)>1){
            String name = "";
            do {
                name = getRandomListItemName(partnersRowNames);
            }while (name.startsWith("__"));
            deletePartner(name);
        }
    }

    public void removeRedundantRegistrationRequests(){
        selectRegistrationRequestsTab();
        waitElementAmountToBeMoreThan(requestItem,0,3);
        wait(200);
        while (getElementsListAmount(requestItem)>4){
            String name = getRandomListItemName(requestItem);
            name = name.split("\n")[0];
            deleteRegistrationRequest(name);
            selectRegistrationRequestsTab();
        }
        selectPartnersTab();
    }

    public String suspendPartner(String application){
        selectPartnersTab();
        activatePartners(application);
        String name = "__";//getRandomListItemName(By.xpath(activePartnerName));
        clickHoverableBtnLoop(name,suspendBtn);
        String title;
        if(application.contains("partner")) {
            title = stringFormat(textElement, suspendPartner);
        }else {
            title = stringFormat(textElement, suspendOrganization);
        }
        waitForElementToBeVisible(title);
        clickAlertPrimaryBtn();
        Assert.assertTrue(waitForRowItemState(name,statusSuspended),String.format("Could not detect partner %s suspended",name));
        return name;
    }

    public void activateSuspendedPartner(String name, String application){
        selectPartnersTab();
        String xpath = stringFormat(textElement,name);
        waitForElementToBeVisible(xpath);
        clickHoverableBtnLoop(name,activateBtn);
        String title;
        if(application.contains("partner")) {
            title = stringFormat(textElement, activatePartner);
        }else {
            title = stringFormat(textElement, activateOrganization);
        }
        waitForElementToBeVisible(title);
        clickAlertPrimaryBtn();
        waitForRowItemState(name,statusApproved);
        wait(1000);
        activatePartners(application);
    }

    public void declinePartnerRegistrationRequest(String name){
        selectRegistrationRequestsTab();
        waitForElementToBeVisible(registrationRequestsCounter);
        int initialRequestsAmount = extractIntFromElement(registrationRequestsCounter);
        clickHoverableBtnLoop(name,declineBtn);
        String title = stringFormat(textElement,declineRequest);
        waitForElementToBeVisible(title);
        clickAlertPrimaryBtn();
        waitForElementToDisappear(By.xpath(String.format(listItemNamedTmpl,name)));
        Assert.assertTrue(waitForTextInElement(registrationRequestsCounter,String.valueOf(initialRequestsAmount-1)),"Could not find one registration element less in counter");
    }

    public void editRegistrationRequest(String name){
        selectRegistrationRequestsTab();
        editPartnerElement(name);
        declineAndConfirm();
    }

    public Map<String, String> createNewPartner(){
        return createNewPartner(false);
    }

    public Map<String, String> createNewPartner(boolean requester){
        String actualCreateNewPartnerBtn = waitForOneOfTwoElements(createNewPartnerBtn,createNewPartnerBtnId,10);
        clickVisibleTry(actualCreateNewPartnerBtn);
        waitForElementToBeVisible(newPartnerTitle);
        partnerData = fillPartnerRandomData();
        if(requester) {
            if(IDP.toLowerCase().contains("okta")) {
                data = fillOktaRequesterRandomData(false);
            }else if(IDP.toLowerCase().contains("auth0")) {
                data = fillAuth0RequesterRandomData();
            }
            partnerData.putAll(data);
        }
        String name = partnerData.get("name");
        clickSaveButton();
        waitFofTitleStatusApproved();
        validateNameInTitle(name);
        String url = driver.getCurrentUrl();
        String partnerId = url.split("/")[10];
        partnerData.put("partnerId",partnerId);
        clickVisibleTry(backBtn);
        validateActivePartner(name);
        return partnerData;
    }

    public void validatePartnerDetails(Map<String,String> partnerData){
        selectPartnersTab();
        String name = partnerData.get("name");
        String partner = stringFormat(textElement,name);//  getRowItem(name);
        String suspendText = stringFormat(textElement,suspendPartner);
        clickVisibleAppearsLoop(partner,suspendText);
        validateNameInTitle(name);
        Assert.assertTrue(validatePartnerData(partnerData));
        clickBackButton();
    }

    public void deletePartner(String name){
        selectPartnersTab();
        closeToasts();
        waitElementAmountToBeMoreThan(partnerItem,0);
        String partner = stringFormat(textElement,name);
        clickVisible(By.xpath(partner));
        validateNameInTitle(name);
        clickDeleteButton();
        clickAlertPrimaryBtn();
        if(application.toLowerCase().contains("partner")) {
            validateToastMessageAppearance(partnerDeletedSuccessfully);
        }else {
            validateToastMessageAppearance(organizationDeletedSuccessfully);
        }
        waitElementAmountToBeMoreThan(partnerItem,0);
        closeToasts();
        waitForElementToDisappear(toastSuccess);
        wait(1000);
    }

    public void deleteRegistrationRequest(String name){
        String rowItem = stringFormat(textElement,name);
        clickVisible(By.xpath(rowItem));
        clickDeclineButton();
        clickAlertPrimaryBtn();
        validateToastMessageAppearance(declinedSuccessfully);
        wait(1000);
    }

    public void editPartner(){
        waitElementAmountToBeMoreThan(partnerItem,0);
        String name = getRandomListItemName(partnerItem);
        editPartnerElement(name);
    }

    public void validatePartnerFieldsLengths(){
        waitElementAmountToBeMoreThan(partnerItem,0);
        String name = getRandomListItemName(partnerItem);
        editPartnerFieldsMaxLength(name);
    }

    public void openPartnersFullView(){
        waitElementAmountToBeMoreThan(partnerItem,0);
        int partnersAmount = extractIntFromElement(partnersCounter);
        List<String> partnersNames = getElementsListTexts(partnerItem);
        clickOnElement(fullViewBtn);
        if(application.toLowerCase().contains("partner")) {
            waitForElementToBeVisible(partnersListTitle);
        }else {
            waitForElementToBeVisible(organizationsListTitle);
        }
        waitElementAmountToBeMoreThan(By.xpath(rowGroup),partnersAmount-1);
        int tableRows = getElementsListAmount(By.xpath(rowGroup));
        Assert.assertEquals(tableRows,partnersAmount +1,"Full view table rows amount found not equals to the amount of partners on main page");
        Assert.assertTrue(validateTextsListInElements(partnersNames,By.xpath(tableCell)));
    }

    public void validateExistingNameInRegistrationRequest(String name){
        selectRegistrationRequestsTab();
        clickRowItemByName(name);
        waitForElementToBeVisible(By.xpath(topNotice));
        Assert.assertTrue(waitForTextInElement(By.xpath(topNoticeTxt),partnerNameAlreadyInUseTxt),String.format("Expected notification %s was not found",partnerNameAlreadyInUseTxt));
        declineAndConfirm();
        waitForElementToBeVisible(partnersTab);
    }

    public void editPartnerElement(String name){
        String rowItem = stringFormat(textElement,name);
        String suspendEl = stringFormat(textElement,suspendPartner);
        clickVisibleAppearsLoop(rowItem,suspendEl);
//        clickRowItemByName(name);
        clickEditButton();
        waitElementAmountToBeMoreThan(clearInputBtn,2);
        partnerData = fillPartnerRandomData(name);
        clickSaveButton();
        validatePartnerData(partnerData);
        Assert.assertTrue(validateNameInTitle(partnerData.get("name")));
    }

    public void editPartnerFieldsMaxLength(String name){
        String item = stringFormat(textElement,name);
        String suspendEl = stringFormat(textElement,suspendPartner);
        clickVisibleAppearsLoop(item,suspendEl);
        clickEditButton();
        waitElementAmountToBeMoreThan(clearInputBtn,2);
        partnerData = fillPartnerMaxLengthData(name);
        clickSaveButton();
        validatePartnerMaxData(partnerData);
        Assert.assertTrue(validateNameInTitle(partnerData.get("name")));
    }

    public void setPartnerAttribute(String name, String attName, String attVal){
        clickRowItemByName(name);
        clickVisibleTry(editBtn);
        waitElementAmountToBeMoreThan(clearInputBtn,2);
        setPartnerAttribute(attName,attVal);
        clickVisibleDisappear(saveBtn);
        clickBackButton();
    }

    public Map<String,String> getRandomPartnerRandomAttributeValue(){
        String name = "__";//getRandomListItemName(partnersRowNames);
        clickRowItemByName(name);
        String attribute = getRandomStringFromArray(partnerAttributes);
        String partnerAttributeTextTmpl = "//label[contains(@for,'attributes.%s')]/../..//p";
        String attValue = stringFormat(partnerAttributeTextTmpl,attribute);
        String value = getVisibleElementText(attValue);
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("attribute",attribute);
        map.put("value",value);
        clickBackButton();
        return map;
    }

    public void validateAttributeIsInSection(Map<String,String> data){
        String name = getRandomListItemName(partnersRowNames);
        clickRowItemByName(name);
        clickEditButton();
        String expectedElement = stringFormat(textElement+up+up+up+inputName,data.get("section"),data.get("attName").toLowerCase());
        Assert.assertTrue(waitForElementPresence(expectedElement));
        clickCancelButton();
        clickBackButton();
    }

    public void openDa(){
        selectPartnersTab();
        String name = getSpecialListItemName(partnerItem,"__");
        openPartnerDa(name);
    }

    public void openPartnerDa(String name){
        clickHoverableBtnLoop(name,accessDABtn);
        switchToNewWindow();
    }

    public Map<String, String> getPartnerId(Map<String, String> map){
        clickVisible(fullViewBtn);
        String partnerId = getVisibleElementTextStrip(partnerIdField);
        map.put("partnerId",partnerId);
        clickBackButton();
        return map;
    }

    public Map<String,String> fillOktaRequesterRandomData(boolean approvingRequest){
        if(!approvingRequest) {
            scrollElementIntoView(toggleBtn);
            clickVisibleAppearsLoop(toggleBtn, lastNameAttribute);
        }else {
            scrollElementIntoView(departmentAttribute);
        }
        data = new HashMap<>();
        data.put("lastName",insertRandomLastName());
        String email = getGuerrillaEmail();
        data.put("login",insertLogin(email));
        data.put("firstName",insertRandomFirstName());
        data.put("email",insertEmail(email));
        data.put("department",insertRandomDepartment());
        data.put("jobFunction",insertRandomJobFunction());
        return data;
    }

    public Map<String,String> fillAuth0RequesterRandomData(){
        scrollElementIntoView(toggleBtn);
        clickVisibleAppearsLoop(toggleBtn,lastNameAttribute);
        data = new HashMap<>();
        String password = getRepValue("passWord");
        data.put("password",insertPasswordAttribute(password));
        String email = getGuerrillaEmail();
        data.put("email",insertEmail(email));
        data.put("given_name",insertRandomGivenName());
        data.put("family_name",insertRandomFamilyName());
        data.put("department",insertRandomDepartment());
        data.put("jobFunction",insertRandomJobFunction());
        return data;
    }

    public Map<String,String> fillPartnerRandomData() {
        return fillPartnerRandomData("");
    }

    public Map<String,String> fillPartnerRandomData(String name){
        partnerData = new HashMap<>();
        if(name.contains("__")) {
            partnerData.put("name", insertModifiedName("__"));
        }else {
            waitForElementToBeVisible(nameAttribute);
            wait(200);
            partnerData.put("name", insertRandomName());
        }
        partnerData.put("description",insertRandomDescription());
        partnerData.put("external_id",insertRandomExternalId());
        partnerData.put("type", selectRandomTypeFirstUpperCase(application));
        partnerData.put("member_limit",insertRandomMemberLimit());
        pageDown();
        wait(900);
        partnerData.put("zip_code",insertRandomZipCode());
        pageDown();
        wait(900);
        partnerData.put("state",insertRandomState());
        pageDown();
        wait(100);
        pageDown();
        wait(900);
        pageDown();
        wait(400);
        partnerData.put("street_address",insertRandomStreetAddress());
        wait(400);
        pageDown();
        wait(900);
        partnerData.put("city",insertRandomCity());
        pageDown();
        wait(900);
        partnerData.put("country",insertRandomCountry());
        return partnerData;
    }

    public Map<String,String> fillPartnerMaxLengthData(String name){
        partnerData = new HashMap<>();
        if(name.contains("__")) {
            partnerData.put("name", insertModifiedMaxName("__"));
        }else {
            waitForElementToBeVisible(nameAttribute);
            wait(200);
            partnerData.put("name", insertRandomMaxName());
        }
        partnerData.put("description",insertRandomMaxDescription());
        partnerData.put("external_id",insertRandomMaxExternalId());
        partnerData.put("type", selectRandomTypeFirstUpperCase(application));
        partnerData.put("member_limit",insertRandomMaxMemberLimit());
        Assert.assertTrue(waitForTextInElement(By.xpath(fieldMessageErrorTxt),valueIsTooLong),"Member limit input length validation error was not found");
        partnerData.put("member_limit",insertRandomMemberLimit());
        pageDown();
        wait(900);
        partnerData.put("zip_code",insertRandomMaxZipCode());
        pageDown();
        wait(900);
        partnerData.put("state",insertRandomMaxState());
        pageDown();
        wait(100);
        pageDown();
        wait(900);
        pageDown();
        wait(400);
        partnerData.put("street_address",insertRandomMaxStreetAddress());
        wait(400);
        pageDown();
        wait(900);
        partnerData.put("city",insertRandomMaxCity());
        pageDown();
        wait(900);
        partnerData.put("country",insertRandomMaxCountry());
        return partnerData;
    }

    public Map<String,String> fillMissingPartnerData(String exceptedAtt){
        partnerData = new HashMap<>();
        if(!"name".contains(exceptedAtt)){
            partnerData.put("name",insertRandomName());
        }else if(!"description".contains(exceptedAtt)){
            partnerData.put("description",insertRandomDescription());
        }else if (!"external_id".contains(exceptedAtt)){
            partnerData.put("external_id",insertRandomExternalId());
        }else if (!"type".contains(exceptedAtt)){
            partnerData.put("type",selectRandomTypeFirstUpperCase(application));
        }else if(!"member_limit".contains(exceptedAtt)){
            partnerData.put("member_limit",insertRandomMemberLimit());
        }else if (!"street_address".contains(exceptedAtt)){
            partnerData.put("street_address",insertRandomStreetAddress());
        }else if (!"city".contains(exceptedAtt)){
            partnerData.put("city",insertRandomCity());
        }else if (!"state".contains(exceptedAtt)){
            partnerData.put("state",insertRandomState());
        }else if (!"zip_code".contains(exceptedAtt)){
            partnerData.put("zip_code",insertRandomZipCode());
        }else if (!"country".contains(exceptedAtt)){
            partnerData.put("country",insertRandomCountry());
        }
        return partnerData;
    }

    public void setPartnerAttribute(String attName, String attVal){
        if(attName.contains("name")){
            sendTextToVisibleElement(nameAttribute,attVal);
        }else if(attName.contains("description")){
            sendTextToVisibleElement(descriptionAttribute,attVal);
        }else if (attName.contains("external_id")){
            sendTextToVisibleElement(externalIdAttribute,attVal);
        }else if(attName.contains("member_limit")){
            sendTextToVisibleElement(memberLimitAttribute,attVal);
        }else if(attName.contains("street_address")){
            sendTextToVisibleElement(streetAddressAttribute,attVal);
        }else if(attName.contains("city")){
            sendTextToVisibleElement(cityAttribute,attVal);
        }else if(attName.contains("state")){
            sendTextToVisibleElement(stateAttribute,attVal);
        }else if(attName.contains("zip_code")){
            sendTextToVisibleElement(zipCodeAttribute,attVal);
        }else if(attName.contains("country")){
            sendTextToVisibleElement(countryAttribute,attVal);
        }
    }

    public boolean validatePartnerMaxData(Map<String,String> data){
        for(Map.Entry<String, String> entry : data.entrySet()){
            String xpath = "";
            if(entry.getKey().contains("zip_code")){
                xpath = String.format(partnerDetailsTmpl,entry.getKey(),entry.getValue().substring(0,20));
            }else if(entry.getKey().contains("member_limit") || entry.getKey().contains("type")){
                xpath = String.format(partnerDetailsTmpl,entry.getKey(),entry.getValue());
            }else {
                xpath = String.format(partnerDetailsTmpl, entry.getKey(), entry.getValue().substring(0,200));
            }
            scrollElementIntoView(By.xpath(xpath));
            waitForElementToBeVisible(By.xpath(xpath));
        }
        return true;
    }

    public boolean validatePartnerData(Map<String,String> data){
        for(Map.Entry<String, String> entry : data.entrySet()){
            if(!entry.getKey().contains("partnerId")) {
                String xpath = String.format(partnerDetailsTmpl, entry.getKey(), entry.getValue());
                scrollElementIntoView(By.xpath(xpath));
                waitForElementToBeVisible(By.xpath(xpath));
            }
        }
        return true;
    }

    public void selectPartnersTab(){
        clickVisibleAppearsLoop(partnersTab,partnersCounter);
    }

    public void activatePartners(String application){
        waitElementAmountToBeMoreThan(partnerItem,0);
        wait(200);
        List<WebElement> partners = getElementsList(partnerItem);
        for(int i=0;i<partners.size() && i<5; i++){
            if(partners.get(i).findElements(By.xpath("."+ statusApproved)).isEmpty()){
                String name = partners.get(i).getText().split("\n")[0];
                activateSuspendedPartner(name,application);
            }
        }

    }


    public void selectRegistrationRequestsTab(){
        clickVisibleAppearsLoop(registrationRequestsTab,partnersCounter);
    }


    public void waitFofTitleStatusApproved(){
        waitForElementToBeVisible(titleStatusApproved);
    }

    public boolean validateActivePartner(String name){
        String activePartnerActiveStatus = String.format(approvedPartner,name);
        waitForElementPresence(By.xpath(activePartnerActiveStatus));
        return true;
    }

    public int getTotalMembers(){
        String text = getVisibleElementTextStrip(totalMembers);
        String numberOnly= text.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }



}
