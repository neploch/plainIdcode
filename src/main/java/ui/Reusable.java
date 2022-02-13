package ui;
import api.Apis;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.github.sukgu.*;

import static utilities.Common.*;

public class Reusable extends BasePage{
    public Reusable(WebDriver driver) {
        super(driver);
    }

    public List<List<String>> tableData;
    public Map<String,String> data;
    public String common = "common";
    public String general = "general";
    public String email = "email";
    public String welcome = "welcome";
    public String reset = "reset";
    public String jobFunction = "jobFunction";
    public String department = "department";
    public String firstName = "firstName";
    public String login ="login";
    public String phone = "phone";
    public String lastName = "lastName";
    public String dataTestId = "data-test-id";
    public String equal = "equal";
    public String notEqual = "NOT_EQUALS";
    public String delegatedAdminRoleName = "Delegated admin";
    public String emailVerified = "Email Verified";
    public String up = "/..";
    public String cssByAttributeTemplate = "[%s='%s']";
    public String lastElement = "(" + "%s" + ")[last()]";
    public String incompleteIconBackgroundColor = "rgb(255, 25, 67)";
    public String incompleteIconBackgroundColorGigya = "rgb(187, 0, 0)";

    public final String downloadsPath = System.getProperty("user.home") + File.separator +  "Downloads";
    public String downloadedTemplateFilePath;
    String plainidCom = "plainid.com";
    public String selectLanguageScrollableCss = "div.LanguagesList";

    public String trollPic = getRepValue("trollPic");
    public String cherryPic = getRepValue("cherryPic");
    public String pineApplePic = getRepValue("pineApplePic");
    public String eBayUrl = getRepValue("eBayUrl");
    public String youTubeUrl = getRepValue("youTubeUrl");
    public String passWord = getRepValue("passWord");

    public String accessDABtn = "//button[contains(@class,'access-da-button')]";
    public String deleteBtn = "//*[contains(@class,'delete-button')]";
    public String declineBtn = "//*[contains(@class,'decline-button')]";
    public String suspendBtn = "//*[contains(@class,'suspend-button')]";
    public String activateBtn = "//*[contains(@class,'activate-button')]";
    public String deactivateBtn = "//*[contains(@class,'deactivate-button')]";
    public String closeBtn = "//*[contains(@class,'close-button')]";
    public String statusApproved = "//*[@status='APPROVED']";
    public String statusSuspended = "//*[@status='SUSPENDED']";
    public String openBtn = "//button[contains(@class,'open-button')]";
    public String searchBtn = "//*[contains(@class,'search-button')]";
    public String createBtn = "//button[contains(@class,'create-button')]";
    public String editBtn = "//button[contains(@class,'edit-button')]";
    public String editIcon = "//i[contains(@class,'edit-icon')]";
    public String removeBtn = "//button[contains(@class,'remove-button')]";
    public String emailSubmitBtn = "//button[contains(@class,'email-submit')]";
    public String clearBtn = "//button[contains(@class,'clear-button')]";
    public String settingsBtn = "//button[contains(@class,'settings-button')]";
    public String settingsBtnId = "//button[@data-test-id='settings-button']";
    public String createBtnTestId = "//button[@data-test-id='create-button']";
    public String noItemsBtn = "//button[contains(@class,'no-items-button')]";
    public String addBtn = "//button[contains(@class,'add-button')]";
    public String successIcon = "//i[contains(@class,'success')]";
    public String toggleBtn = "//button[contains(@class,'toggle-button')]";
    public String segmentBtn = "[contains(@class,'SegmentButton')]";

    public String listItem = "//div[contains(@class,'ListItem')]";
    public String listItemByNameParent = "//div[contains(@class,'list-item') and .//*[contains(text(),'%s')]]";
    public String listItemNamedTmpl = listItem + textElement;
    public String listIemName = listItem + "//p";
    public String tooltipHelpBtn = "//span[contains(@class,'help-button')]";
    public String htmlTooltip = "//*[contains(@class,'html-tooltip')]";
    public String selected = "[contains(@class,'selected')]";

    public String vIcon = "//i[@type='v-sign']";
    public String inputName = "//input[contains(@name,'%s')]";
    public String labelNamed = "//label[contains(@name,'%s')]";
    public String labelFor = "//label[contains(@for,'%s')]";
    public By nameAttribute = By.xpath("//input[contains(@name,'name')]");
    public By descriptionAttribute = By.xpath("//textarea[contains(@name,'description')]");
    public By departmentAttribute = By.xpath("//input[contains(@name,'department')]");
    public By defaultValueAttribute = By.xpath("//input[contains(@name,'defaultValue')]");
    public By jobFunctionAttribute = By.xpath("//input[contains(@name,'jobFunction')]");
    public By firstNameAttribute = By.xpath("//input[contains(@name,'firstName')]");
    public By givenNameAttribute = By.xpath("//input[contains(@name,'given_name')]");
    public String lastNameAttribute = "//input[contains(@name,'lastName')]";
    public By familyNameAttribute = By.xpath("//input[contains(@name,'family_name')]");
    public By passwordAttribute = By.xpath("//input[contains(@name,'password')]");
    public By emailAttribute = By.xpath("//input[contains(@name,'email')]");
    public By loginAttribute = By.xpath("//input[contains(@name,'login')]");
    public By externalIdAttribute = By.xpath("//input[@name='attributes.external_id']");
    public String typeSelectorTmpl = "//div[contains(@class,'SelectControl')]//span[contains(text(),'%s')]/..";
    public By memberLimitAttribute = By.xpath("//input[@name='attributes.member_limit']");
    public By streetAddressAttribute = By.xpath("//input[@name='attributes.street_address']");
    public By cityAttribute = By.xpath("//input[@name='attributes.city']");
    public By stateAttribute = By.xpath("//input[@name='attributes.state']");
    public By phoneNumberAttribute = By.xpath("//input[@name='phone']");
    public By zipCodeAttribute = By.xpath("//input[@name='attributes.zip_code']");
    public By countryAttribute = By.xpath("//input[@name='attributes.country']");
    public By logoUrl = By.xpath("//input[contains(@name,'logoUrl')]");
    public By responseKey = By.xpath("//input[contains(@name,'responseKey')]");
    public By responseValue = By.xpath("//input[contains(@name,'responseValue')]");
    public By type = By.xpath("//input[contains(@name,'type')]");
    public By classification = By.xpath("//input[contains(@name,'classification')]");
    public By displayName = By.xpath("//input[contains(@name,'displayName')]");
    public By title = By.xpath("//input[contains(@name,'title')]");
    public By subTitle = By.xpath("//input[contains(@name,'subTitle')]");
    public By returningUrlString = By.xpath("//input[contains(@name,'returningUrlString')]");
    public By returningUrl = By.xpath("//input[@name='data.returningUrl']");
    public By attributeValue = By.xpath("//input[contains(@name,'attributeValue')]");
    public String attributeName = "//*[contains(@class,'attribute-name')]";

    public By IDPDisplayName = By.cssSelector("input[name='identityProvider.displayName']");
    public By apiKey = By.cssSelector("input[name='apiKey']");
    public By groupId = By.cssSelector("input[name='groupId']");
    public By partnerClaimName = By.cssSelector("input[name='partnerClaimName']");
    public By IDPClientId = By.cssSelector("input[name='identityProvider.clientId']");
    public By IDPClientSecret = By.cssSelector("input[name='identityProvider.clientSecret']");
    public By IDPTokenUrl = By.cssSelector("input[name='identityProvider.tokenUrl']");
    public By IDPJwksUrl = By.cssSelector("input[name='identityProvider.jwksUrl']");
    public By IDPIssuer = By.cssSelector("input[name='identityProvider.issuer']");
    public String IDPMetaDataUrl = "//input[@name='url']";
    public String IDPDomainNameUrl = "//input[@name='identityProvider.attributes.domainUrl']";
    public String IDPAudienceUrl = "//input[@name='identityProvider.attributes.audience']";



