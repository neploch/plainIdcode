package tests.partnerManager;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.Map;

import static utilities.Common.*;


public class PartnerMainRunner extends BaseTest {

    public PartnerMainRunner(){
        setRepValues();
        application = getRepValue("application");
        environment = getRepValue("environment");
        IDP = getRepValue("IDP");
        tenantName = getRepValue("tenantName");
        userName = getRepValue("userName");
        alternativeWorkspaceId = getRepValue("alternativeWorkspaceId");
        alternativeEnvClientId = getRepValue("alternativeEnvClientId");
        alternativeSecret = getRepValue("alternativeSecret");
        serverDeployDelay = Integer.parseInt(getRepValue("serverDeployDelay"));
        passWord = getRepValue("passWord");
    }

    //Test data
    String environment;
    String application;
    String IDP;
    String userName;
    String passWord;
    String tenantName;
    String alternativeWorkspaceId;
    String alternativeEnvClientId;
    String alternativeSecret;
    int serverDeployDelay;

    Map<String,String> map;
    Map<String,String> map1;
    Map<String,String> map2;

     @Test(priority = 2 ,groups= "Sanity", description= "Login tenant, validate inner title")
    public void loginTenant(){
        pmLoginPage.getLogin(userName,passWord);
        env.waitForElementToBeVisible(env.tenantTitle);
        env.waitForTextInElement(env.tenantTitle,tenantName);
    }


    @Test(priority = 3 ,groups= {"Sanity"}, description= "Create partner registration request by API then approve it by GUI")
    public void registrationE2E(){
        apis.getJWT();
        pmLoginPage.getLogin(userName,passWord);
//        map = new HashMap<>();
//        map = partnersWorkspace.getWorkspaceId(map);
//        apis.setEnvParams(map.get("workspaceId"));

//        pmLoginPage.getLogin(userName,passWord);
//        env.selectEnvironment(env.workingEnvName);
        partners.removeRedundantPartners();
        partners.removeRedundantRegistrationRequests();
        String partnerName = apis.registrationRequest();
        partners.approveRegistrationRequest(partnerName,true);
    }

    @Test(priority = 4 ,groups= "Sanity", description= "Create partner registration request by API then decline it by GUI")
    public void registrationDecline(){
        apis.getJWT();
        String partnerName = apis.registrationRequest();
        pmLoginPage.getLogin(userName,passWord);
//        env.selectEnvironment(env.workingEnvName);
        partners.declinePartnerRegistrationRequest(partnerName);
    }

    @Test(priority = 5 ,groups= "Sanity", description= "Suspend active partner")
    public void suspendPartner(){
        pmLoginPage.getLogin(userName,passWord);
//        env.selectEnvironment(env.workingEnvName);
        String name = partners.suspendPartner(application);
        partners.activateSuspendedPartner(name,application);
    }

//    @Test(priority = 6 ,groups= "Sanity", description= "Create new environment")
    public void createEnvironment(){
        pmLoginPage.getLogin(userName,passWord);
        env.createEnvironment();
    }

    @Test(priority = 7 ,groups= {"Sanity"}, description= "Create new partner")
    public void createPartner(){
        pmLoginPage.getLogin(userName,passWord);
//        env.selectEnvironment(env.workingEnvName);
        map = partners.createNewPartner();
        partners.validatePartnerDetails(map);
        partners.deletePartner(map.get("name"));
    }


    @Test(priority = 8 ,groups= "Sanity", description= "Create new application")
    public void createApplication(){
        pmLoginPage.getLogin(userName,passWord);
        String name = authorization.createNewApplication(application);
        authorization.deleteItem(application, "application",name);
    }

    @Test(priority = 9 ,groups= {"Sanity"}, description= "Create new role")
    public void createRole(){
        pmLoginPage.getLogin(userName,passWord);
        map = authorization.createNewRole(application);
        authorization.deleteItem(application,"role",map.get("roleName"));
    }

