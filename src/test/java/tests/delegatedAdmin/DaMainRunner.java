package tests.delegatedAdmin;

import com.aventstack.extentreports.model.NameValuePair;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import utilities.RetryAnalyzer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Iterator;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static utilities.Common.*;


public class DaMainRunner extends BaseTest {

    public DaMainRunner(){
        setRepValues();
        environment = getRepValue("environment");
        userName = getRepValue("userName");
        passWord = getRepValue("passWord");
        application = getRepValue("application");
        IDP = getRepValue("IDP");
    }

    //Test data
    String environment;
    String userName;
    String passWord;
    String application;
    String IDP;
    Map<String,String> data;


    @Test(priority = 1 ,groups= {"Sanity"}, description= "Invite user, validate invitation email")
    public void inviteMember(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.deleteRedundantMembers();
        Map<String,String> data = da.inviteMember();
        re.validateGuerrillaEmail(application, IDP, data,false);
        re.switchToPreviousWindow();
//        partners.openDa();
        da.selectMember(data.get("firstName"));
        da.deleteMember();
        Assert.assertTrue(da.validateNoMemberExists(data.get("firstName")));
    }

    @Test(priority = 2 ,groups= {"Regression"}, description= "Try inviting user with existing login")
    public void inviteMemberWithExistingLogin(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.inviteMemberWithExistingAttribute("login");
    }

    @Test(priority = 3 ,groups= {"Regression"}, description= "Try inviting user with existing email")
    public void inviteMemberWithExistingEmail(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.inviteMemberWithExistingAttribute("email");
    }

    @Test(priority = 4 ,groups= {"Regression"}, description= "Edit existing member data")
    public void editMember(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.editMember();
    }

 //   @Test(priority = 5 ,groups= {"Regression"}, description= "Invite group")
    public void inviteGroup(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.inviteGroup(3);
    }

    @Test(priority = 6 ,groups= {"Regression"}, description= "Resend invitation email for unregistered user")
    public void resendInvitation(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        data = da.resendInvitation();
        re.validateGuerrillaEmail(application, IDP, data,true);
        re.cleanGuerrillaInbox();
    }

    @Test(priority = 7 ,groups= {"Regression"}, description= "Resend invitation email for unregistered user")
    public void resetPassword(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        data = da.resetPassword();
        atko.resetPasswordGuerrilla(application, IDP, data.get("email"));
        re.switchToPreviousWindow();
        re.cleanGuerrillaInbox();
    }

    @Test(priority = 8 ,groups= {"Regression"}, description= "Resend invitation email for unregistered user")
    public void reInviteDeletedMember(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.reInviteDeletedMember(application);
    }

    @Test(priority = 9 ,groups= {"Regression"}, description= "Resend invitation email for unregistered user")
    public void inviteMemberFieldsLengths(){
        pmLoginPage.getLogin(userName,passWord);
        partners.openDa();
        da.validateInviteMemberFieldLengths();
    }


    @Test(priority =  10)
    public void wallaUSDRate(){
        wallaHomePage.openWalla();
        wallaHomePage.openWallaFinance();
        double USDRate = wallaFinance.getUSDRate();
        double expectedUSDRate = 3.186;
        Assert.assertEquals(USDRate,expectedUSDRate,"USD rate found not as expected");
    }

    @Test(priority =  11)
    public void loginCocktaile(){
        cocktailale.getCocktailaleLoginPage();
        cocktailale.loginCocktailale();
        cocktailaleInner.inCocktailaleInner();
    }

    @Test(priority =  11)
    public void loginCocktaileResponce() throws Exception {
        cocktailale.getCocktailaleLoginPage();
        cocktailale.loginCocktailale();

//        WebClient client = new WebClient();
//        HtmlPage page = client.getPage("http://www.example.com/");
//        WebResponse response = page.getWebResponse();
//
//        for (NameValuePair header : response.getResponseHeaders()) {
//            System.out.println(header.getName() + " : " + header.getValue());
//        }
//
//
//        WebClient webClient = new WebClient();
//        WebClient.setThrowExceptionOnScriptError(false);
//        HtmlPage currentPage = webClient.getPage("http://myapp.com");
//        WebResponse response = currentPage.getWebResponse();
//        System.out.println(response.getResponseHeaders());
//// And even in subsequent requests
//        currentPage = webClient.getPage("http://myapp.com");
//        response = currentPage.getWebResponse();
//        System.out.println(response.getResponseHeaders());

//
//        URL oracle = new URL("https://cocktailale.web.app");
//        URLConnection yc = oracle.openConnection();
//
//        String headerName = null;
//        for (int i = 1; (headerName = yc.getHeaderFieldKey(i)) != null; i++) {
//            if (headerName.equals("all")) {
//                String statusCode = yc.getHeaderField("Status Code");
//                System.out.println("Status Code  ::  " + statusCode);
//            } else {
//                System.out.println("Header: "+ yc.getHeaderField(i));
//            }
//        }

        LogEntries logs = driver.manage().logs().get("driver");

        int status = -1;

        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();)
        {
            LogEntry entry = it.next();

            try
            {
                JSONObject json = new JSONObject(entry.getMessage());

                System.out.println(json.toString());

                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if (method != null
                        && "Network.responseReceived".equals(method))
                {
                    JSONObject params = message.getJSONObject("params");

                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");
                    String currentURL = driver.getCurrentUrl();


                    if (currentURL.equals(messageUrl))
                    {
                        status = response.getInt("status");

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! returned response for "
                                        + messageUrl + ": " + status);

                        System.out.println(
                                "---------- bingo !!!!!!!!!!!!!! headers: "
                                        + response.get("headers"));
                    }
                }
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        cocktailaleInner.inCocktailaleInner();

    }



    @Test(priority =  15)
    public void biziBasicCreditCalculation(){
        biziLoginPage.getBiziHomePage();
        biziLoginPage.loginBizi();
        biziHomePage.selectCreditForCar();
        String freeText = biziHomePage.insertFreeCreditText();
        int creditAmount = biziHomePage.insertCreditAmount();
        biziHomePage.selectInstallments6();
        biziHomePage.calculateCreditOffer();
        Assert.assertTrue(biziHomePage.validateOfferFreeText(freeText));
        String initialMonthlyPayment = biziHomePage.getMonthlyPayment();
        biziHomePage.selectDrawerInstallments12();
        biziHomePage.recalculateCreditOffer();
        re.wait(5000);
        String newMonthlyPayment = biziHomePage.getMonthlyPayment();
        Assert.assertFalse(initialMonthlyPayment.contentEquals(newMonthlyPayment));
    }






































    @Test(priority =  16)
    public void loginSorbet(){
        String a = "AA";
        String b = "BB";
        Assert.assertEquals(a,b,"Received " + a + " While expected " + b);

//        sorbetLoginPage.getSorbetLoginPage();
//        sorbetLoginPage.loginWithGoogle(sorbetLoginPage.email,sorbetLoginPage.password);
    }



}