    public String nameInTitle = "//h1[contains(@class,'title') and contains(text(),'%s')]";
    public By getStartedBtn = By.xpath("//button[contains(@class,'get-started-button')]");

    public String backBtn = "//*[contains(@class,'back-button')]";
//    public By declineBtn = By.xpath("//button[contains(@class,'decline-button')]");

    public String saveBtn = "//button[contains(@class,'save-button')]";
    public By continueBtn = By.xpath("//button[contains(@class,'continue-button')]");
    public By uploadBtn = By.xpath("//button[contains(@class,'upload-button')]");
    public By doneBtn = By.xpath("//button[contains(@class,'done-button')]");
    public String cancelBtn = "//button[contains(@class,'cancel-button')]";
    public By importBtn = By.xpath("//button[contains(@class,'import-button')]");
    public By downloadBtn = By.xpath("//button[contains(@class,'download-button')]");
    public By removeInvalidBtn = By.xpath("//button[contains(@class,'remove-invalid-button')]");
    public By regenerateBtn = By.xpath("//button[contains(@class,'regenerate-button')]");
    public String importDataBtn = "//button[contains(@class,'ImportButton')]";




    public By alertPrimaryBtn = By.xpath("//button[contains(@class,'primary')]");
    public By alertSecondaryBtn = By.xpath("//button[contains(@class,'secondary')]");
    public By clearInputBtn = By.xpath("//*[contains(@class,'ClearButton')]");
    public String localClearInputBtn = "//*[contains(@class,'ClearButton')]";
    public String clearInput = "//i[contains(@class,'Input__ClearButton')]";
    public String fieldMessageError = "//div[contains(@class,'FieldMessage') and contains(@class,'error')]";
    public String fieldMessageErrorTxt = fieldMessageError + "//span";
    public String nameCantBeANumberTxt = "Name can`t be a number";
    public String lengthMustBeTxt = "Length must be";
    public String valueIsTooLong = "Value is too long";
    public String mustBeValue1to10000 = "Must be a value between 1 to 10000";
    public String mustBeValueBetween1to9999 = "Must be a value between 1 to 9999";
    public String mustBeValueBetween1toMaxInteger = "Must be a value between 1 to 2147483647";
    public String Entitlements = "Entitlements";
    public String Details = "Details";
    public String availability = "Availability";
    public String policyAvailability = "Policy Availability";
    public String invalidCharacter = "invalid character";
    public String nameAlreadyExists = "Name already exists";
    public String welcomeToOkta = "Welcome to Okta!";
    public String welcomeToCrystalClear = "Welcome to Krystal Clear Optics!";
    public String verifyYourEmail = "Verify your email";
    public String gigyaAdmin = "gigyaadmin";
    public String krystalClearPasswordReset = "Krystal Clear Optics Password Reset";
    public String oktaResetPassword = "Account password reset";
    public String deletedSuccessfully = "deleted successfully";
    public String createdSuccessfully = "Created Successfully";
    public String gotIt = "Got It";
    public String responseKeyAttribute = "attributes.responseKey";
    public String responseValueAttribute = "attributes.responseValue";
    public String gotItBtn = "//button[contains(@class,'done-text')]";

    public String toggle = "//div[contains(@class,'toggle')]";
    public String toggleLabel = labelNamed + "/../..//div[contains(@class,'toggle')]";
    public String toggleIsInactive = labelNamed + "/../..//div[contains(@class,'toggle') and not (contains(@class,'active'))]";
    public String toggleIsActive =  labelNamed + "/../..//div[contains(@class,'toggle') and contains(@class,'active')]";
    public String toggleIsDisabled = labelNamed + "/../..//div[contains(@class,'toggle') and @disabled]";
    public String attributeEditDisabled = "//input[contains(@name,'%s') and @disabled]";
    public By stringBtn = By.xpath("//div[contains(@class,'SegmentButton') and @data-test-id='STRING']");
    public By numberBtn = By.xpath("//div[contains(@class,'SegmentButton') and @data-test-id='NUMBER']");
    public By maxLengthInput = By.xpath("//input[@name='maxLength']");
    public String maxValueInput = "//input[@name='maxValue']";
    public By sortBtn = By.xpath("//button[contains(@class,'SortButton')]");
    public String toast =  "//div[contains(@class,'ToastContent')]";
    public String closeToast = toast + closeBtn;
    public String smartInputControl = "//div[contains(@class,'smart-input__control')]";
    public String fontTypeInputControl = "//div[contains(@class,'smart-input__control')][following-sibling::input[@name='fontFamilyType']]";
    public String smartInputOption = "//div[contains(@class,'SmartInput__Option')]//*[contains(@data-test-id,'%s')]";
    public String colorPickerRGBInput = "//span[contains(@style,'text-transform: capitalize') and text()='%s']/../input";
    public String [] rgb = {"r","g","b"};
    public By inputRequired = By.xpath("//input[@required]");
    public By textareaRequired = By.xpath("//textarea[@required]");
    public By reactDraftWysiwyg = By.xpath("//div[@aria-label='rdw-editor']");
    public By errorBar = By.xpath("//div[contains(@class,'error-bar')]");
    public By failureIcon = By.xpath("//*[contains(@class,'icon') and contains(@class,'failure')]");
    public By errorIcon = By.xpath("//*[contains(@class,'icon') and contains(@class,'error')]");
    public String toastError = "//i[@type='toast-error']";
    public String toastMessage = toast + textElement;
    public String toastSuccess = "//*[@type='toast-success']";
    public String toastDeletedSuccessfully = toast + stringFormat(textElement,deletedSuccessfully);
    public String toastCreatedSuccessfully = toast + stringFormat(textElement,createdSuccessfully);
    public String tableRow = "//div[contains(@class,'table-row')]";
    public String row = "//div[contains(@class,'Row')]";
    public String checkBox = "//div[contains(@class,'checkbox')]";
    public String checkCell = "//div[contains(@class,'check-cell')]";
    public String rowCheckBox = row + checkBox;
    public String rowUnselected = "//div[contains(@class, 'TableRow') and (not(contains(@class, 'selected')))]//div[contains(@class, 'control-cell')]";
    public String rowNthCheckBox = "(" + rowCheckBox + ")[%s]";
    public String rowNthUnselectedCheckBox = "(" + rowUnselected + ")[%s]";
    public String rowNamed = row + textElement;
    public String rowNamedCheckBox = rowNamed + up + up +checkCell;
//    public String rowNamedCheckBox = rowNamed + up + up +checkBox + up;
    public String selectedRow = row + selected;
    public String selectedRowNSpan = "(//div[contains(@class,' row') and contains(@class,'selected')])[%d]";
    public String tableRowEditBtn = tableRow + editBtn;
    public String tableRowDivNamed = "(//div[@role='rowgroup' and contains(@class,'TableRow')])[%s]";
    public String tableCellNamed = tableRowDivNamed + "//div[@role='cell']";
    public By tableHeader = By.xpath("//div[contains(@class,'TableHeader')]");
    public String tableNUnselectedRowNSpan = "((//div[contains(@class, 'TableRow') and(not(contains(@class,'selected')))])[%s]//span)[%s]";
    public String tableUnselectedRowNName = rowNthUnselectedCheckBox + up + "//div[@role='cell'][2]";
    public String rowGroup = "//div[@role='rowgroup']";
    public String tableCell = rowGroup + "//span";
    public String namedLabelValue = "//label[@for='%s']" + up + up  + "//p";

