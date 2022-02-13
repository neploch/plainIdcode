package ui.partnerManagment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.Reusable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.Common.*;

public class Authorization extends Reusable {
    public Authorization(WebDriver driver) {
        super(driver);
        partners = new Partners(driver);
        downloadedTemplateFilePath = downloadsPath + File.separator + entitlementsTemplateFilename + ".csv";
        incompleteDetailsNotification = getRepValue("incompleteDetailsNotification");
    }

    Partners partners;

    public Map<String,String> appData;
    public Map<String,String> entitlementData;
    public Map<String,String> roleData;

    public String [] partnerVisibleAttributes = {"Name","Description", "BPID","Type", "Member limit", "Street address", "City", "State", "Zip code", "Country"};


    String entitlementsTemplateFilename = "import-entitlements-example";
    String incompleteDetailsNotification;

    public String applicationView = "//div[contains(@class,'ApplicationsView')]";
    public String rolesView = "//div[contains(@class,'RolesView')]";
    public String createNewApplicationBtn = applicationView + createBtn;
    public String createNewRoleBtn = rolesView + createBtn;
    public String newRoleAssetsSelectionStep = "//div[contains(@class,'StepsHeader')]//span[contains(@class, 'name')][text()='Assets Selection']";
    public By newApplicationTitle = By.xpath("//div[contains(@class,'title-container')]/h1[text()='New Application']");
    public By colorPicker = By.xpath("//div[contains(@class,'ColorPickerInput')]");
    public By colorPickerBody = By.xpath("//div[contains(@class,'saturation-black')]");
    public By browseFiles = By.xpath("//h2[contains(@class,'browse-files')]");
    public By entitlementsListPreviewTitle = By.xpath("//h1[contains(@class,'title') and contains(text(),'Entitlements List Preview')]");
    String entitlementsImportTableDataTmpl = "//div[contains(@class,'EntitlementsImportTable__RowStatusIndicator')]/..//p[contains(text(),'%s')]";
    public By reviewSelectedEntitlementsSubTitle = By.xpath("//h2[contains(text(),'Review Selected Entitlements')]");
    String definePolicyAvailability = "//h2[contains(text(),'Define Policy Availability')]";
    public String roleAllPartnersAvailabilityBtn = "//div[@data-test-id='ALL']";
    public String roleAllPartnersAvailabilityBtnSelected = roleAllPartnersAvailabilityBtn + selected;
    public By roleSelectedPartnersAvailabilityBtn = By.xpath("//div[@data-test-id='SELECTED']");
    public By roleNonePartnersAvailabilityBtn = By.xpath("//div[@data-test-id='NONE']");
    String roleDetailsTmpl = "//label[@for='%s']/../..//p[contains(text(),'%s')]";
    String searchApplicationBtn = applicationView + openBtn;
    String searchRoleBtn = rolesView + openBtn;
    String applicationItem ="//div[contains(@class,'application-item')]";
    String applicationItemName = applicationItem + "//p";
    String roleItem = "//div[contains(@class,'role-item')]";
    String roleItemName = roleItem + "//p";
    String searchBar = "//div[contains(@class,'search-bar')]";
    String searchInput = searchBar + input;
    String searchApplicationInput = applicationView + input;
    String searchRolesInput = rolesView + input;
    String operatorSmartInputControl = "//div[contains(@class,'OperatorField')]" + smartInputControl;
    String attributeSmartInputControl = "//div[contains(@class,'ItemField') and not(contains(@class,'OperatorField'))]" + smartInputControl;
    public String smartInputOptionNotEqual = "//div[contains(@class,'SmartInput__Option') and contains(@id,'option-1')]";


