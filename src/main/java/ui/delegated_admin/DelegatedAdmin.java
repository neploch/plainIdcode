package ui.delegated_admin;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.Common.*;

public class DelegatedAdmin extends DaReusable {
    public DelegatedAdmin (WebDriver driver){
        super(driver);
        downloadedTemplateFilePath = downloadsPath + File.separator + inviteMembersTemplateFilename + ".csv";
        password = getRepValue("passWord");
        IDP = getRepValue("IDP");
    }

    Map<String,String> userData;
    Map<String,String> data;

    String inviteMembersTemplateFilename = "members-invite-example";
    String userWasInvitedSuccessfully = "User was invited successfully";
    String resetEmailWasSent = "Reset password email was successfully sent to member";
    String limitReached = "//*[text()='Limit Reached']";
    String password;
    String IDP;


    public By title = By.xpath("//h1[contains(@class,'title')]");
    String titleTxt = "Welcome to Admin";
    String roleItem = "//div[contains(@class,'StyledRoleItem')]";
    public By inviteMemberBtn = By.xpath("//button[contains(@class,'invite-members-button')]");
    public By inviteGroupBtn = By.xpath("//div[@data-test-id='Group']");
    public By selectedApplication = By.xpath("//div[contains(@class,'ApplicationsCard__ApplicationItem') and contains(@class,'selected')]");
    public String labelNumber = "(//label)[%d]";
    public String labelNumberToolTip = "(//label)[%d]/span[contains(@class,'help-button')]";
    public By pageLogo = By.xpath("//div[contains(@class,'header')]//img[@class='logo']");
    public By iconTitle = By.xpath("//h2[contains(@class,'title')]");
    public By subTitle = By.xpath("//*[contains(@class,'sub-title')]");
    public String registeredMemberName = "//div[contains(@class,'bpMRGA status-indicator')]/..//p";
    public String unregisteredMemberName = "//div[contains(@class,'kBjvFN status-indicator')]/..//p";
    public String inactiveMemberName = "//div[contains(@class,'gigLxx status-indicator')]/..//p";
    public String accessibleNamedRole = "//div[contains(@class,'RolesList') and contains(@data-test-id,'%s')]";
    public String roles = "//div[@class='roles-container']" + roleItem;
    String roleNamedCheckbox = textElement + up + checkBox;
    String inviteUserCardView = "//div[contains(@class,'InviteUserCardView')]";
    public String titleRow = "//div[@class='title-row']";
    public String memberCounter = titleRow + "//*[contains(text(),'(')]";
    public String logoutMenuAvatar = "//div[contains(@class,'navigation-bar')]//span[contains(@class,'avatar')]";
    public String logoutBtn = "//div[contains(@class,'logout')]";




    public boolean validateTitleText(){
        waitForTextInElement(title,titleTxt);
        return true;
    }

    public void logout(){
        hoverOverVisibleElement(logoutMenuAvatar);
        clickVisible(logoutBtn);
        waitForUrlContains("logout");
    }

    public void validateColorsAndFont(Map<String,String>data){
        waitForElementToBeVisible(title,100);
        String font = getFontType(title);
        waitForElementToBeVisible(inviteMemberBtn);
        String primaryRGB = getBackGroundColor(inviteMemberBtn);
        waitForElementToBeVisible(selectedApplication);
        String secondaryRGB = getElementBorderValue(selectedApplication);
        Assert.assertTrue(font.contains(data.get("font")),String.format("Font validation failed. Found %s while expected %s",font,data.get("font")));
        Assert.assertTrue(primaryRGB.contains(data.get("primaryRGB")),String.format("Primary color validation failed. Found %s while expected %s",primaryRGB,data.get("primaryRGB")));
        Assert.assertTrue(secondaryRGB.contains(data.get("secondaryRGB")),String.format("secondary color validation failed. Found %s while expected %s",secondaryRGB,data.get("secondaryRGB")));
    }