    //**guerrillamail**//
    public String guerrillamailUrl = "https://www.guerrillamail.com/inbox";
    public String guerrillamailEmailAddress = "//span[@id='email-widget']";
    public String guerrillamailMailRow = "//tbody[@id='email_list']//tr[contains(@class,'mail_row')]";
    public String guerrillamailEditEmail = "//span[@id='inbox-id']";
    public String guerrillamailEditEmailInput = guerrillamailEditEmail + input;
    public String guerrillamailEditEmailSaveBtn = guerrillamailEditEmail + "//button[contains(@class,'save')]";
    public String guerrillamailScrambleToggle = "//span[@id='alias-box']//label";
    public String guerillaResetpasswordLink = "//a[contains(@href,'okta.com') and ./span[contains(text(),'Reset Password')]]";
    public String guerrillaBackToInbox = "//a[@id='back_to_inbox_link']";
    public String guerrillaInboxCheckBox = guerrillamailMailRow + "//input[@type='checkbox']";
    public String guerrillaDeleteBtn = "//input[@id='del_button']";
    public String guerrillaOktaActivationLink = "//span[contains(text(),'Activate Okta Account')]/../../a[contains(@href,'okta')]";
    public String guerrillaAuth0ActivationLink = "//div/a[contains(@href,'email-verification')]";
    public String emailBody = "//div[@class='email']";

    //****Mailinator**//
    public String mailinatorDeleteBtn = "//a[contains(@class,'btn-delete')]";
    public String mailinatorUrl = "https://www.mailinator.com/";
    public By mailinatorSearchTextField = By.id("addOverlay");
    public By mailinaotSearchGoBtn = By.id("go-to-public");
    public String mailinatorEmailItem = "//tr[@ng-repeat='email in emails']";
    public String mailinatorSelectEmailInInboxCheckbox = mailinatorEmailItem + "//td[1]";
    public By mailinatorEmptyInboxImg = By.cssSelector("img[alt='Mailinator Logo - Empty Inbox']");
    public String mailinatorEmailItemBySubject = mailinatorEmailItem + textElement;
    public By mailinatorInboxDeleteBtn = By.cssSelector("button[aria-label='Delete Button']");
    public String resetPasswordLink = "//a[@id='reset-password-link']";
    public By mailinatorPublicInboxes = By.cssSelector("a[href='inboxes.jsp']");
    public By msgBodyIframe = By.cssSelector("#html_msg_body");
    public By atkoNewPassword = By.cssSelector("input[id='loginForm.newPassword']");
    public By atkoVerifyNewPassword = By.cssSelector("input[id='loginForm.verifyPassword']");
    public By atkoSecurityAnswer = By.cssSelector("input[id='loginForm.securityAnswer']");
    public By atkoPersonaIcon = By.cssSelector("a.person_security_image_anchor");
    public By atkoNextButton = By.cssSelector("input#next-button");
    public By atkoMigrationFlow = By.className("migration-flow");
    String atkoSecurityCode = "Mm123456";
    public String atkoCangePasswordNewPasswordInput = "//input[@id='change_password.newPassword']";
    public String atkoCangePasswordVerifyPasswordInput = "//input[@id='change_password.verifyPassword']";
    public By atkoSecurityCodeInput = By.cssSelector("input[type='password']");
    public String atkoResetPasswordBtn = "//input[@id='change_password.button.submit']";
    public By atkoNewPasswordReset = By.cssSelector("input[name='newPassword']");
    public By atkoConfirmPasswordReset = By.cssSelector("input[name='confirmPassword']");

    public String noPrivateMainMessage = "//div[@id='main-message']";
    public String noPrivateAdvancedBtn = "//button[@id='details-button']";
    public String noPrivateProceedLink = "//a[@id='proceed-link']";



    public void selectTypeFirstUpperCase(String type){
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        type = String.format(typeSelectorTmpl,type);
        clickOnElement(By.xpath(type));
    }

    public String selectRandomRowCheckBox(){
        waitElementAmountToBeMoreThan(rowUnselected,0);
        wait(300);
        int length = driver.findElements(By.xpath(rowUnselected)).size();
        String index;
        if(length>1) {
            index = Integer.toString(randomNumberInRange(2, length));
        }else {
            index = Integer.toString(randomNumberInRange(1, length));
        }
        String xpath = String.format(rowNthUnselectedCheckBox,index);
        String nameEl = stringFormat(tableUnselectedRowNName,index,"1");
        String name = getElementText(nameEl).stripTrailing();
        scrollElementIntoView(By.xpath(xpath));
        waitForElementToBeClickable(xpath);
        try {
            clickVisible(xpath);
        }catch (Throwable t){
            jsClick(xpath);
        }
        return name;
    }

    public String selectRandomTypeFirstUpperCase(String application){
        String type = getRandomTypeFirstUpper();
        wait(500);
        if (application.equalsIgnoreCase("gigya")) {
            selectSmartOption("//div[contains(@class,'smart-input__control')][../preceding-sibling::div/label[text()='Type']]", type);
        }
        else {
            String xpath = String.format(typeSelectorTmpl,type);
            clickDelayed(By.xpath(xpath),500);
        }
        return  type;
    }

    public String insertRandomStreetAddress(){
        String street = getRandomStreet();
        String number = Integer.toString(randomNumberInRange(1,100));
        String streetAddress = street + " " + number;
//        scrollElementIntoView(streetAddressAttribute);
//        wait(300);
        clickVisible(streetAddressAttribute);
        wait(400);
        sendTextToElement(streetAddressAttribute,streetAddress);
//        wait(400);
        return streetAddress;
    }

    public String insertRandomMaxStreetAddress(){
        String streetAddress = randomStringGenerator(220);
        clickVisible(streetAddressAttribute);
        wait(400);
        sendTextToElement(streetAddressAttribute,streetAddress);
        return streetAddress;
    }

    public String insertRandomCity(){
        String city = getRandomCity();
        clickVisible(cityAttribute);
        wait(400);
        sendTextToElement(cityAttribute,city);
        return city;
    }

    public String insertRandomMaxCity(){
        String city = randomStringGenerator(220);
        clickVisible(cityAttribute);
        wait(400);
        sendTextToElement(cityAttribute,city);
        return city;
    }

    public String insertRandomState(){
        String state = getRandomState();
//        scrollElementIntoView(stateAttribute);
//        wait(250);
        clickVisible(stateAttribute);
        wait(400);
        sendTextToVisibleElement(stateAttribute,state);
//        wait(400);
        return state;
    }

    public String insertRandomMaxState(){
        String state = randomStringGenerator(220);
        clickVisible(stateAttribute);
        wait(400);
        sendTextToVisibleElement(stateAttribute,state);
        return state;
    }

    public String insertRandomCountry(){
        String country = getRandomCountry();
        scrollElementIntoView(countryAttribute);
        clickVisible(countryAttribute);
        wait(400);
        sendTextToVisibleElement(countryAttribute,country);
        wait(400);
        return country;
    }

    public String insertRandomMaxCountry(){
        String country = randomStringGenerator(220);
        scrollElementIntoView(countryAttribute);
        clickVisible(countryAttribute);
        wait(400);
        sendTextToVisibleElement(countryAttribute,country);
        wait(400);
        return country;
    }

    public String insertRandomName(){
        String name = randomStringGenerator(12);
        for (int i=0;i<3;i++) {
            try {
                sendTextToVisibleElement(nameAttribute, name);
                break;
            }catch (Throwable t){
                wait(200);
            }
        }
        return name;
    }

    public String insertRandomMaxName(){
        String name = randomStringGenerator(220);
        for (int i=0;i<3;i++) {
            try {
                sendTextToVisibleElement(nameAttribute, name);
                break;
            }catch (Throwable t){
                wait(200);
            }
        }
        return name;
    }

    public String insertModifiedName(String prefix){
        String name = prefix + randomStringGenerator(8);
        sendTextToVisibleElement(nameAttribute,name);
        return name;
    }

    public String insertModifiedMaxName(String prefix){
        String name = prefix + randomStringGenerator(220);
        sendTextToVisibleElement(nameAttribute,name);
        return name;
    }