    public String createNewApplication(String application){
        waitElementAmountToBeMoreThan(applicationItem,0);
        wait(800);
        clickVisible(createNewApplicationBtn);
        waitForElementToBeVisible(newApplicationTitle);
        appData = fillApplicationRandomData();
        String name = appData.get("name");
        validateNameInTitle(name);
        clickCreateButton();
        closeToasts();
        entitlementData = fillEntitlement();
        String record = createEntitlementRecord();
        clickSaveButton();
        closeToasts();
        if(!application.toLowerCase().contains("gigya")) {
            downloadTemplate(entitlementsTemplateFilename);
            appendToCSVTemplate(downloadedTemplateFilePath, record);
            justSendTextToElement(inputFile, downloadedTemplateFilePath);
            waitForElementToBeVisible(entitlementsListPreviewTitle);
            clickUploadButton();
            Assert.assertTrue(waitForElementToBeVisible(successIcon, 15));
            clickDoneButton();
        }
        Assert.assertTrue(validateNameInTitle(name));
        clickBackButton();
        return name;
    }

    public void removeRedundantApplications(String application){
        if(waitElementAmountToBeMoreThan(applicationItem,0,4)){
            List<String> apps = getElementsListTexts(applicationItemName);
            for(String app : apps){
                if(!app.toLowerCase().contains("plain")){
                    deleteItem(application,"app",app);
                }
            }
        }
    }

    public void removeRedundantRoles(String application){
        if(waitElementAmountToBeMoreThan(roleItem,0,4)){
            List<String> roles = getElementsListTexts(roleItemName);
            for(String role : roles){
                if(!role.toLowerCase().contains("delegated")){
                    deleteItem(application,"role",role);
                }
            }
        }
    }

    public void deleteItem(String application, String type, String name){
        if(type.contains("app")) {
            searchApplication(application, name);
        }else if(type.contains("role")) {
            searchRole(application, name);
        }
        closeToasts();
        clickListItemByName(name);
        clickDeleteButton();
        clickAlertPrimaryBtn();
        Assert.assertTrue(waitForElementToBeVisible(toastDeletedSuccessfully,10));
    }

    public Map<String,String> createNewRole(String application){
        waitElementAmountToBeMoreThan(roleItem,0);
        wait(1000);
        clickVisible(createNewRoleBtn);
        roleData = fillRandomNameDescription();
        clickContinueButton();
        String selectedEntitlement = selectRandomRowCheckBox();
        entitlementData = getRoleEntitlementDetails(selectedEntitlement);
        clickContinueButton();
        if (application.toLowerCase().contains("partner")) {
            waitForElementToBeVisible(reviewSelectedEntitlementsSubTitle);
            clickContinueButton();
        }
        if (!getElementsList(newRoleAssetsSelectionStep).isEmpty()) {
            clickContinueButton();
        }
        clickVisible(roleAllPartnersAvailabilityBtn);
        clickDoneButton();
        validateNameInTitle(roleData.get("name"));
        Assert.assertTrue(validateRoleDetails(roleData,roleDetailsTmpl));
        roleData.put("roleName", roleData.remove("name"));
        clickBackButton();
        roleData.putAll(entitlementData);
        return roleData;
    }

    public void editApplication(String application){
        selectRandomApplication(application);
        clickEditButton();
        appData = fillApplicationRandomData();
        clickBackButton();
        selectApplication(application, appData.get("name"));
        validateApplicationData(appData);
    }

    public void uploadEmptyEntitlements(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        downloadTemplate(entitlementsTemplateFilename);
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        Assert.assertTrue(waitForElementToBeVisible(entitlementsListPreviewTitle),"Couldn't find title");
        Assert.assertTrue(validateErrorBarAppearance(),"Error bar not found");
        Assert.assertTrue(waitForElementContainingText(incompleteDetailsNotification),"Incomplete details notification not found");
    }