    public void validateDisplayData(Map<String, String []> data){
        clickVisible(inviteMemberBtn);
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            String label = stringFormat(labelFor,entry.getKey());
            Assert.assertTrue(waitForTextInElement(label,entry.getValue()[0]));
            String toolTip = label + tooltipHelpBtn;
            hoverOverVisibleElement(toolTip);
            Assert.assertTrue(waitForTextInElement(htmlTooltip,entry.getValue()[1]));
        }
    }
    public void validateStrings(Map<String,String> data){
        for (int i =0;i<3;i++) {
            try {
                Assert.assertTrue(waitForAttributeToContain(pageLogo, "src", data.get("logoPic"),5));
                Assert.assertTrue(waitForTextInElement(iconTitle, data.get("logo")));
                Assert.assertTrue(waitForTextInElement(title, data.get("titleTxt")));
                Assert.assertTrue(waitForTextInElement(subTitle, data.get("subTitleTxt")));
                hoverOverElement(backBtn);
                Assert.assertTrue(waitForElementContainingText(data.get("returningUrlTxt")));
                clickBackButton();
                Assert.assertTrue(waitForUrlContains(data.get("url")));
                break;
            }catch (Throwable t){
                refreshScreen();
            }
        }
    }

    public void inviteMemberWithExistingAttribute(String attribute){
        clickRandomListElement(listItem);
        clickEditButton();
        String existingAttribute = getInputNameValue(attribute);
        clickBackButton();
        userData = fillInviteMemberOkta(attribute,existingAttribute);
        if(attribute.contains("login")) {
            Assert.assertTrue(validateToastErrorAppearance());
        }else {
            Assert.assertTrue(validateToastMessageAppearance(userWasInvitedSuccessfully));
            waitForElementToBeVisible(inviteMemberBtn);
            wait(3000);
            refreshScreen();
            searchMember(userData.get("firstName"));
        }
    }

    public void searchMember(String name){
        waitElementAmountToBeMoreThan(listItem,0);
        sendTextToVisibleElement(input,name);
        clickVisible(searchBtn);
        String memberName = stringFormat(listItemNamedTmpl,name);
        Assert.assertTrue(waitForElementToBeVisible(memberName));
    }