    public String insertName(String name){
        sendTextToVisibleElement(nameAttribute,name);
        return name;
    }

    public String insertPasswordAttribute(String password){
        sendTextToVisibleElement(passwordAttribute,password);
        return password;
    }

    public String insertRandomDepartment(){
        String department = randomStringGenerator(12);
        sendTextToVisibleElement(departmentAttribute,department);
        return department;
    }

    public String insertDepartment(String department){
        sendTextToVisibleElement(departmentAttribute,department);
        return department;
    }

    public String insertRandomJobFunction(){
        String jobFunction = randomStringGenerator(12);
        sendTextToVisibleElement(jobFunctionAttribute,jobFunction);
        return jobFunction;
    }

    public String insertJobFunction(String jobFunction){
        sendTextToVisibleElement(jobFunctionAttribute,jobFunction);
        return jobFunction;
    }

    public String insertRandomFirstName(){
        String firstName = randomStringGenerator(12);
        sendTextToVisibleElement(firstNameAttribute,firstName);
        return firstName;
    }

    public String insertFirstName(String firstName){
        sendTextToVisibleElement(firstNameAttribute,firstName);
        return firstName;
    }

    public String insertRandomGivenName(){
        String givenName = randomStringGenerator(12);
        sendTextToVisibleElement(givenNameAttribute,givenName);
        return givenName;
    }

    public String insertGivenName(String givenName){
        sendTextToVisibleElement(givenNameAttribute,givenName);
        return givenName;
    }

    public String insertRandomLastName(){
        String lastName = randomStringGenerator(12);
        sendTextToVisibleElement(lastNameAttribute,lastName);
        return lastName;
    }

    public String insertLastName(String lastName){
        sendTextToVisibleElement(lastNameAttribute,lastName);
        return lastName;
    }

    public String insertRandomFamilyName(){
        String familyName = randomStringGenerator(12);
        sendTextToVisibleElement(familyNameAttribute,familyName);
        return familyName;
    }

    public String insertFamilyName(String familyName){
        sendTextToVisibleElement(familyNameAttribute,familyName);
        return familyName;
    }

    public String insertEmail(String email){
        sendTextToVisibleElement(emailAttribute,email);
        return email;
    }

    public String insertLogin(String text){
        sendTextToVisibleElement(loginAttribute,text);
        return text;
    }

    public String insertRandomDescription(){
        String description = randomStringGenerator(12);
        sendTextToVisibleElement(descriptionAttribute,description);
        return description;
    }

    public String insertRandomMaxDescription(){
        String description = randomStringGenerator(220);
        sendTextToVisibleElement(descriptionAttribute,description);
        return description;
    }

    public String insertRandomDefaultValue(){
        String defaultValue = randomStringGenerator(4);
        sendTextToVisibleElement(defaultValueAttribute,defaultValue);
        return defaultValue;
    }

    public String insertDescription(String description){
        sendTextToVisibleElement(descriptionAttribute,description);
        return description;
    }

    public String insertRandomResponseKey(){
        String text = randomStringGenerator(12);
        sendTextToVisibleElement(responseKey,text);
        return text;
    }

    public String insertResponseKey(String text){
        sendTextToVisibleElement(responseKey,text);
        return text;
    }

    public String insertRandomResponseValue(){
        String text = randomStringGenerator(12);
        sendTextToVisibleElement(responseValue,text);
        return text;
    }

    public String insertResponseValue(String text){
        sendTextToVisibleElement(responseValue,text);
        return text;
    }

    public String insertRandomType(){
        String text = randomStringGenerator(12);
        sendTextToVisibleElement(type,text);
        return text;
    }

    public String insertType(String text){
        sendTextToVisibleElement(type,text);
        return text;
    }

    public String insertRandomClassification(){
        String text = randomStringGenerator(12);
        sendTextToVisibleElement(classification,text);
        return text;
    }

    public String insertClassification(String text){
        sendTextToVisibleElement(classification,text);
        return text;
    }

    public String insertRandomExternalId(){
        String externalId = randomNumberGenerator(12);
        sendTextToVisibleElement(externalIdAttribute,externalId);
        return externalId;
    }

    public String insertRandomMaxExternalId(){
        String externalId = randomNumberGenerator(220);
        sendTextToVisibleElement(externalIdAttribute,externalId);
        return externalId;
    }

    public String insertRandomZipCode(){
        String zip = randomNumberGenerator(7);
//        scrollElementIntoView(stateAttribute);
//        wait(200);
        clickVisible(zipCodeAttribute);
        wait(400);
        sendTextToVisibleElement(zipCodeAttribute,zip);
//        wait(400);
        return zip;
    }

    public String insertRandomMaxZipCode(){
        String zip = randomNumberGenerator(30);
        clickVisible(zipCodeAttribute);
        wait(400);
        sendTextToVisibleElement(zipCodeAttribute,zip);
        return zip;
    }

    public String insertRandomPhoneNumber(){
        String phoneNumber = "97250" + randomNumberGenerator(7);
        clickVisible(phoneNumberAttribute);
        wait(400);
//        clickVisible(phoneNumberAttribute);
        sendTextToVisibleElement(phoneNumberAttribute,phoneNumber);
//        sendTextAction(phoneNumber);
        return phoneNumber;
    }

    public String insertRandomMemberLimit(){
        String limit = Integer.toString(randomNumberInRange(10,1000));
        scrollElementIntoView(memberLimitAttribute);
        wait(300);
        clearElement(memberLimitAttribute);
        clickVisible(memberLimitAttribute);
        wait(300);
        sendTextAction(memberLimitAttribute,limit);
        return limit;
    }

    public String insertRandomMaxMemberLimit(){
        String limit = "1" + randomNumberGenerator(15);
        scrollElementIntoView(memberLimitAttribute);
        wait(300);
        clearElement(memberLimitAttribute);
        clickVisible(memberLimitAttribute);
        wait(300);
        sendTextAction(memberLimitAttribute,limit);
        return limit;
    }

    public String insertIDPDisplayName(String name){
        sendTextToVisibleElement(IDPDisplayName,name);
        return name;
    }

    public String insertApiKey(String name){
        sendTextToVisibleElement(apiKey,name);
        return name;
    }

    public String insertGroupId(String name){
        sendTextToVisibleElement(groupId,name);
        return name;
    }

    public String insertPartnerClaimName(String name){
        sendTextToVisibleElement(partnerClaimName,name);
        return name;
    }

    public String insertIDPClientId(String name){
        sendTextToVisibleElement(IDPClientId,name);
        return name;
    }

    public String insertIDPClientSecret(String name){
        sendTextToVisibleElement(IDPClientSecret,name);
        return name;
    }

    public String insertIDPTokenUrl(String name){
        sendTextToVisibleElement(IDPTokenUrl,name);
        return name;
    }

    public String insertIDPJwksUrl(String name){
        sendTextToVisibleElement(IDPJwksUrl,name);
        return name;
    }

    public String insertIDPIssuer(String name){
        sendTextToVisibleElement(IDPIssuer,name);
        return name;
    }

    public String insertIDPMetaDataUrl(String url){
        sendTextToVisibleElement(IDPMetaDataUrl,url);
        return url;
    }

    public String insertIDPDomainNameUrl(String url){
        sendTextToVisibleElement(IDPDomainNameUrl,url);
        return url;
    }

    public String insertIDPAudienceUrl(String url){
        sendTextToVisibleElement(IDPAudienceUrl,url);
        return url;
    }

    public String insertAttributeValue(String text){
        sendTextToVisibleElement(attributeValue,text);
        return text;
    }

    public boolean validateNameInTitle(String name){
        String xpath = String.format(nameInTitle,name);
        waitForElementToBeVisible(By.xpath(xpath));
        return true;
    }

    public void clickGetStartedBtn(){
        clickVisible(getStartedBtn);
    }