    public void uploadInvalidEntitlement(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        downloadTemplate(entitlementsTemplateFilename);
        String record = createInvalidEntitlementRecord();
        appendToCSVTemplate(downloadedTemplateFilePath,record);
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        clickUploadButton();
        Assert.assertTrue(validateToastErrorAppearance());
        Assert.assertTrue(validateToastMessageAppearance("invalid Entitlement"));
        closeToasts();
        Assert.assertTrue(validateErrorIconPresence());
        Assert.assertTrue(waitForElementContainingText(incompleteDetailsNotification));
        int initialRowsAmount = getElementsListAmount(tableRow);
        clickVisible(removeInvalidBtn);
        clickAlertPrimaryBtn();
        waitElementAmountToBe(tableRow,initialRowsAmount-1);
        int finalRowsAmount = getElementsListAmount(tableRow);
        Assert.assertEquals(finalRowsAmount, initialRowsAmount-1,"Could not see the invalid entitlement row removed from uploading table");
    }

    public void uploadEntitlementExistingName(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        downloadTemplate(entitlementsTemplateFilename);
        String record = createEntitlementRecord();
        appendToCSVTemplate(downloadedTemplateFilePath,record);
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        clickUploadButton();
        clickDoneButton();
        clickImportBtn();
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        clickUploadButton();
        Assert.assertTrue(validateFailureIconPresence());
        hoverOverVisibleElement(failureIcon);
        Assert.assertTrue(waitForElementContainingText(nameAlreadyExists));
    }

    public void editImportingEntitlement(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        downloadTemplate(entitlementsTemplateFilename);
        String record = createEntitlementRecord();
        appendToCSVTemplate(downloadedTemplateFilePath,record);
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        hoverOverVisibleElement(tableRow);
        clickEditButton();
        clearAllClearInputs();
        entitlementData = fillEntitlement();
        List<String> entitlementValues = getMapValues(entitlementData);
        clickSaveButton();
        clickUploadButton();
        clickDoneButton();
        tableData = getTableRowsTexts();
        Assert.assertTrue(isListInLists(entitlementValues,tableData),"Couldn't find new values in final entitlements table");
    }

    public void deleteImportingEntitlement(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        downloadTemplate(entitlementsTemplateFilename);
        String record = createEntitlementRecord();
        appendToCSVTemplate(downloadedTemplateFilePath,record);
        justSendTextToElement(inputFile,downloadedTemplateFilePath);
        hoverOverVisibleElement(tableRow);
        clickRemoveButton();
        clickAlertPrimaryBtn();
        Assert.assertTrue(waitElementAmountToBe(tableRow,0));
    }

    public void createEntitlement(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        clickCreateButton();
        entitlementData = fillEntitlement();
        clickSaveButton();
        List<String> entitlementValues = getMapValues(entitlementData);
        tableData = getTableRowsTexts();
        Assert.assertTrue(isListInLists(entitlementValues,tableData),"Couldn't find created entitlement values in final entitlements table");
        closeToasts();
        clickVisible(stringFormat(textElement,entitlementData.get("name")));
        clickDeleteButton();
        clickAlertPrimaryBtn();
        waitForElementToBeVisible(toastSuccess);
        closeToasts();
        waitForElementToDisappear(toastSuccess);
    }

    public void createEntitlementWithExistingName(String application){
        try {
            selectRandomApplication(application);
            clickListItemByName(Entitlements);
            tableData = getTableRowsTexts();
            String name = tableData.get(0).get(0);
            clickCreateButton();
            fillEntitlement("name", name);
            clickSaveButton();
            Assert.assertTrue(validateToastErrorAppearance());
        }catch (Throwable t){
                clickBackAndWait();
                createEntitlementWithExistingName(application);
        }
        Assert.assertTrue(validateToastErrorAppearance());
    }

    public void editEntitlement(String application){
        selectRandomApplication(application);
        clickListItemByName(Entitlements);
        clickRandomListElement(rowGroup);
        clickEditButton();
        entitlementData =  fillEntitlement();
        clickSaveAndWait();
        clickVisibleLoop(backBtn);
        List<String> entitlementValues = getMapValues(entitlementData);
        tableData = getTableRowsTexts();
        Assert.assertTrue(isListInLists(entitlementValues,tableData),"Couldn't find new values in final entitlements table");
    }