    @Test(priority = 10 ,groups= {"Sanity"}, description= "Create new role")
    public void fullE2E(){
        pmLoginPage.getLogin(userName,passWord);
        apis.getJWT();
        if(IDP.toLowerCase().contains("auth0")){
            apis.getAuth0AccessToken();
        }
        String envName = "";
        if(IDP.toLowerCase().contains("okta")) {
            envName = "env5";//env.createEnvironment();
        }else if(IDP.toLowerCase().contains("auth0")){
            envName = "env6";
        }
        re.closeElement(re.gotItBtn);
        env.selectEnvironment(envName);
        re.closeElement(re.gotItBtn);
        partners.removeRedundantPartners();
        partners.removeRedundantRegistrationRequests();
        authorization.removeRedundantApplications(application);
        authorization.removeRedundantRoles(application);
        re.closeElement(re.gotItBtn);
        map = env.setIDP();
        map = partnersWorkspace.getWorkspaceId(map);
        apis.setEnvParams(map.get("workspaceId"));
        String envRedirectURI = apis.getPlainIdEnvRedirectURI(map.get("envId"));
        if(IDP.toLowerCase().contains("okta")) {
//            apis.updateOktaRedirectionURIs(envRedirectURI);
            apis.updateOktaInlineHook(map);
        }else if(IDP.toLowerCase().contains("auth0")){
//            apis.updateAuth0Client(envRedirectURI);
            apis.updateAuth0Rules(map);
        }


        String partnerName = apis.registrationRequest();
//        env.selectEnvironment(env.workingEnvName);
        String partnerId = partners.approveRegistrationRequest(partnerName,false);
        String appName = authorization.createNewApplication(application);
        Map<String,String> map3 = authorization.createNewRole(application);
        partners.openPartnerDa(partnerName);
        Map<String,String> map4 = da.inviteMember();
        if(IDP.toLowerCase().contains("okta")){
            partnerName = map4.get("firstName");
        }else if(IDP.toLowerCase().contains("auth0")){
            partnerName = map4.get("given_name");
        }
        da.selectMember(partnerName);
        da.activateUser(application, IDP, map4.get("email"));
        if(IDP.toLowerCase().contains("okta")) {
            atko.resetPassword();
        }
        driver.close();
        re.switchToNewWindow();
        re.cleanGuerrillaInbox();
        driver.close();
        re.switchToNewWindow();
        da.associateMemberWithRole(map3.get("roleName"));

        map.putAll(map4);
        map.putAll(map3);
        map.put("partnerId",partnerId);
        re.wait(serverDeployDelay);

        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }

        driver.close();
        re.switchToNewWindow();

        map2 = partners.createNewPartner();
        partners.openPartnerDa(map2.get("name"));
        Assert.assertTrue(da.validateNoMembersExisting());
        driver.close();
        re.switchToFirstWindow();