    public void clickSaveButton(){
        wait(500);
        clickVisible(saveBtn);
    }

    public void clickSaveButton(int delay){
        wait(delay);
        clickVisible(saveBtn);
    }

    public void clickSaveAndWait(){
        clickVisible(saveBtn);
        wait(800);
    }

    public void clickCancelButton(){
        clickVisible(cancelBtn);
        wait(300);
    }

    public void clickCreateButton(){
        clickVisible(createBtn);
    }

    public void clickSearchButton(){
        clickVisible(searchBtn);
    }

    public void clickUploadButton(){
        wait(500);
        clickVisible(uploadBtn);
    }

    public void clickRegenerateButton(){
        clickVisible(regenerateBtn);
    }

    public void clickDoneButton(){
        wait(500);
        clickVisible(doneBtn);
    }

    public void clickDeclineButton(){
        clickVisible(By.xpath(declineBtn));
    }

    public void clickContinueButton(){
        wait(500);
        clickVisible(continueBtn);
    }

    public void clickBackButton(){
        clickVisible(backBtn);
    }

    public void clickImportBtn(){
        clickVisible(importBtn);
    }

    public void clickDownloadBtn(){
        clickVisible(downloadBtn);
    }

    public void clickBackAndWait(){
        clickVisible(backBtn);
        wait(800);
    }

    public void clickDeleteButton(){
        clickVisible(By.xpath(deleteBtn));
    }

    public void clickCloseButton(){
        clickVisible(By.xpath(closeBtn));
    }

    public void clickAddButton(){
        clickVisible(By.xpath(addBtn));
    }

    public void clickActivateButton(){
        clickVisible(By.xpath(activateBtn));
    }

    public void clickDeactivateButton(){
        clickVisible(By.xpath(deactivateBtn));
    }

    public void clickEditButton(){
        wait(200);
        waitForElementToBeClickable(editBtn);
        clickVisible(editBtn);
    }

    public void clickRemoveButton(){
        wait(100);
        clickVisible(removeBtn);
    }

    public void clickEmailSubmitButton(){
        wait(100);
        clickVisible(emailSubmitBtn);
        wait(2000);
    }

    public void clickAlertPrimaryBtn(){
        clickVisibleDisappear(alertPrimaryBtn);
    }

    public void clickAlertSecondaryBtn(){
        clickVisible(alertSecondaryBtn);
    }

    public void declineAndConfirm(){
        clickDeclineButton();
        clickAlertPrimaryBtn();
    }

    public void deleteAndConfirm(){
        clickDeleteButton();
        clickAlertPrimaryBtn();
    }

    public void cancelAndConfirm(){
        clickVisible(cancelBtn);
        clickVisible(alertSecondaryBtn);
    }

    public void cancelAndApprove(){
        clickVisible(cancelBtn);
        clickVisible(alertPrimaryBtn);
    }

    public void clickSortBtn(){
        clickVisible(sortBtn);
        wait(300);
    }

    public void clickClearBtn(){
        clickVisible(clearBtn);
        wait(200);
    }

    public void selectElementScrollInHoverClickBtnInside(String element, String btn, int forceIndex) {
        waitElementAmountToBeMoreThan(element, 0);
        int index;
        wait(300);
        for (int i = 0; i < 3; ) {
            try {
                if(forceIndex > -1){
                    index = getRandomElementIndex(element);
                }else {
                    index = forceIndex;                }
                String selectedElementXpath = "(" + element + ")[" + index + "]";
                String btnXpath = selectedElementXpath + btn;
                hoverOverVisibleElement(selectedElementXpath,6);
                wait(50);
                clickVisible(btnXpath, 6);
                break;
            } catch (Throwable t) {
                i++;
            }
        }
    }

    public String getRandomListItemName(By element){
        waitElementAmountToBeMoreThan(element,0);
        wait(500);
        return getRandomElement(element).getText().stripTrailing();
    }

    public String getRandomListItemName(String xpath){
        waitElementAmountToBeMoreThan(xpath,0);
        wait(300);
        return getRandomElement(xpath).getText().split("\\\n")[0].stripTrailing();
    }

    public String getSpecialListItemName(String xpath, String name){
        waitElementAmountToBeMoreThan(xpath,0);
        wait(300);
        List<WebElement>list = driver.findElements(By.xpath(xpath));
        for (WebElement element : list){
            String y = element.getText();
            if(element.getText().contains(name)){
                return element.getText().split("\n")[0].stripTrailing();
            }
        }
        return "";
    }

    public String selectRandomListItemName(By element){
        waitElementAmountToBeMoreThan(element,0);
        webElement = getRandomElement(element);
        String name = webElement.getText().stripTrailing();
        scrollElementIntoView(webElement);
        clickVisible(webElement);
        return name;
    }

    public String selectRandomListItemNameInDiv(By element, String cssDiv){
        waitElementAmountToBeMoreThan(element,0);
        webElement = getRandomElement(element);
        String name = webElement.getText().stripTrailing();
        scrollOnElementUntilVisible(cssDiv,stringFormat(textElement,name));
        scrollElementIntoView(webElement);
        clickVisible(webElement);
        return name;
    }

    public String selectRandomScrollableListItemName(String xpath, String scrollableCss){
        waitElementAmountToBeMoreThan(xpath,0);
        webElement = getRandomElement(xpath);
        String name = webElement.getText().stripTrailing();
        if(webElement.isDisplayed()) {
            scrollElementIntoView(webElement);
        }else {
            scrollElementUp(scrollableCss);
        }
        clickVisible(webElement);
        return name;
    }

    public String selectRandomListItemName(String xpath){
        return selectRandomListItemName(By.xpath(xpath));
    }

    public String selectRandomListItemGetAtt(By element, String attribute){
        waitElementAmountToBeMoreThan(element,0);
        webElement = getRandomElement(element);
        String att = webElement.getAttribute(attribute);
        clickVisible(webElement);
        return att;
    }

    public boolean toggleOff(String toggleIsActive, String toggleLabel, String toggleIsInactive, String toggleFor){
        boolean toggleInitiallyOn = isToggleActive(toggleIsActive,toggleFor);
        if(toggleInitiallyOn){
            toggleLabel(toggleLabel,toggleFor);
            waitToggleIsInactive(toggleIsInactive,toggleFor);
            clickSaveAndWait();
        }
        else {
            clickCancelButton();
        }
        return toggleInitiallyOn;
    }

    public boolean toggleOn(String toggleIsActive, String toggleLabel, String toggleFor){
        boolean toggleInitiallyOn = isToggleActive(toggleIsActive,toggleFor);
        if(!toggleInitiallyOn){
            toggleLabel(toggleLabel,toggleFor);
            waitToggleIsActive(toggleIsActive,toggleFor);
            clickSaveAndWait();
        }
        else {
            clickCancelButton();
        }
        return toggleInitiallyOn;
    }


    public void toggleLabel(String toggleLabel, String labelName){
        String xpath = String.format(toggleLabel,labelName);
        clickVisible(By.xpath(xpath));
    }

    public void waitToggleIsInactive(String toggleIsInactive, String labelName){
        String xpath = String.format(toggleIsInactive,labelName);
        waitForElementToBeVisible(By.xpath(xpath));
    }

    public void waitToggleIsActive(String toggleIsActive, String labelName){
        String xpath = String.format(toggleIsActive,labelName);
        waitForElementToBeVisible(By.xpath(xpath));
    }

    public boolean waitToggleIsDisabled(String toggleIsDisabled,String labelName){
        String xpath = String.format(toggleIsDisabled,labelName);
        waitForElementToBeVisible(By.xpath(xpath));
        return true;
    }

    public boolean isToggleActive(String toggleIsActive, String labelName){
        wait(800);
        String xpath = String.format(toggleIsActive,labelName);
        return (!driver.findElements(By.xpath(xpath)).isEmpty());
    }