    public void createRoleWithExistingName(){
        String name = getRandomListItemName(roleItemName);
        clickVisible(createNewRoleBtn);
        insertName(name);
        clickContinueButton();
        Assert.assertTrue(validateToastErrorAppearance());
    }

    public Map<String,String> editRoleName(String application) {
        return editRoleName(application, "",true);
    }

    public Map<String,String> editRoleName(String application, String name) {
        return editRoleName(application, name, false);
    }

    public Map<String,String> editRoleName(String application, String name, boolean complete){
        if(name.length()<1) {
            selectRandomCompletedRole(application);
        }else {
            selectRole(application, name);
        }
        clickListItemByName(Details);
        clickEditButton();
        roleData = fillRandomNameDescription();
        clickSaveButton();
        data = getRoleEntitlement();
        if(complete) {
            clickBackButton();
            searchRole(application, roleData.get("name"));
            Assert.assertTrue(validateTextInElements(roleItemName, roleData.get("name")));
        }
        roleData.put("roleName", roleData.remove("name"));
        roleData.putAll(data);
        return roleData;
    }

    public Map<String,String> editRoleNameAndEntitlement(String application){
        roleData = editRoleName(application, "",false);
        clickListItemByName(Entitlements);
        clickEditButton();
        clickClearBtn();
        String selectedEntitlement = selectRandomRowCheckBox();
        entitlementData = getRoleEntitlementDetails(selectedEntitlement);
        clickVisibleLoop(saveBtn);
        checkRoleAvailability(application);
        clickBackButton();
        roleData.putAll(entitlementData);
        return roleData;
    }

    public void checkRoleAvailability(String application){
        if (application.equalsIgnoreCase("gigya")) {
            clickListItemByName(policyAvailability);
        } else {
            clickListItemByName(availability);
        }
        clickEditButton();
        if(!waitForElementToBeVisible(roleAllPartnersAvailabilityBtnSelected,4)){
            clickVisible(roleAllPartnersAvailabilityBtn);
            clickVisibleLoop(saveBtn);
        }
    }

    public void incompleteRole(String application){
        waitElementAmountToBeMoreThan(roleItem,0);
        wait(300);
        clickVisible(createNewRoleBtn);
        roleData = fillRandomNameDescription();
        clickContinueButton();
        cancelAndApprove();
        waitElementAmountToBeMoreThan(roleItem,0);
        wait(200);
        for(int i=0;i<3;i++){
            searchRole(application, roleData.get("name"));
            String css = stringFormat("div[data-test-id*=\"%s\"]",roleData.get("name"));
            if(waitForElementPresence(By.cssSelector(css),2)){
                String color = getPseudoElementValue(css,true,"background-color");
                if (application.equalsIgnoreCase("gigya")) {
                    Assert.assertTrue(color.contains(incompleteIconBackgroundColorGigya));
                }
                else {
                    Assert.assertTrue(color.contains(incompleteIconBackgroundColor));
                }
                break;
            }else {
                driver.navigate().refresh();
            }
        }
    }

    public void roleEntitlementSelections(String application){
        selectRandomCompletedRole(application);
        clickListItemByName(Entitlements);
        clickEditButton();
        String name = selectRandomRowCheckBox();
        clickVisibleLoop(saveBtn);
        tableData = getTableRowsTexts();
        Assert.assertTrue(isStringInLists(name,tableData));
        clickEditButton();
        String checkBx = stringFormat(rowNamedCheckBox,name);
        scrollElementIntoView(checkBx);
        clickVisible(checkBx);
        clickVisibleLoop(saveBtn);
        tableData = getTableRowsTexts();
        Assert.assertFalse(isStringInLists(name,tableData));
        clickEditButton();
        selectRandomRowCheckBox();
        selectRandomRowCheckBox();
        clickClearBtn();
        Assert.assertTrue(waitForElementToDisappear(selectedRow));
    }