//    waitElementAmountToBeMoreThan(listItem,0,4);
//    String memberName = stringFormat(listItemNamedTmpl,name);
//        for(int i = 0; i<5; i++){
//        waitElementAmountToBeMoreThan(listItem,0,4);
//        sendTextToVisibleElement(input,name);
//        clickVisible(searchBtn);
//        if(waitForElementToBeVisible(memberName,3)){
//            break;
//        }else {
//            refreshScreen();
//        }
//    }
//        Assert.assertTrue(waitForElementToBeVisible(memberName));

    public Map<String,String> editMember(String name){
        if(name.length()<1){
            clickRandomListElement(listItem);
        }else {
            selectMember(name);
        }
        clickEditButton();
        userData = editMemberData();
        waitForElementToBeVisible(toastSuccess);
        closeToasts();
        clickBackButton();
        String memberName = userData.get("firstName") + " " + userData.get("lastName");
        searchMember(userData.get("firstName"));
        Assert.assertTrue(validateTextInElements(listIemName,memberName));
        return userData;
    }

    public void editMember(){
        editMember("");
    }

    public void inviteGroup(int amount){
        deletePreviouslyDownloadedFiles(downloadsPath, inviteMembersTemplateFilename );
        clickVisible(inviteMemberBtn);
        clickVisible(inviteGroupBtn);
        clickVisible(downloadBtn);
        Assert.assertTrue(validateFileDownloaded(downloadsPath,inviteMembersTemplateFilename));
        removeSecondRowCSV(downloadedTemplateFilePath);
        for (int i=0;i<amount;i++){
            appendToCSV(downloadedTemplateFilePath,createInviteMemberRecord());
        }
        justSendTextToElement(input,downloadedTemplateFilePath);
        clickVisible("//button[.//span[contains(text(),'Send by Email')]]");

        Assert.assertTrue(waitElementAmountToBe("//i[contains(@class,'success')]",amount/2));

        clickVisible("//button[.//span[contains(text(),'Done')]]");
        clickVisible(inviteUserCardView + backBtn);

    }

    public void validateInviteMemberFieldLengths(){
        waitElementAmountToBeMoreThan(listItem,0);
        clickVisible(inviteMemberBtn);
        userData = fiiInviteMemberMaxLengths();
        waitForElementToBeVisible(toast);
        waitForElementToDisappear(toast);
        refreshScreen();
        validateMemberData(userData);
        deleteMember();
    }

    public void selectMember(String memberName){
        waitForElementToBeVisible(inviteMemberBtn);
        String xpath = stringFormat(listItemNamedTmpl,memberName);
        while (true) {
            try {
                wait(1000);
                sendTextToVisibleElement(input, memberName);
                clickVisible(searchBtn, 4);
                clickVisible(xpath, 4);
                waitForElementToDisappear(xpath, 4);
                break;
            }catch (Throwable t){

            }
        }
    }

    public boolean validateRoleAssociatedWithMember(String roleName){
        waitForElementToBeVisible(roles,10);
        return waitForAttributeToContain(roles,dataTestId,roleName,14);
    }


    public Map<String,String> inviteMember(){
        clickVisible(inviteMemberBtn);
        if(IDP.toLowerCase().contains("okta")){
        return fillInviteMemberOkta();
        }else if (IDP.toLowerCase().contains("auth0")) {
            return fillInviteMemberAuth0();
        }else if (IDP.toLowerCase().contains("sap")) {
            return fillInviteMemberSap();
        }
        return null;
    }

    public Map<String,String> inviteMember(Map<String,String> userData){
        clickVisible(inviteMemberBtn);
        return fillInviteMemberOkta(userData);
    }

    public Map<String,String> editMemberData(){
        userData = new HashMap<>();
        userData.put("department",insertRandomDepartment());
        userData.put("jobFunction",insertRandomJobFunction());
        userData.put("firstName",insertRandomFirstName());
        userData.put("lastName",insertRandomLastName());
        clickSaveButton();
        return userData;
    }

    public Map<String,String> getMemberData(String application){
        userData = new HashMap<>();
        clickEditButton();
        if (application.equalsIgnoreCase("gigya")) {
            userData.put("phone",getInputNameValue(phone));
            userData.put("lastName",getInputNameValue(lastName));
            userData.put("firstName",getInputNameValue(firstName));
            userData.put("email",getInputNameValue(email));
            userData.put("department",getInputNameValue(department));
            userData.put("jobFunction",getInputNameValue(jobFunction));
        }
        else {
            userData.put("email",getInputNameValue(email));
            userData.put("login",getInputNameValue(login));
            userData.put("firstName",getInputNameValue(firstName));
            userData.put("lastName",getInputNameValue(lastName));
            userData.put("jobFunction",getInputNameValue(jobFunction));
            userData.put("department",getInputNameValue(department));
        }
        clickEditButton();
        return userData;
    }

    public void validateMemberData(Map<String,String> userData){
        searchMember(userData.get("firstName").substring(0,30));
        selectMember(userData.get("firstName").substring(0,30));
        clickEditButton();
        String memberEmail = getInputNameValue(email);
        Assert.assertTrue(userData.get("email").contains(memberEmail) && memberEmail.length()==50);
        String memberLogin = getInputNameValue(login);
        Assert.assertTrue(userData.get("login").contains(memberLogin) && memberLogin.length()==50);
        String memberJobFunction = getInputNameValue(jobFunction);
        Assert.assertTrue(userData.get("jobFunction").contains(memberJobFunction) && memberJobFunction.length()==50);
        String memberFirstName = getInputNameValue(firstName);
        Assert.assertTrue(userData.get("firstName").contains(memberFirstName) && memberFirstName.length()==50);
        String memberLastName = getInputNameValue(lastName);
        Assert.assertTrue(userData.get("lastName").contains(memberLastName) && memberLastName.length()==50);
        String memberDepartment = getInputNameValue(department);
        Assert.assertTrue(userData.get("department").contains(memberDepartment) && memberDepartment.length()==50);
    }

    public void validateInsideDA(){
       Assert.assertTrue(waitForElementToBeVisible(inviteMemberBtn));
    }

    public Map<String,String> fillInviteMemberAuth0(){
        String email = getGuerrillaEmail();
        userData = new HashMap<>();
        userData.put("given_name",insertRandomGivenName());
        userData.put("family_name",insertRandomFamilyName());
        userData.put("email",insertEmail(email));
        userData.put("password",insertPasswordAttribute(password));
        userData.put("department",insertRandomDepartment());
        userData.put("jobFunction",insertRandomJobFunction());
        clickEmailSubmitButton();
        return userData;
    }

    public Map<String,String> fillInviteMemberOkta(){
        String email = getGuerrillaEmail();
        userData = new HashMap<>();
        userData.put("department",insertRandomDepartment());
        userData.put("jobFunction",insertRandomJobFunction());
        userData.put("firstName",insertRandomFirstName());
        userData.put("email",insertEmail(email));
        userData.put("lastName",insertRandomLastName());
        userData.put("login",insertLogin(email));
        clickVisibleTry(emailSubmitBtn);
        return userData;
    }

    public Map<String,String> fillInviteMemberSap(){
        String email = getGuerrillaEmail();
        userData = new HashMap<>();
        userData.put("phone", insertRandomPhoneNumber());
        userData.put("lastName",insertRandomLastName());
        userData.put("firstName",insertRandomFirstName());
        userData.put("email",insertEmail(email));
        userData.put("department",insertRandomDepartment());
        userData.put("jobFunction",insertRandomJobFunction());
        clickEmailSubmitButton();
        return userData;
    }

    public Map<String,String> fillInviteMemberOkta(Map<String,String> userData){
        String email = getGuerrillaEmail();
        insertDepartment(userData.get("department"));
        insertJobFunction(userData.get("jobFunction"));
        insertFirstName(userData.get("firstName"));
        insertEmail(email);
        insertLastName(userData.get("lastName"));
        insertLogin(userData.get("login"));
        clickEmailSubmitButton();
        return userData;
    }

    public Map<String,String> fiiInviteMemberMaxLengths(){
        userData = new HashMap<>();
        userData.put("department",insertDepartment(randomStringGenerator(60)));
        userData.put("jobFunction",insertJobFunction(randomStringGenerator(60)));
        userData.put("firstName",insertFirstName(randomStringGenerator(60)));
        String email = mailinator50EmailGenerator();
        userData.put("email",insertEmail(email));
        userData.put("lastName",insertLastName(randomStringGenerator(60)));
        userData.put("login",insertLogin(email));
        clickEmailSubmitButton();
        return userData;
    }

    public String createInviteMemberRecord(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<2;i++){
            sb.append(randomStringGenerator(10));
            sb.append(",");
        }
        for(int i=0;i<2;i++){
            sb.append(randomEmailGenerator());
            sb.append(",");
        }
        for(int i=0;i<2;i++){
            sb.append(randomStringGenerator(10));
            sb.append(",");
        }
        return sb.toString();
    }

    public Map<String,String> fillInviteMemberOkta(String key, String value){
        clickVisible(inviteMemberBtn);
        userData = new HashMap<>();
        if(key.contentEquals("department")){
            userData.put(key,insertDepartment(value));
        }else {
            userData.put("department",insertRandomDepartment());
        }
        if(key.contentEquals("jobFunction")){
            userData.put(key,insertJobFunction(value));
        }else {
            userData.put("jobFunction",insertRandomJobFunction());
        }
        if(key.contentEquals("firstName")){
            userData.put(key,insertFirstName(value));
        }else {
            userData.put("firstName",insertRandomFirstName());
        }
        String email = mailinatorEmailGenerator();
        if(key.contentEquals("email")){
            userData.put(key,insertEmail(value));
        }else {
            userData.put("email",insertEmail(email));
        }
        if(key.contentEquals("lastName")){
            userData.put(key,insertLastName(value));
        }else {
            userData.put("lastName",insertRandomLastName());
        }
        if(key.contentEquals("login")){
            userData.put(key,insertLogin(value));
        }else {
            userData.put("login",insertLogin(email));
        }
        clickEmailSubmitButton();
        return userData;
    }

    public int selectRandomMember() {
        return selectRandomMember(true);
    }

    public int selectRandomMember(boolean invite){
        if(!waitElementAmountToBeMoreThan(listItem,0,10)){
            if(invite) {
                inviteMember();
            }
        }
        return clickRandomListElement(listItem);
    }

    public void reInviteDeletedMember(String application){
        waitElementAmountToBeMoreThan(unregisteredMemberName,0,10);
        wait(800);
        List<String> unregisteredMembers = getElementsListTexts(unregisteredMemberName);
        String member = getRandomStringFromList(unregisteredMembers).split(" ")[0].trim();
        selectMember(member);
        userData = getMemberData(application);
        deleteMember();
        for (int i=0;i<5;i++){
            try {
                wait(5000);
                refreshScreen();
                inviteMember(userData);
                waitForElementToBeVisible(toastSuccess, 4);
                break;
            }catch (Throwable t){
            }
        }
        Assert.assertTrue(waitForElementToBeVisible(toastSuccess, 4));
    }

    public Map<String,String> resendInvitation(){
        waitElementAmountToBeMoreThan(unregisteredMemberName,0,10);
        wait(800);
        List<String> unregisteredMembers = getElementsListTexts(unregisteredMemberName);
        if(unregisteredMembers.isEmpty()){
            Assert.fail("No unregistered members found");
        }else {
            for (String member : unregisteredMembers){
                selectMember(member.trim());
                clickEditButton();
                if(waitForAttributeToContain(emailAttribute,"value","@",8)){
                    data = new HashMap<>();
                    String login = getElementValue(emailAttribute);
                    data.put("email",login);
                    String firstName = getElementValue(firstNameAttribute);
                    data.put("firstName",firstName);
                    clickEditButton();
                    waitForElementToBeClickable(resendInvitationBtn);
                    wait(200);
                    clickResendInvitationBtn();
                    Assert.assertTrue(validateToastMessageAppearance(userWasInvitedSuccessfully));
                    return data;
                }else {
                    clickBackButton();
                }
            }
            Assert.fail("No unregistered members with non-empty login attribute found");
        }
        return null;
    }

    public void activateInactiveMembers(){
        if(waitElementAmountToBeMoreThan(inactiveMemberName,0,10)) {
            wait(800);
            List<String> inactiveMembers = getElementsListTexts(inactiveMemberName);
            for (String member : inactiveMembers) {
                waitElementAmountToBeMoreThan(inactiveMemberName, 0);
                member = member.split(" ")[0];
                selectMember(member);
                toggleValidateSuccessToast(true);
                clickBackButton();
            }
        }
    }

    public Map<String,String> getRegisteredMemberWithRole(){
        waitElementAmountToBeMoreThan(registeredMemberName,0);
        wait(800);
        userData = new HashMap<>();
        List<String> registeredMembers = getElementsListTexts(registeredMemberName);
        for(String member : registeredMembers){
            waitElementAmountToBeMoreThan(registeredMemberName,0);
            member = member.split(" ")[0];
            selectMember(member);
            //           clickManageButton();
            if(!waitForElementToBeVisible(roles,8)) {
                associateMemberWithRandomRole();
            }
            String roleName = getElementAttribute(roles,dataTestId);
            clickEditButton();
            String memberEmail = getInputNameValue(email);
            clickEditButton();
            userData.put("name",member);
            userData.put("roleName",roleName);
            userData.put("email",memberEmail);
            clickBackButton();
            return userData;

        }
        Assert.fail("Could not find registered member with associated roles");
        return userData;
    }

    public String associateMemberWithRandomRole(){
        clickManageButton();
        waitElementAmountToBeMoreThan(checkBox,0,10);
        int rolesAmount = driver.findElements(By.xpath(checkBox)).size();
        int index;
        String selectedCheckbox;
        String selectedRole;
        do {
            index = rnd.nextInt(rolesAmount);
            index++;
            selectedCheckbox = "(" + checkBox + ")[" + index + "]";
            selectedRole = selectedCheckbox + up + "//p";
        }
        while (getElementText(selectedRole).contains("admin"));

        clickVisible(selectedCheckbox);
        clickSaveButton();

        return selectedRole;
    }

    public Map<String,String> resetPassword(){
        waitElementAmountToBeMoreThan(registeredMemberName,0);
        wait(800);
        userData = new HashMap<>();
        List<String> registeredMembers = getElementsListTexts(registeredMemberName);
        for(String member : registeredMembers){
            waitElementAmountToBeMoreThan(registeredMemberName,0);
            member = member.split(" ")[0];
            selectMember(member.trim());
            clickEditButton();
            if(waitForAttributeToContain(loginAttribute,"value","@",8)){
                data = new HashMap<>();
                String login = getElementValue(loginAttribute);
                data.put("email",login);
                String firstName = getElementValue(firstNameAttribute);
                data.put("firstName",firstName);
                clickEditButton();
                waitForElementToBeClickable(resetPasswordBtn);
                clickResetPasswordBtn();
                clickAlertPrimaryBtn();
                Assert.assertTrue(validateToastMessageAppearance(resetEmailWasSent));
                return data;
            }else {
                clickBackButton();
            }
        }
        Assert.fail("Could not find registered member with associated roles");
        return userData;
    }

    public void memberLimitationMain(int limit){
        int i;
        waitForElementToBeVisible(inviteMemberBtn);
        for (i=0;i<limit-1;i++){
            inviteMember();
        }
        if(!waitForElementToBeVisible(inviteMemberBtn,4)){
            clickBackButton();
        }
        assertMembersLimitReached(false);
        inviteMember();
        assertMembersLimitReached(true);
        int removed = randomNumberInRange(1,4);
        for(i=0;i<removed;i++){
            selectRandomMember();
            deleteMember();
        }
        assertMembersLimitReached(false);
        inviteGroup(removed*2);
        assertMembersLimitReached(true);
    }

    public void memberLimitationIncreased(int limit, int delta){
        refreshScreen();
        int i;
         for(i=0;i<10;i++){
            waitForElementToBeVisible(inviteMemberBtn);
            if(waitForElementToDisappear(limitReached,4)){
                break;
            }
            refreshScreen();
            wait(1000);
        }
        assertMembersLimitReached(false);
        for (i=0;i<delta;i++){
            inviteMember();
        }
        assertMembersLimitReached(true);
    }

    public void memberLimitationDecreased(int limit, int delta){
        int i;
        wait(3000);
        refreshScreen();
        assertMembersLimitReached(true);
        if(delta>1){
            selectRandomMember();
            deleteMember();
            wait(5000);
            refreshScreen();
            assertMembersLimitReached(true);
        }
        for(i=0;i<delta-1;i++){
            selectRandomMember();
            deleteMember();
        }
        refreshScreen();
        assertMembersLimitReached(true);
        selectRandomMember();
        deleteMember();
        wait(2000);
        refreshScreen();
        assertMembersLimitReached(false);
    }


    public void assertMembersLimitReached(boolean positive){
        if(positive){
            try {
                Assert.assertTrue(waitForElementToBeVisible(limitReached, 10));
                Assert.assertTrue(getElementAttribute(inviteMemberBtn, "disabled").contentEquals("true"));
            }catch (Throwable t){
                refreshScreen();
                if(!waitForElementToBeVisible(inviteMemberBtn,10)){
                    clickBackButton();
                }
                Assert.assertTrue(waitForElementToBeVisible(limitReached, 10));
                Assert.assertTrue(getElementAttribute(inviteMemberBtn, "disabled").contentEquals("true"));
            }
        }else {
            try {
                Assert.assertFalse(waitForElementToBeVisible(limitReached, 5));
                Assert.assertNull(getElementAttribute(inviteMemberBtn, "disabled"));
            }catch (Throwable t){
                refreshScreen();
                if(!waitForElementToBeVisible(inviteMemberBtn,10)){
                    clickBackButton();
                }
                Assert.assertFalse(waitForElementToBeVisible(limitReached, 3));
                Assert.assertNull(getElementAttribute(inviteMemberBtn, "disabled"));
            }
        }
    }

    public void validateRoleIsAccessible(String roleName, boolean isPositive){
        String role = searchARole(roleName,true);
        if(isPositive) {
            Assert.assertTrue(waitForElementToBeVisible(role,12), stringFormat("Couldn't find %s role accessible", roleName));
        }else {
            Assert.assertFalse(waitForElementToBeVisible(role,10), stringFormat("%s role found accessible while if shouldn't", roleName));
        }
    }

    public String searchARole(String roleName, boolean manage){
        waitForElementToBeVisible(rolesContent,5);
        clickVisibleAppearsLoop(manageBtn,saveBtn);
        return searchARole(roleName);
    }

    public String searchARole(String roleName){
        String role = stringFormat(accessibleNamedRole,roleName);
        sendTextToElement(input,roleName);
        clickSearchButton();
        return role;
    }

    public void associateMemberWithRole(String roleName){
        searchARole(roleName,true);
        String roleCheckbox = stringFormat(roleNamedCheckbox,roleName);
        String roleCheckboxSelected = roleCheckbox + selected;
        if(!waitForElementToBeVisible(roleCheckboxSelected,3)) {
            clickVisible(roleCheckbox);
        }
        wait(300);

        clickVisibleTry(saveBtn);
        wait(800);
    }

    public void removeMemberRoles(){
        wait(2500);
        List<String>roleNames = getElementsAttribute(roles,dataTestId);
        clickManageButton();
        for(String roleName : roleNames){
            searchARole(roleName);
            String roleCheckbox = stringFormat(roleNamedCheckbox,roleName);
            clickVisible(roleCheckbox);
            wait(300);
        }
        scrollElementIntoView(saveBtn);
        wait(200);
        jsClick(saveBtn);
        wait(400);
        waitForElementToDisappear(roles,5);
    }

    public void deleteMember(){
        waitForElementToBeVisible(manageBtn);
        clickDeleteButton();
        clickAlertPrimaryBtn();
        waitForElementToBeVisible(toastSuccess);
        waitForElementToDisappear(toastSuccess);
        waitForElementToBeVisible(inviteMemberBtn);
        String OS = System.getProperty("os.name", "unknown").toLowerCase();
        if(!OS.contains("wind")){
            wait(5000);
        }
    }

    public boolean validateNoMemberExists(String name){
        waitForElementToBeVisible(inviteMemberBtn);
        waitElementAmountToBeMoreThan(listItem,0,10);
        String xpath = stringFormat(listItemNamedTmpl,name);
        wait(1000);
        sendTextToVisibleElement(input,name);
        return !waitForElementToBeVisible(xpath,6);
    }

    public boolean validateNoMembersExisting(){
        waitForElementToBeVisible(inviteMemberBtn);
        return !(waitElementAmountToBeMoreThan(listItem,0,10));
    }

    public void deleteRedundantMembers(){
        try {
            if (getMembersAmount() > 10) {
                List<String> unregisteredMembers = getElementsListTexts(unregisteredMemberName);
                for (String unregisteredMember : unregisteredMembers) {
                    selectMember(unregisteredMember.split(" ")[0]);
                    deleteMember();
                }
            }
        }catch (Throwable t){}
    }

    public int getMembersAmount(){
        waitForElementToBeVisible(inviteMemberBtn);
        waitForElementToBeVisible(memberCounter);
        String OS = System.getProperty("os.name", "unknown").toLowerCase();
        if(!OS.contains("wind")){
            wait(5000);
        }
        int amount = extractIntFromElement(memberCounter);
        return amount;
    }


}