    public void clickStringBtn(){
        waitForElementPresence(stringBtn);
        scrollElementIntoView(stringBtn);
        clickVisible(stringBtn);
    }

    public void clickNumberBtn(){
        waitForElementPresence(numberBtn);
        scrollElementIntoView(numberBtn);
        clickVisible(numberBtn);
    }

    public void clickTemplateElementVisible(String template, String name){
        String xpath = String.format(template,name);
        scrollElementIntoView(By.xpath(xpath));
        clickVisible(By.xpath(xpath));
    }

    public void editSortableTemplateElementVisible(String template, String name){
        String xpath = String.format(template,name);
        if(!isElementFound(By.xpath(xpath))){
            clickSortBtn();
        }
        scrollElementIntoView(By.xpath(xpath));
        clickVisible(By.xpath(xpath));
        clickEditButton();
    }

    public void clearAllClearInputs(){
        doubleClickAllElements(clearInputBtn);
    }

    public void closeToasts(){
        try {

            if (isElementFound(closeToast)) {
                List<WebElement> toasts = getElementsList(closeToast);
                for (WebElement toast : toasts) {
                    clickVisible(toast);
                    waitForElementToDisappear(toast);
                }
            }
        }catch (Throwable t){

        }
    }

    public void closeElement(String xpath){
        wait(800);
//        String xpath = stringFormat(textElement,txt);
        List<WebElement>list = driver.findElements(By.xpath(xpath));
        for (WebElement element : list){
            try {
                clickVisible(element);
                waitForElementToDisappear(element);
            }catch (Throwable t){

            }
        }
    }

    public boolean waitForRowItemState(String name, String state){
        String xpath = String.format(rowNamed,name) + "/../.." + state;
        waitForElementToBeVisible(xpath);
        return true;
    }

    public void hoverAndClick(String hoverable, String clickable){
        hoverOverVisibleElement(hoverable);
        wait(200);
        clickVisible(clickable);
    }

    public void clickHoverableListItemBtn(String name, String buttonType ){
        String partnerItem = String.format(listItemNamedTmpl,name);
        String btn1 = partnerItem + "/.." + buttonType;
        String btn2 = partnerItem + "/../.." + buttonType;
        hoverOverVisibleElement(partnerItem);
        if(isElementFound(btn1)) {
            clickVisible(btn1);
        }else {
            clickVisible(btn2);
        }
    }

    public void clickHoverableRowBtn(String name, String buttonType ){
        String partnerItem = String.format(rowNamed,name);
        String btn1 = partnerItem + "/.." + buttonType;
        String btn2 = partnerItem + "/../.." + buttonType;
        hoverOverVisibleElement(partnerItem + "/..");
        wait(200);
        if(isElementFound(btn2)) {
//            jsClick(btn2);
            clickVisible(btn2);
        }else {
//            jsClick(btn1);
            clickVisible(btn1);
        }
    }

    public void clickHoverableBtnLoop(String name, String buttonType ){
        String partnerItem = String.format(textElement,name);
        String btn1 = partnerItem + "/.." + buttonType;
        String btn2 = partnerItem + "/../.." + buttonType;
        for(int i=0;i<20;i++) {
            try {
                hoverOverVisibleElement(partnerItem,2);
                wait(200);
                if (isElementFound(btn2)) {
//            jsClick(btn2);
                    clickVisible(btn2,2);
                } else {
//            jsClick(btn1);
                    clickVisible(btn1,2);
                }
                return;
            }catch (Throwable t){
                hoverOverVisibleElement(logo,2);
                wait(100);
            }
        }
    }

    public void clickHoverableListBtn(String name, String buttonType ){
        String partnerItem = String.format(listItemNamedTmpl,name);
        String btn1 = partnerItem + "/.." + buttonType;
        String btn2 = partnerItem + "/../.." + buttonType;
        hoverOverVisibleElement(partnerItem);
        wait(200);
        if(isElementFound(btn2)) {
//            jsClick(btn2);
            clickVisible(btn2);
        }else {
//            jsClick(btn1);
            clickVisible(btn1);
        }
    }

    public String setColorPickerColors(){
        StringBuilder rgba = new StringBuilder();
        for (String letter:rgb){
            String color = String.format(colorPickerRGBInput,letter);
            String value = String.valueOf(randomNumberInRange(255));
            clickVisible(color);
            wait(100);
            clearElement(color);
            wait(100);
            sendTextToElement(color,value);
            wait(100);
            rgba.append(value).append(", ");
        }
        clickAction(logo);
        rgba.setLength(rgba.length() - 2);
        return rgba.toString();
    }

    public String selectRandomFontOption(String [] options){
        clickVisible(fontTypeInputControl);
        String option = getRandomStringFromArray(options);
        String xpath = String.format(smartInputOption,option);
        clickVisible(xpath);
        return option;
    }

    public String selectRandomSmartOption(String smartInputControl, String [] options){
        clickVisible(smartInputControl);
        String option = getRandomStringFromArray(options);
        String xpath = String.format(smartInputOption,option);
        clickVisible(xpath);
        return option;
    }

    public String selectSmartOption(String option){
        clickVisible(smartInputControl);
        String xpath = String.format(smartInputOption,option);
        clickVisible(xpath);
        return option;
    }

    public String selectSmartOption(String smartInputControl, String option){
        clickVisible(smartInputControl);
        String xpath = String.format(smartInputOption,option);
        clickVisible(xpath);
        return option;
    }

    public String selectDirectSmartOption(String smartInputControl, String option){
        clickVisible(smartInputControl);
        clickVisible(option);
        return option;
    }

    public void fillAllRequiredInputsRandomData(int inputsAmount){
        waitForElementToBeVisible(editBtn,10);
        wait(200);
        clickEditButton();
        waitElementAmountToBeMoreThan(inputRequired,inputsAmount-1);
        List<WebElement> elements = driver.findElements(inputRequired);
        wait(200);
        elements.addAll(driver.findElements(reactDraftWysiwyg));
        elements.addAll(driver.findElements(textareaRequired));
        for (WebElement element:elements){
            scrollElementIntoView(element);
            wait(200);
            String name = element.getAttribute("name");
            if (null == name) name = "N/A";
            if(name.toLowerCase().contains("url")){
                element.sendKeys(randomUrlGenerator());
            }
            else if (name.toLowerCase().contains("email")) {
                element.sendKeys(randomEmailGenerator());
            }
            else if (name.toLowerCase().contains("n/a")) {
                element.clear();
                element.sendKeys(randomStringGenerator(8));
                wait(500);
                clickOnElement("//body");
            }
            else {
                element.sendKeys(randomStringGenerator(8));
            }
            wait(200);
        }
        clickSaveAndWait();
        clickBackAndWait();
    }

    public String getListItem(String name){
        return String.format(listItemNamedTmpl,name);
    }

    public String getRowItem(String name){
        return String.format(rowNamed,name);
    }

    public List<String> getListItems(By listItemContainerLocator) {
        WebElement listItemContainerElement = driver.findElement(listItemContainerLocator);
        List<WebElement> listItemElements = listItemContainerElement.findElements(By.xpath("." + listItem));

        List<String> listItems = new ArrayList<>();
        for (WebElement listItemElement : listItemElements) {
            listItems.add(listItemElement.getText());
        }
        return listItems;
    }

    public void clickListItemByName(String name){
        String listItem = getListItem(name);
        waitForElementToBeVisible(By.xpath(listItem));
        scrollElementIntoView(By.xpath(listItem));
        clickVisible(listItem);
    }

    public void clickRowItemByName(String name){
        String rowItem = stringFormat(textElement,name);
        waitForElementToBeVisible(By.xpath(rowItem));
        scrollElementIntoView(By.xpath(rowItem));
//        String x = "//div[contains(@class,' row') and .//span[contains(.,'Jp7CgARO192I')]]";
        hoverOverElement(rowItem + "/..");
        clickVisible(rowItem);
    }