    public void entitlementsReview(String application){
        selectRandomCompletedRole(application);
        clickListItemByName(Entitlements);
        clickEditButton();
        clickClearBtn();
        String name = selectRandomRowCheckBox();
        clickVisibleLoop(saveBtn);
        tableData = getTableRowsTexts();
        Assert.assertTrue(isStringInLists(name,tableData));
        clickVisibleLoop(editBtn);
        clickClearBtn();
        name = selectRandomRowCheckBox();
        clickVisibleLoop(saveBtn);
        tableData = getTableRowsTexts();
        Assert.assertTrue(isStringInLists(name,tableData));
    }

    public String roleAvailabilitySelectPartnersExplicitly(String application){
        String selectedRole = selectRandomCompletedRole(application);
        if (application.equalsIgnoreCase("gigya")) {
            clickListItemByName(policyAvailability);
        } else {
            clickListItemByName(availability);
        }
        clickEditButton();
        clickVisible(roleSelectedPartnersAvailabilityBtn);
        clickIfPresented(clearBtn);
        clickAllElements(By.xpath(removeBtn));
        wait(200);
        String availableForPartner = selectRandomRowCheckBox();
        clickVisibleLoop(saveBtn);
        waitForElementToDisappear(saveBtn,10);
        clickBackButton();
        partners.openPartnerDa(availableForPartner);
        return selectedRole;
    }

    public String roleAvailabilityByPartnersAttribute(String application, Map<String,String> map, boolean isPositive){
        String selectedRole = selectRandomCompletedRole(application);
        if (application.equalsIgnoreCase("gigya")) {
            clickListItemByName(policyAvailability);
        } else {
            clickListItemByName(availability);
        }
        clickEditButton();
        clickVisible(roleSelectedPartnersAvailabilityBtn);
        clickIfPresented(clearBtn);
        clickAllElements(By.xpath(removeBtn));
        wait(200);
        clickIfPresented(noItemsBtn);
        int attIndex = getStringIndexInArray(map.get("attribute"),partners.partnerAttributes);
        selectSmartOption(attributeSmartInputControl, partnerVisibleAttributes[attIndex]);
        insertAttributeValue(map.get("value"));
        if(!isPositive){
            selectSmartOption(operatorSmartInputControl, notEqual);
        }
        clickVisibleLoop(saveBtn);
        waitForElementToDisappear(saveBtn);
        clickBackButton();
        partners.openPartnerDa(map.get("name"));
        return selectedRole;
    }

    public String selectRandomApplication(String application){
        String name;
        do {
            name = getRandomListItemName(By.xpath(applicationItemName));
        }while (name.toLowerCase().contains("plainid"));
        searchApplication(application, name);
        clickListItemByName(name);
        return name;
    }

    public String selectRandomCompletedRole(String application){
        String name;
        do {
            name = getRandomElementNoPseudo(roleItem);
        }while (name.toLowerCase().contains("delegated"));
        return selectRole(application, name);
    }

    public String selectRole(String application, String name){
        searchRole(application, name);
        clickListItemByName(name);
        return name;
    }

    public boolean searchRole(String application, String name){
        waitElementAmountToBeMoreThan(roleItem,0);
        wait(800);
        if(application.toLowerCase().contains("partner")) {
            clickVisible(searchRoleBtn);
        }
        sendTextToVisibleElement(searchRolesInput,name);
        return waitForAttributeToContain(roleItem,dataTestId,name,2);
    }

    public void selectApplication(String application,String name){
        searchApplication(application,name);
        clickListItemByName(name);
    }

    public void searchApplication(String application, String name){
        if(application.toLowerCase().contains("partner")) {
            clickVisible(searchApplicationBtn);
        }
        sendTextToVisibleElement(searchApplicationInput, name);
    }

    public boolean validateRoleDetails(Map<String,String> data, String elementTmpl){
        for(Map.Entry<String, String> entry : data.entrySet()){
            String xpath = String.format(elementTmpl,entry.getKey(),entry.getValue());
            waitForElementToBeVisible(By.xpath(xpath));
        }
        return true;
    }