        map1 = partners.createNewPartner(true);
        partners.openPartnerDa(map1.get("name"));
        String memberName = "";
        if(IDP.toLowerCase().contains("okta")){
            memberName = map1.get("firstName");
        }else if(IDP.toLowerCase().contains("auth0")){
            memberName = map1.get("given_name");
        }
        da.selectMember(memberName);
        da.activateUser(application, IDP, map1.get("email"));
        if(IDP.toLowerCase().contains("okta")) {
            atko.resetPassword();
        }
        driver.close();
        re.switchToNewWindow();
        re.cleanGuerrillaInbox();
        driver.close();
        re.switchToNewWindow();
        map.putAll(map1);
        Assert.assertTrue(da.validateRoleAssociatedWithMember(re.delegatedAdminRoleName));
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"delegated_admin");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map,"delegatedAdmin");
        }
        driver.close();
        re.switchToFirstWindow();

        oid.fillOidData(IDP);
        atko.loginAtko(map4, IDP);
        oid.assertRoleEntitlementIsInTokenResponse(map3);
        atko.loginDaExternally(map.get("workspaceId"), map1, IDP);
    }

    @Test(priority = 31 ,groups= {"Regression"},  description= "Edit existing partner")
    // Converted to Gigya
    public void editPartner(){
        pmLoginPage.getLogin(userName,passWord);
        partners.editPartner();
    }

    @Test(priority = 32 ,groups= {"Regression"},  description= "Validate partner fields lengths")
    public void validatePartnerFieldsLengths(){
        pmLoginPage.getLogin(userName,passWord);
        partners.validatePartnerFieldsLengths();
    }

    @Test(priority = 33 ,groups= {"Regression"}, description= "Open partners full view")
    // Converted to Gigya
    public void openPartnersFullView(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openPartnersFullView();
    }

    @Test(priority = 34 ,groups= {"Regression"}, description= "Try registration with existing partner name")
    // Waiting for partner registration request fix to convert to gigya
    public void registrationWithExistingName(){
        pmLoginPage.getLogin(userName,passWord);
        partners.selectPartnersTab();
        String name = partners.getRandomListItemName(partners.partnerItem);
        apis.getJWT();
        String partnerName = apis.registrationRequest(name);
        partners.validateExistingNameInRegistrationRequest(partnerName);
    }

    @Test(priority = 35 ,groups= {"Regression"}, description= "Create partner registration request by API then edit and decline it by GUI")
    // Waiting for partner registration request fix to convert to gigya
    public void editRegistrationRequest(){
        apis.getJWT();
        String name = apis.registrationRequest();
        pmLoginPage.getLogin(userName,passWord);
        partners.editRegistrationRequest(name);
    }

    @Test(priority = 36 ,groups= {"Regression"}, description= "Try setting partners attribute name as a number")
    // Converted to Gigya
    public void nameAttributeCantBeNumber(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.nameAttributeCantBeNumber(application);
    }

    @Test(priority = 37 ,groups= {"Regression"}, description= "Check 'Can be updated' attribute functionality")
    // Converted to Gigya
    public void attributeCantBeUpdated(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.canBeUpdated(application);
    }

    @Test(priority = 38 ,groups= {"Regression"}, description= "Attribute length limitations")
    // Converted to Gigya
    public void attLengthLimitations(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.attLengthLimitations(application);
    }

    @Test(priority = 39 ,groups= {"Regression"}, description= "Multi value attribute set On can't be reverted")
    // Converted to Gigya
    public void multiValuedAttImmutable(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.multiValuedAttImmutable(application);
    }

    @Test(priority = 40 ,groups= {"Regression"}, description= "PMS required attribute functionality validation")
    // Converted to Gigya
    public void pmsRequired(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.pmsRequired(application);
    }

    @Test(priority = 41 ,groups= {"Regression"}, description= "RMS required attribute functionality validation")
    // Waiting for partner registration request fix to convert to gigya
    public void rmsRequired(){
        pmLoginPage.getLogin(userName,passWord);
        String attName = partnersWorkspace.turnRmsRequiredOff(application);
        apis.getJWT();
        String partnerName = apis.registrationRequestPMMissAttribute(attName);
        partnersWorkspace.getOutFromPartnerWorkspaceSettings();
        partners.declinePartnerRegistrationRequest(partnerName);
        partnersWorkspace.turnRmsRequiredON(attName);
    }

    @Test(priority = 42 ,groups= {"Regression"}, description= "Delegated Admin colors and text font settings")
    // Converted to Gigya
    public void daColorsAndFont(){
        pmLoginPage.getLogin(userName,passWord);
        map = partnersWorkspace.setDaColorsAndFont();
        partnersWorkspace.getOutFromPartnerWorkspaceSettings();
        partners.openDa();
        da.validateColorsAndFont(map);
    }

    @Test(priority = 43 ,groups= {"Regression"}, description= "Add language in Partner Workspace Settings, activate, set as default")
    public void addLanguage(){
        pmLoginPage.getLogin(userName,passWord);
        partnersWorkspace.addLanguage(application);
    }

    @Test(priority = 44 ,groups= {"Regression"}, description= "Set display data in Partner Workspace Settings and validate it in Delegated Admin")
    // isRegistered, organization and status member attributes doesn't appear in invite members
    public void displayData(){
        pmLoginPage.getLogin(userName,passWord);
        Map<String, String []> data = partnersWorkspace.fillDaDisplayData();
        partnersWorkspace.getOutFromPartnerWorkspaceSettings();
        partners.openDa();
        da.validateDisplayData(data);
    }

    @Test(priority = 45 ,groups= {"Regression"}, description= "Set Delegated Admin Strings and logo in Partner Workspace Settings and validate it in Delegated Admin")
    // Converted to Gigya
    public void daStrings(){
        pmLoginPage.getLogin(userName,passWord);
        map = partnersWorkspace.setDaStrings();
        partnersWorkspace.getOutFromPartnerWorkspaceSettings();
        partners.openDa();
        da.validateStrings(map);
    }

    @Test(priority = 46 ,groups= {"Regression"}, description= "Edit existing application")
    // Converted to Gigya
    public void editApplication(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.editApplication(application);
    }

    @Test(priority = 47 ,groups= {"Regression"}, description= "Upload empty entitlement CSV and validate the error notifications")
    // Converted to Gigya
    public void uploadEmptyEntitlements(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.uploadEmptyEntitlements(application);
    }

    @Test(priority = 48 ,groups= {"Regression"}, description= "Try uploading invalid entitlement - missing required attribute, remove invalid entitlement during import")
    // Converted to Gigya
    public void uploadInvalidEntitlement(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.uploadInvalidEntitlement(application);
    }

    @Test(priority = 49 ,groups= {"Regression"}, description= "During importing entitlement edit it")
    // Converted to Gigya
    public void editImportingEntitlement(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.editImportingEntitlement(application);
    }

    @Test(priority = 50 ,groups= {"Regression"}, description= "During importing entitlement delete it")
    // Converted to Gigya
    public void deleteImportingEntitlement(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.deleteImportingEntitlement(application);
    }

    @Test(priority = 51 ,groups= {"Regression"}, description= "Try importing entitlement with existing name")
    // Converted to Gigya
    public void uploadEntitlementExistingName(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.uploadEntitlementExistingName(application);
    }

    @Test(priority = 52 ,groups= {"Regression"}, description= "Create new entitlement")
    // Converted to Gigya
    public void createEntitlement(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.createEntitlement(application);
    }

    @Test(priority = 53 ,groups= {"Regression"}, description= "Try creating entitlement with existing name")
    // Converted to Gigya
    public void createEntitlementWithExistingName(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.createEntitlementWithExistingName(application);
    }

    @Test(priority = 54 ,groups= {"Regression"}, description= "Edit existing entitlement")
    // Converted to Gigya
    public void editEntitlement(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.editEntitlement(application);
    }

    @Test(priority = 55 ,groups= {"Regression"}, description= "Try creating entitlement with existing name")
    // Converted to Gigya
    public void createRoleWithExistingName(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.createRoleWithExistingName();
    }

    @Test(priority = 56 ,groups= {"Regression"}, description= "Edit existing role details")
    // Converted to Gigya
    public void editRoleDetails(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.editRoleName(application);
    }

    @Test(priority = 57 ,groups= {"Regression"}, description= "Manipulate role entitlement selections")
    // Converted to Gigya
    public void roleEntitlementSelections(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.roleEntitlementSelections(application);
    }

    @Test(priority = 58 ,groups= {"Regression"}, description= "Edit existing environment name")
    public void editEnvironment(){
        pmLoginPage.getLogin(userName,passWord);
        env.editEnvironment();
    }

 //   @Test(priority = 38 ,groups= {"Regression"}, description= "Create environment with existing name")
    public void createEnvironmentWithExistingName(){
        pmLoginPage.getLogin(userName,passWord);
        env.createEnvironmentWithExistingName();
    }

    @Test(priority = 60 ,groups= {"Regression"}, description= "Create incomplete role and validate")
    // Converted to Gigya
    public void incompleteRole(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.incompleteRole(application);
    }

    @Test(priority = 61 ,groups= {"Regression"}, description= "Select entitlement for role, validate, change selection, validate")
    // Converted to Gigya
    public void entitlementsReview(){
        pmLoginPage.getLogin(userName,passWord);
        authorization.entitlementsReview(application);
    }

    @Test(priority = 62 ,groups= {"Regression"}, description= "Edit Environment details")
    // Converted to Gigya
    public void editEnvDetails(){
        pmLoginPage.getLogin(userName,passWord);
        env.editEnvDetails(application);
    }

    @Test(priority = 63 ,groups= {"Regression"}, description= "Set role available to explicit partners and validate")
    // Converted to Gigya
    public void roleAvailabilitySelectPartnersExplicitly(){
        pmLoginPage.getLogin(userName,passWord);
        String selectedRole = authorization.roleAvailabilitySelectPartnersExplicitly(application);
        da.selectRandomMember();
        da.validateRoleIsAccessible(selectedRole,true);
    }

    @Test(priority = 64 ,groups= {"Regression"}, description= "Set role available according to partner's attribute value")
    // Converted to Gigya
    public void roleAvailabilityByPartnerAttribute(){
        pmLoginPage.getLogin(userName,passWord);
        map = partners.getRandomPartnerRandomAttributeValue();
        String selectedRole = authorization.roleAvailabilityByPartnersAttribute(application, map,true);
        da.selectRandomMember();
        da.validateRoleIsAccessible(selectedRole,true);
    }

    @Test(priority = 65 ,groups= {"Regression"}, description= "Set role available according to partner's attribute value negative")
    // Converted to Gigya
    public void roleAvailabilityByPartnerAttributeNegative(){
        pmLoginPage.getLogin(userName,passWord);
        map = partners.getRandomPartnerRandomAttributeValue();
        String selectedRole = authorization.roleAvailabilityByPartnersAttribute(application, map,false);
        da.selectRandomMember();
        da.validateRoleIsAccessible(selectedRole,false);
    }

    @Test(priority = 66 ,groups= {"Regression"}, description= "Change attribute section")
    // Converted to Gigya
    public void changeAttributeSection(){
        pmLoginPage.getLogin(userName,passWord);
        map = partnersWorkspace.toggleAttribute(application, "");
        partners.validateAttributeIsInSection(map);
        map = partnersWorkspace.toggleAttribute(application, map.get("attName"));
    }

    //    @Test(priority = 100 ,groups= {"Regression"}, description= "Run Time End to End flow")
    public void RTE2E(){
        pmLoginPage.getLogin(userName,passWord);
        env.createEnvironment();
        re.closeElement(re.gotItBtn);
        map = env.setIDP();
        partners.createNewPartner();
        map = partners.getPartnerId(map);
        map = partnersWorkspace.getWorkspaceId(map);

        Map<String,String> roleData = authorization.createNewRole(application);

        authorization.createNewApplication(application);
        partners.openDa();
        map1 = da.inviteMember();
        da.selectMember(map1.get("firstName"));
        da.associateMemberWithRole(roleData.get("roleName"));
        re.activateUser(application, IDP, map1.get("email"));
        map.putAll(roleData);
        map.put("email",map1.get("email"));
        re.wait(serverDeployDelay);

        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
        re.switchToFirstWindow();
        partners.deletePartner(map.get("name"));
    }

    @Test(priority = 101 ,groups= {"Regression"}, description= "Run Time basic flow")
    // runtime validation missing
    public void RTBasic(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();

        Map<String,String> roleData = authorization.createNewRole(application);

        String appName = authorization.createNewApplication(application);
        partners.openDa();
        map1 = da.inviteMember();
        da.selectMember(map1.get("firstName"));
        da.associateMemberWithRole(roleData.get("roleName"));
        re.activateUser(application, IDP, map1.get("email"));
        map.putAll(roleData);
        map.put("email",map1.get("email"));
        re.wait(serverDeployDelay);

        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
        re.switchToFirstWindow();
        authorization.deleteItem(application,"app",appName);
        authorization.deleteItem(application,"role",roleData.get("roleName"));
    }

    @Test(priority = 102 ,groups= {"Regression"}, description= "Run Time edit existing member assignment")
    // runtime validation missing
    public void RTEditExistingMemberAssignment(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();

        Map<String,String> roleData = authorization.editRoleNameAndEntitlement(application);

        partners.openDa();
        da.selectRandomMember();
        map1 = da.getMemberData(application);
        da.associateMemberWithRole(roleData.get("roleName"));
        map.put("email",map1.get("email"));
        map.putAll(roleData);
        re.wait(serverDeployDelay);

        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
    }

    @Test(priority = 103 ,groups= {"Regression"}, description= "Run Time edit role name")
    // registered members missing
    public void RTEditRoleName(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        re.switchToFirstWindow();
        Map<String,String> roleData = authorization.editRoleName(application,map.get("roleName"));

        map.putAll(roleData);
        re.wait(serverDeployDelay);

        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
    }

    @Test(priority = 104 ,groups= {"Regression"}, description= "Run Time for unregistered member")
    // runtime validation missing
    public void RTUnregisteredMember(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();

        Map<String,String> roleData = authorization.editRoleNameAndEntitlement(application);

        partners.openDa();
        map1 = da.inviteMember();
        da.selectMember(map1.get("firstName"));
        da.associateMemberWithRole(roleData.get("roleName"));
        map.putAll(roleData);
        map.put("email",map1.get("email"));
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"no roles");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "no roles");
        }
    }

    @Test(priority = 105 ,groups= {"Regression"}, description= "Run Time for member with no roles")
    // runtime validation missing
    public void RTNoRoles(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();

        partners.openDa();
        da.selectRandomMember();
        map1 = da.getMemberData(application);
        da.removeMemberRoles();

        map.put("email",map1.get("email"));
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no roles");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "no roles");
        }
    }

    @Test(priority = 106 ,groups= {"Regression"}, description= "Run Time for deleted member")
    // runtime validation missing
    public void RTDeletedMember(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();

        partners.openDa();
        da.selectRandomMember();
        map1 = da.getMemberData(application);
        da.deleteMember();

        map.put("email",map1.get("email"));
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no member");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "no member");
        }
    }

    @Test(priority = 107 ,groups= {"Regression"}, description= "Run Time edit member")
    // registered members missing
    public void RTEditMember(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        map1 = da.editMember(map1.get("name"));
        map.putAll(map1);

        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "member details");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "member details");
        }
    }

    @Test(priority = 108 ,groups= {"Regression"}, description= "Run Time for non existing member")
    // registered members missing
    public void RTNonExistingMember(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        String email = mailinatorEmailGenerator();
        map.put("email",email);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no member");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "no member");
        }
    }

    @Test(priority = 109 ,groups= {"Regression"}, description= "Run Time for invalid clientId")
    // registered members missing
    public void RTInvalidClientId(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        String originalClientId = map.get("envClientId");
        String envClientId = originalClientId.substring(0,3) + randomStringGenerator(4) + originalClientId.substring(6);
        map.put("envClientId",envClientId);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "invalid clientId");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "invalid clientId");
        }
    }

    @Test(priority = 110 ,groups= {"Regression"}, description= "Run Time for invalid secret")
    // registered members missing
    public void RTInvalidClientSecret(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        String originalSecret = map.get("secret");
        String secret = originalSecret.substring(0,3) + randomStringGenerator(4) + originalSecret.substring(6);
        map.put("secret",secret);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "invalid secret");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "invalid secret");
        }
    }

    @Test(priority = 111 ,groups= {"Regression"}, description= "Run Time for workspaceId from other environment")
    // registered members missing
    public void RTOtherWorkspaceId(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        map.put("workspaceId",alternativeWorkspaceId);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no roles");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "other workspaceId");
        }
    }

    @Test(priority = 112 ,groups= {"Regression"}, description= "Run Time for clientId from other environment")
    // registered members missing
    public void RTOtherClientId(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        map.put("envClientId",alternativeEnvClientId);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "invalid secret");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "invalid secret");
        }
    }

    @Test(priority = 113 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTOtherSecret(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        map.put("secret",alternativeSecret);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "invalid secret");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "invalid secret");
        }
    }

    @Test(priority = 114 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTSuspendedPartner(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        re.switchToFirstWindow();
        Map<String,String> roleData = authorization.editRoleName(application, map.get("roleName"),true);
        map.putAll(roleData);
        String name = partners.suspendPartner(application);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no member");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "suspended partner");
        }
        partners.activateSuspendedPartner(name,application);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
    }

    @Test(priority = 115 ,groups= {"Regression","RT"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTExcludeIdentity(){
        if(!environment.toLowerCase().contains("prod")) {
            pmLoginPage.getLogin(userName, passWord);
            map = apis.getRTData();
            partners.openDa();
            map1 = da.getRegisteredMemberWithRole();
            map.putAll(map1);
            da.selectMember(map1.get("name"));
            map1 = da.getMemberData(application);
            map.putAll(map1);

            apis.callRT(map, "excludeIdentity");
        }
    }

    @Test(priority = 116 ,groups= {"Regression","RT"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTExcludePermissions(){
        if(!environment.toLowerCase().contains("prod")) {
            pmLoginPage.getLogin(userName, passWord);
            map = apis.getRTData();
            partners.openDa();
            map1 = da.getRegisteredMemberWithRole();
            map.putAll(map1);
            da.selectMember(map1.get("name"));
            map1 = da.getMemberData(application);
            map.putAll(map1);

            apis.callRT(map, "excludePermissions");
        }
    }

    @Test(priority = 117 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTNewSecret(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        String secret = env.regenerateSecret();

        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        re.switchToFirstWindow();
        Map<String,String> roleData = authorization.editRoleName(application, map.get("roleName"),true);
        map.putAll(roleData);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "invalid secret");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "invalid secret");
        }

        map.put("secret",secret);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }

        String fileName = application + environment + IDP;
        updateRepositoryValue(fileName, "//secret", secret);
        re.wait(serverDeployDelay);
    }

    @Test(priority = 118 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTInactiveMember(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        partners.openDa();
        da.activateInactiveMembers();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        re.toggleValidateSuccessToast(false);
        re.waitForElementToDisappear(re.toast);

        map.putAll(map1);

        re.switchToFirstWindow();
        Map<String,String> roleData = authorization.editRoleName(application, map.get("roleName"),true);
        map.putAll(roleData);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "no member");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map, "no member"); //invalid api key
        }

        re.switchToNewWindow();
        re.toggleValidateSuccessToast(true);
        re.waitForElementToDisappear(re.toast);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
    }

    @Test(priority = 119 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    // registered members missing
    public void RTSecondSecret(){
        pmLoginPage.getLogin(userName,passWord);
        map = apis.getRTData();
        String secret = env.addSecret();

        partners.openDa();
        map1 = da.getRegisteredMemberWithRole();
        map.putAll(map1);
        da.selectMember(map1.get("name"));
        map1 = da.getMemberData(application);
        map.putAll(map1);

        re.switchToFirstWindow();
        Map<String,String> roleData = authorization.editRoleName(application, map.get("roleName"),true);
        map.putAll(roleData);
        re.wait(serverDeployDelay);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }

        map.put("secret",secret);
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map, "");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map);
        }
        String newSecret = env.removeSecret();
        String fileName = application + environment + IDP;
        updateRepositoryValue(fileName, "//secret", newSecret);
        re.wait(serverDeployDelay);
    }

    @Test(priority = 130 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    public void memberLimitNewPartner(){
        pmLoginPage.getLogin(userName,passWord);
        map = partners.createNewPartner();
        int initialLimit = randomNumberInRange(10,15);
        partners.setPartnerAttribute(map.get("name"),"member_limit",Integer.toString(initialLimit));
        int initialMembersCount = partners.getTotalMembers();
        partners.openPartnerDa(map.get("name"));
        da.memberLimitationMain(initialLimit);
        re.switchToFirstWindow();
        re.refreshScreen();
        int addedMembersCount = partners.getTotalMembers();
        Assert.assertTrue(addedMembersCount==initialMembersCount+initialLimit);
        int increaseBy = randomNumberInRange(1,4);
        int limit = initialLimit + increaseBy;
        partners.setPartnerAttribute(map.get("name"),"member_limit",Integer.toString(limit));
        re.switchToNewWindow();
        da.memberLimitationIncreased(limit,increaseBy);
        re.switchToFirstWindow();
        re.refreshScreen();
        int increasedMembersCount = partners.getTotalMembers();
        Assert.assertTrue(increasedMembersCount==initialMembersCount+limit);
        int decreaseBy = randomNumberInRange(1,4);
        limit = limit - decreaseBy;
        partners.setPartnerAttribute(map.get("name"),"member_limit",Integer.toString(limit));
        re.switchToNewWindow();
        da.memberLimitationDecreased(limit,decreaseBy);
        re.switchToFirstWindow();
        re.refreshScreen();
        re.wait(50);
        re.refreshScreen();
        int decreasedMembersCount = partners.getTotalMembers();
        Assert.assertTrue(decreasedMembersCount==initialMembersCount+limit-1);
        partners.deletePartner(map.get("name"));
        partners.refreshScreen();
        int finalMembersCount = partners.getTotalMembers();
        Assert.assertTrue(finalMembersCount==initialMembersCount);
    }

    @Test(priority = 131 ,groups= {"Regression"}, description= "Run Time for secret from other environment")
    public void partnerWithRequester() {
        pmLoginPage.getLogin(userName, passWord);
        map = apis.getRTData();
        map1 = partners.createNewPartner(true);
        map.putAll(map1);
        partners.openPartnerDa(map1.get("name"));
        da.selectMember(map1.get("firstName"));
        Assert.assertTrue(da.validateRoleAssociatedWithMember(re.delegatedAdminRoleName));
        if (!application.equalsIgnoreCase("gigya")) {
            apis.callHook(map,"delegated_admin");
        }
        if(!environment.toLowerCase().contains("prod")) {
            apis.callRT(map,"delegatedAdmin");
        }
        re.switchToFirstWindow();
        partners.deletePartner(map1.get("name"));
    }

}