    public String addLogo(String path){
        sendTextToElement(logoUrl,path);
        return path;
    }

    public boolean validateErrorBarAppearance(){
        waitForElementToBeVisible(errorBar);
        return true;
    }

    public boolean validateFailureIconPresence(){
        waitForElementToBeVisible(failureIcon);
        return true;
    }

    public boolean validateErrorIconPresence(){
        waitForElementToBeVisible(errorIcon);
        return true;
    }

    public boolean validateToastErrorAppearance(){
        waitForElementToBeVisible(toastError);
        return true;
    }

    public boolean validateToastMessageAppearance(String message){
        waitForElementToBeVisible(stringFormat(toastMessage,message),10);
        return true;
    }

    public List<List<String>> getTableRowsTexts(){
        waitForElementToBeVisible(tableHeader);
        wait(1800);
        List<List<String>> tableData = new ArrayList<>();
        int rowCounter = 1;
        while (rowCounter<Integer.MAX_VALUE){
            String rowDiv = stringFormat(tableRowDivNamed,String.valueOf(rowCounter));
            if(isElementFound(rowDiv)){
                List<String>row = new ArrayList<>();
                String rowCells = stringFormat(tableCellNamed,String.valueOf(rowCounter));
                List<WebElement>cells = getElementsList(rowCells);
                for (WebElement cell:cells){
                    String cellTxt = cell.getText().stripTrailing();
                    row.add(cellTxt);
                }
                tableData.add(row);
                rowCounter++;
            }else {
                break;
            }
        }
        return tableData;
    }

    public void bypassNoHttps(){
        if(waitForElementToBeVisible(noPrivateMainMessage,6)) {
            clickVisible(noPrivateAdvancedBtn);
            clickVisible(noPrivateProceedLink);
        }
    }

        public String getGuerrillaEmail(){
            ((JavascriptExecutor)driver).executeScript("window.open('about:blank','_blank');");
            switchToNewWindow();
            driver.get(guerrillamailUrl);
            bypassNoHttps();
            driver.manage().deleteAllCookies();
            refreshScreen();
            String email = "";
            for(int i=0;i<3;i++) {
                try {
                    clickVisible(guerrillamailScrambleToggle,6);
                    wait(400);
                    email = getVisibleElementTextStrip(guerrillamailEmailAddress);
                    driver.close();
                    break;
                }catch (Throwable t){
                    wait(200);
                    refreshScreen();
                }
            }
            switchToNewWindow();
            return email;
        }

    public void openGuerrillaEmail(String email, String subject, String application, String IDP, boolean updateEmail){
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        switchToNewWindow();
        driver.get(guerrillamailUrl);
        email = email.split("@")[0];
        for(int i=0;i<3;i++) {
            bypassNoHttps();
            try {
                if (updateEmail) {
                    clickVisible(guerrillamailEditEmail);
                    sendTextToVisibleElement(guerrillamailEditEmailInput, email);
                    clickVisible(guerrillamailEditEmailSaveBtn);
                }
                String emailContent = "";
                if (subject.toLowerCase().contains("welcome")) {
                    if (application.toLowerCase().contains("partner")) {
                        if(IDP.toLowerCase().contains("okta")) {
                            emailContent = stringFormat(textElement, welcomeToOkta);
                        }else if(IDP.toLowerCase().contains("auth0")){
                            emailContent = stringFormat(textElement, verifyYourEmail);
                        }
                    } else {
                        emailContent = stringFormat(textElement, "gigya-raas");
                    }
                } else if (subject.toLowerCase().contains("reset")) {
                    emailContent = stringFormat(textElement, oktaResetPassword);
                }
                clickVisible(guerrillamailMailRow + emailContent, 300);
                break;
            } catch (Throwable t){
                refreshScreen();
            }
        }
    }


    public void openMailinatorEmail(String email, String subject, String application){
        driver.get(mailinatorUrl);
        sendTextToVisibleElement(mailinatorSearchTextField,email);
        clickVisible(mailinaotSearchGoBtn);
        for (int i=0;i<4;i++) {
            try {
                if (subject.toLowerCase().contains("welcome")) {
                    if(application.toLowerCase().contains("partner")) {
                        clickVisible(stringFormat(mailinatorEmailItemBySubject, welcomeToCrystalClear));
                    }else {
                        clickVisible(stringFormat(mailinatorEmailItemBySubject, gigyaAdmin));
                    }
                    break;
                } else if (subject.toLowerCase().contains("reset")) {
                    clickVisible(stringFormat(mailinatorEmailItemBySubject, krystalClearPasswordReset));
                    break;
                }
            }catch (Throwable t){
                refreshScreen();
            }
        }
    }

    public void mailinatorDeleteInboxEmails(){
        clickVisible(mailinatorPublicInboxes);
        waitForElementToBeVisible(mailinatorSelectEmailInInboxCheckbox);
        wait(800);
        List<WebElement> checkboxes = getElementsList(mailinatorSelectEmailInInboxCheckbox);
        for (WebElement checkbox : checkboxes){
            checkbox.click();
            wait(100);
        }
        clickVisible(mailinatorInboxDeleteBtn);
        waitForElementToDisappear(mailinatorSelectEmailInInboxCheckbox,4);
        waitForElementToBeVisible(mailinatorEmptyInboxImg,4);
        wait(400);
    }

    public void validateGuerrillaEmail(String application, String IDP, Map<String,String> data, boolean updateEmail){
        String email = data.get("email").split("@")[0];
        openGuerrillaEmail(email, welcome,application,IDP, updateEmail);
        String greeting = "Hi " + data.get("firstName");
        String xpath = stringFormat(textElement,greeting);
        Assert.assertTrue(waitForElementPresence(xpath,10), stringFormat("Could not find %s greeting in the invitation email", greeting));
    }
    /*
    public void validateMailinatorEmail(String application, Map<String,String> data){
        openMailinatorEmail(data.get("email"),welcome,application);
        String greeting = "Hi " + data.get("firstName");
        String xpath = stringFormat(textElement,greeting);
        for (int i =0; i<4; i++) {
            try {
                Assert.assertTrue(waitForElementPresence(xpath,10), stringFormat("Could not find %s greeting in the invitation email", greeting));
                break;
            }catch (Throwable t){
                try {
                    refreshScreen();
                }catch (Throwable t1){
                    try {
                        Alert alert = driver.switchTo().alert();
                        String alertText = alert.getText();
                        System.out.println("Alert data: " + alertText);
                        alert.accept();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        mailinatorDeleteInboxEmails();
    }
*/
    public void activateUser(String application, String IDP, String email){
        if (application.equalsIgnoreCase("gigya")) {
            openMailinatorEmail(email,welcome, application);
            switchToIframe(msgBodyIframe);
            waitForElementToBeVisible("//div");
            String emailBody = getElementText("//div");
            Pattern p = Pattern.compile("(password is)(\\s.+)");
            Matcher m = p.matcher(emailBody);
            m.find();
            String tempPassword = m.group(2).trim();
            switchToDefaultContent();
//            mailinatorDeleteInboxEmails();
            new Apis().resetGigyaMemberPassword(email, tempPassword, passWord);
            new Apis().activateGigyaMember(email);
        }
        else {
            openGuerrillaEmail(email,welcome,application, IDP, false);
            clickVisible(emailBody);
            scrollPageDown();
            if(IDP.toLowerCase().contains("okta")) {
                clickVisible(guerrillaOktaActivationLink);
            }else if(IDP.toLowerCase().contains("auth0")) {
                clickVisible(guerrillaAuth0ActivationLink);
            }
            switchToNewWindow();
            if(IDP.toLowerCase().contains("okta")) {

//                waitForElementToBeVisible(atkoNewPassword);
//                String password = mailinatorPasswordGenerator();
//                sendTextToElement(atkoNewPassword, "Mm123456");
//                sendTextToElement(atkoVerifyNewPassword, "Mm123456");
//                sendTextToElement(atkoSecurityAnswer, atkoSecurityCode);
//                scrollElementIntoView(atkoNextButton);
//                clickVisible(atkoPersonaIcon);
//                clickVisible(atkoNextButton);
//                waitForUrlContains(plainidCom);
            }else if(IDP.toLowerCase().contains("auth0")) {
                String xpath = stringFormat(textElement,emailVerified);
                Assert.assertTrue(waitForElementToBeVisible(xpath));
            }
/*            driver.close();
            switchToNewWindow();
            cleanGuerrillaInbox();
            driver.close();
            switchToNewWindow();*/
//            mailinatorDeleteInboxEmails();
        }
    }