    public Map<String,String> fillApplicationRandomData(){
        waitForElementToBeVisible(logoUrl);
        String initialLogo = getElementValue(logoUrl);
        clearAllClearInputs();
        appData = new HashMap<>();
        appData.put("name",insertRandomName());
        appData.put("description",insertRandomDescription());
        if(initialLogo.isEmpty()) {
            appData.put("logoUrl", addLogo(trollPic));
        }else if (!initialLogo.contains(cherryPic)){
            appData.put("logoUrl", addLogo(cherryPic));
        }else {
            appData.put("logoUrl", addLogo(pineApplePic));
        }
        clickVisible(colorPicker);
        setColorPickerColors();
        clickAction(nameAttribute);
        clickSaveAndWait();
        return appData;
    }

    public void validateApplicationData(Map<String,String> data){
        clickEditButton();
        Assert.assertTrue(waitElementValueToBecome(nameAttribute,data.get("name")));
        Assert.assertTrue(waitElementValueToBecome(descriptionAttribute,data.get("description")));
        Assert.assertTrue(waitElementValueToBecome(logoUrl,data.get("logoUrl")));
    }

    public Map<String,String> fillEntitlement(){
        entitlementData = new HashMap<>();
        entitlementData.put("name",insertRandomName());
        entitlementData.put("description",insertRandomDescription());
        entitlementData.put("responseKey",insertRandomResponseKey());
        entitlementData.put("responseValue",insertRandomResponseValue());
        entitlementData.put("type",insertRandomType());
        entitlementData.put("classification",insertRandomClassification());
        return entitlementData;
    }

    public Map<String,String> fillEntitlement(String key, String value){
        waitForElementToBeVisible(nameAttribute);
        entitlementData = new HashMap<>();
        if(key.contentEquals("name")){
            entitlementData.put(key,insertName(value));
        }else {
            entitlementData.put("name",insertRandomName());
        }
        if(key.contentEquals("description")){
            entitlementData.put(key,insertDescription(value));
        }else {
            entitlementData.put("description",insertRandomDescription());
        }
        if(key.contentEquals("responseKey")){
            entitlementData.put(key,insertResponseKey(value));
        }else {
            entitlementData.put("responseKey",insertRandomResponseKey());
        }
        if(key.contentEquals("responseValue")){
            entitlementData.put(key,insertResponseValue(value));
        }else {
            entitlementData.put("responseValue",insertRandomResponseValue());
        }
        if(key.contentEquals("type")){
            entitlementData.put(key,insertType(value));
        }else {
            entitlementData.put("type",insertRandomType());
        }
        if(key.contentEquals("classification")){
            entitlementData.put(key,insertClassification(value));
        }else {
            entitlementData.put("classification",insertRandomClassification());
        }
        return entitlementData;
    }

    public void validateDataInTable(Map<String,String> data){
        for(Map.Entry<String, String> entry : data.entrySet()){
            String xpath = String.format(textElement,entry.getValue());
            Assert.assertTrue(waitForElementToBeVisible(By.xpath(xpath),10));
        }
    }


    public void validateImportedEntitlementsPreview(Map<String,String> data, String elementTmpl){
        for(Map.Entry<String, String> entry : data.entrySet()){
            String xpath = String.format(elementTmpl,entry.getValue());
            waitForElementToBeVisible(By.xpath(xpath));
        }
    }

    public String extractEntitlementRecord(Map<String,String> entitlementData){
        return entitlementData.get("name") + "," + entitlementData.get("description") + "," + entitlementData.get("responseKey") + "," +
                entitlementData.get("responseValue") + "," + entitlementData.get("type") + "," + entitlementData.get("classification");
    }

    public String createInvalidEntitlementRecord(){
        int [] requiredAttributes = {0,2,3};
        int toMiss = getRandomIntFromArray(requiredAttributes);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<6;i++){
            if(i!=toMiss){
                sb.append(randomStringGenerator(10));
            }
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();

    }

    public String createEntitlementRecord(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append(randomStringGenerator(10)).append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();

    }





}