    public void resetPasswordMailinator(String application, String email){
        openMailinatorEmail(email,reset,application);
        switchToIframe(msgBodyIframe);
        clickVisible(resetPasswordLink);
        switchToNewWindow();
        clickVisible(atkoSecurityCodeInput);
        sendTextToVisibleElement(atkoSecurityCodeInput,atkoSecurityCode);
        clickVisible(atkoResetPasswordBtn);
        waitForElementToBeVisible(atkoNewPasswordReset);
        String password = mailinatorPasswordGenerator();
        sendTextToElement(atkoNewPasswordReset,password);
        sendTextToElement(atkoConfirmPasswordReset,password);
        clickVisible(atkoResetPasswordBtn);
        waitForUrlContains(plainidCom);
        switchToSecondWindow();
        mailinatorDeleteInboxEmails();
    }

    public void resetPasswordGuerrilla(String application, String IDP, String email){
        openGuerrillaEmail(email,reset,application, IDP,true);
        scrollPageDown();
        clickVisible(guerillaResetpasswordLink);
        switchToNewWindow();
        clickVisible(atkoCangePasswordNewPasswordInput);
        sendTextToVisibleElement(atkoCangePasswordNewPasswordInput,passWord);
        clickVisible(atkoCangePasswordVerifyPasswordInput);
        sendTextToVisibleElement(atkoCangePasswordVerifyPasswordInput,passWord);
        clickVisible(atkoResetPasswordBtn);
        waitForElementToBeVisible(atkoNewPasswordReset);
        String password = mailinatorPasswordGenerator();
        sendTextToElement(atkoNewPasswordReset,password);
        sendTextToElement(atkoConfirmPasswordReset,password);
        clickVisible(atkoResetPasswordBtn);
        waitForUrlContains(plainidCom);
    }

    public void cleanGuerrillaInbox(){
        waitForElementToBeVisible(guerrillaBackToInbox);
        wait(1200);
        clickVisible(guerrillaBackToInbox);
        waitElementAmountToBeMoreThan(guerrillaInboxCheckBox,0);
        List<WebElement> checkboxes = getElementsList(guerrillaInboxCheckBox);
        for(WebElement element : checkboxes){
            scrollElementIntoView(element);
            try {
                clickVisible(element);
            }catch (Throwable t){
                jsClick(element);
            }
            wait(50);
        }
        clickVisible(guerrillaDeleteBtn);
        wait(2500);
    }

    public String getInputNameValue(String name){
        String xpath = stringFormat(inputName,name);
        waitForElementToBeVisible(xpath);
        wait(200);
        return getElementAttribute(xpath,"value");
    }

    public String getLabelForText(String name){
        String xpath = stringFormat(labelFor,name);
        return getVisibleElementText(xpath).split(" ")[0].trim();
//        waitForElementToBeVisible(xpath);
//        wait(200);
//        return getElementAttribute(xpath,"value");
    }

    public Map<String,String> fillRandomNameDescription(){
        data = new HashMap<>();
        data.put("name",insertRandomName());
        data.put("description",insertRandomDescription());
        return data;
    }

    public Map<String,String> getRoleEntitlement(){
        clickListItemByName(Entitlements);
        String entitlementName = getVisibleElementTextStrip(tableCell);
        return getRoleEntitlementDetails(entitlementName);
    }

    public Map<String,String> getRoleEntitlementDetails(String entitlementName){
        data = new HashMap<>();
        String xpath = stringFormat(textElement,entitlementName);
        waitForElementPresence(xpath);
//        jsClick(xpath);
        scrollElementIntoView(xpath);

        clickVisibleAppearsLoop(xpath,closeBtn);

        String responseKey = getVisibleElementTextStrip(stringFormat(namedLabelValue,responseKeyAttribute));
        String responseValue = getVisibleElementTextStrip(stringFormat(namedLabelValue,responseValueAttribute));
        data.put("responseKey",responseKey);
        data.put("responseValue",responseValue);
        clickCloseButton();
        return data;
    }

    public boolean isInitialFirst(By element, String val1, String val2){
        waitForOneOfTwoTextsInElementAttribute(element,"value",val1,val2);
        return getElementValue(element).contains(val1);
    }

    public String setValueToOneOfTwo(By element, String val1, String val2){
        boolean isInitialFirst = isInitialFirst(element,val1,val2);
        String val = (isInitialFirst)?val2:val1;
        sendTextToElement(element,val);
        wait(200);
        return val;
    }

    public boolean isElementContainingPseudoElement(WebElement element){
        JavascriptExecutor jsExecutor  = (JavascriptExecutor)driver;
        String js = "return window.getComputedStyle(arguments[0], arguments[1]).getPropertyValue(arguments[2]);";
        String cssProperty = (String) jsExecutor.executeScript(js, element, ":after", "content");
        return !cssProperty.contains("none");
    }

    public boolean isElementContainingPseudoElement(String locator){
        webElement = driver.findElement(By.cssSelector(locator));
        JavascriptExecutor jsExecutor  = (JavascriptExecutor)driver;
        String js = "return window.getComputedStyle(arguments[0], arguments[1]).getPropertyValue(arguments[2]);";
        String cssProperty = (String) jsExecutor.executeScript(js, webElement, ":after", "content");
        return !cssProperty.contains("none");
    }

    public String getRandomElementNoPseudo(String xpath){
        waitElementAmountToBeMoreThan(xpath,0);
        wait(200);
        List<WebElement>list = getElementsList(xpath);
        List<String> elements = getElementsAttribute(xpath,dataTestId);
        String [] arr = new String[elements.size()];
        elements.toArray(arr);
        while (true){
            String name = getRandomStringFromArray(arr);
            String cssLocator = stringFormat(cssByAttributeTemplate,dataTestId,name);
            if(!isElementContainingPseudoElement(cssLocator)){
                return name;
            }
        }
    }

    public void clearLocalInputs(String xpath){
        List<WebElement> list = getElementsList(xpath + localClearInputBtn);
        for (WebElement el: list){
            el.click();
            wait(100);
        }
    }

    public void toggleToggle(){
        clickVisible(toggle);
    }

    public void toggleValidateSuccessToast(boolean activate){
        boolean activated;
        do {
            toggleToggle();
            waitForElementToBeVisible(toastSuccess);
            wait(500);
            activated = waitForAttributeToContain(toggle, "class", "active", 3);
        }while (activate != activated);

    }

    public void downloadTemplate(String templateFilename){
        deletePreviouslyDownloadedFiles(downloadsPath, templateFilename );
        clickImportBtn();
        wait(200);
        waitForElementToBeVisible(downloadBtn);
        hoverAndClickOnElement(downloadBtn);

        if(validateFileDownloaded(downloadsPath, templateFilename))
        {
            return;
        }

        Assert.fail("Could not find downloaded file in path " + downloadsPath);
    }

    public void clearBrowsingSession(){
        driver.get("chrome://settings/clearBrowserData");
        wait(2000);
        Shadow shadow = new Shadow(driver);
        WebElement element = shadow.findElement("cr-button#clearBrowsingDataConfirm");
        element.click();
        wait(400);
    }



}
